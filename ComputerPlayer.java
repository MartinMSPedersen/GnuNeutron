/* 

Date: 17th of Januar 2012
version 0.1
All source under GPL version 3 or latter
(GNU General Public License - http://www.gnu.org/)
contact traxplayer@gmail.com for more information about this code

*/


public abstract class ComputerPlayer
{
  protected boolean pbem=false;

  public abstract String computerMove (NeutronBoard nb);
  public abstract String computerMove (NeutronBoard nb,int level,boolean pbem);
  public void setPBEM(boolean value) { pbem=value; }
  public boolean getPBEM() { return pbem; }
  public String computerMove (NeutronBoard nb,int level) {
    return computerMove(nb,level,false);
  }

}
