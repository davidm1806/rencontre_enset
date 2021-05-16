package com.adjourtechnologie.reseauenset.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Annonce {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private String title;
    @NotNull
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.systemDefault());
    private LocalDateTime update_at = LocalDateTime.now();

    @ManyToOne(cascade = {CascadeType.DETACH})
    private Account proprietaire;

}
