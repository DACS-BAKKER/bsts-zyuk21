/*
Name: Alex Yuk
File: 21 Question Game
*/

import java.text.DecimalFormat;
import java.io.*;
import javax.swing.*;

public class TwentyOneQuestions {

    // Directories to file locations
    private static final String DATA_DIRECTORY = "include/Data.txt";
    private static final String SCORE_DIRECTORY = "include/Score.txt";

    private static QuestionTree qt;
    private static QuestionNode currentNode;
    private static JFrame f;

    private static String input;

    private static int computerScore, playerScore;

    public static void main(String[] args) {
        initialize();
        play();
        terminate();
    }

    // Initializes variables and loads data
    private static void initialize() {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        qt = new QuestionTree();
        currentNode = qt.root;

        // Uncomment to print tree in pre order with indents
//        qt.printTree();

        input = JOptionPane.showInputDialog(f, "Would you like to load save state?");
        if (!input.equalsIgnoreCase("yes")) {
            input = JOptionPane.showInputDialog(f, "What would you like your first item to be?");
            writeEmptyFiles();
        }

        qt.loadData(DATA_DIRECTORY);
        loadScore(SCORE_DIRECTORY);
    }

    // Resets the Data.txt and Score.txt files
    private static void writeEmptyFiles() {
        try {
            // Resets the Data.txt file to have player's first answer be root
            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_DIRECTORY));
            bw.write("A:" + input);
            bw.newLine();
            bw.close();

            // Resets Score.txt so both scores are 0
            bw = new BufferedWriter(new FileWriter(SCORE_DIRECTORY));
            bw.write(String.valueOf(0));
            bw.newLine();
            bw.write(String.valueOf(0));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Writes data and closes frame
    private static void terminate() {
        qt.writeData(DATA_DIRECTORY);
        writeScore(SCORE_DIRECTORY);
        System.exit(0);
    }

    // Prints tree indented
    private static void printTree() {
        printTree(qt.root, 0);
    }

    private static void printTree(QuestionNode node, int tabNum) {
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

    // Prints computer score and player score read from Score.txt file
    private static void printScore() {
        String s = "Games I won: " + computerScore +
                "\nGames players won: " + playerScore;

        if (computerScore != 0) {
            // So nothing gets divided by 0
            if (playerScore != 0) {
                // Formats the division below
                DecimalFormat df = new DecimalFormat("0.00");
                s += "\nMy win percentage: " + df.format((double) computerScore / (computerScore + playerScore) * 100) + "%";
            } else
                s += "\nMy win percentage: 100%";
        } else
            s += "\nMy win percentage: 0%";

        s += "\n(Press enter to continue)";
        input = JOptionPane.showInputDialog(f, s);
    }

    // Loads scores from Score.txt
    private static void loadScore(String directory) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(directory));
            // First line of file is computer score
            computerScore = Integer.parseInt(br.readLine());
            // Second line of file is player score
            playerScore = Integer.parseInt(br.readLine());
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Writes score to Score.txt
    private static void writeScore(String directory) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(directory));
            // First line of file is computer score
            bw.write(String.valueOf(computerScore));
            bw.newLine();
            // Second line of file is player score
            bw.write(String.valueOf(playerScore));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Begins game
    private static void play() {
        input = JOptionPane.showInputDialog(f,
                "Welcome to 21 Question!" +
                        "\nPlease answer with yes or no" +
                        "\nThink of an item and I will guess it" +
                        "\n(Press enter to continue)");

        // Loops until player decides to stop
        while (playAgain()) {
            input = JOptionPane.showInputDialog(f, currentNode.data.substring(2));
            // Traversing left and right nodes depending on yes and no respectively
            if (input.equalsIgnoreCase("yes")) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }

        }
    }

    // Checks if player wants to play again after computer has an answer
    private static boolean playAgain() {
        // If computer has an answer
        if (haveAnswer()) {
            printScore();
            input = JOptionPane.showInputDialog(f, "Would you like to play again?");
            // Asks if player wants to play again
            if (input.equalsIgnoreCase("yes")) {
                // Reset currentNode to root
                currentNode = qt.root;
                return true;
            } else {
                input = JOptionPane.showInputDialog(f,
                        "Thank you for playing" +
                                "\n(Press enter to continue)");
                return false;
            }
        }

        return true;
    }

    // Computer has answer
    private static boolean haveAnswer() {
        // Identifies answers with charAt(0) = 'A'
        if (currentNode.data.charAt(0) == 'A') {
            input = JOptionPane.showInputDialog(f, "Where you thinking of " + currentNode.data.substring(2));
            if (input.equalsIgnoreCase("yes")) {
                computerScore++;
                input = JOptionPane.showInputDialog(f,
                        "I win" +
                                "\n(Press enter to continue)");
            } else {
                playerScore++;
                input = JOptionPane.showInputDialog(f, "I lost\nWhat were you thinking of?");
                String newAnswer = "A:" + input;
                String originalData = currentNode.data;

                input = JOptionPane.showInputDialog(f, "Type a Y/N question to distinguish " + originalData.substring(2) + " from " + newAnswer.substring(2));
                // Replaces old node data with new question
                currentNode.data = "Q:" + input;

                input = JOptionPane.showInputDialog(f, "And what is the answer for " + originalData.substring(2));

                // Adds new node for the new answer
                if (input.equalsIgnoreCase("yes")) {
                    // Old answer goes to left
                    currentNode.left = new QuestionNode(originalData);
                    // New answers goes to right
                    currentNode.right = new QuestionNode(newAnswer);
                } else {
                    // Old answer goes to right
                    currentNode.left = new QuestionNode(newAnswer);
                    // New answer goes to left
                    currentNode.right = new QuestionNode(originalData);
                }
            }
            return true;
        }
        return false;
    }
}
