#include "item.h"
#include <string.h>
#include <stdlib.h>


/*typedef enum 
{
    Unassigned, Junk, Health, Armor, Weapon, Wearable
} Type;*/

/*typedef struct{
    Type type;
    char name[15], description[100];
    int magnitude,consumeable;
} item;*/

item* initItem(char* itemName,Type ItemType,char* desc,int statValChange,int consumedOnUse)
{
    item* newItem=malloc(sizeof(item));
    strcpy(newItem->name,itemName);
    strcpy(newItem->description,desc);
    newItem->type=ItemType;
    newItem->magnitude=statValChange;
    newItem->consumeable=consumedOnUse;
    return newItem;
}

void recycleItem(item* recycled,char* itemName, Type itemType, char* desc,int statValChange,int ConsumedOnUse)
{
    strcpy(recycled->name,itemName);
    strcpy(recycled->description,desc);
    recycled->type=itemType;
    recycled->magnitude=statValChange;
    recycled->consumeable=ConsumedOnUse;
}