package com.co.kr.web.rest;

import com.co.kr.domain.Corp;
import com.co.kr.repository.CorpRepository;
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
 * REST controller for managing {@link com.co.kr.domain.Corp}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CorpResource {

    private final Logger log = LoggerFactory.getLogger(CorpResource.class);

    private static final String ENTITY_NAME = "myappCorp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorpRepository corpRepository;

    public CorpResource(CorpRepository corpRepository) {
        this.corpRepository = corpRepository;
    }

    /**
     * {@code POST  /corps} : Create a new corp.
     *
     * @param corp the corp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new corp, or with status {@code 400 (Bad Request)} if the corp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/corps")
    public ResponseEntity<Corp> createCorp(@RequestBody Corp corp) throws URISyntaxException {
        log.debug("REST request to save Corp : {}", corp);
        if (corp.getId() != null) {
            throw new BadRequestAlertException("A new corp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Corp result = corpRepository.save(corp);
        return ResponseEntity
            .created(new URI("/api/corps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /corps/:id} : Updates an existing corp.
     *
     * @param id the id of the corp to save.
     * @param corp the corp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated corp,
     * or with status {@code 400 (Bad Request)} if the corp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the corp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/corps/{id}")
    public ResponseEntity<Corp> updateCorp(@PathVariable(value = "id", required = false) final Long id, @RequestBody Corp corp)
        throws URISyntaxException {
        log.debug("REST request to update Corp : {}, {}", id, corp);
        if (corp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, corp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!corpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Corp result = corpRepository.save(corp);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, corp.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /corps/:id} : Partial updates given fields of an existing corp, field will ignore if it is null
     *
     * @param id the id of the corp to save.
     * @param corp the corp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated corp,
     * or with status {@code 400 (Bad Request)} if the corp is not valid,
     * or with status {@code 404 (Not Found)} if the corp is not found,
     * or with status {@code 500 (Internal Server Error)} if the corp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/corps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Corp> partialUpdateCorp(@PathVariable(value = "id", required = false) final Long id, @RequestBody Corp corp)
        throws URISyntaxException {
        log.debug("REST request to partial update Corp partially : {}, {}", id, corp);
        if (corp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, corp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!corpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Corp> result = corpRepository
            .findById(corp.getId())
            .map(existingCorp -> {
                if (corp.getCorpName() != null) {
                    existingCorp.setCorpName(corp.getCorpName());
                }

                return existingCorp;
            })
            .map(corpRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, corp.getId().toString())
        );
    }

    /**
     * {@code GET  /corps} : get all the corps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of corps in body.
     */
    @GetMapping("/corps")
    public List<Corp> getAllCorps() {
        log.debug("REST request to get all Corps");
        return corpRepository.findAll();
    }

    /**
     * {@code GET  /corps/:id} : get the "id" corp.
     *
     * @param id the id of the corp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the corp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/corps/{id}")
    public ResponseEntity<Corp> getCorp(@PathVariable Long id) {
        log.debug("REST request to get Corp : {}", id);
        Optional<Corp> corp = corpRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(corp);
    }

    /**
     * {@code DELETE  /corps/:id} : delete the "id" corp.
     *
     * @param id the id of the corp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/corps/{id}")
    public ResponseEntity<Void> deleteCorp(@PathVariable Long id) {
        log.debug("REST request to delete Corp : {}", id);
        corpRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
