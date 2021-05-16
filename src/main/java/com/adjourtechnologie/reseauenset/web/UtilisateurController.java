package com.adjourtechnologie.reseauenset.web;

import com.adjourtechnologie.reseauenset.model.AppRole;
import com.adjourtechnologie.reseauenset.model.Utilisateur;
import com.adjourtechnologie.reseauenset.service.UtilisateurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/user_controller")
public class UtilisateurController {
    private final UtilisateurService UtilisateurService;


    public UtilisateurController(UtilisateurService UtilisateurService) {
        this.UtilisateurService = UtilisateurService;
    }

    @PostMapping("user_save")
    public ResponseEntity<Utilisateur> utilisateurSave(@RequestBody Utilisateur utilisateur, BindingResult result) {
        if(result.hasErrors())
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        return UtilisateurService.saveUser(utilisateur);
    }

    @GetMapping("user_find_all")
    public Page<Utilisateur> utilisateurPage(Pageable pageable) {
        return UtilisateurService.findAllUser(pageable);
    }

    @GetMapping("authorisation_find_all")
    public List<AppRole> rolesFindAll() {
        return UtilisateurService.findAllRoles();
    }

    @GetMapping("utilisateur_add_role")
    public ResponseEntity<?> utilisateurAddRole(String rolename, Long userId) {
        return UtilisateurService.addRoleToUser(rolename, userId);
    }

    @GetMapping("utilisateur_remove_role")
    public ResponseEntity<?> utilisateurRemoveRole(String rolename, Long userId) {
        return UtilisateurService.removeRoleToUser(rolename, userId);
    }

    @GetMapping("utilisateur_lock_and_unlock_user")
    public ResponseEntity<?> utilisateurLockAndUnlock(Long userId) {
        return UtilisateurService.activedUser(userId);
    }

    //for admin
    @PostMapping("reset_password")
    public ResponseEntity<?> resetPassword(String password, Long id) {
        return UtilisateurService.resetPassword(password, id);
    }


}
