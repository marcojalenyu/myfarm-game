package marcoyu.myfarm;

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

    /**
     Constructor of Farmer initializes with the following:
     1. 100 Objectcoins
     2. 0 Levels
     3. 0 Experience Points
     4. FARMER status
     5. No earn bonus, cost reduction, and bonus limit increase
     */
    public Farmer() {
        this.wallet = 100.0;
        this.level = 0;
        this.experience = 0.0;
        this.type = FarmerType.FARMER;
        this.earnBonus = 0;
        this.seedCostReduction = 0;
        this.waterLimitIncrease = 0;
        this.fertilizerLimitIncrease = 0;
    }

    /**
     "Farmer plows a tile" by setting isPlowed to true
     @param tile - tile that will be plowed by the farmer
     */
    public void plow(Tile tile) {
        if(!tile.isPlowed() && !tile.hasRock()) {
            tile.setPlowed(true);
            gainExperience(0.5);
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

        boolean hasCoins = false;

        if(tile.isPlowed() && tile.getCrop() == null) {
            switch(plant) {
                case "turnip":
                    if(wallet >= 5-seedCostReduction) {
                        tile.setCrop(new Crop("Turnip", CropType.ROOT_CROP, 2, 1, 2 + waterLimitIncrease, 0, 1 + fertilizerLimitIncrease, (int) (Math.random() * 1 + 1.5), 5.0, 6.0));
                        wallet -= 5 - seedCostReduction;
                        hasCoins = true;
                    }
                    break;
                case "carrot":
                    if(wallet >= 10-seedCostReduction) {
                        tile.setCrop(new Crop("Carrot", CropType.ROOT_CROP, 3, 1, 2+waterLimitIncrease,0, 1+fertilizerLimitIncrease, (int) (Math.random() * 1 + 1.5), 7.5, 9.0));
                        wallet -= 10-seedCostReduction;
                        hasCoins = true;
                    }
                    break;
                case "potato":
                    if(wallet >= 20-seedCostReduction) {
                        tile.setCrop(new Crop("Potato", CropType.ROOT_CROP, 5,3, 4+waterLimitIncrease, 1, 2+fertilizerLimitIncrease, (int) (Math.random() * 9 + 1.5), 12.5, 3.0));
                        wallet -= 20-seedCostReduction;
                        hasCoins = true;
                    }
                    break;
                case "rose":
                    if(wallet >= 5-seedCostReduction) {
                        tile.setCrop(new Crop("Rose", CropType.FLOWER, 1, 1, 2 + waterLimitIncrease, 0, 1 + fertilizerLimitIncrease, 1, 2.5, 5.0));
                        wallet -= 5-seedCostReduction;
                        hasCoins = true;
                    }
                    break;
                case "tulip":
                    if(wallet >= 10-seedCostReduction) {
                        tile.setCrop(new Crop("Tulip", CropType.FLOWER, 2, 2, 3+waterLimitIncrease, 0, 1+fertilizerLimitIncrease, 1, 5.0, 9.0));
                        wallet -= 10-seedCostReduction;
                        hasCoins = true;
                    }
                    break;
                case "sunflower":
                    if(wallet >= 20-seedCostReduction) {
                        tile.setCrop(new Crop("Sunflower", CropType.FLOWER, 3, 2, 3+waterLimitIncrease, 1, 2+fertilizerLimitIncrease, 1, 7.5, 19.0));
                        wallet -= 20-seedCostReduction;
                        hasCoins = true;
                    }
                    break;
                case "mango":
                    if(wallet >= 100-seedCostReduction) {
                        hasCoins = true;
                        if(isTreeValid(tiles, tile)) {
                            tile.setCrop(new Crop("Mango", CropType.FRUIT_TREE, 10, 7, 7 + waterLimitIncrease, 4, 4 + fertilizerLimitIncrease, (int) (Math.random() * 10 + 5.5), 25.0, 8.0));
                            wallet -= 100 - seedCostReduction;
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Not enough space.", "Invalid", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "apple":
                    if(wallet >= 200-seedCostReduction) {
                        hasCoins = true;
                        if(isTreeValid(tiles, tile)) {
                            tile.setCrop(new Crop("Apple", CropType.FRUIT_TREE, 10, 7, 7 + waterLimitIncrease, 5, 5 + fertilizerLimitIncrease, (int) (Math.random() * 5 + 10.5), 25.0, 5.0));
                            wallet -= 200 - seedCostReduction;
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Not enough space.", "Invalid", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
            if(!hasCoins)
                JOptionPane.showMessageDialog(null, "Not enough Objectcoins.", "Invalid", JOptionPane.ERROR_MESSAGE);
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
            if(crop.getTimesWatered() < crop.getWaterBonusLimit())
                crop.setTimesWatered(crop.getTimesWatered()+1);
            gainExperience(0.5);
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
            if(wallet >= 10) {
                if(crop.getTimesFertilized() < crop.getFertilizerBonusLimit())
                    crop.setTimesFertilized(crop.getTimesFertilized()+1);
                wallet -= 10;
                gainExperience(4);
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

        if(crop != null && !crop.isWithered() && crop.getHarvestTime() == 0) {
            double harvestTotal = crop.getProductYield()*(crop.getBasePrice()+earnBonus);
            double waterBonus = harvestTotal*0.2*(crop.getTimesWatered()-1);
            double fertilizerBonus = harvestTotal*0.5*crop.getTimesFertilized();
            double finalHarvestPrice = harvestTotal + waterBonus + fertilizerBonus;

            if(crop.getType().equals(CropType.FLOWER))
                finalHarvestPrice *= 1.1;

            JOptionPane.showMessageDialog(null, "Products Produced: "
                                            + crop.getProductYield() + " "
                                            + crop.getSeed() +"\nObjectcoins Earned: "
                                            + String.format("%.2f", finalHarvestPrice)
                                            + " Objectcoins", "Harvest Successful", JOptionPane.PLAIN_MESSAGE);

            wallet += finalHarvestPrice;
            gainExperience(crop.getExperienceYield());
            tile.setPlowed(false);
            tile.setCrop(null);
        }
        else
            JOptionPane.showMessageDialog(null, "This tile cannot be harvested.", "Invalid", JOptionPane.ERROR_MESSAGE);
    }

    /**
     "Farmer digs crops on a tile" by setting crop to null and setting the isPlowed to false
     @param tile - tile will be shoveled  by the farmer
     */
    public void dig(Tile tile) {
        if (!tile.hasRock()) {
            if(this.getWallet() >= 7) {
                this.setWallet(getWallet() - 7);
                gainExperience(2);
                tile.setCrop(null);
                tile.setPlowed(false);
            }
            else
                JOptionPane.showMessageDialog(null, "Not enough Objectcoins.", "Invalid", JOptionPane.ERROR_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "This tile cannot be dug.", "Invalid", JOptionPane.ERROR_MESSAGE);
    }

    /**
     "Farmer mines a tile" by setting hasRock to false
     @param tile - tile that will be mined by the farmer
     */
    public void mine(Tile tile) {
        if (tile.hasRock()) {
            if (wallet >= 50) {
                tile.setRock(false);
                wallet -= 50;
                gainExperience(15);
            }
            else
                JOptionPane.showMessageDialog(null, "Not enough Objectcoins.", "Invalid", JOptionPane.ERROR_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "This tile cannot be mined.", "Invalid", JOptionPane.ERROR_MESSAGE);
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
        if(experience >= 100) {
            experience -= 100;
            level++;
            JOptionPane.showMessageDialog(null, "Farmer is now level " + level, "Leveled Up", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     This function determines if the farmer can plant a tree on the given tile
     @param tiles - set of tiles in the plot to be used to check the 8 surrounding tiles of the given
     @param tile - tile to be examined
     */
    public boolean isTreeValid(Tile[][] tiles, Tile tile) {
        int row = -1;
        int col = -1;

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                if(i == 0 || i == 4 || j == 0 || j == 9) {
                    if (tiles[i][j].equals(tile))
                        return false;
                }
                else if (tiles[i][j].equals(tile)) {
                    row = i;
                    col = j;
                }
            }
        }

        for(int i = row-1; i <= row+1; i++)
            for(int j = col-1; j <= col+1; j++)
                if(tiles[i][j].hasRock() || tiles[i][j].getCrop() != null)
                    return false;

        return true;
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
