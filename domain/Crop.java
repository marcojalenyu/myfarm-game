/**
    The Crop class contains records related to the crop planted on a tile.
 */
public class Crop {
    /**
     Attributes of Crop:
     1. seed: holds the seed name
     2. type: holds the crop type (root crop, flower, or fruit tree)
     3. harvestTime: holds the days before it is ready for harvest
     4. timesWatered: holds the number of times it has been watered within the limit
     5. waterNeeded: holds the minimum times of watering needed before harvest
     6. waterBonusLimit: holds the maximum amount of watering needed before harvest
     7. timesFertilized: holds the number of times it has been fertilized within the limit
     8. fertilizerNeeded: holds the minimum times of fertilizing needed before harvest
     9. fertilizerBonusLimit: holds the maximum amount of fertilizing needed before harvest
     10. productYield: holds the products produced when harvested
     11. experienceYield: holds the experience produced when harvested
     12. basePrice: holds base selling price per piece
     13. isWithered: holds value determining if it is withered
     */
    private String seed;
    private CropType type;
    private int harvestTime;
    private int timesWatered;
    private int waterNeeded;
    private int waterBonusLimit;
    private int timesFertilized;
    private int fertilizerNeeded;
    private int fertilizerBonusLimit;
    private int productYield;
    private double experienceYield;
    private double basePrice;
    private boolean isWithered;

    /**
     Constructor of Crop initializes the seed planted on a tile.
     */
    public Crop(String seed, CropType type, int harvestTime, int waterNeeded, int waterBonusLimit, int fertilizerNeeded, int fertilizerBonusLimit, int productYield, double experienceYield, double basePrice) {
        this.seed = seed;
        this.type = type;
        this.harvestTime = harvestTime;
        this.timesWatered = 0;
        this.waterNeeded = waterNeeded;
        this.waterBonusLimit = waterBonusLimit;
        this.timesFertilized = 0;
        this.fertilizerNeeded = fertilizerNeeded;
        this.fertilizerBonusLimit = fertilizerBonusLimit;
        this.productYield = productYield;
        this.experienceYield = experienceYield;
        this.basePrice = basePrice;
        this.isWithered = false;
    }

    /**
     Getters and Setters:
     */
    public String getSeed() {
        return seed;
    }
    public CropType getType() {
        return type;
    }
    public int getHarvestTime() {
        return harvestTime;
    }
    public int getTimesWatered() {
        return timesWatered;
    }
    public int getWaterNeeded() {
        return waterNeeded;
    }
    public int getWaterBonusLimit() {
        return waterBonusLimit;
    }
    public int getTimesFertilized() {
        return timesFertilized;
    }
    public int getFertilizerNeeded() {
        return fertilizerNeeded;
    }
    public int getFertilizerBonusLimit() {
        return fertilizerBonusLimit;
    }
    public int getProductYield() {
        return productYield;
    }
    public double getExperienceYield() {
        return experienceYield;
    }
    public double getBasePrice() {
        return basePrice;
    }
    public void setHarvestTime(int harvestTime) {
        this.harvestTime = harvestTime;
    }
    public void setTimesWatered(int timesWatered) {
        this.timesWatered = timesWatered;
    }
    public void setTimesFertilized(int timesFertilized) {
        this.timesFertilized = timesFertilized;
    }
}

