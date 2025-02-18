import javax.swing.*;

public class RockTileState extends TileState {

    public RockTileState(Tile tile) {
        super(tile);
    }

    @Override
    public boolean dig() {
        return false;
    }

    @Override
    public boolean mine() {
        this.tile.setTileState(new NotPlowedTileState(this.tile));
        return true;
    }

    @Override
    public ImageIcon getCropIcon() {
        return new ImageIcon("assets/rocked.jpg");
    }
}
