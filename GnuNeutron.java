/* 
   
   Date: 8th of Januar 2012
   version 0.1
   Copyright Martin Moller Skarbiniks Pedersen
   All source under GPL version 3 or latter
   (GNU General Public License - http://www.gnu.org/)
   contact traxplayer@gmail.com for more information about this code
   
*/
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class GnuNeutron
{

  public GnuNeutron (String computer_algorithme)
  {
    autodisplay = true;
    display = 1;
    computerColour = NeutronBoard.BLACK;
    playerName = "";
    ponder = true;
    nb = new NeutronBoard ();
    if (computer_algorithme.equals("random"))
      {
	cp = new ComputerPlayerRandom ();
      }
    else if (computer_algorithme.equals ("simple"))
      {
	cp = new ComputerPlayerSimple ();
      }
    else if (computer_algorithme.equals ("minimax"))
      {
	cp = new ComputerPlayerMinimax ();
      }
    else if (computer_algorithme.equals ("uct"))
      {
	cp = new ComputerPlayerUct ();
      }
    else if (computer_algorithme.equals("alphabeta"))
	{
        cp=new ComputerPlayerAlphaBeta();
      }
    else {
      cp=new ComputerPlayerAlphaBeta();
    }
    moveHistory=new ArrayList<String>();  
  }

  private boolean analyzeMode, autodisplay, logv, learning;
  private boolean ponder, alarmv;
  private int noise, display, searchDepth, searchTime, computerColour;
  private String playerName;
  private NeutronBoard nb;
  private ComputerPlayer cp;
  private ArrayList<String> moveHistory;
  private void userAnalyze ()
  {
    analyzeMode = true;
    computerColour = NeutronBoard.NOPLAYER;
  }


  void userAnnotate ()
  {;
  }

  void userBack ()
  {;
  }

  void userBench ()
  {;
  }

  private void userBlack ()
  {;
  }

  private void userSetDisplay (ArrayList<String> command)
  {
    ;
  }

  private void userDisplay ()
  {
    System.out.println (nb);
  }

  private void userEdit ()
  {;
  }

  private void userEnd ()
  {;
  }

  private void userExit ()
  {
    goodbye ();
  }

  private void userGo ()
  {
    computerColour = nb.whoToMove ();
  }

  private void userHelp (ArrayList < String > command)
  {
    String topic;

    if (command.size () > 1)
      {
	topic = command.get (1);
      }
    else
      {
	topic = "";
      }
    System.out.println ("");
    if (topic.equals (""))
      {
	System.out.
	  print ("alarm on|off..............turns audible alarm on/off\n");
	System.out.
	  print ("analyze...................analyze a game in progress\n");
	System.out.
	  print ("annotate..................annotate game [help].\n");
	System.out.print ("back n....................undo n moves\n");
	System.out.
	  print ("bench.....................runs performance benchmark.\n");
	System.out.print ("black.....................sets black to move.\n");
	System.out.print ("display...................displays the board\n");
	System.out.
	  print ("display <n>...............sets display options [help]\n");
	System.out.
	  print ("edit......................edit board position. [help]\n");
	System.out.print ("end.......................terminates program.\n");
	System.out.
	  print ("exit......................restores STDIN to keyboard.\n");
	System.out.
	  print
	  ("go........................initiates search (same as move).\n");
	System.out.print ("help [command]............displays help.\n");
	System.out.print ("history...................display game moves.\n");
	System.out.
	  print ("info......................displays program settings.\n");
	System.out.
	  print ("input <filename>..........sets STDIN to <filename>.\n");
	System.out.print ("log on|off................turn logging on/off.\n");
	System.out.print ("more...\n");
	NeutronUtil.getInput ();
	System.out.
	  print
	  ("move......................initiates search (same as go).\n");
	System.out.
	  print ("name......................sets opponent's name.\n");
	System.out.
	  print
	  ("new.......................initialize and start new game.\n");
	System.out.
	  print
	  ("noise n...................no status until n nodes searched.\n");
	System.out.
	  print
	  ("perf.[long]...............times the move generator/make_move.\n");
	System.out.
	  print
	  ("perft.....................tests the move generator/make_move.\n");
	System.out.
	  print ("ponder on|off.............toggle pondering off/on.\n");
	System.out.
	  print
	  ("read <filename>...........read moves in [from <filename>].\n");
	System.out.
	  print
	  ("reada <filename>..........read moves in [from <filename>].\n");
	System.out.
	  print
	  ("                          (appends to current game history.)\n");
	System.out.
	  print ("reset n...................reset game to move n.\n");
	System.out.print ("save <filename>.......saves game.\n");
	System.out.
	  print ("score.....................print evaluation of position.\n");
	System.out.
	  print ("sd n......................sets absolute search depth.\n");
	System.out.
	  print ("show book.................toggle book statistics.\n");
	System.out.
	  print ("st n......................sets absolute search time.\n");
	System.out.
	  print
	  ("test <file> [N]...........test a suite of problems. [help]\n");
	System.out.
	  print ("time......................time controls. [help]\n");
	System.out.
	  print
	  ("trace n...................display search tree below depth n.\n");
	System.out.print ("white.....................sets white to move.\n");
	System.out.println ();
	return;
      }
    if (topic.equals ("analyze"))
      {
	System.out.print ("analyze:\n");
	System.out.
	  print
	  ("the analyze command puts GnuTrax into a mode where it will\n");
	System.out.
	  print ("search forever in the current position. When a move is\n");
	System.out.
	  print ("entered, GnuTrax will make that move, switch sides, and\n");
	System.out.
	  print
	  ("again compute, printing analysis as it searches. You can\n");
	System.out.
	  print ("back up a move by entering \"back\" or you can back up\n");
	System.out.
	  print
	  ("several moves by entering \"back <n>\". Note that <n> is\n");
	System.out.
	  print
	  ("the number of moves, counting each player's move as one.\n");
	System.out.println ();
	return;
      }
    if (topic.equals ("annotate"))
      {
	System.out.print ("annotate:\n");
	System.out.
	  print
	  ("annotate <filename> <b|w|bw> <moves> <margin> <time> [n]\n");
	System.out.
	  print
	  ("where <filename> is the input file with game moves, while the\n");
	System.out.
	  print
	  ("output will be written to filename.can. b/w/bw indicates\n");
	System.out.
	  print
	  ("whether to annotate only the white side (w), the black side (b)\n");
	System.out.
	  print
	  ("or both (bw). <moves> indicates which moves to annotate. A single\n");
	System.out.
	  print
	  ("value says start at the indicated move and go through the\n");
	System.out.
	  print
	  ("entire game. A range (20-30) annotates the given range only.\n");
	System.out.
	  print
	  ("<margin> is the difference between the search value for the\n");
	System.out.
	  print
	  ("move played in the game, and the best move GnuTrax found,\n");
	System.out.
	  print
	  ("before a comment is generated. <time> is the time limit per\n");
	System.out.
	  print
	  ("move in seconds. If the optional \"<n>\" is appended, this\n");
	System.out.
	  print ("produces <n> best moves/scores/PV's, rather than just\n");
	System.out.
	  print
	  ("the very best move. It won't display any move that is worse\n");
	System.out.
	  print
	  ("than the actual game move played, but you can use \"-<n>\" to\n");
	System.out.
	  print
	  ("force GnuTrax to produce <n> PV's regardless of how bad they get.\n");
	System.out.println ();
	return;
      }
    if (topic.equals ("display"))
      {
	System.out.print ("display:\n");
	System.out.
	  print ("display changes   -> display variation when it changes.\n");
	System.out.
	  print
	  ("display extstats  -> display search extension statistics.\n");
	System.out.
	  print ("display hashstats -> display search hashing statistics.\n");
	System.out.
	  print ("display movenum   -> display move numbers in PV.\n");
	System.out.
	  print
	  ("display moves     -> display moves as they are searched.\n");
	System.out.
	  print ("display stats     -> display basic search statistics.\n");
	System.out.print ("display time      -> display time for moves.\n");
	System.out.
	  print
	  ("display variation -> display variation at end of iteration.\n");
	System.out.println ();
	return;
      }
    if (topic.equals ("edit"))
      {
	System.out.println ("edit help: not ready yet.");
	return;
      }
    if (topic.equals ("test"))
      {
	System.out.println ("test help: not ready yet.");
	return;
      }
    if (topic.equals ("time"))
      {
	System.out.print ("time:\n");
	System.out.
	  print
	  ("time is used to set the basic search timing controls. The general\n");
	System.out.print ("form of the command is as follows:\n");
	System.out.println ();
	System.out.
	  print ("      time nmoves/ntime/[nmoves/ntime]/[increment]\n");
	System.out.println ();
	System.out.
	  print
	  ("nmoves/ntime represents a traditional first time control when\n");
	System.out.
	  print
	  ("nmoves is an integer representing the number of moves and ntime\n");
	System.out.
	  print
	  ("is the total time allowed for these moves. The [optional]\n");
	System.out.
	  print
	  ("nmoves/ntime is a traditional secondary time control. Increment\n");
	System.out.
	  print
	  ("is a feature which emulates the fischer clock where <increment>\n");
	System.out.
	  print ("is added to the time left after each move is made.\n");
	System.out.println ();
	System.out.
	  print
	  ("as an alternative, nmoves can be \"sd\" which represents a sudden\n");
	System.out.
	  print
	  ("death time control of the remainder of the game played in ntime.\n");
	System.out.
	  print
	  ("The optional secondary time control can be a sudden-death time\n");
	System.out.print ("control, as in the following example:\n");
	System.out.println ();
	System.out.print ("        time 60/30/sd/30\n");
	System.out.println ();
	System.out.
	  print
	  ("this sets 60 moves in 30 minutes, then game in 30 additional\n");
	System.out.print ("minutes. An increment can be added if desired.\n");
	System.out.println ();
	return;
      }
    System.out.println ("No help found on topic:");
  }


  private void userHistory ()
  {
	if (moveHistory.size()==0) return;
	System.out.println("White\t\tBlack");
	System.out.println("------------\t------------");
	for (int i=0; i<moveHistory.size(); i++) {
          if (i<10) System.out.print(" ");
	  System.out.print((i+1)+". "+moveHistory.get(i));
	  System.out.print("\t");
	  i++;
	  if (i<moveHistory.size()) {
            if (i<10) System.out.print(" ");
	    System.out.print((i+1)+". "+moveHistory.get(i));
	  }
	  System.out.println();
        }
	System.out.println();
  }


  private void userImport (ArrayList < String > command)
  {;
  }
  private void userInfo ()
  {;
  }
  private void userInput (ArrayList < String > command)
  {;
  }
  private void userLoad (ArrayList < String > command)
  {;
  }
  private void userLog (ArrayList < String > command)
  {;
  }
  private void userName (ArrayList < String > command)
  {;
  }
  private void userNew ()
  {
    nb = new NeutronBoard ();
    computerColour = NeutronBoard.BLACK;
    userDisplay();
    moveHistory=new ArrayList<String>();
  }

  private void userPerf (ArrayList < String > command)
  {;
  }
  private void userPerft ()
  {;
  }
  private void userPonder (ArrayList < String > command)
  {;
  }
  private void userRead (ArrayList < String > command)
  {;
  }
  private void userReada (ArrayList < String > command)
  {;
  }
  private void userReset (ArrayList < String > command)
  {;
  }

  private void userSave (ArrayList < String > command)
  {
	String filename=null;
	if (command.size()>1) {
	  filename=command.get(1);
	  try {
	    BufferedWriter bw=new BufferedWriter(new FileWriter(command.get(1)));
	    for (int i=0; i<moveHistory.size(); i++) {
	      bw.write(moveHistory.get(i)+" ");
	    }
	    bw.write("\n");
	    bw.close();
          }
          catch (IOException e) {
	    System.out.println("Error saving - "+e.getMessage());
          }
	}
	else {
	  for (int i=0; i<moveHistory.size(); i++) {
	    System.out.print(moveHistory.get(i)+" ");
	  }
	  System.out.println();
        }
  }

  private void userScore ()
  {;
  }
  private void userSd (ArrayList < String > command)
  {;
  }
  private void userSearch (ArrayList < String > command)
  {;
  }
  private void userSettc (ArrayList < String > command)
  {;
  }

  private void userShow (ArrayList < String > command)
  {
    String topic;

    if (command.size () > 1)
      {
	topic = command.get (1);
      }
    else
      {
	topic = "";
      }

    if (topic.equals ("warranty"))
      {
	System.out.print ("\n        NO WARRANTY\n");
	System.out.
	  print
	  ("BECAUSE THE PROGRAM IS LICENSED FREE OF CHARGE, THERE IS NO WARRANTY\n");
	System.out.
	  print
	  ("FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW.  EXCEPT WHEN\n");
	System.out.
	  print
	  ("OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES\n");
	System.out.
	  print
	  ("PROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED\n");
	System.out.
	  print
	  ("OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF\n");
	System.out.
	  print
	  ("MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.  THE ENTIRE RISK AS\n");
	System.out.
	  print
	  ("TO THE QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU.  SHOULD THE\n");
	System.out.
	  print
	  ("PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY SERVICING,\n");
	System.out.print ("REPAIR OR CORRECTION.\n");
	System.out.println ();
	System.out.
	  print
	  ("IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING\n");
	System.out.
	  print
	  ("WILL ANY COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MAY MODIFY AND/OR\n");
	System.out.
	  print
	  ("REDISTRIBUTE THE PROGRAM AS PERMITTED ABOVE, BE LIABLE TO YOU FOR DAMAGES,\n");
	System.out.
	  print
	  ("INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES ARISING\n");
	System.out.
	  print
	  ("OUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED\n");
	System.out.
	  print
	  ("TO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY\n");
	System.out.
	  print
	  ("YOU OR THIRD PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER\n");
	System.out.
	  print
	  ("PROGRAMS), EVEN IF SUCH HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE\n");
	System.out.println ("POSSIBILITY OF SUCH DAMAGES.\n");
	return;
      }
    if (topic.equals ("conditions"))
      {
	System.out.print ("        GNU GENERAL PUBLIC LICENSE\n");
	System.out.
	  print
	  ("   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION\n");
	System.out.println ();
	System.out.
	  print
	  ("  0. This License applies to any program or other work which contains\n");
	System.out.
	  print
	  ("a notice placed by the copyright holder saying it may be distributed\n");
	System.out.
	  print
	  ("under the terms of this General Public License.  The \"Program\", below,\n");
	System.out.
	  print
	  ("refers to any such program or work, and a \"work based on the Program\"\n");
	System.out.
	  print
	  ("means either the Program or any derivative work under copyright law:\n");
	System.out.
	  print
	  ("that is to say, a work containing the Program or a portion of it,\n");
	System.out.
	  print
	  ("either verbatim or with modifications and/or translated into another\n");
	System.out.
	  print
	  ("language.  (Hereinafter, translation is included without limitation in\n");
	System.out.
	  print
	  ("the term \"modification\".)  Each licensee is addressed as \"you\".\n");
	System.out.println ();
	System.out.
	  print
	  ("Activities other than copying, distribution and modification are not\n");
	System.out.
	  print
	  ("covered by this License; they are outside its scope.  The act of\n");
	System.out.
	  print
	  ("running the Program is not restricted, and the output from the Program\n");
	System.out.
	  print
	  ("is covered only if its contents constitute a work based on the\n");
	System.out.
	  print
	  ("Program (independent of having been made by running the Program).\n");
	System.out.
	  print ("Whether that is true depends on what the Program does.\n");
	System.out.
	  print
	  ("  1. You may copy and distribute verbatim copies of the Program's\n");
	System.out.
	  print
	  ("source code as you receive it, in any medium, provided that you\n");
	System.out.
	  print
	  ("conspicuously and appropriately publish on each copy an appropriate\n");
	System.out.
	  print
	  ("copyright notice and disclaimer of warranty; keep intact all the\n");
	System.out.
	  print
	  ("notices that refer to this License and to the absence of any warranty;\n");
	System.out.println ("more...\n");
	NeutronUtil.getInput ();
	System.out.
	  print
	  ("and give any other recipients of the Program a copy of this License\n");
	System.out.print ("along with the Program.\n");
	System.out.println ();
	System.out.
	  print
	  ("You may charge a fee for the physical act of transferring a copy, and\n");
	System.out.
	  print
	  ("you may at your option offer warranty protection in exchange for a fee.\n");
	System.out.println ();
	System.out.
	  print
	  ("  2. You may modify your copy or copies of the Program or any portion\n");
	System.out.
	  print
	  ("of it, thus forming a work based on the Program, and copy and\n");
	System.out.
	  print
	  ("distribute such modifications or work under the terms of Section 1\n");
	System.out.
	  print
	  ("above, provided that you also meet all of these conditions:\n");
	System.out.println ();
	System.out.
	  print
	  ("    a) You must cause the modified files to carry prominent notices\n");
	System.out.
	  print
	  ("    stating that you changed the files and the date of any change.\n");
	System.out.println ();
	System.out.
	  print
	  ("    b) You must cause any work that you distribute or publish, that in\n");
	System.out.
	  print
	  ("    whole or in part contains or is derived from the Program or any\n");
	System.out.
	  print
	  ("    part thereof, to be licensed as a whole at no charge to all third\n");
	System.out.print ("    parties under the terms of this License.\n");
	System.out.
	  print
	  ("    c) If the modified program normally reads commands interactively\n");
	System.out.
	  print
	  ("    when run, you must cause it, when started running for such\n");
	System.out.
	  print
	  ("    interactive use in the most ordinary way, to print or display an\n");
	System.out.
	  print
	  ("    announcement including an appropriate copyright notice and a\n");
	System.out.
	  print
	  ("    notice that there is no warranty (or else, saying that you provide\n");
	System.out.
	  print
	  ("    a warranty) and that users may redistribute the program under\n");
	System.out.print ("more...\n");
	NeutronUtil.getInput ();
	System.out.
	  print
	  ("    these conditions, and telling the user how to view a copy of this\n");
	System.out.
	  print
	  ("    License.  (Exception: if the Program itself is interactive but\n");
	System.out.
	  print
	  ("    does not normally print such an announcement, your work based on\n");
	System.out.
	  print
	  ("    the Program is not required to print an announcement.)\n");
	System.out.println ();
	System.out.
	  print
	  ("These requirements apply to the modified work as a whole.  If\n");
	System.out.
	  print
	  ("identifiable sections of that work are not derived from the Program,\n");
	System.out.
	  print
	  ("and can be reasonably considered independent and separate works in\n");
	System.out.
	  print
	  ("themselves, then this License, and its terms, do not apply to those\n");
	System.out.
	  print
	  ("sections when you distribute them as separate works.  But when you\n");
	System.out.
	  print
	  ("distribute the same sections as part of a whole which is a work based\n");
	System.out.
	  print
	  ("on the Program, the distribution of the whole must be on the terms of\n");
	System.out.
	  print
	  ("this License, whose permissions for other licensees extend to the\n");
	System.out.
	  print
	  ("entire whole, and thus to each and every part regardless of who wrote it.\n");
	System.out.println ();
	System.out.
	  print
	  ("Thus, it is not the intent of this section to claim rights or contest\n");
	System.out.
	  print
	  ("your rights to work written entirely by you; rather, the intent is to\n");
	System.out.
	  print
	  ("exercise the right to control the distribution of derivative or\n");
	System.out.print ("collective works based on the Program.\n");
	System.out.println ();
	System.out.
	  print
	  ("In addition, mere aggregation of another work not based on the Program\n");
	System.out.
	  print
	  ("with the Program (or with a work based on the Program) on a volume of\n");
	System.out.
	  print
	  ("a storage or distribution medium does not bring the other work under\n");
	System.out.print ("the scope of this License.\n");
	System.out.print ("more...\n");
	NeutronUtil.getInput ();
	System.out.
	  print
	  ("  3. You may copy and distribute the Program (or a work based on it,\n");
	System.out.
	  print
	  ("under Section 2) in object code or executable form under the terms of\n");
	System.out.
	  print
	  ("Sections 1 and 2 above provided that you also do one of the following:\n");
	System.out.println ();
	System.out.
	  print
	  ("    a) Accompany it with the complete corresponding machine-readable\n");
	System.out.
	  print
	  ("    source code, which must be distributed under the terms of Sections\n");
	System.out.
	  print
	  ("    1 and 2 above on a medium customarily used for software interchange; or,\n");
	System.out.println ();
	System.out.
	  print
	  ("    b) Accompany it with a written offer, valid for at least three\n");
	System.out.
	  print
	  ("    years, to give any third party, for a charge no more than your\n");
	System.out.
	  print
	  ("    cost of physically performing source distribution, a complete\n");
	System.out.
	  print
	  ("    machine-readable copy of the corresponding source code, to be\n");
	System.out.
	  print
	  ("    distributed under the terms of Sections 1 and 2 above on a medium\n");
	System.out.
	  print ("    customarily used for software interchange; or,\n");
	System.out.println ();
	System.out.
	  print
	  ("    c) Accompany it with the information you received as to the offer\n");
	System.out.
	  print
	  ("    to distribute corresponding source code.  (This alternative is\n");
	System.out.
	  print
	  ("    allowed only for noncommercial distribution and only if you\n");
	System.out.
	  print
	  ("    received the program in object code or executable form with such\n");
	System.out.
	  print ("    an offer, in accord with Subsection b above.)\n");
	System.out.println ();
	System.out.
	  print
	  ("The source code for a work means the preferred form of the work for\n");
	System.out.
	  print
	  ("making modifications to it.  For an executable work, complete source\n");
	System.out.
	  print
	  ("code means all the source code for all modules it contains, plus any\n");
	System.out.print ("more...\n");
	NeutronUtil.getInput ();
	System.out.
	  print
	  ("associated interface definition files, plus the scripts used to\n");
	System.out.
	  print
	  ("control compilation and installation of the executable.  However, as a\n");
	System.out.
	  print
	  ("special exception, the source code distributed need not include\n");
	System.out.
	  print
	  ("anything that is normally distributed (in either source or binary\n");
	System.out.
	  print
	  ("form) with the major components (compiler, kernel, and so on) of the\n");
	System.out.
	  print
	  ("operating system on which the executable runs, unless that component\n");
	System.out.print ("itself accompanies the executable.\n");
	System.out.println ();
	System.out.
	  print
	  ("If distribution of executable or object code is made by offering\n");
	System.out.
	  print
	  ("access to copy from a designated place, then offering equivalent\n");
	System.out.
	  print
	  ("access to copy the source code from the same place counts as\n");
	System.out.
	  print
	  ("distribution of the source code, even though third parties are not\n");
	System.out.
	  print
	  ("compelled to copy the source along with the object code.\n");
	System.out.println ();
	System.out.
	  print
	  ("  4. You may not copy, modify, sublicense, or distribute the Program\n");
	System.out.
	  print
	  ("except as expressly provided under this License.  Any attempt\n");
	System.out.
	  print
	  ("otherwise to copy, modify, sublicense or distribute the Program is\n");
	System.out.
	  print
	  ("private void, and will automatically terminate your rights under this License.\n");
	System.out.
	  print
	  ("However, parties who have received copies, or rights, from you under\n");
	System.out.
	  print
	  ("this License will not have their licenses terminated so long as such\n");
	System.out.print ("parties remain in full compliance.\n");
	System.out.println ();
	System.out.
	  print
	  ("  5. You are not required to accept this License, since you have not\n");
	System.out.
	  print
	  ("signed it.  However, nothing else grants you permission to modify or\n");
	System.out.
	  print
	  ("distribute the Program or its derivative works.  These actions are\n");
	System.out.print ("more...\n");
	NeutronUtil.getInput ();
	System.out.
	  print
	  ("prohibited by law if you do not accept this License.  Therefore, by\n");
	System.out.
	  print
	  ("modifying or distributing the Program (or any work based on the\n");
	System.out.
	  print
	  ("Program), you indicate your acceptance of this License to do so, and\n");
	System.out.
	  print
	  ("all its terms and conditions for copying, distributing or modifying\n");
	System.out.print ("the Program or works based on it.\n");
	System.out.println ();
	System.out.
	  print
	  ("  6. Each time you redistribute the Program (or any work based on the\n");
	System.out.
	  print
	  ("Program), the recipient automatically receives a license from the\n");
	System.out.
	  print
	  ("original licensor to copy, distribute or modify the Program subject to\n");
	System.out.
	  print
	  ("these terms and conditions.  You may not impose any further\n");
	System.out.
	  print
	  ("restrictions on the recipients' exercise of the rights granted herein.\n");
	System.out.
	  print
	  ("You are not responsible for enforcing compliance by third parties to\n");
	System.out.print ("this License.\n");
	System.out.println ();
	System.out.
	  print
	  ("  7. If, as a consequence of a court judgment or allegation of patent\n");
	System.out.
	  print
	  ("infringement or for any other reason (not limited to patent issues),\n");
	System.out.
	  print
	  ("conditions are imposed on you (whether by court order, agreement or\n");
	System.out.
	  print
	  ("otherwise) that contradict the conditions of this License, they do not\n");
	System.out.
	  print
	  ("excuse you from the conditions of this License.  If you cannot\n");
	System.out.
	  print
	  ("distribute so as to satisfy simultaneously your obligations under this\n");
	System.out.
	  print
	  ("License and any other pertinent obligations, then as a consequence you\n");
	System.out.
	  print
	  ("may not distribute the Program at all.  For example, if a patent\n");
	System.out.
	  print
	  ("license would not permit royalty-free redistribution of the Program by\n");
	System.out.
	  print
	  ("all those who receive copies directly or indirectly through you, then\n");
	System.out.print ("more...\n");
	NeutronUtil.getInput ();
	System.out.
	  print
	  ("the only way you could satisfy both it and this License would be to\n");
	System.out.
	  print ("refrain entirely from distribution of the Program.\n");
	System.out.println ();
	System.out.
	  print
	  ("If any portion of this section is held invalid or unenforceable under\n");
	System.out.
	  print
	  ("any particular circumstance, the balance of the section is intended to\n");
	System.out.
	  print
	  ("apply and the section as a whole is intended to apply in other\n");
	System.out.print ("circumstances.\n");
	System.out.println ();
	System.out.
	  print
	  ("It is not the purpose of this section to induce you to infringe any\n");
	System.out.
	  print
	  ("patents or other property right claims or to contest validity of any\n");
	System.out.
	  print
	  ("such claims; this section has the sole purpose of protecting the\n");
	System.out.
	  print
	  ("integrity of the free software distribution system, which is\n");
	System.out.
	  print
	  ("implemented by public license practices.  Many people have made\n");
	System.out.
	  print
	  ("generous contributions to the wide range of software distributed\n");
	System.out.
	  print
	  ("through that system in reliance on consistent application of that\n");
	System.out.
	  print
	  ("system; it is up to the author/donor to decide if he or she is willing\n");
	System.out.
	  print
	  ("to distribute software through any other system and a licensee cannot\n");
	System.out.print ("impose that choice.\n");
	System.out.println ();
	System.out.
	  print
	  ("This section is intended to make thoroughly clear what is believed to\n");
	System.out.print ("be a consequence of the rest of this License.\n");
	System.out.println ();
	System.out.
	  print
	  ("  8. If the distribution and/or use of the Program is restricted in\n");
	System.out.
	  print
	  ("certain countries either by patents or by copyrighted interfaces, the\n");
	System.out.println ("more...\n");
	NeutronUtil.getInput ();
	System.out.
	  print
	  ("original copyright holder who places the Program under this License\n");
	System.out.
	  print
	  ("may add an explicit geographical distribution limitation excluding\n");
	System.out.
	  print
	  ("those countries, so that distribution is permitted only in or among\n");
	System.out.
	  print
	  ("countries not thus excluded.  In such case, this License incorporates\n");
	System.out.
	  print
	  ("the limitation as if written in the body of this License.\n");
	System.out.println ();
	System.out.
	  print
	  ("  9. The Free Software Foundation may publish revised and/or new versions\n");
	System.out.
	  print
	  ("of the General Public License from time to time.  Such new versions will\n");
	System.out.
	  print
	  ("be similar in spirit to the present version, but may differ in detail to\n");
	System.out.print ("address new problems or concerns.\n");
	System.out.println ();
	System.out.
	  print
	  ("Each version is given a distinguishing version number.  If the Program\n");
	System.out.
	  print
	  ("specifies a version number of this License which applies to it and \"any\n");
	System.out.
	  print
	  ("later version\", you have the option of following the terms and conditions\n");
	System.out.
	  print
	  ("either of that version or of any later version published by the Free\n");
	System.out.
	  print
	  ("Software Foundation.  If the Program does not specify a version number of\n");
	System.out.
	  print
	  ("this License, you may choose any version ever published by the Free Software\n");
	System.out.print ("Foundation.\n");
	System.out.println ();
	System.out.
	  print
	  ("  10. If you wish to incorporate parts of the Program into other free\n");
	System.out.
	  print
	  ("programs whose distribution conditions are different, write to the author\n");
	System.out.
	  print
	  ("to ask for permission.  For software which is copyrighted by the Free\n");
	System.out.
	  print
	  ("Software Foundation, write to the Free Software Foundation; we sometimes\n");
	System.out.
	  print
	  ("make exceptions for this.  Our decision will be guided by the two goals\n");
	System.out.print ("more...\n");
	NeutronUtil.getInput ();
	System.out.
	  print
	  ("of preserving the free status of all derivatives of our free software and\n");
	System.out.
	  println
	  ("of promoting the sharing and reuse of software generally.\n");
      }
  }

  private void userSt (ArrayList < String > command)
  {;
  }
  private void userTest (ArrayList < String > command)
  {;
  }
  private void userTime (ArrayList < String > command)
  {;
  }
  private void userTrace (ArrayList < String > command)
  {;
  }
  private void userWhite ()
  {;
  }

  private static void welcome ()
  {
    System.out.
      print
      ("GnuNeutron is copyright 2011 Martin M. S. Pedersen - email: traxplayer@gmail.com\n");
    System.out.
      print
      ("GnuNeutron comes with ABSOLUTELY NO WARRANTY; for details type \"show warranty\".\n");
    System.out.
      print
      ("This is free software, and you are welcome to redistribute it\n");
    System.out.
      print
      ("under certain conditions; type \"show conditions\" for details.\n");
    System.out.print ("\n");
    System.out.
      print
      ("type \"help\" to get a list of the commands you can use in this program.\n");
    System.out.print ("\n");
    System.out.
      print
      ("    ---===###   GnuNeutron version: 0.1 welcomes you.   ###===---\n");
    System.out.println ();
  }


  private void goodbye ()
  {;
  }

  private void checkForWin ()
  {
    int gameValue;

    gameValue = nb.isGameOver ();
    if (gameValue == NeutronBoard.NOPLAYER)
      return;
    System.out.println("Game over.");
    switch (gameValue)
      {
      case NeutronBoard.WHITE:
	System.out.println ("White won.");
	break;
      case NeutronBoard.BLACK:
	System.out.println ("Black won.");
	break;
      default:
	/* This should never happen */
	/*    assert(0); */
	break;
      }
  }


  private void gotAMove (String theMove)
  {
    try
    {
      nb.makeMove (theMove);
    }
    catch (IllegalMoveException e)
    {
      System.out.println (theMove + ":  " + e);
      return;
    }
    theMove=theMove.toUpperCase().replaceAll("[^A-E1-5]","");
    if (theMove.length()==4) {
      theMove=(new StringBuffer().append(theMove.substring(0,2)).append("-").append(theMove.substring(2,4))).toString();
    }
    if (theMove.length()==6) {
      theMove=(new StringBuffer().append(theMove.substring(0,2)).append(",").append(theMove.substring(2,4)).append("-").append(theMove.substring(4,6))).toString();
    }
    moveHistory.add(theMove);
    System.out.println(nb);
    checkForWin();
  }

  private static int pbem(GnuNeutron gn)
  {
    /* read moves from stdin until eof and then compute and print a move an     d exit. */
   NeutronBoard nb=new NeutronBoard();
   ArrayList<String> moves=NeutronUtil.getInput();
   gn.cp.setPBEM(true);
   try {
     for (String AMove : moves) {
       nb.makeMove(AMove);
     }
   }
   catch (IllegalMoveException e) { 
     System.err.println(e); 
     return 1;
   }
   if (nb.isGameOver()!=NeutronBoard.NOPLAYER) {
     return 0;
   }
   System.out.println(gn.cp.computerMove(nb));
   return 0;
  }
  
  public static void main (String[]args)
  {
    GnuNeutron gnuNeutron = new GnuNeutron("alphabeta");
    boolean pbem=false;

    if (args.length > 0)
      {
	for (int i = 0; i < args.length; i++)
	  {
            if (args[i].equals ("--pbem")) { pbem=true; continue; }
	    if (args[i].equals ("--random")) {
		gnuNeutron = new GnuNeutron ("random"); continue;
	      }
	    if (args[i].equals ("--minimax")) {
		gnuNeutron = new GnuNeutron ("minimax"); continue;
	      }
	    if (args[i].equals ("--alphabeta"))
	      {
		gnuNeutron = new GnuNeutron ("alphabeta"); continue;
	      }
	    if (args[i].equals ("--simple"))
	      {
		gnuNeutron = new GnuNeutron ("simple"); continue;
	      }
	    if (args[i].equals ("--uct"))
	      {
		gnuNeutron = new GnuNeutron ("uct"); continue;
	      }
	  }
      }
   
    if (pbem) System.exit(pbem(gnuNeutron));
    welcome ();
    gnuNeutron.run ();
  }

  private void run ()
  {
    ArrayList < String > command = new ArrayList < String > (5);
    String line = "";

    System.out.println(nb);
    while (true)
      {
	if (nb.whoToMove () == NeutronBoard.WHITE)
	  System.out.print ("White");
	else
	  System.out.print ("Black");
	System.out.print ("(");
	System.out.print(moveHistory.size()+1);
	System.out.print ("): ");

	if ((nb.isGameOver () == NeutronBoard.NOPLAYER)
	    && (nb.whoToMove () == computerColour))
	  {
	    // the computer must give a move
	    System.out.println("Thinking ...");
	    line = cp.computerMove (nb);
	    System.out.println (line);
	    userExit();
	  }
	if ((nb.whoToMove () != computerColour)
	    || (nb.isGameOver () != NeutronBoard.NOPLAYER))
	  {
	    // the human must give a move or a command
	    command = NeutronUtil.getInput ();
	    if (command.size () == 0)
	      continue;		// read more input
	    line = command.get (0);
	  }
	if (line.equals ("analyze"))
	  {
	    userAnalyze ();
	    continue;
	  }
	if (line.equals ("annotate"))
	  {
	    userAnnotate ();
	    continue;
	  }
	if (line.equals ("back"))
	  {
	    userBack ();
	    continue;
	  }
	if (line.equals ("bench"))
	  {
	    userBench ();
	    continue;
	  }
	if (line.equals ("black"))
	  {
	    userBlack ();
	    continue;
	  }
	if (line.equals ("display") || line.equals ("d"))
	  {
	    if (command.size () == 1)
	      {
		userDisplay ();
	      }
	    else
	      {
		userSetDisplay (command);
	      }
	    continue;
	  }
	if (line.equals ("edit"))
	  {
	    userEdit ();
	    continue;
	  }
	if (line.equals ("end"))
	  {
	    userEnd ();
	    continue;
	  }
	if ((line.equals ("exit")) || (line.equals ("quit")))
	  {
	    userExit ();
	    return;
	  }
	if (line.equals ("go"))
	  {
	    userGo ();
	    continue;
	  }
	if (line.equals ("help"))
	  {
	    userHelp (command);
	    continue;
	  }
	if (line.equals ("history") || line.equals ("h"))
	  {
	    userHistory ();
	    continue;
	  }
	if (line.equals ("import"))
	  {
	    userImport (command);
	    continue;
	  }
	if (line.equals ("info"))
	  {
	    userInfo ();
	    continue;
	  }
	if (line.equals ("input"))
	  {
	    userInput (command);
	    continue;
	  }
	if (line.equals ("log"))
	  {
	    userLog (command);
	    continue;
	  }
	if (line.equals ("move"))
	  {
	    userGo ();
	    continue;
	  }
	if (line.equals ("name"))
	  {
	    userName (command);
	    continue;
	  }
	if (line.equals ("new"))
	  {
	    userNew ();
	    continue;
	  }
	if (line.equals ("perf"))
	  {
	    userPerf (command);
	    continue;
	  }
	if (line.equals ("perft"))
	  {
	    userPerft ();
	    continue;
	  }
	if (line.equals ("ponder"))
	  {
	    userPonder (command);
	    continue;
	  }
	if (line.equals ("read"))
	  {
	    userRead (command);
	    continue;
	  }
	if (line.equals ("reada"))
	  {
	    userReada (command);
	    continue;
	  }
	if (line.equals ("reset"))
	  {
	    userReset (command);
	    continue;
	  }
	if (line.equals ("save"))
	  {
	    userSave (command);
	    continue;
	  }
	if (line.equals ("score"))
	  {
	    userScore ();
	    continue;
	  }
	if (line.equals ("sd"))
	  {
	    userSd (command);
	    continue;
	  }
	if (line.equals ("search"))
	  {
	    userSearch (command);
	    continue;
	  }
	if (line.equals ("settc"))
	  {
	    userSettc (command);
	    continue;
	  }
	if (line.equals ("show"))
	  {
	    userShow (command);
	    continue;
	  }
	if (line.equals ("st"))
	  {
	    userSt (command);
	    continue;
	  }
	if (line.equals ("test"))
	  {
	    userTest (command);
	    continue;
	  }
	if (line.equals ("time"))
	  {
	    userTime (command);
	    continue;
	  }
	if (line.equals ("trace"))
	  {
	    userTrace (command);
	    continue;
	  }
	if (line.equals ("white"))
	  {
	    userWhite ();
	    continue;
	  }
	else
	  {
	    if (nb.isGameOver () == NeutronBoard.NOPLAYER)
	      gotAMove (line);
	  }
      }
  }
}
