import java.util.ArrayList;
import java.util.List;

public class Board extends Drawable
{
    Piece[][] gameState;

    public Board(int x,int y, String imagePath)
    {
        gameState = new Piece[y][x];
        this.setImage(new ImageData(imagePath));
    }

    public boolean inBounds(int x, int y)
    {
        if(y >= gameState.length || y<0)
            return false;
        return x<gameState[y].length && x>=0;
    }

    public void setPiece(int x,int y, Piece p)
    {
        if(!inBounds(x, y))
            return;
        gameState[y][x] = new Piece(p); // copy constructor
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

    private boolean compromisesTeam(Piece p,int x, int y, Piece.Team ally)
    {
        return false; // temporary implementation, will check if the move compromises a teammate with the "mustBeSafe" property
    }

    private boolean inDanger(Piece p,int x, int y, Piece.Team ally)
    {
        return true; // temporary implementation, will check if any pieces can reach these coordinates next turn
    }
    private void tryIntent(Piece p,Piece.IntentionVector intent, int x, int y, List<Piece.Coordinate> validMovesAccumulator)
    {
        // if the piece doesn't exist, it can't move anywhere
        if(p == null)
            return;

        // if the position isn't even on the board, don't try going there
        if(!inBounds(x, y))
            return;
        // if this move must be the first move, and the piece has moved, this move is invalid
        if(intent.firstMove() && p.hasMoved())
            return;

        // inspect the target position
        Piece otherPiece = getPiece(x,y);

        // if the space must be empty but it's not, don't go there
        if(intent.mustBeEmpty() && otherPiece != null)
            return;

        // can't friendly fire
        if(!intent.friendlyFire() && otherPiece != null && otherPiece.getTeam() == p.getTeam())
            return;

         // if the move can only happen if it captures an enemy and there is no enemy present to capture, don't go there
        if(intent.mustBeCapture() && (otherPiece == null || otherPiece.getTeam() == p.getTeam()))
            return;

        if(intent.destinationMustBeSafe() && inDanger(p, x, y, p.getTeam()) || compromisesTeam(p, x, y, p.getTeam())) // if moving to that position puts the piece/team in danger, don't go there
            return;


        validMovesAccumulator.add(new Piece.Coordinate(x, y));

        // if an enemy has been captured, stop regardless
        if (!intent.ignoreEnemy() && otherPiece != null && otherPiece.getTeam() != p.getTeam()) {
            return; 
        }

        if(intent.moveType() == Piece.MovementType.Direct)
            return; // Direct movement means try only once
        if(intent.moveType() == Piece.MovementType.Continuous) // Repeat until the position fails the conditions
            tryIntent(p,intent, x + intent.coordinate().x(),y + intent.coordinate().y(), validMovesAccumulator); // recursively travel down the path
    }

    public List<Piece.Coordinate> validMovesOfPiece(Piece p,Piece.Coordinate currentPosition)
    {
        if(p == null)
            return new ArrayList(); // invalid pieces can move nowhere
        
        List<Piece.IntentionVector> pieceIntentions = p.getMovementIntentions(); // ask piece where it wants to go

        List<Piece.Coordinate> validMoves = new ArrayList();

        for(Piece.IntentionVector intent:pieceIntentions)
        {
            tryIntent(p, intent,currentPosition.x() + intent.coordinate().x(),currentPosition.y() + intent.coordinate().y(), validMoves); 
        }


        return validMoves;
    }
    
    public List<Piece.Coordinate> validMovesOfPiece(Piece.Coordinate currentPosition)
    {
        return validMovesOfPiece(getPiece(currentPosition),currentPosition);
    }

    public boolean tryMove(Piece.Coordinate current,Piece.Coordinate target)
    {
        Piece p = getPiece(current);
        if(p==null)
            return false;

        List<Piece.Coordinate> validMoves = validMovesOfPiece(current);

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

        // make the move since it's valid 
        // don't care if there's a piece already occupying the new spot, that should have been verified already

        setPiece(target,p);

        return true;
    }

}