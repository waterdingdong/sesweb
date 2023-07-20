package jp.ecn.sesweb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jp.ecn.sesweb.domain.SalesAmount;
import jp.ecn.sesweb.repository.SalesAmountRepository;
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
 * REST controller for managing {@link jp.ecn.sesweb.domain.SalesAmount}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SalesAmountResource {

    private final Logger log = LoggerFactory.getLogger(SalesAmountResource.class);

    private static final String ENTITY_NAME = "salesAmount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesAmountRepository salesAmountRepository;

    public SalesAmountResource(SalesAmountRepository salesAmountRepository) {
        this.salesAmountRepository = salesAmountRepository;
    }

    /**
     * {@code POST  /sales-amounts} : Create a new salesAmount.
     *
     * @param salesAmount the salesAmount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesAmount, or with status {@code 400 (Bad Request)} if the salesAmount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales-amounts")
    public ResponseEntity<SalesAmount> createSalesAmount(@RequestBody SalesAmount salesAmount) throws URISyntaxException {
        log.debug("REST request to save SalesAmount : {}", salesAmount);
        if (salesAmount.getId() != null) {
            throw new BadRequestAlertException("A new salesAmount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesAmount result = salesAmountRepository.save(salesAmount);
        return ResponseEntity
            .created(new URI("/api/sales-amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-amounts/:id} : Updates an existing salesAmount.
     *
     * @param id the id of the salesAmount to save.
     * @param salesAmount the salesAmount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesAmount,
     * or with status {@code 400 (Bad Request)} if the salesAmount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesAmount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales-amounts/{id}")
    public ResponseEntity<SalesAmount> updateSalesAmount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesAmount salesAmount
    ) throws URISyntaxException {
        log.debug("REST request to update SalesAmount : {}, {}", id, salesAmount);
        if (salesAmount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesAmount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesAmountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SalesAmount result = salesAmountRepository.save(salesAmount);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesAmount.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sales-amounts/:id} : Partial updates given fields of an existing salesAmount, field will ignore if it is null
     *
     * @param id the id of the salesAmount to save.
     * @param salesAmount the salesAmount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesAmount,
     * or with status {@code 400 (Bad Request)} if the salesAmount is not valid,
     * or with status {@code 404 (Not Found)} if the salesAmount is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesAmount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sales-amounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesAmount> partialUpdateSalesAmount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesAmount salesAmount
    ) throws URISyntaxException {
        log.debug("REST request to partial update SalesAmount partially : {}, {}", id, salesAmount);
        if (salesAmount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesAmount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesAmountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesAmount> result = salesAmountRepository
            .findById(salesAmount.getId())
            .map(existingSalesAmount -> {
                if (salesAmount.getSalesYm() != null) {
                    existingSalesAmount.setSalesYm(salesAmount.getSalesYm());
                }
                if (salesAmount.getWorkTime() != null) {
                    existingSalesAmount.setWorkTime(salesAmount.getWorkTime());
                }
                if (salesAmount.getBillingAmount() != null) {
                    existingSalesAmount.setBillingAmount(salesAmount.getBillingAmount());
                }

                return existingSalesAmount;
            })
            .map(salesAmountRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesAmount.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-amounts} : get all the salesAmounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesAmounts in body.
     */
    @GetMapping("/sales-amounts")
    public List<SalesAmount> getAllSalesAmounts() {
        log.debug("REST request to get all SalesAmounts");
        return salesAmountRepository.findAll();
    }

    /**
     * {@code GET  /sales-amounts/:id} : get the "id" salesAmount.
     *
     * @param id the id of the salesAmount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesAmount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales-amounts/{id}")
    public ResponseEntity<SalesAmount> getSalesAmount(@PathVariable Long id) {
        log.debug("REST request to get SalesAmount : {}", id);
        Optional<SalesAmount> salesAmount = salesAmountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(salesAmount);
    }

    /**
     * {@code DELETE  /sales-amounts/:id} : delete the "id" salesAmount.
     *
     * @param id the id of the salesAmount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales-amounts/{id}")
    public ResponseEntity<Void> deleteSalesAmount(@PathVariable Long id) {
        log.debug("REST request to delete SalesAmount : {}", id);
        salesAmountRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
