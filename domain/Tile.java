import javax.swing.*;
import exceptions.*;

/**
    The Tile class contains records related to each tile in the plot.
 */
public class Tile {
    /**
     Attributes of Tile:
     1. tileState: the current state of the tile
     2. crop: holds the crop planted by the farmer (null if no crop)
     */
    private TileStates tileState;
    private Crop crop;

    /**
     Constructor of Tile initializes a tile on the plot.
     */
    public Tile(TileStates tileState) {
        this.tileState = tileState;
        this.crop = null;
    }

    /**
     * Updates the tile and the crop inside the tile.
     */
    public void update() {
        if (this.tileState == TileStates.PLANTED && this.crop != null) {
            this.crop.update();
        }
    }

    public boolean plow() {
        if (this.tileState == TileStates.NOT_PLOWED) {
            this.tileState = TileStates.PLOWED;
            return true;
        }
        return false;
    }

    public boolean dig() {
        if (this.tileState == TileStates.ROCK) {
            return false;
        }
        this.tileState = TileStates.NOT_PLOWED;
        this.crop = null;
        return true;
    }

    public boolean mine() {
        if (this.tileState == TileStates.ROCK) {
            this.tileState = TileStates.NOT_PLOWED;
            return true;
        }
        return false;
    }

    public void plant(Crop crop) {
        this.tileState = TileStates.PLANTED;
        this.crop = crop;
    }

    public Crop harvest() throws InvalidTileException {
        if (this.tileState == TileStates.PLANTED
                && this.crop != null && this.crop.isHarvestable()) {
            Crop harvestedCrop = this.crop;
            this.tileState = TileStates.NOT_PLOWED;
            this.crop = null;
            return harvestedCrop;
        }
        throw new InvalidTileException("This tile cannot be harvested.");
    }

    /**
     Getters and Setters:
     */
    public void placeRock() {
        this.tileState = TileStates.ROCK;
    }

    public void updateButton (JButton tileIcon) {
        switch(tileState) {
            case TileStates.ROCK:
                tileIcon.setIcon(new ImageIcon("assets/rocked.jpg"));
                break;

            case TileStates.NOT_PLOWED:
                tileIcon.setIcon(new ImageIcon("assets/unplowed.jpg"));
                break;

            case TileStates.PLOWED:
                tileIcon.setIcon(new ImageIcon("assets/plowed.jpg"));
                break;

            case TileStates.PLANTED:
                tileIcon.setIcon(new ImageIcon(this.crop.getIcon()));
                break;
        }
    }

    public Crop getCrop() {
        return crop;
    }

    public boolean isInactive() {
        return !tileState.equals(TileStates.PLANTED) || crop.isWithered();
    }

    /**
     This function determines if the farmer can plant a tree on the given tile
     @param tiles - set of tiles in the plot to be used to check the 8 surrounding tiles of the given
     @param isTree - boolean value to determine if the crop is a tree
     */
    public boolean isPlantable(Tile[][] tiles, boolean isTree) {
        if (tileState == TileStates.NOT_PLOWED || tileState == TileStates.PLANTED) {
            return false;
        }

        if (!isTree) {
            return true;
        }

        int row = -1;
        int col = -1;

        // Locate the tile position in the grid
        for (int i = 0; i < Constants.FARM_WIDTH; i++) {
            for (int j = 0; j < Constants.FARM_LENGTH; j++) {
                if (tiles[i][j].equals(this)) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1) break;
        }

        // Ensure the tree is not on the farm's border
        if (row == 0 || row == Constants.FARM_WIDTH - 1 || col == 0 || col == Constants.FARM_LENGTH - 1) {
            return false;
        }

        // Check all adjacent tiles for emptiness
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (tiles[i][j].crop != null) {
                    return false; // Adjacent tile is occupied
                }
            }
        }
    
        return true;
    }
}
