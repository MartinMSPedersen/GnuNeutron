/* 
   Date: 17th of Januar 2012
   version 0.1
   All source under GPL version 3 or latter
   (GNU General Public License - http://www.gnu.org/)
   contact traxplayer@gmail.com for more information about this code
*/

import java.util.ArrayList;

public class ComputerPlayerUct extends ComputerPlayer
{
    public ComputerPlayerUct()
    {
      throw new RuntimeException("Not ready yet.");
    }
     
    public String computerMove(NeutronBoard nb,int level,boolean pbem) {
	return new String();
    }

    public String computerMove(NeutronBoard nb) {
      return computerMove(nb,3,false);
    }
}
