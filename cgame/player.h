#include "item.h"

typedef struct{
    int health, attack, defense, invCap, healthCap;
    item inventory[20];
    char* name;
    item* weapon;
    item* armor;
    item* clothing;

} player;

void equipWeapon(player* character,int index);
player* createPlayer();