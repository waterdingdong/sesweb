package jp.ecn.sesweb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jp.ecn.sesweb.domain.ContractPriceUpdateHistory;
import jp.ecn.sesweb.repository.ContractPriceUpdateHistoryRepository;
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
 * REST controller for managing {@link jp.ecn.sesweb.domain.ContractPriceUpdateHistory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContractPriceUpdateHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ContractPriceUpdateHistoryResource.class);

    private static final String ENTITY_NAME = "contractPriceUpdateHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractPriceUpdateHistoryRepository contractPriceUpdateHistoryRepository;

    public ContractPriceUpdateHistoryResource(ContractPriceUpdateHistoryRepository contractPriceUpdateHistoryRepository) {
        this.contractPriceUpdateHistoryRepository = contractPriceUpdateHistoryRepository;
    }

    /**
     * {@code POST  /contract-price-update-histories} : Create a new contractPriceUpdateHistory.
     *
     * @param contractPriceUpdateHistory the contractPriceUpdateHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractPriceUpdateHistory, or with status {@code 400 (Bad Request)} if the contractPriceUpdateHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contract-price-update-histories")
    public ResponseEntity<ContractPriceUpdateHistory> createContractPriceUpdateHistory(
        @RequestBody ContractPriceUpdateHistory contractPriceUpdateHistory
    ) throws URISyntaxException {
        log.debug("REST request to save ContractPriceUpdateHistory : {}", contractPriceUpdateHistory);
        if (contractPriceUpdateHistory.getId() != null) {
            throw new BadRequestAlertException("A new contractPriceUpdateHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractPriceUpdateHistory result = contractPriceUpdateHistoryRepository.save(contractPriceUpdateHistory);
        return ResponseEntity
            .created(new URI("/api/contract-price-update-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contract-price-update-histories/:id} : Updates an existing contractPriceUpdateHistory.
     *
     * @param id the id of the contractPriceUpdateHistory to save.
     * @param contractPriceUpdateHistory the contractPriceUpdateHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractPriceUpdateHistory,
     * or with status {@code 400 (Bad Request)} if the contractPriceUpdateHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractPriceUpdateHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contract-price-update-histories/{id}")
    public ResponseEntity<ContractPriceUpdateHistory> updateContractPriceUpdateHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContractPriceUpdateHistory contractPriceUpdateHistory
    ) throws URISyntaxException {
        log.debug("REST request to update ContractPriceUpdateHistory : {}, {}", id, contractPriceUpdateHistory);
        if (contractPriceUpdateHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contractPriceUpdateHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contractPriceUpdateHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContractPriceUpdateHistory result = contractPriceUpdateHistoryRepository.save(contractPriceUpdateHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contractPriceUpdateHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contract-price-update-histories/:id} : Partial updates given fields of an existing contractPriceUpdateHistory, field will ignore if it is null
     *
     * @param id the id of the contractPriceUpdateHistory to save.
     * @param contractPriceUpdateHistory the contractPriceUpdateHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractPriceUpdateHistory,
     * or with status {@code 400 (Bad Request)} if the contractPriceUpdateHistory is not valid,
     * or with status {@code 404 (Not Found)} if the contractPriceUpdateHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the contractPriceUpdateHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contract-price-update-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContractPriceUpdateHistory> partialUpdateContractPriceUpdateHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContractPriceUpdateHistory contractPriceUpdateHistory
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContractPriceUpdateHistory partially : {}, {}", id, contractPriceUpdateHistory);
        if (contractPriceUpdateHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contractPriceUpdateHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contractPriceUpdateHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContractPriceUpdateHistory> result = contractPriceUpdateHistoryRepository
            .findById(contractPriceUpdateHistory.getId())
            .map(existingContractPriceUpdateHistory -> {
                if (contractPriceUpdateHistory.getUpdateDate() != null) {
                    existingContractPriceUpdateHistory.setUpdateDate(contractPriceUpdateHistory.getUpdateDate());
                }
                if (contractPriceUpdateHistory.getBeforePrice() != null) {
                    existingContractPriceUpdateHistory.setBeforePrice(contractPriceUpdateHistory.getBeforePrice());
                }
                if (contractPriceUpdateHistory.getAfterPrice() != null) {
                    existingContractPriceUpdateHistory.setAfterPrice(contractPriceUpdateHistory.getAfterPrice());
                }

                return existingContractPriceUpdateHistory;
            })
            .map(contractPriceUpdateHistoryRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contractPriceUpdateHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /contract-price-update-histories} : get all the contractPriceUpdateHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractPriceUpdateHistories in body.
     */
    @GetMapping("/contract-price-update-histories")
    public List<ContractPriceUpdateHistory> getAllContractPriceUpdateHistories() {
        log.debug("REST request to get all ContractPriceUpdateHistories");
        return contractPriceUpdateHistoryRepository.findAll();
    }

    /**
     * {@code GET  /contract-price-update-histories/:id} : get the "id" contractPriceUpdateHistory.
     *
     * @param id the id of the contractPriceUpdateHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractPriceUpdateHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contract-price-update-histories/{id}")
    public ResponseEntity<ContractPriceUpdateHistory> getContractPriceUpdateHistory(@PathVariable Long id) {
        log.debug("REST request to get ContractPriceUpdateHistory : {}", id);
        Optional<ContractPriceUpdateHistory> contractPriceUpdateHistory = contractPriceUpdateHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contractPriceUpdateHistory);
    }

    /**
     * {@code DELETE  /contract-price-update-histories/:id} : delete the "id" contractPriceUpdateHistory.
     *
     * @param id the id of the contractPriceUpdateHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contract-price-update-histories/{id}")
    public ResponseEntity<Void> deleteContractPriceUpdateHistory(@PathVariable Long id) {
        log.debug("REST request to delete ContractPriceUpdateHistory : {}", id);
        contractPriceUpdateHistoryRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
