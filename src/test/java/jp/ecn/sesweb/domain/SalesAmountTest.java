package jp.ecn.sesweb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jp.ecn.sesweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesAmountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesAmount.class);
        SalesAmount salesAmount1 = new SalesAmount();
        salesAmount1.setId(1L);
        SalesAmount salesAmount2 = new SalesAmount();
        salesAmount2.setId(salesAmount1.getId());
        assertThat(salesAmount1).isEqualTo(salesAmount2);
        salesAmount2.setId(2L);
        assertThat(salesAmount1).isNotEqualTo(salesAmount2);
        salesAmount1.setId(null);
        assertThat(salesAmount1).isNotEqualTo(salesAmount2);
    }
}
