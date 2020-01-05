package agh.cs.lab2;

import java.awt.*;

public class Rock extends AbstractObjectOnMap {
    private Vector2d position;

    Rock(Vector2d position){
        this.position=position;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public String toString(){
        return "s";
    }

    public Color color(){
        return new Color(83, 84, 84);
    }
}
