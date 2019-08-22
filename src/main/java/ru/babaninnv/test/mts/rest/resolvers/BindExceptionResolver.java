package ru.babaninnv.test.mts.rest.resolvers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import ru.babaninnv.test.mts.rest.exceptions.TaskNotFoundException;

/**
 * Обработчик исключений выбрасываемых контроллером
 *
 * @author Nikita Babanin
 * @version 1.0
 */
@Component
public class BindExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        // установка http-статуса в зависимости от типа исключения
        if (ConstraintViolationException.class.equals(ex.getClass())) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else if (TaskNotFoundException.class.equals(ex.getClass())) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }

        // сериализует мапу с сообщением в формат JSON
        Map<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
