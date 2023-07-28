package com.job.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.job.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Company.class);
        Company company1 = new Company();
        company1.setCompanyId(1L);
        Company company2 = new Company();
        company2.setCompanyId(company1.getCompanyId());
        assertThat(company1).isEqualTo(company2);
        company2.setCompanyId(2L);
        assertThat(company1).isNotEqualTo(company2);
        company1.setCompanyId(null);
        assertThat(company1).isNotEqualTo(company2);
    }
}
