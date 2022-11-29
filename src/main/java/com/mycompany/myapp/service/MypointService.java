package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.MypointDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Mypoint}.
 */
public interface MypointService {
    /**
     * Save a mypoint.
     *
     * @param mypointDTO the entity to save.
     * @return the persisted entity.
     */
    MypointDTO save(MypointDTO mypointDTO);

    /**
     * Updates a mypoint.
     *
     * @param mypointDTO the entity to update.
     * @return the persisted entity.
     */
    MypointDTO update(MypointDTO mypointDTO);

    /**
     * Partially updates a mypoint.
     *
     * @param mypointDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MypointDTO> partialUpdate(MypointDTO mypointDTO);

    /**
     * Get all the mypoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MypointDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mypoint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MypointDTO> findOne(String id);

    /**
     * Delete the "id" mypoint.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
