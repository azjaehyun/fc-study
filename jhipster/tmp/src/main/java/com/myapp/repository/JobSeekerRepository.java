package com.myapp.repository;

import com.myapp.domain.JobSeeker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobSeeker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {}
