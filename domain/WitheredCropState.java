public class WitheredCropState extends CropState {

    public WitheredCropState(Crop crop) {
        super(crop);
    }

    public void update() {
    }

    public String getIcon(String seedName, String typeString) {
        return "assets/withered.jpg";
    }
}
