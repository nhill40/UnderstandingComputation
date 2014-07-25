package dtm;

import java.util.LinkedList;

/**
 * A simple but powerful implementation of a tape concept - allows us to read/write to the tape as well as move left or
 * right to the next character.  Note that this tape is (theoretically) infinite - i.e. can keep moving in any given
 * direction & writing characters out to infinity.  Obviously, there are underlying constraints that would prevent this
 * in reality...but that's why it's "theoretical" :)
 */
public class Tape {
    private LinkedList<Character> left;
    private Character middle;
    private LinkedList<Character> right;
    private Character blank;

    public Tape(LinkedList<Character> left, Character middle, LinkedList<Character> right, Character blank) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.blank = blank;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("#<Tape ");
        for (Character character : left) {
            sb.append(character);
        }
        sb.append("(").append(middle).append(")");
        for (Character character : right) {
            sb.append(character);
        }
        sb.append(">");

        return sb.toString();
    }

    /**
     * Build a new tape based on this instance with the provided character written in place of the current middle
     * character.  Note that this method is non-destructive (returns a new instance, leaving the existing instance
     * untouched).
     * @param character The character to write in place of the current character on the tape.
     * @return The newly created tape.
     */
    public Tape write(Character character) {
        return new Tape(new LinkedList<>(left), character, new LinkedList<>(right), blank);
    }

    /**
     * Moves the virtual tape head one square to the left.  Note that this method is non-destructive (returns a new
     * instance, leaving the existing instance
     * untouched).
     * @return The newly created tape.
     */
    public Tape moveHeadLeft() {
        LinkedList<Character> leftCopy = new LinkedList<>(left);
        Character leftLast = leftCopy.pollLast();

        LinkedList<Character> rightCopy = new LinkedList<>(right);
        rightCopy.addFirst(middle);

        return new Tape(leftCopy, leftLast == null ? '_' : leftLast, rightCopy, blank);
    }

    /**
     * Moves the virtual tape head one square to the right.  Note that this method is non-destructive (returns a new
     * instance, leaving the existing instance
     * untouched).
     * @return The newly created tape.
     */
    public Tape moveHeadRight() {
        LinkedList<Character> leftCopy = new LinkedList<>(left);
        leftCopy.add(middle);

        LinkedList<Character> rightCopy = new LinkedList<>(right);
        Character newMiddle = rightCopy.pollFirst();

        return new Tape(leftCopy, newMiddle == null ? '_' : newMiddle, rightCopy, blank);
    }

    public Character getMiddle() {
        return middle;
    }
}
