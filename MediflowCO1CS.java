// MediFlow Hospital — Patient Indexing using AVL Tree
// DSA-2 Case Study | KL University

public class MediflowCO1CS {

    static class Node {
        int id, height;
        Node left, right;
        Node(int id) { this.id = id; height = 1; }
    }

    private Node root;

    private int h(Node n)  { return n == null ? 0 : n.height; }
    private int bf(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }
    private void updH(Node n) { n.height = 1 + Math.max(h(n.left), h(n.right)); }

    private Node rotR(Node y) {
        Node x = y.left; y.left = x.right; x.right = y;
        updH(y); updH(x);
        System.out.println("  Right-Rotate at " + y.id);
        return x;
    }

    private Node rotL(Node x) {
        Node y = x.right; x.right = y.left; y.left = x;
        updH(x); updH(y);
        System.out.println("  Left-Rotate at " + x.id);
        return y;
    }

    private Node balance(Node n) {
        updH(n);
        int b = bf(n);
        if (b >  1 && bf(n.left)  >= 0) return rotR(n);             // LL
        if (b >  1 && bf(n.left)  <  0) { n.left  = rotL(n.left);  return rotR(n); } // LR
        if (b < -1 && bf(n.right) <= 0) return rotL(n);             // RR
        if (b < -1 && bf(n.right) >  0) { n.right = rotR(n.right); return rotL(n); } // RL
        return n;
    }

    private Node insert(Node n, int id) {
        if (n == null) return new Node(id);
        if      (id < n.id) n.left  = insert(n.left,  id);
        else if (id > n.id) n.right = insert(n.right, id);
        return balance(n);
    }
    public void insert(int id) { root = insert(root, id); }

    private Node minNode(Node n) { while (n.left != null) n = n.left; return n; }

    private Node delete(Node n, int id) {
        if (n == null) return null;
        if      (id < n.id) n.left  = delete(n.left,  id);
        else if (id > n.id) n.right = delete(n.right, id);
        else {
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;
            Node s = minNode(n.right);
            n.id = s.id;
            n.right = delete(n.right, s.id);
        }
        return balance(n);
    }
    public void delete(int id) { root = delete(root, id); }

    public boolean search(int id) {
        Node c = root;
        while (c != null) {
            if      (id == c.id) return true;
            else if (id <  c.id) c = c.left;
            else                 c = c.right;
        }
        return false;
    }

    private void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.print(n.id + " ");
        inorder(n.right);
    }
    public void printInorder() { System.out.print("Inorder: "); inorder(root); System.out.println(); }
    public int  depth()        { return h(root); }

    public static void main(String[] args) {
        MediflowCO1CS t = new MediflowCO1CS();

        int[] ids = {20, 30, 35, 40, 45, 50, 60, 65, 70, 75, 80, 85, 90};
        System.out.println("=== Morning Insertions ===");
        for (int id : ids) { System.out.print("Insert " + id + " -> "); t.insert(id); }

        t.printInorder();
        System.out.println("Tree depth: " + t.depth() + " (SLA: depth <= 25)");

        System.out.println("\n=== Noon Deletions ===");
        t.delete(30); // transferred
        t.delete(70); // discharged
        t.delete(50); // admission closed

        t.printInorder();
        System.out.println("Tree depth after deletions: " + t.depth());

        System.out.println("\n=== Point-Lookups ===");
        for (int q : new int[]{65, 30, 85}) {
            System.out.println("search(" + q + ") -> " + (t.search(q) ? "FOUND" : "NOT FOUND"));
        }
    }
}