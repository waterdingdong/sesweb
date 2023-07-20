package jp.ecn.sesweb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import jp.ecn.sesweb.IntegrationTest;
import jp.ecn.sesweb.domain.SalesAmount;
import jp.ecn.sesweb.repository.SalesAmountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SalesAmountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalesAmountResourceIT {

    private static final String DEFAULT_SALES_YM = "AAAAAAAAAA";
    private static final String UPDATED_SALES_YM = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORK_TIME = 1;
    private static final Integer UPDATED_WORK_TIME = 2;

    private static final Integer DEFAULT_BILLING_AMOUNT = 1;
    private static final Integer UPDATED_BILLING_AMOUNT = 2;

    private static final String ENTITY_API_URL = "/api/sales-amounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalesAmountRepository salesAmountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesAmountMockMvc;

    private SalesAmount salesAmount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesAmount createEntity(EntityManager em) {
        SalesAmount salesAmount = new SalesAmount()
            .salesYm(DEFAULT_SALES_YM)
            .workTime(DEFAULT_WORK_TIME)
            .billingAmount(DEFAULT_BILLING_AMOUNT);
        return salesAmount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesAmount createUpdatedEntity(EntityManager em) {
        SalesAmount salesAmount = new SalesAmount()
            .salesYm(UPDATED_SALES_YM)
            .workTime(UPDATED_WORK_TIME)
            .billingAmount(UPDATED_BILLING_AMOUNT);
        return salesAmount;
    }

    @BeforeEach
    public void initTest() {
        salesAmount = createEntity(em);
    }

    @Test
    @Transactional
    void createSalesAmount() throws Exception {
        int databaseSizeBeforeCreate = salesAmountRepository.findAll().size();
        // Create the SalesAmount
        restSalesAmountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesAmount)))
            .andExpect(status().isCreated());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeCreate + 1);
        SalesAmount testSalesAmount = salesAmountList.get(salesAmountList.size() - 1);
        assertThat(testSalesAmount.getSalesYm()).isEqualTo(DEFAULT_SALES_YM);
        assertThat(testSalesAmount.getWorkTime()).isEqualTo(DEFAULT_WORK_TIME);
        assertThat(testSalesAmount.getBillingAmount()).isEqualTo(DEFAULT_BILLING_AMOUNT);
    }

    @Test
    @Transactional
    void createSalesAmountWithExistingId() throws Exception {
        // Create the SalesAmount with an existing ID
        salesAmount.setId(1L);

        int databaseSizeBeforeCreate = salesAmountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesAmountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesAmount)))
            .andExpect(status().isBadRequest());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesAmounts() throws Exception {
        // Initialize the database
        salesAmountRepository.saveAndFlush(salesAmount);

        // Get all the salesAmountList
        restSalesAmountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesYm").value(hasItem(DEFAULT_SALES_YM)))
            .andExpect(jsonPath("$.[*].workTime").value(hasItem(DEFAULT_WORK_TIME)))
            .andExpect(jsonPath("$.[*].billingAmount").value(hasItem(DEFAULT_BILLING_AMOUNT)));
    }

    @Test
    @Transactional
    void getSalesAmount() throws Exception {
        // Initialize the database
        salesAmountRepository.saveAndFlush(salesAmount);

        // Get the salesAmount
        restSalesAmountMockMvc
            .perform(get(ENTITY_API_URL_ID, salesAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesAmount.getId().intValue()))
            .andExpect(jsonPath("$.salesYm").value(DEFAULT_SALES_YM))
            .andExpect(jsonPath("$.workTime").value(DEFAULT_WORK_TIME))
            .andExpect(jsonPath("$.billingAmount").value(DEFAULT_BILLING_AMOUNT));
    }

    @Test
    @Transactional
    void getNonExistingSalesAmount() throws Exception {
        // Get the salesAmount
        restSalesAmountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesAmount() throws Exception {
        // Initialize the database
        salesAmountRepository.saveAndFlush(salesAmount);

        int databaseSizeBeforeUpdate = salesAmountRepository.findAll().size();

        // Update the salesAmount
        SalesAmount updatedSalesAmount = salesAmountRepository.findById(salesAmount.getId()).get();
        // Disconnect from session so that the updates on updatedSalesAmount are not directly saved in db
        em.detach(updatedSalesAmount);
        updatedSalesAmount.salesYm(UPDATED_SALES_YM).workTime(UPDATED_WORK_TIME).billingAmount(UPDATED_BILLING_AMOUNT);

        restSalesAmountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSalesAmount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSalesAmount))
            )
            .andExpect(status().isOk());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeUpdate);
        SalesAmount testSalesAmount = salesAmountList.get(salesAmountList.size() - 1);
        assertThat(testSalesAmount.getSalesYm()).isEqualTo(UPDATED_SALES_YM);
        assertThat(testSalesAmount.getWorkTime()).isEqualTo(UPDATED_WORK_TIME);
        assertThat(testSalesAmount.getBillingAmount()).isEqualTo(UPDATED_BILLING_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingSalesAmount() throws Exception {
        int databaseSizeBeforeUpdate = salesAmountRepository.findAll().size();
        salesAmount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesAmountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesAmount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesAmount))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesAmount() throws Exception {
        int databaseSizeBeforeUpdate = salesAmountRepository.findAll().size();
        salesAmount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesAmountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesAmount))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesAmount() throws Exception {
        int databaseSizeBeforeUpdate = salesAmountRepository.findAll().size();
        salesAmount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesAmountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesAmount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesAmountWithPatch() throws Exception {
        // Initialize the database
        salesAmountRepository.saveAndFlush(salesAmount);

        int databaseSizeBeforeUpdate = salesAmountRepository.findAll().size();

        // Update the salesAmount using partial update
        SalesAmount partialUpdatedSalesAmount = new SalesAmount();
        partialUpdatedSalesAmount.setId(salesAmount.getId());

        partialUpdatedSalesAmount.workTime(UPDATED_WORK_TIME).billingAmount(UPDATED_BILLING_AMOUNT);

        restSalesAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesAmount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesAmount))
            )
            .andExpect(status().isOk());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeUpdate);
        SalesAmount testSalesAmount = salesAmountList.get(salesAmountList.size() - 1);
        assertThat(testSalesAmount.getSalesYm()).isEqualTo(DEFAULT_SALES_YM);
        assertThat(testSalesAmount.getWorkTime()).isEqualTo(UPDATED_WORK_TIME);
        assertThat(testSalesAmount.getBillingAmount()).isEqualTo(UPDATED_BILLING_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateSalesAmountWithPatch() throws Exception {
        // Initialize the database
        salesAmountRepository.saveAndFlush(salesAmount);

        int databaseSizeBeforeUpdate = salesAmountRepository.findAll().size();

        // Update the salesAmount using partial update
        SalesAmount partialUpdatedSalesAmount = new SalesAmount();
        partialUpdatedSalesAmount.setId(salesAmount.getId());

        partialUpdatedSalesAmount.salesYm(UPDATED_SALES_YM).workTime(UPDATED_WORK_TIME).billingAmount(UPDATED_BILLING_AMOUNT);

        restSalesAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesAmount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesAmount))
            )
            .andExpect(status().isOk());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeUpdate);
        SalesAmount testSalesAmount = salesAmountList.get(salesAmountList.size() - 1);
        assertThat(testSalesAmount.getSalesYm()).isEqualTo(UPDATED_SALES_YM);
        assertThat(testSalesAmount.getWorkTime()).isEqualTo(UPDATED_WORK_TIME);
        assertThat(testSalesAmount.getBillingAmount()).isEqualTo(UPDATED_BILLING_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingSalesAmount() throws Exception {
        int databaseSizeBeforeUpdate = salesAmountRepository.findAll().size();
        salesAmount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesAmount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesAmount))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesAmount() throws Exception {
        int databaseSizeBeforeUpdate = salesAmountRepository.findAll().size();
        salesAmount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesAmount))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesAmount() throws Exception {
        int databaseSizeBeforeUpdate = salesAmountRepository.findAll().size();
        salesAmount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesAmountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(salesAmount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesAmount in the database
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesAmount() throws Exception {
        // Initialize the database
        salesAmountRepository.saveAndFlush(salesAmount);

        int databaseSizeBeforeDelete = salesAmountRepository.findAll().size();

        // Delete the salesAmount
        restSalesAmountMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesAmount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesAmount> salesAmountList = salesAmountRepository.findAll();
        assertThat(salesAmountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
