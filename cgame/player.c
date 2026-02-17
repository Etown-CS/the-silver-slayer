#include "player.h"
//#include "item.h"



/*typedef struct{
    int health, attack, defense, invCap, healthCap;
    item inventory[20];

} player;*/


player* createPlayer()
{
    player joe, *ptr;
    /* 
    read data in from file to make the character
    */
    ptr=&joe;
   return ptr;
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
    if(character->inventory[index].type==Weapon)
    {
        character->weapon=character->inventory[index];
    }
}