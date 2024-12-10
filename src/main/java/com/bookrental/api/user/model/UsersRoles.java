package com.bookrental.api.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "user_roles_tb", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
public class UsersRoles {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_tb_seq_gen")
    @SequenceGenerator(name = "user_role_tb_seq_gen", sequenceName = "user_role_tb_seq", allocationSize = 1)
    private Long id;

    private boolean isActive;


    @ManyToOne
    @Hidden
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @Hidden
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Roles role;

}
