package jp.ecn.sesweb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import jp.ecn.sesweb.IntegrationTest;
import jp.ecn.sesweb.domain.ContractPriceUpdateHistory;
import jp.ecn.sesweb.repository.ContractPriceUpdateHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContractPriceUpdateHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContractPriceUpdateHistoryResourceIT {

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_BEFORE_PRICE = 1;
    private static final Integer UPDATED_BEFORE_PRICE = 2;

    private static final Integer DEFAULT_AFTER_PRICE = 1;
    private static final Integer UPDATED_AFTER_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/contract-price-update-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContractPriceUpdateHistoryRepository contractPriceUpdateHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContractPriceUpdateHistoryMockMvc;

    private ContractPriceUpdateHistory contractPriceUpdateHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractPriceUpdateHistory createEntity(EntityManager em) {
        ContractPriceUpdateHistory contractPriceUpdateHistory = new ContractPriceUpdateHistory()
            .updateDate(DEFAULT_UPDATE_DATE)
            .beforePrice(DEFAULT_BEFORE_PRICE)
            .afterPrice(DEFAULT_AFTER_PRICE);
        return contractPriceUpdateHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractPriceUpdateHistory createUpdatedEntity(EntityManager em) {
        ContractPriceUpdateHistory contractPriceUpdateHistory = new ContractPriceUpdateHistory()
            .updateDate(UPDATED_UPDATE_DATE)
            .beforePrice(UPDATED_BEFORE_PRICE)
            .afterPrice(UPDATED_AFTER_PRICE);
        return contractPriceUpdateHistory;
    }

    @BeforeEach
    public void initTest() {
        contractPriceUpdateHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createContractPriceUpdateHistory() throws Exception {
        int databaseSizeBeforeCreate = contractPriceUpdateHistoryRepository.findAll().size();
        // Create the ContractPriceUpdateHistory
        restContractPriceUpdateHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contractPriceUpdateHistory))
            )
            .andExpect(status().isCreated());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ContractPriceUpdateHistory testContractPriceUpdateHistory = contractPriceUpdateHistoryList.get(
            contractPriceUpdateHistoryList.size() - 1
        );
        assertThat(testContractPriceUpdateHistory.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testContractPriceUpdateHistory.getBeforePrice()).isEqualTo(DEFAULT_BEFORE_PRICE);
        assertThat(testContractPriceUpdateHistory.getAfterPrice()).isEqualTo(DEFAULT_AFTER_PRICE);
    }

    @Test
    @Transactional
    void createContractPriceUpdateHistoryWithExistingId() throws Exception {
        // Create the ContractPriceUpdateHistory with an existing ID
        contractPriceUpdateHistory.setId(1L);

        int databaseSizeBeforeCreate = contractPriceUpdateHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractPriceUpdateHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contractPriceUpdateHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContractPriceUpdateHistories() throws Exception {
        // Initialize the database
        contractPriceUpdateHistoryRepository.saveAndFlush(contractPriceUpdateHistory);

        // Get all the contractPriceUpdateHistoryList
        restContractPriceUpdateHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractPriceUpdateHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].beforePrice").value(hasItem(DEFAULT_BEFORE_PRICE)))
            .andExpect(jsonPath("$.[*].afterPrice").value(hasItem(DEFAULT_AFTER_PRICE)));
    }

    @Test
    @Transactional
    void getContractPriceUpdateHistory() throws Exception {
        // Initialize the database
        contractPriceUpdateHistoryRepository.saveAndFlush(contractPriceUpdateHistory);

        // Get the contractPriceUpdateHistory
        restContractPriceUpdateHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, contractPriceUpdateHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contractPriceUpdateHistory.getId().intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.beforePrice").value(DEFAULT_BEFORE_PRICE))
            .andExpect(jsonPath("$.afterPrice").value(DEFAULT_AFTER_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingContractPriceUpdateHistory() throws Exception {
        // Get the contractPriceUpdateHistory
        restContractPriceUpdateHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContractPriceUpdateHistory() throws Exception {
        // Initialize the database
        contractPriceUpdateHistoryRepository.saveAndFlush(contractPriceUpdateHistory);

        int databaseSizeBeforeUpdate = contractPriceUpdateHistoryRepository.findAll().size();

        // Update the contractPriceUpdateHistory
        ContractPriceUpdateHistory updatedContractPriceUpdateHistory = contractPriceUpdateHistoryRepository
            .findById(contractPriceUpdateHistory.getId())
            .get();
        // Disconnect from session so that the updates on updatedContractPriceUpdateHistory are not directly saved in db
        em.detach(updatedContractPriceUpdateHistory);
        updatedContractPriceUpdateHistory.updateDate(UPDATED_UPDATE_DATE).beforePrice(UPDATED_BEFORE_PRICE).afterPrice(UPDATED_AFTER_PRICE);

        restContractPriceUpdateHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContractPriceUpdateHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContractPriceUpdateHistory))
            )
            .andExpect(status().isOk());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeUpdate);
        ContractPriceUpdateHistory testContractPriceUpdateHistory = contractPriceUpdateHistoryList.get(
            contractPriceUpdateHistoryList.size() - 1
        );
        assertThat(testContractPriceUpdateHistory.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testContractPriceUpdateHistory.getBeforePrice()).isEqualTo(UPDATED_BEFORE_PRICE);
        assertThat(testContractPriceUpdateHistory.getAfterPrice()).isEqualTo(UPDATED_AFTER_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingContractPriceUpdateHistory() throws Exception {
        int databaseSizeBeforeUpdate = contractPriceUpdateHistoryRepository.findAll().size();
        contractPriceUpdateHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractPriceUpdateHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contractPriceUpdateHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contractPriceUpdateHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContractPriceUpdateHistory() throws Exception {
        int databaseSizeBeforeUpdate = contractPriceUpdateHistoryRepository.findAll().size();
        contractPriceUpdateHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractPriceUpdateHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contractPriceUpdateHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContractPriceUpdateHistory() throws Exception {
        int databaseSizeBeforeUpdate = contractPriceUpdateHistoryRepository.findAll().size();
        contractPriceUpdateHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractPriceUpdateHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contractPriceUpdateHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContractPriceUpdateHistoryWithPatch() throws Exception {
        // Initialize the database
        contractPriceUpdateHistoryRepository.saveAndFlush(contractPriceUpdateHistory);

        int databaseSizeBeforeUpdate = contractPriceUpdateHistoryRepository.findAll().size();

        // Update the contractPriceUpdateHistory using partial update
        ContractPriceUpdateHistory partialUpdatedContractPriceUpdateHistory = new ContractPriceUpdateHistory();
        partialUpdatedContractPriceUpdateHistory.setId(contractPriceUpdateHistory.getId());

        partialUpdatedContractPriceUpdateHistory
            .updateDate(UPDATED_UPDATE_DATE)
            .beforePrice(UPDATED_BEFORE_PRICE)
            .afterPrice(UPDATED_AFTER_PRICE);

        restContractPriceUpdateHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContractPriceUpdateHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContractPriceUpdateHistory))
            )
            .andExpect(status().isOk());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeUpdate);
        ContractPriceUpdateHistory testContractPriceUpdateHistory = contractPriceUpdateHistoryList.get(
            contractPriceUpdateHistoryList.size() - 1
        );
        assertThat(testContractPriceUpdateHistory.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testContractPriceUpdateHistory.getBeforePrice()).isEqualTo(UPDATED_BEFORE_PRICE);
        assertThat(testContractPriceUpdateHistory.getAfterPrice()).isEqualTo(UPDATED_AFTER_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateContractPriceUpdateHistoryWithPatch() throws Exception {
        // Initialize the database
        contractPriceUpdateHistoryRepository.saveAndFlush(contractPriceUpdateHistory);

        int databaseSizeBeforeUpdate = contractPriceUpdateHistoryRepository.findAll().size();

        // Update the contractPriceUpdateHistory using partial update
        ContractPriceUpdateHistory partialUpdatedContractPriceUpdateHistory = new ContractPriceUpdateHistory();
        partialUpdatedContractPriceUpdateHistory.setId(contractPriceUpdateHistory.getId());

        partialUpdatedContractPriceUpdateHistory
            .updateDate(UPDATED_UPDATE_DATE)
            .beforePrice(UPDATED_BEFORE_PRICE)
            .afterPrice(UPDATED_AFTER_PRICE);

        restContractPriceUpdateHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContractPriceUpdateHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContractPriceUpdateHistory))
            )
            .andExpect(status().isOk());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeUpdate);
        ContractPriceUpdateHistory testContractPriceUpdateHistory = contractPriceUpdateHistoryList.get(
            contractPriceUpdateHistoryList.size() - 1
        );
        assertThat(testContractPriceUpdateHistory.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testContractPriceUpdateHistory.getBeforePrice()).isEqualTo(UPDATED_BEFORE_PRICE);
        assertThat(testContractPriceUpdateHistory.getAfterPrice()).isEqualTo(UPDATED_AFTER_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingContractPriceUpdateHistory() throws Exception {
        int databaseSizeBeforeUpdate = contractPriceUpdateHistoryRepository.findAll().size();
        contractPriceUpdateHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractPriceUpdateHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contractPriceUpdateHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contractPriceUpdateHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContractPriceUpdateHistory() throws Exception {
        int databaseSizeBeforeUpdate = contractPriceUpdateHistoryRepository.findAll().size();
        contractPriceUpdateHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractPriceUpdateHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contractPriceUpdateHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContractPriceUpdateHistory() throws Exception {
        int databaseSizeBeforeUpdate = contractPriceUpdateHistoryRepository.findAll().size();
        contractPriceUpdateHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractPriceUpdateHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contractPriceUpdateHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContractPriceUpdateHistory in the database
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContractPriceUpdateHistory() throws Exception {
        // Initialize the database
        contractPriceUpdateHistoryRepository.saveAndFlush(contractPriceUpdateHistory);

        int databaseSizeBeforeDelete = contractPriceUpdateHistoryRepository.findAll().size();

        // Delete the contractPriceUpdateHistory
        restContractPriceUpdateHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, contractPriceUpdateHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContractPriceUpdateHistory> contractPriceUpdateHistoryList = contractPriceUpdateHistoryRepository.findAll();
        assertThat(contractPriceUpdateHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
