import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue(8);
        pq.Insert(9);
        pq.Insert(3);
        pq.Insert(6);
        pq.Insert(2);
        pq.Insert(1);
        pq.Insert(5);
        pq.Insert(4);
        pq.PrintPQ();
        pq.DequeuePQ();
        System.out.println("the new pq is:");
        pq.PrintPQ();
        System.out.println("the new pq is:");
        pq.DequeuePQ();
        pq.PrintPQ();

        

    }

    // classes
    public static class PriorityQueue {
        // atributes
        Integer[] Heap;
        int n;// Size of the Priority queue

        // constructor
        public PriorityQueue(int capacity) {
            Heap = new Integer[capacity + 1];
            n = 0;
        }

        // Methods
        public boolean IsEmpty() {// method to know if the priority queue is empty or not
            boolean aux = false;
            if (this.n == 0)
                aux = true;
            return aux;
        }

        // method to know what is the PQ's size
        public int Size() {
            return this.n;
        }

        // method to insert a new element in the PQ
        public void Insert(int x) {
            if (n == this.Heap.length - 1) {
                Resize(2 * this.Heap.length);// double the size of the heap, like a dynamic
                // array
            }
            n++;
            Heap[n] = x;
            Swim(n);// it's necessary to adjust the Heap's logique after the insertion
        }

        // method to adjust the Heap after an insertion
        private void Swim(int k) {
            while (k > 1 && this.Heap[k / 2] < this.Heap[k]) {
                int temp = Heap[k];
                Heap[k] = Heap[k / 2];
                Heap[k / 2] = temp;
                k = k / 2;// remember in a MaxHeap k/2 is the father's index, then here we'll move to the
                          // father after switch them
            }

        }

        // method to double the size in the Heap, because it's a dynamic array
        public void Resize(int capacity) {
            Integer[] aux = new Integer[capacity];
            for (int i = 0; i < Heap.length; i++) {
                aux[i] = Heap[i];
            }
            Heap = aux;
        }

        // method to print the PQ
        public void PrintPQ() {
            for (int i = 0; i <= n; i++) {
                System.out.println(Heap[i] + " ");
            }
        }

        // method to Dequeue the element in the PQ
        public int DequeuePQ() {
            int max = this.Heap[1];
            Swap(1, this.n);
            this.n--;
            Sink(1);
            Heap[n + 1] = null;
            if (n > 0 && (n == (Heap.length - 1) / 4)) {
                Resize(Heap.length / 2);
            }
            return max;

        }

        // method to change the position between 2 nodes in the HEAP
        public void Swap(int a, int b) {
            int aux = Heap[a];
            Heap[a] = Heap[b];
            Heap[b] = aux;
        }

        // method to adjust the PQ after dequeue to satisfy the HEAP conditions.
        public void Sink(int k) {
            while (2 * k <= n) {
                int j = 2 * k;
                if (j < n && Heap[j] < Heap[j + 1])
                    j++;
                if (Heap[k] >= Heap[j])
                    break;
                Swap(k, j);
                k = j;
            }
        }

    }

    // Functions
}
