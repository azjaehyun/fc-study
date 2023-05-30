package com.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.IntegrationTest;
import com.myapp.domain.JobSeeker;
import com.myapp.repository.JobSeekerRepository;
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
 * Integration tests for the {@link JobSeekerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobSeekerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/job-seekers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobSeekerMockMvc;

    private JobSeeker jobSeeker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobSeeker createEntity(EntityManager em) {
        JobSeeker jobSeeker = new JobSeeker().name(DEFAULT_NAME).email(DEFAULT_EMAIL).phone(DEFAULT_PHONE).experience(DEFAULT_EXPERIENCE);
        return jobSeeker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobSeeker createUpdatedEntity(EntityManager em) {
        JobSeeker jobSeeker = new JobSeeker().name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE).experience(UPDATED_EXPERIENCE);
        return jobSeeker;
    }

    @BeforeEach
    public void initTest() {
        jobSeeker = createEntity(em);
    }

    @Test
    @Transactional
    void createJobSeeker() throws Exception {
        int databaseSizeBeforeCreate = jobSeekerRepository.findAll().size();
        // Create the JobSeeker
        restJobSeekerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobSeeker)))
            .andExpect(status().isCreated());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeCreate + 1);
        JobSeeker testJobSeeker = jobSeekerList.get(jobSeekerList.size() - 1);
        assertThat(testJobSeeker.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobSeeker.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testJobSeeker.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testJobSeeker.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
    }

    @Test
    @Transactional
    void createJobSeekerWithExistingId() throws Exception {
        // Create the JobSeeker with an existing ID
        jobSeeker.setId(1L);

        int databaseSizeBeforeCreate = jobSeekerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobSeekerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobSeeker)))
            .andExpect(status().isBadRequest());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobSeekers() throws Exception {
        // Initialize the database
        jobSeekerRepository.saveAndFlush(jobSeeker);

        // Get all the jobSeekerList
        restJobSeekerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobSeeker.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)));
    }

    @Test
    @Transactional
    void getJobSeeker() throws Exception {
        // Initialize the database
        jobSeekerRepository.saveAndFlush(jobSeeker);

        // Get the jobSeeker
        restJobSeekerMockMvc
            .perform(get(ENTITY_API_URL_ID, jobSeeker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobSeeker.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE));
    }

    @Test
    @Transactional
    void getNonExistingJobSeeker() throws Exception {
        // Get the jobSeeker
        restJobSeekerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobSeeker() throws Exception {
        // Initialize the database
        jobSeekerRepository.saveAndFlush(jobSeeker);

        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();

        // Update the jobSeeker
        JobSeeker updatedJobSeeker = jobSeekerRepository.findById(jobSeeker.getId()).get();
        // Disconnect from session so that the updates on updatedJobSeeker are not directly saved in db
        em.detach(updatedJobSeeker);
        updatedJobSeeker.name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE).experience(UPDATED_EXPERIENCE);

        restJobSeekerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobSeeker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobSeeker))
            )
            .andExpect(status().isOk());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
        JobSeeker testJobSeeker = jobSeekerList.get(jobSeekerList.size() - 1);
        assertThat(testJobSeeker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobSeeker.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testJobSeeker.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testJobSeeker.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
    }

    @Test
    @Transactional
    void putNonExistingJobSeeker() throws Exception {
        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();
        jobSeeker.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobSeekerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobSeeker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobSeeker))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobSeeker() throws Exception {
        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();
        jobSeeker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobSeekerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobSeeker))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobSeeker() throws Exception {
        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();
        jobSeeker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobSeekerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobSeeker)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobSeekerWithPatch() throws Exception {
        // Initialize the database
        jobSeekerRepository.saveAndFlush(jobSeeker);

        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();

        // Update the jobSeeker using partial update
        JobSeeker partialUpdatedJobSeeker = new JobSeeker();
        partialUpdatedJobSeeker.setId(jobSeeker.getId());

        partialUpdatedJobSeeker.name(UPDATED_NAME).phone(UPDATED_PHONE).experience(UPDATED_EXPERIENCE);

        restJobSeekerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobSeeker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobSeeker))
            )
            .andExpect(status().isOk());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
        JobSeeker testJobSeeker = jobSeekerList.get(jobSeekerList.size() - 1);
        assertThat(testJobSeeker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobSeeker.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testJobSeeker.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testJobSeeker.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
    }

    @Test
    @Transactional
    void fullUpdateJobSeekerWithPatch() throws Exception {
        // Initialize the database
        jobSeekerRepository.saveAndFlush(jobSeeker);

        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();

        // Update the jobSeeker using partial update
        JobSeeker partialUpdatedJobSeeker = new JobSeeker();
        partialUpdatedJobSeeker.setId(jobSeeker.getId());

        partialUpdatedJobSeeker.name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE).experience(UPDATED_EXPERIENCE);

        restJobSeekerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobSeeker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobSeeker))
            )
            .andExpect(status().isOk());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
        JobSeeker testJobSeeker = jobSeekerList.get(jobSeekerList.size() - 1);
        assertThat(testJobSeeker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobSeeker.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testJobSeeker.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testJobSeeker.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
    }

    @Test
    @Transactional
    void patchNonExistingJobSeeker() throws Exception {
        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();
        jobSeeker.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobSeekerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobSeeker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobSeeker))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobSeeker() throws Exception {
        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();
        jobSeeker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobSeekerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobSeeker))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobSeeker() throws Exception {
        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();
        jobSeeker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobSeekerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobSeeker))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobSeeker() throws Exception {
        // Initialize the database
        jobSeekerRepository.saveAndFlush(jobSeeker);

        int databaseSizeBeforeDelete = jobSeekerRepository.findAll().size();

        // Delete the jobSeeker
        restJobSeekerMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobSeeker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
