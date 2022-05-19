package com.ifeb2.userservice.repository;

import com.ifeb2.scdevbase.repository.BaseRepository;
import com.ifeb2.userservice.domain.ScMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScMenuRepository extends BaseRepository<ScMenu> {

    @Query(value = "SELECT sm.* FROM sc_menu  sm WHERE sm.id IN " +
            "( SELECT menu_id FROM sc_role_menu WHERE role_id IN ( SELECT role_id FROM sc_user_role WHERE user_id =:uid AND del = '0' ) " +
            "AND sm.del = '0' AND sm.is_show = '1');", nativeQuery = true)
    List<ScMenu> findListForUser(@Param("uid") Long uid);

}
