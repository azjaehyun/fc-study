package com.co.kr.web.rest;

import com.co.kr.domain.RecruitMatch;
import com.co.kr.repository.RecruitMatchRepository;
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
 * REST controller for managing {@link com.co.kr.domain.RecruitMatch}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RecruitMatchResource {

    private final Logger log = LoggerFactory.getLogger(RecruitMatchResource.class);

    private static final String ENTITY_NAME = "myappRecruitMatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecruitMatchRepository recruitMatchRepository;

    public RecruitMatchResource(RecruitMatchRepository recruitMatchRepository) {
        this.recruitMatchRepository = recruitMatchRepository;
    }

    /**
     * {@code POST  /recruit-matches} : Create a new recruitMatch.
     *
     * @param recruitMatch the recruitMatch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recruitMatch, or with status {@code 400 (Bad Request)} if the recruitMatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recruit-matches")
    public ResponseEntity<RecruitMatch> createRecruitMatch(@RequestBody RecruitMatch recruitMatch) throws URISyntaxException {
        log.debug("REST request to save RecruitMatch : {}", recruitMatch);
        if (recruitMatch.getId() != null) {
            throw new BadRequestAlertException("A new recruitMatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecruitMatch result = recruitMatchRepository.save(recruitMatch);
        return ResponseEntity
            .created(new URI("/api/recruit-matches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recruit-matches/:id} : Updates an existing recruitMatch.
     *
     * @param id the id of the recruitMatch to save.
     * @param recruitMatch the recruitMatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruitMatch,
     * or with status {@code 400 (Bad Request)} if the recruitMatch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recruitMatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recruit-matches/{id}")
    public ResponseEntity<RecruitMatch> updateRecruitMatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecruitMatch recruitMatch
    ) throws URISyntaxException {
        log.debug("REST request to update RecruitMatch : {}, {}", id, recruitMatch);
        if (recruitMatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruitMatch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruitMatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecruitMatch result = recruitMatchRepository.save(recruitMatch);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruitMatch.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recruit-matches/:id} : Partial updates given fields of an existing recruitMatch, field will ignore if it is null
     *
     * @param id the id of the recruitMatch to save.
     * @param recruitMatch the recruitMatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruitMatch,
     * or with status {@code 400 (Bad Request)} if the recruitMatch is not valid,
     * or with status {@code 404 (Not Found)} if the recruitMatch is not found,
     * or with status {@code 500 (Internal Server Error)} if the recruitMatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recruit-matches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecruitMatch> partialUpdateRecruitMatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecruitMatch recruitMatch
    ) throws URISyntaxException {
        log.debug("REST request to partial update RecruitMatch partially : {}, {}", id, recruitMatch);
        if (recruitMatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruitMatch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruitMatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecruitMatch> result = recruitMatchRepository
            .findById(recruitMatch.getId())
            .map(existingRecruitMatch -> {
                if (recruitMatch.getUserId() != null) {
                    existingRecruitMatch.setUserId(recruitMatch.getUserId());
                }
                if (recruitMatch.getRecuritId() != null) {
                    existingRecruitMatch.setRecuritId(recruitMatch.getRecuritId());
                }

                return existingRecruitMatch;
            })
            .map(recruitMatchRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruitMatch.getId().toString())
        );
    }

    /**
     * {@code GET  /recruit-matches} : get all the recruitMatches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recruitMatches in body.
     */
    @GetMapping("/recruit-matches")
    public List<RecruitMatch> getAllRecruitMatches() {
        log.debug("REST request to get all RecruitMatches");
        return recruitMatchRepository.findAll();
    }

    /**
     * {@code GET  /recruit-matches/:id} : get the "id" recruitMatch.
     *
     * @param id the id of the recruitMatch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recruitMatch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recruit-matches/{id}")
    public ResponseEntity<RecruitMatch> getRecruitMatch(@PathVariable Long id) {
        log.debug("REST request to get RecruitMatch : {}", id);
        Optional<RecruitMatch> recruitMatch = recruitMatchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recruitMatch);
    }

    /**
     * {@code DELETE  /recruit-matches/:id} : delete the "id" recruitMatch.
     *
     * @param id the id of the recruitMatch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recruit-matches/{id}")
    public ResponseEntity<Void> deleteRecruitMatch(@PathVariable Long id) {
        log.debug("REST request to delete RecruitMatch : {}", id);
        recruitMatchRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
