run: cave
	./cave
	make clean

cave: cave.o player.o item.o enemy.o
	gcc cave.o player.o item.o enemy.o -o cave

cave.o: cave.c
	gcc -c cave.c

player.o: player.c
	gcc -c player.c

item.o: item.c
	gcc -c item.c

enemy.o: enemy.c
	gcc -c enemy.c

clean:
	rm *.o cave