package com.co.kr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.co.kr.IntegrationTest;
import com.co.kr.domain.JobUser;
import com.co.kr.repository.JobUserRepository;
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
 * Integration tests for the {@link JobUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobUserResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String ENTITY_API_URL = "/api/job-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobUserRepository jobUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobUserMockMvc;

    private JobUser jobUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobUser createEntity(EntityManager em) {
        JobUser jobUser = new JobUser().userName(DEFAULT_USER_NAME).age(DEFAULT_AGE);
        return jobUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobUser createUpdatedEntity(EntityManager em) {
        JobUser jobUser = new JobUser().userName(UPDATED_USER_NAME).age(UPDATED_AGE);
        return jobUser;
    }

    @BeforeEach
    public void initTest() {
        jobUser = createEntity(em);
    }

    @Test
    @Transactional
    void createJobUser() throws Exception {
        int databaseSizeBeforeCreate = jobUserRepository.findAll().size();
        // Create the JobUser
        restJobUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobUser)))
            .andExpect(status().isCreated());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeCreate + 1);
        JobUser testJobUser = jobUserList.get(jobUserList.size() - 1);
        assertThat(testJobUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testJobUser.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    @Transactional
    void createJobUserWithExistingId() throws Exception {
        // Create the JobUser with an existing ID
        jobUser.setId(1L);

        int databaseSizeBeforeCreate = jobUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobUser)))
            .andExpect(status().isBadRequest());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobUsers() throws Exception {
        // Initialize the database
        jobUserRepository.saveAndFlush(jobUser);

        // Get all the jobUserList
        restJobUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)));
    }

    @Test
    @Transactional
    void getJobUser() throws Exception {
        // Initialize the database
        jobUserRepository.saveAndFlush(jobUser);

        // Get the jobUser
        restJobUserMockMvc
            .perform(get(ENTITY_API_URL_ID, jobUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobUser.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE));
    }

    @Test
    @Transactional
    void getNonExistingJobUser() throws Exception {
        // Get the jobUser
        restJobUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobUser() throws Exception {
        // Initialize the database
        jobUserRepository.saveAndFlush(jobUser);

        int databaseSizeBeforeUpdate = jobUserRepository.findAll().size();

        // Update the jobUser
        JobUser updatedJobUser = jobUserRepository.findById(jobUser.getId()).get();
        // Disconnect from session so that the updates on updatedJobUser are not directly saved in db
        em.detach(updatedJobUser);
        updatedJobUser.userName(UPDATED_USER_NAME).age(UPDATED_AGE);

        restJobUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobUser))
            )
            .andExpect(status().isOk());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeUpdate);
        JobUser testJobUser = jobUserList.get(jobUserList.size() - 1);
        assertThat(testJobUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testJobUser.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    void putNonExistingJobUser() throws Exception {
        int databaseSizeBeforeUpdate = jobUserRepository.findAll().size();
        jobUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobUser() throws Exception {
        int databaseSizeBeforeUpdate = jobUserRepository.findAll().size();
        jobUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobUser() throws Exception {
        int databaseSizeBeforeUpdate = jobUserRepository.findAll().size();
        jobUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobUserWithPatch() throws Exception {
        // Initialize the database
        jobUserRepository.saveAndFlush(jobUser);

        int databaseSizeBeforeUpdate = jobUserRepository.findAll().size();

        // Update the jobUser using partial update
        JobUser partialUpdatedJobUser = new JobUser();
        partialUpdatedJobUser.setId(jobUser.getId());

        partialUpdatedJobUser.userName(UPDATED_USER_NAME);

        restJobUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobUser))
            )
            .andExpect(status().isOk());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeUpdate);
        JobUser testJobUser = jobUserList.get(jobUserList.size() - 1);
        assertThat(testJobUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testJobUser.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    @Transactional
    void fullUpdateJobUserWithPatch() throws Exception {
        // Initialize the database
        jobUserRepository.saveAndFlush(jobUser);

        int databaseSizeBeforeUpdate = jobUserRepository.findAll().size();

        // Update the jobUser using partial update
        JobUser partialUpdatedJobUser = new JobUser();
        partialUpdatedJobUser.setId(jobUser.getId());

        partialUpdatedJobUser.userName(UPDATED_USER_NAME).age(UPDATED_AGE);

        restJobUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobUser))
            )
            .andExpect(status().isOk());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeUpdate);
        JobUser testJobUser = jobUserList.get(jobUserList.size() - 1);
        assertThat(testJobUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testJobUser.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    void patchNonExistingJobUser() throws Exception {
        int databaseSizeBeforeUpdate = jobUserRepository.findAll().size();
        jobUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobUser() throws Exception {
        int databaseSizeBeforeUpdate = jobUserRepository.findAll().size();
        jobUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobUser() throws Exception {
        int databaseSizeBeforeUpdate = jobUserRepository.findAll().size();
        jobUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobUser in the database
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobUser() throws Exception {
        // Initialize the database
        jobUserRepository.saveAndFlush(jobUser);

        int databaseSizeBeforeDelete = jobUserRepository.findAll().size();

        // Delete the jobUser
        restJobUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobUser> jobUserList = jobUserRepository.findAll();
        assertThat(jobUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
