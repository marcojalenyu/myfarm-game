import exceptions.InvalidTileException;

import javax.swing.*;

public abstract class TileState {

    protected Tile tile;

    public TileState(Tile tile) {
        this.tile = tile;
    }

    public void update() {}

    public boolean plow() {
        return false;
    }

    public boolean dig() {
        this.tile.setTileState(new NotPlowedTileState(this.tile));
        return true;
    }

    public boolean mine() {
        return false;
    }

    public void plant(Crop crop) {
    }

    public Crop harvest() throws InvalidTileException {
        throw new InvalidTileException("This tile cannot be harvested.");
    }

    public boolean water() {
        return false;
    }

    public boolean fertilize() {
        return false;
    }

    public abstract ImageIcon getCropIcon();

    public boolean isPlantable(boolean isTree, int row, int col, Tile[][] tiles) {
        return false;
    }

    public boolean isInactive() {
        return true;
    }

    public Crop getCrop() {
        return null;
    }
}
