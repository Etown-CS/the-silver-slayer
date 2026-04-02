#include "player.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
//#include "item.h"



/*typedef struct{
    int health, attack, defense, invCap, healthCap;
    item inventory[20];

} player;*/


player* createPlayer()
{
    player *newChar = malloc(sizeof(player));
    if (!newChar) 
        return 0;
    /* 
    read data in from file to make the character
    */
    //temp data for the game to work
    //printf("here\n");
    FILE *savefile = fopen("tss_save.txt","r");
    //printf("the pointer:%p\n",(void*)savefile);
    char buffer[256];
    char inputField[256];
    fgets(buffer,sizeof(buffer),savefile);
    while(buffer[0]!='*')
    {
        fgets(buffer,sizeof(buffer),savefile);
    }
    int i=1;
    
    while(buffer[i]!=':'){
        //printf("%c",buffer[i]);
        inputField[i-1]=buffer[i];
        i++;
    }
        
    inputField[i-1]='\0';
    //printf("%s\n",inputField);
    strcpy(newChar->name,inputField);
    i+=2;
    
    newChar->health=parseInt(buffer,i);
    i+=2;
    newChar->healthCap=parseInt(buffer,i);
    i+=3;
    newChar->attack=parseInt(buffer,i);
    i+=5;
    newChar->defense=parseInt(buffer,i);

    int itemsRead=0;
    while(buffer[0]!='[')
        fgets(buffer,sizeof(buffer),savefile);
    fgets(buffer,sizeof(buffer),savefile);
    do
    {   
        //printf("%s\n",buffer);
        int index=1;
        char name[32];
        while(buffer[index]!=',')
        {
            name[index-1]=buffer[index];
            index++;
        }
        name[index-1]='\0';
        index+=1;
        Type type=parseInt(buffer,index);
        index+=1;
        char desk[100];
        while(buffer[index]!=',')
        {
            desk[index-1]=buffer[index];
            index++;
        }

        index+=1;
        int mag=parseInt(buffer,index);
        index+=1;
        int consumeable=parseInt(buffer,index);
        newChar->inventory[itemsRead++]= initItem(name,type,desk,mag,consumeable);
        fgets(buffer,sizeof(buffer),savefile);
    }while(buffer[0]=='{');
    

    fclose(savefile);
    
    //newChar->attack=3;
    //newChar->defense=2;
    //newChar->health=5;
    //newChar->healthCap=10;
    newChar->invCap=10;
    //strcpy(newChar->name,"Dapper Python");
    // newChar->inventory[0] = initItem("Golden Frying Pan",Weapon,"Epic beyond compare",99,0);
    // newChar->inventory[1] = initItem("Brian's Helm",Armor,"Buisness casual, cost effective",3,0);
    // newChar->inventory[2] = initItem("Fashionable Sneakers",Wearable,"The most fashonable sneaking attire",2,0);
    // newChar->inventory[3] = initItem("Roast Orangeneakers",Junk,"I'm not sure what you do with this",0,1);
    // newChar->inventory[4] = initItem("Baked Potato",Health,"A good, but boring dinner",4,1);
    // newChar->inventory[5] = initItem("Apple Vision Pro",Unassigned,"Very very expensive",999,1);
    newChar->currSlot=itemsRead;
    for(int i=itemsRead;i<20;i++)
        newChar->inventory[i]=initItem("",Unassigned,"",0,0);
    newChar->torch=0;
    
   return newChar;
}

int parseInt(char* strin,int i)
{
    int parsedInt=0;
    while(strin[i]>='0'&&strin[i]<='9')
    {
        parsedInt=parsedInt*10 + (strin[i]-'0');
        i++;
    }
    return parsedInt;
}

void writeSave(player *mc,int locCode,int areaCode)
{
    FILE* savefile=fopen("tss_save.txt","r");

    
}

void changeStats(player* character,int h,int a,int d)
{
    character->attack+=a;
    character->defense+=d;
    character->health+=h;

    if(character->attack<0) character->attack=0;
    if(character->defense<0) character->defense=0;
}

void eatFood(player* character,int health)
{
    changeStats(character,health,0,0);
}

int getAttacked(player* character,int dmg)
{
    if(dmg-character->defense<1)
    {
        changeStats(character,-1,0,0);
        return 1;
    }
    changeStats(character,-(dmg-character->defense),0,0);
    return dmg-character->defense;
}

void equipWeapon(player* character,int index)
{
    if(character->inventory[index]->type==Weapon)
    {
        character->weapon=character->inventory[index];
    }
}