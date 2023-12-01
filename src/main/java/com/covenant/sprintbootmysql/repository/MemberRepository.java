package com.covenant.sprintbootmysql.repository;

import com.covenant.sprintbootmysql.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
