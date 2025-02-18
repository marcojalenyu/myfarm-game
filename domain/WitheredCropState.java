public class WitheredCropState extends CropState {

    public WitheredCropState(Crop crop) {
        super(crop);
    }

    @Override
    public void update() {
    }

    @Override
    public String getIcon(String seedName, String typeString) {
        return "assets/withered.jpg";
    }
}
