package com.job.repository;

import com.job.domain.JobPosting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobPosting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {}
