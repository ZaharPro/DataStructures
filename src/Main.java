import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //RB();
        AVL();
    }

    private static void H() {
        Comparator<Pair<Integer, Integer>> first = Comparator.comparing(o -> o.fst);
        Comparator<Pair<Integer, Integer>> second = Comparator.comparing(o -> o.snd);

        ArrayHeap<Integer> integerHeap = new ArrayHeap<>(40, Integer::compareTo);
        for (int i = 31; i >= 0; i--)
            integerHeap.offer(i);

        for (int i = 0; i < 10; i++)
            System.out.print(integerHeap.poll() + " ");
        System.out.println();

        for (int i = 33; i < 44; i++)
            integerHeap.offer(i);

        for (int i : integerHeap)
            System.out.print(i + " ");
        System.out.println();
    }

    private static void S() {
        SimpleTree<String> simpleTree = new SimpleTree<>();
        System.out.println("Enter 10 lines");
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            simpleTree.add(in.nextLine());
        }
        simpleTree.traverseTree(key -> System.out.print(key + " "));
        System.out.println('\n');
    }

    private static void RB() {
        System.out.println("RBTree:");

        RBTree<Integer, Integer> rbTree = new RBTree<>(Integer::compareTo);
        for (int i = 0; i < 50; i++) {
            int k = (int) (Math.random() * 50);
            rbTree.put(k, i);
            System.out.print(k + " ");
        }
        System.out.println();
        for (Map.Entry<?, ?> entry : rbTree) {
            System.out.print(entry.getKey() + " ");
        }
        System.out.println();
        rbTree.printTree();
        Iterator<Map.Entry<Integer, Integer>> iterator = rbTree.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            System.out.print(entry.toString() + " ");
            if (entry.getKey() % 2 == 0)
                iterator.remove();
        }
        System.out.println();
        for (Map.Entry<?, ?> entry : rbTree) {
            System.out.print(entry.getKey() + " ");
        }
        System.out.println();
        rbTree.clear();

        for (int j = 0; j < 5; j++) {
            System.out.println('\n');
            for (int i = 0; i < 10; i++) {
                int k = (int) (Math.random() * 100);
                rbTree.put(k, i);
            }
            rbTree.printTree();
        }
        System.out.println();
    }

    private static void AVL() {
        System.out.println("AVLTree:");
        AVLTree<Integer> avlTree = new AVLTree<>(Integer::compareTo);
        for (int i = 0; i < 50; i++) {
            avlTree.add(i);
            if (i % 10 == 0) {
                avlTree.printTree();
                System.out.println();
            }
        }
        System.out.println();
    }
}