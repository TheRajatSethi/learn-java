package dataStructure;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * ArrayDeque
 */

public class DS1 {
    public static void main(String[] args) {

        Deque<Integer> adq = new ArrayDeque<>();
        adq.addFirst(10);
        adq.addLast(20);
        adq.add(30);

        System.out.println(adq);

        adq.remove(20);

        System.out.println(adq);
        System.out.println(adq.element()); // Retrieves but does not remove

        System.out.println(adq.pollFirst());

    }
}
