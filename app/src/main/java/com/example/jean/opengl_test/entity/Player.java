package com.example.jean.opengl_test.entity;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.example.jean.opengl_test.shapes.Triangle;
import com.example.jean.opengl_test.utils.Vect;

import java.util.ArrayList;

/**
 * Created by Kwarthys on 04/10/2016.
 */

public class Player extends Triangle implements Entity{

    private final float MAX_DX = 2.1f;

    private int _score = 0;
    private float dx = 0;
    private long _time;
    protected long _shootingRate = 1000;
    private ArrayList<Missile> _missiles = new ArrayList<>();

    public float getDX(){return dx;}

    public void setDX(float newDX){dx = newDX;}

    public final int getScore() { return _score; }

    public void incScore(final int inc) {
        _score += inc;
        if(_score < 0) { _score = 0; }
    }

    public void move(float deltaTime){
        pos.set_x(pos.get_x() + dx * deltaTime);
    }

    public Missile[] shoot() {
        if(SystemClock.uptimeMillis() - _time > _shootingRate) {
            _time = SystemClock.uptimeMillis();
            Missile missiles[];
            if(_score >= 100) {
                _missiles.add(new Missile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, 0.0f));
                _missiles.add(new Missile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 1.0f, 10.0f));
                _missiles.add(new Missile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 1.0f, -10.0f));
                _missiles.add(new SideMissile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, 90.0f));
                _missiles.add(new SideMissile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, -90.0f));
                missiles = new Missile[]{
                        _missiles.get(_missiles.size() - 1),
                        _missiles.get(_missiles.size() - 2),
                        _missiles.get(_missiles.size() - 3),
                        _missiles.get(_missiles.size() - 4),
                        _missiles.get(_missiles.size() - 5)
                };
            } else if(_score >= 40) {
                _missiles.add(new Missile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, 10.0f));
                _missiles.add(new Missile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, -10.0f));
                _missiles.add(new SideMissile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, 90.0f));
                _missiles.add(new SideMissile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, -90.0f));
                missiles = new Missile[]{
                        _missiles.get(_missiles.size() - 1),
                        _missiles.get(_missiles.size() - 2),
                        _missiles.get(_missiles.size() - 3),
                        _missiles.get(_missiles.size() - 4)
                };
            } else if(_score >= 10) {
                _missiles.add(new Missile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, 0.0f));
                _missiles.add(new SideMissile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, 90.0f));
                _missiles.add(new SideMissile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, -90.0f));
                missiles = new Missile[]{
                        _missiles.get(_missiles.size() - 1),
                        _missiles.get(_missiles.size() - 2),
                        _missiles.get(_missiles.size() - 3)
                };
            } else {
                _missiles.add(new Missile(_ActivityContext, pos.get_x(), pos.get_y(), 0.15f, 2.0f, 0.0f));
                missiles = new Missile[]{
                        _missiles.get(_missiles.size() - 1)
                };
            }
            return missiles;
        }
        return null;
    }

    public boolean bound(float limitInf, float limitSup)
    {
        if(pos.get_x() < limitInf) {
            pos.set_x(limitInf);
        }
        else if(pos.get_x() > limitSup) {
            pos.set_x(limitSup);
        }

        return true;
    }

    public boolean isHit(float x, float y) {
        return false;
    }

    public final ArrayList<Missile> getMissiles() { return _missiles; }

    public void removeMissile(Missile m) { _missiles.remove(m); }

    public Player(Context context, float leX, float leY, float squaredScale)
    {
        super(context);
        super.init();
        _time = SystemClock.uptimeMillis();
        pos.set_x(leX);
        pos.set_y(leY);
        scale = new Vect(squaredScale, squaredScale, squaredScale, this);
    }

    public void setDestination(float target, float maxInput)
    {
        //float targetX = (float)(Math.tan(target*Math.PI/(2*maxInput))/1.8);
        float targetX = target/maxInput;
        float tmpDX = (targetX - pos.get_x())*80;

        if(tmpDX > MAX_DX)tmpDX = MAX_DX;
        else if(tmpDX < -MAX_DX)tmpDX = -MAX_DX;
        else tmpDX = 0;//Preventing Player to wiggle around on his position with some too lil DX

        setDX(tmpDX);
    }
}
