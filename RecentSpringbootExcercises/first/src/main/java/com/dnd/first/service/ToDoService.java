package com.dnd.first.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.dnd.first.model.ToDo;
import org.springframework.stereotype.Service;


@Service
public class ToDoService {
    private static List<ToDo> todos = new ArrayList<>();
    private static int todoCount = 3;

    static {
        todos.add(new ToDo(1, "surya", "Learn-MySql", new Date(),
                false));
        todos.add(new ToDo(2, "surya", "Learn SpringBoot ", new Date(), false));
        todos.add(new ToDo(3, "Jegan", "Learn Hibernate", new Date(),
                false));
    }

    public List<ToDo> retrieveTodos(String user) {
        System.out.println("welcome "+ user);
        List<ToDo> filteredTodos = new ArrayList<>();
        for (ToDo todo : todos) {
            if (todo.getUser().equals(user)) {
                filteredTodos.add(todo);


            }
        }
        return filteredTodos;
    }

    public void addTodo(String name, String desc, Date targetDate,
                        boolean isDone) {
        todos.add(new ToDo(++todoCount, name, desc, targetDate, isDone));
    }

    public void deleteTodo(int id) {
        Iterator<ToDo> iterator = todos.iterator();
        while (iterator.hasNext()) {
            ToDo todo = iterator.next();
            if (todo.getId() == id) {
                iterator.remove();
            }
        }
    }
}