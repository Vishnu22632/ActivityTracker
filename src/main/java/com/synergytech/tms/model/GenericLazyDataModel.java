package com.synergytech.tms.model;

import com.synergytech.tms.repository.BaseRepository;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author Vishnu 
 */
public class GenericLazyDataModel<T> extends LazyDataModel<T> {

    private final BaseRepository<T, ?> repository;

    public GenericLazyDataModel(BaseRepository<T, ?> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return repository.getEntity(first, pageSize, sortBy, filterBy);
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return repository.countEntity(filterBy);
    }
}
