package jp.ecn.sesweb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import jp.ecn.sesweb.domain.CustomerManager;
import jp.ecn.sesweb.repository.CustomerManagerRepository;
import jp.ecn.sesweb.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link jp.ecn.sesweb.domain.CustomerManager}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerManagerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerManagerResource.class);

    private static final String ENTITY_NAME = "customerManager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerManagerRepository customerManagerRepository;

    public CustomerManagerResource(CustomerManagerRepository customerManagerRepository) {
        this.customerManagerRepository = customerManagerRepository;
    }

    /**
     * {@code POST  /customer-managers} : Create a new customerManager.
     *
     * @param customerManager the customerManager to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerManager, or with status {@code 400 (Bad Request)} if the customerManager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-managers")
    public ResponseEntity<CustomerManager> createCustomerManager(@Valid @RequestBody CustomerManager customerManager)
        throws URISyntaxException {
        log.debug("REST request to save CustomerManager : {}", customerManager);
        if (customerManager.getId() != null) {
            throw new BadRequestAlertException("A new customerManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerManager result = customerManagerRepository.save(customerManager);
        return ResponseEntity
            .created(new URI("/api/customer-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-managers/:id} : Updates an existing customerManager.
     *
     * @param id the id of the customerManager to save.
     * @param customerManager the customerManager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerManager,
     * or with status {@code 400 (Bad Request)} if the customerManager is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerManager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-managers/{id}")
    public ResponseEntity<CustomerManager> updateCustomerManager(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomerManager customerManager
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerManager : {}, {}", id, customerManager);
        if (customerManager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerManager.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerManagerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerManager result = customerManagerRepository.save(customerManager);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerManager.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customer-managers/:id} : Partial updates given fields of an existing customerManager, field will ignore if it is null
     *
     * @param id the id of the customerManager to save.
     * @param customerManager the customerManager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerManager,
     * or with status {@code 400 (Bad Request)} if the customerManager is not valid,
     * or with status {@code 404 (Not Found)} if the customerManager is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerManager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-managers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomerManager> partialUpdateCustomerManager(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomerManager customerManager
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerManager partially : {}, {}", id, customerManager);
        if (customerManager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerManager.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerManagerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerManager> result = customerManagerRepository
            .findById(customerManager.getId())
            .map(existingCustomerManager -> {
                if (customerManager.getCustomerName() != null) {
                    existingCustomerManager.setCustomerName(customerManager.getCustomerName());
                }
                if (customerManager.getName() != null) {
                    existingCustomerManager.setName(customerManager.getName());
                }
                if (customerManager.getEmail() != null) {
                    existingCustomerManager.setEmail(customerManager.getEmail());
                }
                if (customerManager.getPhoneName() != null) {
                    existingCustomerManager.setPhoneName(customerManager.getPhoneName());
                }

                return existingCustomerManager;
            })
            .map(customerManagerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerManager.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-managers} : get all the customerManagers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerManagers in body.
     */
    @GetMapping("/customer-managers")
    public List<CustomerManager> getAllCustomerManagers() {
        log.debug("REST request to get all CustomerManagers");
        return customerManagerRepository.findAll();
    }

    /**
     * {@code GET  /customer-managers/:id} : get the "id" customerManager.
     *
     * @param id the id of the customerManager to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerManager, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-managers/{id}")
    public ResponseEntity<CustomerManager> getCustomerManager(@PathVariable Long id) {
        log.debug("REST request to get CustomerManager : {}", id);
        Optional<CustomerManager> customerManager = customerManagerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerManager);
    }

    /**
     * {@code DELETE  /customer-managers/:id} : delete the "id" customerManager.
     *
     * @param id the id of the customerManager to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-managers/{id}")
    public ResponseEntity<Void> deleteCustomerManager(@PathVariable Long id) {
        log.debug("REST request to delete CustomerManager : {}", id);
        customerManagerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
