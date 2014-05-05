package regex;

import fa.nfa.NFADesign;

/**
 * This is an abstract representation of a regex pattern.  This class provides some important functionality to its
 * subclasses including:
 * (1) A helper method to allow string matching.
 * (2) the ability to understand where to place parenthesis based on relative precedence.
 * (3) the ability to spit out a properly formatted regex representation of the pattern (e.g. "/a/").
 *
 * Subclasses are left to provide a couple vital pieces of information:
 * (1) How to convert themselves to an NFA.
 * (2) What their precedence is relative to the other pattern implementations.
 */
public abstract class Pattern {

    /**
     * For convenience, we can ask a pattern if a given string matches.
     * @param string The string to check for a match against the pattern.
     * @return Whether or not the sting matches the pattern.
     */
    public final boolean matches(String string) {
        return toNFADesign().accepts(string);
    }

    /**
     * Given a precedence, figure out if this pattern has a higher or lower precedence.  If lower, bracket this pattern
     * in parenthesis.  If equal or higher, simply returns itself.
     * @param outerPrecedence The precedence to check against.
     * @return The resulting regex with appropriate parenthesis applied.
     */
    public final String bracket(int outerPrecedence) {
        if (getPrecedence() < outerPrecedence) {
            return "(" + toString() + ")";
        } else {
            return toString();
        }
    }

    /**
     * Produces a "regEx" version of this pattern.  For now, this is simply the toString() representation bounded by
     * a forward slash at either end.
     * @return The regEx version of this pattern.
     */
    public final String toRegEx() {
        return "/" + toString() + "/";
    }

    /**
     * Converts a given pattern into an NFADesign which can in turn be asked to accept strings.
     * @return
     */
    public abstract NFADesign toNFADesign();

    /**
     * Relative order of precedence for this pattern.  Principally used to determine when we need to encapsulate a
     * pattern in parenthesis.
     * @return the order of precedence as expressed as an int (where 0 = lowest precedence)
     */
    public abstract int getPrecedence();
}
