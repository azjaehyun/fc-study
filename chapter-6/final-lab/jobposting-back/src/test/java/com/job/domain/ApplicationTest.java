package com.job.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.job.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Application.class);
        Application application1 = new Application();
        application1.setApplicationId(1L);
        Application application2 = new Application();
        application2.setApplicationId(application1.getApplicationId());
        assertThat(application1).isEqualTo(application2);
        application2.setApplicationId(2L);
        assertThat(application1).isNotEqualTo(application2);
        application1.setApplicationId(null);
        assertThat(application1).isNotEqualTo(application2);
    }
}
