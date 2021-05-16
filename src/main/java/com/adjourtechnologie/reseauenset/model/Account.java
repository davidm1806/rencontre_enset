package com.adjourtechnologie.reseauenset.model;

import com.adjourtechnologie.reseauenset.model.enumeration.StatusAccount;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * date: 2021
 * project: si-recontre-enset
 * author: David matjaba
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private String first_name;
    @NotNull
    private String last_name;
    @Length(max = 50)
    private String email;
    @Length(max = 20)
    private String phone;
    private String address;
    @Enumerated(EnumType.STRING)
    private StatusAccount status;
    private Boolean isActive;
    private LocalDate dateEntree;
    private LocalDate dateDiplomation;
    private Boolean showPrivateProfile;
    @NotNull
    @Length(max = 18)
    private String matricule;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String photoProfile;
    private Boolean hasPhotoProfile = false;
    @Length(max = 10)
    private String promotion;
    private Boolean showPrivateInfo;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime createAt = LocalDateTime.now();
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updateAt = LocalDateTime.now();

    @Transient
    private MultipartFile imagePart;

    @OneToOne(cascade = CascadeType.ALL)
    private Utilisateur utilisateur;


    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JsonIgnore
    private List<Account> frends;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private List<Group> groups;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Filiere filiere;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long utilisateurId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long FiliereId;

}
