import javax.swing.*;
import javax.swing.border.EmptyBorder;
// import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.ArrayList;

/**
   The MyFarmGUI class serves as the view of the program
 */
public class MyFarmGUI extends JFrame {

    private final int WINDOW_WIDTH = 900;
    private final int WINDOW_HEIGHT = 680;

    /**
     Constructor of MyFarmGUI sets the icons, windows, and frames
     */
    public MyFarmGUI(){
        super("My Farm");
        ImageIcon icon = new ImageIcon("assets/icon.png");
        setIconImage(icon.getImage());
        setLayout(new BorderLayout());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        init();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     Attributes of MyFarmGUI:
     1. tiles: contains an array of buttons representing the tiles
     2. actions: contains a list of buttons representing the tools/actions
     3. seeds: contains a list of buttons representing the seeds of crops
     */
    private JButton[][] tiles = new JButton[Constants.FARM_WIDTH][Constants.FARM_LENGTH];
    private ArrayList<JButton> actions = new ArrayList<>();
    private ArrayList<JButton> seeds = new ArrayList<>();

    /**
     Other Attributes of MyFarmGUI (just for displaying some attributes of farmer):
     */
    private JLabel wallet;
    private JLabel level;
    private JLabel experience;
    private JLabel farmerType;
    private JLabel day;

    private JLabel createBlackMinecraftJLabel (String labelText) {
        JLabel label = new JLabel();
        label.setText(labelText);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Minecraft",NORMAL, 15));
        return label;
    }

    private JPanel createPlantsJPanel(List<String> seedNames) {
        JPanel plantsTab =  new JPanel();
        plantsTab.setBackground(Color.decode("#a18a77"));
            
        for (String seedName: seedNames) {
            Icon icon = new ImageIcon("assets/" + seedName + ".png");
            JButton button = new JButton(icon);
            button.setName(seedName);
            button.setPreferredSize( new Dimension(50,50));
            seeds.add(button);
            plantsTab.add(button);
        }
        return plantsTab;
    }

    private JPanel createActionsJPanel(List<String> actionNames) {
        JPanel actionsTab = new JPanel();
        actionsTab.setBackground(Color.decode("#abd7d8"));
        for (String actionName: actionNames) {
            JButton button = new JButton(actionName);
            button.setName(actionName);
            actions.add(button);
            actionsTab.add(button, BorderLayout.NORTH);
        }
        return actionsTab;
    }

    private void init(){

        // NORTH PANEL
        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new BorderLayout());

        // sub panel  Game Title
        JPanel gameTitlePanel = new JPanel();
        gameTitlePanel.setLayout(new GridBagLayout());
        gameTitlePanel.setBackground(Color.decode("#abd7d8"));

        JLabel gameTitle = new JLabel();
        gameTitle.setIcon(new ImageIcon("assets/my-farm.png"));

        gameTitlePanel.add(gameTitle);
        panelNorth.add(gameTitlePanel, BorderLayout.NORTH);

        //sub panel Farmer Information
        JPanel farmerInfoPanel = new JPanel();
        farmerInfoPanel.setLayout(new BorderLayout());

        //sub sub panel Wallet, Level, Experience, Type
        JPanel farmerInfoPanelLeft = new JPanel();
        farmerInfoPanelLeft.setBorder(new EmptyBorder(0,150,0,0));
        farmerInfoPanelLeft.setBackground(Color.decode("#abd7d8"));
        farmerInfoPanelLeft.setLayout(new BorderLayout());

        // sub sub sub panel Wallet and Level
            JPanel farmerInfoPanelLeftUp = new JPanel();
            farmerInfoPanelLeftUp.setLayout(new BorderLayout());
            farmerInfoPanelLeftUp.setBackground(Color.decode("#abd7d8"));

                wallet = createBlackMinecraftJLabel("Wallet: " );
                farmerInfoPanelLeftUp.add(wallet, BorderLayout.NORTH);

                level = createBlackMinecraftJLabel("Level: ");
                farmerInfoPanelLeftUp.add(level, BorderLayout.SOUTH);

            farmerInfoPanelLeft.add(farmerInfoPanelLeftUp, BorderLayout.NORTH);

            //sub sub sub panel Experience and Type
            JPanel farmerInfoPanelLeftDown = new JPanel();
            farmerInfoPanelLeftDown.setLayout(new BorderLayout());
            farmerInfoPanelLeftDown.setBackground(Color.decode("#abd7d8"));

                experience = createBlackMinecraftJLabel("Experience: ");
                farmerInfoPanelLeftDown.add(experience, BorderLayout.NORTH);

                farmerType = createBlackMinecraftJLabel(" Type: ");
                farmerInfoPanelLeftDown.add(farmerType, BorderLayout.SOUTH);

            farmerInfoPanelLeft.add(farmerInfoPanelLeftDown, BorderLayout.SOUTH);
        farmerInfoPanel.add(farmerInfoPanelLeft, BorderLayout.WEST);

        JPanel farmerInfoPanelCenter = new JPanel();
        farmerInfoPanelCenter.setBackground(Color.decode("#abd7d8"));
        farmerInfoPanel.add(farmerInfoPanelCenter, BorderLayout.CENTER);

        //sub panel for Day
        JPanel farmerInfoPanelRight = new JPanel();
        farmerInfoPanelRight.setLayout(new BorderLayout());
        farmerInfoPanelRight.setBackground(Color.decode("#abd7d8"));

        day = createBlackMinecraftJLabel("");
        farmerInfoPanelRight.add(day, BorderLayout.NORTH);

        farmerInfoPanel.add(farmerInfoPanelRight, BorderLayout.EAST);
        panelNorth.add(farmerInfoPanel, BorderLayout.SOUTH);
        this.add(panelNorth, BorderLayout.NORTH);

        //CENTER PANEL

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.decode("#a18a77"));
        centerPanel.setBorder(new EmptyBorder(60, 0,0,0));

        JPanel upperTiles = new JPanel();
        upperTiles.setLayout(new BorderLayout());

        JPanel lowerTiles = new JPanel();
        lowerTiles.setLayout(new BorderLayout());

        JPanel row0 = new JPanel();
        row0.setBackground(Color.decode("#a18a77"));

        JPanel row1 = new JPanel();
        row1.setBackground(Color.decode("#a18a77"));

        JPanel row2 = new JPanel();
        row2.setBackground(Color.decode("#a18a77"));

        JPanel row3 = new JPanel();
        row3.setBackground(Color.decode("#a18a77"));

        JPanel row4 = new JPanel();
        row4.setBackground(Color.decode("#a18a77"));

        for (int i = 0; i < Constants.FARM_WIDTH; i++) {
            for (int j = 0; j < Constants.FARM_LENGTH; j++) {

                tiles[i][j] = new JButton();
                tiles[i][j].setName(String.valueOf(i*10+j));
                tiles[i][j].setIcon(new ImageIcon("unplowed.jpg"));
                tiles[i][j].setPreferredSize(new Dimension(50, 50));

                if (i == 0){
                    row0.add(tiles[i][j]);
                    upperTiles.add(row0, BorderLayout.NORTH);
                }

                if (i == 1){
                    row1.add(tiles[i][j]);
                    upperTiles.add(row1, BorderLayout.CENTER);
                }

                if (i == 2){
                    row2.add(tiles[i][j]);
                    upperTiles.add(row2, BorderLayout.SOUTH);
                }

                if (i == 3){
                    row3.add(tiles[i][j]);
                    lowerTiles.add(row3, BorderLayout.NORTH);
                }

                if (i == 4){
                    row4.add(tiles[i][j]);
                    lowerTiles.add(row4, BorderLayout.CENTER);
                }

            }

        }

        centerPanel.add(upperTiles, BorderLayout.NORTH);
        centerPanel.add(lowerTiles, BorderLayout.CENTER);

        final List<String> SEED_NAMES = List.of(
            "Turnip", "Carrot", "Potato", "Rose", 
            "Tulip", "Sunflower", "Mango", "Apple"
        );

        JPanel plantsTab =  createPlantsJPanel(SEED_NAMES);

        centerPanel.add(plantsTab, BorderLayout.SOUTH);
        this.add(centerPanel, BorderLayout.CENTER);

        //SOUTH PANEL

        final List<String> ACTION_NAMES = List.of(
            "Plow", "Water", "Fertilizer", "Harvest", 
            "Shovel", "Pickaxe", "Register", "End Day"
        );

        JPanel southPanel = createActionsJPanel(ACTION_NAMES);

        this.add(southPanel, BorderLayout.SOUTH);
    }

    /**
     This function creates a listener of inputs for all buttons
     * @param listener: used to catch inputs
     */
    public void setActionListener(ActionListener listener) {
        for (JButton[] jButtonArray: tiles)  {
            for (JButton button: jButtonArray) {
                button.addActionListener(listener);
            }
        }

        for(JButton b : actions)
            b.addActionListener(listener);

        for(JButton b : seeds)
            b.addActionListener(listener);
    }

    /**
     Getters and Setters:
     */
    public JLabel getWallet() { return wallet; }
    public JButton getTile(int i, int j) { return tiles[i][j];}
    public JLabel getLevel() { return level; }
    public JLabel getExperience() { return experience;}
    public JLabel getFarmerType() { return farmerType;}
    public ArrayList<JButton> getActions() { return actions;}
    public ArrayList<JButton> getSeeds() {return seeds;}
    public JLabel getDay() { return day;}
}
