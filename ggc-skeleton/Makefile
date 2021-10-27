run: compile execute
    @make -s clean

test: compile
    @echo "--------------------"
    @echo "STARTING TESTS"
    @echo "--------------------"
    @./runtests.sh
    @notify-send "PO-TESTS" "COMPLETE" &> /dev/null
    @make -s clean
clean:
    @echo "--CLEANING--"
    @find . -type f -name '*.class'  -delete
    @find . -type f -name '*.outhyp' -delete
    @find . -type f -name '*.diff'   -delete

compile: clean
    @echo "--COMPILING--"
    @javac -cp po-uilib.jar:. `find ggc -name '*.java'`
    @echo "--DONE--"

execute:
    @echo "--EXECUTING--"
    @java -cp po-uilib.jar:. ggc.app.App
    @echo "--DONE--"

#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'
tests=1
done=0



for x in tests/*.in; do
    if [ -e ${x%.in}.import ]; then
        java -cp :po-uilib.jar:. -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp ggc.app.App;
    else
        java -cp po-uilib.jar:. -Din=$x -Dout=${x%.in}.outhyp ggc.app.App;
    fi

    diff -cB -w ${x%.in}.out ${x%.in}.outhyp > ${x%.in}.diff ;
    if [ -s ${x%.in}.diff ]; then
        printf "${tests}- ${RED}FAIL${NC}: $x \n" ;
    else
        printf "${tests}- ${GREEN}SUCCESS${NC} \n"
    ((done++))
        rm -f ${x%.in}.diff ${x%.in}.outhyp ; 
    fi
    ((tests++))
done

rm -f saved*

((tests--))

echo "------------------------------"
printf "${GREEN}RESULTS:${NC} ${GREEN}${done}/${tests}${NC}\n"
echo "------------------------------"