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
        this.width = Constants.FARM_WIDTH;
        this.length = Constants.FARM_LENGTH;
        this.tiles = new Tile[width][length];
        this.day = Constants.START_DAY;

        for (int row = 0; row < width; row++)
            for (int col = 0; col < length; col++)
                this.tiles[row][col] = new Tile(row, col);

        generateRocks();
    }

    /**
     This function advances the day of the game, calls update for all tiles.
     */
    public void advanceDay() {
        this.day++;

        for (int row = 0; row < this.width; row++) {
            for (int col = 0; col < this.length; col++) {

                tiles[row][col].update();
            }
        }
    }

    /**
     This function generates 10-30 rocks scattered around the plot based on file input.
     */
    public void generateRocks() {
        try {
            File myObj = new File("assets/filename.txt");
            Scanner reader = new Scanner(myObj);
            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] substrings = data.split(",");
                int i = Integer.parseInt(substrings[0]);
                int j = Integer.parseInt(substrings[1]);

                tiles[i][j].placeRock();
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
                if(this.tiles[row][col].isInactive())
                    inactiveCount++;

        return !this.farmer.canAffordCheapestSeed() &&
                inactiveCount == this.length * this.width;
    }

    public static boolean isTileOnEdge(int row, int col) {
        return row == 0 || row == Constants.FARM_WIDTH - 1 || col == 0 || col == Constants.FARM_LENGTH - 1;
    }

    public static boolean isAdjacentTilesEmpty(int row, int col, Tile[][] tiles) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (tiles[i][j].getTileState() instanceof RockTileState || tiles[i][j].getTileState() instanceof PlantedTileState) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     Getters and Setters:
     */
    public Farmer getFarmer() { return farmer;}
    public int getDay() { return day;}
    public Tile getTile(int i, int j) { return tiles[i][j];}
    public Tile[][] getTiles() { return tiles;}

    public double getFarmerWallet() {return farmer.getWallet();}
    public int getFarmerLevel() {return farmer.getLevel();}
    public double getFarmerExperience() {return farmer.getExperience();}
    public FarmerType getFarmerType() {return farmer.getType();}
}