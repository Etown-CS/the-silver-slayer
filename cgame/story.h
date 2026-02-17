

void initStory();
void initLocations();
char * getStoryEvent(int event);
typedef struct {
    char* name;
    char* sublocations[5];
    int accessableLocations[5];
} location;

extern char *Story[200];
extern location cave;
extern location mine;