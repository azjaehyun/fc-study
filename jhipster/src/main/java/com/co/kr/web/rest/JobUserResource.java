package com.co.kr.web.rest;

import com.co.kr.domain.JobUser;
import com.co.kr.repository.JobUserRepository;
import com.co.kr.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.co.kr.domain.JobUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JobUserResource {

    private final Logger log = LoggerFactory.getLogger(JobUserResource.class);

    private static final String ENTITY_NAME = "myappJobUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobUserRepository jobUserRepository;

    public JobUserResource(JobUserRepository jobUserRepository) {
        this.jobUserRepository = jobUserRepository;
    }

    /**
     * {@code POST  /job-users} : Create a new jobUser.
     *
     * @param jobUser the jobUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobUser, or with status {@code 400 (Bad Request)} if the jobUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-users")
    public ResponseEntity<JobUser> createJobUser(@RequestBody JobUser jobUser) throws URISyntaxException {
        log.debug("REST request to save JobUser : {}", jobUser);
        if (jobUser.getId() != null) {
            throw new BadRequestAlertException("A new jobUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobUser result = jobUserRepository.save(jobUser);
        return ResponseEntity
            .created(new URI("/api/job-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-users/:id} : Updates an existing jobUser.
     *
     * @param id the id of the jobUser to save.
     * @param jobUser the jobUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobUser,
     * or with status {@code 400 (Bad Request)} if the jobUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-users/{id}")
    public ResponseEntity<JobUser> updateJobUser(@PathVariable(value = "id", required = false) final Long id, @RequestBody JobUser jobUser)
        throws URISyntaxException {
        log.debug("REST request to update JobUser : {}, {}", id, jobUser);
        if (jobUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobUser result = jobUserRepository.save(jobUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-users/:id} : Partial updates given fields of an existing jobUser, field will ignore if it is null
     *
     * @param id the id of the jobUser to save.
     * @param jobUser the jobUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobUser,
     * or with status {@code 400 (Bad Request)} if the jobUser is not valid,
     * or with status {@code 404 (Not Found)} if the jobUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobUser> partialUpdateJobUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobUser jobUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobUser partially : {}, {}", id, jobUser);
        if (jobUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobUser> result = jobUserRepository
            .findById(jobUser.getId())
            .map(existingJobUser -> {
                if (jobUser.getUserName() != null) {
                    existingJobUser.setUserName(jobUser.getUserName());
                }
                if (jobUser.getAge() != null) {
                    existingJobUser.setAge(jobUser.getAge());
                }

                return existingJobUser;
            })
            .map(jobUserRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobUser.getId().toString())
        );
    }

    /**
     * {@code GET  /job-users} : get all the jobUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobUsers in body.
     */
    @GetMapping("/job-users")
    public List<JobUser> getAllJobUsers() {
        log.debug("REST request to get all JobUsers");
        return jobUserRepository.findAll();
    }

    /**
     * {@code GET  /job-users/:id} : get the "id" jobUser.
     *
     * @param id the id of the jobUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-users/{id}")
    public ResponseEntity<JobUser> getJobUser(@PathVariable Long id) {
        log.debug("REST request to get JobUser : {}", id);
        Optional<JobUser> jobUser = jobUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jobUser);
    }

    /**
     * {@code DELETE  /job-users/:id} : delete the "id" jobUser.
     *
     * @param id the id of the jobUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-users/{id}")
    public ResponseEntity<Void> deleteJobUser(@PathVariable Long id) {
        log.debug("REST request to delete JobUser : {}", id);
        jobUserRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
