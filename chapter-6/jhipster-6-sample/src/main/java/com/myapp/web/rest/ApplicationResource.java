package com.myapp.web.rest;

import com.myapp.domain.Application;
import com.myapp.repository.ApplicationRepository;
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
 * REST controller for managing {@link com.myapp.domain.Application}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);

    private static final String ENTITY_NAME = "application";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationRepository applicationRepository;

    public ApplicationResource(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    /**
     * {@code POST  /applications} : Create a new application.
     *
     * @param application the application to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new application, or with status {@code 400 (Bad Request)} if the application has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applications")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) throws URISyntaxException {
        log.debug("REST request to save Application : {}", application);
        if (application.getApplicationId() != null) {
            throw new BadRequestAlertException("A new application cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Application result = applicationRepository.save(application);
        return ResponseEntity
            .created(new URI("/api/applications/" + result.getApplicationId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getApplicationId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applications/:applicationId} : Updates an existing application.
     *
     * @param applicationId the id of the application to save.
     * @param application the application to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated application,
     * or with status {@code 400 (Bad Request)} if the application is not valid,
     * or with status {@code 500 (Internal Server Error)} if the application couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applications/{applicationId}")
    public ResponseEntity<Application> updateApplication(
        @PathVariable(value = "applicationId", required = false) final Long applicationId,
        @RequestBody Application application
    ) throws URISyntaxException {
        log.debug("REST request to update Application : {}, {}", applicationId, application);
        if (application.getApplicationId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(applicationId, application.getApplicationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationRepository.existsById(applicationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Application result = applicationRepository.save(application);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, application.getApplicationId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /applications/:applicationId} : Partial updates given fields of an existing application, field will ignore if it is null
     *
     * @param applicationId the id of the application to save.
     * @param application the application to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated application,
     * or with status {@code 400 (Bad Request)} if the application is not valid,
     * or with status {@code 404 (Not Found)} if the application is not found,
     * or with status {@code 500 (Internal Server Error)} if the application couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/applications/{applicationId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Application> partialUpdateApplication(
        @PathVariable(value = "applicationId", required = false) final Long applicationId,
        @RequestBody Application application
    ) throws URISyntaxException {
        log.debug("REST request to partial update Application partially : {}, {}", applicationId, application);
        if (application.getApplicationId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(applicationId, application.getApplicationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationRepository.existsById(applicationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Application> result = applicationRepository
            .findById(application.getApplicationId())
            .map(existingApplication -> {
                if (application.getApplicationDate() != null) {
                    existingApplication.setApplicationDate(application.getApplicationDate());
                }

                return existingApplication;
            })
            .map(applicationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, application.getApplicationId().toString())
        );
    }

    /**
     * {@code GET  /applications} : get all the applications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applications in body.
     */
    @GetMapping("/applications")
    public List<Application> getAllApplications() {
        log.debug("REST request to get all Applications");
        return applicationRepository.findAll();
    }

    /**
     * {@code GET  /applications/:id} : get the "id" application.
     *
     * @param id the id of the application to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the application, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applications/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable Long id) {
        log.debug("REST request to get Application : {}", id);
        Optional<Application> application = applicationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(application);
    }

    /**
     * {@code DELETE  /applications/:id} : delete the "id" application.
     *
     * @param id the id of the application to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete Application : {}", id);
        applicationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
