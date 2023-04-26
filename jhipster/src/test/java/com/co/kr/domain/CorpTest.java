package com.co.kr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.co.kr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CorpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Corp.class);
        Corp corp1 = new Corp();
        corp1.setId(1L);
        Corp corp2 = new Corp();
        corp2.setId(corp1.getId());
        assertThat(corp1).isEqualTo(corp2);
        corp2.setId(2L);
        assertThat(corp1).isNotEqualTo(corp2);
        corp1.setId(null);
        assertThat(corp1).isNotEqualTo(corp2);
    }
}
