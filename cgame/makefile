run: cave
	./cave
	make clean

cave: cave.o player.o item.o enemy.o story.o
	gcc cave.o player.o item.o enemy.o story.o -o cave

cave.o: cave.c
	gcc -c cave.c

player.o: player.c
	gcc -c player.c

item.o: item.c
	gcc -c item.c

enemy.o: enemy.c
	gcc -c enemy.c

story.o: story.c
	gcc -c story.c

clean:
	rm *.o cave