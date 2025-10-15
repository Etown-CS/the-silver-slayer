#include <stdio.h>
#include <string.h>
#include <stdlib.h>

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

int waterlvl=1;

int main()
{
    char startText[]="\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nThe Silver Slayer [c alpha v1.0]\n\n You enter the caves. \n\n";
    char consoleText[32]="Cave/Entryway>";
    char *inputText;
    printWaterText(startText,waterlvl,1);
    
    


    while(1)
    {
        printWaterText(consoleText,waterlvl,0);
        fgets(inputText,256,stdin);
        //printf("%s",inputText);
        handleCommand(inputText);
        waterlvl+=5;
    }


    return 0;
}

int handleCommand(char* input)
{
    if(!strcmp(input,"exit\n")||!strcmp(input,"quit\n"))
        exit(0);
    if(!strcmp(input,"help\n"))
        printWaterText("exit,quit: close the game\nhelp: show the help menu\nuse [int]: use an item from the inventory\n",waterlvl,1);
}

void printWaterText(char* inputText,int percentage,int newline)
{
    //this method should iterate through the string, and print it out
    int i=0;
    while(inputText[i]!='\0'){
        if(((rand()%100))<percentage)
        {
            if(waterlvl>50 && ((rand()%10))-(waterlvl/15)<1)
                printf(BKBLUE CYAN"%c"RESET BKBLACK,inputText[i]);
            else
                printf(BLUE"%c"RESET,inputText[i]);
        }

        else{
            if(waterlvl>50 && ((rand()%10))-(waterlvl/30)<1)
                printf(BKBLUE);
            printf("%c",inputText[i]);
            printf(BKBLACK);
        }
        i++;
    }
    if(newline)
        printf("\n");
}