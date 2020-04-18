#include <stdio.h>
#include <string.h>
#define MAX_NUM_WORDS 500
#define WORD_LENGTH 50

int getAndFilterWord(char * w);
void addWord(char * w, char words[MAX_NUM_WORDS][WORD_LENGTH],int occur[MAX_NUM_WORDS], int * word_count);
void sortWords(char words[MAX_NUM_WORDS][WORD_LENGTH], int occur[MAX_NUM_WORDS],int word_count);
void myTest();

int main(){
    myTest();
    return 0;
}

int getAndFilterWord(char * w){
    int result = 1, i = 0, tempCnt = 0;
    char filteredw[WORD_LENGTH];

    scanf("%s", w);

    /*Checks for end condition*/
    if(strcmp(w, "end")==0) result = 0;

    /*Iterates through the string, only takes alphabetical characters and
      makes uppercase letters lowercase and assigns them to a temp array*/
    for(i=0; i<strlen(w); i++){
        if(w[i]<='Z' && w[i]>='A'){
            filteredw[tempCnt++] = w[i] + 'a'-'A';
        }else if(w[i]<='z' && w[i]>='a'){
            filteredw[tempCnt++] = w[i];
        }
    }
    filteredw[tempCnt] = '\0';
    /*Assigns the temp array to scanned word*/
    for(i=0; i<strlen(filteredw); i++)
        w[i] = filteredw[i];
    w[i] = '\0';
    return result;
}

void addWord(char * w, char words[MAX_NUM_WORDS][WORD_LENGTH],int occur[MAX_NUM_WORDS], int * word_count){
    int i, isFound = 0;
    /*Checks for the given word in words array to find if it exists already
      If it already is in the words array, increase the number of occurrence by 1*/
    for(i=0; i<*word_count; i++){
        if(strcmp(w, words[i])==0){
            isFound = 1;
            occur[i]++;
        }
    }
    /*If the given word is not in words array, add it to the words array and make the number of occurrences 1 */
    if(!isFound){
        strcpy(words[*word_count], w);
        occur[*word_count]++;
        (*word_count)++;
    }
}

void sortWords(char words[MAX_NUM_WORDS][WORD_LENGTH], int occur[MAX_NUM_WORDS],int word_count){
    /*Selection Sort*/
    int i, j;
    int tempInt;
    char tempString[WORD_LENGTH];
    for(i=0; i<word_count; i++){
        for(j=i+1; j<word_count; j++){
            if(occur[j] > occur[i]){
                tempInt = occur[j];
                occur[j] = occur[i];
                occur[i] = tempInt;
                strcpy(tempString, words[j]);
                strcpy(words[j], words[i]);
                strcpy(words[i], tempString);
            }
        }
    }
}

void myTest(){
    char word[WORD_LENGTH];
    char words[MAX_NUM_WORDS][WORD_LENGTH];
    int occur[MAX_NUM_WORDS];
    int i, word_count;
    /*Reads the words and filters them until user enters "end"*/
    while(getAndFilterWord(word))
        if(strcmp(word,"")!=0)
            addWord(word, words, occur, &word_count);

    sortWords(words, occur, word_count);

    for(i=0; i<word_count; i++)
        printf("%s %d\n", words[i], occur[i]);
}
