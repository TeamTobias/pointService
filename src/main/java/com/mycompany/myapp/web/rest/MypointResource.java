package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.MypointRepository;
import com.mycompany.myapp.service.MypointService;
import com.mycompany.myapp.service.dto.MypointDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Mypoint}.
 */
@RestController
@RequestMapping("/api")
public class MypointResource {

    private final Logger log = LoggerFactory.getLogger(MypointResource.class);

    private static final String ENTITY_NAME = "pointService2Mypoint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MypointService mypointService;

    private final MypointRepository mypointRepository;

    public MypointResource(MypointService mypointService, MypointRepository mypointRepository) {
        this.mypointService = mypointService;
        this.mypointRepository = mypointRepository;
    }

    /**
     * {@code POST  /mypoints} : Create a new mypoint.
     *
     * @param mypointDTO the mypointDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mypointDTO, or with status {@code 400 (Bad Request)} if the mypoint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mypoints")
    public ResponseEntity<MypointDTO> createMypoint(@RequestBody MypointDTO mypointDTO) throws URISyntaxException {
        log.debug("REST request to save Mypoint : {}", mypointDTO);
        if (mypointDTO.getId() != null) {
            throw new BadRequestAlertException("A new mypoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MypointDTO result = mypointService.save(mypointDTO);
        return ResponseEntity
            .created(new URI("/api/mypoints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /mypoints/:id} : Updates an existing mypoint.
     *
     * @param id the id of the mypointDTO to save.
     * @param mypointDTO the mypointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mypointDTO,
     * or with status {@code 400 (Bad Request)} if the mypointDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mypointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mypoints/{id}")
    public ResponseEntity<MypointDTO> updateMypoint(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody MypointDTO mypointDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Mypoint : {}, {}", id, mypointDTO);
        if (mypointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mypointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mypointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MypointDTO result = mypointService.update(mypointDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mypointDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /mypoints/:id} : Partial updates given fields of an existing mypoint, field will ignore if it is null
     *
     * @param id the id of the mypointDTO to save.
     * @param mypointDTO the mypointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mypointDTO,
     * or with status {@code 400 (Bad Request)} if the mypointDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mypointDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mypointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mypoints/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MypointDTO> partialUpdateMypoint(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody MypointDTO mypointDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mypoint partially : {}, {}", id, mypointDTO);
        if (mypointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mypointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mypointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MypointDTO> result = mypointService.partialUpdate(mypointDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mypointDTO.getId())
        );
    }

    /**
     * {@code GET  /mypoints} : get all the mypoints.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mypoints in body.
     */
    @GetMapping("/mypoints")
    public ResponseEntity<List<MypointDTO>> getAllMypoints(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Mypoints");
        Page<MypointDTO> page = mypointService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mypoints/:id} : get the "id" mypoint.
     *
     * @param id the id of the mypointDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mypointDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mypoints/{id}")
    public ResponseEntity<MypointDTO> getMypoint(@PathVariable String id) {
        log.debug("REST request to get Mypoint : {}", id);
        Optional<MypointDTO> mypointDTO = mypointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mypointDTO);
    }

    /**
     * {@code DELETE  /mypoints/:id} : delete the "id" mypoint.
     *
     * @param id the id of the mypointDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mypoints/{id}")
    public ResponseEntity<Void> deleteMypoint(@PathVariable String id) {
        log.debug("REST request to delete Mypoint : {}", id);
        mypointService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /*내 포인트 조회*/
    @GetMapping("/mypoints/all/{userid}")
    public ResponseEntity<Iterable<MypointDTO>> getMypointbyUserid(@PathVariable String userid) {
        log.debug("REST request to get Mypoint : {}", userid);
        Iterable<MypointDTO> mypointDTO = mypointService.findByUserid(userid);

        // Optional to list
        return ResponseEntity.status(HttpStatus.OK).body(mypointDTO);
    }
}
