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

#define LITPERCENT 30

void printWaterText(char* inputText,int percentage,int newline);
void printLitText(char* inputText,int newline);
int handleCommand(char* input);
void printInv(player* mc);
void printBattleText(player* mc,enemy * enm);
void roundOfAttack(player* mc,enemy * enm);
void changeLocation(char* identifier);
void stripNewline(char* str);

char consoleText[32];
int waterlvl=1;
player mainChar;
enemy hannibal;
location currentLocation;
int battlemode=0;
int level=0;
int area=0;
int lit=0;

int main()
{
    initStory();
    initLocations();
    currentLocation=cave;
    char startText[]="The Silver Slayer [c alpha v1.1]\n\n";
    strcpy(consoleText,"Cave/Entryway>");
    char inputText[256];
    printf("\033[2J \033[1;1H");
    printWaterText(startText,waterlvl,0);
    printWaterText(getStoryEvent(0),waterlvl,1);
    
    mainChar.attack=3;
    mainChar.defense=2;
    mainChar.health=5;
    mainChar.healthCap=10;
    mainChar.invCap=10;
    mainChar.name="Dapper Python";
    mainChar.inventory[0] = initItem("Golden Frying Pan",Weapon,"Epic beyond compare",99,0);
    hannibal= * createEnemy("Groundhog",10,1,1);

    while(1)
    {
        if(battlemode)
        {
            printBattleText(&mainChar,&hannibal);
        }
        printWaterText(consoleText,waterlvl,0);
        fgets(inputText,256,stdin);
        //printf("%s",inputText);
        if(battlemode)
            printf("\033[2J \033[1;1H");
        stripNewline(inputText);
        handleCommand(inputText);
        
        if(waterlvl>100)
        {
            handleCommand("clear\n");
            printWaterText("You Died",waterlvl,1);
            printWaterText("Respawn   Main Menu",waterlvl,1);
            break;
        }
        if(!battlemode)
            waterlvl+=2;
        
    }


    return 0;
}

int handleCommand(char* input)
{
    if(!strcmp(input,"exit")||!strcmp(input,"quit"))
        exit(0);
    else if(!strcmp(input,"help"))
        printWaterText("GENERAL\nexit / quit: Quit the game.\nsettings: Modify game settings\n\nINVENTORY\ndesc / describe: Show an inventory item's description\ndrop (int)+: Drop an item\ninv / inventory / ls: Display inventory\nuse (int)+: Use an inventory item\n\nCOMBAT\natk / attack: Attack the current enemy",waterlvl,1);
    else if(!strcmp(input,"inv")||!strcmp(input,"inventory"))
        printInv(&mainChar);
    else if(!strcmp(input,"clear"))
        printf("\033[2J \033[1;1H");
    else if(!strcmp(input,"flee"))
    {
        printWaterText("You run in discrace",waterlvl,1);
        printf("\033[2J \033[1;1H");
        battlemode=0;
    }
    else if(!strcmp(input,"atk")||!strcmp(input,"attack\n"))
        roundOfAttack(&mainChar,&hannibal);
    else if(!strcmp(input,"ls")||!strcmp(input,"look\n"))
        printWaterText(getStoryEvent(level+area+1),waterlvl,1);
    else if(!strcmp(input,"search"))
        printWaterText(getStoryEvent(level+area+2),waterlvl,1);
    else if(strstr(input,"goto")!=NULL)
    {
        printWaterText("Where do you want to go?",waterlvl,1);
        fgets(input,256,stdin);
        stripNewline(input);
        changeLocation(input);
    }
    else if(!strcmp(input,"use"))
    {
        printWaterText("what item do you want to use?",waterlvl,1);
        fgets(input,256,stdin);
        stripNewline(input);
        handleItem(input);
    }
    else
    {
        printWaterText("Invalid input: ",waterlvl,0);
        printWaterText(input,waterlvl,0);
        printWaterText("Please type help for available commands",waterlvl,1);
    }

    return 0;
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

void stripNewline(char* str)
{
    int c=0;
    while(str[c]!='\n')
        c++;
    str[c]='\0';
}

void printLitText(char* inputText,int newline)
{
    int i=0;
    while(inputText[i]!='\0')
    {
        if((rand()%100)<LITPERCENT)
        {
            printf(RED"%c",inputText[i]);
            if(rand()%100<LITPERCENT+20)
            {
                //printf(BKYELLOW"%c"BKBLACK"%c",inputText[++i],inputText[++i]);
            }
            printf(RESET);
        }
        else
        {
            printf("%c",inputText[i]);
        }
        i++;
    }
    if(newline)
        printf("\n");
}

void changeLocation(char* identifier)
{
    int locflag=0;
    for(int i=0;i<5;i++)
    {
        if(!strcmp(identifier,currentLocation.sublocations[i]) && currentLocation.accessableLocations[i])
        {
            locflag=1;
            area=i*10;
            snprintf(consoleText,sizeof(consoleText),"%s/%s>",currentLocation.name,currentLocation.sublocations[i]);
            break;
        }
    }
    if(!locflag)
        printWaterText("You can't go there, if there even exists...",waterlvl,1);
        //TODO fix location unlocking.
}

void handleItem(char* identifier)
{
    //TODO fix handling items
}

void printInv(player* mc)
{
    char buffer[32];
    int buffersize=32;
    printWaterText("Weapon: ",waterlvl,0);
    printWaterText(mc->weapon.name,waterlvl,1);
    for(int i=0;i<mc->invCap;i++)
    {
        snprintf(buffer,buffersize,"Slot[%d]: %s",i,mc->inventory[i].name);
        printWaterText(buffer,waterlvl,1);
    }
    
}

void printBattleText(player* mc,enemy * enm)
{
    char buffer[128];
    int buffersize=128;
    snprintf(buffer,buffersize,"\n%s\nHealth: %d/%d\nAttack: %d\nDefense: %d\n","c--",mc->health,mc->healthCap,mc->attack,mc->defense);
    printWaterText(buffer,waterlvl,1);
    snprintf(buffer,buffersize,"%s\nHealth: %d/%d\nAttack: %d\nDefense: %d\n",enm->name,enm->health,enm->healthDefault,enm->attack,enm->defense);
    printWaterText(buffer,waterlvl,1);
}

void roundOfAttack(player* mc,enemy * enm)
{
    enemyGetAttacked(enm,mc->attack);
}