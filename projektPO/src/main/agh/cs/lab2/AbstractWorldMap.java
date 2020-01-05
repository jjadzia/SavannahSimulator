package agh.cs.lab2;

import java.util.*;
import com.google.common.collect.TreeMultimap;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected TreeMultimap<Vector2d, Animal> animals = TreeMultimap.create(Comparator.comparing(Vector2d::getX).thenComparing(Vector2d::getY), Comparator.comparing(Animal::getIndex));
    protected List<Vector2d> grass = new ArrayList<>();


    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal movingAnimal){
        if(!oldPosition.equals(newPosition)){
            movingAnimal.updateIndex(animals.get(newPosition).size());
            animals.put(newPosition, movingAnimal);
            animals.remove(oldPosition, movingAnimal);

            var i=0;
            for(Object j: animals.get(oldPosition)){
                Animal animal = (Animal)j;
                animal.updateIndex(i);
                i++;
            }
        }
    }

    public void feedAnimals(){
        for(Vector2d animalPosition: animals.keySet()){
            if(grass.contains(animalPosition)) findStrongestAnimal(animalPosition).eatGrass();
        }
    }


    public int breedAnimals(int day){
        int babiesConceived = 0;
        for(Object obj: animals.keySet().toArray()){
            Vector2d animalPosition = (Vector2d) obj;

            if(makeBabyIfPossible(animalPosition, day)) babiesConceived++;
        }
        return babiesConceived;
    }

    public boolean place(Animal animal){
        animals.put(animal.getPosition(), animal);
        animal.addObserver(this);
        return true;
    }

    public void run(){
        for(Object animal: animals.values().toArray()){
            ((Animal)animal).move();
        }
    }

    public boolean isOccupied(Vector2d position){
        boolean isGrassOn = this.grass.contains(position);
        boolean isAnimalOn = objectAt(position) != null;
        return isGrassOn || isAnimalOn;
    }

    public Object objectAt(Vector2d position){
        boolean isAnimalOn = !this.animals.get(position).isEmpty();
        return isAnimalOn ? this.animals.get(position).first() : null;
    }

    private Animal findStrongestAnimal(Vector2d position){
        var currentlyStrongest = (Animal)this.objectAt(position);
        for(Animal animal : this.animals.get(position)){
            if(animal.getEnergy() > currentlyStrongest.getEnergy()) currentlyStrongest=animal;
        }
        return currentlyStrongest;
    }

    private Animal findSecondStrongestAnimal(Vector2d position){
        if (this.animals.get(position).size()<=1) return null;
        Animal strongest = this.findStrongestAnimal(position);

        var currentlyStrongest = (Animal)this.objectAt(position);
        var strongestEnergy = 0;
        for(Animal animal : this.animals.get(position)){
            if(!animal.equals(strongest) && animal.getEnergy() > strongestEnergy) {
                currentlyStrongest = animal;
                strongestEnergy = animal.getEnergy();
            }
        }
        return currentlyStrongest;
    }

    private boolean makeBabyIfPossible(Vector2d position, int day){
        boolean haveCouple = animals.get(position).size()>=2;
        if(haveCouple) {
            Animal mother = this.findStrongestAnimal(position);
            Animal father = this.findSecondStrongestAnimal(position);

            if(mother.isFertile() && father.isFertile()) {
                Animal baby = new Animal(mother, father, day);
                this.place(baby);
                return true;
            }
        }
        return false;
    }

    public void buryDeadAnimals(int day, World world){
        world.stats.currentDeadAnimals = 0;
        for(Object obj: animals.values().toArray()){
            Animal animal = (Animal)obj;
            if(animal.getEnergy()<=0) {
                animals.remove(animal.getPosition(), animal);
                animal.die(day);
                world.stats.currentDeadAnimals++;
                world.stats.currentSumOfLifeLength = animal.deathDay-animal.birthDay;
            }
        }
    }

}
