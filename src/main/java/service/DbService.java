package service;

import com.crud.tasks.repository.TaskRepository;
import domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {
@Autowired
    private TaskRepository repository;

public List<Task> getAllTasks(){
    return repository.findAll();
}
public List<Task> getAllById(Long id){
    return  repository.findAllById(id);

}

}
