package com.adjourtechnologie.reseauenset.repository;

import com.adjourtechnologie.reseauenset.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupeRepository extends JpaRepository<Group, Long> {
    Group findByName(String name);
    List<Group> findByTypeGroup(String typeGroup);

}
