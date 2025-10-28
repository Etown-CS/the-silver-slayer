item* initItem(char* itemName,int ItemType,char* desc,int statValChange,int consumedOnUse);

typedef struct{
    int type;
    char name[15], description[100];
    int magnitude,consumeable;
} item;