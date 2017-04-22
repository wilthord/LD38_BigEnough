package com.wilthord.ld38;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.wilthord.ld38.Engine.GameState;
import com.wilthord.ld38.Engine.InputEngine;
import com.wilthord.ld38.Engine.MyContactListener;
import com.wilthord.ld38.Entities.Disparo;
import com.wilthord.ld38.Entities.GameObject;
import com.wilthord.ld38.Entities.Personaje;
import com.wilthord.ld38.util.Constantes;

public class BigEnoughGame extends ApplicationAdapter {
	//SpriteBatch batch;
	Texture img;
	//OrthogonalTiledMapRenderer renderer;
	TiledMap mapa;
	OrthographicCamera camera;
	InputEngine inputEngine;

	Box2DDebugRenderer debugRenderer;

	Personaje miPlayer;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		Box2D.init();
		GameState.world = new World(new Vector2(0, -10), true);
		GameState.world.setContactListener(new MyContactListener());
		debugRenderer = new Box2DDebugRenderer();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w * Constantes.ESCALA_GENERAL + 7, h * Constantes.ESCALA_GENERAL + 7);
		//renderer = new OrthogonalTiledMapRenderer();
		//renderer.setView(camera);
		//camera.zoom+=0.5;
		camera.position.x=(w*2)*Constantes.ESCALA_GENERAL/2;
		camera.position.y=h*Constantes.ESCALA_GENERAL/2;
		camera.update();

		miPlayer = new Personaje(new Vector2(128*Constantes.ESCALA_GENERAL, 128*Constantes.ESCALA_GENERAL));
		GameState.player = miPlayer;
		GameState.entidades.add(GameState.player);

		inputEngine = new InputEngine();
		Gdx.input.setInputProcessor(inputEngine);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Se realiza el update de todos los GameObjects
		//doPhysicsStep(Gdx.graphics.getDeltaTime());
		this.update(Gdx.graphics.getDeltaTime());

		float tempPlayerPosx = GameState.player.getPosicion().x;
		float tempPlayerPosy = GameState.player.getPosicion().y;
		if(camera.position.x-tempPlayerPosx>Constantes.DISTACIA_PLAYER_CAMARA.x){
			camera.position.x=tempPlayerPosx+Constantes.DISTACIA_PLAYER_CAMARA.x;
		}else if(camera.position.x-tempPlayerPosx<-Constantes.DISTACIA_PLAYER_CAMARA.x){
			camera.position.x=tempPlayerPosx-Constantes.DISTACIA_PLAYER_CAMARA.x;
		}

		if(camera.position.y  - tempPlayerPosy > Constantes.DISTACIA_PLAYER_CAMARA.y){
			camera.position.y=tempPlayerPosy+Constantes.DISTACIA_PLAYER_CAMARA.y;
		}else if(camera.position.y-tempPlayerPosy<-Constantes.DISTACIA_PLAYER_CAMARA.y){
			camera.position.y=tempPlayerPosy-Constantes.DISTACIA_PLAYER_CAMARA.y;
		}


		//camera.position.x=cameraPos;
		camera.update();
		//renderer.setView(camera);
		//renderer.render();

		GameState.batch.setProjectionMatrix(camera.combined);
		GameState.batch.begin();
		//Borrar
		//miPlayer.draw();
		for(GameObject go:GameState.entidades){
			go.draw();
		}
		GameState.batch.end();

		GameState.world.step(1 / 45f, 6, 2);

		debugRenderer.render(GameState.world, camera.combined);
	}

	public void update(float deltaTime){
		if(GameState.tiempoProximoDisparo>0){
			GameState.tiempoProximoDisparo-=deltaTime;
		}
		if(inputEngine.isClicked) {
			inputEngine.isClicked = false;
			if(GameState.tiempoProximoDisparo<=0f) {
				GameState.tiempoProximoDisparo=Constantes.TIEMPO_CARGA_DISPARO;
				Vector3 projectedPos = new Vector3(inputEngine.lastPressed.x, inputEngine.lastPressed.y, 0);
				Vector3 tempPos = camera.unproject(projectedPos);
				Vector2 direccion = new Vector2(tempPos.x , tempPos.y);//.sub(GameState.player.getPosicion()).nor();
				GameState.entidades.add(new Disparo(new Vector2(GameState.player.getPosicion().x, GameState.player.getPosicion().y), direccion));
			}else{
				System.out.println("Cargando Poder!!!"+GameState.tiempoProximoDisparo);
			}
		}

		for(GameObject go:GameState.entidades){
			if(!go.alive && !go.muriendo){
				GameState.entidadesEliminar.add(go);
			}
			go.update();

		}

		for(GameObject go:GameState.entidadesEliminar){
			GameState.entidades.remove(go);
			GameState.world.destroyBody(go.body);
		}

		GameState.entidadesEliminar.clear();

		GameState.entidadesCrear.clear();
	}

	private void doPhysicsStep(float deltaTime) {
		// fixed time step
		// max frame time to avoid spiral of death (on slow devices)
		GameState.world.step(deltaTime, Constantes.VELOCITY_ITERATIONS, Constantes.POSITION_ITERATIONS);
		/*
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= Constantes.TIME_STEP) {
			GameState.world.step(Constantes.TIME_STEP, Constantes.VELOCITY_ITERATIONS, Constantes.POSITION_ITERATIONS);
			accumulator -= Constantes.TIME_STEP;
		}*/

		return;
	}
}
