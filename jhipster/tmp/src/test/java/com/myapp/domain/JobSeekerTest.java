package com.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobSeekerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobSeeker.class);
        JobSeeker jobSeeker1 = new JobSeeker();
        jobSeeker1.setId(1L);
        JobSeeker jobSeeker2 = new JobSeeker();
        jobSeeker2.setId(jobSeeker1.getId());
        assertThat(jobSeeker1).isEqualTo(jobSeeker2);
        jobSeeker2.setId(2L);
        assertThat(jobSeeker1).isNotEqualTo(jobSeeker2);
        jobSeeker1.setId(null);
        assertThat(jobSeeker1).isNotEqualTo(jobSeeker2);
    }
}
