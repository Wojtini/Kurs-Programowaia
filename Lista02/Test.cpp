#include <iostream>
#include <string>
#include <exception>
using namespace std;

class ArabRzym{
public:
    static string Rzymskie[13];
    static int Arabskie[13];
};
string ArabRzym::Rzymskie[] = { "I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};;
int ArabRzym::Arabskie[] = { 1,4,5,9,10,40,50,90,100,400,500,900,1000};;



int main(){
    return 0;
}
