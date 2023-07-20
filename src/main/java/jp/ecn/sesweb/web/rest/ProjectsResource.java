package jp.ecn.sesweb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jp.ecn.sesweb.domain.Projects;
import jp.ecn.sesweb.repository.ProjectsRepository;
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
 * REST controller for managing {@link jp.ecn.sesweb.domain.Projects}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjectsResource {

    private final Logger log = LoggerFactory.getLogger(ProjectsResource.class);

    private static final String ENTITY_NAME = "projects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectsRepository projectsRepository;

    public ProjectsResource(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    /**
     * {@code POST  /projects} : Create a new projects.
     *
     * @param projects the projects to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projects, or with status {@code 400 (Bad Request)} if the projects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projects")
    public ResponseEntity<Projects> createProjects(@RequestBody Projects projects) throws URISyntaxException {
        log.debug("REST request to save Projects : {}", projects);
        if (projects.getId() != null) {
            throw new BadRequestAlertException("A new projects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Projects result = projectsRepository.save(projects);
        return ResponseEntity
            .created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projects/:id} : Updates an existing projects.
     *
     * @param id the id of the projects to save.
     * @param projects the projects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projects,
     * or with status {@code 400 (Bad Request)} if the projects is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projects/{id}")
    public ResponseEntity<Projects> updateProjects(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Projects projects
    ) throws URISyntaxException {
        log.debug("REST request to update Projects : {}, {}", id, projects);
        if (projects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projects.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Projects result = projectsRepository.save(projects);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projects.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /projects/:id} : Partial updates given fields of an existing projects, field will ignore if it is null
     *
     * @param id the id of the projects to save.
     * @param projects the projects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projects,
     * or with status {@code 400 (Bad Request)} if the projects is not valid,
     * or with status {@code 404 (Not Found)} if the projects is not found,
     * or with status {@code 500 (Internal Server Error)} if the projects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/projects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Projects> partialUpdateProjects(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Projects projects
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projects partially : {}, {}", id, projects);
        if (projects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projects.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Projects> result = projectsRepository
            .findById(projects.getId())
            .map(existingProjects -> {
                if (projects.getName() != null) {
                    existingProjects.setName(projects.getName());
                }

                return existingProjects;
            })
            .map(projectsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projects.getId().toString())
        );
    }

    /**
     * {@code GET  /projects} : get all the projects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projects in body.
     */
    @GetMapping("/projects")
    public List<Projects> getAllProjects() {
        log.debug("REST request to get all Projects");
        return projectsRepository.findAll();
    }

    /**
     * {@code GET  /projects/:id} : get the "id" projects.
     *
     * @param id the id of the projects to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projects, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projects/{id}")
    public ResponseEntity<Projects> getProjects(@PathVariable Long id) {
        log.debug("REST request to get Projects : {}", id);
        Optional<Projects> projects = projectsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projects);
    }

    /**
     * {@code DELETE  /projects/:id} : delete the "id" projects.
     *
     * @param id the id of the projects to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProjects(@PathVariable Long id) {
        log.debug("REST request to delete Projects : {}", id);
        projectsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
