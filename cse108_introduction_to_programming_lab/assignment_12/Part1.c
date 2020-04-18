void addToLast(Node * head, int val){
	Node * new;
	new = (Node*)malloc(sizeof(Node));
	new->val = val;
	new->next = NULL;
	while(head->next!=NULL){
		head = head->next;
	}
	head->next = new;
}

int addToIndex(Node ** head, int val, int n){
	int result = 0, i, size = 0;
	Node * sizeTemp, * temp, * headTemp, * tailTemp;
	tailTemp = (*head);
	headTemp = (*head);
	sizeTemp = (*head);
	while(sizeTemp->next!=NULL){
		sizeTemp = sizeTemp->next;
		size++;
	}
	if(n > size+1 || n < 0) return -1;
	temp = (Node*)malloc(sizeof(Node));
	temp->val = val;
	if(n!=0){
		for(i=0; i<n-1; i++)
			tailTemp = tailTemp->next;
		for(i=0; i<n; i++)
			headTemp = headTemp->next;
		tailTemp->next = temp;
		temp->next = headTemp;
	} else {
		temp->next = (*head);
		(*head) = temp;
	}
	return result;
}

void removeLast(Node * head){
	int i, size=0;
	Node * temp;
	temp = head;
	while(head->next!=NULL){
		head = head->next;
		size++;
	}
	for(i=0; i<size-1; i++)
		temp = temp->next;
	temp->next = NULL;
	free(head);
}

int removeByIndex(Node ** head, int n){
	int result = 0, i, size = 0;
	Node * sizeTemp, * headTemp, * tailTemp;
	tailTemp = (*head);
	headTemp = (*head);
	sizeTemp = (*head);
	while(sizeTemp->next!=NULL){
		sizeTemp = sizeTemp->next;
		size++;
	}
	if(n > size+1 || n < 0) return -1;
	if(n!=0){
		for(i=0; i<n-1; i++)
			tailTemp = tailTemp->next;
		for(i=0; i<n; i++)
			headTemp = headTemp->next;
		tailTemp->next = headTemp->next;
	} else {
		(*head) = (*head)->next;
	}
	free(headTemp);
	return result;
}

Node * arrayToLinkedList(int ar[], int size){
	Node * list, * temp;
	int i;
	list = (Node*)malloc(sizeof(Node));
	temp = list;
	for(i=0; i<size; i++){
		if(i==size-1)
			list->next = NULL;
		else
			list->next = (Node*)malloc(sizeof(Node));
		list->val = ar[i];
		list = list->next;

	}
	return temp;
}
