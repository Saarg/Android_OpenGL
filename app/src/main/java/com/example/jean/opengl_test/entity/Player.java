package com.example.jean.opengl_test.entity;

import com.example.jean.opengl_test.shapes.Triangle;

/**
 * Created by Kwarthys on 04/10/2016.
 */

public class Player extends Triangle implements Entity{

    private float dx = 0;

    public float getDX(){return dx;}

    public void setDX(float newDX){dx = newDX;}

    public void move(){x += dx;}

    public void bound(float limitInf, float limitSup)
    {
        if(x < limitInf) {
            x = limitInf;
        }
        if(x > limitSup) {
            x = limitSup;
        }
    }
}