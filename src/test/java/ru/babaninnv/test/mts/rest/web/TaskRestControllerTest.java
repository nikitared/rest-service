package ru.babaninnv.test.mts.rest.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Optional;

import ru.babaninnv.test.mts.rest.domain.Task;
import ru.babaninnv.test.mts.rest.domain.TaskStatus;
import ru.babaninnv.test.mts.rest.service.TaskService;

import static org.mockito.ArgumentMatchers.anyString;

/**
 * @author Nikita Babanin
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRestControllerTest {

    private MockMvc mvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void task() throws Exception {
        String guid = "f7fb6228-319c-4685-8f35-051a4e20b0f9";

        Mockito.when(taskService.add()).thenReturn(guid);

        mvc.perform(MockMvcRequestBuilders.get("/task"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().string(guid));

        Mockito.verify(taskService).add();
    }

    @Test
    public void getTaskById_When_GuidIs_Correct() throws Exception {

        String guid = "f7fb6228-319c-4685-8f35-051a4e20b0f9";

        Mockito.when(taskService.getById(anyString()))
                .thenReturn(Optional.of(new Task(guid, TaskStatus.created, new Date())));

        mvc.perform(MockMvcRequestBuilders.get(String.format("/task/%s", guid)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("created"));

        Mockito.verify(taskService).getById(guid);
    }

    @Test
    public void getTaskById_When_GuidIs_Incorrect() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/task/12300----4-234-34"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getTaskById_When_TaskIs_NotFound() throws Exception {

        String guid = "f7fb6228-319c-4685-8f35-051a4e20b0f9";

        Mockito.when(taskService.getById(anyString())).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get(String.format("/task/%s", guid)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value((String.format("Задача с guid %s не найдена", guid))));

        Mockito.verify(taskService).getById(guid);
    }
}