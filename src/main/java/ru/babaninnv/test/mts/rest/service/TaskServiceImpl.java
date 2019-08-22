package ru.babaninnv.test.mts.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import ru.babaninnv.test.mts.rest.domain.Task;
import ru.babaninnv.test.mts.rest.domain.TaskStatus;
import ru.babaninnv.test.mts.rest.respositories.TaskRepository;

/**
 * Сервис обработки запросов по задаче
 *
 * @author Nikita Babanin
 * @version 1.0
 */
@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    private final ThreadPoolTaskScheduler scheduledExecutorService;

    /**
     * Значение задержки в секундах
     * */
    private long delaySeconds;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           ThreadPoolTaskScheduler scheduledExecutorService,
                           @Value("${app.delay.seconds}") long delaySeconds) {
        this.taskRepository = taskRepository;
        this.scheduledExecutorService = scheduledExecutorService;
        this.delaySeconds = delaySeconds;
    }

    @Override
    public String add() {
        String guid = UUID.randomUUID().toString();
        logger.info("guid: {}", guid);
        taskRepository.save(new Task(guid, TaskStatus.created, new Date()));

        scheduledExecutorService.execute(() -> {
            updateStatus(guid, TaskStatus.running);

            scheduledExecutorService.schedule(() -> {
                updateStatus(guid, TaskStatus.finished);
            }, Instant.now().plus(delaySeconds, ChronoUnit.SECONDS));
        });

        return guid;
    }

    @Override
    public Optional<Task> getById(String guid) {
        return taskRepository.findById(guid);
    }

    /**
     * Обновляет статус и дату/время
     *
     * @param guid идентификатор задачи
     * @param taskStatus новый статус
     */
    private void updateStatus(String guid, TaskStatus taskStatus) {
        taskRepository.findById(guid).ifPresent(task -> {
            logger.info("Task: {}", task);
            task.setStatus(taskStatus);
            task.setTimestamp(new Date());
            taskRepository.save(task);
        });
    }
}
