typedef struct
{
    char name[15];
    int health, attack, defense;
    int healthDefault, attackDefault, defenseDefault;
} enemy;


enemy* createEnemy(char* enemyName,int h,int a,int d);

void enemyChangeStats(enemy* enemy,int h,int a,int d);

int enemyGetAttacked(enemy* enemy, int dmg);
