package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RectangularMap extends AbstractWorldMap{

    public int width;
    public int height;
    public Parameters parameters = new Parameters();
    public int jungleWidth;
    public int jungleHeight;


    RectangularMap(){
        this.width = parameters.width;
        this.height = parameters.height;
        this.jungleWidth = (int) (this.width*Math.sqrt(parameters.jungleRatio));
        this.jungleHeight = (int) (this.height*Math.sqrt(parameters.jungleRatio));
    }

    public Vector2d whereShouldMove(Vector2d position, MapDirections direction){
        Vector2d newPosition = position.add(direction.toUnitVector());
        int newX=newPosition.getX();
        int newY=newPosition.getY();
        if(newX > width) return new Vector2d(newX%width, newY);
        if(newX < 0) return new Vector2d(newX+width, newY);
        if(newY > height) return new Vector2d(newX, newY%height);
        if(newY < 0) return new Vector2d(newX, newY+height);
        else return newPosition;
    }

    public Vector2d findFreePosition(){
        Vector2d randomPosition;
        int randomX;
        int randomY;
        do{
            randomX = new Random().nextInt(width + 1);
            randomY = new Random().nextInt(height + 1);

            randomPosition = new Vector2d( randomX, randomY);
        }while(this.isOccupied(randomPosition));
        return randomPosition;
    }

    public void growGrass(){
        this.growGrassInJungle();
        this.growGrassOnStep();
    }

    private void growGrassInJungle(){
        Vector2d jungleGrowPosition;
        Vector2d jungleFurthestEdge = new Vector2d((width+1)/2+jungleWidth/2, (height+1)/2+jungleHeight/2);
        Vector2d jungleClosestEdge = new Vector2d((width+1)/2-jungleWidth/2, (height+1)/2-jungleHeight/2);
        List<Vector2d> jungleGrass = new ArrayList<>();
        for ( Vector2d position: grass){
            if(position.follows(jungleClosestEdge) && position.precedes(jungleFurthestEdge)) jungleGrass.add(position);
        }
        if(jungleGrass.size()<(jungleHeight*jungleWidth)){
            do{
                int randomXInJungle = new Random().nextInt(jungleWidth) + jungleClosestEdge.getX();
                int randomYInJungle = new Random().nextInt(jungleHeight) + jungleClosestEdge.getY();

                jungleGrowPosition = new Vector2d(randomXInJungle, randomYInJungle);
            } while (jungleGrass.contains(jungleGrowPosition));
            grass.add(jungleGrowPosition);
        }
    }

    private void growGrassOnStep(){
        Vector2d stepGrowPosition;
        Vector2d jungleFurthestEdge = new Vector2d((width+1)/2+jungleWidth/2, (height+1)/2+jungleHeight/2);
        Vector2d jungleClosestEdge = new Vector2d((width+1)/2-jungleWidth/2, (height+1)/2-jungleHeight/2);
        int jungleStartX = jungleClosestEdge.getX();
        int jungleStartY = jungleClosestEdge.getY();
        int jungleEndX = jungleFurthestEdge.getX();
        int jungleEndY = jungleFurthestEdge.getY();
        int grassInJungle = 0;
        for ( Vector2d position: grass){
            if(position.follows(jungleClosestEdge) && position.precedes(jungleFurthestEdge)) grassInJungle++;
        }
        if(grass.size() - grassInJungle < (height+1) * (width+1) - jungleHeight*jungleWidth){
            do{
                int randomXOnStep = new Random().nextInt(width + 1);
                int randomYOnStep;
                if(randomXOnStep >= jungleStartX && randomXOnStep < jungleEndX) {
                    randomYOnStep = new Random().nextInt(height - jungleHeight+1);
                    if(randomYOnStep >=jungleStartY) randomYOnStep+= jungleHeight;
                }
                else randomYOnStep = new Random().nextInt(height+1);

                stepGrowPosition = new Vector2d(randomXOnStep, randomYOnStep);
            } while (grass.contains(stepGrowPosition));

            grass.add(stepGrowPosition);
        }
    }

    public void placeInitialAnimals(){
        for(int i=0; i<this.parameters.initialAnimals; i++){
            this.place(new Animal(this));
        }
    }

    public void placeInitialGrass(){
        for(int i=0; i<this.parameters.initialGrass; i++){
            this.grass.add(findFreePosition());
        }
    }

}
