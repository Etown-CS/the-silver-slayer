#include <stdio.h>
//#include <string.h>
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

void printWaterText(char* inputText,int percentage);

int waterlvl=1;

int main()
{
    char inputText[]="You enter the caves, It feels a bit moist for some reason...";
    printWaterText(inputText,waterlvl);

    for(;waterlvl<=100;waterlvl++)
    {
        printf(YELLOW"Water lvl=%d"RESET,waterlvl);
        printWaterText("Waters Rise! Get to high ground!!! This is so that there are more characters in the text!!!",waterlvl);
        
    }

    printWaterText("You died, very sad",100);


    return 0;
}

void printWaterText(char* inputText,int percentage){
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
    printf("\n");
}