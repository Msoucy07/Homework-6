public class MorseTester {
    public static void main(String[] args) {
        MorseTree tree = new MorseTree();
        tree.buildTreeFromFile("morse.txt"); // Ensure this file exists in your project directory

        System.out.println("Preorder tree contents: " + tree.preorderTraversal());
        System.out.println("Postorder tree contents: " + tree.postorderTraversal());

        String input = "meow i am a cat";
        System.out.println("Input: " + input);

        String morse = tree.translateToMorse(input);
        System.out.println("To Morse: " + morse);

        String english = tree.translateToEnglish(morse);
        System.out.println("To English: " + english);
    }
}
