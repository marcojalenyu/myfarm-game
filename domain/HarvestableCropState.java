public class HarvestableCropState extends CropState {

    public HarvestableCropState(Crop crop) {
        super(crop);
    }

    public void update() {
        crop.setCropState(new WitheredCropState(crop));
    }

    public String getIcon(String seedName, String typeString) {
        return "assets/"+ seedName +"Done.png";
    }
}
