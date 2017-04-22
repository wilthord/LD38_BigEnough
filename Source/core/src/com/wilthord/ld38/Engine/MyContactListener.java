package com.wilthord.ld38.Engine;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.wilthord.ld38.Entities.Disparo;
import com.wilthord.ld38.Entities.IActor;
import com.wilthord.ld38.Entities.Personaje;
import com.wilthord.ld38.util.Constantes;

/**
 * Created by wilthord on 27/08/2016.
 */
public class MyContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Object objA = contact.getFixtureA().getUserData();
        Object objB = contact.getFixtureB().getUserData();

        //Detectamos las colisiones de un disparo
        if(objA!= null  && objA instanceof Disparo){
            if(calcularColisionDisparo((Disparo) objA, objB)){
                return;
            }
        }else if(objB!= null  && objB instanceof Disparo){
            if(calcularColisionDisparo((Disparo) objB, objA)){
                return;
            }
        }

    }

    public boolean calcularColisionDisparo(Disparo disp, Object otro){

        if(otro != null) {
/*            if (otro instanceof String && otro.equals(Constantes.TILEOBJ_PISO)) {
                disp.desplegar();
                return true;
            }*/
        }

        //Si el disparo colisiona con cualquier otra cosa, se debe destruir
        disp.destruir();

        return true;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
