package implementations;

import interfaces.Solvable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BalancedParentheses implements Solvable {
    private static final Set<Character> openingBracketsSet = Set.of('(', '[', '{');
    private static final Map<Character, Character> closingOpeningBracketsMap = Map.ofEntries(
            Map.entry(')', '('),
            Map.entry(']', '['),
            Map.entry('}', '{')
    );

    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        java.util.ArrayDeque<Character> openingBracketsStack = new java.util.ArrayDeque<>();
        for (int i = 0; i < parentheses.length(); i++) {
            if (openingBracketsSet.contains(parentheses.charAt(i))) {
                openingBracketsStack.push(parentheses.charAt(i));
                continue;
            }

            if (closingOpeningBracketsMap.containsKey(parentheses.charAt(i))) {
                if (openingBracketsStack.isEmpty()
                        || !closingOpeningBracketsMap.get(parentheses.charAt(i)).equals(openingBracketsStack.pop())) {
                    return false;
                }
            }
        }

        return openingBracketsStack.isEmpty();
    }
}
