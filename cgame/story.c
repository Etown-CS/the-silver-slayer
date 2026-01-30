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
    Story[0]="You Enter the Caves, It's dripping water, and quite damp in here";
    Story[1]="You Look around, Struggling to see, It's very dark in here.";
    Story[2]="You sqwint your eyes, and see that you are in the mouth of cave system, the way behind you has collapsed, but water is leaking in...";
    Story[10]="You come to a Y in the caves with two smaller caves to go through.";
    Story[11]="The path on the right is long and narrow, you can't see more than a few feet in. The cave on the left is big and echoey, You hear Dripping sounds in it";
    Story[12]="Looking around closer you see a small lantern in between the two paths, it won't do much, but it'll help reveal things that were in the dark";
    
}

char * getStoryEvent(int event)
{

    





    return Story[event];
}

