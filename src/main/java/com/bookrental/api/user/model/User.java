package com.bookrental.api.user.model;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(nullable = false, defaultValue = "Fiction", minLength = 3, maxLength = 25)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "mobile_no", unique = true )
    @Schema(defaultValue ="9868090501", minLength = 10, maxLength = 13)
    private String mobileNo;

    @Schema(defaultValue ="address", minLength = 3, maxLength = 25)
    private String address;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Hidden
    Set<UsersRoles> roles;
}
