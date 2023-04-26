package com.co.kr.repository;

import com.co.kr.domain.JobUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobUserRepository extends JpaRepository<JobUser, Long> {}
