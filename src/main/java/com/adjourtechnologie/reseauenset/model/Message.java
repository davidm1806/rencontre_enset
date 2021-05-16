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
    private LocalDateTime send_at;

    @ManyToOne
    private Account sensBy;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Group group;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long groupId;
}
