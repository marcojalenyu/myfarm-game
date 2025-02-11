import javax.swing.*;

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
    private CropStates cropState = CropStates.GROWING;

    /**
     Constructor of Crop initializes the seed planted on a tile.
     */
    public Crop(String seed, CropType type, int harvestTime, int waterNeeded, int waterBonusLimit, int fertilizerNeeded, int fertilizerBonusLimit, int productYield, double experienceYield, double basePrice) {
        this.seed = seed;
        this.type = type;
        this.harvestTime = harvestTime;
        this.timesWatered = Constants.START_TIMES_WATERED;
        this.waterNeeded = waterNeeded;
        this.waterBonusLimit = waterBonusLimit;
        this.timesFertilized = Constants.START_TIMES_FERTILIZED;
        this.fertilizerNeeded = fertilizerNeeded;
        this.fertilizerBonusLimit = fertilizerBonusLimit;
        this.productYield = productYield;
        this.experienceYield = experienceYield;
        this.basePrice = basePrice;
    }

    /**
     * Updates the crop per day by reducing harvest time and checking if harvestable or withered
     */
    public void update() {
        this.harvestTime--;

        if(this.harvestTime == 0) {
            if (this.timesWatered < this.waterNeeded || this.timesFertilized < this.fertilizerNeeded) {
                cropState = CropStates.WITHERED;
            }
            else {
                cropState = CropStates.HARVESTABLE;
            }
        }
        else if(this.harvestTime < 0) {
            cropState = CropStates.WITHERED;
        }
    }

    public double computeHarvestPrice(int earnBonus) {

        double harvestTotal = productYield * (basePrice + earnBonus);
        double waterBonus = harvestTotal * 0.2 * (timesWatered - 1);
        double fertilizerBonus = harvestTotal * 0.5 * timesFertilized;
        double finalHarvestPrice = harvestTotal + waterBonus + fertilizerBonus;

        if (type.equals(CropType.FLOWER))
            finalHarvestPrice *= 1.1;

        return finalHarvestPrice;
    }

    public void water() {
        if (timesWatered < waterBonusLimit)
            timesWatered++;
    }

    public void fertilize() {
        if (timesFertilized < fertilizerBonusLimit)
            timesFertilized++;
    }

    public boolean isWateredEnough() {
        return timesWatered >= waterNeeded;
    }

    public boolean isFertilizedEnough() {
        return timesFertilized >= fertilizerNeeded;
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
    public int getProductYield() {
        return productYield;
    }
    public double getExperienceYield() {
        return experienceYield;
    }
    public CropStates getCropState() {
        return cropState;
    }
}
