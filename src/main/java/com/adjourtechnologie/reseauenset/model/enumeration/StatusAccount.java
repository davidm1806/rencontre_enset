package com.adjourtechnologie.reseauenset.model.enumeration;

import lombok.Getter;

@Getter
public enum StatusAccount {

    NOUVEAU("nouveau"),
    ANCIEN("ancien");

    String name;

    StatusAccount(String name){
        this.name = name;
    }


}
