package jp.ecn.sesweb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import jp.ecn.sesweb.domain.PartnerManager;
import jp.ecn.sesweb.repository.PartnerManagerRepository;
import jp.ecn.sesweb.web.rest.errors.BadRequestAlertException;
import jp.ecn.sesweb.web.rest.vm.MasterSearchConditionVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link jp.ecn.sesweb.domain.PartnerManager}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartnerManagerResource {

    private final Logger log = LoggerFactory.getLogger(PartnerManagerResource.class);

    private static final String ENTITY_NAME = "partnerManager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartnerManagerRepository partnerManagerRepository;

    public PartnerManagerResource(PartnerManagerRepository partnerManagerRepository) {
        this.partnerManagerRepository = partnerManagerRepository;
    }

    /**
     * {@code POST  /partner-managers} : Create a new partnerManager.
     *
     * @param partnerManager the partnerManager to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partnerManager, or with status {@code 400 (Bad Request)} if the partnerManager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partner-managers")
    public ResponseEntity<PartnerManager> createPartnerManager(@Valid @RequestBody PartnerManager partnerManager)
        throws URISyntaxException {
        log.debug("REST request to save PartnerManager : {}", partnerManager);
        if (partnerManager.getId() != null) {
            throw new BadRequestAlertException("A new partnerManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartnerManager result = partnerManagerRepository.save(partnerManager);
        return ResponseEntity
            .created(new URI("/api/partner-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /partner-managers/:id} : Updates an existing partnerManager.
     *
     * @param id the id of the partnerManager to save.
     * @param partnerManager the partnerManager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerManager,
     * or with status {@code 400 (Bad Request)} if the partnerManager is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partnerManager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partner-managers/{id}")
    public ResponseEntity<PartnerManager> updatePartnerManager(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PartnerManager partnerManager
    ) throws URISyntaxException {
        log.debug("REST request to update PartnerManager : {}, {}", id, partnerManager);
        if (partnerManager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partnerManager.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partnerManagerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PartnerManager result = partnerManagerRepository.save(partnerManager);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partnerManager.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /partner-managers/:id} : Partial updates given fields of an existing partnerManager, field will ignore if it is null
     *
     * @param id the id of the partnerManager to save.
     * @param partnerManager the partnerManager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partnerManager,
     * or with status {@code 400 (Bad Request)} if the partnerManager is not valid,
     * or with status {@code 404 (Not Found)} if the partnerManager is not found,
     * or with status {@code 500 (Internal Server Error)} if the partnerManager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/partner-managers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartnerManager> partialUpdatePartnerManager(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PartnerManager partnerManager
    ) throws URISyntaxException {
        log.debug("REST request to partial update PartnerManager partially : {}, {}", id, partnerManager);
        if (partnerManager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partnerManager.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partnerManagerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartnerManager> result = partnerManagerRepository
            .findById(partnerManager.getId())
            .map(existingPartnerManager -> {
                if (partnerManager.getName() != null) {
                    existingPartnerManager.setName(partnerManager.getName());
                }
                if (partnerManager.getCompanyName() != null) {
                    existingPartnerManager.setCompanyName(partnerManager.getCompanyName());
                }
                if (partnerManager.getEmail() != null) {
                    existingPartnerManager.setEmail(partnerManager.getEmail());
                }
                if (partnerManager.getPhoneNumber() != null) {
                    existingPartnerManager.setPhoneNumber(partnerManager.getPhoneNumber());
                }
                if (partnerManager.getWechatId() != null) {
                    existingPartnerManager.setWechatId(partnerManager.getWechatId());
                }

                return existingPartnerManager;
            })
            .map(partnerManagerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partnerManager.getId().toString())
        );
    }

    /**
     * {@code GET  /partner-managers} : get all the partnerManagers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partnerManagers in body.
     */
    @GetMapping("/partner-managers")
    public List<PartnerManager> getAllPartnerManagers() {
        log.debug("REST request to get all PartnerManagers");
        return partnerManagerRepository.findAll();
    }

    /**
     * {@code GET  /partner-managers} : get all the partnerManagers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partnerManagers in body.
     */
    @PostMapping("/select-partner-managers")
    public List<PartnerManager> selectPartnerManagers(@Valid @RequestBody MasterSearchConditionVm masterSearchConditionVm) {
        log.debug("REST request to select PartnerManagers parameter is " + masterSearchConditionVm.toString());
        if ("".equals(masterSearchConditionVm.getSearchValue())) {
            return partnerManagerRepository.findAll();
        }
        if ("name".equals(masterSearchConditionVm.getSearchField())) {
            return partnerManagerRepository.findByNameContaining(masterSearchConditionVm.getSearchValue());
        } else {
            return partnerManagerRepository.findByCompanyNameContaining(masterSearchConditionVm.getSearchValue());
        }
    }

    /**
     * {@code GET  /partner-managers/:id} : get the "id" partnerManager.
     *
     * @param id the id of the partnerManager to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partnerManager, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partner-managers/{id}")
    public ResponseEntity<PartnerManager> getPartnerManager(@PathVariable Long id) {
        log.debug("REST request to get PartnerManager : {}", id);
        Optional<PartnerManager> partnerManager = partnerManagerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partnerManager);
    }

    /**
     * {@code DELETE  /partner-managers/:id} : delete the "id" partnerManager.
     *
     * @param id the id of the partnerManager to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partner-managers/{id}")
    public ResponseEntity<Void> deletePartnerManager(@PathVariable Long id) {
        log.debug("REST request to delete PartnerManager : {}", id);
        partnerManagerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
