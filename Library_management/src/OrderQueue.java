import java.util.Arrays;

public class OrderQueue { // maxHeap
    private Order[] queue;
    private int size;
    private int capacity;

    private static final int FRONT = 1;

    public OrderQueue() {
        this(20);
    }

    public OrderQueue(int maxsize) {
        this.capacity = maxsize;
        this.size = 0;
        queue = new Order[this.capacity + 1];
        queue[0] = null;
    }

    private int parent(int pos) {
        return pos / 2;
    }

    private int leftChild(int pos) {
        return (2 * pos);
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    private boolean isLeaf(int pos) {
        return leftChild(pos) > size && rightChild(pos) > size;
    }

    private void swap(int first, int second) {
        Order tmp;
        tmp = queue[first];
        queue[first] = queue[second];
        queue[second] = tmp;
    }

    private void Heapify(int pos) {
        if (!isLeaf(pos)) {
            if (queue[pos].compareTo(queue[leftChild(pos)]) < 0 || queue[pos].compareTo(queue[rightChild(pos)]) < 0) {
                if (queue[leftChild(pos)].compareTo(queue[rightChild(pos)]) > 0) {
                    swap(pos, leftChild(pos));
                    Heapify(leftChild(pos));
                } else {
                    swap(pos, rightChild(pos));
                    Heapify(rightChild(pos));
                }
            }
        }
    }

    public void insert(Order element) {
        if (size + 1 >= capacity)
            this.grow();

        queue[++size] = element;
        int current = size;

        while (queue[parent(current)] != null && queue[current].compareTo(queue[parent(current)]) > 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    private void grow() {
        int oldCapacity = queue.length;
        int newCapacity = oldCapacity * 2;
        queue = Arrays.copyOf(queue, newCapacity);
        this.capacity = newCapacity;
    }

    public void print() {
        OrderQueue q = new OrderQueue(this.capacity);
        q.queue = Arrays.copyOf(queue, capacity);
        q.size = this.size;
        while (q.getSize() > 0)
            q.extractMax().printDetails();
    }

    public Order extractMax() {
        Order popped = queue[FRONT];
        queue[FRONT] = queue[size--];
        Heapify(FRONT);
        return popped;
    }

    public int getSize() {
        return size;
    }

}
