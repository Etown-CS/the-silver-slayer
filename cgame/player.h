#ifndef PLAYER_H
#define PLAYER_H

#include "item.h"

typedef struct{
    int health, attack, defense, invCap, healthCap, currSlot;
    item* inventory[20];
    char name[64];
    item* weapon;
    item* armor;
    item* clothing;
    int torch;

} player;

typedef enum
{
    Fight,Wait,Skip
}attackModes;

void equipWeapon(player* character,int index);
player* createPlayer();
void eatFood(player* character,int health);
int getAttacked(player* character,int dmg);
int parseInt(char* strin,int i);

#endif