#include "enemy.h"
#include <string.h>



/*typedef struct
{
    char name[15];
    int health, attack, defense;
    int healthDefault, attackDefault, defenseDefault;
} enemy;*/

enemy* createEnemy(char* enemyName,int h,int a,int d)
{
    enemy newEnemy, *ptr;
    strcpy(newEnemy.name,enemyName);
    newEnemy.health=h;
    newEnemy.healthDefault=h;
    newEnemy.attack=a;
    newEnemy.attackDefault=a;
    newEnemy.defense=d;
    newEnemy.defenseDefault=d;
    ptr=&newEnemy;
    return ptr;
}

void enemyChangeStats(enemy* enemy,int h,int a,int d)
{
    enemy->attack+=a;
    enemy->defense+=d;
    enemy->health+=h;

    if(enemy->attack<0) enemy->attack=0;
    if(enemy->defense<0) enemy->defense=0;
}

int enemyGetAttacked(enemy* enemy, int dmg)
{
    dmg=dmg-enemy->defense;
    if(dmg<1) dmg=1;
    enemyChangeStats(enemy,-dmg,0,0);
    return dmg;
}