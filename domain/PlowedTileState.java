import javax.swing.*;

public class PlowedTileState extends TileState {

    public PlowedTileState(Tile tile) {
        super(tile);
    }

    @Override
    public void plant(Crop crop) {
        this.tile.setTileState(new PlantedTileState(this.tile, crop));
    }

    @Override
    public ImageIcon getCropIcon() {
        return new ImageIcon("assets/plowed.jpg");
    }

    @Override
    public boolean isPlantable(boolean isTree, int row, int col, Tile[][] tiles) {

        if (isTree) {
            // Ensure the tree is not on the farm's border or has adjacent non-enpty tiles
            if (MyFarm.isTileOnEdge(row, col) || !MyFarm.isAdjacentTilesEmpty(row, col, tiles)) {
                return false;
            }
        }

        return true;
    }
}
