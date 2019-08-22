package ru.babaninnv.test.mts.rest.web;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import ru.babaninnv.test.mts.rest.domain.TaskStatus;

/**
 * DTO-объект для передачи данных на клиент
 *
 * @author Nikita Babanin
 * @version 1.0
 */
public class TaskResource {

    /**
     * Статус
     * */
    private TaskStatus status;

    /**
     * Дата и время создания
     * */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date timestamp;

    public TaskResource(TaskStatus status, Date timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
