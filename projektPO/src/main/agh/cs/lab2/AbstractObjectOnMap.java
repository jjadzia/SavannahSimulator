package agh.cs.lab2;

import java.awt.*;

public abstract class AbstractObjectOnMap {
    abstract public Vector2d getPosition();
    public int getX(){
        return getPosition().getX();
    }
    public int getY(){
        return getPosition().getY();
    }
    abstract public Color color();

}
