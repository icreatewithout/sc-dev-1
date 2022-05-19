package com.ifeb2.scdevbase.service;

import com.ifeb2.scdevbase.domain.BaseEntity;
import com.ifeb2.scdevbase.repository.BaseRepository;
import com.ifeb2.scdevbase.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
public class BaseService<T extends BaseEntity, K extends BaseRepository<T>> {

    @Autowired
    public K repository;

    public T getById(Long id) {
        Optional<T> optional = repository.findById(id);
        return optional.orElse(null);
    }

    public T getOne(T t) {
        if (StringUtils.isBlank(t.getDel())) {
            t.setDel(Constants.NOT_DEl);
        }
        Example<T> example = Example.of(t);
        Optional<T> one = repository.findOne(example);
        return one.orElse(null);
    }

    public List<T> findList(T t) {
        if (StringUtils.isBlank(t.getDel())) {
            t.setDel(Constants.NOT_DEl);
        }
        Example<T> example = Example.of(t);
        return repository.findAll(example);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long save(T t) {
        if (StringUtils.isBlank(t.getDel())) {
            t.setDel(Constants.NOT_DEl);
        }
        repository.save(t);
        return t.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<T> saveAll(List<T> t) {
        return repository.saveAll(t);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(T t) {
        delete(t, false);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(T t, Boolean physical) {
        if (physical) {
            repository.delete(t);
        } else {
            t.setDel(Constants.DEl);
            repository.save(t);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        deleteById(id, false);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id, Boolean physical) {
        if (physical) {
            repository.deleteById(id);
        } else {
            T t = repository.getOne(id);
            t.setDel(Constants.DEl);
            repository.saveAndFlush(t);
        }
    }

    public Page<T> findPage(T t) {
        return findPage(t, "id");
    }

    public Page<T> findPage(T t, String... field) {
        return findPage(t, Sort.Direction.DESC, field);
    }

    public Page<T> findPage(T t, Sort.Direction sort, String... field) {
        if (StringUtils.isBlank(t.getDel())) {
            t.setDel(Constants.NOT_DEl);
        }
        Pageable pageable = PageRequest.of(t.getPageNum() - 1, t.getPageSize(), sort, field);
        Example<T> example = Example.of(t);
        return repository.findAll(example, pageable);
    }

}
