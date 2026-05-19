import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Node {
    private char v;
    private Node l;
    private Node r;

    public Node(char value) {
        v = value;
        l = null;
        r = null;
    }

    public char getValue() { return v; }
    public Node getLeft() { return l; }
    public Node getRight() { return r; }

    public void setLeft(Node node) { l = node; }
    public void setRight(Node node) { r = node; }

    public void print() {
        System.out.print(v);
    }
}

class Tree {
    private Node h;

    public Tree(Node root) {
        h = root;
    }

    public Node getRoot() { return h; }

    public void preOrder(Node node) {
        node.print();
        if(node.getLeft() != null) preOrder(node.getLeft());
        if(node.getRight() != null) preOrder(node.getRight());
    }

    public void inOrder(Node node) {
        if(node.getLeft() != null) inOrder(node.getLeft());
        node.print();
        if(node.getRight() != null) inOrder(node.getRight());
    }

    public void postOrder(Node node) {
        if(node.getLeft() != null) postOrder(node.getLeft());
        if(node.getRight() != null) postOrder(node.getRight());
        node.print();
    }
}

public class Main {
    public static final char EMPTY = '.';

    public static int toIndex(char node) {
        if(node == EMPTY) return -1;
        else return (int) (node - 'A');
    }

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Node[] arr = new Node[n];
        int i;
        char input;
        for(i=0; i<n; i++) arr[i] = new Node((char) ('A' + i));
        Tree t = new Tree(arr[0]); //'A'가 루트 노드가 됨
        for(i=0; i<n; i++) {
            String line = br.readLine();
            input = line.charAt(0);
            Node parent = arr[toIndex(input)];
            input = line.charAt(2);
            if(input != EMPTY) {
                Node left = arr[toIndex(input)];
                parent.setLeft(left);
            }
            input = line.charAt(4);
            if(input != EMPTY) {
                Node right = arr[toIndex(input)];
                parent.setRight(right);
            }
        }
        t.preOrder(t.getRoot());
        System.out.println();
        t.inOrder(t.getRoot());
        System.out.println();
        t.postOrder(t.getRoot());
    }
}