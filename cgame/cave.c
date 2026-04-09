#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
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

/*
* TODOS:
* create ssh puzzle with JTR to get out of the cave area and into the mine area
* Finish the exit puzzle with the elevator in the mine
* mine ddos puzzles
* mine boss battle + loot
* STRETCH TODOS:
* when you delete the shadow file, shift back the inventory.
* loot from the enemies
* drop command
* enemy abilities
*/

void printWaterText(char* inputText,int percentage,int newline);
void printLitText(char* inputText,int newline);
int handleCommand(char* input);
void handleItem(char* idx);
void printSpecialText(char* inputText,int newline);
void unlockLocation(int currAreaCode);
void printInv(player* mc);
void printBattleText(player* mc,enemy * enm);
void roundOfAttack(player* mc,enemy * enm,attackModes mode);
void useAbility(enemy* enmy,player* mc);
void changeLocation(char* identifier);
void stripNewline(char* str);
void changeConsoleText(char* location,char* sublocation);
void summonEnemy();

char consoleText[32];
int waterlvl=1;
player *mainChar;
enemy *hannibal;
location *currentLocation;
int battlemode=0;
int lit=0;
int locCode;
int areaCode;

int main()
{
    initStory();
    initLocations();
    
    char startText[]="The Silver Slayer [c alpha v2.8]\n\n";
    strcpy(consoleText,"Cave/Entryway>");
    char inputText[256];
    printf("\033[2J \033[1;1H");
    printSpecialText(startText,0);
    printSpecialText(getStoryEvent(0),1);

    mainChar=createPlayer(&locCode,&areaCode);
    if(!locCode)
        currentLocation=&cave;
    else
        currentLocation=&mine;
    currentLocation->area=areaCode;

    hannibal= createEnemy("Groundhog",10,1,1,adware);

    while(mainChar->health>0)
    {
        if(battlemode)
        {
            printBattleText(mainChar,hannibal);
        }
        printf("\n");
        printSpecialText(consoleText,0);
        fgets(inputText,256,stdin);
        printf("\n");
        //printf("%s",inputText);
        if(battlemode)
            printf("\033[2J \033[1;1H");
        stripNewline(inputText);
        handleCommand(inputText);
        
        
            
        
        if(!battlemode)
        {
            waterlvl+=2;
            summonEnemy();
        }
        else if(hannibal->health<1)
            battlemode=0;
        if(&cave==currentLocation && waterlvl>=100)
        {
            printSpecialText("The water surrounds you and you sink into the depths drowning in the mine",1);
            break;
        }
    }
    handleCommand("clear");
    printSpecialText("You Died",1);
    printSpecialText("Respawn   Main Menu",1);
    mainChar->health=mainChar->healthCap;
    writeSave(mainChar,currentLocation->level,0);
    sleep(10);
    return 0;
}

int handleCommand(char* input)
{
    if(!strcmp(input,"exit")||!strcmp(input,"quit"))
    {
        writeSave(mainChar,currentLocation->level,currentLocation->area);
        exit(0);
    }
    else if(!strcmp(input,"help"))
        printSpecialText("GENERAL\nexit / quit: Quit the game.\nsettings: Modify game settings\n\nINVENTORY\ndesc / describe: Show an inventory item's description\ndrop (int)+: Drop an item\ninv / inventory / ls: Display inventory\nuse (int)+: Use an inventory item\n\nCOMBAT\natk / attack: Attack the current enemy",1);
    else if(!strcmp(input,"inv")||!strcmp(input,"inventory"))
        printInv(mainChar);
    else if(!strcmp(input,"clear"))
        printf("\033[2J \033[1;1H");
    else if(!strcmp(input,"flee"))
    {
        if(battlemode)
        {
            if(hannibal->power !=lastProspector)
            {
                //printf("\033[2J \033[1;1H");
                printSpecialText("You run in discrace",1);
                battlemode=0;
            }
            else
            {
                int success=0;
                for(int i=0;i<mainChar->invCap;i++)
                {
                    if(!strcmp(mainChar->inventory[i]->name,"DDOS Drill"))
                    {
                        mainChar->health/=2;
                        printSpecialText("you Manage to Escape, but you get hit for half of your health on the way out",1);
                        success=1;
                    }
                    break;
                }
                if(!success)
                {
                    printSpecialText("You Can't Flee!",1);
                    roundOfAttack(mainChar,hannibal,Wait);
                }
            }
        }
        else
        {
            printSpecialText("There's nothing to run from?",1);
        }
    }
    else if(!strcmp(input,"atk")||!strcmp(input,"attack"))
    {
        if(battlemode)
            roundOfAttack(mainChar,hannibal,Fight);
        else
            printSpecialText("You're not in combat?",1);
    }
    else if(!strcmp(input,"wait"))
    {
        if(battlemode)
            roundOfAttack(mainChar,hannibal,Wait);
        else
            printSpecialText("You're not in combat?",1);
    }
    else if(!strcmp(input,"ls")||!strcmp(input,"look"))
    {
        if(&cave == currentLocation)
        {
            printSpecialText(getStoryEvent(currentLocation->level+currentLocation->area+1 + (waterlvl>=50 ? 50 : 0) ),1);
            unlockLocation(currentLocation->area);
        }
        else
        {
            printSpecialText(getStoryEvent(currentLocation->level+currentLocation->area+1 + (lit ? 50 : 0) ),1);
            unlockLocation(currentLocation->area);
        }
        
    }
    else if(!strcmp(input,"search"))
    {
        int mod;
        if(&cave==currentLocation)
            mod=waterlvl>=50?50:0;
        else
            mod=lit?50:0;
        printSpecialText(getStoryEvent(currentLocation->level+currentLocation->area+2+mod),1);
        currentLocation->accessableItems[currentLocation->area/10]=1;
    }
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
            if(mainChar->inventory[idx]->type!=Unassigned)
            {
                printSpecialText(mainChar->inventory[idx]->name,1);
                printSpecialText(mainChar->inventory[idx]->description,1);
                printf("Power: "GREEN"%d"RESET" %s\n",mainChar->inventory[idx]->magnitude,(mainChar->inventory[idx]->consumeable ? "\nConsumable" : ""));
            }
            else
            {
                printSpecialText("Nothing is in that slot",1);
            }
        }
    }
    else if(!strcmp(input,"warp"))
    {
        if(currentLocation==&mine)
            currentLocation=&cave;
        else
            currentLocation=&mine;
        
        printSpecialText("Swapped",1);
    }
    else if(!strcmp(input,"lol"))
        printSpecialText("you hear a whisper, what's so funny?",1);
    else if(!strcmp(input,"map"))
        printSpecialText("It's far too dark to see the map, you will have to Look to see what's ahead.",1);
    else if(!strcmp(input,"echo health"))
    {
        char buffer[8];
        snprintf(buffer,sizeof(buffer),"%d/%d",mainChar->health,mainChar->healthCap);
        printSpecialText(buffer,1);
    }
    else if(!strcmp(input,"pickup"))
    {
        
        if(mainChar->currSlot>=mainChar->invCap)
            printSpecialText("Your Inventory is full! You need to drop an item before picking up anything else",1);
        else
        {
            
            if(currentLocation->locItems[currentLocation->area/10]!=NULL && currentLocation->accessableItems[currentLocation->area/10])
            {
                printSpecialText("You found a ",0);
                printf(YELLOW"%s"RESET,currentLocation->locItems[currentLocation->area/10]->name);
                printSpecialText(" It, ",0);
                printSpecialText(currentLocation->locItems[currentLocation->area/10]->description,1);
                mainChar->inventory[mainChar->currSlot++]=currentLocation->locItems[currentLocation->area/10];
            }
            else if(&mine == currentLocation && !currentLocation->area && currentLocation->accessableItems[0])
            {
                printSpecialText("you've pickup up a torch, use the command TORCH to light and extinguish it",1);
                mainChar->torch=1;
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
    if(!strcmp("Continue",identifier) && currentLocation->area==10 && currentLocation==&mine)
    {
        if(currentLocation->mineSubArea==0 || currentLocation->mineSubArea==2)
        {
            currentLocation->mineSubArea+=2;
            printSpecialText("You continue into the maze...",1);
            return;
        }
        else if(currentLocation->mineSubArea==6)
            currentLocation->mineSubArea=0;

    }
    else if(currentLocation->area==10 && currentLocation==&mine && currentLocation->mineSubArea==4)
    {
        if(!strcmp("Right",identifier))
        {
            currentLocation->mineSubArea+=2;
            printSpecialText("You go right in the maze...",1);
            return;
        }
        else if(!strcmp("Left",identifier))//TODO fix the maze cd-ing
        {
            currentLocation->mineSubArea-=2;
            printSpecialText("You go left into the maze...",1);
            return;
        }
    }
    else
    {
    for(int i=0;i<5;i++)
    {
        if(!strcmp(identifier,currentLocation->sublocations[i]) && currentLocation->accessableLocations[i])
        {
            locflag=1;
            currentLocation->area=i*10;
            changeConsoleText(currentLocation->name,currentLocation->sublocations[i]);
            int code;
            if(&cave==currentLocation)
                code=currentLocation->level+currentLocation->area+(waterlvl>=50?50:0);
            else
                code=currentLocation->level+currentLocation->area+(waterlvl>=50?50:0);
                printSpecialText(getStoryEvent(code),1);
            break;
        }
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
            
            
            //TODO FIX WEAPON AND ARMOR EQUIPING
            if(mainChar->weapon!=NULL)
            {
                mainChar->attack -= mainChar->weapon->magnitude;
            }
            
            printSpecialText("Weapon: ",0);
            printSpecialText(mainChar->inventory[index]->name,0);
            if(mainChar->weapon==mainChar->inventory[index])
            {
                mainChar->weapon=NULL;
                printSpecialText(" Was Unequipped",1);
            }
            else
            {
                
                mainChar->weapon=mainChar->inventory[index];
                printSpecialText(" Was Equipped",1);
                mainChar->attack+=mainChar->weapon->magnitude;
            }
        break;

        case Armor:
            if(mainChar->armor!=NULL)
                mainChar->defense -= mainChar->armor->magnitude;
            
            printSpecialText("Armor: ",0);
            printSpecialText(mainChar->inventory[index]->name,0);
            if(mainChar->armor==mainChar->inventory[index])
            {
                mainChar->armor=NULL;
                printSpecialText(" Was Unequipped",1);
            }
            else
            {
                mainChar->armor=mainChar->inventory[index];
                mainChar->defense=mainChar->armor->magnitude;
                printSpecialText(" Was Equipped",1);
            }
            
        break;

        case Wearable:
            if(mainChar->clothing==mainChar->inventory[index])
            {
                printSpecialText("Armor: ",0);
                printSpecialText(mainChar->clothing->name,0);
                printSpecialText(" Was Unequipped",1);
            }
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

        case Puzzle:
            if(!strcmp(mainChar->inventory[index]->name,"Shadow File"))
                printSpecialText("this Item is meant to be used with another...",1);
            else if(!strcmp(mainChar->inventory[index]->name,"Johnny The Ripper"))
            {
                
                for(int i=0;i<mainChar->invCap;i++)
                {
                    if(!strcmp(mainChar->inventory[i]->name,"Shadow File"))
                    {
                        if(mainChar->inventory[i]->magnitude)
                            printSpecialText("Breaking the shadow file reveals the user: applej and the password: #Min3rsP4rad1ce",1);
                        else
                            printSpecialText("Breaking the shadow file reveals the user: mazeman13 and the password: M4$sterM4zer!",1);
                        recycleItem(mainChar->inventory[i],"",0,"",0,0);
                        return;
                    }
                }
            }
            else if(!strcmp(mainChar->inventory[index]->name,"Secure Fossilized Shell"))
            {
                char usr[32],pwd[32];
                printSpecialText("Username:",0);
                fgets(usr,sizeof(usr),stdin);
                printSpecialText("Password:",0);
                stripNewline(usr);
                stripNewline(pwd);
                if(!strcmp("applej",usr)&&!strcmp("#Min3rsP4rad1ce",pwd))
                {
                    //TODO Change location from cave to mine
                    printSpecialText("The Shell warps your location...",1);
                    printSpecialText(getStoryEvent(waterlvl>50? 99:49),1);
                    currentLocation=&mine;
                }
                else if(!strcmp("mazeman13",usr)&&!strcmp("M4$sterM4zer!",pwd))
                {
                    printSpecialText("The Shell warps your location...",1);
                    //printSpecialText(getStoryEvent(lit?170:120),1);
                    currentLocation->accessableLocations[2]=1;
                    changeLocation("Mineshaft");
                }

            }

        break;

        default:
        printSpecialText("Nothing is in that slot",1);
        //printSpecialText("I'm not even sure what you'd do with a ",0);
        //printSpecialText(mainChar->inventory[index]->name,1);
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
        printSpecialText("Damage: ",0);
        printf(YELLOW"%d"RESET"\n",mc->weapon->magnitude);
    }
    if(mc->armor!=NULL)
    {
        printSpecialText("Current Armour Equipped: ",0);
        printSpecialText(mc->armor->name,1);
        printSpecialText("Defense: ",0);
        printf(GREEN"%d"RESET"\n",mc->armor->magnitude);
    }
    if(mc->clothing!=NULL)
    {
        printSpecialText("Current Clothing Equipped: ",0);
        printSpecialText(mc->clothing->name,1);
        printSpecialText("StylePoints: ",0);
        printf(CYAN"%d"RESET"\n",mc->clothing->magnitude);
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
    snprintf(buffer,buffersize,"\n%s\nHealth: %d/%d\nAttack: %d\nDefense: %d\n",mc->name,mc->health,mc->healthCap,mc->attack,mc->defense);
    printSpecialText(buffer,1);
    snprintf(buffer,buffersize,"%s\nHealth: %d/%d\nAttack: %d\nDefense: %d\n",enm->name,enm->health,enm->healthDefault,enm->attack,enm->defense);
    printSpecialText(buffer,1);
}

void roundOfAttack(player* mc,enemy * enm,attackModes mode)
{
    char buffer[64];
    if(mode==Fight)
    {
        //weapons and damage
        int eDamage=enemyGetAttacked(enm,mc->attack);
        snprintf(buffer,sizeof(buffer),"%s took ",enm->name);
        printSpecialText(buffer,0);
        printf(GREEN"%d"RESET,eDamage);
        printSpecialText(" Damage!",1);
    }
    else if(mode==Wait)
        printSpecialText("You bide your time...",1);
    if(mode==Fight || mode ==Wait)
    {
        if(enm->health>0)
        {
            int damage=getAttacked(mc,enm->attack);
            printSpecialText("You took ",0);
            printf(RED"%d"RESET,damage);
            printSpecialText(" Damage, ouch.",1);
            if(rand()%5==1)
            {
                useAbility(hannibal,mainChar);
            }
        }
    
        else
        {
            printf(GREEN"You Defeated %s, congrats!"RESET,enm->name);
        }
    }
}

void summonEnemy()
{
    int chance=rand()%40;
    if(chance  < (&mine == currentLocation ? 15 : 10))
    {
        hannibal=copyEnemy(currentLocation->spawnAbleEnemys[rand()%10]);
        battlemode=1;
        printSpecialText("A wild ",0);
        printSpecialText(hannibal->name,0);
        printSpecialText(" Has appeared!",1);
    }
    //printf("the random roll %d, location diff=%d\n",chance,(&mine == currentLocation ? 15 : 10));
}

//  None, //No ability
//     lootPlus, //Gives extra loot, money or something
//     spyWare, //"reports" player data
//     trojan, //Tampers with the player or items
//     replicates, //Replicates the enemy multiple times
//     adware, //destorts the user's ui
//     skeleTON, //Skeleton boss
//     lastProspector, //mine boss

void useAbility(enemy* enmy,player* mc)
{
    switch(enmy->power)
    {
        case spyWare:
            printf(YELLOW"The %s has hit you with spyware!"RESET,enmy->name);
        break;
        case trojan:
            printf(YELLOW"The %s has hit you with a trojan!"RESET,enmy->name);
        break;
        case replicates:
            printf(YELLOW"%s Just Duplicated!"RESET,enmy->name);
            enmy->health+=enmy->healthDefault;
            enmy->attack+=enmy->attackDefault;
        break;
        case adware:
            printf(YELLOW"the %s is blasting you with adds!\n"RESET,enmy->name);
            int len=strlen(consoleText);
            consoleText[rand()%len]='a'+rand()%26;
            consoleText[rand()%len]='A'+rand()%26;
            consoleText[rand()%len]='0'+rand()%10;
        break;
        case skeleTON:
             printf(RED"The %s has hit you with bone fragments!"RESET,enmy->name);
        break;
        case lastProspector:
            printf(CYAN"The %s has cursed you!"RESET,enmy->name);
        break;
        default:
        break;
    }
}