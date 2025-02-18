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
    private final int row;
    private final int col;
    private TileState tileState;

    /**
     Constructor of Tile initializes a tile on the plot.
     */
    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        this.tileState = new NotPlowedTileState(this);
    }

    /**
     * Updates the tile and the crop inside the tile.
     */
    public void update() {
        this.tileState.update();
    }

    public boolean plow() {
        return this.tileState.plow();
    }

    public boolean dig() {
        return this.tileState.dig();
    }

    public boolean mine() {
        return this.tileState.mine();
    }

    public void plant(Crop crop) {
        this.tileState.plant(crop);
    }

    public boolean water() {
        return this.tileState.water();
    }

    public boolean fertilize() {
        return this.tileState.fertilize();
    }

    public Crop harvest() throws InvalidTileException {
        try {
            return this.tileState.harvest();
        } catch (InvalidTileException e) {
            throw new InvalidTileException(e.getMessage());
        }
    }

    public void placeRock() {
        this.tileState = new RockTileState(this);
    }

    public boolean isInactive() {
        return this.tileState.isInactive();
    }

    public ImageIcon getCropIcon() {
        return this.tileState.getCropIcon();
    }

    /**
     This function determines if the farmer can plant a tree on the given tile
     @param tiles - set of tiles in the plot to be used to check the 8 surrounding tiles of the given
     @param isTree - boolean value to determine if the crop is a tree
     */
    public boolean isPlantable(Tile[][] tiles, boolean isTree) {
        return this.tileState.isPlantable(isTree, row, col, tiles);
    }

    /**
     Getters and Setters:
     */
    public TileState getTileState() {
        return tileState;
    }
    public void setTileState(TileState tileState) {
        this.tileState = tileState;
    }
}
