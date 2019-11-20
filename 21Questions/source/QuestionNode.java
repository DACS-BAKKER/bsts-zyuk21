/*
Name: Alex Yuk
File: Node made for 21 Questions
 */

public class QuestionNode {
    // Stores question or answer
    public String data;
    public QuestionNode left, right;

    public QuestionNode(String data) {
        this.data = data;
        this.left = this.right = null;
    }
}
