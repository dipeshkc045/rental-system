package com.bookrental.api.user.model;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
    @Hidden
    private Long id;

    @Schema(nullable = false, defaultValue = "Fiction", minLength = 3, maxLength = 25)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    @Schema(minLength = 5,maxLength = 15)
    private String password;

    @Column(name = "mobile_no", unique = true)
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")
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
