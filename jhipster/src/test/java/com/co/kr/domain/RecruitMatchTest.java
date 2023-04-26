package com.co.kr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.co.kr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecruitMatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecruitMatch.class);
        RecruitMatch recruitMatch1 = new RecruitMatch();
        recruitMatch1.setId(1L);
        RecruitMatch recruitMatch2 = new RecruitMatch();
        recruitMatch2.setId(recruitMatch1.getId());
        assertThat(recruitMatch1).isEqualTo(recruitMatch2);
        recruitMatch2.setId(2L);
        assertThat(recruitMatch1).isNotEqualTo(recruitMatch2);
        recruitMatch1.setId(null);
        assertThat(recruitMatch1).isNotEqualTo(recruitMatch2);
    }
}
