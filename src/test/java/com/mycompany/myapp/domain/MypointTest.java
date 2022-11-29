package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MypointTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mypoint.class);
        Mypoint mypoint1 = new Mypoint();
        mypoint1.setId("id1");
        Mypoint mypoint2 = new Mypoint();
        mypoint2.setId(mypoint1.getId());
        assertThat(mypoint1).isEqualTo(mypoint2);
        mypoint2.setId("id2");
        assertThat(mypoint1).isNotEqualTo(mypoint2);
        mypoint1.setId(null);
        assertThat(mypoint1).isNotEqualTo(mypoint2);
    }
}
