package com.adjourtechnologie.reseauenset.repository;

import com.adjourtechnologie.reseauenset.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    @Modifying
    @Query(nativeQuery = true, value = "delete from utilisateur_app_roles where utilisateur_id= :idUser and app_roles_id= :idRole")
    void  removeRoleAtUser(@Param("idUser") Long idUser, @Param("idRole") Long idRole);

    AppRole findByName(String name);
}
