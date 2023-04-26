package com.co.kr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.co.kr.IntegrationTest;
import com.co.kr.domain.Corp;
import com.co.kr.repository.CorpRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CorpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CorpResourceIT {

    private static final String DEFAULT_CORP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CORP_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/corps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CorpRepository corpRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCorpMockMvc;

    private Corp corp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corp createEntity(EntityManager em) {
        Corp corp = new Corp().corpName(DEFAULT_CORP_NAME);
        return corp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corp createUpdatedEntity(EntityManager em) {
        Corp corp = new Corp().corpName(UPDATED_CORP_NAME);
        return corp;
    }

    @BeforeEach
    public void initTest() {
        corp = createEntity(em);
    }

    @Test
    @Transactional
    void createCorp() throws Exception {
        int databaseSizeBeforeCreate = corpRepository.findAll().size();
        // Create the Corp
        restCorpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(corp)))
            .andExpect(status().isCreated());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeCreate + 1);
        Corp testCorp = corpList.get(corpList.size() - 1);
        assertThat(testCorp.getCorpName()).isEqualTo(DEFAULT_CORP_NAME);
    }

    @Test
    @Transactional
    void createCorpWithExistingId() throws Exception {
        // Create the Corp with an existing ID
        corp.setId(1L);

        int databaseSizeBeforeCreate = corpRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(corp)))
            .andExpect(status().isBadRequest());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCorps() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get all the corpList
        restCorpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corp.getId().intValue())))
            .andExpect(jsonPath("$.[*].corpName").value(hasItem(DEFAULT_CORP_NAME)));
    }

    @Test
    @Transactional
    void getCorp() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        // Get the corp
        restCorpMockMvc
            .perform(get(ENTITY_API_URL_ID, corp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(corp.getId().intValue()))
            .andExpect(jsonPath("$.corpName").value(DEFAULT_CORP_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCorp() throws Exception {
        // Get the corp
        restCorpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCorp() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        int databaseSizeBeforeUpdate = corpRepository.findAll().size();

        // Update the corp
        Corp updatedCorp = corpRepository.findById(corp.getId()).get();
        // Disconnect from session so that the updates on updatedCorp are not directly saved in db
        em.detach(updatedCorp);
        updatedCorp.corpName(UPDATED_CORP_NAME);

        restCorpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCorp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCorp))
            )
            .andExpect(status().isOk());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
        Corp testCorp = corpList.get(corpList.size() - 1);
        assertThat(testCorp.getCorpName()).isEqualTo(UPDATED_CORP_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCorp() throws Exception {
        int databaseSizeBeforeUpdate = corpRepository.findAll().size();
        corp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, corp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(corp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCorp() throws Exception {
        int databaseSizeBeforeUpdate = corpRepository.findAll().size();
        corp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(corp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCorp() throws Exception {
        int databaseSizeBeforeUpdate = corpRepository.findAll().size();
        corp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(corp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCorpWithPatch() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        int databaseSizeBeforeUpdate = corpRepository.findAll().size();

        // Update the corp using partial update
        Corp partialUpdatedCorp = new Corp();
        partialUpdatedCorp.setId(corp.getId());

        restCorpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCorp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCorp))
            )
            .andExpect(status().isOk());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
        Corp testCorp = corpList.get(corpList.size() - 1);
        assertThat(testCorp.getCorpName()).isEqualTo(DEFAULT_CORP_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCorpWithPatch() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        int databaseSizeBeforeUpdate = corpRepository.findAll().size();

        // Update the corp using partial update
        Corp partialUpdatedCorp = new Corp();
        partialUpdatedCorp.setId(corp.getId());

        partialUpdatedCorp.corpName(UPDATED_CORP_NAME);

        restCorpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCorp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCorp))
            )
            .andExpect(status().isOk());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
        Corp testCorp = corpList.get(corpList.size() - 1);
        assertThat(testCorp.getCorpName()).isEqualTo(UPDATED_CORP_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCorp() throws Exception {
        int databaseSizeBeforeUpdate = corpRepository.findAll().size();
        corp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, corp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(corp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCorp() throws Exception {
        int databaseSizeBeforeUpdate = corpRepository.findAll().size();
        corp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(corp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCorp() throws Exception {
        int databaseSizeBeforeUpdate = corpRepository.findAll().size();
        corp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(corp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Corp in the database
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCorp() throws Exception {
        // Initialize the database
        corpRepository.saveAndFlush(corp);

        int databaseSizeBeforeDelete = corpRepository.findAll().size();

        // Delete the corp
        restCorpMockMvc
            .perform(delete(ENTITY_API_URL_ID, corp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Corp> corpList = corpRepository.findAll();
        assertThat(corpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
