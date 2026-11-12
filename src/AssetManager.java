import java.util.*;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AssetManager {
    
    HashMap<Drawable.ImageData, BufferedImage> imageMap;

    private void registerImage(Drawable.ImageData path) throws IOException
    {
        imageMap.put(path, ImageIO.read(new File("images/"+path.imagePath() +".png")));
    }

    public AssetManager()
    {
        imageMap = new HashMap<Drawable.ImageData, BufferedImage>();
        try
        {
            registerImage(new Drawable.ImageData("BG/Boards/Chess"));
            registerImage(new Drawable.ImageData("Pieces/White/Queen"));
            registerImage(new Drawable.ImageData("Pieces/Black/Queen"));

        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    public BufferedImage requestImage(Drawable.ImageData image)
    {
        return imageMap.get(image);
    }
}
