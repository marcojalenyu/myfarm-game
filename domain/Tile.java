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
     Getters and Setters:
     */
    public TileStates getTileState() {
        return tileState;
    }
    public void setTileState(TileStates tileState) {
        this.tileState = tileState;
    }
    public Crop getCrop() {
        return crop;
    }
    public void setCrop(Crop crop) {
        this.crop = crop;
    }
}
