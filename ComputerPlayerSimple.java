/* 
Date: 8th of Januar 2012
version 0.1
All source under GPL version 3 or latter
(GNU General Public License - http://www.gnu.org/)
contact traxplayer@gmail.com for more information about this code
*/

import java.util.ArrayList;

public class ComputerPlayerSimple extends ComputerPlayer
{
  public ComputerPlayerSimple()
  {   
     ;
  }   

  public String computerMove(NeutronBoard nb) {
    return computerMove(nb,3,false);
  }
   
  public String computerMove (NeutronBoard nb,int level,boolean pbem)
  {
    int best_value,eval;
    String best_move=new String();
    NeutronBoard nb_copy;
    ArrayList <String> moves=nb.uniqueMoves();

    best_value=2000;
    if (nb.whoToMove()==NeutronBoard.WHITE) best_value=-2000;
    for (String AMove : moves) {
      nb_copy=new NeutronBoard(nb);
      try {
        nb_copy.makeMove(AMove);
      }
      catch (IllegalMoveException e) {
        e.printStackTrace();
        throw new RuntimeException("(027) This should never happen.");
      }
      eval=NeutronUtil.evaluation(nb_copy);
      if ((nb.whoToMove()==NeutronBoard.BLACK) && (eval<best_value)) {
        best_move=AMove;
        best_value=eval;
      }
      if ((nb.whoToMove()==NeutronBoard.WHITE) && (eval>best_value)) {
        best_move=AMove;
        best_value=eval;
      }
    }
    return best_move;
  }
}
