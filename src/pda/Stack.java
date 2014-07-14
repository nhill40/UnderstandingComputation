package pda;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple implementation of a stack concept - allows us to push to/pop from the top of the stack as well as take a
 * peek at what is currently on the top of the stack.
 */
public class Stack {
    private LinkedList<Character> contents;

    public Stack(List<Character> contents) {
        this.contents = new LinkedList<>(contents);
    }

    /**
     * Build a new stack based on this instance with a new character sitting on top.  Note that this method is
     * non-destructive (returns a new instance, leaving the existing instance untouched).
     * @param character The character to place at the top of the new stack.
     * @return The newly created stack.
     */
    public Stack push(Character character) {
        LinkedList<Character> newContents = new LinkedList<>(contents);
        newContents.addFirst(character);
        return new Stack(newContents);
    }

    /**
     * Build a new stack based on this instance with the topmost character removed.  Note that this method is
     * non-destructive (returns a new instance, leaving the existing instance untouched).
     * @return The newly created stack.
     */
    public Stack pop() {
        LinkedList<Character> newContents = new LinkedList<>(contents);
        newContents.removeFirst();
        return new Stack(newContents);
    }

    /**
     * Take a look at what the topmost character on the stack currently is without modifying the stack.
     * @return The current topmost character.
     */
    public Character top() {
        return contents.peek();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stack (").append(top()).append(")");
        for (int i = 1; i < contents.size(); i++) {
            sb.append(contents.get(i));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Stack) {
            Stack otherStack = (Stack) o;
            return contents.size() == otherStack.contents.size() && contents.containsAll(otherStack.contents);
        }
        return false;
    }
}
