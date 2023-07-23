package com.job.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.job.IntegrationTest;
import com.job.domain.JobPosting;
import com.job.repository.JobPostingRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link JobPostingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobPostingResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_POSTED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POSTED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/job-postings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{jobId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobPostingMockMvc;

    private JobPosting jobPosting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobPosting createEntity(EntityManager em) {
        JobPosting jobPosting = new JobPosting()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .location(DEFAULT_LOCATION)
            .postedDate(DEFAULT_POSTED_DATE);
        return jobPosting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobPosting createUpdatedEntity(EntityManager em) {
        JobPosting jobPosting = new JobPosting()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .location(UPDATED_LOCATION)
            .postedDate(UPDATED_POSTED_DATE);
        return jobPosting;
    }

    @BeforeEach
    public void initTest() {
        jobPosting = createEntity(em);
    }

    @Test
    @Transactional
    void createJobPosting() throws Exception {
        int databaseSizeBeforeCreate = jobPostingRepository.findAll().size();
        // Create the JobPosting
        restJobPostingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPosting)))
            .andExpect(status().isCreated());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeCreate + 1);
        JobPosting testJobPosting = jobPostingList.get(jobPostingList.size() - 1);
        assertThat(testJobPosting.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJobPosting.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobPosting.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testJobPosting.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
    }

    @Test
    @Transactional
    void createJobPostingWithExistingId() throws Exception {
        // Create the JobPosting with an existing ID
        jobPosting.setJobId(1L);

        int databaseSizeBeforeCreate = jobPostingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobPostingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPosting)))
            .andExpect(status().isBadRequest());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobPostings() throws Exception {
        // Initialize the database
        jobPostingRepository.saveAndFlush(jobPosting);

        // Get all the jobPostingList
        restJobPostingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=jobId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(jobPosting.getJobId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].postedDate").value(hasItem(DEFAULT_POSTED_DATE.toString())));
    }

    @Test
    @Transactional
    void getJobPosting() throws Exception {
        // Initialize the database
        jobPostingRepository.saveAndFlush(jobPosting);

        // Get the jobPosting
        restJobPostingMockMvc
            .perform(get(ENTITY_API_URL_ID, jobPosting.getJobId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.jobId").value(jobPosting.getJobId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.postedDate").value(DEFAULT_POSTED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingJobPosting() throws Exception {
        // Get the jobPosting
        restJobPostingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobPosting() throws Exception {
        // Initialize the database
        jobPostingRepository.saveAndFlush(jobPosting);

        int databaseSizeBeforeUpdate = jobPostingRepository.findAll().size();

        // Update the jobPosting
        JobPosting updatedJobPosting = jobPostingRepository.findById(jobPosting.getJobId()).get();
        // Disconnect from session so that the updates on updatedJobPosting are not directly saved in db
        em.detach(updatedJobPosting);
        updatedJobPosting.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).location(UPDATED_LOCATION).postedDate(UPDATED_POSTED_DATE);

        restJobPostingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobPosting.getJobId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobPosting))
            )
            .andExpect(status().isOk());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeUpdate);
        JobPosting testJobPosting = jobPostingList.get(jobPostingList.size() - 1);
        assertThat(testJobPosting.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJobPosting.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobPosting.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testJobPosting.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingJobPosting() throws Exception {
        int databaseSizeBeforeUpdate = jobPostingRepository.findAll().size();
        jobPosting.setJobId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobPostingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobPosting.getJobId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobPosting))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobPosting() throws Exception {
        int databaseSizeBeforeUpdate = jobPostingRepository.findAll().size();
        jobPosting.setJobId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobPostingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobPosting))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobPosting() throws Exception {
        int databaseSizeBeforeUpdate = jobPostingRepository.findAll().size();
        jobPosting.setJobId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobPostingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPosting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobPostingWithPatch() throws Exception {
        // Initialize the database
        jobPostingRepository.saveAndFlush(jobPosting);

        int databaseSizeBeforeUpdate = jobPostingRepository.findAll().size();

        // Update the jobPosting using partial update
        JobPosting partialUpdatedJobPosting = new JobPosting();
        partialUpdatedJobPosting.setJobId(jobPosting.getJobId());

        partialUpdatedJobPosting.title(UPDATED_TITLE);

        restJobPostingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobPosting.getJobId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobPosting))
            )
            .andExpect(status().isOk());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeUpdate);
        JobPosting testJobPosting = jobPostingList.get(jobPostingList.size() - 1);
        assertThat(testJobPosting.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJobPosting.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobPosting.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testJobPosting.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateJobPostingWithPatch() throws Exception {
        // Initialize the database
        jobPostingRepository.saveAndFlush(jobPosting);

        int databaseSizeBeforeUpdate = jobPostingRepository.findAll().size();

        // Update the jobPosting using partial update
        JobPosting partialUpdatedJobPosting = new JobPosting();
        partialUpdatedJobPosting.setJobId(jobPosting.getJobId());

        partialUpdatedJobPosting
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .location(UPDATED_LOCATION)
            .postedDate(UPDATED_POSTED_DATE);

        restJobPostingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobPosting.getJobId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobPosting))
            )
            .andExpect(status().isOk());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeUpdate);
        JobPosting testJobPosting = jobPostingList.get(jobPostingList.size() - 1);
        assertThat(testJobPosting.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJobPosting.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobPosting.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testJobPosting.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingJobPosting() throws Exception {
        int databaseSizeBeforeUpdate = jobPostingRepository.findAll().size();
        jobPosting.setJobId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobPostingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobPosting.getJobId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobPosting))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobPosting() throws Exception {
        int databaseSizeBeforeUpdate = jobPostingRepository.findAll().size();
        jobPosting.setJobId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobPostingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobPosting))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobPosting() throws Exception {
        int databaseSizeBeforeUpdate = jobPostingRepository.findAll().size();
        jobPosting.setJobId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobPostingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobPosting))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobPosting in the database
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobPosting() throws Exception {
        // Initialize the database
        jobPostingRepository.saveAndFlush(jobPosting);

        int databaseSizeBeforeDelete = jobPostingRepository.findAll().size();

        // Delete the jobPosting
        restJobPostingMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobPosting.getJobId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobPosting> jobPostingList = jobPostingRepository.findAll();
        assertThat(jobPostingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
