package ru.babaninnv.test.mts.rest.respositories;

import org.springframework.data.repository.CrudRepository;

import ru.babaninnv.test.mts.rest.domain.Task;

/**
 * CRUD DAO для доступа к таблице {@code task}
 */
public interface TaskRepository extends CrudRepository<Task, String> {}
