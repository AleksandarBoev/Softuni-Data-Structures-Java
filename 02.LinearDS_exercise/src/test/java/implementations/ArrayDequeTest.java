package implementations;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
    My own tests. Not everything might be correct
 */
public class ArrayDequeTest {
    ArrayDeque<Integer> arrayDeque;

    @Before
    public void init() {
        arrayDeque = new ArrayDeque<>();
    }

    @Test
    public void addLast_whenAddingThreeElements_correctlyAdded() {
        arrayDeque.addLast(0);
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);

        Integer counter = 0;
        for (Integer integer : arrayDeque) {
            assertEquals(counter++, integer);
        }
    }

    @Test
    public void grow_whenAddingMoreElementsThanInitialCapacity_worksCorrectly() {
        int initialCapacity = 8;

        for (int i = 0; i < initialCapacity + 1; i++) {
            arrayDeque.addLast(i);
        }

        Integer counter = 0;
        for (Integer integer : arrayDeque) {
            assertEquals(counter++, integer);
        }
    }

    @Test
    public void addFirst_whenAddingElements_worksCorrectly() {
        Integer numberOfElements = 3;
        for (int i = 0; i < numberOfElements; i++) {
            arrayDeque.addFirst(i);
        }

        for (Integer integer : arrayDeque) {
            assertEquals(--numberOfElements, integer);
        }
    }

    @Test
    public void addFirst_whenAddingMoreElementsThanInitialCapacity_worksCorrectly() {
        Integer initialCapacity = 8;

        for (int i = 0; i < initialCapacity + 1; i++) {
            arrayDeque.addFirst(i);
        }

        for (Integer integer : arrayDeque) {
            assertEquals(initialCapacity--, integer);
        }
    }

    @Test
    public void size_whenAddingFirst_worksCorrectly() {
        for (int i = 0; i < 3; i++) {
            arrayDeque.addFirst(0);
            assertEquals(i + 1, arrayDeque.size());
        }
    }

    @Test
    public void size_whenAddingFirstAndGrowIsMade_worksCorrectly() {
        for (int i = 0; i < 9; i++) {
            arrayDeque.addFirst(0);
            assertEquals(i + 1, arrayDeque.size());
        }
    }

    @Test
    public void size_whenAddingLast_worksCorrectly() {
        for (int i = 0; i < 3; i++) {
            arrayDeque.addLast(0);
            assertEquals(i + 1, arrayDeque.size());
        }
    }

    @Test
    public void size_whenAddingLastAndGrowIsMade_worksCorrectly() {
        for (int i = 0; i < 9; i++) {
            arrayDeque.addLast(0);
            assertEquals(i + 1, arrayDeque.size());
        }
    }

    @Test
    public void get_whenSearchingForNonExistingElement_returnsNull() {
        for (int i = 0; i < 3; i++) {
            arrayDeque.addLast(i);
        }

        Integer nonExistingValue = -1; // will search by value, not index
        Integer actualReturnedElement = arrayDeque.get(nonExistingValue);

        assertNull(actualReturnedElement);
    }

    @Test
    public void get_whenSearchingForExistingElementInFirstPlace_returnsIt() {
        for (int i = 0; i < 3; i++) {
            arrayDeque.addLast(i);
        }

        Integer existingValue = 0; // will search by value, not index
        Integer actualReturnedElement = arrayDeque.get(existingValue);

        assertNotNull(actualReturnedElement);
        assertEquals(Integer.valueOf(0), actualReturnedElement);
    }

    @Test
    public void get_whenSearchingForExistingElementInMiddlePlace_returnsIt() {
        for (int i = 0; i < 3; i++) {
            arrayDeque.addLast(i);
        }

        Integer existingValue = 1; // will search by value, not index
        Integer actualReturnedElement = arrayDeque.get(existingValue);

        assertNotNull(actualReturnedElement);
        assertEquals(Integer.valueOf(1), actualReturnedElement);
    }

    @Test
    public void get_whenSearchingForExistingElementInEnd_returnsIt() {
        for (int i = 0; i < 3; i++) {
            arrayDeque.addLast(i);
        }

        Integer existingValue = 2; // will search by value, not index
        Integer actualReturnedElement = arrayDeque.get(existingValue);

        assertNotNull(actualReturnedElement);
        assertEquals(Integer.valueOf(2), actualReturnedElement);
    }

    @Test
    public void set_settingValidIndexElement_worksCorrectly() {
        arrayDeque.addLast(0);

        arrayDeque.set(0, 200);

        assertEquals(Integer.valueOf(200), arrayDeque.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_settingInvalidBiggerIndex_throwsException() {
        arrayDeque.set(5, 200);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_settingInvalidNegativeIndex_throwsException() {
        arrayDeque.set(-1, 200);
    }

    @Test(expected = IllegalStateException.class)
    public void peek_noElements_shouldThrowException() {
        arrayDeque.peek();
    }

    @Test
    public void peek_whenHavingElements_worksCorrectly() {
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);

        assertEquals(arrayDeque.peek(), Integer.valueOf(1));
    }

    @Test
    public void remove_whenRemovingElement_sizeIsDecreased() {
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);

        assertEquals(3, arrayDeque.size());

        arrayDeque.remove(Integer.valueOf(2));

        assertEquals(2, arrayDeque.size());
    }

    @Test
    public void remove_whenRemovingNonExistingElement_shouldReturnNull() {
        assertNull(arrayDeque.remove(Integer.valueOf(1)));
    }

    @Test
    public void remove_whenRemovingExistingElementFromTheMiddle_shouldNotBeInStructure() {
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);

        arrayDeque.remove(Integer.valueOf(2));

        assertNotNull(arrayDeque.get(Integer.valueOf(1)));
        assertNull(arrayDeque.get(Integer.valueOf(2)));
        assertNotNull(arrayDeque.get(Integer.valueOf(3)));
    }

    @Test
    public void remove_whenRemovingExistingElementFromTheBeginning_shouldNotBeInStructure() {
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);

        arrayDeque.remove(Integer.valueOf(1));

        assertNull(arrayDeque.get(Integer.valueOf(1)));
        assertNotNull(arrayDeque.get(Integer.valueOf(2)));
        assertNotNull(arrayDeque.get(Integer.valueOf(3)));
    }

    @Test
    public void remove_whenRemovingExistingElementFromTheEnd_shouldNotBeInStructure() {
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);

        arrayDeque.remove(Integer.valueOf(3));

        assertNotNull(arrayDeque.get(Integer.valueOf(1)));
        assertNotNull(arrayDeque.get(Integer.valueOf(2)));
        assertNull(arrayDeque.get(Integer.valueOf(3)));
    }

    @Test(expected = IllegalStateException.class)
    public void removeLast_whenRemovingFromEmptyStructure_shouldThrowException() {
        arrayDeque.removeLast();
    }

    @Test
    public void removeLast_whenRemovingFromFullStructure_shouldWorkProperly() {
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);

        arrayDeque.removeLast();

        assertNotNull(arrayDeque.get(Integer.valueOf(1)));
        assertNotNull(arrayDeque.get(Integer.valueOf(2)));
        assertNull(arrayDeque.get(Integer.valueOf(3)));
    }

    @Test(expected = IllegalStateException.class)
    public void removeFirst_whenRemovingFromEmptyStructure_shouldThrowException() {
        arrayDeque.removeFirst();
    }

    @Test
    public void removeFirst_whenRemovingFromFullStructure_worksCorrectly() {
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);

        arrayDeque.removeFirst();

        assertNull(arrayDeque.get(Integer.valueOf(1)));
        assertNotNull(arrayDeque.get(Integer.valueOf(2)));
        assertNotNull(arrayDeque.get(Integer.valueOf(3)));
    }

    @Test
    public void insert_whenInsertingAtBeginning_worksCorrectly() {
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);

        arrayDeque.insert(0, 100);

        assertEquals(Integer.valueOf(100), arrayDeque.get(0));
        assertEquals(Integer.valueOf(1), arrayDeque.get(1));
        assertEquals(Integer.valueOf(2), arrayDeque.get(2));
        assertEquals(Integer.valueOf(3), arrayDeque.get(3));
    }

    @Test
    public void insert_whenInsertingInTheMiddle_worksCorrectly() {
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);

        arrayDeque.insert(1, 100);

        assertEquals(Integer.valueOf(1), arrayDeque.get(0));
        assertEquals(Integer.valueOf(100), arrayDeque.get(1));
        assertEquals(Integer.valueOf(2), arrayDeque.get(2));
        assertEquals(Integer.valueOf(3), arrayDeque.get(3));
    }

    @Test
    public void insert_whenInsertingAtEnd_worksCorrectly() {
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);

        arrayDeque.insert(2, 100);

        assertEquals(Integer.valueOf(1), arrayDeque.get(0));
        assertEquals(Integer.valueOf(2), arrayDeque.get(1));
        assertEquals(Integer.valueOf(100), arrayDeque.get(2));
        assertEquals(Integer.valueOf(3), arrayDeque.get(3));
    }

    @Test
    public void insert_whenInsertingInAlmostFullStructure_worksCorrectly() {
        int initialCapacity = 8;
        for (int i = 0; i < initialCapacity - 1; i++) {
            arrayDeque.addLast(i);
        }

        arrayDeque.insert(1, 100);

        assertEquals(Integer.valueOf(0), arrayDeque.get(0));
        assertEquals(Integer.valueOf(100), arrayDeque.get(1));
        for (int i = 2; i < initialCapacity; i++) {
            assertEquals(Integer.valueOf(i - 1), arrayDeque.get(i));
        }
    }

    @Test
    public void insert_whenInsertingInFullStructure_worksCorrectly() {
        int initialCapacity = 8;
        for (int i = 0; i < initialCapacity; i++) {
            arrayDeque.addLast(i);
        }

        arrayDeque.insert(1, 100);

        assertEquals(Integer.valueOf(0), arrayDeque.get(0));
        assertEquals(Integer.valueOf(100), arrayDeque.get(1));
        for (int i = 2; i < initialCapacity; i++) {
            assertEquals(Integer.valueOf(i - 1), arrayDeque.get(i));
        }
    }
}
