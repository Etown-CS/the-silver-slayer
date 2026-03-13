#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "player.h"
#include "enemy.h"
#include "story.h"

#define RED     "\x1b[31m"
#define GREEN   "\x1b[32m"
#define YELLOW  "\x1b[33m"
#define BLUE    "\e[1;94m"
#define MAGENTA "\x1b[35m"
#define CYAN    "\x1b[36m"
#define RESET   "\x1b[0m"

#define BKBLACK "\e[40m"
#define BKRED "\e[41m"
#define BKGREEN "\e[42m"
#define BKYELLOW "\e[43m"
#define BKBLUE "\e[44m"
#define BKMAGENTA "\e[45m"
#define BKCYAN "\e[46m"
#define BKWHITE "\e[47m"

#define LITPERCENT 10

void printWaterText(char* inputText,int percentage,int newline);
void printLitText(char* inputText,int newline);
int handleCommand(char* input);
void handleItem(char* idx);
void printSpecialText(char* inputText,int newline);
void unlockLocation(int currAreaCode);
void printInv(player* mc);
void printBattleText(player* mc,enemy * enm);
void roundOfAttack(player* mc,enemy * enm);
void changeLocation(char* identifier);
void stripNewline(char* str);
void changeConsoleText(char* location,char* sublocation);

char consoleText[32];
int waterlvl=1;
player *mainChar;
enemy *hannibal;
location *currentLocation;
int battlemode=0;
int lit=0;

int main()
{
    initStory();
    initLocations();
    currentLocation=&cave;
    char startText[]="The Silver Slayer [c alpha v1.1]\n\n";
    strcpy(consoleText,"Cave/Entryway>");
    char inputText[256];
    printf("\033[2J \033[1;1H");
    printSpecialText(startText,0);
    printSpecialText(getStoryEvent(0),1);
    
    mainChar=createPlayer();
    

    hannibal= createEnemy("Groundhog",10,1,1,None);

    while(waterlvl<=100)
    {
        if(battlemode)
        {
            printBattleText(mainChar,hannibal);
        }

        printSpecialText(consoleText,0);
        fgets(inputText,256,stdin);
        //printf("%s",inputText);
        if(battlemode)
            printf("\033[2J \033[1;1H");
        stripNewline(inputText);
        handleCommand(inputText);
        
        
            
        
        if(!battlemode)
            waterlvl+=2;
        
    }
    handleCommand("clear");
    printSpecialText("You Died",1);
    printSpecialText("Respawn   Main Menu",1);


    return 0;
}

int handleCommand(char* input)
{
    if(!strcmp(input,"exit")||!strcmp(input,"quit"))
        exit(0);
    else if(!strcmp(input,"help"))
        printSpecialText("GENERAL\nexit / quit: Quit the game.\nsettings: Modify game settings\n\nINVENTORY\ndesc / describe: Show an inventory item's description\ndrop (int)+: Drop an item\ninv / inventory / ls: Display inventory\nuse (int)+: Use an inventory item\n\nCOMBAT\natk / attack: Attack the current enemy",1);
    else if(!strcmp(input,"inv")||!strcmp(input,"inventory"))
        printInv(mainChar);
    else if(!strcmp(input,"clear"))
        printf("\033[2J \033[1;1H");
    else if(!strcmp(input,"flee"))
    {
        printSpecialText("You run in discrace",1);
        printf("\033[2J \033[1;1H");
        battlemode=0;
    }
    else if(!strcmp(input,"atk")||!strcmp(input,"attack\n"))
        roundOfAttack(mainChar,hannibal);
    else if(!strcmp(input,"ls")||!strcmp(input,"look\n"))
    {
        printSpecialText(getStoryEvent(currentLocation->level+currentLocation->area+1),1);
        unlockLocation(currentLocation->area);
    }
    else if(!strcmp(input,"search"))
        printSpecialText(getStoryEvent(currentLocation->level+currentLocation->area+2),1);
    else if(strstr(input,"goto")!=NULL||strstr(input,"cd")!=NULL)
    {
        printSpecialText("Where do you want to go?",1);
        fgets(input,256,stdin);
        stripNewline(input);
        changeLocation(input);
    }
    else if(strstr(input,"use")!=NULL)
    {
        printSpecialText("Specify an inventory slot.",1);
        fgets(input,256,stdin);
        stripNewline(input);
        handleItem(input);
    }
    else if(strstr(input,"desc")!=NULL||strstr(input,"description")!=NULL||strstr(input,"desk")!=NULL)
    {
        printSpecialText("Specify an inventory slot.",1);
        fgets(input,256,stdin);
        int idx=input[0]-'0';
        if(idx>mainChar->invCap)
            printSpecialText("You're inventory isn't that big!",1);
        else
        {
            printSpecialText(mainChar->inventory[idx]->name,1);
            printSpecialText(mainChar->inventory[idx]->description,1);
        }
    }
    else if(!strcmp(input,"map"))
        printSpecialText("It's far too dark to see the map, you will have to Look to see what's ahead.",1);
    else if(!strcmp(input,"pickup"))
    {
        
        if(mainChar->currSlot>=mainChar->invCap)
            printSpecialText("Your Inventory is full! You need to drop an item before picking up anything else",1);
        else
        {
            
            if(currentLocation->locItems[currentLocation->area/10]!=NULL)
            {
                printSpecialText("You found a ",0);
                printf(YELLOW"%s"RESET,currentLocation->locItems[currentLocation->area/10]->name);
                printSpecialText(" It, ",0);
                printSpecialText(currentLocation->locItems[currentLocation->area/10]->description,1);
                mainChar->inventory[mainChar->currSlot++]=currentLocation->locItems[currentLocation->area/10];
            }
            else
                printSpecialText("Either there's nothing here or you haven't SEARCHed enough",1);
        }

    }
    else if(!strcmp(input,"torch"))
        {
            if(mainChar->torch)
            {
                lit=!lit;
                if(!lit)
                    printSpecialText("You extinguish your torch.",1);
                
                else
                    printSpecialText("You light your torch.",1);
            }
            else
                printSpecialText("You don't have anything to do that yet.",1);

        }
    else if(!strcmp(input,"swaplvl"))
    {
        if(currentLocation==&cave)
            currentLocation=&mine;
        else
            currentLocation=&cave;
        changeConsoleText(currentLocation->name,currentLocation->sublocations[currentLocation->area/10]);
    }
    else
    {
        printSpecialText("Invalid input: ",0);
        printSpecialText(input,1);
        printSpecialText("Please type help for available commands",1);
    }

    return 0;
}

void printSpecialText(char* inputText,int newline)
{
    if(currentLocation==&cave)
        printWaterText(inputText,waterlvl,newline);
    else
        printLitText(inputText,newline);
}

void printWaterText(char* inputText,int percentage,int newline)
{
    //printLitText(inputText,newline);
    //this method should iterate through the string, and print it out
    int i=0;
    while(inputText[i]!='\0')
    {
        if(((rand()%100))<percentage&&inputText[i]!='\n')
        {
            if(waterlvl>50 && ((rand()%10))-(waterlvl/15)<1)
                printf(BKBLUE CYAN"%c"RESET BKBLACK,inputText[i]);
            else
                printf(BLUE"%c"RESET,inputText[i]);
        }

        else
        {
            if(waterlvl>50 && ((rand()%10))-(waterlvl/30)<1 && inputText[i]!='\n')
                printf(BKBLUE);
            printf("%c",inputText[i]);
            printf(BKBLACK);
        }
        i++;
    }
    if(newline)
        printf("\n");
}

void printLitText(char* inputText,int newline)
{
    if(lit)
    {
        int i=0;
        while(inputText[i]!='\0')
        {
            if((rand()%100)<LITPERCENT)
            {
                printf(BKYELLOW"%c",inputText[i]);
                if(rand()%100<LITPERCENT+20 && inputText[++i]!='\0')
                {
                    printf(RED"%c"RESET,inputText[i]);
                    if(inputText[++i]!='\0')
                        printf(BKYELLOW"%c",inputText[i]);
                }
                printf(BKBLACK);
            }
            else
            {
                printf("%c",inputText[i]);
            }
            i++;
        }
    }
    else
        printf("%s",inputText);
    if(newline)
        printf("\n");
}

void stripNewline(char* str)
{
    int c=0;
    while(str[c]!='\n')//) || str[c]!='\0')
        c++;
    str[c]='\0';
}

void changeLocation(char* identifier)
{
    int locflag=0;
    for(int i=0;i<5;i++)
    {
        if(!strcmp(identifier,currentLocation->sublocations[i]) && currentLocation->accessableLocations[i])
        {
            locflag=1;
            currentLocation->area=i*10;
            changeConsoleText(currentLocation->name,currentLocation->sublocations[i]);
            printSpecialText(getStoryEvent(currentLocation->level+currentLocation->area),1);
            break;
        }
    }
    if(!locflag)
        printSpecialText("You can't go there, if there even exists...",1);
}

void changeConsoleText(char* location,char* sublocation)
{
    snprintf(consoleText,sizeof(consoleText),"%s/%s>",location,sublocation);
}

void handleItem(char* idx)
{
    int index=idx[0]-'0';
    if(index<0)
    {
        char buffer[96];
        snprintf(buffer,sizeof(buffer),"Please put in an numeric number between 0 and %d",mainChar->invCap);
        printSpecialText(buffer,1);
    }

    if(index>mainChar->invCap)
    {
        printSpecialText("You're inventory isn't that big!",1);
        return;
    }
    //printSpecialText(mainChar.inventory[index].name,100,1);

    switch(mainChar->inventory[index]->type)
    {
        case Weapon:
            mainChar->weapon=mainChar->inventory[index];
            printSpecialText("Weapon: ",0);
            printSpecialText(mainChar->weapon->name,0);
            printSpecialText(" Was Equipped",1);
        break;

        case Armor:
            mainChar->armor=mainChar->inventory[index];
            printSpecialText("Armor: ",0);
            printSpecialText(mainChar->armor->name,0);
            printSpecialText(" Was Equipped",1);
        break;

        case Wearable:
            mainChar->clothing=mainChar->inventory[index];
            printSpecialText("Clothing: ",0);
            printSpecialText(mainChar->clothing->name,0);
            printSpecialText(" Was Equipped",1);
        break;

        case Health:
            
            printSpecialText("You chow down, on the ",0);
            printSpecialText(mainChar->inventory[index]->name,0);
            char healthText[40];
            snprintf(healthText,sizeof(healthText)," It's Quite Filling, restoring %d Health",mainChar->inventory[index]->magnitude);
            printSpecialText(healthText,1);
            eatFood(mainChar,mainChar->inventory[index]->magnitude);
            recycleItem(mainChar->inventory[index],"",Unassigned,"",0,0);
        break;

        case Junk:
            printSpecialText("This might be usefull elsewhere but here, ",0);
            printSpecialText(mainChar->inventory[index]->name,0);
            printSpecialText("? That's Junk",1);
        break;

        default:
        printSpecialText("I'm not even sure what you'd do with a ",0);
        printSpecialText(mainChar->inventory[index]->name,1);
    }
}

void unlockLocation(int currAreaCode)
{
    //printf(YELLOW"Something was unlocked: %d\n"RESET,currAreaCode);
    if(currentLocation==&cave)
    {
        switch(currAreaCode){
            case 30:
                currentLocation->accessableLocations[4]=1;
                break;
            case 10:
                currentLocation->accessableLocations[2]=1;
                currentLocation->accessableLocations[3]=1;
            case 0:
                currentLocation->accessableLocations[1]=1;
            default:
            
            break; 
        }
    }
    else
    {
        currentLocation->accessableLocations[(currAreaCode/10)+1]=1;
    }
}

void printInv(player* mc)
{
    //printf("here");
    char buffer[32];
    int buffersize=32;
    
    if(mc->weapon!=NULL)
    {
        printSpecialText("Current Weapon Equipped: ",0);
        printSpecialText(mc->weapon->name,1);
    }
    if(mc->armor!=NULL)
    {
        printSpecialText("Current Armour Equipped: ",0);
        printSpecialText(mc->armor->name,1);
    }
    if(mc->clothing!=NULL)
    {
        printSpecialText("Current Clothing Equipped: ",0);
        printSpecialText(mc->clothing->name,1);
    }
    
    for(int i=0;i<mc->invCap;i++)
    {
        snprintf(buffer,buffersize,"Slot[%d]: %s",i,mc->inventory[i]->name);
        printSpecialText(buffer,1);
    }
    
}

void printBattleText(player* mc,enemy * enm)
{
    char buffer[128];
    int buffersize=128;
    snprintf(buffer,buffersize,"\n%s\nHealth: %d/%d\nAttack: %d\nDefense: %d\n","c--",mc->health,mc->healthCap,mc->attack,mc->defense);
    printSpecialText(buffer,1);
    snprintf(buffer,buffersize,"%s\nHealth: %d/%d\nAttack: %d\nDefense: %d\n",enm->name,enm->health,enm->healthDefault,enm->attack,enm->defense);
    printSpecialText(buffer,1);
}

void roundOfAttack(player* mc,enemy * enm)
{
    //TODO test the battle mode
    int eDamage=enemyGetAttacked(enm,mc->attack);
    char buffer[40];
    snprintf(buffer,sizeof(buffer),"%s took %d Damage!",enm->name,eDamage);
    printSpecialText(buffer,1);
    if(enm->health>0)
    {
        int damage=getAttacked(mc,enm->attack);
        snprintf(buffer,sizeof(buffer),"You took %d Damage, ouch.",damage);
        printSpecialText(buffer,1);
    }
    else
    {
        battlemode=0;
        snprintf(buffer,sizeof(buffer),"You Defeated %s, congrats!",enm->name);
        printSpecialText(buffer,1);
    }
}

void summonEnemy(enemy *enm)
{
    //TODO: create a summoning enemy algorithm
}