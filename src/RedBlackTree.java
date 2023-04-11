public class RedBlackTree<T extends Comparable<T>> {
    private Node root; // корень

    private enum Color { // добавляем цвета
        RED, BLACK
    }

    private class Node { // создаем класс ноды
        private Node leftChild;
        private Node rightChild;
        private T value;
        private Color color;

        @Override
        public String toString() {
            return "Node{" +
                    " value=" + value +
                    ", color=" + color +
                    '}';
        }
    }

    public boolean add(T value) {
        if (root != null) {
            boolean result = addNode(root, value);// если есть корень дерева, то передаем его следующему методу
            root = rebalance(root);//проверяем нужна ли ребалансировка
            root.color = Color.BLACK;
            return result;
        } else {
            root = new Node();// если корня еще нет, то просто добавляем его в дерево и делаем черным по условию
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }

    private boolean addNode(Node node, T value) {
        if (node.value == value) { // если пытаются добавить уже существующее в дереве значение, то ничего не добавляем (значения должны быть уникальными)
            return false;
        } else {
            if (node.value.compareTo(value) > 0) { // если значение ноды больше нового значения, то отправляем это значение влево
                if (node.leftChild != null) {
                    boolean result = addNode(node.leftChild, value);
                    node.leftChild = rebalance(node.leftChild);
                    return result;
                } else {
                    node.leftChild = new Node();
                    node.leftChild.color = Color.RED;
                    node.leftChild.value = value;
                    return true;
                }
            } else {// иначе, вправо
                if (node.rightChild != null) {
                    boolean result = addNode(node.rightChild, value);
                    node.rightChild = rebalance(node.rightChild);
                    return result;
                } else {
                    node.rightChild = new Node();
                    node.rightChild.color = Color.RED;
                    node.rightChild.value = value;
                    return true;
                }
            }
        }
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needBalance;
        do {
            needBalance = false;
            // проверяем на условие, что правый ребенок красный, а левый черный или отсутствует
            if (result.rightChild != null && result.rightChild.color == Color.RED && (result.leftChild == null || result.leftChild.color == Color.BLACK)) {
                needBalance = true;
                result = rightSwap(result);//делаем правый поворот
            }
            // проверяем на условие что левый ребенок и левый ребенок левого ребенка оба красные
            if (result.leftChild != null && result.leftChild.color == Color.RED && result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED) {
                needBalance = true;
                result = leftSwap(result);//делаем левый разворот
            }
            //проверяем, что оба ребенка красные
            if (result.leftChild != null && result.leftChild.color == Color.RED && result.rightChild != null && result.rightChild.color == Color.RED) {
                needBalance = true;
                swapColors(result);//меняем цвета
            }
        } while (needBalance);//если ни одно из условий не выполняется, ребалансировка не нужна
        return result;
    }

    private Node rightSwap(Node node) {
        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    private Node leftSwap(Node node) {
        Node leftChild = node.leftChild;
        Node betweenChild = leftChild.rightChild;
        leftChild.rightChild = node;
        node.leftChild = betweenChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    private void swapColors(Node node) {
        node.rightChild.color = Color.BLACK;
        node.leftChild.color = Color.BLACK;
        node.color = Color.RED;
    }

    // распечатываем все ноды, вызываем корень и раскручиваем дерево
    public void printTree() {
        printTree(root);
    }

    private void printTree(Node node) {
        if (node != null) {
            System.out.print(node + "\n");
            System.out.println("левые");
            printTree(node.leftChild);
            System.out.println("правые");
            printTree(node.rightChild);
        }
    }
}