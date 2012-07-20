/* 

Date: 8th of Januar 2012
version 0.1
All source under GPL version 3 or latter
(GNU General Public License - http://www.gnu.org/)
contact traxplayer@gmail.com for more information about this code

*/

import java.util.ArrayList;

public class NeutronBoard
{
  // Board description...
  //
  //      A   B   C   D   E
  //    +---+---+---+---+---+
  //  1 | x | x | x | x | x | 1 <- Black's back row
  //    +---+---+---+---+---+
  //  2 |   |   |   |   |   | 2
  //    +---+---+---+---+---+
  //  3 |   |   | * |   |   | 3
  //    +---+---+---+---+---+
  //  4 |   |   |   |   |   | 4
  //    +---+---+---+---+---+
  //  5 | o | o | o | o | o | 5 <- White's back row
  //    +---+---+---+---+---+
  //      A   B   C   D   E
  //
  // x = Black pawn  o = White pawn  * = Neutron

  private int wtm;
  private int[][] board;
  private int gameover;
  private boolean firstMove;
  private int neutron_x,neutron_y;

  public static final int
    EMPTY = 0, INVALID = 7,   
    BLACK = 1, WHITE = 2, NEUTRON = 3,
    NOPLAYER = 4;

  public static final long serialVersionUID = 24312472L;
  public boolean blank (int piece) { return (piece == EMPTY); }

  public NeutronBoard ()
  {
    int i, j;

    wtm = WHITE;
    gameover = NOPLAYER;
    firstMove = true;
    board = new int[7][7];
    for (i=1; i<=5; i++)
      for (j=1; j<=5; j++)
	board[i][j] = EMPTY;
    board[3][3]=NEUTRON;
    for (j=1; j<=5; j++) {
      board[1][j]=BLACK;
      board[5][j]=WHITE;
      board[j][0]=INVALID;
      board[j][6]=INVALID;
      board[0][j]=INVALID;
      board[6][j]=INVALID;
    }
    board[0][0]=INVALID;
    board[0][6]=INVALID;
    board[6][0]=INVALID;
    board[6][6]=INVALID;
   
    neutron_x=3; neutron_y=3;
  }

  public int getAt(int row, int col) { return board[row][col]; }
  public boolean isFirstMove() { return firstMove; }

  public NeutronBoard (NeutronBoard org)
  {
    int i, j;

    wtm = org.wtm;
    firstMove = org.firstMove;
    neutron_x=org.neutron_x; neutron_y=org.neutron_y;
    board = new int[7][7];
    gameover=org.gameover;
    for (i=0; i<7; i++)
	for (j=0; j<7; j++)
	    this.board[i][j] = org.board[i][j];
  }

  public int NeutronFreeSpaces()
  {
     int result=0;
     if (board[neutron_x-1][neutron_y-1]==EMPTY) result++; 
     if (board[neutron_x-1][neutron_y  ]==EMPTY) result++; 
     if (board[neutron_x-1][neutron_y+1]==EMPTY) result++; 
     if (board[neutron_x  ][neutron_y  ]==EMPTY) result++; 
     if (board[neutron_x  ][neutron_y  ]==EMPTY) result++; 
     if (board[neutron_x+1][neutron_y-1]==EMPTY) result++; 
     if (board[neutron_x+1][neutron_y  ]==EMPTY) result++; 
     if (board[neutron_x+1][neutron_y+1]==EMPTY) result++; 
    
     return result;
   }

  private boolean legalMove(int from_x,int from_y,int to_x, int to_y)
  {
     int dir_x, dir_y;
     int dist;
 
     dir_x=(int)Math.signum(to_x-from_x);
     dir_y=(int)Math.signum(to_y-from_y);
     if ((dir_x==0) && (dir_y==0)) return false;
     dist=computeDistance(from_x,from_y,dir_x,dir_y);
     if (dir_x==0) return (dist==Math.abs(to_y-from_y));
     if (dir_y==0) return (dist==Math.abs(to_x-from_x));
     if (Math.abs(to_y-from_y)!=Math.abs(to_x-from_x)) return false;
     return (dist==Math.abs(to_y-from_y));
  }

  private int computeDistance(int x,int y,int dir_x,int dir_y)
  {
    int result=1;
   
    while (board[x+result*dir_x][y+result*dir_y]==EMPTY) {
      result++;
    }
    return result-1;
  }

  private boolean isLeftRightMirror() 
  {
     for (int x=1; x<3; x++) {
       for (int y=1; y<3; y++) {
         if (board[x][y]!=board[x][5-y]) return false;
       }
     }
     return true;
  }
  
  @Override public String toString ()
  {
    StringBuffer result = new StringBuffer (500);
    int i, j;
    String cols = "     A   B   C   D   E\n";
    String space= "   +---+---+---+---+---+\n";
    result.append(cols);
    for (i=1; i<=5; i++) {
      result.append(space);
      result.append(" "+i+" ");
      for (j=1; j<=5; j++) { 
        result.append("| ");
        switch (board[i][j]) {
          case BLACK:   result.append("x "); break;
          case NEUTRON: result.append("* "); break;
          case WHITE:   result.append("o "); break;
          case EMPTY:   result.append("  "); break;
	  default:
            //This should never happen
            assert(false);
        }
      }
      result.append("| "+i);
      if (i==1) result.append(" <- Black's back row");
      if (i==5) result.append(" <- White's back row");
      result.append("\n");
    }
    result.append(space);
    result.append(cols);
    return result.toString ();
  }


/**
 * Try to make the specified move. If it is legal, then
 * update the board.
 * Accepts upper-case and lower-case letters.
 * A2,C4-E2 eg. means move the neutron to A2 and the C4 piece to E2
 * @param  move The move
 */
  public void makeMove (String move) throws IllegalMoveException
  {
    if (gameover != NOPLAYER)
      throw new IllegalMoveException ("Game is over. gameover="+gameover);

    String theMove=move.toUpperCase().replaceAll("[^A-E1-5]","");
    int p_from_x,p_from_y,p_to_x,p_to_y;
    int n_to_x=0,n_to_y=0;
    int n_dir_x,n_dir_y,p_dir_x,p_dir_y;
    int dist;
    if (!firstMove) {
      if (theMove.length()==2) { /* Check if the neutron are moved to the back */
        n_to_y=theMove.charAt(0)-'@'; 
        n_to_x=theMove.charAt(1)-'0'; 
        if ((n_to_x!=1) && (n_to_x!=5)) {
          throw new IllegalMoveException("Illegal move. You must also move a piece.");
        }
        n_dir_x=(int)Math.signum(n_to_x-neutron_x);
        n_dir_y=(int)Math.signum(n_to_y-neutron_y);
        if ((n_dir_x==0) && (n_dir_y==0)) {
  	  throw new IllegalMoveException("Neutron must move.");
        }
        if ((n_dir_x==0) && (n_dir_y==0)) {
	  throw new IllegalMoveException("Neutron must move.");
        }
        if ((n_dir_x*n_dir_y!=0) && (Math.abs(n_to_y-neutron_y)!=Math.abs(n_to_x-neutron_x))) {
          throw new IllegalMoveException("Neutron must move in a straight line or diagonal.");
        }
        dist=computeDistance(neutron_x,neutron_y,n_dir_x,n_dir_y);
        if ((n_dir_x!=0) && (dist!=Math.abs(neutron_x-n_to_x))) {
	    throw new IllegalMoveException("Neutron can't move to that place.");
        }
        if ((n_dir_y!=0) && (dist!=Math.abs(neutron_y-n_to_y))) {
	    throw new IllegalMoveException("Neutron can't move to that place.");
        }
        board[neutron_x][neutron_y]=EMPTY;
        neutron_x=n_to_x; 
        neutron_y=n_to_y;
        board[neutron_x][neutron_y]=NEUTRON;
        firstMove=false;
        switchPlayer ();
        isGameOver ();		// updates the gameOver attribute
        return;
      }
      if (theMove.length()!=6) throw new IllegalMoveException("Nonsense. \""+theMove+"\"");
      n_to_y=theMove.charAt(0)-'@'; 
      n_to_x=theMove.charAt(1)-'0'; 
      p_from_y=theMove.charAt(2)-'@';
      p_from_x=theMove.charAt(3)-'0';
      p_to_y=theMove.charAt(4)-'@';
      p_to_x=theMove.charAt(5)-'0';
      n_dir_x=(int)Math.signum(n_to_x-neutron_x);
      n_dir_y=(int)Math.signum(n_to_y-neutron_y);
      if ((n_dir_x==0) && (n_dir_y==0)) {
	throw new IllegalMoveException("Neutron must move.");
      }
      if ((n_dir_x*n_dir_y!=0) && (Math.abs(n_to_y-neutron_y)!=Math.abs(n_to_x-neutron_x))) {
        throw new IllegalMoveException("Neutron must move in a straight line or diagonal.");
      }
      dist=computeDistance(neutron_x,neutron_y,n_dir_x,n_dir_y);
      if ((n_dir_x!=0) && (dist!=Math.abs(neutron_x-n_to_x))) {
	    throw new IllegalMoveException("Neutron can't move to that place.");
      }
      if ((n_dir_y!=0) && (dist!=Math.abs(neutron_y-n_to_y))) {
	    throw new IllegalMoveException("Neutron can't move to that place.");
      }
    }
    else {
      if (theMove.length()!=4) throw new IllegalMoveException("Nonsense.");
      p_from_y=theMove.charAt(0)-'@';
      p_from_x=theMove.charAt(1)-'0';
      p_to_y=theMove.charAt(2)-'@';
      p_to_x=theMove.charAt(3)-'0';
    } 
    p_dir_x=(int)Math.signum(p_to_x-p_from_x);
    p_dir_y=(int)Math.signum(p_to_y-p_from_y);
    if ((p_dir_x*p_dir_y!=0) && (Math.abs(p_to_y-p_from_y)!=Math.abs(p_to_x-p_from_x))) {
      throw new IllegalMoveException("Piece must move in a straight line or diagonal.");
    }
    if ((p_dir_x==0) && (p_dir_y==0)) {
      throw new IllegalMoveException("Piece must move.");
    }
    if (board[p_from_x][p_from_y]!=whoToMove()) {
      throw new IllegalMoveException("Your piece not at that position.");
    }
    if (!firstMove) {
	board[neutron_x][neutron_y]=EMPTY;
	board[n_to_x][n_to_y]=NEUTRON;
    }
    dist=computeDistance(p_from_x,p_from_y,p_dir_x,p_dir_y);
    if ((p_dir_x!=0) && (dist!=Math.abs(p_from_x-p_to_x))) {
	board[neutron_x][neutron_y]=NEUTRON;
	board[n_to_x][n_to_y]=EMPTY;
        throw new IllegalMoveException("Piece can't move to that place.");
    }
    if ((p_dir_y!=0) && (dist!=Math.abs(p_from_y-p_to_y))) {
	board[neutron_x][neutron_y]=NEUTRON;
	board[n_to_x][n_to_y]=EMPTY;
	throw new IllegalMoveException("Piece can't move to that place.");
    }
    board[p_from_x][p_from_y]=EMPTY;
    board[p_to_x][p_to_y]=whoToMove();
    if (!firstMove) {
	neutron_x=n_to_x;
	neutron_y=n_to_y;
    }

    /* note that switchPlayer() _must_ come before isGameOver() */
    firstMove=false;
    switchPlayer ();
    isGameOver ();		// updates the gameOver attribute
  }


  public int whoToMove() { return wtm; }

  public void switchPlayer ()
  {
    switch (wtm)
      {
      case WHITE:
	wtm = BLACK;
	break;
      case BLACK:
	wtm = WHITE;
	break;
      default:
	/* This should never happen */
	assert (false);
	break;
      }
  }

  private boolean canNeutronMove() 
  {
    for (int x=neutron_x-1; x<=neutron_x+1; x++) {
      for (int y=neutron_y-1; y<=neutron_y+1; y++) {
        if (board[x][y]==EMPTY) return true;
      }
    }
    return false;
  }
 
  public int isGameOver ()
  {
    if (gameover!=NOPLAYER) return gameover; // cached value
    switch (neutron_x) {
      case 1: gameover=BLACK; break;
      case 5: gameover=WHITE; break;
      default:
        if (!canNeutronMove()) {
          gameover=whoDidLastMove();
          break;
        }
        gameover=NOPLAYER; 
    }
    return gameover;
  }


  public ArrayList<String> uniqueMoves()
  {
    // throws away all equal moves
    int p_distance,n_distance;

    if (firstMove) {
      ArrayList<String> Moves = new ArrayList<String>(7);	
      Moves.add(new String("A5-A2"));
      Moves.add(new String("B5-B2"));
      Moves.add(new String("C5-C4"));
      Moves.add(new String("A5-B4"));
      Moves.add(new String("B5-A4"));
      Moves.add(new String("B5-E2"));
      Moves.add(new String("C5-A3"));
      return Moves;
    }
    if (!canNeutronMove()) {
      return new ArrayList<String>(0);
    } 
    ArrayList<String> Moves = new ArrayList<String>(100);	
    char[] AMove=new char[8];
    AMove[2]=',';
    AMove[5]='-';
    for (int n_dir_x=-1; n_dir_x<=1; n_dir_x++) {
      for (int n_dir_y=-1; n_dir_y<=(isLeftRightMirror()?0:1); n_dir_y++) {
        if ((n_dir_x==0) && (n_dir_y==0)) continue;
        for (n_distance=1;board[n_dir_x*n_distance+neutron_x][n_dir_y*n_distance+neutron_y]==EMPTY;n_distance++) { ; }
        n_distance--;
        if (n_distance>0) {
          AMove[0]=(char)('@'+n_dir_y*n_distance+neutron_y);
          AMove[1]=(char)('0'+n_dir_x*n_distance+neutron_x);
          board[neutron_x][neutron_y]=EMPTY;
          board[n_dir_x*n_distance+neutron_x][n_dir_y*n_distance+neutron_y]=NEUTRON;
          for (int x=1; x<=5; x++) {
            for (int y=1; y<=(isLeftRightMirror()?3:5); y++) {
              if (board[x][y]==wtm) {
                AMove[3]=(char)('@'+y);
                AMove[4]=(char)('0'+x);
                for (int p_dir_x=-1; p_dir_x<=1; p_dir_x++) {
                  for (int p_dir_y=-1; p_dir_y<=(isLeftRightMirror()?0:1); p_dir_y++) {
                    if ((p_dir_x==0) && (p_dir_y==0)) continue;
                    for (p_distance=1;board[p_dir_x*p_distance+x][p_dir_y*p_distance+y]==EMPTY;p_distance++) { ; }
                    p_distance--;
                    if (p_distance>0) {
                      // got a legal move
                      AMove[6]=(char)('@'+p_dir_y*p_distance+y);
                      AMove[7]=(char)('0'+p_dir_x*p_distance+x);
                      Moves.add(new String(AMove));
                    } 
                  }
                }
              }
            }
          }
          board[neutron_x][neutron_y]=NEUTRON;
          board[n_dir_x*n_distance+neutron_x][n_dir_y*n_distance+neutron_y]=EMPTY;
        }
      }
    }

    Moves.trimToSize ();
    return Moves;
  }

  public int whoDidLastMove ()
  {
    if (firstMove==true) return NOPLAYER;
    switch (wtm)
      {
      case WHITE: return BLACK;
      case BLACK: return WHITE;
      default:
	// This should never happen 
	assert (false);
	break;
      }
    return 0;			// To make the compiler happy
  }


  // Unit test
  public static void main (String[] args)
  {
    NeutronBoard n;
    ArrayList<String> moves;
    ArrayList<String> aMove;
    boolean result;

    result = true;

    try
    {
      n = new NeutronBoard();
      System.out.println(n);
      moves = n.uniqueMoves ();
      System.out.println(moves.size());
      if (moves.size () != 7) {
	  result = false;
	  System.err.println ("Test 1: FAILED\n");
      } else {
	  System.err.println ("Test 1: OK\n");
      }
      n=new NeutronBoard();
      n.makeMove("c5c4");
      System.out.println("After 1. C5-C4\n"+n);
    }
    catch (Exception e)
    {
      e.printStackTrace ();
      result = false;
    }
    if (result == false) { System.exit (-1); }
    else {
	System.out.println("All tests went OK.");
	System.exit (0);
    }
  }

}
