package com.bookrental.api.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_tb_seq_gen")
    @SequenceGenerator(name = "role_tb_seq_gen", sequenceName = "role_seq_gen",allocationSize = 1)
    private Long id;
    private String name;
    private boolean isActive;

}
