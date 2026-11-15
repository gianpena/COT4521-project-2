run: compile
	java Project2Framework

compile:
	rm -f *.class
	javac Project2Framework.java

.PHONY: compile run