package com.co.kr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.co.kr.IntegrationTest;
import com.co.kr.domain.Recruit;
import com.co.kr.repository.RecruitRepository;
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
 * Integration tests for the {@link RecruitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecruitResourceIT {

    private static final String DEFAULT_RECRUIT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_RECRUIT_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recruits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecruitMockMvc;

    private Recruit recruit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recruit createEntity(EntityManager em) {
        Recruit recruit = new Recruit().recruitText(DEFAULT_RECRUIT_TEXT);
        return recruit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recruit createUpdatedEntity(EntityManager em) {
        Recruit recruit = new Recruit().recruitText(UPDATED_RECRUIT_TEXT);
        return recruit;
    }

    @BeforeEach
    public void initTest() {
        recruit = createEntity(em);
    }

    @Test
    @Transactional
    void createRecruit() throws Exception {
        int databaseSizeBeforeCreate = recruitRepository.findAll().size();
        // Create the Recruit
        restRecruitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruit)))
            .andExpect(status().isCreated());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeCreate + 1);
        Recruit testRecruit = recruitList.get(recruitList.size() - 1);
        assertThat(testRecruit.getRecruitText()).isEqualTo(DEFAULT_RECRUIT_TEXT);
    }

    @Test
    @Transactional
    void createRecruitWithExistingId() throws Exception {
        // Create the Recruit with an existing ID
        recruit.setId(1L);

        int databaseSizeBeforeCreate = recruitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecruitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruit)))
            .andExpect(status().isBadRequest());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRecruits() throws Exception {
        // Initialize the database
        recruitRepository.saveAndFlush(recruit);

        // Get all the recruitList
        restRecruitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recruit.getId().intValue())))
            .andExpect(jsonPath("$.[*].recruitText").value(hasItem(DEFAULT_RECRUIT_TEXT)));
    }

    @Test
    @Transactional
    void getRecruit() throws Exception {
        // Initialize the database
        recruitRepository.saveAndFlush(recruit);

        // Get the recruit
        restRecruitMockMvc
            .perform(get(ENTITY_API_URL_ID, recruit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recruit.getId().intValue()))
            .andExpect(jsonPath("$.recruitText").value(DEFAULT_RECRUIT_TEXT));
    }

    @Test
    @Transactional
    void getNonExistingRecruit() throws Exception {
        // Get the recruit
        restRecruitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecruit() throws Exception {
        // Initialize the database
        recruitRepository.saveAndFlush(recruit);

        int databaseSizeBeforeUpdate = recruitRepository.findAll().size();

        // Update the recruit
        Recruit updatedRecruit = recruitRepository.findById(recruit.getId()).get();
        // Disconnect from session so that the updates on updatedRecruit are not directly saved in db
        em.detach(updatedRecruit);
        updatedRecruit.recruitText(UPDATED_RECRUIT_TEXT);

        restRecruitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecruit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRecruit))
            )
            .andExpect(status().isOk());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeUpdate);
        Recruit testRecruit = recruitList.get(recruitList.size() - 1);
        assertThat(testRecruit.getRecruitText()).isEqualTo(UPDATED_RECRUIT_TEXT);
    }

    @Test
    @Transactional
    void putNonExistingRecruit() throws Exception {
        int databaseSizeBeforeUpdate = recruitRepository.findAll().size();
        recruit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecruitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recruit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recruit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecruit() throws Exception {
        int databaseSizeBeforeUpdate = recruitRepository.findAll().size();
        recruit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recruit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecruit() throws Exception {
        int databaseSizeBeforeUpdate = recruitRepository.findAll().size();
        recruit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecruitWithPatch() throws Exception {
        // Initialize the database
        recruitRepository.saveAndFlush(recruit);

        int databaseSizeBeforeUpdate = recruitRepository.findAll().size();

        // Update the recruit using partial update
        Recruit partialUpdatedRecruit = new Recruit();
        partialUpdatedRecruit.setId(recruit.getId());

        partialUpdatedRecruit.recruitText(UPDATED_RECRUIT_TEXT);

        restRecruitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecruit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecruit))
            )
            .andExpect(status().isOk());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeUpdate);
        Recruit testRecruit = recruitList.get(recruitList.size() - 1);
        assertThat(testRecruit.getRecruitText()).isEqualTo(UPDATED_RECRUIT_TEXT);
    }

    @Test
    @Transactional
    void fullUpdateRecruitWithPatch() throws Exception {
        // Initialize the database
        recruitRepository.saveAndFlush(recruit);

        int databaseSizeBeforeUpdate = recruitRepository.findAll().size();

        // Update the recruit using partial update
        Recruit partialUpdatedRecruit = new Recruit();
        partialUpdatedRecruit.setId(recruit.getId());

        partialUpdatedRecruit.recruitText(UPDATED_RECRUIT_TEXT);

        restRecruitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecruit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecruit))
            )
            .andExpect(status().isOk());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeUpdate);
        Recruit testRecruit = recruitList.get(recruitList.size() - 1);
        assertThat(testRecruit.getRecruitText()).isEqualTo(UPDATED_RECRUIT_TEXT);
    }

    @Test
    @Transactional
    void patchNonExistingRecruit() throws Exception {
        int databaseSizeBeforeUpdate = recruitRepository.findAll().size();
        recruit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecruitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recruit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recruit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecruit() throws Exception {
        int databaseSizeBeforeUpdate = recruitRepository.findAll().size();
        recruit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recruit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecruit() throws Exception {
        int databaseSizeBeforeUpdate = recruitRepository.findAll().size();
        recruit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(recruit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recruit in the database
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecruit() throws Exception {
        // Initialize the database
        recruitRepository.saveAndFlush(recruit);

        int databaseSizeBeforeDelete = recruitRepository.findAll().size();

        // Delete the recruit
        restRecruitMockMvc
            .perform(delete(ENTITY_API_URL_ID, recruit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recruit> recruitList = recruitRepository.findAll();
        assertThat(recruitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
