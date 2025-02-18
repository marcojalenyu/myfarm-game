public class HarvestableCropState extends CropState {

    public HarvestableCropState(Crop crop) {
        super(crop);
    }

    @Override
    public void update() {
        crop.setCropState(new WitheredCropState(crop));
    }

    @Override
    public String getIcon(String seedName, String typeString) {
        return "assets/"+ seedName +"Done.png";
    }
}
