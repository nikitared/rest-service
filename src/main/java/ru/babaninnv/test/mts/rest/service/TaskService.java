package ru.babaninnv.test.mts.rest.service;

import java.util.Optional;

import ru.babaninnv.test.mts.rest.domain.Task;

/**
 * Сервис обработки запросов по задаче
 *
 * @author Nikita Babanin
 * @version 1.0
 */
public interface TaskService {

    /**
     * Выполняет добавление задачи в хранилище со статусом , затем изменение статуса
     *
     * @return идентификатор созданной задачи в виде guid'a
     * */
    String add();

    /**
     * Находит и возвращает задачу по идентификатору
     *
     * @param guid идентификатор задачи
     * @return задача
     */
    Optional<Task> getById(String guid);
}
