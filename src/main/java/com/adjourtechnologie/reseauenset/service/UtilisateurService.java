package com.adjourtechnologie.reseauenset.service;

import com.adjourtechnologie.reseauenset.model.AppRole;
import com.adjourtechnologie.reseauenset.model.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UtilisateurService {
    ResponseEntity<Utilisateur> saveUser(Utilisateur utilisateur);
    Utilisateur findUserByUserName(String username);
    Utilisateur updateUtilisateur(Utilisateur utilisateur);
    ResponseEntity<?> addRoleToUser(String roleName, Long id);
    ResponseEntity<?> lockUser(Long idUser);
    ResponseEntity<?> activedUser(Long idUser);
    ResponseEntity<?> removeRoleToUser(String roleName, Long idUser);


    Page<Utilisateur> findAllUser(Pageable pageable);
    Page<Utilisateur> userActivatedFindAll(Pageable pageable);
    List<AppRole> findAllRoles();

    //rechercher compte employé
    List<Utilisateur> findByAppRolesAndByName(String roleName, String search);
    List<Utilisateur> findByAppRolesAndByName(String roleName);

    ResponseEntity<?> resetPassword(String password, Long userId);

    ResponseEntity<Utilisateur> updateAccountUser(Utilisateur utilisateur);
    ResponseEntity<Utilisateur> updatePasswordUser(Utilisateur utilisateur);

    int countAllUser();
    int countAllUserConnceted();
    int countAllUserUnActivated();

    ResponseEntity<?> userSetStatusOnLine(boolean value);
}
