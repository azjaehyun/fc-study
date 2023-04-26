package com.co.kr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.co.kr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecruitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recruit.class);
        Recruit recruit1 = new Recruit();
        recruit1.setId(1L);
        Recruit recruit2 = new Recruit();
        recruit2.setId(recruit1.getId());
        assertThat(recruit1).isEqualTo(recruit2);
        recruit2.setId(2L);
        assertThat(recruit1).isNotEqualTo(recruit2);
        recruit1.setId(null);
        assertThat(recruit1).isNotEqualTo(recruit2);
    }
}
