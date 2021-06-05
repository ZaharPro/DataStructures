import java.util.*;

public class ArrayHeap<E> extends AbstractCollection<E> implements Cloneable {

    private final Comparator<? super E> comparator;

    private Object[] elementData;

    private int size = 0;

    private int modCount = 0;

    public ArrayHeap(int initialCapacity, Comparator<? super E> comparator) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.comparator = comparator;
    }

    public ArrayHeap() {
        this(16, null);
    }

    private void grow() {
        int newCapacity = elementData.length * 2;
        if (newCapacity < 0) {
            throw new OutOfMemoryError("Сори, переполнено");
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private E elementOf(int index) {
        return (E) elementData[index];
    }

    private int parentOf(int index) {
        return (index - 1) / 2;
    }

    private int leftOf(int index) {
        return index * 2 + 1;
    }

    private int rightOf(int index) {
        return index * 2 + 2;
    }

    private int swap(int oldIndex, int newIndex) {
        Object temp = elementData[oldIndex];
        elementData[oldIndex] = elementData[newIndex];
        elementData[newIndex] = temp;
        return newIndex;
    }

    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (size == elementData.length) {
            grow();
        }
        int parent, cursor = size++;
        elementData[cursor] = e;
        while (cursor > 0) {
            parent = parentOf(cursor);
            if (compare(elementOf(cursor), elementOf(parent)) > 0) {
                cursor = swap(cursor, parent);
            } else {
                break;
            }
        }
        modCount++;
        return true;
    }

    public boolean offer(E e) {
        return add(e);
    }

    private void removeFirst() {
        swap(0, --size);
        if (size == 2) {
            if (compare(elementOf(0), elementOf(1)) < 0) {
                swap(0, 1);
            }
        } else {
            int left, right, max, cursor = 0;
            while ((right = rightOf(cursor)) < size) {
                left = leftOf(cursor);
                max = (compare(elementOf(left), elementOf(right)) > 0) ? left : right;
                if (compare(elementOf(cursor), elementOf(max)) < 0) {
                    cursor = swap(cursor, max);
                } else {
                    break;
                }
            }
        }
        elementData[size] = null;
        modCount++;
    }

    @SuppressWarnings("unchecked")
    private int compare(E e1, E e2) {
        return comparator == null ?
                ((Comparable<? super E>) e1).compareTo(e2)
                : comparator.compare(e1, e2);
    }

    public E peek() {
        return elementOf(0);
    }

    public E poll() {
        E result = peek();
        removeFirst();
        return result;
    }

    public E remove() {
        E result = poll();
        if (result == null) {
            throw new NoSuchElementException("Ничего нету");
        }
        return result;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
        modCount++;
    }

    private void reinitialize() {
        size = 0;
        modCount = 0;
    }

    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int count = modCount;

            @Override
            public boolean hasNext() {
                return peek() != null;
            }

            @Override
            public E next() {
                if (count != modCount) {
                    throw new ConcurrentModificationException("Хто-то что-то поменял");
                }
                E result = ArrayHeap.this.remove();
                count = modCount;
                return result;
            }
        };
    }

    @SuppressWarnings("unchecked")
    public Object clone() {
        ArrayHeap<E> arrayHeap;
        try {
            arrayHeap = (ArrayHeap<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        arrayHeap.reinitialize();
        arrayHeap.addAll(this);
        return arrayHeap;
    }
}