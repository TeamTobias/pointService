package com.mycompany.myapp.service.rest;

import com.mycompany.myapp.repository.MypointRepository;
import com.mycompany.myapp.service.MypointService;
import com.mycompany.myapp.service.dto.MypointDTO;
import com.mycompany.myapp.service.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
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

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Mypoint}.
 */
@RestController
@RequestMapping("/mypoints")
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

    /*포인트 적립*/
    @PostMapping("/v1")
    public ResponseEntity<MypointDTO> createMypoint(@RequestBody MypointDTO mypointDTO) throws URISyntaxException {
        log.debug("REST request to save Mypoint : {}", mypointDTO);
        if (mypointDTO.getUserid() != null) throw new BadRequestAlertException(
            "A new mypoint cannot already have an ID",
            ENTITY_NAME,
            "idexists"
        );
        MypointDTO result = mypointService.save(mypointDTO);
        return ResponseEntity
            .created(new URI("/api/mypoints/" + result.getUserid()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getUserid()))
            .body(result);
    }

    /*내 포인트 조회*/
    @GetMapping("/v1/{userid}")
    public ResponseEntity<Iterable<MypointDTO>> getMypointbyUserid(@PathVariable String userid) {
        log.debug("REST request to get Mypoint : {}", userid);
        Iterable<MypointDTO> mypointDTO = mypointService.findByUserid(userid);

        return ResponseEntity.status(HttpStatus.OK).body(mypointDTO);
    }

    @PutMapping("/v1/{id}")
    public ResponseEntity<MypointDTO> updateMypoint(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody MypointDTO mypointDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Mypoint : {}, {}", id, mypointDTO);
        if (mypointDTO.getUserid() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");

        if (!Objects.equals(id, mypointDTO.getUserid())) throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");

        if (!mypointRepository.existsById(id)) throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");

        MypointDTO result = mypointService.update(mypointDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mypointDTO.getUserid()))
            .body(result);
    }

    //    /**
    //     * {@code PATCH  /mypoints/:id} : Partial updates given fields of an existing mypoint, field will ignore if it is null
    //     *
    //     * @param id the id of the mypointDTO to save.
    //     * @param mypointDTO the mypointDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mypointDTO,
    //     * or with status {@code 400 (Bad Request)} if the mypointDTO is not valid,
    //     * or with status {@code 404 (Not Found)} if the mypointDTO is not found,
    //     * or with status {@code 500 (Internal Server Error)} if the mypointDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PatchMapping(value = "/v1/{id}", consumes = { "application/json", "application/merge-patch+json" })
    //    public ResponseEntity<MypointDTO> partialUpdateMypoint(
    //        @PathVariable(value = "id", required = false) final String id,
    //        @RequestBody MypointDTO mypointDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to partial update Mypoint partially : {}, {}", id, mypointDTO);
    //        if (mypointDTO.getId() == null) {
    //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, mypointDTO.getId())) {
    //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!mypointRepository.existsById(id)) {
    //            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        Optional<MypointDTO> result = mypointService.partialUpdate(mypointDTO);
    //
    //        return ResponseUtil.wrapOrNotFound(
    //            result,
    //            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mypointDTO.getId())
    //        );
    //    }

    @GetMapping("/v1")
    public ResponseEntity<List<MypointDTO>> getAllMypoints(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Mypoints");
        Page<MypointDTO> page = mypointService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    //    /**
    //     * {@code GET  /mypoints/:id} : get the "id" mypoint.
    //     *
    //     * @param id the id of the mypointDTO to retrieve.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mypointDTO, or with status {@code 404 (Not Found)}.
    //     */
    //    @GetMapping("/v1/{id}")
    //    public ResponseEntity<MypointDTO> getMypoint(@PathVariable String id) {
    //        log.debug("REST request to get Mypoint : {}", id);
    //        Optional<MypointDTO> mypointDTO = mypointService.findOne(id);
    //        return ResponseUtil.wrapOrNotFound(mypointDTO);
    //    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Void> deleteMypoint(@PathVariable String id) {
        log.debug("REST request to delete Mypoint : {}", id);
        mypointService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
