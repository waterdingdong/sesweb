package jp.ecn.sesweb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jp.ecn.sesweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContractsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contracts.class);
        Contracts contracts1 = new Contracts();
        contracts1.setId(1L);
        Contracts contracts2 = new Contracts();
        contracts2.setId(contracts1.getId());
        assertThat(contracts1).isEqualTo(contracts2);
        contracts2.setId(2L);
        assertThat(contracts1).isNotEqualTo(contracts2);
        contracts1.setId(null);
        assertThat(contracts1).isNotEqualTo(contracts2);
    }
}
