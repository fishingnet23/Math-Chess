import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

public class Board extends Drawable
{
    Piece[][] gameState;
    int r;
    int c;
    public Board(int x,int y, String imagePath)
    {
        r=y;
        c=x;
        this.setImage(new ImageData(imagePath));
        clear();
    }
    public Board(int x, int y)
    {
        r=y;
        c=x;
        clear();
    }

    public void clear()
    {
        gameState = new Piece[r][c];
    }

    public int getRows()
    {
        return r;
    }
    public int getColumns()
    {
        return c;
    }

    public boolean inBounds(int x, int y)
    {
        return y < gameState.length && y>=0 && x<gameState[y].length && x>=0;
    }

    public void setPiece(int x,int y, Piece p)
    {
        if(!inBounds(x, y))
            return;
        gameState[y][x] = new Piece(p); // copy constructor
        gameState[y][x].setPosition(new Piece.Coordinate(x,y));
    }

    public Piece getPiece(int x, int y)
    {
        if(!inBounds(x, y))
            return null;
        
        return gameState[y][x];
    }

    public void setPiece(Piece.Coordinate coordinate, Piece p)
    {
        setPiece(coordinate.x(), coordinate.y(),p);
    }

    public Piece getPiece(Piece.Coordinate coordinate)
    {
        return getPiece(coordinate.x(), coordinate.y());
    }

    private boolean compromisesTeam(Piece toMove,Piece.Coordinate targetPos, Piece.Team ally)
    {
        

        for(int i=0;i<gameState.length;i++)
            for(int j=0;j<gameState[i].length;j++)
            {
                Piece pieceAtPos = getPiece(j,i);
                if(pieceAtPos == null)
                    continue;
                if(!pieceAtPos.mustBeSafe() || pieceAtPos.getTeam() != ally) 
                    continue;
                // only check pieces that must be safe and are on the team
                
                // simulate move
                Piece.Coordinate oldPos = toMove.getPosition();
                Piece atTarget = getPiece(targetPos); // cache in case moving the piece would be a capture

                gameState[oldPos.y()][oldPos.x()] = null;
                gameState[targetPos.y()][targetPos.x()] = toMove;
                toMove.setPosition(targetPos);
                boolean inDanger = inDanger(pieceAtPos);

                // return state to what it was before
                gameState[targetPos.y()][targetPos.x()] = atTarget; // return a captured piece to its old position
                gameState[oldPos.y()][oldPos.x()] = toMove;
                toMove.setPosition(oldPos);

                if(inDanger)
                    return true;
            }

        return false;
    }

    private boolean inDanger(Piece p)
    {
        
        for(int i=0;i<gameState.length;i++)
            for(int j=0;j<gameState[i].length;j++)
            {
                Piece pieceAtPos = getPiece(j,i);
                if(pieceAtPos == null || pieceAtPos == p)
                    continue;

                List<Piece.Coordinate> moves = allAttackedPositions(pieceAtPos);
                for(Piece.Coordinate move:moves)
                    if(move.equals(p.getPosition()))
                        return true;
            }

        return false;
    }

    private List<Piece.Coordinate> allAttackedPositions(Piece p)
    {
        if(p == null)
            return new ArrayList(); // invalid pieces can move nowhere
        
        List<Piece.IntentionVector> pieceIntentions = p.getMovementIntentions(); // ask piece where it wants to go

        List<Piece.Coordinate> validMoves = new ArrayList();

        for(Piece.IntentionVector intent:pieceIntentions)
        {
            tryIntent(p, intent,p.getPosition().x() + intent.coordinate().x(),p.getPosition().y() + intent.coordinate().y(), validMoves, true); 
        }


        return validMoves;
    }

    private void tryIntent(Piece p,Piece.IntentionVector intent, int x, int y, List<Piece.Coordinate> validMovesAccumulator, boolean unsafe)
    {tryIntent(p, intent, x, y, validMovesAccumulator, unsafe,0);}
    private void tryIntent(Piece p,Piece.IntentionVector intent, int x, int y, List<Piece.Coordinate> validMovesAccumulator, boolean unsafe, int currentStep)
    {
        // if the piece doesn't exist, it can't move anywhere
        if(p == null)
        { 
            System.out.println("invalid piece trying to move");
            return;
        }

        // if the position isn't even on the board, don't try going there
        if(!inBounds(x, y))
        {           
            return;
        }
        // if this move must be the first move, and the piece has moved, this move is invalid
        if(intent.firstMove() && p.hasMoved())
        {
            System.out.println("Must be first move!");
            return;
        }
        // inspect the target position
        Piece otherPiece = getPiece(x,y);
        // if the space must be empty but it's not, don't go there
        if(intent.mustBeEmpty() && otherPiece != null)
        {
            System.out.println("Space must be empty!");
            return;
        }
        // can't friendly fire
        if(!intent.friendlyFire() && otherPiece != null && otherPiece.getTeam() == p.getTeam())
        {
            System.out.println("Can't friendly fire!" + p.getTeam() +" "+ otherPiece.getTeam());
            return;
        }

        if(intent.moveType() == Piece.MovementType.FixedStep && intent.maxSteps() <= currentStep)
        {
            System.out.println("Fixed step has ended: " + intent.maxSteps() + " " + currentStep);
            return;
        }
        if(unsafe || !unsafe && (!intent.mustBeCapture() || otherPiece != null && otherPiece.getTeam() != p.getTeam()) && !compromisesTeam(p, new Piece.Coordinate(x, y), p.getTeam()))
            validMovesAccumulator.add(new Piece.Coordinate(x, y));

        // if an enemy has been captured, stop regardless
        if (!intent.ignoreEnemy() && otherPiece != null && otherPiece.getTeam() != p.getTeam()) {
            System.out.println("Enemy has been captured, stopping path");
            return;
        }

        if(intent.moveType() == Piece.MovementType.Direct)
        {
            System.out.println("Direct move, stopping");
            return; // Direct movement means try only once
        }

        if(intent.moveType() == Piece.MovementType.Continuous || intent.moveType() == Piece.MovementType.FixedStep) // Repeat until the position fails the conditions
            tryIntent(p,intent, x + intent.coordinate().x(),y + intent.coordinate().y(), validMovesAccumulator, unsafe, currentStep+1); // recursively travel down the path
    }

    public List<Piece.Coordinate> validMovesOfPiece(Piece p)
    {
        if(p == null)
            return new ArrayList(); // invalid pieces can move nowhere
        
        List<Piece.IntentionVector> pieceIntentions = p.getMovementIntentions(); // ask piece where it wants to go

        List<Piece.Coordinate> validMoves = new ArrayList();

        for(Piece.IntentionVector intent:pieceIntentions)
        {
            tryIntent(p, intent,p.getPosition().x() + intent.coordinate().x(),p.getPosition().y() + intent.coordinate().y(), validMoves, false); 
        }


        return validMoves;
    }
    
    public List<Piece.Coordinate> validMovesOfPiece(Piece.Coordinate currentPosition)
    {
        return validMovesOfPiece(getPiece(currentPosition));
    }

    public boolean tryMove(Piece.Coordinate current,Piece.Coordinate target)
    {

        Piece p = getPiece(current);
        System.out.println("Trying to move:");
        if(p==null)
            return false;
    
        List<Piece.Coordinate> validMoves = validMovesOfPiece(current);
        System.out.println(validMoves);
        boolean thisMoveIsValid = false;
        for(Piece.Coordinate coord:validMoves)
        {   
            if(coord.equals(target))
            {
               
                thisMoveIsValid = true;
                break;
            }
        }
        
        if(!thisMoveIsValid)
            return false;
        System.out.println("Valid move found!");
        // make the move since it's valid 
        // don't care if there's a piece already occupying the new spot, that should have been verified already

        p.setHasMoved(true);
        setPiece(target,p);
        gameState[current.y()][current.x()] = null;

        return true;
    }

    public DrawRect tileToRegion(DrawRect boardSurface, int x, int y, AssetManager assets)
    {

        Drawable.DrawRect pieceRect = calculateDrawRect(boardSurface, new Drawable.DrawRect(x*1.0/c, 1-y*1.0/r, 1.0/c,1.0/r));
        
        // offset to center
        pieceRect = new Drawable.DrawRect(pieceRect.x() + pieceRect.w() / 2,pieceRect.y() - pieceRect.h() / 2,pieceRect.w(),pieceRect.h());
    
        return pieceRect;
    }
}