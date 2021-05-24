package com.adjourtechnologie.reseauenset.repository;

import com.adjourtechnologie.reseauenset.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupeRepository extends JpaRepository<Group, Long> {
    Group findByName(String name);
    List<Group> findByTypeGroup(String typeGroup);

    @Query(nativeQuery = true, value = "select g.* from group_account g " +
            "join account_groups ag on ag.groups_id=g.id " +
            "join account a on ag.membres_id = a.id " +
            "join utilisateur u on u.id = a.utilisateur_id " +
            "where u.id= :accountId")
    List<Group> groupByAccount(Long accountId);

}
