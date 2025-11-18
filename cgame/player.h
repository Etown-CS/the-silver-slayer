#include "item.h"

typedef struct{
    int health, attack, defense, invCap, healthCap;
    item inventory[20];

} player;

player* createPlayer();