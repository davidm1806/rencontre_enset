package com.adjourtechnologie.reseauenset.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(columnDefinition = "MEDIUMTEXT")
    @NonNull
    private String content;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime send_at = LocalDateTime.now();
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updateAt;
    private String pseudo;
    private String username;
    private Long userId;

    private String firstName;
    private String lastName;
    private String sexe;

    @ManyToOne
    private Account sensBy;

    private Boolean epingle = false;
    private Boolean updated = false;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Group group;
    @Column(name = "group_id_doublon")
    private Long groupId;
}
