package com.application.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.application.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resume.class);
        Resume resume1 = new Resume();
        resume1.setResumeId(1L);
        Resume resume2 = new Resume();
        resume2.setResumeId(resume1.getResumeId());
        assertThat(resume1).isEqualTo(resume2);
        resume2.setResumeId(2L);
        assertThat(resume1).isNotEqualTo(resume2);
        resume1.setResumeId(null);
        assertThat(resume1).isNotEqualTo(resume2);
    }
}
