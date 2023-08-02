package com.application.repository;

import com.application.domain.Resume;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Resume entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {}
