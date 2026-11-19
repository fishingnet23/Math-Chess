import java.util.*;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AssetManager {
    
    HashMap<Drawable.ImageData, BufferedImage> imageMap;

    public  void registerImage(Drawable.ImageData path) throws IOException
    {
        imageMap.put(path, ImageIO.read(new File("images/"+path.imagePath())));
    }

    public AssetManager()
    {
        imageMap = new HashMap<Drawable.ImageData, BufferedImage>();
    }
    public BufferedImage requestImage(Drawable.ImageData image)
    {
        return imageMap.get(image);
    }
}
