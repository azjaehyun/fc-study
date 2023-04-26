package com.co.kr.repository;

import com.co.kr.domain.Recruit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Recruit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecruitRepository extends JpaRepository<Recruit, Long> {}
