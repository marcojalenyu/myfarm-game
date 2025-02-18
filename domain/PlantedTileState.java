import exceptions.InvalidTileException;

import javax.swing.*;

public class PlantedTileState extends TileState {

    private final Crop crop;

    public PlantedTileState(Tile tile, Crop crop) {
        super(tile);
        this.crop = crop;
    }

    @Override
    public void update() {
        this.crop.update();
    }

    @Override
    public Crop harvest() throws InvalidTileException {
        if (this.crop.isHarvestable()) {
            this.tile.setTileState(new NotPlowedTileState(this.tile));
            return this.crop;
        }
        throw new InvalidTileException("This tile cannot be harvested.");
    }

    @Override
    public boolean water() {
        this.crop.water();
        return true;
    }

    @Override
    public boolean fertilize() {
        this.crop.fertilize();
        return true;
    }

    @Override
    public ImageIcon getCropIcon() {
        return new ImageIcon(this.crop.getIcon());
    }

    @Override
    public boolean isInactive() {
        return this.crop.isWithered();
    }

    @Override
    public Crop getCrop() {
        return this.crop;
    }
}
