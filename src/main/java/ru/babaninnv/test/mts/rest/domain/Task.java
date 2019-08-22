package ru.babaninnv.test.mts.rest.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Сущность(таблица) "Задача"
 *
 * @author Nikita Babanin
 * @version 1.0
 */
@Entity
public class Task {

    /**
     * Идентификатор задачи
     * */
    @Id
    private String guid;

    /**
     * Статус
     * */
    private TaskStatus status;

    /**
     * Дата и время создания
     * */
    private Date timestamp;

    public Task() {
    }

    public Task(String guid, TaskStatus status, Date timestamp) {
        this.guid = guid;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    @Override
    public String toString() {
        return "Task{" +
                "guid='" + guid + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}
