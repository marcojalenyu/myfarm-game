/**
    The Driver class runs the main of MyFarm.
 */
public class Driver {
    /**
     Main creates an instance of a controller containing new instances of the view and model.
     */
    public static void main(String[] args) {
        MyFarmGUI gui = new MyFarmGUI();
        MyFarm model = new MyFarm();
        Controller controller = new Controller(gui, model);
    }
}
