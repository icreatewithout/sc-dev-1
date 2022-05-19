package com.ifeb2.userservice.repository;

import com.ifeb2.scdevbase.repository.BaseRepository;
import com.ifeb2.userservice.domain.ScDept;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScDeptRepository extends BaseRepository<ScDept> {

    @Query(value = "select sd.* from sc_dept sd where sd.parent_id is null and sd.del = '0' order by sd.create_time desc limit 1", nativeQuery = true)
    ScDept findTheLast();

    @Query(value = "select sd.* from sc_dept sd where sd.parent_id=:parentId and sd.del = '0' order by sd.create_time desc limit 1", nativeQuery = true)
    ScDept findTheLastByParentId(@Param("parentId") Long parentId);
}
