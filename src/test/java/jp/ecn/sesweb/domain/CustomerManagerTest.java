package jp.ecn.sesweb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jp.ecn.sesweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerManagerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerManager.class);
        CustomerManager customerManager1 = new CustomerManager();
        customerManager1.setId(1L);
        CustomerManager customerManager2 = new CustomerManager();
        customerManager2.setId(customerManager1.getId());
        assertThat(customerManager1).isEqualTo(customerManager2);
        customerManager2.setId(2L);
        assertThat(customerManager1).isNotEqualTo(customerManager2);
        customerManager1.setId(null);
        assertThat(customerManager1).isNotEqualTo(customerManager2);
    }
}
