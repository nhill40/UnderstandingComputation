package regex;

import fa.nfa.NFADesign;

import java.util.LinkedList;
import java.util.List;

public class Choose extends Pattern {
    private List<Pattern> patterns;

    public Choose(Pattern first, Pattern second) {
        patterns = new LinkedList<Pattern>();
        patterns.add(first);
        patterns.add(second);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Pattern pattern : patterns) {
            sb.append(pattern.bracket(getPrecedence()));
            sb.append("|");
        }

        return sb.deleteCharAt(sb.length()-1).toString();
    }

    @Override
    public int getPrecedence() {
        return 0;
    }

    @Override
    public NFADesign toNFADesign() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
