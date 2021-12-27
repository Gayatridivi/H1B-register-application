package com.flashadeal.h1b.web.rest;

import com.flashadeal.h1b.domain.LoginProfile;
import com.flashadeal.h1b.repository.LoginProfileRepository;
import com.flashadeal.h1b.service.LoginProfileQueryService;
import com.flashadeal.h1b.service.LoginProfileService;
import com.flashadeal.h1b.service.criteria.LoginProfileCriteria;
import com.flashadeal.h1b.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.flashadeal.h1b.domain.LoginProfile}.
 */
@RestController
@RequestMapping("/api")
public class LoginProfileResource {

    private final Logger log = LoggerFactory.getLogger(LoginProfileResource.class);

    private static final String ENTITY_NAME = "loginProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoginProfileService loginProfileService;

    private final LoginProfileRepository loginProfileRepository;

    private final LoginProfileQueryService loginProfileQueryService;

    public LoginProfileResource(
        LoginProfileService loginProfileService,
        LoginProfileRepository loginProfileRepository,
        LoginProfileQueryService loginProfileQueryService
    ) {
        this.loginProfileService = loginProfileService;
        this.loginProfileRepository = loginProfileRepository;
        this.loginProfileQueryService = loginProfileQueryService;
    }

    /**
     * {@code POST  /login-profiles} : Create a new loginProfile.
     *
     * @param loginProfile the loginProfile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loginProfile, or with status {@code 400 (Bad Request)} if the loginProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/login-profiles")
    public ResponseEntity<LoginProfile> createLoginProfile(@RequestBody LoginProfile loginProfile) throws URISyntaxException {
        log.debug("REST request to save LoginProfile : {}", loginProfile);
        if (loginProfile.getId() != null) {
            throw new BadRequestAlertException("A new loginProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoginProfile result = loginProfileService.save(loginProfile);
        return ResponseEntity
            .created(new URI("/api/login-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /login-profiles/:id} : Updates an existing loginProfile.
     *
     * @param id the id of the loginProfile to save.
     * @param loginProfile the loginProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loginProfile,
     * or with status {@code 400 (Bad Request)} if the loginProfile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loginProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/login-profiles/{id}")
    public ResponseEntity<LoginProfile> updateLoginProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoginProfile loginProfile
    ) throws URISyntaxException {
        log.debug("REST request to update LoginProfile : {}, {}", id, loginProfile);
        if (loginProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loginProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loginProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoginProfile result = loginProfileService.save(loginProfile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loginProfile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /login-profiles/:id} : Partial updates given fields of an existing loginProfile, field will ignore if it is null
     *
     * @param id the id of the loginProfile to save.
     * @param loginProfile the loginProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loginProfile,
     * or with status {@code 400 (Bad Request)} if the loginProfile is not valid,
     * or with status {@code 404 (Not Found)} if the loginProfile is not found,
     * or with status {@code 500 (Internal Server Error)} if the loginProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/login-profiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoginProfile> partialUpdateLoginProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoginProfile loginProfile
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoginProfile partially : {}, {}", id, loginProfile);
        if (loginProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loginProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loginProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoginProfile> result = loginProfileService.partialUpdate(loginProfile);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loginProfile.getId().toString())
        );
    }

    /**
     * {@code GET  /login-profiles} : get all the loginProfiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loginProfiles in body.
     */
    @GetMapping("/login-profiles")
    public ResponseEntity<List<LoginProfile>> getAllLoginProfiles(LoginProfileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LoginProfiles by criteria: {}", criteria);
        Page<LoginProfile> page = loginProfileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /login-profiles/count} : count all the loginProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/login-profiles/count")
    public ResponseEntity<Long> countLoginProfiles(LoginProfileCriteria criteria) {
        log.debug("REST request to count LoginProfiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(loginProfileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /login-profiles/:id} : get the "id" loginProfile.
     *
     * @param id the id of the loginProfile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loginProfile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/login-profiles/{id}")
    public ResponseEntity<LoginProfile> getLoginProfile(@PathVariable Long id) {
        log.debug("REST request to get LoginProfile : {}", id);
        Optional<LoginProfile> loginProfile = loginProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loginProfile);
    }

    /**
     * {@code DELETE  /login-profiles/:id} : delete the "id" loginProfile.
     *
     * @param id the id of the loginProfile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/login-profiles/{id}")
    public ResponseEntity<Void> deleteLoginProfile(@PathVariable Long id) {
        log.debug("REST request to delete LoginProfile : {}", id);
        loginProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
