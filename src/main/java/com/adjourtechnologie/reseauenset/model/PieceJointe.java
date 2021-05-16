package com.adjourtechnologie.reseauenset.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PieceJointe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String message;

    private String originaleFileName;
    private String remoteURL;
    private String localURL;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime sendAt;

    @ManyToOne
    private Account sensBy;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Group group;


    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long groupId;
}
