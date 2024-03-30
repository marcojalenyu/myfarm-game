import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
    The MyFarm class contains all records relating to all the other classes.
 */
public class MyFarm {
    /**
     Attributes of MyFarm:
     1. farmer: holds record of player/farmer
     2. width: holds the width of the plot
     3. length: holds the length of the plot
     4. tiles: holds the lot
     5. day: holds the nth day
     */
    private final Farmer farmer;
    private final int width;
    private final int length;
    private final Tile[][] tiles;
    private int day;

    /**
     Constructor of MyFarm initializes the farmer, lot, and day counter.
     */
    public MyFarm() {
        this.farmer = new Farmer();
        this.width = 5;
        this.length = 10;
        this.tiles = new Tile[5][10];
        this.day = 1;

        for (int row = 0; row < width; row++)
            for (int col = 0; col < length; col++)
                this.tiles[row][col] = new Tile();

        generateRocks();
    }

    /**
     This function advances the day of the game, affecting every crop planted.
     */
    public void advanceDay() {
        this.day++;

        for (int row = 0; row < this.width; row++) {
            for (int col = 0; col < this.length; col++) {
                Crop crop = this.tiles[row][col].getCrop();
                if (crop != null){
                    crop.setHarvestTime(crop.getHarvestTime()-1);

                    if(crop.getHarvestTime() == 0) {
                        if (crop.getTimesWatered() < crop.getWaterNeeded() || crop.getTimesFertilized() < crop.getFertilizerNeeded())
                            crop.setWithered(true);
                    }
                    else if(crop.getHarvestTime() < 0)
                        crop.setWithered(true);
                }
            }
        }
    }

    /**
     This function generates 10-30 rocks scattered around the plot based on file input.
     */
    public void generateRocks() {
        try {
            File myObj = new File("filename.txt");
            Scanner reader = new Scanner(myObj);
            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] substrings = data.split(",");
                int i = Integer.parseInt(substrings[0]);
                int j = Integer.parseInt(substrings[1]);

                tiles[i][j].setRock(true);
            }
        } catch(IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    /**
     This function checks if the following conditions are met:
     1. Cannot afford new seeds
     2. No active/growing crops
     */
    public boolean isGameOver() {
        int inactiveCount = 0;

        for (int row = 0; row < this.width; row++)
            for (int col = 0; col < this.length; col++)
                if(this.tiles[row][col].getCrop() == null || this.tiles[row][col].getCrop().isWithered())
                    inactiveCount++;

        return this.farmer.getWallet() < 5 - this.farmer.getSeedCostReduction() && inactiveCount == this.length * this.width;
    }

    /**
     Getters and Setters:
     */
    public Farmer getFarmer() { return farmer;}
    public int getDay() { return day;}
    public Tile getTile(int i, int j) { return tiles[i][j];}
    public Tile[][] getTiles() { return tiles;}
}