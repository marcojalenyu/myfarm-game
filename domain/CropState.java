public abstract class CropState {

    protected Crop crop;

    public CropState(Crop crop) {
        this.crop = crop;
    }

    public abstract void update();

    public abstract String getIcon(String seedName, String typeString);

    public boolean isWithered() {
        return this instanceof WitheredCropState;
    }

    public boolean isHarvestable() {
        return this instanceof HarvestableCropState;
    }
}
