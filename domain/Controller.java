import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Controller class connects the model (logic) with the view (GUI)
 */
public class Controller implements ActionListener{
    /**
     Unique Attributes of Controller:
     1. gui: holds the view of the program
     2. myFarm: holds the model of the program
     3. farmer: allows easier access to the farmer attribute of the model
     4. selected: stores the name of the selected button
     */
    private MyFarmGUI gui;
    private MyFarm myFarm;
    private Farmer farmer;
    private String selected = "";

    /**
     Constructor of Controller sets a listener for the inputs
     * @param gui: view
     * @param myFarm: model
     */
    public Controller(MyFarmGUI gui, MyFarm myFarm){
        this.gui = gui;
        this.myFarm = myFarm;
        this.farmer = myFarm.getFarmer();

        updateView();

        gui.setActionListener(this);
    }

    /**
     This function updates the GUI after every action
     */
    public void updateView() {
        gui.getWallet().setText("Wallet: " + String.format("%.2f", farmer.getWallet()));
        gui.getLevel().setText("Level: " + farmer.getLevel());
        gui.getExperience().setText("Experience: " + farmer.getExperience());
        gui.getFarmerType().setText("Type: " + farmer.getType());
        gui.getDay().setText("Day " + myFarm.getDay());

        /**
         For updating the tiles:
         */
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                Tile tile = myFarm.getTile(i, j);
                JButton tileIcon = gui.getTile(i, j);
                Crop crop = tile.getCrop();

                if (tile.hasRock())
                    tileIcon.setIcon(new ImageIcon("assets/rocked.jpg"));
                else if (tile.isPlowed()) {
                    if(crop == null)
                        tileIcon.setIcon(new ImageIcon("assets/plowed.jpg"));
                    else {
                        if (crop.isWithered())
                            tileIcon.setIcon(new ImageIcon("assets/withered.jpg"));
                        else if(crop.getHarvestTime() == 0)
                            tileIcon.setIcon(new ImageIcon("assets/"+crop.getSeed()+"Done.png"));
                        else if(crop.getTimesWatered() >= crop.getWaterNeeded()) {
                            if(crop.getTimesFertilized() >= crop.getFertilizerNeeded()) {
                                if(crop.getType().equals(CropType.FRUIT_TREE))
                                    tileIcon.setIcon(new ImageIcon("assets/healthyTree.png"));
                                else
                                    tileIcon.setIcon(new ImageIcon("assets/healthyPlant.png"));
                            }
                            else {
                                if(crop.getType().equals(CropType.FRUIT_TREE))
                                    tileIcon.setIcon(new ImageIcon("assets/wateredTree.png"));
                                else
                                    tileIcon.setIcon(new ImageIcon("assets/wateredPlant.png"));
                            }
                        }
                        else if(crop.getTimesFertilized() >= crop.getFertilizerNeeded()) {
                            if(crop.getTimesWatered() < crop.getWaterNeeded()) {
                                if(crop.getType().equals(CropType.FRUIT_TREE))
                                    tileIcon.setIcon(new ImageIcon("assets/fertilizedTree.png"));
                                else
                                    tileIcon.setIcon(new ImageIcon("assets/fertilizedPlant.png"));
                            }
                        }
                        else {
                            if(crop.getType().equals(CropType.FRUIT_TREE))
                                tileIcon.setIcon(new ImageIcon("assets/growingTree.png"));
                            else
                                tileIcon.setIcon(new ImageIcon("assets/growingPlant.jpg"));
                        }
                    }
                }
                else
                    tileIcon.setIcon(new ImageIcon("assets/unplowed.jpg"));
            }
        }
    }

    /**
     * This function prompts the user if they want to register and checks its validity
     * @return true if user wants to register and is high enough level;
     *         false if otherwise
     */
    public boolean canRegister() {
        boolean isValid = false;
        int option = JOptionPane.NO_OPTION;

        switch (farmer.getType()){
            case FARMER:
                if(farmer.getLevel() >= 5)
                    isValid = true;
                    option = JOptionPane.showConfirmDialog(gui,"Do you want to be a registered farmer for 200 Objectcoins?", "Registration", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                break;
            case REGISTERED_FARMER:
                if(farmer.getLevel() >= 10)
                    isValid = true;
                    option = JOptionPane.showConfirmDialog(gui,"Do you want to be a distinguished farmer for 300 Objectcoins?", "Registration", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                break;
            case DISTINGUISHED_FARMER:
                if(farmer.getLevel() >= 15)
                    isValid = true;
                    option = JOptionPane.showConfirmDialog(gui,"Do you want to be a legendary farmer for 400 Objectcoins?", "Registration", JOptionPane.YES_NO_OPTION ,
                            JOptionPane.QUESTION_MESSAGE);
                break;
            case LEGENDARY_FARMER:
                isValid = true;
                JOptionPane.showMessageDialog(gui, "You are already at the highest title.", "Invalid", JOptionPane.ERROR_MESSAGE);
                break;
        }

        if(option == JOptionPane.YES_OPTION)
            if(isValid)
                return true;
            else
                JOptionPane.showMessageDialog(gui, "Your level is not high enough.", "Invalid", JOptionPane.ERROR_MESSAGE);

       return false;
    }

    /**
     This function sets the backgrounds of buttons to white to show it is not selected
     */
    public void deselect() {
        for(JButton b : gui.getActions())
            b.setBackground(Color.white);
        for(JButton b : gui.getSeeds())
            b.setBackground(Color.white);
    }

    /**
     This function processes the inputs to the model
     @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String button = ((JButton) e.getSource()).getName();

        if (selected == "") {
            switch(button) {
                case "End Day":
                    if (!myFarm.isGameOver()) {
                        myFarm.advanceDay();
                        deselect();
                    }
                    else {
                        int option = JOptionPane.showConfirmDialog(gui, "Restart?", "Game Over", JOptionPane.YES_NO_OPTION);
                        gui.dispose();
                        if (option == JOptionPane.YES_OPTION) {
                            Controller controller = new Controller(new MyFarmGUI(), new MyFarm());
                        }
                    }
                    break;
                case "Register":
                    if(canRegister())
                        farmer.register();
                    break;
                default:
                    selected = button;

                    for(JButton b : gui.getActions()) {
                        if(b.getName().equals(selected))
                            b.setBackground(Color.cyan);
                    }
                    for(JButton b : gui.getSeeds()) {
                        if(b.getName().equals(selected))
                            b.setBackground(Color.cyan);
                    }

                    break;
            }
        }
        else {

            deselect();

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {

                    Tile tile = myFarm.getTile(i, j);
                    Crop crop = tile.getCrop();

                    if (button.equals(String.valueOf(i * 10 + j))) {
                        switch(selected) {
                            case "Plow":
                                farmer.plow(tile);
                                break;
                            case "Water":
                                farmer.water(crop);
                                break;
                            case "Fertilizer":
                                farmer.fertilize(crop);
                                break;
                            case "Harvest":
                                farmer.harvest(tile);
                                break;
                            case "Shovel":
                                farmer.dig(tile);
                                break;
                            case "Pickaxe":
                                farmer.mine(tile);
                                break;
                            default:
                                for(JButton s : gui.getSeeds())
                                    if(selected.equals(s.getName())) {
                                        farmer.plant(tile, s.getName(), myFarm.getTiles());
                                    }
                                break;
                        }
                    }
                }
            }

            selected = "";
        }

        updateView();
    }
}
