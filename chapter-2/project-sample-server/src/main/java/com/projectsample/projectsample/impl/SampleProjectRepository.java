package com.projectsample.projectsample.impl;

import com.projectsample.projectsample.domain.SampleProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleProjectRepository extends JpaRepository<SampleProject,Long> {

}
