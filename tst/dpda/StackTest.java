package dpda;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StackTest {

    private Stack stack;

    @Before
    public void setUp() {
        stack = new Stack(Arrays.asList('a', 'b', 'c', 'd', 'e'));
    }

    @Test
    public void test_top() {
        assertEquals(new Character('a'), stack.top());
    }

    @Test
    public void test_pop() {
        assertEquals(new Character('c'), stack.pop().pop().top());
    }

    @Test
    public void test_push() {
        assertEquals(new Character('y'), stack.push('x').push('y').top());
    }

    @Test
    public void test_push_pop() {
        assertEquals(new Character('x'), stack.push('x').push('y').pop().top());
    }
}
