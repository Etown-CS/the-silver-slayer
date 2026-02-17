#include "story.h"
/*
Enter cave.
ssh through to mine.
do ddos mine puzzles.
profit.
VUtDOrizBNAy?FKemwO9bWxI9K*5*2t+
*/

char *Story[200];
location mine;
location cave;

void initLocations()
{
    mine.name="Mine";
    mine.sublocations[0]="Enterance";
    mine.sublocations[1]="Maze";
    mine.sublocations[2]="Mineshaft";
    mine.sublocations[3]="Elevator";
    mine.accessableLocations[0]=1;
    mine.accessableLocations[1]=1;

    cave.name="Cave";
    cave.sublocations[0]="Entryway";
    cave.sublocations[1]="Split";
    cave.sublocations[2]="Side Cave";
    cave.sublocations[3]="Tunnels";
    cave.sublocations[4]="Boulders";
    cave.accessableLocations[0]=1;
    cave.accessableLocations[1]=1;

}

void initStory()
{
    //----------------CAVE-----------------//

    //entryway
    Story[0]="You Enter the Caves, It's dripping water, and quite damp in here";
    Story[1]="You Look around, Struggling to see, It's very dark in here.";
    Story[2]="You sqwint your eyes, and see that you are in the mouth of cave system, the way behind you has collapsed, but water is leaking in...";
    
    //entryway [sunk]
    Story[50]="You slosh through the water to the enterance, seeing that the water is pouring in quite intensly";
    Story[51]="Looking around you can barely see anything as the water begins to cover everything";
    Story[52]="Peering at the enterance, you see that the water is coming in more furiously now than before, the caves are filling up fast";

    //split
    Story[10]="You come to a Y in the caves with two smaller caves to go through.";
    Story[11]="The path on the right is long and narrow, you can't see more than a few feet in. The cave on the left is big and echoey, You hear Dripping sounds in it";
    Story[12]="Looking around closer you see a small lantern in between the two paths, it won't do much, but it'll help reveal things that were in the dark";
    Story[13]="you pickup the lantern, it is very dusty, but looks like it has enough use in it to give some light, if you could only find the way to light it";

    //split [sunk]

    Story[60]="You slosh to the cave split, there are two caves on either side and the entryway behind";
    Story[61]="You look around the cave split, you hear the water flowing around the cave at an alarming rate";
    Story[62]="You stare into the inky depths of the water, but you can't see anything on the ground.";

    //side cave
    Story[20]="You walk to the left into the side cave, your steps echo as you walk into the cave.";
    Story[21]="The side cave is very big, stalagtites and stalagmites are everywhere, and there are pools of water around";
    Story[22]="You scrounge around the side cave, until you find [ITEM OF IMPORTANCE]";//TODO FINISH THIS

    //side cave [sunk]

    Story[70]="You slosh left into the side cave, the sound reverberating off the walls";
    Story[71]="The side cave, while very big, is filling up fast with water, the stalagmites are sinking under the water, and it's a miracle you haven't stepped on one";
    Story[72]="You attempt to search the side cave, clumsily moving around when your foot hits somthing making a loud clang";

    //tunnels
    Story[30]="You walk into the right cave, its it barely tall enough to stand in.";
    Story[31]="You look around the area, you can see light coming from somewhere else in the cave";
    Story[32]="Looking further, you find that the light comes from a few stones at the back of the tunnel, the wall there is not solid stone, and there is something on the other side";

    //tunnels [sunk]

    Story[80]="You slosh into the right cave, the water filling it up really makes you feel claustrophobic.";
    Story[81]="You look around the small cave tunnel, the light coming from deeper in reflects strangely on the other side";
    Story[82]="You hear the sound of water flowing out into the caves into somewhere else coming from the end of the tunnel";

    //Ssh puzzle
    Story[40]="You stand before the glowing wall";
    Story[41]="the glowing is definetly behind the wall, and there is a draft coming through it";
    Story[42]="You peek behind the wall, the light does not look like daylight, as it seems to be flickering";

    Story[49]="";

    //Ssh puzzle [sunk]
    Story[90]="";
    Story[91]="";
    Story[92]="the wall isn't really a wall, like you'd think, but a series of bolders, you think if I could only hit it with something hard, it might smash the wall down";

    Story[99]="";



    //------------------------MINE--------------------------//

    //TODO: make a lit text similar to the water text. That will make this part so much cooler

    //mine entrance
    Story[100]="you stumble out of the cave, the water from the caves is running out down into the endless way down into the mineshaft";
    Story[101]="";
    Story[102]="";

    //mine entrance [lit]
    Story[150]="";
    Story[151]="";
    Story[152]="";

    //mine maze
    Story[110]="";
    Story[111]="";
    Story[112]="";

    //mine maze [lit]
    Story[160]="";
    Story[161]="";
    Story[162]="";

    //mineshaft
    Story[120]="";
    Story[121]="";
    Story[122]="";

    //mineshaft [lit]
    Story[170]="";
    Story[171]="";
    Story[172]="";

    //evavator puzzle (ddos)
    Story[130]="";
    Story[131]="";
    Story[132]="";

    //elevator puzzle (ddos) [lit]
    Story[180]="";
    Story[181]="";
    Story[182]="";

    //Exit
    Story[199]="You stumble out of the entryway to the mines, your trusty lantern fizzling out completely, as you leave it by the mine's entrance.";
}


char * getStoryEvent(int event)
{
    return Story[event];
}

