package com.codelikealexito.client.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    private Role(String name) {
        this.name = name;
    }

    public static Role giveRole(String name) {
        return new Role(name);
    }
}
