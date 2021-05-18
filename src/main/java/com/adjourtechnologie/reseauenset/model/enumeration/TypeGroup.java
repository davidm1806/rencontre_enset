package com.adjourtechnologie.reseauenset.model.enumeration;

import lombok.Getter;

@Getter
public enum TypeGroup {
    ROOT(1,"ENSET", "Ensemble des étudiants de l'enset"),
    DEPARTEMENT(2,"DEPARTEMENT", "Etudiant d'un même département"),
    AUTRE(2,"AUTRE", "Ancien étudiant, nouveau étudiant"),
    PROMOTION(1,"PROMOTION", "Etudiants d'une même promotion");

    int id;
    String name, description;

    TypeGroup(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
