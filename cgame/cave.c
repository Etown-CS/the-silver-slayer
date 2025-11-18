#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "player.h"
#include "enemy.h"

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

void printWaterText(char* inputText,int percentage,int newline);
int handleCommand(char* input);
void printInv(player* mc);
int waterlvl=1;
player mainChar;

int main()
{
    char startText[]="The Silver Slayer [c alpha v1.0]\n\n You enter the caves. \n\n";
    char consoleText[32]="Cave/Entryway>";
    char inputText[256];
    printf("\033[2J \033[1;1H");
    printWaterText(startText,waterlvl,1);
    
    
    mainChar.attack=3;
    mainChar.defense=2;
    mainChar.health=5;
    mainChar.healthCap=10;
    mainChar.invCap=10;
    mainChar.inventory[0] = initItem("Golden Frying Pan",Weapon,"Epic beyond compare",99,1);


    while(1)
    {
        printWaterText(consoleText,waterlvl,0);
        fgets(inputText,256,stdin);
        //printf("%s",inputText);
        handleCommand(inputText);
        if(waterlvl>100)
        {
            handleCommand("clear\n");
            printWaterText("You Died",waterlvl,1);
            printWaterText("Respawn   Main Menu",waterlvl,1);
            break;
        }
        waterlvl+=10;
    }


    return 0;
}

int handleCommand(char* input)
{
    if(!strcmp(input,"exit\n")||!strcmp(input,"quit\n"))
        exit(0);
    else if(!strcmp(input,"help\n"))
        printWaterText("GENERAL\nexit / quit: Quit the game.\nsettings: Modify game settings\n\nINVENTORY\ndesc / describe: Show an inventory item's description\ndrop (int)+: Drop an item\ninv / inventory / ls: Display inventory\nuse (int)+: Use an inventory item\n\nCOMBAT\natk / attack: Attack the current enemy",waterlvl,1);
    else if(!strcmp(input,"ls\n")||!strcmp(input,"inv\n")||!strcmp(input,"inventory\n"))
        printInv(&mainChar);
    else if(!strcmp(input,"clear\n"))
        printf("\033[2J \033[1;1H");
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

void printInv(player* mc)
{
    char buffer[32];
    int buffersize=32;
    for(int i=0;i<mc->invCap;i++)
    {
        snprintf(buffer,32,"Slot[%d]: %s",i,mc->inventory[i].name);
        printWaterText(buffer,waterlvl,1);
    }
    
}