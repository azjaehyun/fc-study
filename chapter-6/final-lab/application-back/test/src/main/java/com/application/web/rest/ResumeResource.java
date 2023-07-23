package com.application.web.rest;

import com.application.domain.Resume;
import com.application.repository.ResumeRepository;
import com.application.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.application.domain.Resume}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResumeResource {

    private final Logger log = LoggerFactory.getLogger(ResumeResource.class);

    private static final String ENTITY_NAME = "resume";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumeRepository resumeRepository;

    public ResumeResource(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    /**
     * {@code POST  /resumes} : Create a new resume.
     *
     * @param resume the resume to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resume, or with status {@code 400 (Bad Request)} if the resume has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resumes")
    public ResponseEntity<Resume> createResume(@RequestBody Resume resume) throws URISyntaxException {
        log.debug("REST request to save Resume : {}", resume);
        if (resume.getResumeId() != null) {
            throw new BadRequestAlertException("A new resume cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resume result = resumeRepository.save(resume);
        return ResponseEntity
            .created(new URI("/api/resumes/" + result.getResumeId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getResumeId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resumes/:resumeId} : Updates an existing resume.
     *
     * @param resumeId the id of the resume to save.
     * @param resume the resume to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resume,
     * or with status {@code 400 (Bad Request)} if the resume is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resume couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resumes/{resumeId}")
    public ResponseEntity<Resume> updateResume(
        @PathVariable(value = "resumeId", required = false) final Long resumeId,
        @RequestBody Resume resume
    ) throws URISyntaxException {
        log.debug("REST request to update Resume : {}, {}", resumeId, resume);
        if (resume.getResumeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(resumeId, resume.getResumeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeRepository.existsById(resumeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Resume result = resumeRepository.save(resume);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resume.getResumeId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resumes/:resumeId} : Partial updates given fields of an existing resume, field will ignore if it is null
     *
     * @param resumeId the id of the resume to save.
     * @param resume the resume to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resume,
     * or with status {@code 400 (Bad Request)} if the resume is not valid,
     * or with status {@code 404 (Not Found)} if the resume is not found,
     * or with status {@code 500 (Internal Server Error)} if the resume couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resumes/{resumeId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Resume> partialUpdateResume(
        @PathVariable(value = "resumeId", required = false) final Long resumeId,
        @RequestBody Resume resume
    ) throws URISyntaxException {
        log.debug("REST request to partial update Resume partially : {}, {}", resumeId, resume);
        if (resume.getResumeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(resumeId, resume.getResumeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumeRepository.existsById(resumeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Resume> result = resumeRepository
            .findById(resume.getResumeId())
            .map(existingResume -> {
                if (resume.getTitle() != null) {
                    existingResume.setTitle(resume.getTitle());
                }
                if (resume.getContent() != null) {
                    existingResume.setContent(resume.getContent());
                }
                if (resume.getSubmittedDate() != null) {
                    existingResume.setSubmittedDate(resume.getSubmittedDate());
                }

                return existingResume;
            })
            .map(resumeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resume.getResumeId().toString())
        );
    }

    /**
     * {@code GET  /resumes} : get all the resumes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumes in body.
     */
    @GetMapping("/resumes")
    public ResponseEntity<List<Resume>> getAllResumes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Resumes");
        Page<Resume> page = resumeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resumes/:id} : get the "id" resume.
     *
     * @param id the id of the resume to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resume, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resumes/{id}")
    public ResponseEntity<Resume> getResume(@PathVariable Long id) {
        log.debug("REST request to get Resume : {}", id);
        Optional<Resume> resume = resumeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resume);
    }

    /**
     * {@code DELETE  /resumes/:id} : delete the "id" resume.
     *
     * @param id the id of the resume to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {
        log.debug("REST request to delete Resume : {}", id);
        resumeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
