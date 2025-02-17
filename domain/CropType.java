/**
    The CropType class is an enumeration of the crop types.
 */
public enum CropType {
    ROOT_CROP(1),
    FLOWER(1.1),
    FRUIT_TREE(1);

    private final double harvestMultiplier;

    CropType(double harvestMultiplier) {
        this.harvestMultiplier = harvestMultiplier;
    }

    public double getHarvestMultiplier() {
        return harvestMultiplier;
    }
}