package solutions;

import java.util.Arrays;
import java.util.TreeSet;

public class CookiesProblem {
    public Integer solve(int k, int[] cookies) {
        TreeSet<Integer> treeSet = getTreeSetFromIntArray(cookies);
        int operationsCounter = 0;

        while (hasElementsSmallerThanValue(treeSet, k)) {
            if (treeSet.size() < 2) {
                return -1;
            }

            Integer leastSweetCookie = treeSet.pollFirst();
            Integer secondLeastSweetCookie = treeSet.pollFirst();

            Integer newCookie = mixCookies(leastSweetCookie, secondLeastSweetCookie);
            treeSet.add(newCookie);
            operationsCounter++;
        }

        return operationsCounter;
    }

    private TreeSet<Integer> getTreeSetFromIntArray(int[] array) {
        TreeSet<Integer> treeSet = new TreeSet<>();

        for (int i = 0; i < array.length; i++) {
            treeSet.add(array[i]);
        }

        return treeSet;
    }

    private boolean hasElementsSmallerThanValue(TreeSet<Integer> treeSet, int value) {
        return treeSet.floor(value - 1) != null;
    }

    private Integer mixCookies(Integer cookie, Integer sweeterCookie) {
        return cookie + 2 * sweeterCookie;
    }
}
