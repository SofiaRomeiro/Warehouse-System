### To run all tests:
		chmod +x runtests.sh
		./runtests.sh 

### To compile:
		javac -cp po-uilib.jar:. `find  -name *.java`

### To run with imported files:
		java -Dimport=tests/<filename>.import ggc.app.App o