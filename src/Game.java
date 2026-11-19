import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel {

    private AssetManager assets;
    private Drawable.DrawRect windowSurface;
    private Board board;
    private Drawable.DrawRect boardSurface;

    private Piece selected;
    private Piece.Team turn;

    public void restart(GamePreset preset)
    {
        board = new Board(8, 8);
        board.clear();
        selected = null;
        turn = Piece.Team.P1;
        preset.setup(assets, board);
        repaint();
    }

    public Game(GamePreset preset) {
        assets = new AssetManager();
        
        restart(preset);
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

                            if(p!=null && p.getTeam() == turn)
                                if(selected == p)
                                    selected = null;
                                else
                                    selected = p;

                            if(selected != null && selected != p)
                            {

                                if(board.tryMove(selected.getPosition(), new Piece.Coordinate(j,i)))
                                    turn = turn == Piece.Team.P1? Piece.Team.P2 : Piece.Team.P1;
                                selected = null;
                            }
                            
                            repaint();

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

        if(selected != null)
        {
            List<Piece.Coordinate> validMoves = board.validMovesOfPiece(selected);
            for(Piece.Coordinate coord:validMoves)
            {
                Drawable.DrawRect tileRect = board.tileToRegion(boardSurface, coord.x(), coord.y(), assets);
                Piece captured = board.getPiece(coord);
                if(captured == null)
                    g2d.setColor(new Color(255,100,100,100));
                else
                    g2d.setColor(new Color(100,255,100,150));
                g2d.fillRect((int)Math.ceil(tileRect.x()), (int)Math.ceil(tileRect.y()), (int)Math.ceil(tileRect.w()), (int)Math.ceil(tileRect.h()));
            }

        }

        for (int i=0;i<board.getRows();i++) {
            for (int j=0;j<board.getColumns();j++) {
                Piece p = board.getPiece(j, i);
                if(p == null)
                    continue;
                BufferedImage pieceImg = assets.requestImage(p.getImage());
                Drawable.DrawRect pieceRect = board.tileToRegion(boardSurface, j, i, assets);
                                
                drawImage(g2d, pieceImg, pieceRect);
                // System.out.println("Drew: " + j +", " + i);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(700, 700);
    }
}
