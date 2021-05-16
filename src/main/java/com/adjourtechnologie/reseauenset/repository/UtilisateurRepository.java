package com.adjourtechnologie.reseauenset.repository;

import com.adjourtechnologie.reseauenset.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByUsername(String username);


    @Query(nativeQuery = true, value = "select u.* from utilisateur u " +
            "inner join utilisateur_app_roles uar on u.id = uar.utilisateur_id " +
            "inner join app_role ar on uar.app_roles_id = ar.id " +
            "where ar.name= :roleName")
    List<Utilisateur> findByAppRolesAndByName(String roleName);

    @Query(nativeQuery = true, value = "select count(id) as count_id from utilisateur")
    int countAllUser();

    @Query(nativeQuery = true, value = "select count(id) as count_id from utilisateur as u where u.is_online = true")
    int countAllUserConnected();

    @Query(nativeQuery = true, value = "update utilisateur set actived= :actived where id= :id")
    @Modifying
    void setActivate(boolean actived, long id);

    @Query(nativeQuery = true, value = "update utilisateur set is_online= :value where username= :username")
    @Modifying
    void setStatusOnline(boolean value, String username);



}
