package com.job.web.rest;

import com.job.domain.JobPosting;
import com.job.repository.JobPostingRepository;
import com.job.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.job.domain.JobPosting}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JobPostingResource {

    private final Logger log = LoggerFactory.getLogger(JobPostingResource.class);

    private static final String ENTITY_NAME = "jobPosting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobPostingRepository jobPostingRepository;

    public JobPostingResource(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }

    /**
     * {@code POST  /job-postings} : Create a new jobPosting.
     *
     * @param jobPosting the jobPosting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobPosting, or with status {@code 400 (Bad Request)} if the jobPosting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-postings")
    public ResponseEntity<JobPosting> createJobPosting(@RequestBody JobPosting jobPosting) throws URISyntaxException {
        log.debug("REST request to save JobPosting : {}", jobPosting);
        if (jobPosting.getJobId() != null) {
            throw new BadRequestAlertException("A new jobPosting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobPosting result = jobPostingRepository.save(jobPosting);
        return ResponseEntity
            .created(new URI("/api/job-postings/" + result.getJobId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getJobId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-postings/:jobId} : Updates an existing jobPosting.
     *
     * @param jobId the id of the jobPosting to save.
     * @param jobPosting the jobPosting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobPosting,
     * or with status {@code 400 (Bad Request)} if the jobPosting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobPosting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-postings/{jobId}")
    public ResponseEntity<JobPosting> updateJobPosting(
        @PathVariable(value = "jobId", required = false) final Long jobId,
        @RequestBody JobPosting jobPosting
    ) throws URISyntaxException {
        log.debug("REST request to update JobPosting : {}, {}", jobId, jobPosting);
        if (jobPosting.getJobId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(jobId, jobPosting.getJobId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobPostingRepository.existsById(jobId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobPosting result = jobPostingRepository.save(jobPosting);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobPosting.getJobId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-postings/:jobId} : Partial updates given fields of an existing jobPosting, field will ignore if it is null
     *
     * @param jobId the id of the jobPosting to save.
     * @param jobPosting the jobPosting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobPosting,
     * or with status {@code 400 (Bad Request)} if the jobPosting is not valid,
     * or with status {@code 404 (Not Found)} if the jobPosting is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobPosting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-postings/{jobId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobPosting> partialUpdateJobPosting(
        @PathVariable(value = "jobId", required = false) final Long jobId,
        @RequestBody JobPosting jobPosting
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobPosting partially : {}, {}", jobId, jobPosting);
        if (jobPosting.getJobId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(jobId, jobPosting.getJobId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobPostingRepository.existsById(jobId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobPosting> result = jobPostingRepository
            .findById(jobPosting.getJobId())
            .map(existingJobPosting -> {
                if (jobPosting.getTitle() != null) {
                    existingJobPosting.setTitle(jobPosting.getTitle());
                }
                if (jobPosting.getDescription() != null) {
                    existingJobPosting.setDescription(jobPosting.getDescription());
                }
                if (jobPosting.getLocation() != null) {
                    existingJobPosting.setLocation(jobPosting.getLocation());
                }
                if (jobPosting.getPostedDate() != null) {
                    existingJobPosting.setPostedDate(jobPosting.getPostedDate());
                }

                return existingJobPosting;
            })
            .map(jobPostingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobPosting.getJobId().toString())
        );
    }

    /**
     * {@code GET  /job-postings} : get all the jobPostings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobPostings in body.
     */
    @GetMapping("/job-postings")
    public ResponseEntity<List<JobPosting>> getAllJobPostings(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JobPostings");
        Page<JobPosting> page = jobPostingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-postings/:id} : get the "id" jobPosting.
     *
     * @param id the id of the jobPosting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobPosting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-postings/{id}")
    public ResponseEntity<JobPosting> getJobPosting(@PathVariable Long id) {
        log.debug("REST request to get JobPosting : {}", id);
        Optional<JobPosting> jobPosting = jobPostingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jobPosting);
    }

    /**
     * {@code DELETE  /job-postings/:id} : delete the "id" jobPosting.
     *
     * @param id the id of the jobPosting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-postings/{id}")
    public ResponseEntity<Void> deleteJobPosting(@PathVariable Long id) {
        log.debug("REST request to delete JobPosting : {}", id);
        jobPostingRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
