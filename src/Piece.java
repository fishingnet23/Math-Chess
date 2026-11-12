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
    private Team team = Team.P1;
    private List<IntentionVector> moveData;
    private boolean hasMoved = false;
    private boolean mustBeSafe = false;
    private Coordinate position;

    public Piece(){name=null; moveData = new ArrayList();position = new Coordinate(0, 0);} // default constructor
    public Piece(String name, Drawable.ImageData image, List<IntentionVector> moveData){this.name=name;this.setImage(image); this.moveData=moveData;position = new Coordinate(0, 0);}
    public Piece(Piece other){name=other.name;moveData=other.moveData;this.setImage(other.getImage());position = new Coordinate(0, 0);team=other.team;hasMoved=other.hasMoved;mustBeSafe=other.mustBeSafe;}
    
    public void setName(String name){this.name = name;}
    public void setMovementIntentions(List<IntentionVector> data){moveData=data;}
    public void setTeam(Team t){team=t;}
    public void setHasMoved(boolean val){hasMoved = val;}
    public void shouldAlwaysBeSafe(boolean val){mustBeSafe = val;}
    public void setPosition(Coordinate pos){position=pos;}

    public String getName(){return name;}
    public List<IntentionVector> getMovementIntentions(){return moveData;}
    public Team getTeam(){return team;}
    public boolean hasMoved(){return hasMoved;}
    public boolean mustBeSafe(){return mustBeSafe;}
    public Coordinate getPosition(){return position;}
    
    public void reset()
    {
        hasMoved = false;
    }

}