package pl.javastart.demo;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TaskRepository {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    //będzie to podpinał spring
    public TaskRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        entityManager = entityManagerFactory.createEntityManager();
    }

    //wyświetlanie listy zadań które są jeszcze do zrobienia
    public List<Task> findNotDone() {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.isDone = false", Task.class);
//        List<Task> resultList = query.getResultList();
        return query.getResultList();
    }

    public List<Task> findDone() {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.isDone = true", Task.class);
//        List<Task> resultList = query.getResultList();
        return query.getResultList();
    }

    public Task findById(Long id) {
        return entityManager.find(Task.class, id); //wyszukanie po id
    }

    public void save(Task task) {
        //pier musimy otworzyc sesje i zamknac
        entityManager.getTransaction().begin();
        entityManager.persist(task); // zapisuje obiekt
        entityManager.getTransaction().commit();
    }
}
