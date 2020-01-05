package agh.cs.lab2;


import java.util.Random;

public enum MapDirections {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString(){
        switch(this) {
            case NORTH:
                return "Północ";
            case SOUTH:
                return "Południe";
            case WEST:
                return "Zachód";
            case EAST:
                return "Wschód";
            case NORTHEAST:
                return "Pn-wsch";
            case SOUTHEAST:
                return "Pd-wsch";
            case SOUTHWEST:
                return "Pn-zach";
            case NORTHWEST:
                return "Pd-zach";
            default: return "";
        }
    }

    public String toChar(){
        switch(this) {
            case NORTH:
                return "^";
            case SOUTH:
                return "v";
            case WEST:
                return "<";
            case EAST:
                return ">";
            case NORTHEAST:
                return "/^";
            case SOUTHEAST:
                return "\\v";
            case SOUTHWEST:
                return "v/";
            case NORTHWEST:
                return "^\\";
            default: return "";
        }
    }


    public MapDirections next(){
        switch(this) {
            case NORTH:
                return NORTHEAST;
            case SOUTH:
                return SOUTHWEST;
            case WEST:
                return NORTHWEST;
            case EAST:
                return SOUTHEAST;
            case NORTHEAST:
                return EAST;
            case SOUTHEAST:
                return SOUTH;
            case SOUTHWEST:
                return WEST;
            case NORTHWEST:
                return NORTH;
            default: return NORTH;
        }
    }

    public MapDirections previous(){
        switch(this) {
            case NORTH:
                return NORTHWEST;
            case SOUTH:
                return SOUTHEAST;
            case WEST:
                return SOUTHWEST;
            case EAST:
                return NORTHEAST;
            case NORTHEAST:
                return NORTH;
            case SOUTHEAST:
                return EAST;
            case SOUTHWEST:
                return SOUTH;
            case NORTHWEST:
                return WEST;
            default: return NORTH;
        }
    }

    public Vector2d toUnitVector(){
        switch(this) {
            case NORTH:
                return new Vector2d(0,1);
            case SOUTH:
                return new Vector2d(0,-1);
            case WEST:
                return new Vector2d(-1,0);
            case EAST:
                return new Vector2d(1,0);
            case NORTHEAST:
                return new Vector2d(1,1);
            case SOUTHEAST:
                return new Vector2d(1,-1);
            case SOUTHWEST:
                return new Vector2d(-1,-1);
            case NORTHWEST:
                return new Vector2d(-1,1);
            default: return new Vector2d(0,0);
        }
    }

    static public MapDirections randomDirection(){
        int number = new Random().nextInt(8);
        switch(number) {
            case 0:
                return NORTHEAST;
            case 1:
                return SOUTHWEST;
            case 2:
                return NORTHWEST;
            case 3:
                return SOUTHEAST;
            case 4:
                return EAST;
            case 5:
                return SOUTH;
            case 6:
                return WEST;
            case 7:
                return NORTH;
            default: return NORTH;
        }
    }
}