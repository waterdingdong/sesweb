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
import jp.ecn.sesweb.domain.PartnerManager;
import jp.ecn.sesweb.repository.PartnerManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartnerManagerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartnerManagerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "\\d@iG773b.c&78";
    private static final String UPDATED_EMAIL = "Qm0@X.<o9[U";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/partner-managers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartnerManagerRepository partnerManagerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartnerManagerMockMvc;

    private PartnerManager partnerManager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerManager createEntity(EntityManager em) {
        PartnerManager partnerManager = new PartnerManager()
            .name(DEFAULT_NAME)
            .companyName(DEFAULT_COMPANY_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .wechatId(DEFAULT_WECHAT_ID);
        return partnerManager;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerManager createUpdatedEntity(EntityManager em) {
        PartnerManager partnerManager = new PartnerManager()
            .name(UPDATED_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .wechatId(UPDATED_WECHAT_ID);
        return partnerManager;
    }

    @BeforeEach
    public void initTest() {
        partnerManager = createEntity(em);
    }

    @Test
    @Transactional
    void createPartnerManager() throws Exception {
        int databaseSizeBeforeCreate = partnerManagerRepository.findAll().size();
        // Create the PartnerManager
        restPartnerManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partnerManager))
            )
            .andExpect(status().isCreated());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeCreate + 1);
        PartnerManager testPartnerManager = partnerManagerList.get(partnerManagerList.size() - 1);
        assertThat(testPartnerManager.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPartnerManager.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testPartnerManager.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPartnerManager.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPartnerManager.getWechatId()).isEqualTo(DEFAULT_WECHAT_ID);
    }

    @Test
    @Transactional
    void createPartnerManagerWithExistingId() throws Exception {
        // Create the PartnerManager with an existing ID
        partnerManager.setId(1L);

        int databaseSizeBeforeCreate = partnerManagerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partnerManager))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerManagerRepository.findAll().size();
        // set the field null
        partnerManager.setName(null);

        // Create the PartnerManager, which fails.

        restPartnerManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partnerManager))
            )
            .andExpect(status().isBadRequest());

        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerManagerRepository.findAll().size();
        // set the field null
        partnerManager.setCompanyName(null);

        // Create the PartnerManager, which fails.

        restPartnerManagerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partnerManager))
            )
            .andExpect(status().isBadRequest());

        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPartnerManagers() throws Exception {
        // Initialize the database
        partnerManagerRepository.saveAndFlush(partnerManager);

        // Get all the partnerManagerList
        restPartnerManagerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].wechatId").value(hasItem(DEFAULT_WECHAT_ID)));
    }

    @Test
    @Transactional
    void getPartnerManager() throws Exception {
        // Initialize the database
        partnerManagerRepository.saveAndFlush(partnerManager);

        // Get the partnerManager
        restPartnerManagerMockMvc
            .perform(get(ENTITY_API_URL_ID, partnerManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partnerManager.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.wechatId").value(DEFAULT_WECHAT_ID));
    }

    @Test
    @Transactional
    void getNonExistingPartnerManager() throws Exception {
        // Get the partnerManager
        restPartnerManagerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPartnerManager() throws Exception {
        // Initialize the database
        partnerManagerRepository.saveAndFlush(partnerManager);

        int databaseSizeBeforeUpdate = partnerManagerRepository.findAll().size();

        // Update the partnerManager
        PartnerManager updatedPartnerManager = partnerManagerRepository.findById(partnerManager.getId()).get();
        // Disconnect from session so that the updates on updatedPartnerManager are not directly saved in db
        em.detach(updatedPartnerManager);
        updatedPartnerManager
            .name(UPDATED_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .wechatId(UPDATED_WECHAT_ID);

        restPartnerManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartnerManager.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPartnerManager))
            )
            .andExpect(status().isOk());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeUpdate);
        PartnerManager testPartnerManager = partnerManagerList.get(partnerManagerList.size() - 1);
        assertThat(testPartnerManager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPartnerManager.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testPartnerManager.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPartnerManager.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPartnerManager.getWechatId()).isEqualTo(UPDATED_WECHAT_ID);
    }

    @Test
    @Transactional
    void putNonExistingPartnerManager() throws Exception {
        int databaseSizeBeforeUpdate = partnerManagerRepository.findAll().size();
        partnerManager.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partnerManager.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partnerManager))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartnerManager() throws Exception {
        int databaseSizeBeforeUpdate = partnerManagerRepository.findAll().size();
        partnerManager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partnerManager))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartnerManager() throws Exception {
        int databaseSizeBeforeUpdate = partnerManagerRepository.findAll().size();
        partnerManager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerManagerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partnerManager)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartnerManagerWithPatch() throws Exception {
        // Initialize the database
        partnerManagerRepository.saveAndFlush(partnerManager);

        int databaseSizeBeforeUpdate = partnerManagerRepository.findAll().size();

        // Update the partnerManager using partial update
        PartnerManager partialUpdatedPartnerManager = new PartnerManager();
        partialUpdatedPartnerManager.setId(partnerManager.getId());

        partialUpdatedPartnerManager
            .companyName(UPDATED_COMPANY_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .wechatId(UPDATED_WECHAT_ID);

        restPartnerManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartnerManager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartnerManager))
            )
            .andExpect(status().isOk());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeUpdate);
        PartnerManager testPartnerManager = partnerManagerList.get(partnerManagerList.size() - 1);
        assertThat(testPartnerManager.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPartnerManager.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testPartnerManager.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPartnerManager.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPartnerManager.getWechatId()).isEqualTo(UPDATED_WECHAT_ID);
    }

    @Test
    @Transactional
    void fullUpdatePartnerManagerWithPatch() throws Exception {
        // Initialize the database
        partnerManagerRepository.saveAndFlush(partnerManager);

        int databaseSizeBeforeUpdate = partnerManagerRepository.findAll().size();

        // Update the partnerManager using partial update
        PartnerManager partialUpdatedPartnerManager = new PartnerManager();
        partialUpdatedPartnerManager.setId(partnerManager.getId());

        partialUpdatedPartnerManager
            .name(UPDATED_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .wechatId(UPDATED_WECHAT_ID);

        restPartnerManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartnerManager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartnerManager))
            )
            .andExpect(status().isOk());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeUpdate);
        PartnerManager testPartnerManager = partnerManagerList.get(partnerManagerList.size() - 1);
        assertThat(testPartnerManager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPartnerManager.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testPartnerManager.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPartnerManager.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPartnerManager.getWechatId()).isEqualTo(UPDATED_WECHAT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingPartnerManager() throws Exception {
        int databaseSizeBeforeUpdate = partnerManagerRepository.findAll().size();
        partnerManager.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partnerManager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partnerManager))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartnerManager() throws Exception {
        int databaseSizeBeforeUpdate = partnerManagerRepository.findAll().size();
        partnerManager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partnerManager))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartnerManager() throws Exception {
        int databaseSizeBeforeUpdate = partnerManagerRepository.findAll().size();
        partnerManager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerManagerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partnerManager))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartnerManager in the database
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartnerManager() throws Exception {
        // Initialize the database
        partnerManagerRepository.saveAndFlush(partnerManager);

        int databaseSizeBeforeDelete = partnerManagerRepository.findAll().size();

        // Delete the partnerManager
        restPartnerManagerMockMvc
            .perform(delete(ENTITY_API_URL_ID, partnerManager.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartnerManager> partnerManagerList = partnerManagerRepository.findAll();
        assertThat(partnerManagerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
