run: compile
	java Project2_COT4521

compile:
	rm -f *.class
	javac *.java

.PHONY: compile run
