/* 

Date: 18th of Januar 2012
version 0.1
All source under GPL version 3 or latter
(GNU General Public License - http://www.gnu.org/)
contact traxplayer@gmail.com for more information about this code

*/


public class TranspositionEntry
{
   private NeutronBoard position;
   private int depth,value,valueType;
   public static final int ALPHA_CUT=0, BETA_CUT=1, EXACT=2;

  public TranspositionEntry(NeutronBoard position,int depth, int value, int valueType) {
	this.position=position;
	this.depth=depth;
	this.value=value;
	this.valueType=valueType;
  }
  public NeutronBoard getPosition() { return position; }
  public int getDepth() { return depth; }
  public int getValue() { return value; }
  public int getValueType() { return valueType; }
}
