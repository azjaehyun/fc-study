package com.job.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.job.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobPostingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobPosting.class);
        JobPosting jobPosting1 = new JobPosting();
        jobPosting1.setJobId(1L);
        JobPosting jobPosting2 = new JobPosting();
        jobPosting2.setJobId(jobPosting1.getJobId());
        assertThat(jobPosting1).isEqualTo(jobPosting2);
        jobPosting2.setJobId(2L);
        assertThat(jobPosting1).isNotEqualTo(jobPosting2);
        jobPosting1.setJobId(null);
        assertThat(jobPosting1).isNotEqualTo(jobPosting2);
    }
}
