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
    private CropStates cropState = CropStates.GROWING;

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
        this.harvestTime--;

        if (this.harvestTime == 0
                && this.isWateredEnough()
                && this.isFertilizedEnough()) {
            cropState = CropStates.HARVESTABLE;
        }
        else if(this.harvestTime <= 0) {
            cropState = CropStates.WITHERED;
        }
    }

    public int getFinalYield() {
        if(finalYield == 0)
            finalYield = (int) (Math.random() * (maxYield - minYield + 1) + minYield);
        return finalYield;
    }

    public double computeHarvestPrice(int earnBonus, int waterLimitIncrease, int fertilizerLimitIncrease) {

        double harvestTotal = finalYield * (basePrice + earnBonus);

        if (timesWatered > waterLimitIncrease) {
            timesWatered = waterLimitIncrease;
        }
        double waterBonus = harvestTotal * 0.2 * (timesWatered - 1);

        if (timesFertilized > fertilizerLimitIncrease) {
            timesFertilized = fertilizerLimitIncrease;
        }
        double fertilizerBonus = harvestTotal * 0.5 * timesFertilized;
        double finalHarvestPrice = harvestTotal + waterBonus + fertilizerBonus;
        finalHarvestPrice *= type.getHarvestMultiplier();

        return finalHarvestPrice;
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
        return cropState.equals(CropStates.WITHERED);
    }

    public boolean isHarvestable() {
        return cropState.equals(CropStates.HARVESTABLE);
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
    public double getExperienceYield() {
        return experienceYield;
    }
    public CropStates getCropState() {
        return cropState;
    }
    public int getSeedCost() {
        return seedCost;
    }
    public boolean isTree() {
        return type.equals(CropType.FRUIT_TREE);
    }
}
