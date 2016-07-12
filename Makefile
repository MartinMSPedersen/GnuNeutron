# Date: 7th of Januar 2011
# version 0.1
# All source under GPL version 2
# (GNU General Public License - http://www.gnu.org/)
# contact traxplayer@gmail.com for more information about this code

#PATH_TO_JAVAC = /usr/lib/jvm/java-6-sun-1.6.0.10/bin/
# Anders er sej
PATH_TO_JAVAC=
MAIN_OBJECTS = GnuNeutron.java NeutronBoard.java IllegalMoveException.java ComputerPlayerSimple.java ComputerPlayer.java ComputerPlayerAlphaBeta.java ComputerPlayerRandom.java NeutronUtil.java ComputerPlayerUct.java ComputerPlayerMinimax.java TranspositionEntry.java TranspositionTable.java


JAVAC = ${PATH_TO_JAVAC}javac
#JAVAC_FLAGS =  -Xmaxwarns 5 -Xmaxerrs 5 -Xlint:all -source 1.6
#JAVAC_FLAGS =  -Xmaxwarns 5 -Xmaxerrs 5 -Xlint:all -source 1.7
JAVAC_FLAGS =  -Xmaxwarns 5 -Xmaxerrs 5 -Xlint:all 

all:	neutron 

neutron: $(MAIN_OBJECTS)
	$(JAVAC) $(JAVAC_FLAGS) $(MAIN_OBJECTS) && jar cfe neutron.jar GnuNeutron *.class $(MAIN_OBJECTS) Makefile *.txt

.PHONY:	clean tar

tar:
	make clean && cd .. && tar jcvf neutron/neutron.tar.bz2 neutron/* && cp neutron/neutron.tar.bz2 .

clean:
	-/bin/rm -f *.class neutron.tar.bz2 *~ \#* 2>/dev/null
