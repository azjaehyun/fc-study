package com.myapp.repository;

import com.myapp.domain.JobPosting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobPosting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {}
