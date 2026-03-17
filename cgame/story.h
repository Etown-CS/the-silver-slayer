#ifndef STORY_H
#define STORY_H

#include "item.h"
#include "enemy.h"

void initStory();
void initLocations();
char * getStoryEvent(int event);
typedef struct {
    char* name;
    char* sublocations[5];
    item* locItems[5];
    int accessableItems[5];
    int accessableLocations[5];
    int level;
    int area;
    enemy* spawnAbleEnemys[10];
    enemy* boss;
} location;

extern char *Story[200];
extern location cave;
extern location mine;

#endif