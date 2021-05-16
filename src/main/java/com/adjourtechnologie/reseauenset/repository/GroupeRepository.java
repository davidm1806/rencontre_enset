package com.adjourtechnologie.reseauenset.repository;

import com.adjourtechnologie.reseauenset.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeRepository extends JpaRepository<Group, Long> {
    Group findByName(String name);

}
