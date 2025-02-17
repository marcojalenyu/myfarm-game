import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    /**
     Constructor of MyFarmGUI sets the icons, windows, and frames
     */
    public MyFarmGUI(){
        super("My Farm");
        ImageIcon icon = new ImageIcon("assets/icon.png");
        setIconImage(icon.getImage());
        setLayout(new BorderLayout());

        final int WINDOW_WIDTH = 900;
        final int WINDOW_HEIGHT = 680;
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

    private JPanel createFarmerInfoSubPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#abd7d8"));
        panel.setLayout(new BorderLayout());
        return panel;
    }

    private JPanel createGameTitlePanel() {
        JPanel gameTitlePanel = new JPanel();
        gameTitlePanel.setLayout(new GridBagLayout());
        gameTitlePanel.setBackground(Color.decode("#abd7d8"));

        JLabel gameTitle = new JLabel();
        gameTitle.setIcon(new ImageIcon("assets/my-farm.png"));

        gameTitlePanel.add(gameTitle);
        return gameTitlePanel;
    }

    private JPanel createFarmerInfoPanel() {
        JPanel farmerInfoPanel = new JPanel();
        farmerInfoPanel.setLayout(new BorderLayout());

        //sub sub panel Wallet, Level, Experience, Type
        JPanel farmerInfoPanelLeft = createFarmerInfoSubPanel();
        farmerInfoPanelLeft.setBorder(new EmptyBorder(0,150,0,0));

        // sub sub sub panel Wallet and Level
            JPanel farmerInfoPanelLeftUp = createFarmerInfoSubPanel();

                wallet = createBlackMinecraftJLabel("Wallet: " );
                farmerInfoPanelLeftUp.add(wallet, BorderLayout.NORTH);

                level = createBlackMinecraftJLabel("Level: ");
                farmerInfoPanelLeftUp.add(level, BorderLayout.SOUTH);

            farmerInfoPanelLeft.add(farmerInfoPanelLeftUp, BorderLayout.NORTH);

            //sub sub sub panel Experience and Type
            JPanel farmerInfoPanelLeftDown = createFarmerInfoSubPanel();

                experience = createBlackMinecraftJLabel("Experience: ");
                farmerInfoPanelLeftDown.add(experience, BorderLayout.NORTH);

                farmerType = createBlackMinecraftJLabel(" Type: ");
                farmerInfoPanelLeftDown.add(farmerType, BorderLayout.SOUTH);

            farmerInfoPanelLeft.add(farmerInfoPanelLeftDown, BorderLayout.SOUTH);
        farmerInfoPanel.add(farmerInfoPanelLeft, BorderLayout.WEST);

        JPanel farmerInfoPanelCenter = createFarmerInfoSubPanel();
        farmerInfoPanel.add(farmerInfoPanelCenter, BorderLayout.CENTER);

        //sub panel for Day
        JPanel farmerInfoPanelRight = createFarmerInfoSubPanel();

        day = createBlackMinecraftJLabel("");
        farmerInfoPanelRight.add(day, BorderLayout.NORTH);

        farmerInfoPanel.add(farmerInfoPanelRight, BorderLayout.EAST);
        return farmerInfoPanel;
    }

    private JPanel[] createTillRowJPanelArray() {
        final int NUMBER_OF_ROWS = 5;
        JPanel[] rowArray = new JPanel[NUMBER_OF_ROWS];

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            rowArray[i] = new JPanel();
            rowArray[i].setBackground(Color.decode("#a18a77"));
        }

        for (int i = 0; i < Constants.FARM_WIDTH; i++) {
            for (int j = 0; j < Constants.FARM_LENGTH; j++) {
                tiles[i][j] = new JButton();
                tiles[i][j].setName(String.valueOf(i*10+j));
                tiles[i][j].setIcon(new ImageIcon("unplowed.jpg"));
                tiles[i][j].setPreferredSize(new Dimension(50, 50));

                rowArray[i].add(tiles[i][j]);
            }
        }
        return rowArray;
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

    public void updateView(MyFarm myFarm) {
        wallet.setText("Wallet: " + String.format("%.2f", myFarm.getFarmerWallet()));
        level.setText("Level: " + myFarm.getFarmerLevel());
        experience.setText("Experience: " + myFarm.getFarmerExperience());
        farmerType.setText("Type: " + myFarm.getFarmerType());
        day.setText("Day " + myFarm.getDay());

        // For updating the tiles images:
        for (int i = 0; i < Constants.FARM_WIDTH; i++) {
            for (int j = 0; j < Constants.FARM_LENGTH; j++) {
                Tile tile = myFarm.getTile(i, j);
                JButton tileIcon = tiles[i][j];

                updateTile(tile, tileIcon);
            }
        }
    }

    private void updateTile (Tile tile, JButton tileIcon) {
        switch(tile.getTileState()) {
            case TileStates.ROCK:
                tileIcon.setIcon(new ImageIcon("assets/rocked.jpg"));
                break;

            case TileStates.NOT_PLOWED:
                tileIcon.setIcon(new ImageIcon("assets/unplowed.jpg"));
                break;

            case TileStates.PLOWED:
                tileIcon.setIcon(new ImageIcon("assets/plowed.jpg"));
                break;

            case TileStates.PLANTED:
                Crop crop = tile.getCrop();

                switch(crop.getCropState()) {

                    case CropStates.GROWING:
                        if (crop.getType().equals(CropType.FRUIT_TREE)){
                            if (crop.isWateredEnough() && crop.isFertilizedEnough())
                                tileIcon.setIcon(new ImageIcon("assets/healthyTree.png"));
                            else if (crop.isWateredEnough())
                                tileIcon.setIcon(new ImageIcon("assets/wateredTree.png"));
                            else if (crop.isFertilizedEnough())
                                tileIcon.setIcon(new ImageIcon("assets/fertilizedTree.png"));
                            else
                                tileIcon.setIcon(new ImageIcon("assets/growingTree.png"));
                        }
                        else {
                            if (crop.isWateredEnough() && crop.isFertilizedEnough())
                                tileIcon.setIcon(new ImageIcon("assets/healthyPlant.png"));
                            else if (crop.isWateredEnough())
                                tileIcon.setIcon(new ImageIcon("assets/wateredPlant.png"));
                            else if (crop.isFertilizedEnough())
                                tileIcon.setIcon(new ImageIcon("assets/fertilizedPlant.png"));
                            else
                                tileIcon.setIcon(new ImageIcon("assets/growingPlant.jpg"));
                        }
                        break;

                    case CropStates.HARVESTABLE:
                        tileIcon.setIcon(new ImageIcon("assets/"+crop.getSeed()+"Done.png"));
                        break;

                    case CropStates.WITHERED:
                        tileIcon.setIcon(new ImageIcon("assets/withered.jpg"));
                        break;

                }
                break;
        }
    }

    private void init(){
        // NORTH PANEL
        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new BorderLayout());

        // sub panel  Game Title
        JPanel gameTitlePanel = createGameTitlePanel();
        panelNorth.add(gameTitlePanel, BorderLayout.NORTH);

        //sub panel Farmer Information
        JPanel farmerInfoPanel = createFarmerInfoPanel();
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

        JPanel[] tillRowArray = createTillRowJPanelArray();
        upperTiles.add(tillRowArray[0], BorderLayout.NORTH);
        upperTiles.add(tillRowArray[1], BorderLayout.CENTER);
        upperTiles.add(tillRowArray[2], BorderLayout.SOUTH);
        lowerTiles.add(tillRowArray[3], BorderLayout.NORTH);
        lowerTiles.add(tillRowArray[4], BorderLayout.CENTER);

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
    public ArrayList<JButton> getActions() { return actions;}
    public ArrayList<JButton> getSeeds() {return seeds;}
}
