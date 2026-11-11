import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Game extends JPanel {

    private AssetManager assets;
    private Board board;
    public Game() {
        board = new Board(8, 8, "BG/Boards/Chess");
        assets = new AssetManager();

        Piece.IntentionVector slideUp = new Piece.IntentionVector(new Piece.Coordinate(0,1),Piece.MovementType.Continuous);
        Piece.IntentionVector slideLeft = new Piece.IntentionVector(new Piece.Coordinate(-1,0),Piece.MovementType.Continuous);
        Piece.IntentionVector slideDown = new Piece.IntentionVector(new Piece.Coordinate(0,-1),Piece.MovementType.Continuous);
        Piece.IntentionVector slideRight = new Piece.IntentionVector(new Piece.Coordinate(1,0),Piece.MovementType.Continuous);
        Piece.IntentionVector slideUpRight = new Piece.IntentionVector(new Piece.Coordinate(1,1),Piece.MovementType.Continuous);
        Piece.IntentionVector slideUpLeft = new Piece.IntentionVector(new Piece.Coordinate(-1,1),Piece.MovementType.Continuous);
        Piece.IntentionVector slideDownRight = new Piece.IntentionVector(new Piece.Coordinate(1,-1),Piece.MovementType.Continuous);
        Piece.IntentionVector slideDownLeft = new Piece.IntentionVector(new Piece.Coordinate(-1,-1),Piece.MovementType.Continuous);


        Piece wQueen = new Piece("Queen", new Drawable.ImageData("White/Queen"), new ArrayList<Piece.IntentionVector>(
            java.util.Arrays.asList(slideUp, slideLeft, slideDown, slideRight,slideUpRight,slideUpLeft,slideDownLeft,slideDownRight)));
        board.setPiece(4, 0, wQueen);
    }

    private void drawImageRelativeTo(Graphics2D g2d,BufferedImage originalImage,int surfaceWidth, int surfaceHeight, double relativeX,double relativeY)
    {
        if(originalImage == null)
            return;

        int imgWidth = originalImage.getWidth();
        int imgHeight = originalImage.getHeight();
        
        double widthRatio = (double) surfaceWidth / imgWidth;
        double heightRatio = (double) surfaceHeight / imgHeight;
        double bestRatio = Math.min(widthRatio, heightRatio); // smushes down to fit whole image
        // if it's changed to Math.max, it will crop instead

        int newWidth = (int) (imgWidth * bestRatio);
        int newHeight = (int) (imgHeight * bestRatio);

        int targetCenterX = (int) (surfaceWidth * relativeX);
        int targetCenterY = (int) (surfaceHeight * relativeY);

        int x = targetCenterX - (newWidth / 2);
        int y = targetCenterY - (newHeight / 2);

        g2d.drawImage(originalImage, x, y, newWidth, newHeight, null);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // base class clears the screen before drawing
        
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage boardImg = assets.requestImage(board.getImage());
        drawImageRelativeTo(g2d, boardImg,getWidth(),getHeight(),0.5,0.5);

    }
}
