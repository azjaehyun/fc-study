package com.myapp.repository;

import com.myapp.domain.Applicant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Applicant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {}
