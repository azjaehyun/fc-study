package com.job.web.rest;

import com.job.domain.Applicant;
import com.job.repository.ApplicantRepository;
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
 * REST controller for managing {@link com.job.domain.Applicant}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ApplicantResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantResource.class);

    private static final String ENTITY_NAME = "applicant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantRepository applicantRepository;

    public ApplicantResource(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    /**
     * {@code POST  /applicants} : Create a new applicant.
     *
     * @param applicant the applicant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicant, or with status {@code 400 (Bad Request)} if the applicant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicants")
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) throws URISyntaxException {
        log.debug("REST request to save Applicant : {}", applicant);
        if (applicant.getApplicantId() != null) {
            throw new BadRequestAlertException("A new applicant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Applicant result = applicantRepository.save(applicant);
        return ResponseEntity
            .created(new URI("/api/applicants/" + result.getApplicantId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getApplicantId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicants/:applicantId} : Updates an existing applicant.
     *
     * @param applicantId the id of the applicant to save.
     * @param applicant the applicant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicant,
     * or with status {@code 400 (Bad Request)} if the applicant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicants/{applicantId}")
    public ResponseEntity<Applicant> updateApplicant(
        @PathVariable(value = "applicantId", required = false) final Long applicantId,
        @RequestBody Applicant applicant
    ) throws URISyntaxException {
        log.debug("REST request to update Applicant : {}, {}", applicantId, applicant);
        if (applicant.getApplicantId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(applicantId, applicant.getApplicantId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantRepository.existsById(applicantId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Applicant result = applicantRepository.save(applicant);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicant.getApplicantId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applicants/:applicantId} : Partial updates given fields of an existing applicant, field will ignore if it is null
     *
     * @param applicantId the id of the applicant to save.
     * @param applicant the applicant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicant,
     * or with status {@code 400 (Bad Request)} if the applicant is not valid,
     * or with status {@code 404 (Not Found)} if the applicant is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applicants/{applicantId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Applicant> partialUpdateApplicant(
        @PathVariable(value = "applicantId", required = false) final Long applicantId,
        @RequestBody Applicant applicant
    ) throws URISyntaxException {
        log.debug("REST request to partial update Applicant partially : {}, {}", applicantId, applicant);
        if (applicant.getApplicantId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(applicantId, applicant.getApplicantId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicantRepository.existsById(applicantId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Applicant> result = applicantRepository
            .findById(applicant.getApplicantId())
            .map(existingApplicant -> {
                if (applicant.getName() != null) {
                    existingApplicant.setName(applicant.getName());
                }
                if (applicant.getEmail() != null) {
                    existingApplicant.setEmail(applicant.getEmail());
                }
                if (applicant.getPhone() != null) {
                    existingApplicant.setPhone(applicant.getPhone());
                }

                return existingApplicant;
            })
            .map(applicantRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicant.getApplicantId().toString())
        );
    }

    /**
     * {@code GET  /applicants} : get all the applicants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicants in body.
     */
    @GetMapping("/applicants")
    public ResponseEntity<List<Applicant>> getAllApplicants(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Applicants");
        Page<Applicant> page = applicantRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicants/:id} : get the "id" applicant.
     *
     * @param id the id of the applicant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicants/{id}")
    public ResponseEntity<Applicant> getApplicant(@PathVariable Long id) {
        log.debug("REST request to get Applicant : {}", id);
        Optional<Applicant> applicant = applicantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(applicant);
    }

    /**
     * {@code DELETE  /applicants/:id} : delete the "id" applicant.
     *
     * @param id the id of the applicant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicants/{id}")
    public ResponseEntity<Void> deleteApplicant(@PathVariable Long id) {
        log.debug("REST request to delete Applicant : {}", id);
        applicantRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
