#include "item.h"
#include <string.h>


typedef enum 
{
    Unassigned, Junk, Health, Armor, Weapon, Wearable
} Type;

typedef struct{
    Type type;
    char name[15], description[100];
    int magnitude,consumeable;
} item;

item* initItem(char* itemName,Type ItemType,char* desc,int statValChange,int consumedOnUse)
{
    item newItem;
    strcpy(newItem.name,itemName);
    strcpy(newItem.description,desc);
    newItem.type=ItemType;
    newItem.consumeable=statValChange;
    newItem.consumeable;
    return &newItem;
}