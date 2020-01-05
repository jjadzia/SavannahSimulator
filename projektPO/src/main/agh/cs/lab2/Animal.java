package agh.cs.lab2;

import jdk.dynalink.beans.StaticClass;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractObjectOnMap implements Comparable {
    private MapDirections orientation;
    private Vector2d position = new Vector2d(2,2);
    private RectangularMap ownMap;
    private List<IPositionChangeObserver> observers;
    private int energy;
    private Genotype genotype;
    private Parameters parameters = new Parameters();
    private int index;
    public int birthDay;
    public int deathDay;

    private Animal(){
        this.orientation = MapDirections.randomDirection();
        this.energy = parameters.startEnergy;
        this.observers = new ArrayList<>();
    }
    Animal(RectangularMap map){
       this();
       this.ownMap=map;
       this.genotype=new Genotype();
       this.index = ownMap.animals.get(this.position).size()+1;
       this.position = ownMap.findFreePosition();
       this.birthDay = 0;
    }

    Animal(RectangularMap map, Vector2d position) {
        this();
        this.ownMap = map;
        this.position = position;
        this.genotype=new Genotype();
        this.index = ownMap.animals.get(this.position).size()+1;
    }

    Animal(Animal mother, Animal father, int day){
        this();
        this.ownMap=mother.ownMap;
        this.genotype=new Genotype(mother.genotype, father.genotype);
        this.birthDay = day;
        // picking spawn position
        MapDirections randomDirection = MapDirections.randomDirection();
        Vector2d birthPlace = mother.position;
        Vector2d initialSpawnPosition = this.ownMap.whereShouldMove(birthPlace, randomDirection);
        Vector2d spawnPosition = initialSpawnPosition;
        do{
            this.position = spawnPosition;
            randomDirection = randomDirection.next();
            spawnPosition =  this.ownMap.whereShouldMove(birthPlace, randomDirection);
        }while( !spawnPosition.equals(initialSpawnPosition) && ownMap.animals.keySet().contains(this.position) );

        // sharing energy
        this.energy = mother.getEnergyForBaby() + father.getEnergyForBaby();
    }

    private int getEnergyForBaby(){
        int energyForBaby = (int)(this.energy*0.25);
        this.energy -= energyForBaby;
        return energyForBaby;
    }

    void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    private void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition){
        for (IPositionChangeObserver obs: this.observers) {
            obs.positionChanged(oldPosition, this.position, this);
        }
    }
    public void die(int day){
        removeObserver(this.ownMap);
        deathDay = day;
    }

    public int getIndex(){
        return this.index;
    }

    public void updateIndex(int number){
        this.index = number;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public String getGenotype(){
        String genotype ="";
        for(int i: this.genotype.gens ) genotype+=Integer.toString(i);
        return genotype;
    }

    public int getEnergy(){
        return this.energy;
    }

    public MapDirections getOrientation(){
        return this.orientation;
    }

    public String toString() {
        return this.orientation.toChar();
    }

    public void move(){
        this.energy=this.energy-this.parameters.moveEnergy;
        this.rotate();
        Vector2d oldPosition = this.position;
        this.position=this.ownMap.whereShouldMove(this.position, this.orientation);
        this.positionChanged(oldPosition);
    }

    private void rotate(){
        int numberOfRotation = this.genotype.pickRotation();
        for(int i=0; i<=numberOfRotation; i++) this.orientation=this.orientation.next();
    }

    public void printGenotype(){
        for(int i: genotype.gens) System.out.print(i);
        System.out.println();
    }

    public Color color(){
        if(energy>50) return new Color(21, 0, 255);
        if(energy>40) return new Color(0, 47, 255);
        if(energy>30) return new Color(0, 110, 255);
        if(energy>20) return new Color(0, 150, 255);
        if(energy>10) return new Color(0, 200, 255);
        if(energy<=0) return new Color(0, 0, 0);

        else return new Color(194, 233, 237);
    }

    public void eatGrass(){
        if(this.energy>=0) {
            this.energy += this.parameters.plantEnergy;
            this.ownMap.grass.remove(position);
        }
    }

    public int compareTo(Object o) {
        Integer energy1 = this.energy;
        Integer energy2 = ((Animal) o).energy;

        return energy1.compareTo(energy2);
    }
    public boolean isFertile() {
        return this.energy>0.5*this.parameters.startEnergy;
    }
}
