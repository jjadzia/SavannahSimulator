package agh.cs.lab2;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    public int[] gens;
    private Random random = new Random();

    public Genotype(){
        gens = new int[32];
        for(int i=0; i<32; i++){
            gens[i]= random.nextInt(8);
        }
        Arrays.sort(gens);
        this.ensureAllGensAppear();
    }

    public Genotype(Genotype mother, Genotype father){
        gens = new int[32];
        gens = Arrays.copyOf(mother.gens, mother.gens.length);

        int firstRandomCut = random.nextInt(30)+1;
        int secondRandomCut = random.nextInt(31-firstRandomCut)+firstRandomCut+1;
        int[] possibleCuts = {0, firstRandomCut, secondRandomCut, 31};

        int partFromFather = random.nextInt(3);

        for(int i = possibleCuts[partFromFather]; i<possibleCuts[partFromFather+1]; i++){
            gens[i] = father.gens[i];
        }

        Arrays.sort(gens);
        this.ensureAllGensAppear();
    }

    private void ensureAllGensAppear(){
        int testedGen=0;
        boolean correctGenotype = false;
        while(!correctGenotype){
            if(doesGenAppear(testedGen)) testedGen++;
            else {
                int typeOfChangedGen;
                int missingGen = testedGen;
                do {
                    int genToChange = this.pickRandomGen();
                    typeOfChangedGen = gens[genToChange];
                    gens[genToChange] = missingGen;
                    missingGen = typeOfChangedGen;
                    Arrays.sort(gens);
                } while(!doesGenAppear(missingGen));
            }
            if(testedGen==8) correctGenotype=true;
        }
    }

    private int pickRandomGen(){
        return random.nextInt(32);
    }

    private boolean doesGenAppear(int testedGen){
        return Arrays.binarySearch(gens,testedGen)>=0;
    }

    public int pickRotation(){
        return gens[this.pickRandomGen()];
    }

}
