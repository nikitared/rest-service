package ru.babaninnv.test.mts.rest.exceptions;

import java.text.MessageFormat;

/**
 * Исключение, выбрасываемое если задача не найдена
 *
 * @author Nikita Babanin
 * @version 1.0
 */
public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String guid) {
        super(MessageFormat.format("Задача с guid {0} не найдена", guid));
    }
}
