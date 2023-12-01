package com.covenant.sprintbootmysql.repository;

import com.covenant.sprintbootmysql.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
