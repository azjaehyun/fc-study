package com.co.kr.repository;

import com.co.kr.domain.Corp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Corp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorpRepository extends JpaRepository<Corp, Long> {}
