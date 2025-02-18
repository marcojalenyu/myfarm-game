import static java.lang.Math.min;

/**
    The Crop class contains records related to the crop planted on a tile.
 */
public class Crop implements Cloneable {
    /**
     Attributes of Crop:
     1. seed: holds the seed name
     2. type: holds the crop type (root crop, flower, or fruit tree)
     3. harvestTime: holds the days before it is ready for harvest
     4. timesWatered: holds the number of times it has been watered within the limit
     5. waterNeeded: holds the minimum times of watering needed before harvest
     6. timesFertilized: holds the number of times it has been fertilized within the limit
     7. fertilizerNeeded: holds the minimum times of fertilizing needed before harvest
     8. minYield: holds the minimum number of products produced when harvested
     9. maxYield: holds the maximum number of products produced when harvested
     10. finalYield: holds the final number of products produced when harvested
     11. experienceYield: holds the experience produced when harvested
     12. basePrice: holds base selling price per piece
     13. seedCost: holds the cost of the seed
     14. cropState: holds the current state of the crop
     */
    private final String seed;
    private final CropType type;
    private int harvestTime;
    private int timesWatered;
    private final int waterNeeded;
    private int timesFertilized;
    private final int fertilizerNeeded;
    private final int minYield;
    private final int maxYield;
    private int finalYield;
    private final double experienceYield;
    private final double basePrice;
    private final int seedCost;
    private CropState cropState;

    /**
     Constructor of Crop initializes the seed planted on a tile.
     */
    public Crop(String seed, CropType type, int harvestTime, int waterNeeded, int fertilizerNeeded, int minYield, int maxYield, double experienceYield, double basePrice, int seedCost) {
        this.seed = seed;
        this.type = type;
        this.harvestTime = harvestTime;
        this.timesWatered = Constants.START_TIMES_WATERED;
        this.waterNeeded = waterNeeded;
        this.timesFertilized = Constants.START_TIMES_FERTILIZED;
        this.fertilizerNeeded = fertilizerNeeded;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.finalYield = 0;
        this.experienceYield = experienceYield;
        this.basePrice = basePrice;
        this.seedCost = seedCost;
        this.cropState = null;
    }

    /**
     * Clones the crop
     * @return a new crop with the same attributes
     */
    @Override
    public Crop clone() {
        try {
            return (Crop) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Updates the crop per day by reducing harvest time and checking if harvestable or withered
     */
    public void update() {
        cropState.update();
    }

    public int computeFinalYield() {
        if(finalYield == 0)
            finalYield = (int) (Math.random() * (maxYield - minYield + 1) + minYield);
        return finalYield;
    }

    public double computeHarvestPrice(int earnBonus, int waterLimitIncrease, int fertilizerLimitIncrease) {
        // To limit the water and fertilizer bonus gained
        timesWatered = min(timesWatered, waterNeeded+waterLimitIncrease);
        timesFertilized = min(timesFertilized, fertilizerNeeded+fertilizerLimitIncrease);
        double harvestTotal = finalYield * (basePrice + earnBonus);
        double waterBonus = harvestTotal * 0.2 * (timesWatered - 1);
        double fertilizerBonus = harvestTotal * 0.5 * timesFertilized;
        double finalHarvestPrice = harvestTotal + waterBonus + fertilizerBonus;
        return finalHarvestPrice * type.getHarvestMultiplier();
    }

    public String getIcon() {
        String typeString;
        if (type.equals(CropType.FRUIT_TREE)) {
            typeString = "Tree";
        } else {
            typeString = "Plant";
        }

        return this.cropState.getIcon(seed, typeString);
    }

    public void water() {
        timesWatered++;
    }

    public void fertilize() {
        timesFertilized++;
    }

    public boolean isWateredEnough() {
        return timesWatered >= waterNeeded;
    }

    public boolean isFertilizedEnough() {
        return timesFertilized >= fertilizerNeeded;
    }

    public boolean isWithered() {
        return this.cropState.isWithered();
    }

    public boolean isHarvestable() {
        return this.cropState.isHarvestable();
    }

    /**
     Getters and Setters:
     */
    public String getSeed() {
        return seed;
    }
    public double getExperienceYield() {
        return experienceYield;
    }
    public int getSeedCost() {
        return seedCost;
    }
    public boolean isTree() {
        return type.equals(CropType.FRUIT_TREE);
    }
    public void setCropState(CropState cropState) {
        this.cropState = cropState;
    }

    public void startGrowingState() {
        this.cropState = new GrowingCropState(this);
    }
    public Boolean isTimeToHarvest() {
        return this.harvestTime == 0;
    }
    public void progressHarvestTime() {
        harvestTime--;
    }
}
