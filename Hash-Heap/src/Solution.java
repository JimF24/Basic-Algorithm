import java.io.*;
import java.util.*;

class Soldier {
    String name;
    int evalution;
}

class MinHeap {
    private int size;
    private int capacity;
    private Soldier[] array;
    private HashMap<String, Integer> position = new HashMap<String, Integer>();

    public MinHeap(int size) {
        capacity = size;
        this.size = 0;
        array = new Soldier[size];
    }

    private void HeapHold(int pos) {
        int left = 2 * pos + 1, right = 2 * pos + 2;
        int minpos = pos;
        if (left < size && array[left].evalution < array[minpos].evalution) {
            minpos = left;
        }
        if (right < size && array[right].evalution < array[minpos].evalution) {
            minpos = right;
        }
        if (minpos != pos) {
            Soldier tmp = array[minpos];
            array[minpos] = array[pos];
            array[pos] = tmp;
            //update new position
            position.put(array[minpos].name, minpos);
            position.put(array[pos].name, pos);
            HeapHold(minpos);
        }
    }

    public void HeapInsert(String name, int evalution) {
        if (size == capacity) return ;
        Soldier soldier = new Soldier();
        soldier.evalution = evalution;
        soldier.name = name;

        int current = size;
        array[current] = soldier;
        position.put(array[current].name, current);
        size++;

        int parent = (current - 1) / 2;
        while (parent >= 0 && array[parent].evalution > array[current].evalution) {
            Soldier tmp = array[parent];
            array[parent] = array[current];
            array[current] = tmp;
            //update new position
            position.put(array[parent].name, parent);
            position.put(array[current].name, current);

            current = parent;
            parent = (current - 1) / 2;
        }
    }

    public void HeapUpdate(String name, int addEval) {
        int pos = position.get(name);
        array[pos].evalution += addEval;
        HeapHold(pos);
    }

    public int HeapPeek() {
        if (size == 0) return -1;
        return array[0].evalution;
    }

    public void HeapPop() {
        String deleteName = array[0].name;
        array[0] = array[size - 1];
        position.put(array[0].name, 0);
        position.remove(deleteName);
        size--;
        HeapHold(0);
    }

    public int HeapSize() {
        return size;
    }
}

public class Solution {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int size = scan.nextInt();
        MinHeap heap = new MinHeap(size);
        for (int i = 0;i < size; ++i) {
            String name = scan.next();
            int evalution = scan.nextInt();
            heap.HeapInsert(name, evalution);
        }

        int count = scan.nextInt();
        for (int i = 0;i < count; ++i) {
            int cmd = scan.nextInt();
            if (cmd == 1) {
                String name = scan.next();
                int addEval = scan.nextInt();
                heap.HeapUpdate(name, addEval);
            } else if (cmd == 2) {
                int standard = scan.nextInt();
                while (heap.HeapPeek() != -1 && heap.HeapPeek() < standard) {
                    heap.HeapPop();
                }
                System.out.println(heap.HeapSize());
            }
        }
    }
}
