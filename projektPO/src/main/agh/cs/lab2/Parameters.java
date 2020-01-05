package agh.cs.lab2;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Parameters {
    public int width;
    public int height;
    public int startEnergy;
    public int moveEnergy;
    public int plantEnergy;
    public double jungleRatio;
    public int windowWidth;
    public int windowHeight;
    public int initialAnimals;
    public int initialGrass;



    Parameters() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(
                    "/Users/jadziauljasz/Downloads/projektPO/src/main/agh/cs/lab2/parameters.json"));

            JSONObject jsonObject = (JSONObject) obj;

            this.width = Integer.valueOf(jsonObject.get("width").toString());
            this.height = Integer.valueOf(jsonObject.get("height").toString());
            this.startEnergy = Integer.valueOf(jsonObject.get("startEnergy").toString());
            this.moveEnergy = Integer.valueOf(jsonObject.get("moveEnergy").toString());
            this.plantEnergy = Integer.valueOf(jsonObject.get("plantEnergy").toString());
            this.jungleRatio = Double.valueOf(jsonObject.get("jungleRatio").toString());
            this.windowWidth = Integer.valueOf(jsonObject.get("windowWidth").toString());
            this.windowHeight = Integer.valueOf(jsonObject.get("windowHeight").toString());
            this.initialAnimals = Integer.valueOf(jsonObject.get("initialAnimals").toString());
            this.initialGrass = Integer.valueOf(jsonObject.get("initialGrass").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
