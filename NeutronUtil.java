/* 
   
   Date: 8th of Januar 2012
   version 0.1
   All source under GPL version 3 or latter 
   (GNU General Public License - http://www.gnu.org/)
   contact traxplayer@gmail.com for more information about this code
   
*/

import java.util.Random;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Collections;

public abstract class NeutronUtil
{

    static boolean DEBUG=false;
    static Random random_generator;
    static
    {
	random_generator = new Random ();
    }
    
    public static int getRandom(int limit) {
	return random_generator.nextInt (limit);
    }
    
    public static String getRandomMove (NeutronBoard t) throws IllegalMoveException {
	String move;
	int losingMoves = 0;

	if (t.isGameOver()!=NeutronBoard.NOPLAYER) {
	    if (DEBUG) {
	      throw new RuntimeException("(010)");
            }
	    return new String ("");
	}
	ArrayList <String> moves=t.uniqueMoves();
        Collections.shuffle(moves);
	if (DEBUG) System.out.println(moves);
	if (moves.size()==1) {
	    return moves.get (0);
	}
	ArrayList <String> moves_not_losing=new ArrayList <String>(moves.size());
	
	for (int i=0; i<moves.size(); i++) {
	    NeutronBoard t_copy=new NeutronBoard(t);
	    t_copy.makeMove(moves.get(i));
	    int gameOverValue =t_copy.isGameOver();
	    switch (gameOverValue) {
	    case NeutronBoard.WHITE:
	    case NeutronBoard.BLACK:
		if (t_copy.whoDidLastMove()==gameOverValue) {
		    if (DEBUG) {
			System.out.println("Winning move found");
		    }
		    return (moves.get(i));	/* Winning move found */
		}
		/* losing move found */
	    losingMoves++;
	    if (DEBUG) {
		System.out.println("Losing move found");
	    }
	    break;
	    case NeutronBoard.NOPLAYER:
		moves_not_losing.add(moves.get(i));
		if (DEBUG) {
		    System.out.println("Not losing move found");
		    System.out.println(moves_not_losing);
		}
		break;
	    default:
		/* This should never happen */
		assert (false);
	    }
	}
	if (moves_not_losing.size()==0) {
	    /* Only losing moves left */
	    if (DEBUG) {
		System.out.println("Only losing moves left");
	    }
            System.out.println(moves);
	    return moves.get (0);
	}
	return moves_not_losing.get(random_generator.nextInt(moves_not_losing.size()));
    }

        public static ArrayList <String> getInput() {
	ArrayList < String > result = new ArrayList < String > (10);
	String line;
	try {
	    line=new BufferedReader (new InputStreamReader (System.in)).readLine
 ();
	    if (line!=null) {
		StringTokenizer st = new StringTokenizer (line);
		while (st.hasMoreTokens ()) result.add (st.nextToken ());
	    }
	}
	catch (IOException e) {
	    e.printStackTrace ();
	}
	return result;
    }

    public static void main(String[] args)
    {
      NeutronBoard nb=new NeutronBoard();

      System.out.println(nb);
      try {
        System.out.println(getRandomMove(nb));
      }
      catch (IllegalMoveException e) {
	throw new RuntimeException();
      }
    }

  public static int evaluation(NeutronBoard nb)
  {
    /* +1000 is win for white
       -1000 is win for black
    */
    int result=0;
    if (nb.isGameOver()!=NeutronBoard.NOPLAYER) {
      return (nb.isGameOver()==NeutronBoard.WHITE?1000:-1000);
    }
    for (int col=1; col<=5; col++) {
      switch (nb.getAt(1,col)) { // Black back-row
 	case NeutronBoard.EMPTY: result-=10; break; 
 	case NeutronBoard.BLACK: result+= 5; break; 
 	case NeutronBoard.WHITE: result+= 8; break; 
        default: throw new RuntimeException("(026) This should never happen.");
	}
      switch (nb.getAt(5,col)) { // White back-row
 	case NeutronBoard.EMPTY: result+=10; break; 
 	case NeutronBoard.WHITE: result-= 5; break; 
 	case NeutronBoard.BLACK: result-= 8; break; 
        default: throw new RuntimeException("(026) This should never happen.");
	}
     }
     if (nb.whoDidLastMove()==NeutronBoard.BLACK) {
       result+=3*nb.NeutronFreeSpaces()+nb.uniqueMoves().size();
     }
     if (nb.whoToMove()==NeutronBoard.WHITE) {
       result-=3*nb.NeutronFreeSpaces()-nb.uniqueMoves().size();
     }
     return result;
   } 
}
