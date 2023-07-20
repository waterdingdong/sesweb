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
import jp.ecn.sesweb.domain.CustomerManager;
import jp.ecn.sesweb.repository.CustomerManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CustomerManagerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerManagerResourceIT {

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/customer-managers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerManagerRepository customerManagerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerManagerMockMvc;

    private CustomerManager customerManager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerManager createEntity(EntityManager em) {
        CustomerManager customerManager = new CustomerManager()
            .customerName(DEFAULT_CUSTOMER_NAME)
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phoneName(DEFAULT_PHONE_NAME);
        return customerManager;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerManager createUpdatedEntity(EntityManager em) {
        CustomerManager customerManager = new CustomerManager()
            .customerName(UPDATED_CUSTOMER_NAME)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneName(UPDATED_PHONE_NAME);
        return customerManager;
    }

    @BeforeEach
    public void initTest() {
        customerManager = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomerManager() throws Exception {
        int databaseSizeBeforeCreate = customerManagerRepository.findAll().size();
        // Create the CustomerManager
        restCustomerManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerManager))
            )
            .andExpect(status().isCreated());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerManager testCustomerManager = customerManagerList.get(customerManagerList.size() - 1);
        assertThat(testCustomerManager.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testCustomerManager.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerManager.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerManager.getPhoneName()).isEqualTo(DEFAULT_PHONE_NAME);
    }

    @Test
    @Transactional
    void createCustomerManagerWithExistingId() throws Exception {
        // Create the CustomerManager with an existing ID
        customerManager.setId(1L);

        int databaseSizeBeforeCreate = customerManagerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerManager))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustomerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerManagerRepository.findAll().size();
        // set the field null
        customerManager.setCustomerName(null);

        // Create the CustomerManager, which fails.

        restCustomerManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerManager))
            )
            .andExpect(status().isBadRequest());

        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerManagerRepository.findAll().size();
        // set the field null
        customerManager.setName(null);

        // Create the CustomerManager, which fails.

        restCustomerManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerManager))
            )
            .andExpect(status().isBadRequest());

        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomerManagers() throws Exception {
        // Initialize the database
        customerManagerRepository.saveAndFlush(customerManager);

        // Get all the customerManagerList
        restCustomerManagerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneName").value(hasItem(DEFAULT_PHONE_NAME)));
    }

    @Test
    @Transactional
    void getCustomerManager() throws Exception {
        // Initialize the database
        customerManagerRepository.saveAndFlush(customerManager);

        // Get the customerManager
        restCustomerManagerMockMvc
            .perform(get(ENTITY_API_URL_ID, customerManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerManager.getId().intValue()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneName").value(DEFAULT_PHONE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCustomerManager() throws Exception {
        // Get the customerManager
        restCustomerManagerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCustomerManager() throws Exception {
        // Initialize the database
        customerManagerRepository.saveAndFlush(customerManager);

        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();

        // Update the customerManager
        CustomerManager updatedCustomerManager = customerManagerRepository.findById(customerManager.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerManager are not directly saved in db
        em.detach(updatedCustomerManager);
        updatedCustomerManager.customerName(UPDATED_CUSTOMER_NAME).name(UPDATED_NAME).email(UPDATED_EMAIL).phoneName(UPDATED_PHONE_NAME);

        restCustomerManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomerManager.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomerManager))
            )
            .andExpect(status().isOk());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
        CustomerManager testCustomerManager = customerManagerList.get(customerManagerList.size() - 1);
        assertThat(testCustomerManager.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testCustomerManager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerManager.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerManager.getPhoneName()).isEqualTo(UPDATED_PHONE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCustomerManager() throws Exception {
        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();
        customerManager.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerManager.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerManager))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomerManager() throws Exception {
        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();
        customerManager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerManager))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomerManager() throws Exception {
        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();
        customerManager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerManagerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerManager))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerManagerWithPatch() throws Exception {
        // Initialize the database
        customerManagerRepository.saveAndFlush(customerManager);

        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();

        // Update the customerManager using partial update
        CustomerManager partialUpdatedCustomerManager = new CustomerManager();
        partialUpdatedCustomerManager.setId(customerManager.getId());

        partialUpdatedCustomerManager.customerName(UPDATED_CUSTOMER_NAME).name(UPDATED_NAME).email(UPDATED_EMAIL);

        restCustomerManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerManager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerManager))
            )
            .andExpect(status().isOk());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
        CustomerManager testCustomerManager = customerManagerList.get(customerManagerList.size() - 1);
        assertThat(testCustomerManager.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testCustomerManager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerManager.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerManager.getPhoneName()).isEqualTo(DEFAULT_PHONE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCustomerManagerWithPatch() throws Exception {
        // Initialize the database
        customerManagerRepository.saveAndFlush(customerManager);

        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();

        // Update the customerManager using partial update
        CustomerManager partialUpdatedCustomerManager = new CustomerManager();
        partialUpdatedCustomerManager.setId(customerManager.getId());

        partialUpdatedCustomerManager
            .customerName(UPDATED_CUSTOMER_NAME)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneName(UPDATED_PHONE_NAME);

        restCustomerManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerManager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerManager))
            )
            .andExpect(status().isOk());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
        CustomerManager testCustomerManager = customerManagerList.get(customerManagerList.size() - 1);
        assertThat(testCustomerManager.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testCustomerManager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerManager.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerManager.getPhoneName()).isEqualTo(UPDATED_PHONE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCustomerManager() throws Exception {
        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();
        customerManager.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerManager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerManager))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomerManager() throws Exception {
        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();
        customerManager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerManager))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomerManager() throws Exception {
        int databaseSizeBeforeUpdate = customerManagerRepository.findAll().size();
        customerManager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerManagerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerManager))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerManager in the database
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomerManager() throws Exception {
        // Initialize the database
        customerManagerRepository.saveAndFlush(customerManager);

        int databaseSizeBeforeDelete = customerManagerRepository.findAll().size();

        // Delete the customerManager
        restCustomerManagerMockMvc
            .perform(delete(ENTITY_API_URL_ID, customerManager.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerManager> customerManagerList = customerManagerRepository.findAll();
        assertThat(customerManagerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
