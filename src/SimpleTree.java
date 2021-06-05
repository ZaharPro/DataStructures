import java.util.function.Consumer;

class SimpleTree<E extends Comparable<E>> {
    private Node<E> root;

    public SimpleTree() {
        root = null;
    }

    private static class Node<K> {
        K key;
        Node<K> left, right;

        Node(K key) {
            this.key = key;
        }
    }

    public void add(E key) {
        if (key == null)
            throw new NullPointerException();
        root = insert(root, key);
    }

    private Node<E> insert(Node<E> root, E key) {
        if (root == null) {
            return new Node<>(key);
        }
        if (key.compareTo(root.key) < 0) {
            root.left = insert(root.left, key);
        } else {
            root.right = insert(root.right, key);
        }
        return root;
    }

    public void traverseTree(Consumer<E> visitor) {
        traverse(root, visitor);
    }

    private void traverse(Node<E> root, Consumer<E> visitor) {
        if (root != null) {
            traverse(root.left, visitor);
            visitor.accept(root.key);
            traverse(root.right, visitor);
        }
    }
}