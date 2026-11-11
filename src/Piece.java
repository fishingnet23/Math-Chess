import java.util.ArrayList;
import java.util.List;

public class Piece extends Drawable
{
   
    public enum MovementType {Direct,Continuous}
    public enum Team {P1,P2}
    
    public record Coordinate(int x, int y) {}
    public record IntentionVector(
        Coordinate coordinate,
        MovementType moveType,
        boolean destinationMustBeSafe,
        boolean mustBeEmpty, 
        boolean mustBeCapture,
        boolean firstMove,
        boolean friendlyFire,
        boolean ignoreEnemy)
    {
        public IntentionVector(Coordinate coordinate, MovementType moveType) {
            this(coordinate, moveType, false,false,false,false,false,false);
        }
    }

    private String name;
    private Team team;
    private List<IntentionVector> moveData;
    private boolean hasMoved = false;
    private boolean mustBeSafe = false;

    public Piece(){name=null; moveData = new ArrayList();} // default constructor
    public Piece(String name, Drawable.ImageData image, List<IntentionVector> moveData){this.name=name;this.setImage(image); this.moveData=moveData;}
    public Piece(Piece other){name=other.name;moveData=other.moveData;this.setImage(other.getImage());}
    
    public void setName(String name){this.name = name;}
    public void setMovementIntentions(List<IntentionVector> data){moveData=data;}
    public void setTeam(Team t){team=t;}
    public void setHasMoved(boolean val){hasMoved = val;}
    public void shouldAlwaysBeSafe(boolean val){mustBeSafe = val;}

    public String getName(){return name;}
    public List<IntentionVector> getMovementIntentions(){return moveData;}
    public Team getTeam(){return team;}
    public boolean hasMoved(){return hasMoved;}
    public boolean mustBeSafe(){return mustBeSafe;}

    
    public void reset()
    {
        hasMoved = false;
    }

}