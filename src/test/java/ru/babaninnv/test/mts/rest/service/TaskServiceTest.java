package ru.babaninnv.test.mts.rest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import ru.babaninnv.test.mts.rest.domain.Task;
import ru.babaninnv.test.mts.rest.domain.TaskStatus;
import ru.babaninnv.test.mts.rest.respositories.TaskRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Nikita Babanin
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Test
    public void add() throws InterruptedException {

        TaskRepository taskRepository = mock(TaskRepository.class);
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(new Task("123", TaskStatus.created, new Date())));

        ThreadPoolTaskScheduler scheduledExecutorService = new ThreadPoolTaskScheduler();
        scheduledExecutorService.setThreadNamePrefix("TPTS-");
        scheduledExecutorService.initialize();

        TaskService taskService = new TaskServiceImpl(taskRepository, scheduledExecutorService, 10L);
        taskService.add();

        // порядок выполнения операцй обновления статуса
        InOrder inOrder = inOrder(taskRepository);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        inOrder.verify(taskRepository).save(taskCaptor.capture());
        assertEquals(TaskStatus.created, taskCaptor.getValue().getStatus());
        assertNotNull(taskCaptor.getValue().getTimestamp());

        Thread.yield();

        taskCaptor = ArgumentCaptor.forClass(Task.class);
        inOrder.verify(taskRepository).save(taskCaptor.capture());
        assertEquals(TaskStatus.running, taskCaptor.getValue().getStatus());

        TimeUnit.SECONDS.sleep(15L);

        taskCaptor = ArgumentCaptor.forClass(Task.class);
        inOrder.verify(taskRepository).save(taskCaptor.capture());
        assertEquals(TaskStatus.finished, taskCaptor.getValue().getStatus());
    }
}