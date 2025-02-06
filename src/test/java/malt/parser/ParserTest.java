package malt.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


import java.util.ArrayList;
import java.util.List;

import malt.MaltException;
import malt.task.Task;
import malt.task.TaskList;
import malt.task.Todo;
import malt.ui.Ui;
import malt.storage.Storage;

public class ParserTest {

    // A dummy UI implementation to avoid console output during tests.
    private static class DummyUi extends Ui {
        public List<String> messages = new ArrayList<>();

        @Override
        public void showLine() {
            // No operation (or you can capture a message if needed)
        }

        @Override
        public void showWelcome() {
            // No operation
        }

        @Override
        public void showGoodbye() {
            messages.add("Goodbye");
        }


        public void showError(Exception e) {
            messages.add("Error: " + e.getMessage());
        }

        @Override
        public String readCommand() {
            return "";
        }
    }

    // A dummy Storage implementation to bypass file I/O.
    private static class DummyStorage extends Storage {
        public DummyStorage() {
            super("dummy.txt");
        }

        @Override
        public void saveTasks(List<Task> tasks) {
            // Do nothing
        }

        @Override
        public List<Task> loadTasks() {
            return new ArrayList<>();
        }
    }

    @Test
    public void testUnknownCommand() {
        TaskList tasks = new TaskList();
        DummyUi dummyUi = new DummyUi();
        DummyStorage dummyStorage = new DummyStorage();
        String input = "foobar";
        MaltException exception = assertThrows(MaltException.class, () -> {
            Parser.parseAndExecute(input, tasks, dummyUi, dummyStorage);
        });
        assertEquals("I'm sorry, but I don't know what that means!", exception.getMessage());
    }

    @Test
    public void testTodoCommand() throws MaltException {
        TaskList tasks = new TaskList();
        DummyUi dummyUi = new DummyUi();
        DummyStorage dummyStorage = new DummyStorage();

        // Issue todo command.
        String input = "todo test task";
        boolean exit = Parser.parseAndExecute(input, tasks, dummyUi, dummyStorage);

        assertFalse(exit);

        assertEquals(1, tasks.size());
        Task task = tasks.getTask(0);
        assertInstanceOf(Todo.class, task);
        assertEquals("[T][ ] test task", task.toString());
    }

    @Test
    public void testByeCommand() throws MaltException {
        TaskList tasks = new TaskList();
        DummyUi dummyUi = new DummyUi();
        DummyStorage dummyStorage = new DummyStorage();
        String input = "bye";
        boolean exit = Parser.parseAndExecute(input, tasks, dummyUi, dummyStorage);
        assertTrue(exit);
        assertTrue(dummyUi.messages.contains("Goodbye"));
    }
}
