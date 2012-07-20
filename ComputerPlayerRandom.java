/* 
   Date: 8th of Januar 2012
   version 0.1
   All source under GPL version 3 or latter
   (GNU General Public License - http://www.gnu.org/)
   contact traxplayer@gmail.com for more information about this code
*/

import java.util.ArrayList;

public class ComputerPlayerRandom extends ComputerPlayer
{
    public ComputerPlayerRandom()
    {
      ;
    }

    public String computerMove(NeutronBoard nb) {
      return computerMove(nb,3,false);
    }

    public String computerMove(NeutronBoard nb,int level,boolean pbem) {
      String theMove;
      
      try { 
        theMove=NeutronUtil.getRandomMove (nb); 
      }
      catch (IllegalMoveException e) { 
        e.printStackTrace();
	throw new RuntimeException ("(020)"); 
      }
      return theMove;
    }

    public static void main(String[] args)
    {
      ;
    }

}
