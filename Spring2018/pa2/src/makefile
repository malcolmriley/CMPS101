# Makefile for CMPS 101 Programming Assignment 2

main : main.o apint.o
	gcc -o MAIN main.o apint.o -lm

apint.o : apint.c apint.h
	gcc -c -std=c99 -Wall apint.c apint.h -lm

main.o: main.c
	gcc -c -std=c99 -Wall main.c -lm

clean :
	rm -f *.o *.gch