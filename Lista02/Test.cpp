#include <iostream>
#include <string>
#include <exception>
#include <cstdlib>
using namespace std;

int const ARRSIZE = 13;

class ArabRzymException{};
class ArabRzym{
public:
    static string Rzymskie[ARRSIZE];
    static int Arabskie[ARRSIZE];
    static string arab2rzym(int arab);
    static int rzym2arab(string rzym) throw (ArabRzymException);
};
string ArabRzym::Rzymskie[] = { "I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};;
int ArabRzym::Arabskie[] = { 1,4,5,9,10,40,50,90,100,400,500,900,1000};
//Funkcje które zwracaja indexy z tablic
int findInt(int number){
            for (int i=0;i<ARRSIZE;i++){
                    if (ArabRzym::Arabskie[i] == number)
                            return i;
            }
            return -1;
    }
int findLetter(string letter){
            for (int i=0;i<ARRSIZE;i++){
                    if (ArabRzym::Rzymskie[i]==letter)
                            return i;
            }
            return -1;
    }
bool czyRzymskie(string a){
    for(int i=0;i<a.length();i++){
        if(findLetter(string(1,a[i]))==-1){
            return false;
        }
    }
    return true;
}
int smallerIntIndex(int number){
    for(int i=ARRSIZE-1;i>0;i--){
        if(ArabRzym::Arabskie[i]<=number){
            return i;
        }
    }
    return 0;
}
//Metody ArabRzym
string ArabRzym::arab2rzym (int arab) {
    string a = "";
    while(arab!=0){
        int liczba = arab;
        int smallerLiczba = smallerIntIndex(arab);
        arab -= Arabskie[smallerLiczba];
        a += Rzymskie[smallerLiczba];
    }
    return a;
}
int ArabRzym::rzym2arab (string rzym) throw (ArabRzymException) {
            int liczba = 0;
            for(int i=0;i<rzym.length();i++){
                string pom = string(1,rzym[i]);
                string pom2 = "";
                if(!(i+1==rzym.length())){
                    pom2 = string(1,rzym[i+1]);
                }
                string connPom = pom + pom2;
                int pomIndex = findLetter(connPom);
                if(pomIndex!=-1){
                    liczba += ArabRzym::Arabskie[pomIndex];
                    i++;
                }else{
                    int firstIndex = findLetter(pom);
                    int secondIndex = findLetter(pom2);
                    if(firstIndex>=secondIndex || secondIndex == -1){
                        liczba += ArabRzym::Arabskie[firstIndex];
                    }else{
                        throw ArabRzymException();
                    }
                }
            }

            string pom2 = arab2rzym(liczba);
            if(pom2!=rzym){
                throw ArabRzymException();
            }
            return liczba;
        }

bool CzyLiczba(char *str){
    while(*str){
        if(!isdigit(str[0])){
            return false;
        }
        str++;
    }
return true;
}

//MAIN
int main(int argc, char *argv[]){
    for(int i=1;i<argc;i++){
        if(CzyLiczba(argv[i])){
            cout << argv[i] << " - " << ArabRzym::arab2rzym(atoi(argv[i]));
            cout << endl;
        }else{
            try{
                cout << argv[i] << " - " << ArabRzym::rzym2arab(argv[i]);
                cout << endl;
            }catch(ArabRzymException ex){
                cout << argv[i] << " - " << "Niepoprawny zabis rzymski";
                cout << endl;
            }
        }
    }
    return 0;
}
