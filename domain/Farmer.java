import javax.swing.*;

/**
 The Farmer class contains records related to the farmer such as wallet and level.
 This class also includes methods that allow the farmer to interact with the tiles.
 */
public class Farmer {
    /**
     Attributes of Farmer:
     1. wallet: holds the number of Objectcoins
     2. level: holds the level of the farmer
     3. experience: holds the number of experience gained before level up
     4. type: holds the registration status
     5. earnBonus: holds the bonus earnings per produce
     6. seedCostReduction: holds the seed cost reduction
     7. waterLimitIncrease: holds the water bonus limit increase
     8. fertilizerLimitIncrease: holds the fertilizer bonus limit increase
     */
    private double wallet;
    private int level;
    private double experience;
    private FarmerType type;
    private int earnBonus;
    private int seedCostReduction;
    private int waterLimitIncrease;
    private int fertilizerLimitIncrease;
    private Shop shop;

    /**
     Constructor of Farmer initializes with the default values in Constants.
     */
    public Farmer() {
        this.wallet = Constants.START_WALLET;
        this.level = Constants.START_LEVEL;
        this.experience = Constants.START_EXPERIENCE;
        this.type = Constants.START_FARMER_TYPE;
        this.earnBonus = Constants.START_BONUS;
        this.seedCostReduction = Constants.START_SEED_DISCOUNT;
        this.waterLimitIncrease = Constants.START_WATER_BONUS_LIMIT;
        this.fertilizerLimitIncrease = Constants.START_FERTILIZER_BONUS_LIMIT;
        this.shop = new Shop();
    }

    /**
     "Farmer plows a tile" by setting isPlowed to true
     @param tile - tile that will be plowed by the farmer
     */
    public void plow(Tile tile) {
        if(tile.getTileState() == TileStates.NOT_PLOWED) {
            tile.setTileState(TileStates.PLOWED);
            gainExperience(Constants.PLOWING_EXP);
        }
        else
            JOptionPane.showMessageDialog(null, "This tile cannot be plowed.", "Invalid", JOptionPane.ERROR_MESSAGE);
    }

    /**
     "Farmer plants a seed on a tile" by setting crop with a new instance of Crop
     @param tile - tile where the seed will be planted (receives new instance of Crop)
     @param plant - name of the seed to be planted (determines which Crop to be instantiated)
     @param tiles - set of tiles in the plot (used to validate tree crops)
     */
    public void plant(Tile tile, String plant, Tile[][] tiles) {

        boolean isTree = shop.getCropSeeds().get(plant).getType() == CropType.FRUIT_TREE;

        if(tile.isPlantable(tile, tiles, isTree)) {
            
            Crop crop = shop.buy(plant, wallet, seedCostReduction);

            if(crop == null) {
                JOptionPane.showMessageDialog(null, "Not enough Objectcoins", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }

            wallet -= crop.getSeedCost() - seedCostReduction;
            tile.setCrop(crop);
        }
        else
            JOptionPane.showMessageDialog(null, "This tile cannot be planted on.", "Invalid", JOptionPane.ERROR_MESSAGE);
    }

    /**
     "Farmer waters a crop" by adding 1 to timesWatered
     @param crop - crop that will be watered by the farmer
     */
    public void water(Crop crop) {
        if(crop != null) {
            crop.water();
            gainExperience(Constants.WATERING_EXP);
        }
        else
            JOptionPane.showMessageDialog(null, "This tile has no crop.", "Invalid", JOptionPane.ERROR_MESSAGE);
    }

    /**
     "Farmer fertilizes a tile" by adding 1 to timesFertilized
     @param crop - crop that will be fertilized by the farmer
     */
    public void fertilize(Crop crop) {
        if(crop != null) {
            if(wallet >= Constants.FERTILIZER_COST) {
                crop.fertilize();
                wallet -= Constants.FERTILIZER_COST;
                gainExperience(Constants.FERTILIZING_EXP);
            }
            else
                JOptionPane.showMessageDialog(null, "Not enough Objectcoins.", "Invalid", JOptionPane.ERROR_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "This tile has no crop.", "Invalid", JOptionPane.ERROR_MESSAGE);
    }

    /**
     "Farmer harvests crops on a tile" by setting crop to null and adding value to the wallet and experience of farmer
     @param tile - tile whose crop will be harvested by the farmer
     */
    public void harvest(Tile tile) {
        Crop crop = tile.getCrop();

        if (crop != null && crop.getCropState() == CropStates.HARVESTABLE) {
            double finalHarvestPrice = crop.computeHarvestPrice(earnBonus);

            JOptionPane.showMessageDialog(null, "Products Produced: "
                                            + crop.getProductYield() + " "
                                            + crop.getSeed() +"\nObjectcoins Earned: "
                                            + String.format("%.2f", finalHarvestPrice)
                                            + " Objectcoins", "Harvest Successful", JOptionPane.PLAIN_MESSAGE);

            wallet += finalHarvestPrice;
            gainExperience(crop.getExperienceYield());
            tile.setTileState(TileStates.NOT_PLOWED);
            tile.setCrop(null);
        }
        else
            JOptionPane.showMessageDialog(null, "This tile cannot be harvested.", "Invalid", JOptionPane.ERROR_MESSAGE);
    }

    /**
     "Farmer digs crops on a tile" by setting crop to null and setting state to NOT_PLOWED
     @param tile - tile will be shoveled  by the farmer
     */
    public void dig(Tile tile) {

        if (tile.getTileState() == TileStates.ROCK)
            JOptionPane.showMessageDialog(null, "This tile cannot be dug.", "Invalid", JOptionPane.ERROR_MESSAGE);

        if(this.getWallet() < Constants.DIGGING_COST)
            JOptionPane.showMessageDialog(null, "Not enough Objectcoins.", "Invalid", JOptionPane.ERROR_MESSAGE);

        // Tile is diggable and have enough money to dig
        this.setWallet(getWallet() - Constants.DIGGING_COST);
        gainExperience(Constants.DIGGING_EXP);
        tile.setTileState(TileStates.NOT_PLOWED);
        tile.setCrop(null);
    }

    /**
     "Farmer mines a tile" by setting hasRock to false
     @param tile - tile that will be mined by the farmer
     */
    public void mine(Tile tile) {

        if (tile.getTileState() != TileStates.ROCK)
            JOptionPane.showMessageDialog(null, "This tile cannot be mined.", "Invalid", JOptionPane.ERROR_MESSAGE);

        if (this.getWallet() < Constants.MINING_COST)
            JOptionPane.showMessageDialog(null, "Not enough Objectcoins.", "Invalid", JOptionPane.ERROR_MESSAGE);

        // Tile is rock and have enough money to mine
        tile.setTileState(TileStates.NOT_PLOWED);
        wallet -= Constants.MINING_COST;
        gainExperience(Constants.MINING_EXP);
    }

    /**
     "Farmer registers" by changing FarmerType and the associated bonus
     */
    public void register() {
        switch(type) {
            case FARMER:
                if(this.wallet >= 200) {
                    this.wallet -= 200;
                    type = FarmerType.REGISTERED_FARMER;
                    setBonuses(1, 1, 0, 0);
                    JOptionPane.showMessageDialog(null, "You are now a registered farmer.");
                }
                else
                    JOptionPane.showMessageDialog(null, "Not enough Objectcoins.", "Invalid", JOptionPane.ERROR_MESSAGE);
                break;
            case REGISTERED_FARMER:
                if(this.wallet >= 300) {
                    this.wallet -= 300;
                    type = FarmerType.DISTINGUISHED_FARMER;
                    setBonuses(2, 2, 1, 0);
                    JOptionPane.showMessageDialog(null, "You are now a distinguished farmer.");
                }
                else
                    JOptionPane.showMessageDialog(null, "Not enough Objectcoins.", "Invalid", JOptionPane.ERROR_MESSAGE);
                break;
            case DISTINGUISHED_FARMER:
                if(this.wallet >= 400) {
                    this.wallet -= 400;
                    type = FarmerType.LEGENDARY_FARMER;
                    setBonuses(4, 3, 2, 1);
                    JOptionPane.showMessageDialog(null, "You are now a legendary farmer.");
                }
                else
                    JOptionPane.showMessageDialog(null, "Not enough Objectcoins.", "Invalid", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    /**
     "Farmer gains experience points" by adding value to the experience of the farmer, leveling up if it reaches 100
     @param points - experience points gained from the action
     */
    public void gainExperience(double points) {
        experience += points;
        if(experience >= Constants.EXP_TO_LEVEL_UP) {
            experience -= Constants.EXP_TO_LEVEL_UP;
            level++;
            JOptionPane.showMessageDialog(null, "Farmer is now level " + level, "Leveled Up", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     Getters and Setters:
     */
    public double getWallet() { return wallet;}
    public int getLevel() { return level;}
    public double getExperience() { return experience;}
    public FarmerType getType() { return type;}
    public int getSeedCostReduction() { return seedCostReduction;}
    public void setWallet(double wallet) { this.wallet = wallet;}
    public void setBonuses(int earnBonus, int seedCostReduction, int waterLimitIncrease, int fertilizerLimitIncrease) {
        this.earnBonus = earnBonus;
        this.seedCostReduction = seedCostReduction;
        this.waterLimitIncrease = waterLimitIncrease;
        this.fertilizerLimitIncrease = fertilizerLimitIncrease;
    }
}
