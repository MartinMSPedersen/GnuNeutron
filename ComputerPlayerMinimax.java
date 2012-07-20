/* 
   Date: 10th of Januar 2012
   version 0.1
   All source under GPL version 3 or latter
   (GNU General Public License - http://www.gnu.org/)
   contact traxplayer@gmail.com for more information about this code
*/

import java.util.ArrayList;

public class ComputerPlayerMinimax extends ComputerPlayer
{
    public ComputerPlayerMinimax()
    {
	System.out.println("Using Minimax");
    }
    
    public String computerMove(NeutronBoard nb) {
      return computerMove(nb,3,false);
    }

    public String computerMove(NeutronBoard nb,int level,boolean pbem) {
	String best_move=new String();
	int value,best_value=3000;
	boolean DEBUG=false;

	NeutronBoard nb_copy;

	if (nb.whoToMove()==NeutronBoard.WHITE) {
	    best_value=-best_value;
	    if (DEBUG) System.out.println("wtm=WHITE");
	}
	else {
	    if (DEBUG) System.out.println("wtm=BLACK");
	}

	for (String AMove : nb.uniqueMoves()) {
	    nb_copy=new NeutronBoard(nb);
	    System.out.print(".");
	    try {
		nb_copy.makeMove(AMove);
	    }
	    catch (IllegalMoveException e) {
		e.printStackTrace();
		throw new RuntimeException("(033) This should never happen.");
	    }
	    value=minimax(nb_copy,level);
	    if (DEBUG) {
		System.err.println(nb_copy+AMove+": "+value);
	    }
	    if ((nb.whoToMove()==NeutronBoard.BLACK) && (value<best_value)) {
		best_value=value;
		best_move=AMove;
		if (best_value==-1000) break;
		if (DEBUG) {
		    System.out.println("wtm=BLACK, best_move="+AMove+" value="+value);
		}
	    }
	    if ((nb.whoToMove()==NeutronBoard.WHITE) && (value>best_value)) {
		best_value=value;
		best_move=AMove;
		if (best_value==1000) break;
		if (DEBUG) {
		    System.out.println("wtm=WHITE, best_move="+AMove+" best_value="+value);
		}

	    }
	}
	System.out.println();
	return best_move;
    }

    private int minimax(NeutronBoard nb, int depth)
    {
	NeutronBoard n_copy;
	int best_value=-3000;

       if (nb.whoToMove()==NeutronBoard.BLACK) {
         best_value=3000;
       }
       if ((depth==0) || (nb.isGameOver()!=NeutronBoard.NOPLAYER)) {
	 return NeutronUtil.evaluation(nb);
       }
       for (String AMove : nb.uniqueMoves()) {
          n_copy=new NeutronBoard(nb);
	  try {
	    n_copy.makeMove(AMove);
	  }
	  catch (IllegalMoveException e) { 
	      e.printStackTrace();
	      throw new RuntimeException("(033) This should never happen.");
	  }
	  if (nb.whoToMove()==NeutronBoard.WHITE) { // The max player
	   
	       best_value=Math.max(best_value,minimax(n_copy,depth-1));
	  }
          else { // The min-player
	       best_value=Math.min(best_value,minimax(n_copy,depth-1));
	  }
       } 
       return best_value;
    }
}
