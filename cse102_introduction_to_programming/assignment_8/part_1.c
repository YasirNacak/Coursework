#include <stdio.h>
#include <string.h>

#define NAMELEN 50
#define MAXRECORD 500

typedef struct person_s {
	int id;
	char name[NAMELEN];
} Person;

typedef struct record_s {
	int id;
	char number[NAMELEN];
} Record;

typedef struct Records_s {
	Record data[MAXRECORD];
	int size;
} Records;

typedef struct people_s {
	Person data[MAXRECORD];
	int size;
} People;

/* ========== IMPLEMENT THE FUNCTIONS BELOW ========== */
/*
	Write Records.data array to the filename.
	Note that file should be binary.
*/
void writeRecords(Records records, char* filename) {
    FILE *fileptr;
    fileptr = fopen(filename,"wb");
	fwrite(records.data, sizeof(Record), records.size, fileptr);
	fclose(fileptr);
}

/*
	Reads Record structs from the binary file and places them to Records.data array.
	Note that the number of Record structs in the file is unknown.
*/
void readRecords(Records *records, char* filename) {
    FILE *fileptr;
    Record rec;
    fileptr = fopen(filename,"rb");
    while(fread(&rec, sizeof(Record), 1, fileptr)){
		records->data[records->size] = rec;
		records->size++;
	}
	fclose(fileptr);
}

/*
	Write People.data array to the filename.
	Note that file should be binary.
*/
void writePeople(People people, char* filename) {
    FILE *fileptr;
    fileptr = fopen(filename,"wb");
	fwrite(people.data, sizeof(Person), people.size, fileptr);
	fclose(fileptr);
}

/*
	Reads Person structs from the binary file and places them to People.data array.
	Note that the number of Person structs in the file is unknown.
*/
void readPeople(People *people, char* filename) {
    FILE *fileptr;
    Person per;
    fileptr = fopen(filename,"rb");
    while(fread(&per, sizeof(Person), 1, fileptr)){
		people->data[people->size] = per;
		people->size++;
	}
	fclose(fileptr);
}

/*
    Reads the input file and constructs People and Records structs.
  	Note that each Record in Records is unique (should not present multiple times).
  */
void read(char* filename, People *people, Records *records) {
    FILE *fileptr;
    int i=0, count=0, record_count=0;
    int ID, phone_count;
    char name[NAMELEN], surname[NAMELEN], temp_phone_num[11];
    fileptr = fopen(filename,"rt");

    while(fscanf(fileptr,"%d %s %s %d", &ID, name, surname, &phone_count)==4){

        people->data[count].id = ID;
        people->size = count + 1;
        strcpy(people->data[count].name, name);
        strcat(people->data[count].name, " ");
        strcat(people->data[count].name, surname);
        for(i=0; i<phone_count; i++, record_count++){
            fscanf(fileptr, "%s", temp_phone_num);
            records->size = record_count + 1;
            records->data[record_count].id = ID;
            strcpy(records->data[record_count].number, temp_phone_num);
        }
        count++;
    }
    fclose(fileptr);
}

/* ========== IMPLEMENT THE FUNCTIONS ABOVE ========== */

void print(People people, Records records) {
	int i,j,found = 0;
	/* header */
	printf("%-5s %-30s %-20s\n", "ID","NAME","NUMBER(s)");
	/* line */
	for (i = 0; i < 57; ++i)
		printf("-");
	printf("\n");

	for (i = 0; i < people.size; ++i) {
		found = 0;
		printf("%-5d %-30s", people.data[i].id, people.data[i].name);
		for (j = 0; j < records.size; ++j) {
			if(records.data[j].id == people.data[i].id) {
				if(found)
					printf("%36s", "");
				printf("%-20s\n", records.data[j].number);
				found = 1;
			}
		}
		printf("\n");
	}
}

int isPeopleEq(People ppl1, People ppl2) {
	int i;
	if(ppl1.size != ppl2.size)
		return 0;
	for (i = 0; i < ppl1.size; ++i)
		if(strcmp(ppl1.data[i].name,ppl2.data[i].name) ||
			ppl1.data[i].id != ppl2.data[i].id)
			return 0;
	return 1;
}

int isRecordsEq(Records rec1, Records rec2) {
	int i;
	if(rec1.size != rec2.size)
		return 0;
	for (i = 0; i < rec1.size; ++i)
		if(strcmp(rec1.data[i].number,rec2.data[i].number) ||
			rec1.data[i].id != rec2.data[i].id)
			return 0;
	return 1;
}

int main(int argc, char** argv) {
	People people1,people2;
	Records records1,records2;
	people1.size = 0;
	records1.size = 0;
	/*read(argv[1],&people1, &records1);*/
	read("input.txt",&people1, &records1);
	print(people1, records1);
	writePeople(people1,"people.bin");
	writeRecords(records1,"records.bin");
	readRecords(&records2,"records.bin");
	readPeople(&people2,"people.bin");
	print(people2, records2);
	printf("%s\n", isRecordsEq(records1,records2) ? "RECORDS ARE SAME" : "RECORDS ARE DIFFERENT!");
	printf("%s\n", isPeopleEq(people1,people2) ? "PEOPLE ARE SAME" : "PEOPLE ARE DIFFERENT!");
	return 0;
}
