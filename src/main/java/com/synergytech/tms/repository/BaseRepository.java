package com.synergytech.tms.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

public abstract class BaseRepository<T, ID extends Serializable> {

    @PersistenceContext(unitName = "tmsDS")
    protected EntityManager entityManager;

    private final Class<T> entityClass;
    
      
        
    
    protected CriteriaQuery<T> criteriaQuery;
    protected CriteriaBuilder criteriaBuilder;
    protected Root<T> root;
    protected List<Predicate> predicateList;
    
    
    

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public void create(T entity) {
        entityManager.persist(entity);
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Transactional
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Transactional
    public void delete(ID id) {
        findById(id).ifPresent(entityManager::remove);
    }

    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        cq.from(entityClass);
        TypedQuery<T> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    public long count() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(entityClass)));
        TypedQuery<Long> query = entityManager.createQuery(cq);
        return query.getSingleResult();
    }
    
    public int countEntity(Map<String, FilterMeta> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> root = countQuery.from(entityClass);
        countQuery.select(cb.count(root));
        applyFilters(filters, root, countQuery);
        return entityManager.createQuery(countQuery).getSingleResult().intValue();
    }

        
    public List<T> getEntity(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        this.criteriaBuilder = cb;
        this.criteriaQuery = query;
        this.root = root;
        this.predicateList = new ArrayList<>();

        applyFilters(filters, root, query);
        // Apply sorting (optional)
        // Apply pagination
        return entityManager.createQuery(query)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private void applyFilters(Map<String, FilterMeta> filters, Root<T> root, CriteriaQuery<?> query) {
        Predicate[] predicates = filters.values().stream()
                .map(filter -> criteriaBuilder.like(root.get(filter.getField()), "%" + filter.getFilterValue() + "%"))
                .toArray(Predicate[]::new);
        query.where(predicates);
    }
    
    
    
    
}
