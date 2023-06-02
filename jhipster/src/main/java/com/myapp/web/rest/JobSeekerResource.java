package com.myapp.web.rest;

import com.myapp.domain.JobSeeker;
import com.myapp.repository.JobSeekerRepository;
import com.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.myapp.domain.JobSeeker}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JobSeekerResource {

    private final Logger log = LoggerFactory.getLogger(JobSeekerResource.class);

    private static final String ENTITY_NAME = "jobSeeker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerResource(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }

    /**
     * {@code POST  /job-seekers} : Create a new jobSeeker.
     *
     * @param jobSeeker the jobSeeker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobSeeker, or with status {@code 400 (Bad Request)} if the jobSeeker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-seekers")
    public ResponseEntity<JobSeeker> createJobSeeker(@RequestBody JobSeeker jobSeeker) throws URISyntaxException {
        log.debug("REST request to save JobSeeker : {}", jobSeeker);
        if (jobSeeker.getId() != null) {
            throw new BadRequestAlertException("A new jobSeeker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobSeeker result = jobSeekerRepository.save(jobSeeker);
        return ResponseEntity
            .created(new URI("/api/job-seekers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-seekers/:id} : Updates an existing jobSeeker.
     *
     * @param id the id of the jobSeeker to save.
     * @param jobSeeker the jobSeeker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobSeeker,
     * or with status {@code 400 (Bad Request)} if the jobSeeker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobSeeker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-seekers/{id}")
    public ResponseEntity<JobSeeker> updateJobSeeker(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobSeeker jobSeeker
    ) throws URISyntaxException {
        log.debug("REST request to update JobSeeker : {}, {}", id, jobSeeker);
        if (jobSeeker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobSeeker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobSeekerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobSeeker result = jobSeekerRepository.save(jobSeeker);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobSeeker.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-seekers/:id} : Partial updates given fields of an existing jobSeeker, field will ignore if it is null
     *
     * @param id the id of the jobSeeker to save.
     * @param jobSeeker the jobSeeker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobSeeker,
     * or with status {@code 400 (Bad Request)} if the jobSeeker is not valid,
     * or with status {@code 404 (Not Found)} if the jobSeeker is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobSeeker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-seekers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobSeeker> partialUpdateJobSeeker(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobSeeker jobSeeker
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobSeeker partially : {}, {}", id, jobSeeker);
        if (jobSeeker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobSeeker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobSeekerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobSeeker> result = jobSeekerRepository
            .findById(jobSeeker.getId())
            .map(existingJobSeeker -> {
                if (jobSeeker.getName() != null) {
                    existingJobSeeker.setName(jobSeeker.getName());
                }
                if (jobSeeker.getEmail() != null) {
                    existingJobSeeker.setEmail(jobSeeker.getEmail());
                }
                if (jobSeeker.getPhone() != null) {
                    existingJobSeeker.setPhone(jobSeeker.getPhone());
                }
                if (jobSeeker.getExperience() != null) {
                    existingJobSeeker.setExperience(jobSeeker.getExperience());
                }

                return existingJobSeeker;
            })
            .map(jobSeekerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobSeeker.getId().toString())
        );
    }

    /**
     * {@code GET  /job-seekers} : get all the jobSeekers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobSeekers in body.
     */
    @GetMapping("/job-seekers")
    public List<JobSeeker> getAllJobSeekers() {
        log.debug("REST request to get all JobSeekers");
        return jobSeekerRepository.findAll();
    }

    /**
     * {@code GET  /job-seekers/:id} : get the "id" jobSeeker.
     *
     * @param id the id of the jobSeeker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobSeeker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-seekers/{id}")
    public ResponseEntity<JobSeeker> getJobSeeker(@PathVariable Long id) {
        log.debug("REST request to get JobSeeker : {}", id);
        Optional<JobSeeker> jobSeeker = jobSeekerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jobSeeker);
    }

    /**
     * {@code DELETE  /job-seekers/:id} : delete the "id" jobSeeker.
     *
     * @param id the id of the jobSeeker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-seekers/{id}")
    public ResponseEntity<Void> deleteJobSeeker(@PathVariable Long id) {
        log.debug("REST request to delete JobSeeker : {}", id);
        jobSeekerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
