package jp.ecn.sesweb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jp.ecn.sesweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartnerManagerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerManager.class);
        PartnerManager partnerManager1 = new PartnerManager();
        partnerManager1.setId(1L);
        PartnerManager partnerManager2 = new PartnerManager();
        partnerManager2.setId(partnerManager1.getId());
        assertThat(partnerManager1).isEqualTo(partnerManager2);
        partnerManager2.setId(2L);
        assertThat(partnerManager1).isNotEqualTo(partnerManager2);
        partnerManager1.setId(null);
        assertThat(partnerManager1).isNotEqualTo(partnerManager2);
    }
}
