package com.co.kr.repository;

import com.co.kr.domain.RecruitMatch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RecruitMatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecruitMatchRepository extends JpaRepository<RecruitMatch, Long> {}
