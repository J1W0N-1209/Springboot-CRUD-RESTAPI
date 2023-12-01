package com.covenant.sprintbootmysql.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    // 연간관계 주인 : 외래키를 가진쪽 -> 항상 N
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonManagedReference // 연간관계 주인 반대
    private Author author;

}
