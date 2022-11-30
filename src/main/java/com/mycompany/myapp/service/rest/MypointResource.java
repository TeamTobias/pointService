package com.mycompany.myapp.service.rest;

import com.mycompany.myapp.repository.MypointRepository;
import com.mycompany.myapp.service.MypointService;
import com.mycompany.myapp.service.dto.MypointDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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

    /*포인트 사용*/
    @DeleteMapping("/v1")
    public ResponseEntity<MypointDTO> updateMypoint(@RequestBody MypointDTO mypointDTO) throws URISyntaxException {
        log.debug("REST request to save Mypoint : {}", mypointDTO);
        MypointDTO result = mypointService.update(mypointDTO);
        return ResponseEntity
            .created(new URI("/api/mypoints/" + result.getUserid()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getUserid()))
            .body(result);
    }

    @PatchMapping("/v1")
    public ResponseEntity<List<MypointDTO>> getAllMypoints(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Mypoints");
        Page<MypointDTO> page = mypointService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Void> deleteMypoint(@PathVariable String id) {
        log.debug("REST request to delete Mypoint : {}", id);
        mypointService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
