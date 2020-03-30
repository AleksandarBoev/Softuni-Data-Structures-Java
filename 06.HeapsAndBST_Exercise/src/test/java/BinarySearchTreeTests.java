import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinarySearchTreeTests {
    BinarySearchTree<Integer> bst;
    Integer smallestElement = 10;
    Integer biggestElement = 80;

    @Before
    public void init() {
        bst = new BinarySearchTree<>();
        bst.insert(50);

        bst.insert(20);
        bst.insert(smallestElement);
        bst.insert(30);

        bst.insert(70);
        bst.insert(60);
        bst.insert(biggestElement);

        /*
                50
           20       70
         10  30   60  80
         */
    }

    @Test
    public void contains_rootElementValue_shouldReturnTrue() {
        Assert.assertTrue(bst.contains(50));
    }

    @Test
    public void contains_smallestLeafElement_shouldReturnTrue() {
        Assert.assertTrue(bst.contains(smallestElement));
    }

    @Test
    public void contains_biggestLeafElement_shouldReturnTrue() {
        Assert.assertTrue(bst.contains(biggestElement));
    }

    @Test
    public void contains_nonExistingElement_shouldReturnFalse() {
        Assert.assertFalse(bst.contains(-10));
    }

    @Test
    public void search_whenSearchingForExistingMiddleElement_shouldWorkCorrectly() {
        BinarySearchTree<Integer> actualSearchedTree = bst.search(20);
        List<Integer> actualElements = actualSearchedTree.range(smallestElement, biggestElement);
        List<Integer> expectedElements = new ArrayList<>(Arrays.asList(10, 20, 30));

        assertEqualSizesAndElements(expectedElements, actualElements);
    }

    @Test
    public void search_whenFindingTreeAndMakingAChange_shouldNotAffectOriginalTree() {
        BinarySearchTree<Integer> actualSearchedTree = bst.search(20);
        actualSearchedTree.deleteMin(); // deletes smallestElement

        Assert.assertTrue(bst.contains(smallestElement));
    }

    @Test
    public void range_whenGiven25And40_shouldWorkCorrectly() {
        List<Integer> rangeResult = bst.range(25, 40);

        int expectedSize = 1;
        Integer expectedElement = 30;

        Assert.assertEquals(expectedSize, rangeResult.size());
        Assert.assertEquals(expectedElement, rangeResult.get(0));
    }

    @Test
    public void range_whenGiven10And40_shouldWorkCorrectly() {
        List<Integer> rangeResult = bst.range(10, 40);
        List<Integer> expectedElements = Arrays.asList(10, 20, 30);

        assertEqualSizesAndElements(expectedElements, rangeResult);
    }

    @Test
    public void range_whenGiven60And80_shouldWorkCorrectly() {
        List<Integer> rangeResult = bst.range(60, 80);
        List<Integer> expectedElements = Arrays.asList(60, 70, 80);

        assertEqualSizesAndElements(expectedElements, rangeResult);
    }

    @Test
    public void range_whenGiven25And75_shouldWorkCorrectly() {
        List<Integer> rangeResult = bst.range(25, 65);
        List<Integer> expectedElements = Arrays.asList(30, 50, 60);

        assertEqualSizesAndElements(expectedElements, rangeResult);
    }

    @Test
    public void range_whenGivenOutOfScopeRange_shouldReturnEmptyList() {
        Assert.assertTrue(bst.range(1, 5).isEmpty());
    }

    @Test
    public void range_whenCalledOnEmptyTree_shouldReturnEmptyList() {
        bst = new BinarySearchTree<>();
        Assert.assertTrue(bst.range(smallestElement, biggestElement).isEmpty());
    }

    @Test
    public void range_whenGivenRangesEqualToASingleValueFromTree_shouldSingleElementList() {
        Assert.assertEquals(1, bst.range(20, 20).size());
        Assert.assertEquals(Integer.valueOf(20), bst.range(20, 20).get(0));
    }

    @Test
    public void count_whenCalledOnTree_shouldWorkCorrectly() {
        Assert.assertEquals(7, bst.count());
    }

    @Test
    public void count_whenCalledOnEmptyTree_shouldReturn0() {
        bst = new BinarySearchTree<>();
        Assert.assertEquals(0, bst.count());
    }

    @Test
    public void count_whenCalledOnTreeWithOneElement_shouldReturn0() {
        bst = new BinarySearchTree<>();
        bst.insert(5);
        Assert.assertEquals(1, bst.count());
    }

    @Test
    public void count_whenAddingToTree_shouldIncrement() {
        bst = new BinarySearchTree<>();
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(i, bst.count());
            bst.insert(i);
        }
    }

    @Test
    public void count_whenRemovingAllElements_shouldDecrement() {
        for (int i = 7; i >= 1; i--) {
            Assert.assertEquals(i, bst.count());
            bst.deleteMin();
        }

        Assert.assertEquals(0, bst.count());
    }

    @Test
    public void count_whenAddingDuplicateElement_shouldStayTheSame() {
        Assert.assertEquals(7, bst.count());
        bst.insert(smallestElement);
        Assert.assertEquals(7, bst.count());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteMax_whenDeletingFromEmptyTree_shouldThrowException() {
        bst = new BinarySearchTree<>();
        bst.deleteMax();
    }

    @Test
    public void deleteMax_whenDeletingFromNormalTree_shouldWorkCorrectly() {
        bst.deleteMax();

        List<Integer> expectedElements = Arrays.asList(10, 20, 30, 50, 60, 70);
        List<Integer> actualElements = bst.range(smallestElement, biggestElement);

        assertEqualSizesAndElements(expectedElements, actualElements);
    }

    @Test
    public void deleteMax_whenDeletingRightmostChildWhichHasLeftChild_shouldWorkCorrectly() {
        bst.deleteMax();
        bst.deleteMax();

        List<Integer> expectedElements = Arrays.asList(10, 20, 30, 50, 60);
        List<Integer> actualElements = bst.range(smallestElement, biggestElement);

        assertEqualSizesAndElements(expectedElements, actualElements);
    }

    @Test
    public void deleteMax_whenDeleting_countShouldDecrement() {
        bst.deleteMax();
        Assert.assertEquals(6, bst.count());
    }

    @Test
    public void deleteMax_whenDeletingAllElements_shouldWorkCorrectlyEachTime() {
        List<Integer> expectedElements = new ArrayList<>(Arrays.asList(10, 20, 30, 50, 60, 70, 80));

        int allElementsCount = 7;
        for (int i = 0; i < allElementsCount; i++) {
            List<Integer> currentActualElements = bst.range(smallestElement, biggestElement);
            assertEqualSizesAndElements(expectedElements, currentActualElements);

            bst.deleteMax();
            expectedElements.remove(expectedElements.size() - 1);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteMin_whenDeletingFromEmptyTree_shouldThrowException() {
        bst = new BinarySearchTree<>();
        bst.deleteMin();
    }

    @Test
    public void deleteMin_whenDeletingFromNormalTree_shouldWorkCorrectly() {
        bst.deleteMin();

        List<Integer> expectedElements = Arrays.asList(20, 30, 50, 60, 70, 80);
        List<Integer> actualElements = bst.range(smallestElement, biggestElement);

        assertEqualSizesAndElements(expectedElements, actualElements);
    }

    @Test
    public void deleteMin_whenDeletingLeftMostChildWhichHasRightChild_shouldWorkCorrectly() {
        bst.deleteMin(); // deletes 10
        bst.deleteMin(); // deletes 20, which has a right child - 30

        List<Integer> expectedElements = Arrays.asList(30, 50, 60, 70, 80);
        List<Integer> actualElements = bst.range(smallestElement, biggestElement);

        assertEqualSizesAndElements(expectedElements, actualElements);
    }

    @Test
    public void deleteMin_whenDeleting_countShouldDecrement() {
        bst.deleteMin();
        Assert.assertEquals(6, bst.count());
    }

    @Test
    public void deleteMin_whenDeletingAllElements_shouldWorkCorrectlyEachTime() {
        List<Integer> expectedElements = new ArrayList<>(Arrays.asList(10, 20, 30, 50, 60, 70, 80));

        int allElementsCount = 7;
        for (int i = 0; i < allElementsCount; i++) {
            List<Integer> currentActualElements = bst.range(smallestElement, biggestElement);
            assertEqualSizesAndElements(expectedElements, currentActualElements);

            bst.deleteMin();
            expectedElements.remove(0);
        }
    }

    @Test
    public void rank_whenCheckingRankOfSmallestElement_shouldReturn0() {
        Assert.assertEquals(0, bst.rank(10));
    }

    @Test
    public void rank_whenCheckingRankOfMiddleElement_shouldWorkCorrectly() {
        Assert.assertEquals(1, bst.rank(20));
    }

    @Test
    public void rank_whenCheckingRankOfLeafElement_shouldWorkCorrectly() {
        Assert.assertEquals(2, bst.rank(30));
    }

    @Test
    public void rank_whenCheckingRankOfRootElement_shouldWorkCorrectly() {
        Assert.assertEquals(3, bst.rank(50));
    }

    @Test
    public void ceil_callingOnBiggestElement_shouldReturnNull() {
        Assert.assertNull(bst.ceil(biggestElement));
    }

    @Test
    public void ceil_callingOnNonExistingElement_shouldReturnNull() {
        Assert.assertNull(bst.ceil(55));
    }

    @Test
    public void ceil_callingOnRoot_shouldReturnGrandChild() {
        Integer actual = bst.ceil(50);
        Assert.assertEquals(Integer.valueOf(60), bst.ceil(50));
    }

    @Test
    public void ceil_callingOnLeafElement_shouldReturnParent() {
        Assert.assertEquals(Integer.valueOf(20), bst.ceil(10));
    }

    @Test
    public void ceil_callingOnLeafElement_shouldReturnGrandParent() {
        Assert.assertEquals(Integer.valueOf(50), bst.ceil(30));
    }

    @Test
    public void ceil_callingOnMultipleElements_shouldWorkCorrectly() {
        List<Integer> allElements = Arrays.asList(10, 20, 30, 50, 60, 70, 80);

        for (int i = 0; i < allElements.size() - 1; i++) {
            Assert.assertEquals(allElements.get(i + 1), bst.ceil(allElements.get(i)));
        }
    }

    @Test
    public void floor_callingOnSmallestElement_shouldReturnNull() {
        Assert.assertNull(bst.floor(smallestElement));
    }

    @Test
    public void floor_callingOnNonExistingElement_shouldReturnNull() {
        Assert.assertNull(bst.floor(25));
    }

    @Test
    public void floor_callingOnRoot_shouldReturnGrandChild() {
        Assert.assertEquals(Integer.valueOf(30), bst.floor(50));
    }

    @Test
    public void floor_callingOnLeafElement_shouldReturnParent() {
        Assert.assertEquals(Integer.valueOf(70), bst.floor(80));
    }

    @Test
    public void floor_callingOnLeaf_shouldReturnGrandParent() {
        Assert.assertEquals(Integer.valueOf(50), bst.floor(60));
    }

    @Test
    public void floor_callingOnMultipleElements_shouldWorkCorrectly() {
        List<Integer> allElements = bst.range(smallestElement, biggestElement);

        for (int i = 1; i < allElements.size(); i++) {
            Assert.assertEquals(allElements.get(i - 1), bst.floor(allElements.get(i)));
        }
    }

    private<E> void assertEqualSizesAndElements(List<E> expected, List<E> actual) {
        Assert.assertEquals("Collection sizes mismatch!", expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals("Element values mismatch!", expected.get(i), actual.get(i));
        }
    }
}
