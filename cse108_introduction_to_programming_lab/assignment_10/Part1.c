/* =================== IMPLEMENT THE FUNCTIONS BELOW =================== */

/* Parse the line string to construct an ilce struct. Then place this ilce
   struct to nufus struct. This function will be called for each line in
   ilceler.txt.
*/
void addIlce(nufus *nuf, char* line) {
    char ilceadi[MAXSTRLEN], iladi[MAXSTRLEN];
    int pop;
    sscanf(line, "%s %s %d", ilceadi, iladi, &pop);
    strcpy(nuf->ilceler[nuf->count].isim,ilceadi);
    strcpy(nuf->ilceler[nuf->count].il,iladi);
    nuf->ilceler[nuf->count].nufus = pop;
    nuf->count++;
}

/* sort nufus struct according to sortby and order parameters and print the
   sorted nufus struct using printNufus function.

   - order parameter defines how the sorting will be done. It can be either
	 ascending (artan A-Z) or descending (azalan Z-A).

   - sort parameter defines which information is used during the sorting
     process. nufus struct can be sorted by population, by district(ilce) name
     or by city name. If city name is used on sorting, you should also sort
     the districts of the same city by its name. If district name is used on
     sorting you should use the city name for sorting the districts with the
     same name.

     Example output (sorting is done by city name, ascending order):
     ILCE                          IL                               NUFUS
     --------------------------------------------------------------------
     .
     .
     .
     AKYAKA                        KARS                             11375
     ARPACAY                       KARS                             18737
     DIGOR                         KARS                             24932
     KAGIZMAN                      KARS                             46687
     KARS                          KARS                            111278
     SARIKAMIS                     KARS                             47231
     SELIM                         KARS                             24924
     SUSUZ                         KARS                             11302
     .
     .
     .
*/
void printSorted(nufus nuf, sortby sort, order ord) {
    int i, j, smallsub;
    char tempIlce[MAXSTRLEN], tempIL[MAXSTRLEN];
    int tempNufus;
    if(sort == ISIM){
        for(i=0; i<ILCESAYISI; i++){
            smallsub = i;
            for(j=i+1; j<ILCESAYISI; j++){
                if(ord==ASC){
                    if(strcmp(nuf.ilceler[j].isim ,nuf.ilceler[smallsub].isim) < 0){
                        smallsub = j;
                    }
                } else {
                    if(strcmp(nuf.ilceler[j].isim ,nuf.ilceler[smallsub].isim) > 0){
                        smallsub = j;
                    }
                }

            }
            tempNufus = nuf.ilceler[i].nufus;
            nuf.ilceler[i].nufus = nuf.ilceler[smallsub].nufus;
            nuf.ilceler[smallsub].nufus = tempNufus;
            strcpy(tempIlce,nuf.ilceler[i].isim);
            strcpy(nuf.ilceler[i].isim,nuf.ilceler[smallsub].isim);
            strcpy(nuf.ilceler[smallsub].isim,tempIlce);
            strcpy(tempIL,nuf.ilceler[i].il);
            strcpy(nuf.ilceler[i].il,nuf.ilceler[smallsub].il);
            strcpy(nuf.ilceler[smallsub].il,tempIL);
        }
    } else if(sort == IL){
        for(i=0; i<ILCESAYISI; i++){
            smallsub = i;
            for(j=i+1; j<ILCESAYISI; j++){
                if(ord==ASC){
                    if(strcmp(nuf.ilceler[j].il ,nuf.ilceler[smallsub].il) < 0){
                        smallsub = j;
                    }
                } else {
                    if(strcmp(nuf.ilceler[j].il ,nuf.ilceler[smallsub].il) > 0){
                        smallsub = j;
                    }
                }
            }
            tempNufus = nuf.ilceler[i].nufus;
            nuf.ilceler[i].nufus = nuf.ilceler[smallsub].nufus;
            nuf.ilceler[smallsub].nufus = tempNufus;
            strcpy(tempIlce,nuf.ilceler[i].isim);
            strcpy(nuf.ilceler[i].isim,nuf.ilceler[smallsub].isim);
            strcpy(nuf.ilceler[smallsub].isim,tempIlce);
            strcpy(tempIL,nuf.ilceler[i].il);
            strcpy(nuf.ilceler[i].il,nuf.ilceler[smallsub].il);
            strcpy(nuf.ilceler[smallsub].il,tempIL);
        }
    } else if(sort == NUFUS){
        for(i=0; i<ILCESAYISI; i++){
            smallsub = i;
            for(j=i+1; j<ILCESAYISI; j++){
                if(ord==ASC){
                    if(nuf.ilceler[j].nufus < nuf.ilceler[smallsub].nufus){
                        smallsub = j;
                    }
                } else {
                    if(nuf.ilceler[j].nufus > nuf.ilceler[smallsub].nufus){
                        smallsub = j;
                    }
                }
            }
            tempNufus = nuf.ilceler[i].nufus;
            nuf.ilceler[i].nufus = nuf.ilceler[smallsub].nufus;
            nuf.ilceler[smallsub].nufus = tempNufus;
            strcpy(tempIlce,nuf.ilceler[i].isim);
            strcpy(nuf.ilceler[i].isim,nuf.ilceler[smallsub].isim);
            strcpy(nuf.ilceler[smallsub].isim,tempIlce);
            strcpy(tempIL,nuf.ilceler[i].il);
            strcpy(nuf.ilceler[i].il,nuf.ilceler[smallsub].il);
            strcpy(nuf.ilceler[smallsub].il,tempIL);
        }
    }

    printNufus(nuf);

}

/* calculate the city population based on nufus struct */
int getIlNufus(nufus nuf, char* il) {
    int i, pop=0;
    for(i=0; i<ILCESAYISI; i++){
        if(strcmp(nuf.ilceler[i].il,il)==0){
            pop += nuf.ilceler[i].nufus;
        }
    }
    return pop;
}

/* find the city which has the maximum population based on nufus struct */
/* return the address of the city name from iller struct */
char* mostCrowdedCity(nufus nuf, iller *sehirler) {
    int popMax=0, popIndex, i;
    char result[MAXSTRLEN];
    for(i=0; i<ILSAYISI; i++){
        if(getIlNufus(nuf, sehirler->isim[i]) > popMax){
            popMax = getIlNufus(nuf, sehirler->isim[i]);
            strcpy(result, sehirler->isim[i]);
            popIndex = i;
        }
    }
    return sehirler->isim[popIndex];
}

/* find the city which has the maximum number of districts (ilce) */
/* return the address of the city name from iller struct */
char* mostFragmentedCity(nufus nuf, iller *sehirler) {
    int fragMax=0, tempFrag=0, fragIndex, i, j;
    for(i=0; i<ILSAYISI; i++){
        tempFrag = 0;
        for(j=0; j<ILCESAYISI; j++){
            if(strcmp(sehirler->isim[i], nuf.ilceler[j].il)==0){
                tempFrag++;
            }
        }
        if(tempFrag > fragMax){
            fragMax = tempFrag;
            fragIndex = i;
        }
    }
    return sehirler->isim[fragIndex];
}

/* construct iller struct based on the given nufus struct */
/* every il should occur one time in iller struct */
void constructIller(nufus nuf, iller *sehirler) {
    int i, j, cnt=0, flag;
    for(i=0; i<nuf.count; i++){
        flag = 0;
        for(j=0; j<ILSAYISI; j++){
            if(strcmp(nuf.ilceler[i].il, sehirler->isim[j]) == 0){
                flag = 1;
            }
        }
        if(!flag){
                strcpy(sehirler->isim[cnt++],nuf.ilceler[i].il);
        }
    }
}
