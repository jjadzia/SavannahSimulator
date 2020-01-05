package agh.cs.lab2;

import javax.swing.*;

public class World {
    public RectangularMap map;
    public int windowWidth;
    public int windowHeight;
    protected JFrame frame = new JFrame("Step with jungle in center");
    protected WorldStatistics stats = new WorldStatistics();
    private int day;

    World(){
        map = new RectangularMap();
        windowWidth = map.parameters.windowWidth;
        windowHeight = map.parameters.windowHeight;
        frame.setSize(windowWidth, windowHeight);
        day = 0;
    }

    public void startBeautifulDay() {
        day++;
        map.buryDeadAnimals(day, this);
        this.map.growGrass();
        map.run();
        map.feedAnimals();

        stats.currentNumberOfBabies =  map.breedAnimals(day);
        this.updateStatistics();

        return;
    }

    public void startWorld(){
        this.map.placeInitialAnimals();
        this.map.placeInitialGrass();
        this.updateStatistics();

        //System.out.println(map.animals.size());
    }

    private void updateStatistics(){
        stats.currentNumberOfGrass = map.grass.size();
        stats.currentNumberOfAnimals = map.animals.size();
        stats.currentSumOfEnergy = 0;
        map.animals.forEach((vector2d, animal) -> stats.currentSumOfEnergy+=animal.getEnergy());
    }

}
