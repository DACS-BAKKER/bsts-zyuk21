/*
Name: Alex Yuk
File: Binary Search Tree Class
 */

public class BinarySearchTree<T> {
    class Node {
        private int key;
        private T element;
        private Node left, right;
        private int size;

        public Node(int key, T element) {
            this.key = key;
            this.element = element;
            this.size = 1;
            this.left = this.right = null;
        }
    }

    private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    // Returns true if tree contains key
    public boolean contains(int key) {
        return get(key) != null;
    }

    // Returns T based on key
    public T get(int key) {
        return get(root, key);
    }

    private T get(Node node, int key) {
        if (node == null)
            return null;

        // Smaller on left side
        if (key < node.key)
            return get(node.left, key);
        // Bigger on right side
        else if (key > node.key)
            return get(node.right, key);
        else
            return node.element;
    }

    // Removes the specified key and its associated value from this symbol table
    public void delete(int key) {
        root = delete(root, key);
    }

    private Node delete(Node node, int key) {
        if (node == null)
            return null;

        // Smaller on left side
        if (key < node.key)
            node.left = delete(node.left, key);
        // Bigger on right side
        else if (key > node.key)
            node.right = delete(node.right, key);
        // Checks that left and right node are not null before deleting
        else if (node.right == null)
            return node.left;
        else if (node.left == null)
            return node.right;
        else {
            // Swaps with the min
            Node temp = node;
            node = min(temp.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }
        node.size = size(node.left) + size(node.right) + 1;

        return node;
    }

    // Deletes the min
    private Node deleteMin(Node node) {
        if (node.left == null)
            return node.right;
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    // Returns height
    public int height() {
        return height(root) + 1;
    }

    private int height(Node node) {
        if (node == null)
            return -1;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    // Returns the largest key in the symbol table.
    public int max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null)
            return x;
        else
            return max(x.right);
    }

    // Returns the smallest key in the symbol table.
    public int min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null)
            return x;
        else
            return min(x.left);
    }

    // Traverse and prints the keys in levelOrder order
    public void levelOrder() {
        int height = height();
        for (int i = 1; i <= height; i++)
            printLevel(root, i);
    }

    private void printLevel(Node root, int level) {
        if (root == null)
            return;
        if (level == 1)
            System.out.print(root.element + " ");
        else if (level > 1) {
            printLevel(root.left, level - 1);
            printLevel(root.right, level - 1);
        }
    }

    // Traverse and prints the keys in inOrder order
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        // Recursion on left subtree
        inOrder(node.left);
        System.out.print(node.element + " ");
        // Recursion on right subtree
        inOrder(node.right);
    }

    // Traverse and prints the keys in postOrder order
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node == null)
            return;
        // Recursion on left subtree
        postOrder(node.left);
        // Recursion on right subtree
        postOrder(node.right);
        // Print data of root node
        System.out.print(node.element + " ");
    }

    // Traverse and prints the keys in preOrder order
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node == null)
            return;
        // Print data of root node
        System.out.print(node.element + " ");
        // Recursion on left subtree
        preOrder(node.left);
        // Recursion on right subtree
        preOrder(node.right);
    }

    // Inserts the specified key-value pair into the symbol table, overwriting the old value with the new value if the symbol table already contains the specified key.
    public void put(int key, T element) {
        root = put(root, key, element);
    }

    private Node put(Node node, int key, T element) {
        if (node == null)
            return new Node(key, element);

        if (key < node.key)
            node.left = put(node.left, key, element);
        else if (key > node.key)
            node.right = put(node.right, key, element);
        else
            node.element = element;

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    // Returns the number of key-value pairs in this symbol table
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if(node == null)
            return 0;
        return node.size;
    }

    public static void main(String[]args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();

        int[] arr = {5, 2, 7, 3, 6, 1};

        for (int i = 0; i < arr.length; i++) {
            tree.put(arr[i], arr[i]);
        }

        System.out.println("Size: " + tree.size());


        System.out.print("InOrder print: ");
        tree.inOrder();
        System.out.println();

        System.out.print("LevelOrder print: ");
        tree.levelOrder();
        System.out.println();

        System.out.print("PostOrder print: ");
        tree.postOrder();
        System.out.println();

        System.out.print("PreOrder print: ");
        tree.preOrder();
        System.out.println();

        System.out.println("Contains 2?: " + tree.contains(2));

        System.out.print("InOrder print after deleting key 2: ");
        tree.delete(2);
        tree.inOrder();
        System.out.println();

        System.out.println("Max: " + tree.max());
        System.out.println("Min: " + tree.min());
        System.out.println("Height: " + tree.height());
        System.out.println("Contains 2?: " + tree.contains(2));
    }
}