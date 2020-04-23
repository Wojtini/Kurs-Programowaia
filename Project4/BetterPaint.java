package wm.betterpaint;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Wojciech Maziarz
 */
public class BetterPaint extends Frame {
    
    static Plotno MainPlotno;
    static String option = "none";
    static Point pressedPos;
    static Point releasedPos;
    static Shape[] allFigures = new Shape[50];
    static Shape selectedFigure;
    static int wasPressLMB = 0;
    static int NumberOfActiveFigures=0;
    static Color selectedColor = Color.WHITE;
    
    public BetterPaint() {
        initWindow();
    }
    
    private void initWindow() {   
        setTitle("Basic shapes");
        setSize(500, 500);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapterDemo());
        setLayout(new BorderLayout());
        initUI();
    }
    
    private void initUI(){
        MainPlotno = new Plotno();
        add(MainPlotno,BorderLayout.CENTER);
        
        ToolsPanel ToolsBar = new ToolsPanel();
        add(ToolsBar,BorderLayout.EAST);
    }
    

    public static void main(String[] args) {
        
        BetterPaint ex = new BetterPaint();
        ex.setVisible(true);
    }
  

    class WindowAdapterDemo extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    class ToolsPanel extends Panel{ //Stores ToolBoxes
        public ToolsPanel(){
            setBackground(Color.LIGHT_GRAY);
            setLayout(new GridLayout(3,1));
            ToolBox CreationBox = new ToolBox("Create Shapes",Color.RED);
            //Tests
            CreationBox.addButton("Select",Color.RED,new OnSelectButtonPress());
            CreationBox.addButton("Rectangle",Color.RED,new OnSelectButtonPress());
            CreationBox.addButton("Circle",Color.RED,new OnSelectButtonPress());
            CreationBox.addButton("Triangle",Color.RED,new OnSelectButtonPress());
            //CreationBox.addButton("Select",Color.RED);
            add(CreationBox);
            //Tests
            ToolBox SaveLoadBox = new ToolBox("Save/Load",Color.BLUE);
            SaveLoadBox.addButton("Save",Color.BLUE,new SaveButtonPress());
            SaveLoadBox.addButton("Load",Color.BLUE,new LoadButtonPress());
            add(SaveLoadBox);
            
            //ToolBox FreeBox = new ToolBox("Not annouced",Color.GRAY);
            //add(FreeBox);
            ToolBox ProgramBox = new ToolBox("Program Info",Color.GREEN);
            add(ProgramBox);
        }
    }

    class ToolBox extends Panel{
        public int OptionsLen = 1;
        public ToolBox(String title,Color color){
            setBackground(color);
            setLayout(new GridLayout(OptionsLen,1));
            Label Title = new Label(title);
            add(Title);
        }
        public void addButton(String label,Color color,ActionListener listener){
            OptionsLen+=1;
            setLayout(new GridLayout(OptionsLen,1));
            SelectToolButton a = new SelectToolButton(label,color,listener);
            add(a);
        }
    }

    class Plotno extends Canvas{
        public Plotno(){
            setBackground(Color.CYAN);
            addMouseListener(new MouseControl());
            addMouseWheelListener(new ScaleHandler());
            
            // Tworzenie obiektu odpowiedzialnego za obsluge myszy
            MovingAdapter ma = new MovingAdapter();

            // Wywolanie metod odpowiedzialnych za dodawanie obslugi myszy
            addMouseMotionListener(ma);
            addMouseListener(ma);
        }
        @Override
        public void paint(Graphics g) {  
            Graphics2D g2 = (Graphics2D) g;
            for(int i=0;i<NumberOfActiveFigures;i++){
                Shape a = allFigures[i];
                g2.setPaint(GetFigureColor(a));
                g2.fill(a);
            }
            
            //System.out.println("Repainted");
        }
    }

    class SelectToolButton extends Button{
        private String ButtonTitle = null;
        public SelectToolButton(String title, Color color,ActionListener listener){
            ButtonTitle = title;
            setBackground(color);
            setLabel(title);
            addActionListener(listener);
        }
        public void Pressed(){
            option = ButtonTitle;
            System.out.println(ButtonTitle);
        }
    }
    
    class SaveButtonPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
    
    class LoadButtonPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
    class ScaleHandler implements MouseWheelListener {
        
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            doScale(e);
        }
        
        private void doScale(MouseWheelEvent e) {
            
            int x = e.getX();
            int y = e.getY();

            if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                if (selectedFigure!= null) {        
                    int amount =  e.getWheelRotation() * 5;
                    try{
                        Rectangle2 a = (Rectangle2) selectedFigure; 
                        a.addHeight(amount);
                        a.addWidth(amount);        
                    }catch(Exception ex3){
                        try{
                            Circle2 b = (Circle2) selectedFigure;
                            b.addHeight(amount);
                            b.addWidth(amount);
                        }catch(Exception ex4){
                            Triangle2 c = (Triangle2) selectedFigure;
                            c.addHeight(amount);
                            c.addWidth(amount);
                        }
                    }
                    MainPlotno.repaint();
                }
            }            
        }
    }
    
    class OnSelectButtonPress implements ActionListener {
        public void actionPerformed(ActionEvent e) { 
            SelectToolButton selectedButton = (SelectToolButton) e.getSource();
            selectedButton.Pressed();
        }
    }
    class MovingAdapter extends MouseAdapter {

        private int x;
        private int y;

        // Po nacisnieciu myszy zapamietane sa wspolrzedne
        @Override
        public void mousePressed(MouseEvent e) {   
            x = e.getX();
            y = e.getY();
        }

        // Przy przesuwaniu myszy uruchamia sie metoda doMove
        @Override
        public void mouseDragged(MouseEvent e) {
            doMove(e);
        }   
        
        private void doMove(MouseEvent e) {
            
            int dx = e.getX() - x;
            int dy = e.getY() - y;

            // Jesli nacisnelismy na prostokat
            //System.out.println(User.selectedFigure);
            
            if (selectedFigure!=null && "none".equals(option)) {
                //System.out.println("Przesuwanko");
                MoveFigure(selectedFigure,dx,dy);
                MainPlotno.repaint();
            }

            x += dx;
            y += dy;            
        }
    }
    public void MoveFigure(Shape shape,int dx,int dy){
        //User.selectedFigure.addX(dx);
        //User.selectedFigure.addY(dy);
        try{
                Circle2 b = (Circle2) shape;
                b.addX(dx);
                b.addY(dy);
            }catch(Exception ex1){
                try{
                    Triangle2 k = (Triangle2) shape;
                    k.addX(dx);
                    k.addY(dy);
                }catch(Exception ex2){
                    Rectangle2 c = (Rectangle2) shape;
                    c.addX(dx);
                    c.addY(dy);
                }
            }
    }
    class MouseControl implements MouseListener{
        public void mouseExited(MouseEvent e){
        }
        @Override
        public void mouseEntered(MouseEvent e){
        }
        @Override
        public void mouseReleased(MouseEvent e){
            if(wasPressLMB == 1){
                wasPressLMB = 0;
                int x = e.getX();
                int y = e.getY();

                releasedPos = new Point(x,y);
                int startX = Math.min(x,pressedPos.x);
                int startY = Math.min(y,pressedPos.y);
                int lenX = Math.abs(pressedPos.x-x);
                int lenY = Math.abs(pressedPos.y-y);
                Draw(startX,startY,lenX,lenY);
                MainPlotno.repaint();               
            }
        }
        @Override
        public void mousePressed(MouseEvent e){
            if( e.getModifiersEx()!=InputEvent.BUTTON3_DOWN_MASK){ //Jesli wcisniety zostal LPM
                if("Select".equals(option)){
                    Select(e.getX(), e.getY());
                }
                wasPressLMB = 1;
                int x = e.getX();
                int y = e.getY();
                pressedPos = new Point(x,y);
            }else{ //Prawy przycisk myszy
                //System.out.println("right click");
                if(selectedFigure != null){
                    ColorChooseMenu pom = new ColorChooseMenu();
                    MainPlotno.removeMouseListener(this);
                }
            }
        }
        @Override
        public void mouseClicked(MouseEvent e){
        }
    }
    public static void SetFigureColor(Shape shape,Color color){
         try{
            Circle2 b = (Circle2) shape;
            b.color = color;
        }catch(Exception ex1){
            try{
                Triangle2 k = (Triangle2) shape;
                k.color = color;
            }catch(Exception ex2){
                Rectangle2 c = (Rectangle2) shape;
                c.color = color;
            }
        }
    }
        public void Draw(int x,int y,int lenx,int leny){
            switch(option){
                case "Rectangle":
                    Rectangle2 newFigure = new Rectangle2(x,y,lenx,leny);
                    addFigure(newFigure);
                    break;
                case "Triangle":
                    Triangle2 newTriangle = new Triangle2(x,y,lenx,leny);
                    addFigure(newTriangle);
                    break;
                case "Circle":
                    Circle2 newCircle = new Circle2(x,y,lenx,leny);
                    addFigure(newCircle);
                    break;
                case "none":              
                    break;
            }
            option = "none";
        }
        public static void Select(int x,int y){
            selectedFigure = null;
            for(int i=0;i<NumberOfActiveFigures;i++){
                Shape a = allFigures[i];
                if(a.getBounds2D().contains(x,y)){
                    selectedFigure = a;
                }
            }
            System.out.println("SELECTED:" + selectedFigure);
            option = "none";
        }
        public static void addFigure(Shape s){
            allFigures[NumberOfActiveFigures] = s;
            NumberOfActiveFigures++;
        }
        
    public Color GetFigureColor(Shape shape){
        try{
            Circle2 b = (Circle2) shape;
            return b.color;
        }catch(Exception ex1){
            try{
                Triangle2 k = (Triangle2) shape;
                return k.color;
            }catch(Exception ex2){
                Rectangle2 c = (Rectangle2) shape;
                return c.color;
            }
        }
    }
    class ColorChooseMenu extends Frame{
        TextField R,G,B;
        public ColorChooseMenu(){
            initWindow();
        }
        private void initWindow() {   
            setTitle("Basic shapes");
            setSize(100, 200);
            setLocationRelativeTo(null);
            initUI();
            setVisible(true);
        }
        private void initUI(){
            setLayout(new GridLayout(4,1));
            R = new TextField();
            G = new TextField();
            B = new TextField();
            R.setBackground(Color.RED);
            B.setBackground(Color.BLUE);
            G.setBackground(Color.GREEN);
            add(R);
            add(G);
            add(B);
            Button przycisk = new Button("Zatwierdz");
            add(przycisk);
            przycisk.addActionListener(new RGBchoose());
        }
        class RGBchoose implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                int intR = Integer.parseInt(R.getText());
                int intG = Integer.parseInt(G.getText());
                int intB = Integer.parseInt(B.getText());
    //            for(int i=0;i<BetterPaint.User.NumberOfActiveFigures;i++){
                   //BetterPaint.User.allFigures[i]
                   System.out.println(selectedFigure);
                   SetFigureColor(selectedFigure, new Color(intR,intG,intB));
    //            }
                MainPlotno.repaint();
                setVisible(false);
                MainPlotno.addMouseListener(new MouseControl());
            }
        }
    }
    class Rectangle2 extends Rectangle2D.Float{
        Color color = Color.WHITE;
        public Rectangle2(int x, int y, int width, int height) {
           setFrame(x, y, width, height);
        }
        public void addWidth(int w) {
            this.width += w;
        }
        public void addHeight(int h) {     
            this.height += h;
        }
        public void addX(int dx){
            this.x += dx;
        }
        public void addY(int dy){
            this.y += dy;
        }
    }
    class Circle2 extends Ellipse2D.Float{
        Color color = Color.WHITE;
        public Circle2(int x, int y, int width, int height) {
           setFrame(x, y, width, height);
        }
        public void addWidth(int w) {
            this.width += w;
        }
        public void addHeight(int h) {     
            this.height += h;
        }
        public void addX(int dx){
            this.x += dx;
        }
        public void addY(int dy){
            this.y += dy;
        }
    }

    class Triangle2 extends Polygon{
        Color color = Color.WHITE;
        public Triangle2(int x, int y, int width, int height) {
           addPoint(x,y);
           addPoint(x+width,y+height);
           addPoint(x+width,y);

        }
        public void addWidth(int w) {
            //this.width += w;
            this.xpoints[1]+=w;
            this.xpoints[2]+=w;

        }
        public void addHeight(int h) {     
            //this.height += h;
            this.ypoints[1]+=h;
            //this.ypoints[0]+=h;
        }
        public void addX(int dx){
            for(int i=0;i<this.npoints;i++){
                this.xpoints[i] += dx;
            }
        }
        public void addY(int dy){
            for(int i=0;i<this.npoints;i++){
                this.ypoints[i] += dy;
            }
        }
    }
}