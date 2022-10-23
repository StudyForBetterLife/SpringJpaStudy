package com.jpa.repository;

import com.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositoryJpa extends JpaRepository<Member, Long> {
}