package com.wilthord.ld38.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.wilthord.ld38.Engine.GameState;
import com.wilthord.ld38.util.Assets;

/**
 * Created by wilthord on 27/08/2016.
 */
public class GameObject {

    protected Sprite curSprite;
    public Vector2 pos;
    protected Vector2 tam;
    public boolean alive = true;

    //Variable utilizada, para seguir ppintando el objeto, mientras se reproduce la animación de muerte
    public boolean muriendo = false;
    public Body body;

    public void update(){

    }

    public void draw(){
        Assets.getInstance().pintarSprite(GameState.batch, curSprite, pos, tam);
    }
}
