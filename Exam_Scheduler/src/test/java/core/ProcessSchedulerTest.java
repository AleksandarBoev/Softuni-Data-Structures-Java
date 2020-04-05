package core;

import model.ScheduledTask;
import model.Task;
import org.junit.Before;
import org.junit.Test;
import shared.Scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProcessSchedulerTest {
    private Scheduler scheduler;

    @Before
    public void setUp() {
        this.scheduler = new ProcessScheduler();
        for (int i = 1; i <= 20; i++) {
            this.scheduler.add(new ScheduledTask(i, "test_description"));
        }
    }

    @Test
    public void testPeekOnSingleElement() {
        Scheduler scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(100, "test_description"));
        Task task = scheduler.peek();
        assertNotNull(task);
        assertEquals(100, task.getId());
    }

    @Test
    public void testPeekOnMultipleElement() {
        Scheduler scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(73, "test_description"));
        for (int i = 1; i <= 20; i++) {
            scheduler.add(new ScheduledTask(i, "test_description"));
        }
        scheduler.add(new ScheduledTask(100, "test_description"));
        Task task = scheduler.peek();
        assertNotNull(task);
        assertEquals(73, task.getId());
    }

    @Test
    public void testAddOnMultipleElement() {
        Task task = this.scheduler.peek();
        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals(20, this.scheduler.size());

        int expectedId = 1;
        while (this.scheduler.size() > 0) {
            Task process = this.scheduler.process();
            assertNotNull(process);
            assertEquals(expectedId++, process.getId());
        }
        assertEquals(21, expectedId);
    }

    @Test
    public void testAddOnSingleElement() {
        Scheduler scheduler = new ProcessScheduler();
        assertNull(scheduler.peek());
        assertEquals(0, scheduler.size());

        scheduler.add(new ScheduledTask(1, "test_description"));

        assertNotNull(scheduler.peek());
        assertEquals(1, scheduler.peek().getId());
        assertEquals(1, scheduler.size());
    }

    @Test
    public void testPeekShouldReturnCorrectAndShouldNotRemove() {
        Scheduler scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(42, "test_description"));
        for (int i = 1; i <= 20; i++) {
            scheduler.add(new ScheduledTask(i, "test_description"));
        }
        Task task = scheduler.peek();
        assertNotNull(task);
        assertEquals(42, task.getId());
        assertEquals(21, scheduler.size());
        task = scheduler.peek();
        assertNotNull(task);
        assertEquals(42, task.getId());
        assertEquals(21, scheduler.size());
    }

    @Test
    public void testProcessShouldReturnCorrectAndShouldRemove() {
        Scheduler scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(42, "test_description"));
        for (int i = 1; i <= 20; i++) {
            scheduler.add(new ScheduledTask(i, "test_description"));
        }
        Task task = scheduler.process();
        assertNotNull(task);
        assertEquals(42, task.getId());
        assertEquals(20, scheduler.size());
        task = scheduler.process();
        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals(19, scheduler.size());
    }

    @Test
    public void insertBefore_whenInsertingInStructureWithOnlyOneElement_shouldWorkCorrectly() {
        Task task = new ScheduledTask(1, "");
        Task insertedTask = new ScheduledTask(100, "");

        scheduler = new ProcessScheduler();
        scheduler.add(task);
        scheduler.insertBefore(task.getId(), insertedTask);
        List<Task> actual = scheduler.toList();

        List<Task> expected = new ArrayList<>(Arrays.asList(insertedTask, task));

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void insertBefore_whenInsertingInBeginningOfStructure_shouldWorkCorrectly() {
        Task insertedTask = new ScheduledTask(100, "");

        List<Task> expected = scheduler.toList();
        expected.add(0, insertedTask);

        scheduler.insertBefore(1, insertedTask);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void insertBefore_whenInsertingInMiddleOfStructure_shouldWorkCorrectly() {
        Task insertedTask = new ScheduledTask(100, "");

        List<Task> expected = scheduler.toList();
        expected.add(4, insertedTask);

        scheduler.insertBefore(5, insertedTask);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void insertBefore_whenInsertingInEndOfStructure_shouldWorkCorrectly() {
        Task insertedTask = new ScheduledTask(100, "");

        List<Task> expected = scheduler.toList();
        expected.add(expected.size() - 1, insertedTask);

        scheduler.insertBefore(20, insertedTask);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void insertBefore_whenInserting_shouldIncrementSize() {
        int expectedSize = scheduler.size() + 1;

        scheduler.insertBefore(1, new ScheduledTask(100, ""));
        int actualSize = scheduler.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertABefore_whenGivenNonExistingId_shouldThrowException() {
        scheduler.insertBefore(-1, new ScheduledTask(100, ""));
    }

    @Test
    public void insertAfter_whenInsertingInStructureWithOnlyOneElement_shouldWorkCorrectly() {
        Task task = new ScheduledTask(1, "");
        Task insertedTask = new ScheduledTask(100, "");

        scheduler = new ProcessScheduler();
        scheduler.add(task);
        scheduler.insertAfter(task.getId(), insertedTask);
        List<Task> actual = scheduler.toList();

        List<Task> expected = new ArrayList<>(Arrays.asList(task, insertedTask));

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void insertAfter_whenInsertingInBeginningOfStructure_shouldWorkCorrectly() {
        Task insertedTask = new ScheduledTask(100, "");

        List<Task> expected = scheduler.toList();
        expected.add(1, insertedTask);

        scheduler.insertAfter(1, insertedTask);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void insertAfter_whenInsertingInMiddleOfStructure_shouldWorkCorrectly() {
        Task insertedTask = new ScheduledTask(100, "");

        List<Task> expected = scheduler.toList();
        expected.add(5, insertedTask);

        scheduler.insertAfter(5, insertedTask);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void insertAfter_whenInsertingInEndOfStructure_shouldWorkCorrectly() {
        Task insertedTask = new ScheduledTask(100, "");

        List<Task> expected = scheduler.toList();
        expected.add(insertedTask);

        scheduler.insertAfter(20, insertedTask);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void insertAfter_whenInserting_shouldIncrementSize() {
        int expectedSize = scheduler.size() + 1;

        scheduler.insertAfter(1, new ScheduledTask(100, ""));
        int actualSize = scheduler.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertAfter_whenGivenNonExistingId_shouldThrowException() {
        scheduler.insertAfter(-1, new ScheduledTask(100, ""));
    }

    @Test
    public void find_whenSearchingById_shouldWorkCorrectly() {
        Task foundTask = scheduler.find(5);

        assertEquals(5, foundTask.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void find_whenSearchingByNonExistingId_shouldThrowException() {
        scheduler.find(-100);
    }

    @Test
    public void find_whenSearchingByTask_shouldWorkCorrectly() {
        Task foundTask = scheduler.find(new ScheduledTask(5, ""));

        assertEquals(5, foundTask.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void find_whenSearchingByNonExistingTask_shouldThrowException() {
        scheduler.find(new ScheduledTask(-100, ""));
    }

    @Test
    public void reschedule_whenSwapping_shouldWorkCorrectly() {
        List<Task> expected = scheduler.toList();
        scheduler.reschedule(new ScheduledTask(5, ""), new ScheduledTask(10, ""));
        List<Task> actual = scheduler.toList();

        Task temp = expected.get(4);
        expected.set(4, expected.get(9));
        expected.set(9, temp);

        assertSizesAndContent(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reschedule_whenFirstTaskIsWithInvalidId_shouldThrowException() {
        scheduler.reschedule(new ScheduledTask(-10, ""), new ScheduledTask(10, ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void reschedule_whenSecondTaskIsWithInvalidId_shouldThrowException() {
        scheduler.reschedule(new ScheduledTask(5, ""), new ScheduledTask(-10, ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void reschedule_whenBothTasksAreWithInvalidIds_shouldThrowException() {
        scheduler.reschedule(new ScheduledTask(-5, ""), new ScheduledTask(-10, ""));
    }

    @Test
    public void toArray_shouldWorkCorrectly() {
        List<Task> expected = scheduler.toList();
        List<Task> actual = new ArrayList<>(Arrays.asList(scheduler.toArray()));

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void toArray_whenCalledOnEmptyStructure_shouldReturnEmptyArray() {
        scheduler = new ProcessScheduler();
        assertEquals(0, scheduler.toArray().length);
    }

    @Test
    public void reverse() {
        this.scheduler = new ProcessScheduler();
        for (int i = 1; i <= 3; i++) {
            this.scheduler.add(new ScheduledTask(i, ""));
        }

        List<Task> expected = new ArrayList<>();
        expected.add(new ScheduledTask(3, ""));
        expected.add(new ScheduledTask(2, ""));
        expected.add(new ScheduledTask(1, ""));

        this.scheduler.reverse();
        assertSizesAndContent(expected, this.scheduler.toList());
    }

    //TODO should it throw exception? or return false?
    @Test(expected = IllegalArgumentException.class)
    public void remove_whenRemovingByIdNonExistingElement_shouldThrowException() {
        scheduler.remove(-10);
    }

    @Test
    public void remove_whenRemovingByIdOnlyElement_shouldWorkCorrectly() {
        scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(1, ""));
        scheduler.remove(1);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(new ArrayList<Task>(), actual);
    }

    @Test
    public void remove_whenRemovingByIdFirstElement_shouldWorkCorrectly() {
        List<Task> expected = scheduler.toList();
        expected.remove(0);

        scheduler.remove(1);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void remove_whenRemovingByIdLastElement_shouldWorkCorrectly() {
        List<Task> expected = scheduler.toList();
        expected.remove(expected.size() - 1);

        scheduler.remove(20);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void remove_whenRemovingByIdMiddleElement_shouldWorkCorrectly() {
        List<Task> expected = scheduler.toList();
        expected.remove(5);

        scheduler.remove(6);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void remove_whenRemovingById_sizeShouldDecrement() {
        int expectedSize = scheduler.size() - 1;
        scheduler.remove(1);
        int actualSize = scheduler.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void remove_whenRemovingByTaskOnlyElement_shouldWorkCorrectly() {
        scheduler = new ProcessScheduler();
        Task onlyTask = new ScheduledTask(1, "");
        scheduler.add(onlyTask);
        scheduler.remove(onlyTask);
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(new ArrayList<Task>(), actual);
    }

    @Test
    public void remove_whenRemovingByTaskFirstElement_shouldWorkCorrectly() {
        List<Task> expected = scheduler.toList();
        expected.remove(0);

        scheduler.remove(new ScheduledTask(1, ""));
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void remove_whenRemovingByTaskLastElement_shouldWorkCorrectly() {
        List<Task> expected = scheduler.toList();
        expected.remove(expected.size() - 1);

        scheduler.remove(new ScheduledTask(20, ""));
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test
    public void remove_whenRemovingByTaskMiddleElement_shouldWorkCorrectly() {
        List<Task> expected = scheduler.toList();
        expected.remove(5);

        scheduler.remove(new ScheduledTask(6, ""));
        List<Task> actual = scheduler.toList();

        assertSizesAndContent(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void remove_whenRemovingByTaskNonExistingElement_shouldThrowException() {
        scheduler.remove(new ScheduledTask(-10, ""));
    }

    @Test
    public void remove_whenRemovingByTask_sizeShouldDecrement() {
        int expectedSize = scheduler.size() - 1;
        scheduler.remove(new ScheduledTask(1, ""));
        int actualSize = scheduler.size();

        assertEquals(expectedSize, actualSize);
    }

    private void assertSizesAndContent(List<Task> expected, List<Task> actual) {
        assertEquals("Wrong sizes!", expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals("Wrong task ids!", expected.get(i).getId(), actual.get(i).getId());
        }
    }
}