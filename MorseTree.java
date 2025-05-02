import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MorseTree {
    public class TreeNode {
        String value;
        TreeNode left, right;
    
        public TreeNode(String value) {
            this.value = value;
            left = right = null;
        }
    }
    
    private TreeNode root;

    public MorseTree() {
        root = new TreeNode(""); // root node has no letter
    }

    // Build tree from file
    public void buildTreeFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String letter = line.substring(0, 1).toLowerCase(); // store as lowercase
                String code = line.substring(2);
                insert(letter, code);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
    }

    private void insert(String letter, String code) {
        TreeNode current = root;
        for (char symbol : code.toCharArray()) {
            if (symbol == 'o') {
                if (current.left == null) current.left = new TreeNode("");
                current = current.left;
            } else if (symbol == '-') {
                if (current.right == null) current.right = new TreeNode("");
                current = current.right;
            }
        }
        current.value = letter;
    }

    public String preorderTraversal() {
        return preorderHelper(root).trim();
    }

    private String preorderHelper(TreeNode node) {
        if (node == null) return "";
        String result = node.value.isEmpty() ? "" : node.value + " ";
        result += preorderHelper(node.left);
        result += preorderHelper(node.right);
        return result;
    }

    public String postorderTraversal() {
        return postorderHelper(root).trim();
    }

    private String postorderHelper(TreeNode node) {
        if (node == null) return "";
        String result = postorderHelper(node.left);
        result += postorderHelper(node.right);
        result += node.value.isEmpty() ? "" : node.value + " ";
        return result;
    }

    public String translateToMorse(String text) {
        StringBuilder result = new StringBuilder();
        text = text.toLowerCase();
        for (char c : text.toCharArray()) {
            if (c == ' ') continue;
            String morse = findMorseCode(root, Character.toString(c), "");
            if (!morse.isEmpty()) {
                result.append(morse).append(" | ");
            } else {
                result.append("[?]").append(" | ");
                System.err.println("Could not find Morse for: " + c);
            }
        }
        return result.toString().trim();
    }

    private String findMorseCode(TreeNode node, String target, String path) {
        if (node == null) return "";
        if (target.equalsIgnoreCase(node.value)) return path;

        String leftPath = findMorseCode(node.left, target, path + "o");
        if (!leftPath.isEmpty()) return leftPath;

        return findMorseCode(node.right, target, path + "-");
    }

    public String translateToEnglish(String morse) {
        StringBuilder result = new StringBuilder();
        String[] codes = morse.split("\\|");
        for (String code : codes) {
            TreeNode current = root;
            for (char symbol : code.trim().toCharArray()) {
                if (symbol == 'o') {
                    current = current.left;
                } else if (symbol == '-') {
                    current = current.right;
                }
                if (current == null) break;
            }
            if (current != null && !current.value.isEmpty()) {
                result.append(current.value);
            }
        }
        return result.toString();
    }
}
