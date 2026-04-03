#include "player.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
//#include "item.h"



/*typedef struct{
    int health, attack, defense, invCap, healthCap;
    item inventory[20];

} player;*/


player* createPlayer(int *loc,int *area)
{
    player *newChar = malloc(sizeof(player));
    if (!newChar) 
        return 0;
    /* 
    read data in from file to make the character
    */
    //temp data for the game to work
    //printf("here\n");
    FILE *savefile = fopen("tss_save.sav","r");
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

    while(buffer[0]!='L')
        fgets(buffer,sizeof(buffer),savefile);
    *loc=parseInt(buffer,10);
    *area=parseInt(buffer,11+(*loc));

    //printf("area: %d, location %d\n",*loc,*area);
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
        index+=2;
        
        char desk[100];
        int deskindex=0;
        while(buffer[index]!=',')
        {
            desk[deskindex]=buffer[index];
            index++;deskindex++;
            //printf("%d: %c\n",index,buffer[index]);
        }
        desk[deskindex]='\0';
        index+=1;
        //printf("%c",buffer[index]);
        int mag=parseInt(buffer,index);

        while(buffer[index]!=',')
            index++;
        
        index++;
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
    FILE* tempsave=fopen("tss_save.tmp","w+");

    char linein[1024];

    while(fgets(linein,sizeof(linein),savefile))
    {
        if(linein[0]=='*')
        {
            int atk = mc->weapon ? (mc->attack)-(mc->weapon->magnitude) : mc->attack;
            int def = mc->armor ? (mc->defense)-(mc->armor->magnitude) : mc->defense;
            snprintf(linein,sizeof(linein),"*%s: %d/%d, %d/%d, %d/%d, {blind=0, dazed=0, poison=0, strength=0, known=0, doom=0, fire=0, weak=0}\n",mc->name,mc->health,mc->healthCap,atk,atk,def,def);
        }
        else if(linein[0]=='L')
            snprintf(linein,sizeof(linein),"Location: %d/%d, Bits: 0, Swaps: 1, Mountain searches: 0, Mirror moves: 0",locCode,areaCode);
        else if(linein[0]=='[')
        {
            fputs(linein,tempsave);
            for(int i=0;i<mc->currSlot;i++)
            {
                snprintf(linein,sizeof(linein),"{%s,%d,%s,%d,%d}\n",mc->inventory[i]->name,mc->inventory[i]->type,mc->inventory[i]->description,mc->inventory[i]->magnitude,mc->inventory[i]->consumeable);
                fputs(linein,tempsave);
            }
            fgets(linein,sizeof(linein),savefile);
            while(linein[0]!=']')
                fgets(linein,sizeof(linein),savefile);
        }
        fputs(linein,tempsave);
    }

    fclose(savefile);
    fclose(tempsave);

    rename("tss_save.tmp","tss_save.sav");
    
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