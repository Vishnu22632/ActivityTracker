package com.synergytech.tms.repository;

import com.synergytech.tms.model.Task;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Stateless
public class TaskRepository extends BaseRepository<Task, Long> {

    public TaskRepository() {
        super(Task.class);
    }

    public List<Task> findByProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> taskRoot = cq.from(Task.class);
        cq.where(cb.equal(taskRoot.get("project").get("id"), projectId));
        TypedQuery<Task> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    public Long countTasksByProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Task> taskRoot = cq.from(Task.class);
        cq.select(cb.count(taskRoot))
          .where(cb.equal(taskRoot.get("project").get("id"), projectId));
        TypedQuery<Long> query = entityManager.createQuery(cq);
        return query.getSingleResult();
    }

    public Long countCompletedTasksByProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Task> taskRoot = cq.from(Task.class);
        cq.select(cb.count(taskRoot))
          .where(cb.and(
              cb.equal(taskRoot.get("project").get("id"), projectId),
              cb.equal(taskRoot.get("taskStatus"), Task.TaskStatus.COMPLETED)
          ));
        TypedQuery<Long> query = entityManager.createQuery(cq);
        return query.getSingleResult();
    }

    public void delete(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
