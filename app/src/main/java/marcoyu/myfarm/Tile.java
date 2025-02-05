package marcoyu.myfarm;

/**
    The Tile class contains records related to each tile in the plot.
 */
public class Tile {
    /**
     Attributes of Tile:
     1. isPlowed: determines if the tile is plowed
     2. hasRock: determines if the tile has a rock
     3. crop: holds the crop planted by the farmer (null if no crop)
     */
    private boolean isPlowed;
    private boolean hasRock;
    private Crop crop;

    /**
     Constructor of Tile initializes a tile on the plot.
     */
    public Tile() {
        this.isPlowed = false;
        this.hasRock = false;
        this.crop = null;
    }

    /**
     Getters and Setters:
     */
    public boolean isPlowed() {
        return isPlowed;
    }
    public boolean hasRock() {
        return hasRock;
    }
    public Crop getCrop() {
        return crop;
    }
    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }
    public void setRock(boolean hasRock) {
        this.hasRock = hasRock;
    }
    public void setCrop(Crop crop) {
        this.crop = crop;
    }
}
