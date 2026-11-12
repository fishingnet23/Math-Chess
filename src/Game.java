import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game extends JPanel {

    private AssetManager assets;
    private Drawable.DrawRect windowSurface;
    private Board board;
    private Drawable.DrawRect boardSurface;

    private Piece selected;

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

        Piece wQueen = new Piece("Queen", new Drawable.ImageData("Pieces/White/Queen"), new ArrayList<Piece.IntentionVector>(
            java.util.Arrays.asList(slideUp, slideLeft, slideDown, slideRight,slideUpRight,slideUpLeft,slideDownLeft,slideDownRight)));
        
        Piece bQueen = new Piece(wQueen);
        bQueen.setTeam(Piece.Team.P2);
        bQueen.setImage(new Drawable.ImageData("Pieces/Black/Queen"));

        board.setPiece(3, 0, wQueen);
        board.setPiece(3,7, bQueen);

        // detect clicking

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < board.getRows(); i++) {
                    for (int j = 0; j < board.getColumns(); j++) {
                        Drawable.DrawRect region = board.tileToRegion(boardSurface, j, i, assets);
                        if(region == null)
                            continue;
                        if(region.pointInside(e.getPoint().getX(),e.getPoint().getY()))
                        {
                            System.out.println("tile: " + j +", " + i);
                            
                            Piece p = board.getPiece(j,i);
                            
                            if(selected != null)
                                board.tryMove(selected.getPosition(), new Piece.Coordinate(j,i));
                            repaint();
                            
                            selected = p;
                            if(p==null)
                                continue;


                        }
                    }
                }
            }
        });

    }
    private void drawImage(Graphics2D g2d,BufferedImage image, Drawable.DrawRect data)
    {
        if(image == null)
            return;

        g2d.drawImage(image, (int) data.x(), (int) data.y(), (int) data.w(), (int) data.h(), null);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // base class clears the screen before drawing
        
        Graphics2D g2d = (Graphics2D) g;

        windowSurface = new Drawable.DrawRect(0,0,getWidth(),getHeight());

        BufferedImage boardImg = assets.requestImage(board.getImage());
        Drawable.DrawRect boardDimensions = new Drawable.DrawRect(0.5,0.5,1,1);

        boardSurface = board.calculateDrawRect(boardImg,windowSurface, boardDimensions);

        drawImage(g2d, boardImg, boardSurface);

        for (int i=0;i<board.getRows();i++) {
            for (int j=0;j<board.getColumns();j++) {
                Piece p = board.getPiece(j, i);
                if(p == null)
                    continue;
                BufferedImage pieceImg = assets.requestImage(p.getImage());
                Drawable.DrawRect pieceRect = board.tileToRegion(boardSurface, j, i, assets);
                                
                drawImage(g2d, pieceImg, pieceRect);
                System.out.println("Drew: " + j +", " + i);
            }
        }
    }
}
