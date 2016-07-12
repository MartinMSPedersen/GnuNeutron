#!/bin/bash 

JAVA_EXEC="java -server -jar neutron.jar --pbem"
OPENING="$(echo | $JAVA_EXEC)"
MOVES="$OPENING"
START="$(date +%s)"
echo -n "$MOVES "
let MOVE_COUNT=1
while true
do
  let MOVE_COUNT=MOVE_COUNT+1
  MOVE="$(echo "$MOVES" | nice $JAVA_EXEC)" # || exit
  if [ -z "$MOVE" -o "$MOVE" = "RESIGN" -o "$MOVE_COUNT" -gt 20 ] ; then
    echo "$MOVE"
    END=$(date +%s)
    echo $((END-START))
    START=$END
    #OPENING="$(echo | $JAVA_EXEC)"
    MOVES="$OPENING"
    MOVE_COUNT=0
    echo -n "$MOVES "
  else
    MOVES+=" $MOVE"
    echo -n "$MOVE "
  fi
done
   
  



