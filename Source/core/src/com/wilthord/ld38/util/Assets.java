package com.wilthord.ld38.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by wilthord on 27/08/2016.
 */
public class Assets {

    private static Assets instancia;

    private Assets(){

    }

    public static Assets getInstance(){
        if(instancia==null) {
            instancia = new Assets();
        }
        return instancia;
    }

    public void pintarSprite(SpriteBatch batch, Sprite tex, Vector2 pos){
        //tex.draw(batch);
        batch.draw(tex, pos.x, pos.y);
    }

    public void pintarSprite(SpriteBatch batch, Sprite tex, Vector2 pos, Vector2 tam){
        batch.draw(tex, pos.x, pos.y, tam.x, tam.y);
    }

}
