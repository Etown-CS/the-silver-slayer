#include "story.h"
//#include "item.h"
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
    for(int i=0;i<5;i++)
    {
        mine.sublocations[i]=0;
        cave.sublocations[i]=0;
        mine.accessableItems[i]=0;
        cave.accessableItems[i]=0;
    }
    mine.name="Mine";
    mine.level=100;
    mine.area=0;
    mine.sublocations[0]="Entrance";
    mine.sublocations[1]="Maze";
    mine.sublocations[2]="Mineshaft";
    mine.sublocations[3]="Door";
    mine.sublocations[4]="Elevator";
    mine.accessableLocations[0]=1;
    mine.locItems[3]=initItem("DDOS Drill",Puzzle,"A drill that repeatedly spams packets at the target",0,0);
    mine.locItems[1]=initItem("Shadow File",Puzzle,"This needs to be used with a ripper",0,1);
    mine.mineSubArea=0;

    mine.spawnAbleEnemys[0]=createEnemy("Bug",3,1,0,None);
    mine.spawnAbleEnemys[1]=createEnemy("Dweller",10,4,3,None);
    mine.spawnAbleEnemys[2]=createEnemy("Gobbler",5,3,2,None);
    mine.spawnAbleEnemys[3]=createEnemy("Phantom",3,5,0,None);
    mine.spawnAbleEnemys[4]=createEnemy("Rat",1,3,0,None);
    mine.spawnAbleEnemys[5]=createEnemy("Skeleton",6,2,3,None);
    mine.spawnAbleEnemys[6]=createEnemy("Banshee",9,9,1,adware);
    mine.spawnAbleEnemys[7]=createEnemy("PISMPE",2,0,0,spyWare);
    mine.spawnAbleEnemys[8]=createEnemy("RAT",6,10,0,trojan);
    mine.spawnAbleEnemys[9]=createEnemy("Worm",4,1,5,replicates);
    mine.boss=createEnemy("Last Prospector",45,6,10,lastProspector);
    

    cave.name="Cave";
    cave.level=0;
    cave.mineSubArea=-1;
    cave.sublocations[0]="Entryway";
    cave.sublocations[1]="Split";
    cave.sublocations[2]="Side Cave";
    cave.sublocations[3]="Tunnels";
    cave.sublocations[4]="Boulders";
    cave.accessableLocations[0]=0;
    cave.locItems[2]=initItem("Secure Fossilized Shell",Puzzle,"Encrypts anything you say into it",0,0);
    cave.locItems[3]=initItem("Johnny The Ripper",Puzzle,"Cracks Shadow Files into usernames and passwords for the Secure Fossilized Shell",0,0);
    cave.locItems[4]=initItem("Shadow File",Puzzle,"This needs to be used with a ripper",1,1);

    cave.spawnAbleEnemys[0]=createEnemy("Blue Flower",3,3,0,None);
    cave.spawnAbleEnemys[1]=createEnemy("Bug",3,1,0,None);
    cave.spawnAbleEnemys[2]=createEnemy("Gobbler",5,3,2,None);
    cave.spawnAbleEnemys[3]=createEnemy("Gremlin",4,1,1,None);
    cave.spawnAbleEnemys[4]=createEnemy("Rat",1,3,0,None);
    cave.spawnAbleEnemys[5]=createEnemy("Skeleton",6,2,3,None);
    cave.spawnAbleEnemys[6]=createEnemy("Waterlogged",7,3,4,None);
    cave.spawnAbleEnemys[7]=createEnemy("Scavenger",8,0,0,lootPlus);
    cave.spawnAbleEnemys[8]=createEnemy("PISMPE",2,0,0,spyWare);
    cave.spawnAbleEnemys[9]=createEnemy("RAT",6,10,0,trojan);
    cave.boss=createEnemy("SkeleTON",30,5,5,skeleTON);
    

}

void initStory()
{
    //----------------CAVE-----------------//

    //entryway
    Story[0]="You Enter the Caves, It's dripping water, and quite damp in here";
    Story[1]="You Look around, Struggling to see, It's very dark in here, but you do see a cave SPLIT ahead";
    Story[2]="You sqwint your eyes, and see that you are in the mouth of cave system, the way behind you has collapsed, but water is leaking in...";
    
    //entryway [sunk]
    Story[50]="You slosh through the water to the enterance, seeing that the water is pouring in quite intensly";
    Story[51]="Looking around you can barely see anything as the water begins to cover everything, even the SPLIT ahead is filling up with water";
    Story[52]="Peering at the enterance, you see that the water is coming in more furiously now than before, the caves are filling up fast";

    //split
    Story[10]="You come to a Y in the caves with two smaller caves to go through.";
    Story[11]="The path on the right is a long and narrow set of TUNNELS, you can't see more than a few feet in. The SIDE CAVE on the left is big and echoey, You hear Dripping sounds in it";
    Story[12]="Looking around closer you see a small lantern in between the two paths, it won't do much, but it'll help reveal things that were in the dark";

    //split [sunk]

    Story[60]="You slosh to the cave split, there are two caves on either side and the entryway behind";
    Story[61]="You look around the cave split, you hear the water flowing in the cave at an alarming rate, into both the TUNNELS and the SIDE CAVE";
    Story[62]="You stare into the inky depths of the water, but you can't see anything on the ground.";

    //side cave
    Story[20]="You walk to the left into the side cave, your steps echo as you walk into the cave.";
    Story[21]="The side cave is very big, stalagtites and stalagmites are everywhere, and there are pools of water around";
    Story[22]="You scrounge around the side cave, until you find the SECURE FOSSILIZED SHELL";

    //side cave [sunk]

    Story[70]="You slosh left into the side cave, the sound reverberating off the walls";
    Story[71]="The side cave, while very big, is filling up fast with water, the stalagmites are sinking under the water, and it's a miracle you haven't stepped on one";
    Story[72]="You attempt to search the side cave, clumsily moving around when your foot hits somthing making a loud clang";

    //tunnels
    Story[30]="You walk into the right cave, its it barely tall enough to stand in";
    Story[31]="You look around the area, you can see light coming from somewhere else in the cave behind some BOULDERS";
    Story[32]="Looking around the tunnels, you find JOHNNY THE RIPPER lodged in the wall";

    //tunnels [sunk]

    Story[80]="You slosh into the right cave, the water filling it up really makes you feel claustrophobic";
    Story[81]="You look around the small cave tunnel, the light coming from the BOULDERS reflects strangely on the other side";
    Story[82]="Looking around the tunnels, you see JOHNNY THE RIPPER lodged in the walls just above the waterline";

    //Ssh puzzle
    Story[40]="The boulders have wind blowing through them, and you can hear water rushing on the other side";
    Story[41]="you see a shadowy thing on the boulder, but are unable to make out what it is";
    Story[42]="after close inspection this appears to be a SHADOW FILE";

    Story[49]="You SSH out of the cave into another area...";

    //Ssh puzzle [sunk]
    Story[90]="You hear the water rushing very quickly on the other side.";
    Story[91]="You see a shadowy thing just under the water, but are unsure of what it is";
    Story[92]="After close inspection you see the shadowy thing under the water is a SHADOW FILE";

    Story[99]="You barely escape the cave in time SSHing out as the water fills up past your shoulders.";



    //------------------------MINE--------------------------//

    //mine entrance
    Story[100]="you stumble out of the cave, the water from the caves is running out down into the endless way down into the mineshaft";
    Story[101]="There are wooden beams holding up the mine, you can't see much in either direction";
    Story[102]="You search around the cave, on the ground is a TORCH that is leaning against a beam";

    //mine entrance [lit]
    Story[150]="You see the mine clearly in the light, the water is still running down into the endless shafts";
    Story[151]="You see on the left there is a great expanse of mine tunnels going down into the earth, and on the right there is a MAZE of mineshafts";
    Story[152]="You search around the tunnels with the torch but don't discover anything interesting";

    //mine maze
    Story[110]="You come to the confusing maze of mineshafts, it is very easy to get turned around in here";
    Story[111]="You look around the mineshafts, You will have to CONTINUE deeper until you find a place to go...";
    Story[112]="You attempt to look around the area, but run into a wall, so decide to stop";
    Story[113]="The next room in the maze looks like it was a active mining zone, you think to yourself you should SEARCH before you CONTINUE";
    Story[114]="You find a SHADOW FILE lodged in the wall, Hopefully you remembered Johnny's Ripper";
    Story[115]="You squint your eyes to see that there are mine tunnels LEFT and RIGHT";
    Story[116]="Bumbling around the room in the dark reveals that there is nothing here";
    Story[117]="as your eyes adjust to the light you see a faint light out on one of the tunnels leading to a MINESHAFT";
    Story[118]="You stumble around the room, but are unable to find anything useful, if only you had more light...";
    Story[119]="You SSH and stumble over a rock in the dark as you escape the maze";

    //mine maze [lit]
    Story[160]="You see many tunnels, but you can't see where they go, or anything beyond the torch's light";
    Story[161]="You look at all of the tunnels, and don't see any way out, it looks like you will have to CONTINUE deeper";
    Story[162]="The mineshaft looks like it has been abandoned for many years, old decaying wood, and spiderwebs are common down here";
    Story[163]="This room is shadowy and looks like a active mining place, the torch casts strange shadows on the wall";
    Story[164]="You Search, but see only SHADOWS.";
    Story[165]="The room is uninteresting, there is nothing here except more mineshafts, leading LEFT or RIGHT";
    Story[166]="Even With the light you don't find anything interesting when you search the room";
    Story[167]="You look around but the light of your torch blinds you from seeing the way out";
    Story[168]="You find the DENIAL DRILL OF SERVICE leaning next to the beam.";
    Story[169]="You SSH out of the maze nearly avoiding a rock on the other side";


    //mineshaft
    Story[120]="You hear a rumbling in the Mineshaft as something aproaches you...";
    Story[121]="You Frantically look around in the dark but can't find the way out";
    Story[122]="You hear the ghostly clanging metal of a THE LAST PROSPECTOR";//Trigger boss battle

    //mineshaft [lit]
    Story[170]="You See a large ghostly figure approach you";
    Story[171]="You frantically look around for an escape but THE LAST PROSPECTOR blocks your way";//Trigger boss battle
    Story[172]="You search around the room, but nothing can save you from THE LAST PROSPECTOR";

    //evavator puzzle (ddos)
    Story[130]="";//TBD
    Story[131]="ddos time";
    Story[132]="";

    //elevator puzzle (ddos) [lit]
    Story[180]="";
    Story[181]="ddos but lighter";
    Story[182]="";

    //Exit
    Story[199]="You stumble out of the entryway to the mines, your trusty torch fizzling out completely, as you leave it by the mine's entrance.";
}



char * getStoryEvent(int event)
{
    if(event>110 && event<120 || event>160 && event<170)
    {
        event+=mine.mineSubArea;
    }
    return Story[event];
}

