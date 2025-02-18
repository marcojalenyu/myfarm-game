import javax.swing.*;

public class NotPlowedTileState extends TileState {

    public NotPlowedTileState(Tile tile) {
        super(tile);
    }

    @Override
    public boolean plow() {
        this.tile.setTileState(new PlowedTileState(tile));
        return true;
    }

    @Override
    public ImageIcon getCropIcon() {
        return new ImageIcon("assets/unplowed.jpg");
    }
}
