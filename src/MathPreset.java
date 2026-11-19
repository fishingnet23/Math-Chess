import java.io.IOException;
import java.util.ArrayList;

public class MathPreset implements GamePreset{

    public void setup(AssetManager assets, Board board)
    {
        Drawable.ImageData boardImg = new Drawable.ImageData("BG/Boards/numberPlane.png");

        Drawable.ImageData wQueenImg = new Drawable.ImageData("Pieces/math1/sigma_x_to_n.png");
        Drawable.ImageData wKingImg = new Drawable.ImageData("Pieces/math1/eix.png");
        Drawable.ImageData wKnightImg = new Drawable.ImageData("Pieces/math1/x_to_the_n.png");
        Drawable.ImageData wBishopImg = new Drawable.ImageData("Pieces/math1/mx_plus_b.png");
        Drawable.ImageData wRookImg = new Drawable.ImageData("Pieces/math1/abs_x.png");
        Drawable.ImageData wPawnImg = new Drawable.ImageData("Pieces/math1/x.png");

        Drawable.ImageData bQueenImg = new Drawable.ImageData("Pieces/math2/sigma_x_to_n.png");
        Drawable.ImageData bKingImg = new Drawable.ImageData("Pieces/math2/eix.png");
        Drawable.ImageData bKnightImg = new Drawable.ImageData("Pieces/math2/x_to_the_n.png");
        Drawable.ImageData bBishopImg = new Drawable.ImageData("Pieces/math2/mx_plus_b.png");
        Drawable.ImageData bRookImg = new Drawable.ImageData("Pieces/math2/abs_x.png");
        Drawable.ImageData bPawnImg = new Drawable.ImageData("Pieces/math2/x.png");

        try
        {
        assets.registerImage(boardImg);

        assets.registerImage(wQueenImg);
        assets.registerImage(wKingImg);
        assets.registerImage(wKnightImg);
        assets.registerImage(wBishopImg);
        assets.registerImage(wRookImg);
        assets.registerImage(wPawnImg);

        assets.registerImage(bQueenImg);
        assets.registerImage(bKingImg);
        assets.registerImage(bKnightImg);
        assets.registerImage(bBishopImg);
        assets.registerImage(bRookImg);
        assets.registerImage(bPawnImg);
        }
        catch(IOException e)
        {
            System.out.println("Invalid image files! " + e);
        }

        board.setImage(boardImg);

        Piece.IntentionVector slideUp = new Piece.IntentionVector(new Piece.Coordinate(0,1),Piece.MovementType.Continuous);
        Piece.IntentionVector slideLeft = new Piece.IntentionVector(new Piece.Coordinate(-1,0),Piece.MovementType.Continuous);
        Piece.IntentionVector slideDown = new Piece.IntentionVector(new Piece.Coordinate(0,-1),Piece.MovementType.Continuous);
        Piece.IntentionVector slideRight = new Piece.IntentionVector(new Piece.Coordinate(1,0),Piece.MovementType.Continuous);
        Piece.IntentionVector slideUpRight = new Piece.IntentionVector(new Piece.Coordinate(1,1),Piece.MovementType.Continuous);
        Piece.IntentionVector slideUpLeft = new Piece.IntentionVector(new Piece.Coordinate(-1,1),Piece.MovementType.Continuous);
        Piece.IntentionVector slideDownRight = new Piece.IntentionVector(new Piece.Coordinate(1,-1),Piece.MovementType.Continuous);
        Piece.IntentionVector slideDownLeft = new Piece.IntentionVector(new Piece.Coordinate(-1,-1),Piece.MovementType.Continuous);
       
        Piece.IntentionVector upOne = new Piece.IntentionVector(new Piece.Coordinate(0, 1), Piece.MovementType.Direct);
        Piece.IntentionVector upOneMustBeEmpty = new Piece.IntentionVector(new Piece.Coordinate(0, 1), Piece.MovementType.Direct,true,false,false,false,false);
        Piece.IntentionVector upTwoMustBeEmptyFirstMove = new Piece.IntentionVector(new Piece.Coordinate(0, 1), Piece.MovementType.FixedStep,true,false,true,false,false,2);


        Piece.IntentionVector leftOne = new Piece.IntentionVector(new Piece.Coordinate(-1, 0), Piece.MovementType.Direct);
        
        Piece.IntentionVector downOne = new Piece.IntentionVector(new Piece.Coordinate(0, -1), Piece.MovementType.Direct);
        Piece.IntentionVector downOneMustBeEmpty = new Piece.IntentionVector(new Piece.Coordinate(0, -1), Piece.MovementType.Direct,true,false,false,false,false);
        Piece.IntentionVector downTwoMustBeEmptyFirstMove = new Piece.IntentionVector(new Piece.Coordinate(0, -1), Piece.MovementType.FixedStep,true,false,true,false,false,2);

        
        Piece.IntentionVector rightOne = new Piece.IntentionVector(new Piece.Coordinate(1, 0), Piece.MovementType.Direct);
        Piece.IntentionVector corner1 = new Piece.IntentionVector(new Piece.Coordinate(1,1), Piece.MovementType.Direct);
        Piece.IntentionVector corner2 = new Piece.IntentionVector(new Piece.Coordinate(1,-1), Piece.MovementType.Direct);
        Piece.IntentionVector corner3 = new Piece.IntentionVector(new Piece.Coordinate(-1,1), Piece.MovementType.Direct);
        Piece.IntentionVector corner4 = new Piece.IntentionVector(new Piece.Coordinate(-1,-1), Piece.MovementType.Direct);

        Piece.IntentionVector upDiagCaptureLeft = new Piece.IntentionVector(new Piece.Coordinate(-1, 1), Piece.MovementType.Direct, false, true, false, false, false);
        Piece.IntentionVector upDiagCaptureRight = new Piece.IntentionVector(new Piece.Coordinate(1, 1), Piece.MovementType.Direct, false, true, false, false, false);

        Piece.IntentionVector downDiagCaptureLeft = new Piece.IntentionVector(new Piece.Coordinate(-1, -1), Piece.MovementType.Direct, false, true, false, false, false);
        Piece.IntentionVector downDiagCaptureRight = new Piece.IntentionVector(new Piece.Coordinate(1, -1), Piece.MovementType.Direct, false, true, false, false, false);

        Piece.IntentionVector LShape1 = new Piece.IntentionVector(new Piece.Coordinate(1,2), Piece.MovementType.Direct);
        Piece.IntentionVector LShape2 = new Piece.IntentionVector(new Piece.Coordinate(2,1), Piece.MovementType.Direct);
        Piece.IntentionVector LShape3 = new Piece.IntentionVector(new Piece.Coordinate(1,-2), Piece.MovementType.Direct);
        Piece.IntentionVector LShape4 = new Piece.IntentionVector(new Piece.Coordinate(-2,1), Piece.MovementType.Direct);
        Piece.IntentionVector LShape5 = new Piece.IntentionVector(new Piece.Coordinate(-1,2), Piece.MovementType.Direct);
        Piece.IntentionVector LShape6 = new Piece.IntentionVector(new Piece.Coordinate(2,-1), Piece.MovementType.Direct);
        Piece.IntentionVector LShape7 = new Piece.IntentionVector(new Piece.Coordinate(-1,-2), Piece.MovementType.Direct);
        Piece.IntentionVector LShape8 = new Piece.IntentionVector(new Piece.Coordinate(-2,-1), Piece.MovementType.Direct);

        

        Piece wQueen = new Piece("Queen",wQueenImg, new ArrayList<Piece.IntentionVector>(
            java.util.Arrays.asList(slideUp, slideLeft, slideDown, slideRight,slideUpRight,slideUpLeft,slideDownLeft,slideDownRight)));
        Piece wRook = new Piece("Rook", wRookImg,new ArrayList<Piece.IntentionVector>(java.util.Arrays.asList(slideUp,slideDown,slideLeft,slideRight)));
        Piece wBishop = new Piece("Bishop", wBishopImg,new ArrayList<Piece.IntentionVector>(java.util.Arrays.asList(slideUpLeft,slideDownLeft,slideUpRight,slideDownRight)));
        Piece wKnight = new Piece("Knight", wKnightImg,new ArrayList<Piece.IntentionVector>(java.util.Arrays.asList(LShape1,LShape2,LShape3,LShape4,LShape5,LShape6,LShape7,LShape8)));
        Piece wKing = new Piece("King",wKingImg, new ArrayList<Piece.IntentionVector>(
            java.util.Arrays.asList(upOne,leftOne,rightOne,downOne,corner1,corner2,corner3,corner4)));
        wKing.shouldAlwaysBeSafe(true);
        
        Piece wPawn = new Piece("Pawn", wPawnImg,new ArrayList<Piece.IntentionVector>(java.util.Arrays.asList(upOneMustBeEmpty,upTwoMustBeEmptyFirstMove,upDiagCaptureRight,upDiagCaptureLeft)));
        Piece bPawn = new Piece("Pawn", bPawnImg,new ArrayList<Piece.IntentionVector>(java.util.Arrays.asList(downOneMustBeEmpty,downTwoMustBeEmptyFirstMove,downDiagCaptureRight,downDiagCaptureLeft)));

        bPawn.setTeam(Piece.Team.P2);

        Piece bQueen = new Piece(wQueen);
        bQueen.setTeam(Piece.Team.P2);
        bQueen.setImage(bQueenImg);

        Piece bRook = new Piece(wRook);
        bRook.setTeam(Piece.Team.P2);
        bRook.setImage(bRookImg);

        Piece bBishop = new Piece(wBishop);
        bBishop.setTeam(Piece.Team.P2);
        bBishop.setImage(bBishopImg);

        Piece bKing = new Piece(wKing);
        bKing.setTeam(Piece.Team.P2);
        bKing.setImage(bKingImg);

        Piece bKnight = new Piece(wKnight);
        bKnight.setTeam(Piece.Team.P2);
        bKnight.setImage(bKnightImg);

        board.setPiece(3, 0, wQueen);
        board.setPiece(3,7, bQueen);

        board.setPiece(0,0 ,wRook);
        board.setPiece(7, 0,wRook);
        board.setPiece(0, 7,bRook);
        board.setPiece(7,7, bRook);

        board.setPiece(2,0, wBishop);
        board.setPiece(5,0, wBishop);
        board.setPiece(2,7, bBishop);
        board.setPiece(5,7, bBishop);

        board.setPiece(4, 0, wKing);
        board.setPiece(4,7, bKing);

        board.setPiece(1,0 ,wKnight);
        board.setPiece(6, 0,wKnight);
        board.setPiece(1, 7,bKnight);
        board.setPiece(6,7, bKnight);

        for(int i=0;i<board.getColumns();i++)
        {
            board.setPiece(i,1, wPawn);
            board.setPiece(i, board.getRows()-2, bPawn);
        }

    }
}
