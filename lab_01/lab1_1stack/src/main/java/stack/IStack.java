package stack;

public interface IStack {
    public void push(int value);

    public int pop();

    public int size();

    public boolean isEmpty();

    public int peek();
}