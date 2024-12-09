package com.bookrental.api.security.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_tb_seq_gen")
    @SequenceGenerator(name = "user_tb_seq_gen", sequenceName = "user_tb_seq", allocationSize = 1)
    private Long id;
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String password;
    private boolean isActive;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Hidden
    Set<UsersRoles> roles;
}
