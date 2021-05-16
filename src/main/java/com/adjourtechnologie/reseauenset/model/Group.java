package com.adjourtechnologie.reseauenset.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "group_account")
public class Group {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private String name;
    private String description;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime created_at = LocalDateTime.now();
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updated_at;

    @ManyToOne
    private Account createdBy;
    @ManyToOne
    private Account updatedBy;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, mappedBy = "groups")
    @JsonIgnore
    private List<Account> membres;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<PieceJointe> pieceJointes = new ArrayList<>();
}
