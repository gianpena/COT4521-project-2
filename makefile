compile:
	rm -f *.class
	javac Project2Framework.java

run: compile
	java Project2Framework

.PHONY: compile run