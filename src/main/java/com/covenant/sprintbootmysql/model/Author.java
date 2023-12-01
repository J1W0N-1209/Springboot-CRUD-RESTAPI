package com.covenant.sprintbootmysql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK
    private String firstName;
    private String lastName;

    //EAGER(즉시로딩): 연관관계가 설정된 모든 테이블에 대해서 조인이 이뤄집니다.
    //LAZY(지연로딩): 기본적으로 연관관계 테이블을 조인하지 않고 조인이 필요한 경우에 Join을 합니다.
    //cascade: JPA에는 영속화란 개념이 있습니다. CascadeType.ALL이면 부모가 영속화가 되면 자식도 영속화가 됩니다. 정확한 표현은 아니지만 부모와 자식의 상태가 동시에 변하게 합니다.
    @JsonBackReference
    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Book> books;
}
