//package wm.betterpaint;
//Basic awt imports
//import java.awt.*;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.TextField;
import java.awt.Polygon;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Label;
//Event imports
//import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
//Geom imports
//import java.awt.geom.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
//Rest
import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 *
 * @author Wojciech Maziarz
 */
public class BetterPaint extends Frame {
    
    Plotno MainPlotno;
    String option = "none";
    Point pressedPos;
    Point releasedPos;
    Shape[] allFigures = new Shape[50];
    Shape selectedFigure;
    int wasPressLMB = 0;
    int NumberOfActiveFigures=0;
    Color selectedColor = Color.WHITE;
    //Okienka
    TextWindow InfoWindow = new TextWindow();
    TextWindow InstWindow = new TextWindow();
    ColorChooseMenu ColorMenu = new ColorChooseMenu();
    
    TextField saveloadName = new TextField("nazwa_pliku");
    /**
    * Konstruktor g³ówny, inicjuje wszystkie okienka
    */
    public BetterPaint() {
        initWindow("Basic shapes",500,500);
    }
    
    private void initWindow(String title,int width,int height) {   
        setTitle(title);
        setSize(width, height);
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
        
        InitInstWindow();
        InitInfoWindow();
    }
    
    private void InitInstWindow(){
        InstWindow.AddText("Instrukcja uzywania programu:");
        InstWindow.AddText("Kliknij wybrany figura i narysuj ja na plotnie");
        InstWindow.AddText("Aby zaznaczya figura jako aktywna kliknij opcja");
        InstWindow.AddText("SELECT a nastepnie kliknij na figura");
        InstWindow.AddText("Aby zmienic kolor aktywnej figury wcisnij PRAWY");
        InstWindow.AddText("PRZYCISK MYSZY. Aby zapisac/wczytac plotno");
        InstWindow.AddText("kliknij przycisk SAVE/LOAD");
    }
    private void InitInfoWindow(){
        InfoWindow.AddText("Program: BetterPaint");
        InfoWindow.AddText("Autor: Wojciech Maziarz");
        InfoWindow.AddText("Program zostal‚ stworzony jako zadanie na kurs:");
        InfoWindow.AddText("KURS PROGRAMOWANIA.");
    }
    public static void main(String[] args) {
        
        BetterPaint ex = new BetterPaint();
        ex.setVisible(true);
    }
    /**
     * Save current figures to filename
     * @param filename
     * @throws IOException 
     */
    public void SaveFigures (String filename) throws IOException{
        FileOutputStream f = new FileOutputStream(new File(filename));
	ObjectOutputStream o = new ObjectOutputStream(f);
	o.writeObject(allFigures);
	o.close();
        
        FileOutputStream f2 = new FileOutputStream(new File(filename + "2"));
	ObjectOutputStream o2 = new ObjectOutputStream(f2);
	o2.writeObject(NumberOfActiveFigures);
	o2.close();
    }
    /**
     * Saves current figures list
     * @param filename
     * @throws IOException 
     */
    public void LoadFigures (String filename) throws IOException{
        try{
            selectedFigure = null;
            
            FileInputStream fi = new FileInputStream(new File(filename));
            ObjectInputStream oi = new ObjectInputStream(fi);
            allFigures = (Shape[]) oi.readObject();
            MainPlotno.repaint();
            
            FileInputStream fi2 = new FileInputStream(new File(filename+"2"));
            ObjectInputStream oi2 = new ObjectInputStream(fi2);
            NumberOfActiveFigures = (int) oi2.readObject();
        }catch(ClassNotFoundException ex3){
            System.out.println("Plik jest uszkodzony i nie mogl zostac wczytany");
        }
    }
    
    class WindowAdapterDemo extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
    /**
    * ToolsPanel is panel that can hold toolboxes
    */
    class ToolsPanel extends Panel{
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
            SaveLoadBox.addTextArea(saveloadName);
            SaveLoadBox.addButton("Save",Color.BLUE,new SaveButtonPress());
            SaveLoadBox.addButton("Load",Color.BLUE,new LoadButtonPress());
            add(SaveLoadBox);
            
            //ToolBox FreeBox = new ToolBox("Not annouced",Color.GRAY);
            //add(FreeBox);
            ToolBox ProgramBox = new ToolBox("Program Info",Color.GREEN);
            ProgramBox.addButton("Instrukcja", Color.GREEN, new InstructionButtonListener());
            ProgramBox.addButton("Info", Color.GREEN, new InfoButtonListener());
            add(ProgramBox);
        }
    }
    /**
     * Toolbox class that can hold various buttons
     */
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
        public void addTextArea(TextField object){
            OptionsLen+=1;
            setLayout(new GridLayout(OptionsLen,1));
            add(object);
        }
    }
    /**
     * Canvas that user can draw onto
     */
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
        /**
         * paint all figures stored in allFigures array
         * @param g graphics
         */
        @Override
        public void paint(Graphics g) {  
            Graphics2D g2 = (Graphics2D) g;
            for(int i=0;i<NumberOfActiveFigures;i++){
                Shape a = allFigures[i];
                g2.setPaint(GetFigureColor(a));
                g2.fill(a);
            }
        }
    }
    /**
     * Button that allow user to select which figure he wants to create
     */
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
    /**
     *  NOT IMPLEMENTED
     */
    class SaveButtonPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                SaveFigures(saveloadName.getText());
                System.out.println("Zapisano plik");
            }catch(IOException except){
                System.out.println("Blad w zapisywaniu pliku");
            }
        }
    }
    /**
     * Loading button listener
     */
    class LoadButtonPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                LoadFigures(saveloadName.getText());
                System.out.println("Wczytano plik:");
            }catch(IOException except){
                System.out.println("Blad w wczytywaniu pliku");
            }
        }
    }
    /**
     * Listener that focuses on scaling selected figure with mousewheel
     */
    class InstructionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            InstWindow.Open();
        }
    }
    class InfoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            InfoWindow.Open();
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
    /**
     * Listener that checks which figure user wants to draw
     */
    class OnSelectButtonPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { 
            SelectToolButton selectedButton = (SelectToolButton) e.getSource();
            selectedButton.Pressed();
        }
    }
    /**
     * Adapter that focuses on moving selected figure around canvas
     */
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
    /**
     * Moves figure along mouse cursor 
     * @param shape
     * @param dx
     * @param dy 
     */
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
        @Override
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
                    ColorMenu.Open();
                    MainPlotno.removeMouseListener(this);
                }
            }
        }
        @Override
        public void mouseClicked(MouseEvent e){
        }
    }
    /**
     * Sets color of a given figure
     * @param shape
     * @param color 
     */
    public void SetFigureColor(Shape shape,Color color){
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
    /**
     * Draws a shape defined in "BettarPaint.option" variable
     * @param x - x position
     * @param y - y position
     * @param lenx - length along x axis(width)
     * @param leny - length along y axis(heigth)
     */
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
        /**
         * Method select the figure at given (x,y) point
         * always select the figure on top and also only one can be selected
         * at a given time
         * @param x - int x point
         * @param y - int y point
         */
        public void Select(int x,int y){
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
        /**
         * Stores figure in Shape BetterPaint.allFigures array
         * @param s Figure to be added
         */
        public void addFigure(Shape s){
            allFigures[NumberOfActiveFigures] = s;
            NumberOfActiveFigures++;
        }
        /**
         * Methotd returns color of a given shape
         * @param shape
         * @return Color
         */
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
            this.addWindowListener(new WindowAdapterDemo());
        }
        public void Open(){
            setVisible(true);
        }
        private void initWindow() {   
            setTitle("Basic shapes");
            setSize(100, 200);
            setLocationRelativeTo(null);
            initUI();
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
                SetColor(intR,intG,intB);
                setVisible(false);
            }
        }
    }
    /**
     * Sets color of the selected figure
     * @param R
     * @param G
     * @param B 
     */
    public void SetColor(int R,int G,int B){
        try{
            if(R>=0 && R<=255 && G>=0 && G<=255 && B>=0 && B<=255){
                SetFigureColor(selectedFigure, new Color(R,G,B));
                MainPlotno.repaint();
                MainPlotno.addMouseListener(new MouseControl());           
            }               
        }catch(Exception ex5){
            System.out.println("Nieprawidlowa dana");
        }
    }
    class Rectangle2 extends Rectangle2D.Float implements Serializable{
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
    class Circle2 extends Ellipse2D.Float implements Serializable{
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

    class Triangle2 extends Polygon implements Serializable{
        Color color = Color.WHITE;
        public Triangle2(int x, int y, int width, int height) {
           addPoint(x,y);
           addPoint(x+width,y+height);
           addPoint(x+width,y);

        }
        public void addWidth(int w) {
            this.xpoints[1]+=w;
            this.xpoints[2]+=w;

        }
        public void addHeight(int h) {
            this.ypoints[1]+=h;
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
    class TextWindow extends Frame{
        private int x = 300;
        private int y = 300;
        private String[] texts = new String[10];
        private int NumberOfTexts = 0;
        private TextCanvas TC = new TextCanvas();
        public TextWindow(){
            setSize(x,y);
            setResizable(false);
            //setVisible(true);
            setBackground(Color.RED);
            addWindowListener(new WindowAdapterPI());
            TC.setBackground(Color.WHITE);
            add(TC);
        }
        public void Open(){
            setVisible(true);
        }
        public void AddText(String newText){
            NumberOfTexts++;
            texts[NumberOfTexts]=newText;
            TC.repaint();
        }
        class WindowAdapterPI extends WindowAdapter{
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }    
        }
        class TextCanvas extends Canvas{
            @Override
            public void paint(Graphics g){
                paintComponent(g);
            }
            public void paintComponent(Graphics g) {
                for(int i=0;i<texts.length;i++){
                    String a = texts[i];
                    if(a!=null){
                        g.drawString(texts[i], 10, i*15+15);
                    }
                }
            }
        }
    }
}