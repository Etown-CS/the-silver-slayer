typedef struct
{
    char name[15];
    int health, attack, defense;
    int healthDefault, attackDefault, defenseDefault;
} enemy;


enemy* createEnemy(char* enemyName,int h,int a,int d);

void changeStats(enemy* enemy,int h,int a,int d);

int getAttacked(enemy* enemy, int dmg);
