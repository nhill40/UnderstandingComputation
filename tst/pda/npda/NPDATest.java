package pda.npda;

import fa.State;
import org.junit.Test;
import pda.PDAConfiguration;
import pda.PDARule;
import pda.Stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NPDATest {

    @Test
    public void test_accepting() {
        NPDARulebook rulebook = new NPDARulebook(Arrays.asList(
                new PDARule(STATE1, 'a', STATE1, '$', Arrays.asList('a', '$')),
                new PDARule(STATE1, 'a', STATE1, 'a', Arrays.asList('a', 'a')),
                new PDARule(STATE1, 'a', STATE1, 'b', Arrays.asList('a', 'b')),
                new PDARule(STATE1, 'b', STATE1, '$', Arrays.asList('b', '$')),
                new PDARule(STATE1, 'b', STATE1, 'a', Arrays.asList('b', 'a')),
                new PDARule(STATE1, 'b', STATE1, 'b', Arrays.asList('b', 'b')),
                new PDARule(STATE1, null, STATE2, '$', Arrays.asList('$')),
                new PDARule(STATE1, null, STATE2, 'a', Arrays.asList('a')),
                new PDARule(STATE1, null, STATE2, 'b', Arrays.asList('b')),
                new PDARule(STATE2, 'a', STATE2, 'a', new ArrayList<Character>()),
                new PDARule(STATE2, 'b', STATE2, 'b', new ArrayList<Character>()),
                new PDARule(STATE2, null, STATE3, '$', Arrays.asList('$'))
        ));

        PDAConfiguration configuration = new PDAConfiguration(STATE1, new Stack(Arrays.asList('$')));
        NPDA npda = new NPDA(new HashSet<PDAConfiguration>(Arrays.asList(configuration)), new ArrayList<State>(Arrays.asList(STATE3)), rulebook);
        assertTrue(npda.accepting());

        Set<PDAConfiguration> currentConfigurationsSnapshot = npda.getCurrentConfigurations();
        assertEquals(3, currentConfigurationsSnapshot.size());
        List<String> currentConfigAsStrings = new ArrayList<>();
        for (PDAConfiguration currentConfig : currentConfigurationsSnapshot) {
            currentConfigAsStrings.add(currentConfig.toString());
        }
        assertTrue(currentConfigAsStrings.contains("state=1, stack=Stack ($)"));
        assertTrue(currentConfigAsStrings.contains("state=2, stack=Stack ($)"));
        assertTrue(currentConfigAsStrings.contains("state=3, stack=Stack ($)"));

        npda.readString("abb");
        assertFalse(npda.accepting());

        currentConfigurationsSnapshot = npda.getCurrentConfigurations();
        assertEquals(3, currentConfigurationsSnapshot.size());
        currentConfigAsStrings = new ArrayList<>();
        for (PDAConfiguration currentConfig : currentConfigurationsSnapshot) {
            currentConfigAsStrings.add(currentConfig.toString());
        }
        assertTrue(currentConfigAsStrings.contains("state=1, stack=Stack (b)ba$"));
        assertTrue(currentConfigAsStrings.contains("state=2, stack=Stack (a)$"));
        assertTrue(currentConfigAsStrings.contains("state=2, stack=Stack (b)ba$"));

        npda.readString("a");
        assertTrue(npda.accepting());

        currentConfigurationsSnapshot = npda.getCurrentConfigurations();
        assertEquals(4, currentConfigurationsSnapshot.size());
        currentConfigAsStrings = new ArrayList<>();
        for (PDAConfiguration currentConfig : currentConfigurationsSnapshot) {
            currentConfigAsStrings.add(currentConfig.toString());
        }
        assertTrue(currentConfigAsStrings.contains("state=1, stack=Stack (a)bba$"));
        assertTrue(currentConfigAsStrings.contains("state=2, stack=Stack ($)"));
        assertTrue(currentConfigAsStrings.contains("state=2, stack=Stack (a)bba$"));
        assertTrue(currentConfigAsStrings.contains("state=3, stack=Stack ($)"));
    }
}
