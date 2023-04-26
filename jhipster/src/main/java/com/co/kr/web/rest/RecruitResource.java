package com.co.kr.web.rest;

import com.co.kr.domain.Recruit;
import com.co.kr.repository.RecruitRepository;
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
 * REST controller for managing {@link com.co.kr.domain.Recruit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RecruitResource {

    private final Logger log = LoggerFactory.getLogger(RecruitResource.class);

    private static final String ENTITY_NAME = "myappRecruit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecruitRepository recruitRepository;

    public RecruitResource(RecruitRepository recruitRepository) {
        this.recruitRepository = recruitRepository;
    }

    /**
     * {@code POST  /recruits} : Create a new recruit.
     *
     * @param recruit the recruit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recruit, or with status {@code 400 (Bad Request)} if the recruit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recruits")
    public ResponseEntity<Recruit> createRecruit(@RequestBody Recruit recruit) throws URISyntaxException {
        log.debug("REST request to save Recruit : {}", recruit);
        if (recruit.getId() != null) {
            throw new BadRequestAlertException("A new recruit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recruit result = recruitRepository.save(recruit);
        return ResponseEntity
            .created(new URI("/api/recruits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recruits/:id} : Updates an existing recruit.
     *
     * @param id the id of the recruit to save.
     * @param recruit the recruit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruit,
     * or with status {@code 400 (Bad Request)} if the recruit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recruit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recruits/{id}")
    public ResponseEntity<Recruit> updateRecruit(@PathVariable(value = "id", required = false) final Long id, @RequestBody Recruit recruit)
        throws URISyntaxException {
        log.debug("REST request to update Recruit : {}, {}", id, recruit);
        if (recruit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Recruit result = recruitRepository.save(recruit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recruits/:id} : Partial updates given fields of an existing recruit, field will ignore if it is null
     *
     * @param id the id of the recruit to save.
     * @param recruit the recruit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruit,
     * or with status {@code 400 (Bad Request)} if the recruit is not valid,
     * or with status {@code 404 (Not Found)} if the recruit is not found,
     * or with status {@code 500 (Internal Server Error)} if the recruit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recruits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Recruit> partialUpdateRecruit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Recruit recruit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Recruit partially : {}, {}", id, recruit);
        if (recruit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Recruit> result = recruitRepository
            .findById(recruit.getId())
            .map(existingRecruit -> {
                if (recruit.getRecruitText() != null) {
                    existingRecruit.setRecruitText(recruit.getRecruitText());
                }

                return existingRecruit;
            })
            .map(recruitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruit.getId().toString())
        );
    }

    /**
     * {@code GET  /recruits} : get all the recruits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recruits in body.
     */
    @GetMapping("/recruits")
    public List<Recruit> getAllRecruits() {
        log.debug("REST request to get all Recruits");
        return recruitRepository.findAll();
    }

    /**
     * {@code GET  /recruits/:id} : get the "id" recruit.
     *
     * @param id the id of the recruit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recruit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recruits/{id}")
    public ResponseEntity<Recruit> getRecruit(@PathVariable Long id) {
        log.debug("REST request to get Recruit : {}", id);
        Optional<Recruit> recruit = recruitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recruit);
    }

    /**
     * {@code DELETE  /recruits/:id} : delete the "id" recruit.
     *
     * @param id the id of the recruit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recruits/{id}")
    public ResponseEntity<Void> deleteRecruit(@PathVariable Long id) {
        log.debug("REST request to delete Recruit : {}", id);
        recruitRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
