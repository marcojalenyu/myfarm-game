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
    private final MyFarmGUI gui;
    private final MyFarm myFarm;
    private final Farmer farmer;
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

        gui.updateView(myFarm);

        gui.setActionListener(this);
    }

    /**
     * This function prompts the user if they want to register and checks its validity
     * @return true if user wants to register and is high enough level;
     *         false if otherwise
     */
    public boolean canRegister() {
        if (farmer.isLegendary()) {
            JOptionPane.showMessageDialog(gui, "You are already at the highest title.", "Invalid", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!farmer.canLevelUp()) {
            JOptionPane.showMessageDialog(gui, "Your level is not high enough.", "Invalid", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int option = JOptionPane.showConfirmDialog(gui,"Do you want to be a" + farmer.getNextLevelString() + " for " + farmer.getLevelUpCost() + " Objectcoins?", "Registration", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

        return option == JOptionPane.YES_OPTION;
    }

    private void endDay() {
        if (!myFarm.isGameOver()) {
            myFarm.advanceDay();
            gui.triggerDeselect();
        }
        else {
            int option = JOptionPane.showConfirmDialog(gui, "Restart?", "Game Over", JOptionPane.YES_NO_OPTION);
            gui.dispose();
            if (option == JOptionPane.YES_OPTION) {
                Controller controller = new Controller(new MyFarmGUI(), new MyFarm());
            }
        }
    }

    /**
     This function processes the inputs to the model
     @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String button = ((JButton) e.getSource()).getName();
        try {
            if (selected.isEmpty()) {
                switch(button) {
                    case "End Day":
                        endDay();
                        break;
                    case "Register":
                        if(canRegister()){
                            String output = farmer.register();
                            JOptionPane.showMessageDialog(gui, "You are now a " + output + ".", "Registration Successful", JOptionPane.PLAIN_MESSAGE);
                        }
                        break;
                    default:
                        selected = button;
                        gui.triggerSelect(selected);
                        break;
                }
            }
            else {

                gui.triggerDeselect();

                for (int i = 0; i < Constants.FARM_WIDTH; i++) {
                    for (int j = 0; j < Constants.FARM_LENGTH; j++) {

                        Tile tile = myFarm.getTile(i, j);

                        if (button.equals(String.valueOf(i * Constants.FARM_LENGTH + j))) {
                            switch(selected) {
                                case "Plow":
                                    farmer.plow(tile);
                                    break;
                                case "Water":
                                    farmer.water(tile);
                                    break;
                                case "Fertilizer":
                                    farmer.fertilize(tile);
                                    break;
                                case "Harvest":
                                    String output = farmer.harvest(tile);
                                    JOptionPane.showMessageDialog(gui, output, "Harvest Successful", JOptionPane.PLAIN_MESSAGE);
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui, ex.getMessage(), "Invalid", JOptionPane.ERROR_MESSAGE);
        }

        gui.updateView(myFarm);
    }
}
