import java.math.BigDecimal;

/**
 * Constants class to store default values for the game.
 */
public class Constants {
    // MyFarm Constants
    public static final int FARM_WIDTH = 5;
    public static final int FARM_LENGTH = 10;
    public static final int START_DAY = 1;

    // Farmer Constants
    public static final double START_WALLET = 100.0;
    public static final int START_LEVEL = 0;
    public static final double START_EXPERIENCE = 0.0;
    public static final FarmerType START_FARMER_TYPE = FarmerType.FARMER;
    public static final int START_BONUS = 0;
    public static final int START_SEED_DISCOUNT = 0;
    public static final int START_WATER_BONUS_LIMIT = 0;
    public static final int START_FERTILIZER_BONUS_LIMIT = 0;

    // Experience Constants
    public static final double EXP_TO_LEVEL_UP = 100.0;
    public static final double PLOWING_EXP = 0.5;
    public static final double WATERING_EXP = 0.5;
    public static final double FERTILIZING_EXP = 4;
    public static final double DIGGING_EXP = 2;
    public static final double MINING_EXP = 15;

    // Costs Constants
    // public static final double PLOW_COST = 0.0;
    // public static final double WATER_COST = 0.0;
    public static final double FERTILIZER_COST = 10.0;
    public static final double DIGGING_COST = 7.0;
    public static final double MINING_COST = 50.0;

    // Crop Constants
    public static final int START_TIMES_WATERED = 0;
    public static final int START_TIMES_FERTILIZED = 0;
}
