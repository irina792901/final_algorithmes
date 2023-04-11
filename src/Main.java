public class Main {
    public static void main(String[] args) {
        RedBlackTree<Integer> node = new RedBlackTree<>();

        node.add(10);
        node.printTree();
        System.out.println("\n");
        node.add(20);
        node.printTree();
        System.out.println("\n");
        node.add(30);
        node.printTree();
        System.out.println("\n");
        node.add(40);
        node.printTree();
        System.out.println("\n");
        node.add(-10);
        node.printTree();
        System.out.println("\n");
        node.add(-20);
        node.printTree();
    }
}
