package stack;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Stack<E> {
    private final ArrayList<E> items = new ArrayList<>();

    public void push(E item) {
        items.add(item);
    }

    public E pop() {
        if (items.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return items.remove(items.size() - 1);
    }

    public E peek() {
        if (items.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return items.get(items.size() - 1);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}

