/*
Name: Alex Yuk
File: Binary Tree made for 21 Questions
 */

import java.io.*;

public class QuestionTree {

    public QuestionNode root;

    public QuestionTree() {
        root = new QuestionNode("");
    }

    // Reads data from Data.txt
    public void loadData(String directory) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(directory));
            loadData(root, br);
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads recursively in pre order
    private void loadData(QuestionNode node, BufferedReader br) throws IOException {
        String line = br.readLine();
        if(line == null) {
            return;
        }
        // Stops calling for left and right if reading answer
        else if (line.charAt(0) == 'A') {
            node.data = line;
            return;
        }

        node.data = line;
        // Recursion on left subtree
        node.left = new QuestionNode("");
        loadData(node.left, br);
        // Recursion on right subtree
        node.right = new QuestionNode("");
        loadData(node.right, br);
    }

    // Writes data into Data.txt
    public void writeData(String directory) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(directory));
            writeData(root, bw);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Writes recursively in pre order
    private void writeData(QuestionNode node, BufferedWriter bw) throws IOException {
        if(node == null || node.data == "") {
            return;
        }

        bw.write(node.data);
        bw.newLine();
        // Recursion on left subtree
        writeData(node.left, bw);
        // Recursion on right subtree
        writeData(node.right, bw);
    }


    // Prints tree with indent
    public void printTree() {
        printTree(root, 0);
    }

    private void printTree(QuestionNode node, int tabNum) {
        if (node == null) {
            return;
        }
        for(int i = 0; i < tabNum; i++)
            System.out.print("\t");

        System.out.println(node.data);
        // Recursion on left subtree
        printTree(node.left, tabNum + 1);
         // Recursion on right subtree
        printTree(node.right, tabNum + 1);
    }
}
