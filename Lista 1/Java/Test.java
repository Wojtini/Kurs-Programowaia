public class Test {
    public static void main(String[] args) {
        LiczbyPierwsze a;
        try {
                int n=Integer.parseInt(args[0]);
                if(n>=0){
                    a = new LiczbyPierwsze(n);
                    for(int i=1;i<args.length;i++){
                        try {
                            int m=Integer.parseInt(args[i]);
                            if(m<a.IloscPierwszych && m>=0){
                                System.out.println(m + " - " + a.liczba(m));
                            }else{
                                System.out.println(m + " - " + "liczba spoza zakresu");
                            }
                        }
                        catch (NumberFormatException ex) {
                            System.out.println(args[i] + " - Nieprawid³owa dana");
                        }
                    }
                }else{
                    System.out.println(args[0] + " Nieprawid³owy zakres");
                }
            }
        catch (NumberFormatException ex) {
            System.out.println(args[0] + " Nieprawid³owy zakres");
        }
    }
    public static class LiczbyPierwsze{
        int[] Pierwsze;
        int IloscPierwszych;
        public LiczbyPierwsze(int n){
            n=n+1;
            int[] tab;
            tab = new int[n];
            tab[0] = 0;
            tab[1] = 0;
            for(int i=2;i<n;i++){ //Wypelnienie tablicy jedynkami
                tab[i]=1;
            }
            for(int i=2;i<n;i++){ //Sito
                if(tab[i]==1){
                    IloscPierwszych++;
                    for(int j=i+i;j<n;j=j+i){
                        tab[j]=0;
                    }
                }
            }
            Pierwsze = new int[IloscPierwszych];
            int pom=0;
            for(int i=2;i<n;i++){ //Wypisanie l. pierwszych do tablicy
                //System.out.println(i+" "+ tab[i]);
                if(tab[i]==1){
                    Pierwsze[pom] = i;
                    pom++;
                }
            }
        }
        public void WypiszPierwsze(){
            for(int i=0;i<IloscPierwszych;i++){
                System.out.println(Pierwsze[i]);
            }
        }
        public int liczba(int m){
            return Pierwsze[m];
        }
    }
}

