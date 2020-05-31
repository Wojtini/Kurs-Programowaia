import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.awt.event.*;
import java.util.*;
/**
 * 
 * @author Wojciech.Maziarz
 */
public class zad6{
    private Tree<Integer> fullTreeInt;
    private Tree<String> fullTreeString;
    private Tree<Float> fullTreeFloat;
    private ErrorMSG errWindow;
    public Tree currentTree;
    public Window GUI;
     
    public static void main(String[] args) {
        zad6 a = new zad6();
    }
    /**
     * Constructor initiate trees, default tree and GUI
     */
    public zad6(){
        Node<Integer> root = new Node<>(50);
        Node<String> root1 = new Node<>("placeholder");
        Node<Float> root2 = new Node<>(50.0f);
        fullTreeInt = new Tree<>(root);
        fullTreeString = new Tree<>(root1);
        fullTreeFloat = new Tree<>(root2);
        currentTree = fullTreeInt;
        defaultTree();
        errWindow = new ErrorMSG();
        this.GUI = new Window(fullTreeInt);
        
    }
    /**
     * Tree to display when user opens app
     */
    public void defaultTree(){
        fullTreeInt.insertValue(25);
        fullTreeInt.insertValue(75);
        fullTreeInt.insertValue(10);
        fullTreeInt.insertValue(40);
        fullTreeInt.insertValue(60);
        fullTreeInt.insertValue(90);
        fullTreeInt.insertValue(5);
        fullTreeInt.insertValue(8);
        fullTreeInt.insertValue(9);
        fullTreeInt.insertValue(4);
        fullTreeInt.delete(25,fullTreeInt.root);
    }
    /**
     * Node class
     * @param <T> int string and float are only available
     */
    public class Node<T>{
        public T value;
        public Node parent = null;
        public Node leftChild = null;
        public Node rightChild = null;
        /**
         * Creates nodes with specific value
         * @param value int string and float are only available
         */
        public Node(T value){
            this.value = value;
        }
    }
    /**
     * Tree class
     * @param <T> int string and float are only available
     */
    public class Tree<T>{
        Node<T> root;
        /**
         * Creates tree with given node as a root
         * @param root sets given node as root
         */
        public Tree(Node<T> root){
            this.root = root;
        }
        /**
         * Method that check if given value is in tree
         * @param value int string and float are only available
         */
        private Node<T> searchValue(T value){
            return this.search(value,this.root);
        }
        /**
         * Deletes root's children and sets root value
         * @param value int string and float are only available
         */
        public void resetTree(T value){
            root.leftChild = null;
            root.rightChild = null;
            root.value = value;
        }
        /**
         * Search method
         * @param value to find
         * @param currNode - node where algorythm start searching
         * @return 
         */
        private Node<T> search(T value,Node<T> currNode){
            int result;
            try{
                int valueInt = (int) value;
                int currNodeInt = (int) currNode.value;
                result = porownywarka.check(valueInt,currNodeInt);
            }catch(Exception ex){
                try{
                    float valueInt = (float) value;
                    float currNodeInt = (float) currNode.value;
                    result = porownywarka.check(valueInt,currNodeInt);
                }catch(Exception ex2){
                    String valueInt = (String) value;
                    String currNodeInt = (String) currNode.value;
                    result = porownywarka.check(valueInt,currNodeInt);
                }
            }
            if(result==1){
                if(currNode.rightChild!=null){
                    return this.search(value,currNode.rightChild);
                }
            }else if(result==-1){
                if(currNode.leftChild!=null){
                    return this.search(value,currNode.leftChild);
                }
            }else{
                return currNode;
            }
            return null;
        }
        /**
         * Inserts given value
         * @param value value
         */
        public void insertValue(T value){
            this.insert(new Node<>(value), this.root);
        }
        /**
         * Inserts newly created node into currNode
         * @param newNode new Node
         * @param currNode current Node/place to insert
         */
        private void insert(Node<T> newNode,Node<T> currNode){
            int result;
            try{
                int valueInt = (int) newNode.value;
                int currNodeInt = (int) currNode.value;
                result = porownywarka.check(valueInt,currNodeInt);
            }catch(Exception ex){
                try{
                    float valueInt = (float) newNode.value;
                    float currNodeInt = (float) currNode.value;
                    result = porownywarka.check(valueInt,currNodeInt);
                }catch(Exception ex2){
                    String valueInt = (String) newNode.value;
                    String currNodeInt = (String) currNode.value;
                    result = porownywarka.check(valueInt,currNodeInt);
                }
            }
            if(result==1){
                if(currNode.rightChild==null){
                    currNode.rightChild=newNode;
                    newNode.parent = currNode;
                }else{
                    this.insert(newNode,currNode.rightChild);
                }
            }else if(result==-1){
                if(currNode.leftChild==null){
                    currNode.leftChild=newNode;
                    newNode.parent = currNode;
                }else{
                    this.insert(newNode,currNode.leftChild);
                }
            }else{
//                System.out.println("Podana wartosc juz istnieje w drzewie binarnym");
                errWindow.ShowError("Podana wartosc juz istnieje w drzewie");
            }
        }
        /**
         * Deletes node with given value
         * @param value given value
         */
        public void deleteValue(T value){
            this.delete(value,this.root);
        }
        /**
         * Deletes value in searchNodes Children
         * @param value given value
         * @param searchNode node
         */
        private void delete(T value,Node<T> searchNode){
            Node<T> delNode = this.search(value, searchNode);
            if(delNode.parent != null){
                if(delNode.leftChild == null && delNode.rightChild == null){
                    if(delNode.parent.leftChild == delNode){
                        delNode.parent.leftChild = null;
                    }else{
                        delNode.parent.rightChild = null;
                    }
                    delNode.parent = null;
                }else if(delNode.leftChild != null && delNode.rightChild == null){
                    if(delNode.parent.leftChild == delNode){
                        delNode.parent.leftChild = delNode.leftChild;
                        delNode.leftChild.parent = delNode.parent;
                    }else{
                        delNode.parent.rightChild = delNode.leftChild;
                        delNode.leftChild.parent = delNode.parent;
                    }
                    delNode.parent = null;
                }else if(delNode.leftChild == null && delNode.rightChild != null){
                    if(delNode.parent.leftChild == delNode){
                        delNode.parent.leftChild = delNode.rightChild;
                        delNode.rightChild.parent = delNode.parent;
                    }else{
                        delNode.parent.rightChild = delNode.rightChild;
                        delNode.rightChild.parent = delNode.parent;
                    }
                    delNode.parent = null;
                }else{
                    Node<T> pom = minimumNode(delNode.rightChild);
                    delNode.value = pom.value;
                    this.delete(pom.value,delNode.rightChild);
                }
            }
        }
        /**
         * Gets lowest value in given subtree
         * @param Node search minimum value in that Node
         * @return return node 
         */
        public Node<T> minimumNode(Node<T> Node){
            if(Node.leftChild != null){
                return minimumNode(Node.leftChild);
            }else{
                return Node;
            }
        }
    }
    /**
     * Static class to compare values
     */
    static class porownywarka{
        //Integer
        public static int check(int insertedNode,int Node){
            if(insertedNode == Node){
                return 0;
            }
            else if(insertedNode > Node){
                return 1; 
            }else{
                return -1;
            }
        }
        //String
        public static int check(String insertedNode,String Node){
            if(insertedNode.equals(Node)){
                return 0;
            }
            else if(insertedNode.compareTo(Node) > 0){
                return 1; 
            }else{
                return -1;
            }
        }
        //Integer
        public static int check(float insertedNode,float Node){
            if(insertedNode == Node){
                return 0;
            }
            else if(insertedNode > Node){
                return 1; 
            }else{
                return -1;
            }
        }
    }
    /**
     * Creates window/GUI
     * @param <T> 
     */
    class Window<T> extends JFrame{
        public TreeDraw TD;
        public JTextField userValue;
        /**
         * Constructor
         * @param t - default tree to draw
         */
        public Window(Tree<T> t){
            setSize(700,500);
            setLayout(new BorderLayout());
            TD = new TreeDraw(t.root);
            add(TD,BorderLayout.CENTER);
            JPanel test = new JPanel();
            test.setLayout(new GridLayout(6,1));
            userValue = new JTextField("node/new tree value");
            test.add(userValue);
            test.add(new TButton("Search"));
            test.add(new TButton("Insert"));
            test.add(new TButton("Delete"));
            test.add(new TButton("Return to root"));
            JPanel CreationPanel = new JPanel();
            CreationPanel.setLayout(new GridLayout(5,1));
            CreationPanel.add(new JLabel("Create new tree"));
            CreationPanel.add(new TButton("int"));
            CreationPanel.add(new TButton("float"));
            CreationPanel.add(new TButton("string"));
            test.add(CreationPanel);
            add(test,BorderLayout.EAST);
            setVisible(true);
        }
        /**
         * Panel to draw tree into
         */
        class TreeDraw extends JPanel{
            List<Circle2> circleList = new ArrayList<>();
            Node<T> t;
            /**
             * Constructor
             * @param t 
             */
            public TreeDraw(Node<T> t){
                this.t = t;
                this.addMouseListener(new MouseControl());
            }
            /**
             * Paint
             * @param g 
             */
            @Override
            public void paint(Graphics g){
                circleList = new ArrayList<>();
                Graphics2D g2 = (Graphics2D) g;
                drawTree(g2,t);
            }
            /**
             * changes tree that is drawn
             * @param tree 
             */
            public void changeTreeDraw(Tree<T> tree){
                t = tree.root;
            }
            /**
             * Draws tree
             * @param g
             * @param node 
             */
            public void drawTree(Graphics2D g,Node<T> node){
                if(node.parent != null){
                    g.drawLine(this.getWidth()/2,40,this.getWidth()/2,0);
                }
                drawNode(g,node,this.getWidth()/2,40);
                //Drawing Left
                if(node.leftChild!= null){
                    g.drawLine(this.getWidth()/2,40,this.getWidth()/4,80);
                    drawNode(g,node.leftChild,this.getWidth()/4,80);
                    if(node.leftChild.leftChild!= null){
                        g.drawLine(this.getWidth()/4,80,this.getWidth()/8,160);
                        drawNode(g,node.leftChild.leftChild,this.getWidth()/8,160);
                        drawLeafChild(g,node.leftChild.leftChild,this.getWidth()/8,160);
                    }
                    if(node.leftChild.rightChild!= null){
                        g.drawLine(this.getWidth()/4,80,this.getWidth()/8*3,160);
                        drawNode(g,node.leftChild.rightChild,this.getWidth()/8*3,160);
                        drawLeafChild(g,node.leftChild.rightChild,this.getWidth()/8*3,160);
                    }
                }
                //Drawing Right
                if(node.rightChild!= null){
                    g.drawLine(this.getWidth()/2,40,this.getWidth()/4*3,80);
                    drawNode(g,node.rightChild,this.getWidth()/4*3,80);
                    if(node.rightChild.leftChild!= null){
                        g.drawLine(this.getWidth()/4*3,80,this.getWidth()/8*5,160);
                        drawNode(g,node.rightChild.leftChild,this.getWidth()/8*5,160);
                        drawLeafChild(g,node.rightChild.leftChild,this.getWidth()/8*5,160);
                    }
                    if(node.rightChild.rightChild!= null){
                        g.drawLine(this.getWidth()/4*3,80,this.getWidth()/8*7,160);
                        drawNode(g,node.rightChild.rightChild,this.getWidth()/8*7,160);
                        drawLeafChild(g,node.rightChild.rightChild,this.getWidth()/8*7,160);
                    }
                }    
            }
            /**
             * Draws lines under the node at x y position
             * @param g graphics
             * @param node node to check child
             * @param x pos
             * @param y pos
             */
            public void drawLeafChild(Graphics2D g,Node<T> node,int x,int y){
                if(node.rightChild!= null){
                    g.drawLine(x,y,x+40,y+40);
                }
                if(node.leftChild!= null){
                    g.drawLine(x,y,x-40,y+40);
                }
            }
            /**
             * Draws node at x,y pos
             * @param g graphics2D
             * @param node node
             * @param xpos pos
             * @param ypos pos
             */
            public void drawNode(Graphics2D g,Node<T> node,int xpos,int ypos){
                Circle2 newNode = new Circle2(xpos,ypos,node);
                circleList.add(newNode);
                g.setPaint(Color.RED);
                g.fill(newNode);
                g.setPaint(Color.BLACK);
                try{
                    g.drawString(Integer.toString((int) node.value), xpos,ypos);
                }catch(Exception ex){
                    try{
                        g.drawString(Float.toString((float) node.value), xpos,ypos);
                    }catch(Exception ex2){
                        g.drawString((String) node.value, xpos,ypos);
                    }
                }
            }
            /**
             * Node graphical representation
             */
            class Circle2 extends Ellipse2D.Float{
                Node<T> node;
                public int x;
                public int y;
                public Circle2(int x, int y, Node<T> node) {
                   setFrame(x-20, y-20, 40, 40);
                   this.x = x-20;
                   this.y = y-20;
                   this.node = node;
                }
            }
            /**
             * Mouse listener added to nodes graphical representation
             */
            class MouseControl implements MouseListener{
                @Override
                public void mouseExited(MouseEvent e){
                }
                @Override
                public void mouseEntered(MouseEvent e){
                }
                @Override
                public void mouseReleased(MouseEvent e){
                }
                @Override
                public void mousePressed(MouseEvent e){    
                }
                @Override
                public void mouseClicked(MouseEvent e){
                    for(int i=0;i<GUI.TD.circleList.size();i++){
                        Circle2 currCheck = (Circle2) GUI.TD.circleList.get(i);
                        if(currCheck.contains(e.getX(),e.getY())){
                            if(currCheck.node == GUI.TD.t && currCheck.node.parent != null){
                                GUI.TD.t = currCheck.node.parent;
                                GUI.repaint();
                                break;
                            }else{
                                GUI.TD.t = currCheck.node;
                                GUI.repaint();
                                break;
                            }
                        }
                    }
                }
            }
        }
        /**
         * Buttons GUI
         */
        class TButton extends JButton{
            public TButton(String text){
                super(text);
                this.addActionListener(new buttonListener());
                }
            }
            /**
             * Listener checks which button was pressed
             */
            class buttonListener implements ActionListener{
                public void actionPerformed(ActionEvent actionEvent) {
                    JButton button = (JButton)actionEvent.getSource();
                    String label = button.getText();
                    String label2 = userValue.getText();
                    System.out.println("Pressed: " + label);
                    try{
                        if("Insert".equals(label)){
                            if(currentTree == fullTreeInt){
                                int a = Integer.parseInt(label2);
                                fullTreeInt.insertValue(a);
                                System.out.println("Inserted: " + a);                       
                            }else if(currentTree == fullTreeFloat){
                                Float a = Float.parseFloat(label2);
                                fullTreeFloat.insertValue(a);
                                System.out.println("Inserted: " + a);                       
                            }else if(currentTree == fullTreeString){
                                fullTreeString.insertValue(label2);
                                System.out.println("Inserted: " + label2);                       
                            }
                        }
                        if("Delete".equals(label)){
                            if(currentTree == fullTreeInt){
                                int a = Integer.parseInt(label2);
                                fullTreeInt.deleteValue(a);
                                System.out.println("Deleted: " + a);                       
                            }else if(currentTree == fullTreeFloat){
                                Float a = Float.parseFloat(label2);
                                fullTreeFloat.deleteValue(a);
                                System.out.println("Deleted: " + a);                       
                            }else if(currentTree == fullTreeString){
                                fullTreeString.deleteValue(label2);
                                System.out.println("Deleted: " + label2);                       
                            }
                        }
                        if("Search".equals(label)){
                            if(currentTree == fullTreeInt){
                                int a = Integer.parseInt(label2);                    
                                if(fullTreeInt.searchValue(a)!=null){
                                    errWindow.ShowError("Podana wartosc znajduje sie w drzewie"); 
                                }else{
                                    errWindow.ShowError("Nie odnaleziono wartosci");
                                }  
                            }else if(currentTree == fullTreeFloat){
                                Float a = Float.parseFloat(label2);
                                if(fullTreeFloat.searchValue(a)!=null){
                                    errWindow.ShowError("Podana wartosc znajduje sie w drzewie"); 
                                }else{
                                    errWindow.ShowError("Nie odnaleziono wartosci");
                                }   
                                errWindow.ShowError("Podana wartosc juz istnieje w drzewie");                        
                            }else if(currentTree == fullTreeString){
                                if(fullTreeString.searchValue(label2)!=null){
                                    errWindow.ShowError("Podana wartosc znajduje sie w drzewie"); 
                                }else{
                                    errWindow.ShowError("Nie odnaleziono wartosci");
                                }                      
                            }
                        }
                        if("Return to root".equals(label)){
                            GUI.TD.t = currentTree.root;
                        }
                        if("int".equals(label)){
                            int a = Integer.parseInt(label2);
                            currentTree = fullTreeInt;
                            currentTree.resetTree(a);
                            GUI.TD.changeTreeDraw(currentTree);
                        }
                        if("float".equals(label)){
                            Float a = Float.parseFloat(label2);
                            currentTree = fullTreeFloat;
                                System.out.println("xD" + label2);     
                            currentTree.resetTree(a);
                            GUI.TD.changeTreeDraw(currentTree);
                        }
                        if("string".equals(label)){
                            currentTree = fullTreeString;
                            currentTree.resetTree(label2);
                            GUI.TD.changeTreeDraw(currentTree);
                        }
                    }catch(Exception ex){
                        //System.out.println("zly format");
                        errWindow.ShowError("Podana wartosc zostala podana w zlym formacie");
                    }
                    repaint();
            }
        }
    }
    class ErrorMSG extends JFrame{
        private JLabel msg;
        public ErrorMSG(){
            msg = new JLabel();
            this.add(msg);
            this.setSize(700,100);
        }
        public void ShowError(String errmsg){
            this.msg.setText(errmsg);
            this.setVisible(true);
        }
    }
}