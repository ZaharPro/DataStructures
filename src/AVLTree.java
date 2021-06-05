import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class AVLTree<E> {

    private final Comparator<E> comparator;

    private Node<E> root;

    private int size = 0;

    public AVLTree() {
        comparator = null;
    }

    public AVLTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    static final class Node<K> {
        K key;
        int height;
        Node<K> left, right;

        Node(K key) {
            this.key = key;
            height = 1;
        }

        public int hashCode() {
            long bits = key == null ? 0 : key.hashCode();
            bits *= 17;
            return (((int) bits) ^ ((int) (bits >> 32)));
        }

        public boolean equals(Object o) {
            if (!(o instanceof AVLTree.Node))
                return false;
            AVLTree.Node<?> e = (AVLTree.Node<?>) o;
            return valEquals(key, e.key);
        }

        public String toString() {
            return key + "," + height;
        }
    }

    private Node<E> getNode(E key) {
        Node<E> r = root;
        while (r != null) {
            int cmp = compare(r.key, key);
            if (cmp > 0) {
                r = r.left;
            } else if (cmp < 0) {
                r = r.right;
            } else {
                return r;
            }
        }
        return null;
    }


    private Node<E> rotateLeft(Node<E> node) {
        Node<E> right = node.right;
        Node<E> left = right.left;

        right.left = node;
        node.right = left;

        setHeight(node);
        setHeight(right);

        return right;
    }

    private Node<E> rotateRight(Node<E> node) {
        Node<E> left = node.left;
        Node<E> right = left.right;

        left.right = node;
        node.left = right;

        setHeight(node);
        setHeight(left);

        return left;
    }

    private int getBalance(Node<E> node) {
        return (node == null) ? 0 : (getHeight(node.left) - getHeight(node.right));
    }

    private int getHeight(Node<E> node) {
        return (node == null) ? 0 : node.height;
    }

    private void setHeight(Node<E> node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public void add(E key) {
        root = insert(root, key);
        size++;
    }

    private Node<E> insert(Node<E> r, E key) {
        if (r == null)
            return new Node<>(key);

        int cmp = compare(r.key, key);
        if (cmp > 0) {
            r.left = insert(r.left, key);
        } else if (cmp < 0) {
            r.right = insert(r.right, key);
        } else {
            return r;
        }

        setHeight(r);

        int balance = getBalance(r);

        if (r.left != null) {
            int one = compare(r.left.key, key);
            // Left Left Case
            if (balance > 1 && one > 0)
                return rotateRight(r);

            // Left Right Case
            if (balance > 1 && one < 0) {
                r.left = rotateLeft(r.left);
                return rotateRight(r);
            }
        }

        if (r.right != null) {
            int two = compare(r.right.key, key);
            // Right Right Case
            if (balance < -1 && two < 0)
                return rotateLeft(r);

            // Right Left Case
            if (balance < -1 && two > 0) {
                r.right = rotateRight(r.right);
                return rotateLeft(r);
            }
        }

        return r;
    }

    private int compare(E e1, E e2) {
        if (comparator == null) {
            if (e1 == null)
                throw new NullPointerException();
            @SuppressWarnings("unchecked")
            Comparable<? super E> k = (Comparable<? super E>) e1;
            return (k.compareTo(e2));
        } else {
            return comparator.compare(e1, e2);
        }
    }

    public void clear() {
        size = 0;
        root = null;
    }

    static boolean valEquals(Object o1, Object o2) {
        return (o1 == null ? o2 == null : o1.equals(o2));
    }

    public int size() {
        return size;
    }

    ///
    private ArrayList<Pair<Node<E>, Integer>> list = new ArrayList<>();

    public void printTree() {
        if (root != null) {
            readyToPrint(new Pair<>(root, 0));
            list.sort(Comparator.comparing(pair -> pair.snd));
            loop:
            for (int level = 0; list.size() != 0; level++) {
                Iterator<Pair<Node<E>, Integer>> pairIterator = list.iterator();
                while (pairIterator.hasNext()) {
                    Pair<Node<E>, Integer> pair = pairIterator.next();
                    if (pair.snd == level) {
                        System.out.print(pair.fst.toString() + " ");
                        pairIterator.remove();
                    } else {
                        System.out.println();
                        continue loop;
                    }
                }
            }
            System.out.println();
        }
    }

    private void readyToPrint(Pair<Node<E>, Integer> pair) {
        if (pair.fst != null) {
            readyToPrint(new Pair<>(pair.fst.left, pair.snd + 1));
            list.add(pair);
            readyToPrint(new Pair<>(pair.fst.right, pair.snd + 1));
        }
    }
}