public class GrowingCropState extends CropState {

    public GrowingCropState(Crop crop) {
        super(crop);
    }

    @Override
    public void update() {
        crop.progressHarvestTime();

        if (crop.isTimeToHarvest()) {
            if (crop.isWateredEnough() && crop.isFertilizedEnough()) {
                crop.setCropState(new HarvestableCropState(crop));
            }
            else {
                crop.setCropState(new WitheredCropState(crop));
            }
        }
    }

    @Override
    public String getIcon(String seedName, String typeString) {

        if (crop.isWateredEnough() && crop.isFertilizedEnough())
            return "assets/healthy" + typeString + ".png";
        else if (crop.isWateredEnough())
            return "assets/watered" + typeString + ".png";
        else if (crop.isFertilizedEnough())
            return "assets/fertilized" + typeString + ".png";
        else
            return "assets/growing" + typeString + ".png";
    }
}
