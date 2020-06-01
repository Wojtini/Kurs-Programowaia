#include <iostream>
#include <string.h>
#include <limits>
using namespace std;

template<typename T> class Node{
public:
    T value;
    Node<T> *parent = NULL;
    Node<T> *leftChild = NULL;
    Node<T> *rightChild = NULL;
    Node(T value){
//        cout << "Created node with value: " << value << endl;
        this->value = value;
    }
    ~Node(){
//        cout << "Deleted node with value: " << this->value << endl;
    }
    Node<T>* Search(T searchValue){
//        cout << "Porownywane wartosci: " << searchValue << " i " << this->value << endl;
        if(searchValue == this->value){
//            cout << "Znaleziony" << endl;
            return this;
        }
        else if(searchValue > this->value){
            if(this->rightChild == nullptr){
//                cout << "BRAK prawego dziecku" << endl;
                return NULL;
            }else{
//                cout << "Szukam w prawym dziecku: DZIECKO: " << this->rightChild->value << endl;
                return this->rightChild->Search(searchValue);
            }
        }
        else{
            if(this->leftChild == nullptr){
//                cout << "BRAK lewego dzieca" << endl;
                return NULL;
            }else{
//                cout << "Szukam w lewym dziecku: DZIECKO" << this->leftChild->value  << endl;
                return this->leftChild->Search(searchValue);
            }
        }
    }
    void Insert(T insertValue){
//        cout << "INSERT: porownoje: " << insertValue << " i " << this->value << endl;
        if(insertValue > this->value){
            if(this->rightChild == NULL){
                Node<T> *newNode = new Node<T>(insertValue);
                newNode->parent = this;
                this->rightChild = newNode;
                cout << this->rightChild->value << endl;
//                cout << "Wrzucone do lewego dziecka: " << insertValue << endl;
            }else{
//                cout << "Proboje wrzucic w prawym dziecku" << endl;
                this->rightChild->Insert(insertValue);
            }
        }
        else{
            if(this->leftChild == NULL){
                Node<T> *newNode = new Node<T>(insertValue);
                newNode->parent = this;
                this->leftChild = newNode;
                cout << this->leftChild->value << endl;
//                cout << "Wrzucone do lewego dziecka: " << insertValue << endl;
            }else{
//                cout << "Proboje wrzucic w lewym dziecku" << endl;
                this->leftChild->Insert(insertValue);
            }
        }
    }
    void Delete(T deleteValue){
        //Szukanie node do usuniecia
        Node<T>* pom = this->Search(deleteValue);
        //Usuwanie
        if(pom->leftChild==NULL && pom->rightChild==NULL){
            if(pom->parent != NULL) {
                if (pom->parent->leftChild == pom) {
                    pom->parent->leftChild = NULL;
                } else if(pom->parent->rightChild == pom) {
                    pom->parent->rightChild = NULL;
                }
            }
        }
        else if(pom ->leftChild!=NULL && pom->rightChild==NULL){
            pom->leftChild->parent = pom->parent;
            if (pom->parent->leftChild == pom) {
                pom->parent->leftChild = pom->leftChild;
            } else if(pom->parent->rightChild == pom) {
                pom->parent->rightChild = pom->leftChild;
            }
            delete pom;
        }
        else if(pom ->leftChild==NULL && pom->rightChild!=NULL){
            pom->rightChild->parent = pom->parent;
            if (pom->parent->leftChild == pom) {
                pom->parent->leftChild = pom->rightChild;
            } else if(pom->parent->rightChild == pom) {
                pom->parent->rightChild = pom->rightChild;
            }
            delete pom;
        }else{
            T min = pom->rightChild->minimumValue();
            pom->value = min;
            pom->rightChild->Delete(min);
        }
    }
    T minimumValue(){
        if(this->leftChild==NULL){
            return this->value;
        }else{
            return this->leftChild->minimumValue();
        }
    }
    void Show(){
        if(this->leftChild != NULL){
            this->leftChild->Show();
        }
        cout << this->value << " ";
        if(this->rightChild != NULL){
            this->rightChild->Show();
        }
    }
};
//useful funkcje
int isInt(char a[])
{
    int len=strlen(a);
    int sum=0;
    for(int i=0;i<len;i++)
    {
        if(isdigit(a[i])!=0)
            sum++;
        else if(a[i]=='-')
            sum++;
    }
    if(sum==len)
        return 1;
    else
        return 0;
}
int isFloat(char a[])
{
    int len=strlen(a);
    int sum=0;
    for(int i=0;i<len;i++)
    {
        if(isdigit(a[i])!=0)
        {
            sum++;
        }
        else if(a[i]=='.')
        {
            sum++;
        }
        else if(a[i]=='-')
        {
            sum++;
        }
    }
    if(sum==len)
        return 1;
    else
        return 0;
}
//Interfejs uzytkownika input
int getInt(){
    int x;
    cout << "Wpisz wartosc typu INT: ";
    cin >> x;
    while(cin.fail()) {
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(),'\n');
        cout << "Zly typ danych.  Sproboj panownie: ";
        cin >> x;
    }
    return x;
}
float getFloat(){
    float x;
    cout << "Wpisz wartosc typu FLOAT: ";
    cin >> x;
    while(cin.fail()) {
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(),'\n');
        cout << "Zly typ danych.  Sproboj panownie: ";
        cin >> x;
    }
    return x;
}
//Interfejs uzytkownika
void intTreeManager(int rootValue){
    string userInput;
    Node<int> root(rootValue);
    do{
        int givenValue;
        cout << "wykonaj operacje INSERT/SEARCH/DELETE/SHOW/EXIT" << endl;
        cin >> userInput;
        if(userInput == "INSERT"){
            givenValue = getInt();
            if(!root.Search(givenValue)){
                root.Insert(givenValue);
            }else{
                cout << "Podana wartosc juz sie znajduje w drzewie" << endl;
            }
        }
        if(userInput == "SEARCH"){
            givenValue = getInt();
            if(root.Search(givenValue) != NULL){
                cout << "Podana wartosc znajduje sie w drzewie" << endl;
            }else{
                cout << "Podana wartosc NIE znajduje sie w drzewie" << endl;
            }
        }
        if(userInput == "DELETE"){
            givenValue = getInt();
            if(root.Search(givenValue)==NULL){
                cout << "Nie ma takiej wartosci" << endl;
            }else{
                root.Delete(givenValue);
                cout << "Usunieto element" << endl;
            }
        }
        if(userInput == "SHOW"){
            cout << "Inorder: ";
            root.Show();
            cout << endl;
        }
    }while(userInput != "EXIT");
}
void floatTreeManager(float rootValue){
    string userInput;
    Node<float> root(rootValue);
    do{
        cout << "wykonaj operacje INSERT/SEARCH/DELETE/SHOW/EXIT" << endl;
        cin >> userInput;
        float givenValue;
        if(userInput == "INSERT"){
            givenValue = getFloat();
            if(!root.Search(givenValue)){
                root.Insert(givenValue);
            }else{
                cout << "Podana wartosc juz sie znajduje w drzewie" << endl;
            }
        }
        if(userInput == "SEARCH"){
            givenValue = getFloat();
            if(root.Search(givenValue) != NULL){
                cout << "Podana wartosc znajduje sie w drzewie" << endl;
            }else{
                cout << "Podana wartosc NIE znajduje sie w drzewie" << endl;
            }
        }
        if(userInput == "DELETE"){
            givenValue = getFloat();
            if(root.Search(givenValue)==NULL){
                cout << "Nie ma takiej wartosci" << endl;
            }else{
                root.Delete(givenValue);
                cout << "Usunieto element" << endl;
            }
        }
        if(userInput == "SHOW"){
            cout << "Inorder: ";
            root.Show();
            cout << endl;
        }
    }while(userInput != "EXIT");
}
void stringTreeManager(string rootValue){
    string userInput;
    Node<string> root(rootValue);
    do{
        cout << "wykonaj operacje INSERT/SEARCH/DELETE/SHOW/EXIT" << endl;
        cin >> userInput;
        string givenValue;
        if(userInput == "INSERT"){
            cout << "Wpisz wartosc: ";
            cin >> givenValue;
            if(!root.Search(givenValue)){
                root.Insert(givenValue);
            }else{
                cout << "Podana wartosc juz sie znajduje w drzewie" << endl;
            }
        }
        if(userInput == "SEARCH"){
            cout << "Wpisz wartosc: ";
            cin >> givenValue;
            if(root.Search(givenValue) != NULL){
                cout << "Podana wartosc znajduje sie w drzewie" << endl;
            }else{
                cout << "Podana wartosc NIE znajduje sie w drzewie" << endl;
            }
        }
        if(userInput == "DELETE"){
            cout << "Wpisz wartosc: ";
            cin >> givenValue;
            if(root.Search(givenValue)==NULL){
                cout << "Nie ma takiej wartosci" << endl;
            }else{
                root.Delete(givenValue);
                cout << "Usunieto element" << endl;
            }
        }
        if(userInput == "SHOW"){
            cout << "Inorder: ";
            root.Show();
            cout << endl;
        }
    }while(userInput != "EXIT");
}
//Main function
int main() {
    cout << "Wpisz wartosc korzenia";
    string userRootInput;
    cin >> userRootInput;
    //konwertowanie stringa do chara
    int strLen = userRootInput.length();
    char charArray[strLen + 1];
    strcpy(charArray, userRootInput.c_str());
    //kolejne przypadki
    if(isInt(charArray)){
        cout << "Rozpoznano typ INT" << endl;
        intTreeManager(stoi(userRootInput));
    }else if(isFloat(charArray)){
        cout << "Rozpoznano typ FLOAT" << endl;
        floatTreeManager(stof(userRootInput));
    }else{
        cout << "Rozpoznano typ STRING" << endl;
        stringTreeManager(userRootInput);
    }

    return 0;
}

