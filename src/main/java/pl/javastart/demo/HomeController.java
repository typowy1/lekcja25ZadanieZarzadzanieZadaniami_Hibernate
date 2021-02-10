package pl.javastart.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private TaskRepository taskRepository;

    //spring podczas tworzenia home kontrolera ustawi tez task repository
    public HomeController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        //spring podczas tworzenia home kontrolera ustawi tez task repository
        //wyświetlanie listy zadań które są jeszcze do zrobienia
        List<Task> tasks = taskRepository.findNotDone(); //pobieramy zadan z repo
        List<Task> tasksDone = taskRepository.findDone(); //pobieramy zadan z repo
        model.addAttribute("tasks", tasks); //pobieramy zadania
        model.addAttribute("tasksDone", tasksDone); //wyświetlanie listy zadań już zrealizowanych (archiwum)
        model.addAttribute("newTask", new Task()); //dodawanie nowego zadania
        return "home";
    }

    //    oznaczanie zadania jako gotowego
    @GetMapping("/done")
    public String markAsDone(@RequestParam Long id) {
        //wyszukujemy z repo po id
        Task task = taskRepository.findById(id);
        task.setDone(true); //oznaczamy jako zrobione
        // i zapisujemy do bazy
        taskRepository.save(task);
        //po wszystkim przekierowujemy na strone główną
        return "redirect:/";
    }

    @PostMapping("/add")
    public String add(Task task) {
        taskRepository.save(task);
        return "redirect:/";
    }
}
