package jp.ecn.sesweb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import jp.ecn.sesweb.domain.Contracts;
import jp.ecn.sesweb.repository.ContractsRepository;
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
 * REST controller for managing {@link jp.ecn.sesweb.domain.Contracts}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContractsResource {

    private final Logger log = LoggerFactory.getLogger(ContractsResource.class);

    private static final String ENTITY_NAME = "contracts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractsRepository contractsRepository;

    public ContractsResource(ContractsRepository contractsRepository) {
        this.contractsRepository = contractsRepository;
    }

    /**
     * {@code POST  /contracts} : Create a new contracts.
     *
     * @param contracts the contracts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contracts, or with status {@code 400 (Bad Request)} if the contracts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contracts")
    public ResponseEntity<Contracts> createContracts(@Valid @RequestBody Contracts contracts) throws URISyntaxException {
        log.debug("REST request to save Contracts : {}", contracts);
        if (contracts.getId() != null) {
            throw new BadRequestAlertException("A new contracts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contracts result = contractsRepository.save(contracts);
        return ResponseEntity
            .created(new URI("/api/contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contracts/:id} : Updates an existing contracts.
     *
     * @param id the id of the contracts to save.
     * @param contracts the contracts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contracts,
     * or with status {@code 400 (Bad Request)} if the contracts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contracts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contracts/{id}")
    public ResponseEntity<Contracts> updateContracts(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Contracts contracts
    ) throws URISyntaxException {
        log.debug("REST request to update Contracts : {}, {}", id, contracts);
        if (contracts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contracts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contractsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Contracts result = contractsRepository.save(contracts);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contracts.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contracts/:id} : Partial updates given fields of an existing contracts, field will ignore if it is null
     *
     * @param id the id of the contracts to save.
     * @param contracts the contracts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contracts,
     * or with status {@code 400 (Bad Request)} if the contracts is not valid,
     * or with status {@code 404 (Not Found)} if the contracts is not found,
     * or with status {@code 500 (Internal Server Error)} if the contracts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contracts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Contracts> partialUpdateContracts(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Contracts contracts
    ) throws URISyntaxException {
        log.debug("REST request to partial update Contracts partially : {}, {}", id, contracts);
        if (contracts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contracts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contractsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Contracts> result = contractsRepository
            .findById(contracts.getId())
            .map(existingContracts -> {
                if (contracts.getLastName() != null) {
                    existingContracts.setLastName(contracts.getLastName());
                }
                if (contracts.getFirstName() != null) {
                    existingContracts.setFirstName(contracts.getFirstName());
                }
                if (contracts.getEcnId() != null) {
                    existingContracts.setEcnId(contracts.getEcnId());
                }
                if (contracts.getManager() != null) {
                    existingContracts.setManager(contracts.getManager());
                }
                if (contracts.getManagerEcnId() != null) {
                    existingContracts.setManagerEcnId(contracts.getManagerEcnId());
                }
                if (contracts.getContractStartDate() != null) {
                    existingContracts.setContractStartDate(contracts.getContractStartDate());
                }
                if (contracts.getContractEndDate() != null) {
                    existingContracts.setContractEndDate(contracts.getContractEndDate());
                }
                if (contracts.getOrderUnitPrice() != null) {
                    existingContracts.setOrderUnitPrice(contracts.getOrderUnitPrice());
                }
                if (contracts.getMaximumOrderSettlementCondition() != null) {
                    existingContracts.setMaximumOrderSettlementCondition(contracts.getMaximumOrderSettlementCondition());
                }
                if (contracts.getMinimumOrderSettlementCondition() != null) {
                    existingContracts.setMinimumOrderSettlementCondition(contracts.getMinimumOrderSettlementCondition());
                }
                if (contracts.getOrderDeductionUnitPrice() != null) {
                    existingContracts.setOrderDeductionUnitPrice(contracts.getOrderDeductionUnitPrice());
                }
                if (contracts.getOrderSurchargeUnitPrice() != null) {
                    existingContracts.setOrderSurchargeUnitPrice(contracts.getOrderSurchargeUnitPrice());
                }
                if (contracts.getPurchaseUnitPrice() != null) {
                    existingContracts.setPurchaseUnitPrice(contracts.getPurchaseUnitPrice());
                }
                if (contracts.getMaximumPurchaseSettlementCondition() != null) {
                    existingContracts.setMaximumPurchaseSettlementCondition(contracts.getMaximumPurchaseSettlementCondition());
                }
                if (contracts.getMinimumPurchaseSettlementCondition() != null) {
                    existingContracts.setMinimumPurchaseSettlementCondition(contracts.getMinimumPurchaseSettlementCondition());
                }
                if (contracts.getPurchaseDeductionUnitPrice() != null) {
                    existingContracts.setPurchaseDeductionUnitPrice(contracts.getPurchaseDeductionUnitPrice());
                }
                if (contracts.getPurchaseSurchargeUnitPrice() != null) {
                    existingContracts.setPurchaseSurchargeUnitPrice(contracts.getPurchaseSurchargeUnitPrice());
                }

                return existingContracts;
            })
            .map(contractsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contracts.getId().toString())
        );
    }

    /**
     * {@code GET  /contracts} : get all the contracts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contracts in body.
     */
    @GetMapping("/contracts")
    public List<Contracts> getAllContracts() {
        log.debug("REST request to get all Contracts");
        return contractsRepository.findAll();
    }

    /**
     * {@code GET  /contracts/:id} : get the "id" contracts.
     *
     * @param id the id of the contracts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contracts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contracts/{id}")
    public ResponseEntity<Contracts> getContracts(@PathVariable Long id) {
        log.debug("REST request to get Contracts : {}", id);
        Optional<Contracts> contracts = contractsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contracts);
    }

    /**
     * {@code DELETE  /contracts/:id} : delete the "id" contracts.
     *
     * @param id the id of the contracts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contracts/{id}")
    public ResponseEntity<Void> deleteContracts(@PathVariable Long id) {
        log.debug("REST request to delete Contracts : {}", id);
        contractsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
