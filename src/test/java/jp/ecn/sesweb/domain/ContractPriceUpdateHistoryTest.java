package jp.ecn.sesweb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jp.ecn.sesweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContractPriceUpdateHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractPriceUpdateHistory.class);
        ContractPriceUpdateHistory contractPriceUpdateHistory1 = new ContractPriceUpdateHistory();
        contractPriceUpdateHistory1.setId(1L);
        ContractPriceUpdateHistory contractPriceUpdateHistory2 = new ContractPriceUpdateHistory();
        contractPriceUpdateHistory2.setId(contractPriceUpdateHistory1.getId());
        assertThat(contractPriceUpdateHistory1).isEqualTo(contractPriceUpdateHistory2);
        contractPriceUpdateHistory2.setId(2L);
        assertThat(contractPriceUpdateHistory1).isNotEqualTo(contractPriceUpdateHistory2);
        contractPriceUpdateHistory1.setId(null);
        assertThat(contractPriceUpdateHistory1).isNotEqualTo(contractPriceUpdateHistory2);
    }
}
