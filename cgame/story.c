#include "story.h"
/*
Enter cave.
ddos through to mine.
do mine puzzles.
profit.
VUtDOrizBNAy?FKemwO9bWxI9K*5*2t+
*/

void initStory()
{
    //----------------CAVE-----------------//

    //entryway
    Story[0]="You Enter the Caves, It's dripping water, and quite damp in here";
    Story[1]="You Look around, Struggling to see, It's very dark in here.";
    Story[2]="You sqwint your eyes, and see that you are in the mouth of cave system, the way behind you has collapsed, but water is leaking in...";
    
    //entryway [sunk]
    Story[50]="";

    //split
    Story[10]="You come to a Y in the caves with two smaller caves to go through.";
    Story[11]="The path on the right is long and narrow, you can't see more than a few feet in. The cave on the left is big and echoey, You hear Dripping sounds in it";
    Story[12]="Looking around closer you see a small lantern in between the two paths, it won't do much, but it'll help reveal things that were in the dark";
    Story[13]="you pickup the lantern, it is very dusty, but looks like it has enough use in it to give some light, if you could only find the way to light it";

    //split [sunk]

    //side cave
    Story[20]="You walk to the left into the side cave, your steps echo as you walk into the cave.";
    Story[21]="The side cave is very big, stalagtites and stalagmites are everywhere, and there are pools of water around";
    Story[22]="You scrounge around the side cave";//TODO FINISH THIS

    //side cave [sunk]

    //tunnels
    Story[30]="You walk into right cave, its it barely tall enough to stand in.";
    Story[31]="You look around the area, you can see light coming from somewhere else in the cave";
    Story[32]="Looking further, you find that the light comes from a few stones at the back of the tunnel, the wall there is not solid stone, and there is something on the other side";

    //tunnels [sunk]

    //Ddos puzzle
    Story[40]="You stand before the glowing wall";
    Story[41]="the glowing is definetly behind the wall, and there is a draft coming through it";
    Story[42]="You peek behind the wall, the light does not look like daylight, as it seems to be flickering";

    //Ddos puzzle [sunk]
    Story[90]="";
    Story[92]="the wall isn't really a wall, like you'd think, but a series of bolders, you think if I could only hit it with something hard, it might smash the wall down";



    //------------------------MINE--------------------------//

    //TODO: make a lit text similar to the water text. That will make this part so much cooler

    //mine entrance
    Story[100]="you stumble out of the cave, the water running out through the cracks";

    //mine entrance [lit]
    Story[150]="";

    //mine maze

    //mine maze [lit]

    //mineshaft

    //mineshaft [lit]

    //evavator puzzle

    //elevator puzzle [lit]

    //Exit
    Story[200]="You stumble out of the entryway to the mines, your trusty lantern fizzling out completely, as you leave it by the mine's entrance.";


}

char * getStoryEvent(int event)
{

    





    return Story[event];
}

