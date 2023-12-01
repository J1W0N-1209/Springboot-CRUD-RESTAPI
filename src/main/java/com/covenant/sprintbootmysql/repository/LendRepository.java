package com.covenant.sprintbootmysql.repository;

import com.covenant.sprintbootmysql.model.Book;
import com.covenant.sprintbootmysql.model.Lend;
import com.covenant.sprintbootmysql.model.LendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LendRepository extends JpaRepository<Lend,Long> {
    Optional<Lend> findByBookAndStatus(Book book, LendStatus lendStatus);
}
