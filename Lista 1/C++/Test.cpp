#include <iostream>
#include <vector>
#include <cstdlib>
using namespace std;

class LiczbyPierwsze{
public:
    vector<int> Pierwsze;
    int dlugosc;
    int IloscPierwszych;
    public :~LiczbyPierwsze(){
        cout << "Usuniêto Obiekt";
        cout << endl;
    }
    public :LiczbyPierwsze(int n){
        Pierwsze.resize(n);
        dlugosc=n;
        IloscPierwszych = 0;
        Pierwsze[0] = 0;
        Pierwsze[1] = 0;
        for(int i=2;i<n;i++){
            Pierwsze[i] = 1;
        }
        for(int i=2;i<n;i++){
            if(Pierwsze[i]==1){
                IloscPierwszych++;
                for(int j=i*i;j<n;j=j+i){
                    Pierwsze[j]=0;
                }
            }
        }
    }
    public :Liczba(int m){
        for(int i=2;i<dlugosc;i++){
            if(Pierwsze[i]==1){
                if(m==0){
                    return i;
                }else{
                    m--;
                }
            }
        }
    }
    public :Wypisz(){
        for(int i=2;i<dlugosc;i++){
            if(Pierwsze[i]==1){
                cout << i;
                cout << endl;
            }
        }
    }
};

bool CzyLiczba(char *str){
    while(*str){
        if(!isdigit(str[0])){
            return false;
        }
        str++;
    }
return true;
}

int main(int argc, char *argv[]){
    if(atoi(argv[1])<0 || !CzyLiczba(argv[1])){
        cout << argv[1];
        cout << "- Nieprawid³owy zakres";
        return 0;
    }
    LiczbyPierwsze a = LiczbyPierwsze(atoi(argv[1]));
    for(int i=2;i<argc;i++){
        int k = atoi(argv[i]);
        if(!CzyLiczba(argv[i])){
            cout << argv[i];
            cout << " - Nieprawidlowa dana";
        }
        else if(k>a.IloscPierwszych){
            cout << k;
            cout << " - Liczba spoza zakresu";
        }else{
            cout << k;
            cout << " - ";
            cout << a.Liczba(k);
        }
        cout << endl;
    }
    return 0;
}
