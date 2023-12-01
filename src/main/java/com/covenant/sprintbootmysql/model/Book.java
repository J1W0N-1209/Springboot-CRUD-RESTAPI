package com.covenant.sprintbootmysql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String isbn;
    @JsonBackReference
    @OneToMany(mappedBy = "book",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Lend> lends;
}
