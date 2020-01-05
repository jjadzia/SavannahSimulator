package agh.cs.lab2;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    private TreeSet<Vector2d> xOrder = new TreeSet<>(Comparator.comparing(Vector2d::getX).thenComparing(Vector2d::getY));
    private TreeSet<Vector2d> yOrder = new TreeSet<>(Comparator.comparing(Vector2d::getY).thenComparing(Vector2d::getX));

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal){
        if(!oldPosition.equals(newPosition)) {
            xOrder.remove(oldPosition);
            xOrder.add(newPosition);

            yOrder.remove(oldPosition);
            yOrder.add(newPosition);
        }
    }

    public void add(AbstractObjectOnMap obj){
        this.xOrder.add(obj.getPosition());
        this.yOrder.add(obj.getPosition());
    }

    public void addAnimal(Animal obj){
        this.add(obj);
        obj.addObserver(this);
    }

    public Vector2d getLowerLeft(){
        return new Vector2d(xOrder.first().getX(), yOrder.first().getY());
    }

    public Vector2d getUpperRight(){
        return new Vector2d(xOrder.last().getX(), yOrder.last().getY());
    }
}
