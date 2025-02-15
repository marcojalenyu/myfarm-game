import java.util.HashMap;
import java.util.Map;

public class Shop {
    private Map<String, Crop> cropSeeds;

    public Shop() {
        this.cropSeeds = new HashMap<>();
        initializeCrops();
    }

    private void initializeCrops() {
        cropSeeds.put("turnip", new Crop("Turnip", CropType.ROOT_CROP, 2, 1, 0, 1, 2, 5.0, 6.0, 5));
        cropSeeds.put("carrot", new Crop("Carrot", CropType.ROOT_CROP, 3, 1, 0, 1, 2, 7.5, 9.0, 10));
        cropSeeds.put("potato", new Crop("Potato", CropType.ROOT_CROP, 5, 3, 1, 1, 10, 12.5, 3.0, 20));
        cropSeeds.put("rose", new Crop("Rose", CropType.FLOWER, 1, 1, 0, 1, 1, 2.5, 5.0, 5));
        cropSeeds.put("tulip", new Crop("Tulip", CropType.FLOWER, 2, 2, 0, 1, 1, 5.0, 9.0, 10));
        cropSeeds.put("sunflower", new Crop("Sunflower", CropType.FLOWER, 3, 2, 1, 1, 1, 7.5, 19.0, 20));
        cropSeeds.put("mango", new Crop("Mango", CropType.FRUIT_TREE, 10, 7, 4, 5, 15, 25.0, 8.0, 100));
        cropSeeds.put("apple", new Crop("Apple", CropType.FRUIT_TREE, 10, 7, 5, 10, 15, 25.0, 5.0, 200));
    }

    public Crop buy(String seedName, double wallet, int seedCostReduction) {
        Crop crop = cropSeeds.get(seedName);

        if (crop == null) {
            return null;
        }

        int finalCost = crop.getSeedCost() - seedCostReduction;

        if (wallet <= finalCost) {
            return null;
        }
    
        return crop;
    }

    public Map<String, Crop> getCropSeeds() {
        return cropSeeds;
    }
}
