package com.bookrental.api.member.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_sequence_generator")
    @SequenceGenerator(
            name = "member_sequence_generator",
            sequenceName = "member_sequence",
            allocationSize = 1
    )
    private Long id;
    private String name;
    private String email;
    private String mobileNo;
    private String address;

}
