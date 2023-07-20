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
import jp.ecn.sesweb.domain.Contracts;
import jp.ecn.sesweb.repository.ContractsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContractsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContractsResourceIT {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ECN_ID = "AAAAAAAAAA";
    private static final String UPDATED_ECN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_ECN_ID = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_ECN_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CONTRACT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTRACT_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CONTRACT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTRACT_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ORDER_UNIT_PRICE = 1;
    private static final Integer UPDATED_ORDER_UNIT_PRICE = 2;

    private static final Integer DEFAULT_MAXIMUM_ORDER_SETTLEMENT_CONDITION = 1;
    private static final Integer UPDATED_MAXIMUM_ORDER_SETTLEMENT_CONDITION = 2;

    private static final Integer DEFAULT_MINIMUM_ORDER_SETTLEMENT_CONDITION = 1;
    private static final Integer UPDATED_MINIMUM_ORDER_SETTLEMENT_CONDITION = 2;

    private static final Integer DEFAULT_ORDER_DEDUCTION_UNIT_PRICE = 1;
    private static final Integer UPDATED_ORDER_DEDUCTION_UNIT_PRICE = 2;

    private static final Integer DEFAULT_ORDER_SURCHARGE_UNIT_PRICE = 1;
    private static final Integer UPDATED_ORDER_SURCHARGE_UNIT_PRICE = 2;

    private static final Integer DEFAULT_PURCHASE_UNIT_PRICE = 1;
    private static final Integer UPDATED_PURCHASE_UNIT_PRICE = 2;

    private static final Integer DEFAULT_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION = 1;
    private static final Integer UPDATED_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION = 2;

    private static final Integer DEFAULT_MINIMUM_PURCHASE_SETTLEMENT_CONDITION = 1;
    private static final Integer UPDATED_MINIMUM_PURCHASE_SETTLEMENT_CONDITION = 2;

    private static final Integer DEFAULT_PURCHASE_DEDUCTION_UNIT_PRICE = 1;
    private static final Integer UPDATED_PURCHASE_DEDUCTION_UNIT_PRICE = 2;

    private static final Integer DEFAULT_PURCHASE_SURCHARGE_UNIT_PRICE = 1;
    private static final Integer UPDATED_PURCHASE_SURCHARGE_UNIT_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContractsRepository contractsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContractsMockMvc;

    private Contracts contracts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contracts createEntity(EntityManager em) {
        Contracts contracts = new Contracts()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .ecnId(DEFAULT_ECN_ID)
            .manager(DEFAULT_MANAGER)
            .managerEcnId(DEFAULT_MANAGER_ECN_ID)
            .contractStartDate(DEFAULT_CONTRACT_START_DATE)
            .contractEndDate(DEFAULT_CONTRACT_END_DATE)
            .orderUnitPrice(DEFAULT_ORDER_UNIT_PRICE)
            .maximumOrderSettlementCondition(DEFAULT_MAXIMUM_ORDER_SETTLEMENT_CONDITION)
            .minimumOrderSettlementCondition(DEFAULT_MINIMUM_ORDER_SETTLEMENT_CONDITION)
            .orderDeductionUnitPrice(DEFAULT_ORDER_DEDUCTION_UNIT_PRICE)
            .orderSurchargeUnitPrice(DEFAULT_ORDER_SURCHARGE_UNIT_PRICE)
            .purchaseUnitPrice(DEFAULT_PURCHASE_UNIT_PRICE)
            .maximumPurchaseSettlementCondition(DEFAULT_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION)
            .minimumPurchaseSettlementCondition(DEFAULT_MINIMUM_PURCHASE_SETTLEMENT_CONDITION)
            .purchaseDeductionUnitPrice(DEFAULT_PURCHASE_DEDUCTION_UNIT_PRICE)
            .purchaseSurchargeUnitPrice(DEFAULT_PURCHASE_SURCHARGE_UNIT_PRICE);
        return contracts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contracts createUpdatedEntity(EntityManager em) {
        Contracts contracts = new Contracts()
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .ecnId(UPDATED_ECN_ID)
            .manager(UPDATED_MANAGER)
            .managerEcnId(UPDATED_MANAGER_ECN_ID)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .orderUnitPrice(UPDATED_ORDER_UNIT_PRICE)
            .maximumOrderSettlementCondition(UPDATED_MAXIMUM_ORDER_SETTLEMENT_CONDITION)
            .minimumOrderSettlementCondition(UPDATED_MINIMUM_ORDER_SETTLEMENT_CONDITION)
            .orderDeductionUnitPrice(UPDATED_ORDER_DEDUCTION_UNIT_PRICE)
            .orderSurchargeUnitPrice(UPDATED_ORDER_SURCHARGE_UNIT_PRICE)
            .purchaseUnitPrice(UPDATED_PURCHASE_UNIT_PRICE)
            .maximumPurchaseSettlementCondition(UPDATED_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION)
            .minimumPurchaseSettlementCondition(UPDATED_MINIMUM_PURCHASE_SETTLEMENT_CONDITION)
            .purchaseDeductionUnitPrice(UPDATED_PURCHASE_DEDUCTION_UNIT_PRICE)
            .purchaseSurchargeUnitPrice(UPDATED_PURCHASE_SURCHARGE_UNIT_PRICE);
        return contracts;
    }

    @BeforeEach
    public void initTest() {
        contracts = createEntity(em);
    }

    @Test
    @Transactional
    void createContracts() throws Exception {
        int databaseSizeBeforeCreate = contractsRepository.findAll().size();
        // Create the Contracts
        restContractsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contracts)))
            .andExpect(status().isCreated());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeCreate + 1);
        Contracts testContracts = contractsList.get(contractsList.size() - 1);
        assertThat(testContracts.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContracts.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContracts.getEcnId()).isEqualTo(DEFAULT_ECN_ID);
        assertThat(testContracts.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testContracts.getManagerEcnId()).isEqualTo(DEFAULT_MANAGER_ECN_ID);
        assertThat(testContracts.getContractStartDate()).isEqualTo(DEFAULT_CONTRACT_START_DATE);
        assertThat(testContracts.getContractEndDate()).isEqualTo(DEFAULT_CONTRACT_END_DATE);
        assertThat(testContracts.getOrderUnitPrice()).isEqualTo(DEFAULT_ORDER_UNIT_PRICE);
        assertThat(testContracts.getMaximumOrderSettlementCondition()).isEqualTo(DEFAULT_MAXIMUM_ORDER_SETTLEMENT_CONDITION);
        assertThat(testContracts.getMinimumOrderSettlementCondition()).isEqualTo(DEFAULT_MINIMUM_ORDER_SETTLEMENT_CONDITION);
        assertThat(testContracts.getOrderDeductionUnitPrice()).isEqualTo(DEFAULT_ORDER_DEDUCTION_UNIT_PRICE);
        assertThat(testContracts.getOrderSurchargeUnitPrice()).isEqualTo(DEFAULT_ORDER_SURCHARGE_UNIT_PRICE);
        assertThat(testContracts.getPurchaseUnitPrice()).isEqualTo(DEFAULT_PURCHASE_UNIT_PRICE);
        assertThat(testContracts.getMaximumPurchaseSettlementCondition()).isEqualTo(DEFAULT_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION);
        assertThat(testContracts.getMinimumPurchaseSettlementCondition()).isEqualTo(DEFAULT_MINIMUM_PURCHASE_SETTLEMENT_CONDITION);
        assertThat(testContracts.getPurchaseDeductionUnitPrice()).isEqualTo(DEFAULT_PURCHASE_DEDUCTION_UNIT_PRICE);
        assertThat(testContracts.getPurchaseSurchargeUnitPrice()).isEqualTo(DEFAULT_PURCHASE_SURCHARGE_UNIT_PRICE);
    }

    @Test
    @Transactional
    void createContractsWithExistingId() throws Exception {
        // Create the Contracts with an existing ID
        contracts.setId(1L);

        int databaseSizeBeforeCreate = contractsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contracts)))
            .andExpect(status().isBadRequest());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractsRepository.findAll().size();
        // set the field null
        contracts.setLastName(null);

        // Create the Contracts, which fails.

        restContractsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contracts)))
            .andExpect(status().isBadRequest());

        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractsRepository.findAll().size();
        // set the field null
        contracts.setFirstName(null);

        // Create the Contracts, which fails.

        restContractsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contracts)))
            .andExpect(status().isBadRequest());

        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractsRepository.findAll().size();
        // set the field null
        contracts.setOrderUnitPrice(null);

        // Create the Contracts, which fails.

        restContractsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contracts)))
            .andExpect(status().isBadRequest());

        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContracts() throws Exception {
        // Initialize the database
        contractsRepository.saveAndFlush(contracts);

        // Get all the contractsList
        restContractsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contracts.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].ecnId").value(hasItem(DEFAULT_ECN_ID)))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER)))
            .andExpect(jsonPath("$.[*].managerEcnId").value(hasItem(DEFAULT_MANAGER_ECN_ID)))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderUnitPrice").value(hasItem(DEFAULT_ORDER_UNIT_PRICE)))
            .andExpect(jsonPath("$.[*].maximumOrderSettlementCondition").value(hasItem(DEFAULT_MAXIMUM_ORDER_SETTLEMENT_CONDITION)))
            .andExpect(jsonPath("$.[*].minimumOrderSettlementCondition").value(hasItem(DEFAULT_MINIMUM_ORDER_SETTLEMENT_CONDITION)))
            .andExpect(jsonPath("$.[*].orderDeductionUnitPrice").value(hasItem(DEFAULT_ORDER_DEDUCTION_UNIT_PRICE)))
            .andExpect(jsonPath("$.[*].orderSurchargeUnitPrice").value(hasItem(DEFAULT_ORDER_SURCHARGE_UNIT_PRICE)))
            .andExpect(jsonPath("$.[*].purchaseUnitPrice").value(hasItem(DEFAULT_PURCHASE_UNIT_PRICE)))
            .andExpect(jsonPath("$.[*].maximumPurchaseSettlementCondition").value(hasItem(DEFAULT_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION)))
            .andExpect(jsonPath("$.[*].minimumPurchaseSettlementCondition").value(hasItem(DEFAULT_MINIMUM_PURCHASE_SETTLEMENT_CONDITION)))
            .andExpect(jsonPath("$.[*].purchaseDeductionUnitPrice").value(hasItem(DEFAULT_PURCHASE_DEDUCTION_UNIT_PRICE)))
            .andExpect(jsonPath("$.[*].purchaseSurchargeUnitPrice").value(hasItem(DEFAULT_PURCHASE_SURCHARGE_UNIT_PRICE)));
    }

    @Test
    @Transactional
    void getContracts() throws Exception {
        // Initialize the database
        contractsRepository.saveAndFlush(contracts);

        // Get the contracts
        restContractsMockMvc
            .perform(get(ENTITY_API_URL_ID, contracts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contracts.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.ecnId").value(DEFAULT_ECN_ID))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER))
            .andExpect(jsonPath("$.managerEcnId").value(DEFAULT_MANAGER_ECN_ID))
            .andExpect(jsonPath("$.contractStartDate").value(DEFAULT_CONTRACT_START_DATE.toString()))
            .andExpect(jsonPath("$.contractEndDate").value(DEFAULT_CONTRACT_END_DATE.toString()))
            .andExpect(jsonPath("$.orderUnitPrice").value(DEFAULT_ORDER_UNIT_PRICE))
            .andExpect(jsonPath("$.maximumOrderSettlementCondition").value(DEFAULT_MAXIMUM_ORDER_SETTLEMENT_CONDITION))
            .andExpect(jsonPath("$.minimumOrderSettlementCondition").value(DEFAULT_MINIMUM_ORDER_SETTLEMENT_CONDITION))
            .andExpect(jsonPath("$.orderDeductionUnitPrice").value(DEFAULT_ORDER_DEDUCTION_UNIT_PRICE))
            .andExpect(jsonPath("$.orderSurchargeUnitPrice").value(DEFAULT_ORDER_SURCHARGE_UNIT_PRICE))
            .andExpect(jsonPath("$.purchaseUnitPrice").value(DEFAULT_PURCHASE_UNIT_PRICE))
            .andExpect(jsonPath("$.maximumPurchaseSettlementCondition").value(DEFAULT_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION))
            .andExpect(jsonPath("$.minimumPurchaseSettlementCondition").value(DEFAULT_MINIMUM_PURCHASE_SETTLEMENT_CONDITION))
            .andExpect(jsonPath("$.purchaseDeductionUnitPrice").value(DEFAULT_PURCHASE_DEDUCTION_UNIT_PRICE))
            .andExpect(jsonPath("$.purchaseSurchargeUnitPrice").value(DEFAULT_PURCHASE_SURCHARGE_UNIT_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingContracts() throws Exception {
        // Get the contracts
        restContractsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContracts() throws Exception {
        // Initialize the database
        contractsRepository.saveAndFlush(contracts);

        int databaseSizeBeforeUpdate = contractsRepository.findAll().size();

        // Update the contracts
        Contracts updatedContracts = contractsRepository.findById(contracts.getId()).get();
        // Disconnect from session so that the updates on updatedContracts are not directly saved in db
        em.detach(updatedContracts);
        updatedContracts
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .ecnId(UPDATED_ECN_ID)
            .manager(UPDATED_MANAGER)
            .managerEcnId(UPDATED_MANAGER_ECN_ID)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .orderUnitPrice(UPDATED_ORDER_UNIT_PRICE)
            .maximumOrderSettlementCondition(UPDATED_MAXIMUM_ORDER_SETTLEMENT_CONDITION)
            .minimumOrderSettlementCondition(UPDATED_MINIMUM_ORDER_SETTLEMENT_CONDITION)
            .orderDeductionUnitPrice(UPDATED_ORDER_DEDUCTION_UNIT_PRICE)
            .orderSurchargeUnitPrice(UPDATED_ORDER_SURCHARGE_UNIT_PRICE)
            .purchaseUnitPrice(UPDATED_PURCHASE_UNIT_PRICE)
            .maximumPurchaseSettlementCondition(UPDATED_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION)
            .minimumPurchaseSettlementCondition(UPDATED_MINIMUM_PURCHASE_SETTLEMENT_CONDITION)
            .purchaseDeductionUnitPrice(UPDATED_PURCHASE_DEDUCTION_UNIT_PRICE)
            .purchaseSurchargeUnitPrice(UPDATED_PURCHASE_SURCHARGE_UNIT_PRICE);

        restContractsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContracts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContracts))
            )
            .andExpect(status().isOk());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeUpdate);
        Contracts testContracts = contractsList.get(contractsList.size() - 1);
        assertThat(testContracts.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContracts.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContracts.getEcnId()).isEqualTo(UPDATED_ECN_ID);
        assertThat(testContracts.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testContracts.getManagerEcnId()).isEqualTo(UPDATED_MANAGER_ECN_ID);
        assertThat(testContracts.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testContracts.getContractEndDate()).isEqualTo(UPDATED_CONTRACT_END_DATE);
        assertThat(testContracts.getOrderUnitPrice()).isEqualTo(UPDATED_ORDER_UNIT_PRICE);
        assertThat(testContracts.getMaximumOrderSettlementCondition()).isEqualTo(UPDATED_MAXIMUM_ORDER_SETTLEMENT_CONDITION);
        assertThat(testContracts.getMinimumOrderSettlementCondition()).isEqualTo(UPDATED_MINIMUM_ORDER_SETTLEMENT_CONDITION);
        assertThat(testContracts.getOrderDeductionUnitPrice()).isEqualTo(UPDATED_ORDER_DEDUCTION_UNIT_PRICE);
        assertThat(testContracts.getOrderSurchargeUnitPrice()).isEqualTo(UPDATED_ORDER_SURCHARGE_UNIT_PRICE);
        assertThat(testContracts.getPurchaseUnitPrice()).isEqualTo(UPDATED_PURCHASE_UNIT_PRICE);
        assertThat(testContracts.getMaximumPurchaseSettlementCondition()).isEqualTo(UPDATED_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION);
        assertThat(testContracts.getMinimumPurchaseSettlementCondition()).isEqualTo(UPDATED_MINIMUM_PURCHASE_SETTLEMENT_CONDITION);
        assertThat(testContracts.getPurchaseDeductionUnitPrice()).isEqualTo(UPDATED_PURCHASE_DEDUCTION_UNIT_PRICE);
        assertThat(testContracts.getPurchaseSurchargeUnitPrice()).isEqualTo(UPDATED_PURCHASE_SURCHARGE_UNIT_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingContracts() throws Exception {
        int databaseSizeBeforeUpdate = contractsRepository.findAll().size();
        contracts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contracts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contracts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContracts() throws Exception {
        int databaseSizeBeforeUpdate = contractsRepository.findAll().size();
        contracts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contracts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContracts() throws Exception {
        int databaseSizeBeforeUpdate = contractsRepository.findAll().size();
        contracts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contracts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContractsWithPatch() throws Exception {
        // Initialize the database
        contractsRepository.saveAndFlush(contracts);

        int databaseSizeBeforeUpdate = contractsRepository.findAll().size();

        // Update the contracts using partial update
        Contracts partialUpdatedContracts = new Contracts();
        partialUpdatedContracts.setId(contracts.getId());

        partialUpdatedContracts
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .maximumPurchaseSettlementCondition(UPDATED_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION)
            .purchaseDeductionUnitPrice(UPDATED_PURCHASE_DEDUCTION_UNIT_PRICE)
            .purchaseSurchargeUnitPrice(UPDATED_PURCHASE_SURCHARGE_UNIT_PRICE);

        restContractsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContracts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContracts))
            )
            .andExpect(status().isOk());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeUpdate);
        Contracts testContracts = contractsList.get(contractsList.size() - 1);
        assertThat(testContracts.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContracts.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContracts.getEcnId()).isEqualTo(DEFAULT_ECN_ID);
        assertThat(testContracts.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testContracts.getManagerEcnId()).isEqualTo(DEFAULT_MANAGER_ECN_ID);
        assertThat(testContracts.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testContracts.getContractEndDate()).isEqualTo(UPDATED_CONTRACT_END_DATE);
        assertThat(testContracts.getOrderUnitPrice()).isEqualTo(DEFAULT_ORDER_UNIT_PRICE);
        assertThat(testContracts.getMaximumOrderSettlementCondition()).isEqualTo(DEFAULT_MAXIMUM_ORDER_SETTLEMENT_CONDITION);
        assertThat(testContracts.getMinimumOrderSettlementCondition()).isEqualTo(DEFAULT_MINIMUM_ORDER_SETTLEMENT_CONDITION);
        assertThat(testContracts.getOrderDeductionUnitPrice()).isEqualTo(DEFAULT_ORDER_DEDUCTION_UNIT_PRICE);
        assertThat(testContracts.getOrderSurchargeUnitPrice()).isEqualTo(DEFAULT_ORDER_SURCHARGE_UNIT_PRICE);
        assertThat(testContracts.getPurchaseUnitPrice()).isEqualTo(DEFAULT_PURCHASE_UNIT_PRICE);
        assertThat(testContracts.getMaximumPurchaseSettlementCondition()).isEqualTo(UPDATED_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION);
        assertThat(testContracts.getMinimumPurchaseSettlementCondition()).isEqualTo(DEFAULT_MINIMUM_PURCHASE_SETTLEMENT_CONDITION);
        assertThat(testContracts.getPurchaseDeductionUnitPrice()).isEqualTo(UPDATED_PURCHASE_DEDUCTION_UNIT_PRICE);
        assertThat(testContracts.getPurchaseSurchargeUnitPrice()).isEqualTo(UPDATED_PURCHASE_SURCHARGE_UNIT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateContractsWithPatch() throws Exception {
        // Initialize the database
        contractsRepository.saveAndFlush(contracts);

        int databaseSizeBeforeUpdate = contractsRepository.findAll().size();

        // Update the contracts using partial update
        Contracts partialUpdatedContracts = new Contracts();
        partialUpdatedContracts.setId(contracts.getId());

        partialUpdatedContracts
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .ecnId(UPDATED_ECN_ID)
            .manager(UPDATED_MANAGER)
            .managerEcnId(UPDATED_MANAGER_ECN_ID)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .orderUnitPrice(UPDATED_ORDER_UNIT_PRICE)
            .maximumOrderSettlementCondition(UPDATED_MAXIMUM_ORDER_SETTLEMENT_CONDITION)
            .minimumOrderSettlementCondition(UPDATED_MINIMUM_ORDER_SETTLEMENT_CONDITION)
            .orderDeductionUnitPrice(UPDATED_ORDER_DEDUCTION_UNIT_PRICE)
            .orderSurchargeUnitPrice(UPDATED_ORDER_SURCHARGE_UNIT_PRICE)
            .purchaseUnitPrice(UPDATED_PURCHASE_UNIT_PRICE)
            .maximumPurchaseSettlementCondition(UPDATED_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION)
            .minimumPurchaseSettlementCondition(UPDATED_MINIMUM_PURCHASE_SETTLEMENT_CONDITION)
            .purchaseDeductionUnitPrice(UPDATED_PURCHASE_DEDUCTION_UNIT_PRICE)
            .purchaseSurchargeUnitPrice(UPDATED_PURCHASE_SURCHARGE_UNIT_PRICE);

        restContractsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContracts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContracts))
            )
            .andExpect(status().isOk());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeUpdate);
        Contracts testContracts = contractsList.get(contractsList.size() - 1);
        assertThat(testContracts.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContracts.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContracts.getEcnId()).isEqualTo(UPDATED_ECN_ID);
        assertThat(testContracts.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testContracts.getManagerEcnId()).isEqualTo(UPDATED_MANAGER_ECN_ID);
        assertThat(testContracts.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testContracts.getContractEndDate()).isEqualTo(UPDATED_CONTRACT_END_DATE);
        assertThat(testContracts.getOrderUnitPrice()).isEqualTo(UPDATED_ORDER_UNIT_PRICE);
        assertThat(testContracts.getMaximumOrderSettlementCondition()).isEqualTo(UPDATED_MAXIMUM_ORDER_SETTLEMENT_CONDITION);
        assertThat(testContracts.getMinimumOrderSettlementCondition()).isEqualTo(UPDATED_MINIMUM_ORDER_SETTLEMENT_CONDITION);
        assertThat(testContracts.getOrderDeductionUnitPrice()).isEqualTo(UPDATED_ORDER_DEDUCTION_UNIT_PRICE);
        assertThat(testContracts.getOrderSurchargeUnitPrice()).isEqualTo(UPDATED_ORDER_SURCHARGE_UNIT_PRICE);
        assertThat(testContracts.getPurchaseUnitPrice()).isEqualTo(UPDATED_PURCHASE_UNIT_PRICE);
        assertThat(testContracts.getMaximumPurchaseSettlementCondition()).isEqualTo(UPDATED_MAXIMUM_PURCHASE_SETTLEMENT_CONDITION);
        assertThat(testContracts.getMinimumPurchaseSettlementCondition()).isEqualTo(UPDATED_MINIMUM_PURCHASE_SETTLEMENT_CONDITION);
        assertThat(testContracts.getPurchaseDeductionUnitPrice()).isEqualTo(UPDATED_PURCHASE_DEDUCTION_UNIT_PRICE);
        assertThat(testContracts.getPurchaseSurchargeUnitPrice()).isEqualTo(UPDATED_PURCHASE_SURCHARGE_UNIT_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingContracts() throws Exception {
        int databaseSizeBeforeUpdate = contractsRepository.findAll().size();
        contracts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contracts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contracts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContracts() throws Exception {
        int databaseSizeBeforeUpdate = contractsRepository.findAll().size();
        contracts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contracts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContracts() throws Exception {
        int databaseSizeBeforeUpdate = contractsRepository.findAll().size();
        contracts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contracts))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contracts in the database
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContracts() throws Exception {
        // Initialize the database
        contractsRepository.saveAndFlush(contracts);

        int databaseSizeBeforeDelete = contractsRepository.findAll().size();

        // Delete the contracts
        restContractsMockMvc
            .perform(delete(ENTITY_API_URL_ID, contracts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contracts> contractsList = contractsRepository.findAll();
        assertThat(contractsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
