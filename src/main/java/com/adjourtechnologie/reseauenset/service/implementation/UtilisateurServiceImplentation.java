package com.adjourtechnologie.reseauenset.service.implementation;

import com.adjourtechnologie.reseauenset.model.AppRole;
import com.adjourtechnologie.reseauenset.model.Utilisateur;
import com.adjourtechnologie.reseauenset.repository.AppRoleRepository;
import com.adjourtechnologie.reseauenset.repository.UtilisateurRepository;
import com.adjourtechnologie.reseauenset.service.UtilisateurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UtilisateurServiceImplentation implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final AppRoleRepository appRoleRepository;
    private final AuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurServiceImplentation(UtilisateurRepository utilisateurRepository,
                                          AppRoleRepository appRoleRepository,
                                          AuthenticationFacade authenticationFacade,
                                          PasswordEncoder passwordEncoder) {

        this.utilisateurRepository = utilisateurRepository;
        this.appRoleRepository = appRoleRepository;
        this.authenticationFacade = authenticationFacade;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<Utilisateur> saveUser(Utilisateur utilisateur) {
        Utilisateur user;
        boolean addRoleuser = true;
        System.out.println(utilisateur);
        if (utilisateur.getId() != null) {
            addRoleuser = false;
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(utilisateur.getId());

            if (!utilisateurOptional.isPresent())
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

            user = utilisateurOptional.get();
        } else {
            // verification if user don't exist for created a new user
            if(utilisateurRepository.findByUsername(utilisateur.getUsername()) != null)
                return new ResponseEntity<>(HttpStatus.CONFLICT);

            if(utilisateur.getPassword() == null)
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

            user = new Utilisateur();
        }

        /*if(!utilisateur.getPassword().equals(utilisateur.getPasswordConfirm()))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);*/
        if(utilisateur.getPassword() != null)
            user.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        user.setUsername(utilisateur.getUsername());
        user.setUpdateAt(LocalDateTime.now());
        user.setPseudo(utilisateur.getPseudo());

        user = utilisateurRepository.save(user);


        if (addRoleuser) {
            if(addRoleToUser("USER", user.getId()).getStatusCode()!= HttpStatus.OK)
                return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Override
    public Utilisateur findUserByUserName(String username) {
        return utilisateurRepository.findByUsername(username);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public ResponseEntity<?> addRoleToUser(String roleName, Long id) {
        AppRole role = appRoleRepository.findByName(roleName);
        if(role == null)
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        if(!utilisateurOptional.isPresent())
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.getAppRoles().add(role);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> lockUser(Long idUser) {
//        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(idUser);
//        if (utilisateurOptional.isPresent()) {
//            Utilisateur utilisateur = utilisateurOptional.get();
//            utilisateur.setLoocked(!utilisateur.getLoocked());
//            return new ResponseEntity<>(utilisateurRepository.save(utilisateur), HttpStatus.OK);
//        } else
            return null;
    }

    @Override
    public ResponseEntity<?> activedUser(Long idUser) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(idUser);
        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            utilisateur.setActived(!utilisateur.isActived());
            return new ResponseEntity<>(utilisateurRepository.save(utilisateur), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    public ResponseEntity<?> removeRoleToUser(String roleName, Long idUser) {
        AppRole appRole= appRoleRepository.findByName(roleName);
        if(appRole == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        appRoleRepository.removeRoleAtUser(idUser, appRole.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Page<Utilisateur> findAllUser(Pageable pageable) {
        return utilisateurRepository.findAll(pageable);
    }

    @Override
    /*public List<AppRole> findAllRoles() {
        return appRoleRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
    }*/
    public List<AppRole> findAllRoles() {
        return appRoleRepository.findAll();
    }

    @Override
    public Page<Utilisateur> userActivatedFindAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Utilisateur> findByAppRolesAndByName(String roleName, String search) {
        return null;
    }

    @Override
    public List<Utilisateur> findByAppRolesAndByName(String roleName) {
        return utilisateurRepository.findByAppRolesAndByName(roleName);
    }

    @Override
    public ResponseEntity<?> resetPassword(String password, Long userId) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(userId);
        if(!optionalUtilisateur.isPresent())
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        optionalUtilisateur.get().setPassword(passwordEncoder.encode(password));
        utilisateurRepository.save(optionalUtilisateur.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Utilisateur> updateAccountUser(Utilisateur utilisateur) {
        Utilisateur user = utilisateurRepository.findByUsername(authenticationFacade.getAuthentication().getName());
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        if (utilisateur.getUsername() != null)
            user.setUsername(utilisateur.getUsername());
        if (utilisateur.getPassword() != null)
            user.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        if(utilisateur.getPseudo() != null)
            user.setPseudo(utilisateur.getPseudo());
        return new ResponseEntity<>(utilisateurRepository.save(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Utilisateur> updatePasswordUser(Utilisateur utilisateur) {
        Utilisateur user = utilisateurRepository.findByUsername(authenticationFacade.getAuthentication().getName());
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        user.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

        return new ResponseEntity<>(utilisateurRepository.save(user), HttpStatus.OK);
    }

    @Override
    public int countAllUser() {
        return utilisateurRepository.countAllUser();
    }

    @Override
    public int countAllUserConnceted() {
        return utilisateurRepository.countAllUserConnected();
    }

    @Override
    public int countAllUserUnActivated() {
//        return utilisateurRepository.countAllUserLoocked();
        return 0;
    }

    @Override
    public ResponseEntity<?> userSetStatusOnLine(boolean value) {
        String username;
        if(authenticationFacade.getAuthentication()==null)
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        username = authenticationFacade.getAuthentication().getName();

        utilisateurRepository.setStatusOnline(value, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
