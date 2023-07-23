package com.job.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.job.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Applicant.class);
        Applicant applicant1 = new Applicant();
        applicant1.setApplicantId(1L);
        Applicant applicant2 = new Applicant();
        applicant2.setApplicantId(applicant1.getApplicantId());
        assertThat(applicant1).isEqualTo(applicant2);
        applicant2.setApplicantId(2L);
        assertThat(applicant1).isNotEqualTo(applicant2);
        applicant1.setApplicantId(null);
        assertThat(applicant1).isNotEqualTo(applicant2);
    }
}
