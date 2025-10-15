run: cave
	./cave
	make clean

cave: cave.o
	gcc cave.o -o cave

cave.o: cave.c
	gcc -c cave.c

clean:
	rm *.o cave