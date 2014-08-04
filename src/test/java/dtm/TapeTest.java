package dtm;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class TapeTest {

    @Test
    public void test_tape() {
        Tape tape = new Tape(new LinkedList<>(Arrays.asList('1', '0', '1')), '1',  new LinkedList<Character>(), '_');
        assertEquals("#<Tape 101(1)>", tape.toString());
        assertEquals(new Character('1'), tape.getMiddle());
    }

    @Test
    public void test_moveHeadLeft() {
        Tape tape = new Tape(new LinkedList<>(Arrays.asList('1', '0', '1')), '1',  new LinkedList<Character>(), '_');
        assertEquals("#<Tape 101(1)>", tape.toString());
        assertEquals("#<Tape 10(1)1>", tape.moveHeadLeft().toString());

        // Verify that the moveLeft operation is non-destructive:
        assertEquals("#<Tape 101(1)>", tape.toString());
    }

    @Test
    public void test_write() {
        Tape tape = new Tape(new LinkedList<>(Arrays.asList('1', '0', '1')), '1',  new LinkedList<Character>(), '_');
        assertEquals("#<Tape 101(1)>", tape.toString());
        assertEquals("#<Tape 101(0)>", tape.write('0').toString());

        // Verify that the write operation is non-destructive:
        assertEquals("#<Tape 101(1)>", tape.toString());
    }

    @Test
    public void test_moveHeadRight() {
        Tape tape = new Tape(new LinkedList<>(Arrays.asList('1', '0', '1')), '1',  new LinkedList<Character>(), '_');
        assertEquals("#<Tape 101(1)>", tape.toString());
        assertEquals("#<Tape 1011(_)>", tape.moveHeadRight().toString());

        // Verify that the moveRight operation is non-destructive:
        assertEquals("#<Tape 101(1)>", tape.toString());
    }
}