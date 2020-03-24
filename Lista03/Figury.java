public class Figury {
    public static void main(String[] args) {
        try{
            if(args[0].equals("o")){
                Kolo a = new Kolo();
                float n=Integer.parseInt(args[1]); 
                a.promien = n;
                a.Obwod();
                a.Pole();
                a.Wypisz();
            }
            else if(args[0].equals("c")){
                Czworokat a = new Czworokat();
                a.bok1 = Float.parseFloat(args[1]);
                a.bok2 = Integer.parseInt(args[2]);
                a.bok3 = Integer.parseInt(args[3]);
                a.bok4 = Integer.parseInt(args[4]);
                a.kat = Integer.parseInt(args[5]);
                a.Wypisz();
            }
            else if(args[0].equals("p")){
                Pieciokat a = new Pieciokat();
                a.bok = Integer.parseInt(args[1]);
                a.Wypisz();
            }
            else if(args[0].equals("s")){
                Szesciokat a = new Szesciokat();
                a.bok = Float.parseFloat(args[1]);
                a.Wypisz();
            }
            else{
                System.out.println("Nie istanieje taka figura");
            }
        }
        catch(ArrayIndexOutOfBoundsException ex){
            System.out.println("Za malo argumentow");
        }
        catch(NumberFormatException ex){
            System.out.println("Jeden z argumentow nie jest liczba");
        }
        
    }
    interface PoleObwod{
        public float Pole();
        public float Obwod();
    }
    public static class Figura implements PoleObwod{
        //public float Pole = 0;
        //public float Obwod = 0;
        @Override
        public float Pole(){
            return 0;
        }
        @Override
        public float Obwod(){
            return 0;
        }
        public void Wypisz(){
            System.out.println(Pole());
            System.out.println(Obwod());
        }
    }
    public static class Czworokat extends Figura{
        public float bok1,bok2,bok3,bok4,kat;
        @Override
        public float Obwod(){
            return bok1+bok2+bok3+bok4;
        }
        @Override
        public float Pole(){
            return (float) (bok1*bok1*Math.sin(Math.toRadians(kat)));
        }
    } //DONE
    public static class Kwadrat extends Czworokat{
        @Override
        public float Pole(){
            return bok1*bok2;
        }
    } //DONE
    public static class Prostokat extends Czworokat{
        @Override
        public float Pole(){
            if(bok1==bok2){
                return bok1*bok3;
            }else{
                return bok1*bok2;
            }
            
        }
    } //DONE
    public static class Romb extends Czworokat{
        @Override
        public float Pole(){
            return (float) (bok1*bok1*Math.sin(kat));
        }
    } //DONE
    public static class Kolo extends Figura{
        public float promien;
        @Override
        public float Pole(){
            return ((float) Math.PI*promien*promien);
        }
        @Override
        public float Obwod(){
            return ((float) Math.PI*2*promien);
        }
    } //DONE
    public static class Pieciokat extends Figura{
        public float bok;
        @Override
        public float Pole(){
            return (float) ((5/4)*bok*bok*(1/Math.tan(Math.toRadians(36.0))));
        }
        @Override
        public float Obwod(){
            return 5*bok;
        }
    } //DONE
    public static class Szesciokat extends Figura{
        public float bok;
        @Override
        public float Pole(){
            return (float) ((3*bok*bok*Math.sqrt(3))/2);
        }
        @Override
        public float Obwod(){
            return 6*bok;
        }
    } //DONE
}
