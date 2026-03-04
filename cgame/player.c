#include "player.h"
#include <stdlib.h>
//#include "item.h"



/*typedef struct{
    int health, attack, defense, invCap, healthCap;
    item inventory[20];

} player;*/


player* createPlayer()
{
    player *newChar = malloc(sizeof(player));
    if (!newChar) 
        return 0;
    /* 
    read data in from file to make the character
    */
    //temp data for the game to work
    newChar->attack=3;
    newChar->defense=2;
    newChar->health=5;
    newChar->healthCap=10;
    newChar->invCap=10;
    newChar->name="Dapper Python";
    newChar->inventory[0] = initItem("Golden Frying Pan",Weapon,"Epic beyond compare",99,0);
    newChar->inventory[1] = initItem("Brian's Helm",Armor,"Buisness casual, cost effective",3,0);
    newChar->inventory[2] = initItem("Fashionable Sneakers",Wearable,"The most fashonable sneaking attire",2,0);
    newChar->inventory[3] = initItem("Roast Orangeneakers",Junk,"I'm not sure what you do with this",0,1);
    newChar->inventory[4] = initItem("Baked Potato",Health,"A good, but boring dinner",4,1);
    newChar->inventory[5] = initItem("Apple Vision Pro",Unassigned,"Very very expensive",999,1);
    newChar->currSlot=6;
    for(int i=6;i<20;i++)
        newChar->inventory[i]=initItem("",Unassigned,"",0,0);
    newChar->torch=1;
    
   return newChar;
}

void changeStats(player* character,int h,int a,int d)
{
    character->attack+=a;
    character->defense+=d;
    character->health+=h;

    if(character->attack<0) character->attack=0;
    if(character->defense<0) character->defense=0;
}

int getAttacked(player* character,int dmg)
{
    if(dmg-character->defense<1)
    {
        changeStats(character,-1,0,0);
        return 1;
    }
    changeStats(character,-(dmg-character->defense),0,0);
    return dmg-character->defense;
}

void equipWeapon(player* character,int index)
{
    if(character->inventory[index]->type==Weapon)
    {
        character->weapon=character->inventory[index];
    }
}