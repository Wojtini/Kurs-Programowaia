#include <iostream>

using namespace std;

template<typename T> class Node{
public:
    T value;
    Node<T> *parent = NULL;
    Node<T> *leftChild = NULL;
    Node<T> *rightChild = NULL;
    Node(T value){
        cout << "Created node with value: " << value << endl;
        this->value = value;
    }
    ~Node(){
//        if(this->parent != NULL) {
//            if (this->parent->leftChild == this) {
//                this->parent->leftChild = NULL;
//            } else if(this->parent->rightChild == this) {
//                this->parent->rightChild = NULL;
//            }
//        }
        cout << "Deleted node with value: " << this->value << endl;
    }
    Node<T>* Search(T searchValue){
        cout << "Porownywane wartosci: " << searchValue << " i " << this->value << endl;
        if(searchValue == this->value){
            cout << "Znaleziony" << endl;
            return this;
        }
        else if(searchValue > this->value){
            if(this->rightChild == nullptr){
                cout << "BRAK prawego dziecku" << endl;
                return NULL;
            }else{
                cout << "Szukam w prawym dziecku: DZIECKO: " << this->rightChild->value << endl;
                return this->rightChild->Search(searchValue);
            }
        }
        else{
            if(this->leftChild == nullptr){
                cout << "BRAK lewego dzieca" << endl;
                return NULL;
            }else{
                cout << "Szukam w lewym dziecku: DZIECKO" << this->leftChild->value  << endl;
                return this->leftChild->Search(searchValue);
            }
        }
    }
    void Insert(T insertValue){
        cout << "INSERT: porownoje: " << insertValue << " i " << this->value << endl;
        if(insertValue > this->value){
            if(this->rightChild == NULL){
                Node<T> *newNode = new Node<T>(insertValue);
                newNode->parent = this;
                this->rightChild = newNode;
                cout << this->rightChild->value << endl;
                cout << "Wrzucone do lewego dziecka: " << insertValue << endl;
            }else{
                cout << "Proboje wrzucic w prawym dziecku" << endl;
                this->rightChild->Insert(insertValue);
            }
        }
        else{
            if(this->leftChild == NULL){
                Node<T> *newNode = new Node<T>(insertValue);
                newNode->parent = this;
                this->leftChild = newNode;
                cout << this->leftChild->value << endl;
                cout << "Wrzucone do lewego dziecka: " << insertValue << endl;
            }else{
                cout << "Proboje wrzucic w lewym dziecku" << endl;
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
            
        }
//        pom->value = pom->rightChild->minimumValue();
    }
    T minimumValue(){
        if(this->leftChild==NULL){
            return this->value;
        }else{
            return this->leftChild->minimumValue();
        }
    }
};

int main() {
    cout << "Wpisz wartosc korzenia";
    int rootValue;
    cin >> rootValue;
    Node<int> rootInt(rootValue);
    string userInput;
    do{
        cout << "wykonaj operacje INSERT/SEARCH/DELETE";
        cin >> userInput;
        if(userInput == "INSERT"){
            cout << "Wpisz wartosc: ";
            int givenValue;
            cin >> givenValue;
            if(!rootInt.Search(givenValue)){
                rootInt.Insert(givenValue);
            }else{
                cout << "Podana wartosc juz sie znajduje w drzewie" << endl;
            }
        }
        if(userInput == "SEARCH"){
            cout << "Wpisz wartosc: ";
            int givenValue;
            cin >> givenValue;
            if(rootInt.Search(givenValue) != NULL){
                cout << "Podana wartosc znajduje sie w drzewie" << endl;
            }else{
                cout << "Podana wartosc NIE znajduje sie w drzewie" << endl;
            }
        }
        if(userInput == "DELETE"){
            cout << "Wpisz wartosc: ";
            int givenValue;
            cin >> givenValue;
            rootInt.Delete(givenValue);
        }
    }while(userInput != "EXIT");
    return 0;
}
