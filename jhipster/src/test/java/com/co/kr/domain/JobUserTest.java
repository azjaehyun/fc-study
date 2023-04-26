package com.co.kr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.co.kr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobUser.class);
        JobUser jobUser1 = new JobUser();
        jobUser1.setId(1L);
        JobUser jobUser2 = new JobUser();
        jobUser2.setId(jobUser1.getId());
        assertThat(jobUser1).isEqualTo(jobUser2);
        jobUser2.setId(2L);
        assertThat(jobUser1).isNotEqualTo(jobUser2);
        jobUser1.setId(null);
        assertThat(jobUser1).isNotEqualTo(jobUser2);
    }
}
