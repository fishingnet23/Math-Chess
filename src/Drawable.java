

public class Drawable {
    public record ImageData(String imagePath) {}

    private ImageData image;
    public void setImage(ImageData newImage){image = newImage;}
    public ImageData getImage(){return image;}
}