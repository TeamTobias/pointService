package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MypointDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MypointDTO.class);
        MypointDTO mypointDTO1 = new MypointDTO();
        mypointDTO1.setUserid("id1");
        MypointDTO mypointDTO2 = new MypointDTO();
        assertThat(mypointDTO1).isNotEqualTo(mypointDTO2);
        mypointDTO2.setUserid(mypointDTO1.getUserid());
        assertThat(mypointDTO1).isEqualTo(mypointDTO2);
        mypointDTO2.setUserid("id2");
        assertThat(mypointDTO1).isNotEqualTo(mypointDTO2);
        mypointDTO1.setUserid(null);
        assertThat(mypointDTO1).isNotEqualTo(mypointDTO2);
    }
}
