import java.awt.image.BufferedImage;

public class Drawable {
    public record ImageData(String imagePath) {}
    public record DrawRect(double x,double y, double w, double h){

        public boolean pointInside(double pX, double pY) {
            return pX >= x && pX <= x + w && pY >= y && pY <= y + h;
        }

    }

    private ImageData image;
    public void setImage(ImageData newImage){image = newImage;}
    public ImageData getImage(){return image;}

    public Drawable.DrawRect calculateDrawRect(BufferedImage originalImage, Drawable.DrawRect surface, Drawable.DrawRect relImage)
    {
        if(originalImage == null)
            return new Drawable.DrawRect(0,0,0,0);

        int imgWidth = originalImage.getWidth();
        int imgHeight = originalImage.getHeight();
        
        double widthRatio = (double) surface.w() / imgWidth;
        double heightRatio = (double) surface.h() / imgHeight;
        double bestRatio = Math.min(widthRatio, heightRatio); // smushes down to fit whole image
        // if it's changed to Math.max, it will crop instead

        int newWidth = (int) (relImage.w()*imgWidth * bestRatio);
        int newHeight = (int) (relImage.h()*imgHeight * bestRatio);

        int targetCenterX = (int) ((surface.w()) * relImage.x() +surface.x());
        int targetCenterY = (int) ((surface.h()) * relImage.y() +surface.y());

        int x = targetCenterX - (newWidth / 2);
        int y = targetCenterY - (newHeight / 2);

        return new Drawable.DrawRect(x,y,newWidth,newHeight);
    }

    public Drawable.DrawRect calculateDrawRect(Drawable.DrawRect surface, Drawable.DrawRect relImage)
    {

        int newWidth = (int) (relImage.w()*surface.w());
        int newHeight = (int) (relImage.h()*surface.h());

        int targetCenterX = (int) ((surface.w()) * relImage.x() +surface.x());
        int targetCenterY = (int) ((surface.h()) * relImage.y() +surface.y());

        int x = targetCenterX - (newWidth / 2);
        int y = targetCenterY - (newHeight / 2);

        return new Drawable.DrawRect(x,y,newWidth,newHeight);
    }
}