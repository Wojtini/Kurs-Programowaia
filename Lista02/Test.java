public class Test {
    public static void main(String args[]) {
        for(int i=0;i<args.length;i++){
            try {
                int n=Integer.parseInt(args[i]);
                try{
                    System.out.println(n + " - " + ArabRzym.arab2rzym(n));
                }catch(ArabRzymException ex){System.out.println("Nieznany blad");}
            }
            catch (NumberFormatException ex) {
                if(czyRzymskie(args[i])){
                    try{
                        System.out.println(args[i] + " - " + ArabRzym.rzym2arab(args[i]));
                    }catch(ArabRzymException ex2){
                        System.out.println(args[i] + " " + ex2.getMessage());
                    }
                }else{
                    System.out.println(args[i] + " - Nieprawidlowa dana");
                }
            }
        }
    }

    public static boolean czyRzymskie(String a){
        for(int i=0;i<a.length();i++){
            if(findLetter(ArabRzym.Rzymskie,Character.toString(a.charAt(i)))==-1){
                return false;
            }
        }
        return true;
    }
    //Funkcje* które zwracaj¹ indexy z tablic
    public static int findInt(int[] tab, int number){
            for (int i=0;i<tab.length;i++){
                    if (tab[i] == number)
                            return i;
            }
            return -1;
    }
    public static int findLetter(String[] tab, String letter){
            for (int i=0;i<tab.length;i++){
                    if (tab[i].equals(letter))
                            return i;
            }
            return -1;
    }
    //Funkcja* zwracaj¹ca najmniejsz¹ liczbê a z danej tablicy mniejsz¹ od b
    public static int smallerIntIndex(int[] tab, int number){
        for(int i=tab.length-1;i>0;i--){
            if(tab[i]<=number){
                return i;
            }
        }
        return 0;
    }
    //ArabRzym classes
    public static class ArabRzym {
        private static String[] Rzymskie = { "I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};
        private static int[] Arabskie = { 1,4,5,9,10,40,50,90,100,400,500,900,1000};
        public static int rzym2arab (String rzym) throws ArabRzymException {
            int liczba = 0;
            for(int i=0;i<rzym.length();i++){
                String pom = Character.toString(rzym.charAt(i));
                
                String pom2 = "";
                
                for(int j=i+2;j<rzym.length();j++){
                    pom2 = Character.toString(rzym.charAt(j));
                    if(findLetter(Rzymskie,pom)<findLetter(Rzymskie,pom2)){
                        throw new ArabRzymException("Niepoprawny zapis Arabski");
                    }
                }
                
                if(!(i+1==rzym.length())){
                    pom2 = Character.toString(rzym.charAt(i+1));
                }
                String connPom = pom + pom2;

                int pomIndex = findLetter(Rzymskie,connPom);
                if(pomIndex!=-1){
                    liczba += Arabskie[pomIndex];
                    i++;
                }else{
                    int firstIndex = findLetter(Rzymskie,pom);
                    int secondIndex = findLetter(Rzymskie,pom2);
                    if(firstIndex>=secondIndex || secondIndex == -1){
                        liczba += Arabskie[firstIndex];
                        //System.out.println(Arabskie[firstIndex]);
                    }else{
                        throw new ArabRzymException("Niepoprawny zapis Arabski");
                        //liczba += Arabskie[secondIndex] - Arabskie[firstIndex]; -- Powinno byæ w pierwszym warunku
                        //System.out.println(Arabskie[secondIndex] + " " + Arabskie[firstIndex]);
                    }
                }
            }
            String pom2 = arab2rzym(liczba);
            if(!pom2.equals(rzym)){
                throw new ArabRzymException("Niepoprawny zapis Arabski");
            }
            return liczba;
        }
        public static String arab2rzym (int arab) throws ArabRzymException {
            String a = "";
            while(arab!=0){
                int liczba = arab;
                int smallerLiczba = smallerIntIndex(Arabskie,arab);
                arab -= Arabskie[smallerLiczba];
                //System.out.println(smallerLiczba);
                a += Rzymskie[smallerLiczba];
            }
            return a;
        }
    }
    public static class ArabRzymException extends Exception{
        public ArabRzymException(String message){
            super(message);
        }
    }

}
