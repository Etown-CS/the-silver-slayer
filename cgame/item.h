#ifndef ITEM_H
#define ITEM_H
typedef enum 
{
    Unassigned, Junk, Health, Armor, Weapon, Wearable, Equipment
} Type;
typedef struct{
    Type type;
    char name[32], description[100];
    int magnitude,consumeable;
} item;



item* initItem(char* itemName,Type ItemType,char* desc,int statValChange,int consumedOnUse);


#endif