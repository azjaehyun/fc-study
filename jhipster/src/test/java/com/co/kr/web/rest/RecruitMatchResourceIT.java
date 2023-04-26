package com.co.kr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.co.kr.IntegrationTest;
import com.co.kr.domain.RecruitMatch;
import com.co.kr.repository.RecruitMatchRepository;
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
 * Integration tests for the {@link RecruitMatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecruitMatchResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_RECURIT_ID = 1L;
    private static final Long UPDATED_RECURIT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/recruit-matches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecruitMatchRepository recruitMatchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecruitMatchMockMvc;

    private RecruitMatch recruitMatch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecruitMatch createEntity(EntityManager em) {
        RecruitMatch recruitMatch = new RecruitMatch().userId(DEFAULT_USER_ID).recuritId(DEFAULT_RECURIT_ID);
        return recruitMatch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecruitMatch createUpdatedEntity(EntityManager em) {
        RecruitMatch recruitMatch = new RecruitMatch().userId(UPDATED_USER_ID).recuritId(UPDATED_RECURIT_ID);
        return recruitMatch;
    }

    @BeforeEach
    public void initTest() {
        recruitMatch = createEntity(em);
    }

    @Test
    @Transactional
    void createRecruitMatch() throws Exception {
        int databaseSizeBeforeCreate = recruitMatchRepository.findAll().size();
        // Create the RecruitMatch
        restRecruitMatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitMatch)))
            .andExpect(status().isCreated());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeCreate + 1);
        RecruitMatch testRecruitMatch = recruitMatchList.get(recruitMatchList.size() - 1);
        assertThat(testRecruitMatch.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testRecruitMatch.getRecuritId()).isEqualTo(DEFAULT_RECURIT_ID);
    }

    @Test
    @Transactional
    void createRecruitMatchWithExistingId() throws Exception {
        // Create the RecruitMatch with an existing ID
        recruitMatch.setId(1L);

        int databaseSizeBeforeCreate = recruitMatchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecruitMatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitMatch)))
            .andExpect(status().isBadRequest());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRecruitMatches() throws Exception {
        // Initialize the database
        recruitMatchRepository.saveAndFlush(recruitMatch);

        // Get all the recruitMatchList
        restRecruitMatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recruitMatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].recuritId").value(hasItem(DEFAULT_RECURIT_ID.intValue())));
    }

    @Test
    @Transactional
    void getRecruitMatch() throws Exception {
        // Initialize the database
        recruitMatchRepository.saveAndFlush(recruitMatch);

        // Get the recruitMatch
        restRecruitMatchMockMvc
            .perform(get(ENTITY_API_URL_ID, recruitMatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recruitMatch.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.recuritId").value(DEFAULT_RECURIT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRecruitMatch() throws Exception {
        // Get the recruitMatch
        restRecruitMatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecruitMatch() throws Exception {
        // Initialize the database
        recruitMatchRepository.saveAndFlush(recruitMatch);

        int databaseSizeBeforeUpdate = recruitMatchRepository.findAll().size();

        // Update the recruitMatch
        RecruitMatch updatedRecruitMatch = recruitMatchRepository.findById(recruitMatch.getId()).get();
        // Disconnect from session so that the updates on updatedRecruitMatch are not directly saved in db
        em.detach(updatedRecruitMatch);
        updatedRecruitMatch.userId(UPDATED_USER_ID).recuritId(UPDATED_RECURIT_ID);

        restRecruitMatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecruitMatch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRecruitMatch))
            )
            .andExpect(status().isOk());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeUpdate);
        RecruitMatch testRecruitMatch = recruitMatchList.get(recruitMatchList.size() - 1);
        assertThat(testRecruitMatch.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testRecruitMatch.getRecuritId()).isEqualTo(UPDATED_RECURIT_ID);
    }

    @Test
    @Transactional
    void putNonExistingRecruitMatch() throws Exception {
        int databaseSizeBeforeUpdate = recruitMatchRepository.findAll().size();
        recruitMatch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecruitMatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recruitMatch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recruitMatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecruitMatch() throws Exception {
        int databaseSizeBeforeUpdate = recruitMatchRepository.findAll().size();
        recruitMatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitMatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recruitMatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecruitMatch() throws Exception {
        int databaseSizeBeforeUpdate = recruitMatchRepository.findAll().size();
        recruitMatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitMatchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitMatch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecruitMatchWithPatch() throws Exception {
        // Initialize the database
        recruitMatchRepository.saveAndFlush(recruitMatch);

        int databaseSizeBeforeUpdate = recruitMatchRepository.findAll().size();

        // Update the recruitMatch using partial update
        RecruitMatch partialUpdatedRecruitMatch = new RecruitMatch();
        partialUpdatedRecruitMatch.setId(recruitMatch.getId());

        partialUpdatedRecruitMatch.userId(UPDATED_USER_ID).recuritId(UPDATED_RECURIT_ID);

        restRecruitMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecruitMatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecruitMatch))
            )
            .andExpect(status().isOk());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeUpdate);
        RecruitMatch testRecruitMatch = recruitMatchList.get(recruitMatchList.size() - 1);
        assertThat(testRecruitMatch.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testRecruitMatch.getRecuritId()).isEqualTo(UPDATED_RECURIT_ID);
    }

    @Test
    @Transactional
    void fullUpdateRecruitMatchWithPatch() throws Exception {
        // Initialize the database
        recruitMatchRepository.saveAndFlush(recruitMatch);

        int databaseSizeBeforeUpdate = recruitMatchRepository.findAll().size();

        // Update the recruitMatch using partial update
        RecruitMatch partialUpdatedRecruitMatch = new RecruitMatch();
        partialUpdatedRecruitMatch.setId(recruitMatch.getId());

        partialUpdatedRecruitMatch.userId(UPDATED_USER_ID).recuritId(UPDATED_RECURIT_ID);

        restRecruitMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecruitMatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecruitMatch))
            )
            .andExpect(status().isOk());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeUpdate);
        RecruitMatch testRecruitMatch = recruitMatchList.get(recruitMatchList.size() - 1);
        assertThat(testRecruitMatch.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testRecruitMatch.getRecuritId()).isEqualTo(UPDATED_RECURIT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingRecruitMatch() throws Exception {
        int databaseSizeBeforeUpdate = recruitMatchRepository.findAll().size();
        recruitMatch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecruitMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recruitMatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recruitMatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecruitMatch() throws Exception {
        int databaseSizeBeforeUpdate = recruitMatchRepository.findAll().size();
        recruitMatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recruitMatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecruitMatch() throws Exception {
        int databaseSizeBeforeUpdate = recruitMatchRepository.findAll().size();
        recruitMatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitMatchMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(recruitMatch))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecruitMatch in the database
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecruitMatch() throws Exception {
        // Initialize the database
        recruitMatchRepository.saveAndFlush(recruitMatch);

        int databaseSizeBeforeDelete = recruitMatchRepository.findAll().size();

        // Delete the recruitMatch
        restRecruitMatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, recruitMatch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecruitMatch> recruitMatchList = recruitMatchRepository.findAll();
        assertThat(recruitMatchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
