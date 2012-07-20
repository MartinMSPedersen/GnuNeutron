/* 
   Date: 17th of Januar 2012
   version 0.1
   All source under GPL version 3 or latter
   (GNU General Public License - http://www.gnu.org/)
   contact traxplayer@gmail.com for more information about this code
*/

import java.util.ArrayList;
import java.util.Collections;

public class ComputerPlayerAlphaBeta extends ComputerPlayer
{
    public ComputerPlayerAlphaBeta()
    { ; }

    public String computerMove(NeutronBoard nb) {
      return computerMove(nb,3,pbem);
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
	ArrayList<String> moves=nb.uniqueMoves();
	Collections.shuffle(moves);
	for (String AMove : moves) {
	    nb_copy=new NeutronBoard(nb);
	    if (!pbem) System.out.print(".");
	    try {
		nb_copy.makeMove(AMove);
	    }
	    catch (IllegalMoveException e) {
		e.printStackTrace();
		throw new RuntimeException("(033) This should never happen.");
	    }
	    value=alphabeta(nb_copy,level,-3000,3000);
	    if (DEBUG) {
		System.err.println(nb_copy+AMove+": "+value);
	    }
	    if ((nb.whoToMove()==NeutronBoard.BLACK) && (value<best_value)) {
		best_value=value;
		best_move=AMove;
		if (DEBUG) {
		    System.out.println("wtm=BLACK, best_move="+AMove+" value="+value);
		}
		if (value==-1000) break; // winning move
	    }
	    if ((nb.whoToMove()==NeutronBoard.WHITE) && (value>best_value)) {
		best_value=value;
		best_move=AMove;
		if (DEBUG) {
		    System.out.println("wtm=WHITE, best_move="+AMove+" best_value="+value);
		}
		if (value==1000) break; // winning move

	    }
	}
	if (!pbem) System.out.println();
	return best_move;
    }

    private int alphabeta(NeutronBoard nb, int depth,int alpha, int beta)
    {
	NeutronBoard n_copy;

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
	       alpha=Math.max(alpha,alphabeta(n_copy,depth-1,alpha,beta));
	       if (beta<=alpha) break; // beta cut-off
	  }
          else { // The min-player
	       beta=Math.min(beta,alphabeta(n_copy,depth-1,alpha,beta));
	       if (beta<=alpha) break; // alpha cut-off
	  }
       } 
       if (nb.whoToMove()==NeutronBoard.WHITE) return alpha;
       return beta;
    }
}
