#ifndef ENEMY_H
#define ENEMY_H

typedef enum 
{
    None, //No ability
    lootPlus, //Gives extra loot, money or something
    spyWare, //"reports" player data
    trojan, //Tampers with the player or items
    replicates, //Replicates the enemy multiple times
    adware, //destorts the user's ui
    skeleTON, //Skeleton boss
    lastProspector, //mine boss
} Ability;
typedef struct
{
    char name[16];
    int health, attack, defense;
    int healthDefault, attackDefault, defenseDefault;
    Ability power;
    
} enemy;


enemy* createEnemy(char* enemyName,int h,int a,int d,Ability ab);

void enemyChangeStats(enemy* enemy,int h,int a,int d);

int enemyGetAttacked(enemy* enemy, int dmg);

enemy* copyEnemy(enemy* enmy);


#endif