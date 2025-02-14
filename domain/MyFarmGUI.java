import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
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

        JPanel plantsTab =  new JPanel();
        plantsTab.setBackground(Color.decode("#a18a77"));

        Icon turnipIcon = new ImageIcon("assets/Turnip.png");
        JButton turnip = new JButton(turnipIcon);
        turnip.setName("turnip");
        turnip.setPreferredSize( new Dimension(50,50));
        seeds.add(turnip);

        Icon carrotIcon = new ImageIcon("assets/Carrot.png");
        JButton carrot= new JButton(carrotIcon);
        carrot.setName("carrot");
        carrot.setPreferredSize( new Dimension(50,50));
        seeds.add(carrot);

        Icon potatoIcon = new ImageIcon("assets/Potato.png");
        JButton potato= new JButton(potatoIcon);
        potato.setName("potato");
        potato.setPreferredSize( new Dimension(50,50));
        seeds.add(potato);

        Icon roseIcon = new ImageIcon("assets/Rose.png");
        JButton rose = new JButton(roseIcon);
        rose.setName("rose");
        rose.setPreferredSize( new Dimension(50,50));
        seeds.add(rose);

        Icon tulipIcon = new ImageIcon("assets/Tulip.png");
        JButton tulip = new JButton(tulipIcon);
        tulip.setName("tulip");
        tulip.setPreferredSize( new Dimension(50,50));
        seeds.add(tulip);

        Icon sunflowerIcon = new ImageIcon("assets/Sunflower.png");
        JButton sunflower= new JButton(sunflowerIcon);
        sunflower.setName("sunflower");
        sunflower.setPreferredSize( new Dimension(50,50));
        seeds.add(sunflower);

        Icon mangoIcon = new ImageIcon("assets/Mango.png");
        JButton mango = new JButton(mangoIcon);
        mango.setName("mango");
        mango.setPreferredSize( new Dimension(50,50));
        seeds.add(mango);

        Icon appleIcon = new ImageIcon("assets/Apple.png");
        JButton apple = new JButton(appleIcon);
        apple.setName("apple");;
        apple.setPreferredSize( new Dimension(50,50));
        seeds.add(apple);

        for(JButton b : seeds)
            plantsTab.add(b);

        centerPanel.add(plantsTab, BorderLayout.SOUTH);
        this.add(centerPanel, BorderLayout.CENTER);

        //SOUTH PANEL

        JPanel southPanel =  new JPanel();
        southPanel.setBackground(Color.decode("#abd7d8"));

        JButton plow = new JButton("Plow");
        plow.setName("Plow");

        JButton water = new JButton("Water");
        water.setName("Water");

        JButton fertilizer = new JButton("Fertilizer");
        fertilizer.setName("Fertilizer");

        JButton harvest = new JButton("Harvest");
        harvest.setName("Harvest");

        JButton shovel = new JButton("Shovel");
        shovel.setName("Shovel");

        JButton pickaxe = new JButton("Pickaxe");
        pickaxe.setName("Pickaxe");

        JButton register = new JButton("Register");
        register.setName("Register");

        JButton endDay = new JButton("End Day");
        endDay.setName("End Day");

        actions.add(plow);
        actions.add(water);
        actions.add(fertilizer);
        actions.add(harvest);
        actions.add(shovel);
        actions.add(pickaxe);
        actions.add(register);
        actions.add(endDay);

        for(JButton b : actions)
            southPanel.add(b, BorderLayout.NORTH);


        this.add(southPanel, BorderLayout.SOUTH);

    }

    /**
     This function creates a listener of inputs for all buttons
     * @param listener: used to catch inputs
     */
    public void setActionListener(ActionListener listener) {
        for (int i = 0; i < Constants.FARM_WIDTH; i++) {
            for (int j = 0; j < Constants.FARM_LENGTH; j++) {
                tiles[i][j].addActionListener(listener);
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
