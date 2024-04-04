package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Timer;
import java.util.TimerTask;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

public class M22_VerificationWithTimeout {
    @Test
    void action022() {
        TaskManager taskManager = Mockito.mock(TaskManager.class);

        simulateTaskAddAction(taskManager, 50);

        //passes when someMethod() is called no later than within 100 ms
        //exits immediately when verification is satisfied (e.g. may not wait full 100 ms)
        verify(taskManager, timeout(100)).addTask("Task 1");
        //above is an alias to:
        verify(taskManager, timeout(100).times(1)).addTask("Task 1");

        simulateTaskAddAction(taskManager, 50);
        //passes as soon as someMethod() has been called 2 times under 100 ms
        verify(taskManager, timeout(100).times(2)).addTask("Task 1");

        //equivalent: this also passes as soon as someMethod() has been called 2 times under 100 ms
        verify(taskManager, timeout(100).atLeast(2)).addTask("Task 1");
    }

    private void simulateTaskAddAction(TaskManager taskManager, long delay) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                taskManager.addTask("Task 1");
            }
        }, delay);
    }

    static class TaskManager {
        public void addTask(String task) {
            // TODO: adding task
        }
    }
}