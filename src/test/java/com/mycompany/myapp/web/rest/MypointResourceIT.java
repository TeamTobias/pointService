package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Mypoint;
import com.mycompany.myapp.repository.MypointRepository;
import com.mycompany.myapp.service.dto.MypointDTO;
import com.mycompany.myapp.service.mapper.MypointMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link MypointResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MypointResourceIT {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final Long DEFAULT_TOTAL_AMOUNT = 1L;
    private static final Long UPDATED_TOTAL_AMOUNT = 2L;

    private static final Long DEFAULT_UNIT_AMOUNT = 1L;
    private static final Long UPDATED_UNIT_AMOUNT = 2L;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/mypoints";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MypointRepository mypointRepository;

    @Autowired
    private MypointMapper mypointMapper;

    @Autowired
    private MockMvc restMypointMockMvc;

    private Mypoint mypoint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mypoint createEntity() {
        Mypoint mypoint = new Mypoint().userid(DEFAULT_USERID).total_amount(DEFAULT_TOTAL_AMOUNT).unit_amount(DEFAULT_UNIT_AMOUNT);
        //            .createdAt(DEFAULT_CREATED_AT);
        return mypoint;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mypoint createUpdatedEntity() {
        Mypoint mypoint = new Mypoint().userid(UPDATED_USERID).total_amount(UPDATED_TOTAL_AMOUNT).unit_amount(UPDATED_UNIT_AMOUNT);
        //            .createdAt(UPDATED_CREATED_AT);
        return mypoint;
    }

    @BeforeEach
    public void initTest() {
        mypointRepository.deleteAll();
        mypoint = createEntity();
    }

    @Test
    void createMypoint() throws Exception {
        int databaseSizeBeforeCreate = mypointRepository.findAll().size();
        // Create the Mypoint
        MypointDTO mypointDTO = mypointMapper.toDto(mypoint);
        restMypointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mypointDTO)))
            .andExpect(status().isCreated());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeCreate + 1);
        Mypoint testMypoint = mypointList.get(mypointList.size() - 1);
        assertThat(testMypoint.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testMypoint.getTotal_amount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testMypoint.getUnit_amount()).isEqualTo(DEFAULT_UNIT_AMOUNT);
        assertThat(testMypoint.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    void createMypointWithExistingId() throws Exception {
        // Create the Mypoint with an existing ID
        mypoint.setId("existing_id");
        MypointDTO mypointDTO = mypointMapper.toDto(mypoint);

        int databaseSizeBeforeCreate = mypointRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMypointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mypointDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMypoints() throws Exception {
        // Initialize the database
        mypointRepository.save(mypoint);

        // Get all the mypointList
        restMypointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mypoint.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID)))
            .andExpect(jsonPath("$.[*].total_amount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].unit_amount").value(hasItem(DEFAULT_UNIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    void getMypoint() throws Exception {
        // Initialize the database
        mypointRepository.save(mypoint);

        // Get the mypoint
        restMypointMockMvc
            .perform(get(ENTITY_API_URL_ID, mypoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mypoint.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID))
            .andExpect(jsonPath("$.total_amount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.unit_amount").value(DEFAULT_UNIT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    void getNonExistingMypoint() throws Exception {
        // Get the mypoint
        restMypointMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMypoint() throws Exception {
        // Initialize the database
        mypointRepository.save(mypoint);

        int databaseSizeBeforeUpdate = mypointRepository.findAll().size();

        // Update the mypoint
        Mypoint updatedMypoint = mypointRepository.findById(mypoint.getId()).get();
        updatedMypoint.userid(UPDATED_USERID).total_amount(UPDATED_TOTAL_AMOUNT).unit_amount(UPDATED_UNIT_AMOUNT);
        //            .createdAt(UPDATED_CREATED_AT);
        MypointDTO mypointDTO = mypointMapper.toDto(updatedMypoint);

        restMypointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mypointDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mypointDTO))
            )
            .andExpect(status().isOk());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeUpdate);
        Mypoint testMypoint = mypointList.get(mypointList.size() - 1);
        assertThat(testMypoint.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testMypoint.getTotal_amount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testMypoint.getUnit_amount()).isEqualTo(UPDATED_UNIT_AMOUNT);
        //        assertThat(testMypoint.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    void putNonExistingMypoint() throws Exception {
        int databaseSizeBeforeUpdate = mypointRepository.findAll().size();
        mypoint.setId(UUID.randomUUID().toString());

        // Create the Mypoint
        MypointDTO mypointDTO = mypointMapper.toDto(mypoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMypointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mypointDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mypointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMypoint() throws Exception {
        int databaseSizeBeforeUpdate = mypointRepository.findAll().size();
        mypoint.setId(UUID.randomUUID().toString());

        // Create the Mypoint
        MypointDTO mypointDTO = mypointMapper.toDto(mypoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMypointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mypointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMypoint() throws Exception {
        int databaseSizeBeforeUpdate = mypointRepository.findAll().size();
        mypoint.setId(UUID.randomUUID().toString());

        // Create the Mypoint
        MypointDTO mypointDTO = mypointMapper.toDto(mypoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMypointMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mypointDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMypointWithPatch() throws Exception {
        // Initialize the database
        mypointRepository.save(mypoint);

        int databaseSizeBeforeUpdate = mypointRepository.findAll().size();

        // Update the mypoint using partial update
        Mypoint partialUpdatedMypoint = new Mypoint();
        partialUpdatedMypoint.setId(mypoint.getId());

        //        partialUpdatedMypoint.createdAt(UPDATED_CREATED_AT);

        restMypointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMypoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMypoint))
            )
            .andExpect(status().isOk());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeUpdate);
        Mypoint testMypoint = mypointList.get(mypointList.size() - 1);
        assertThat(testMypoint.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testMypoint.getTotal_amount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testMypoint.getUnit_amount()).isEqualTo(DEFAULT_UNIT_AMOUNT);
        assertThat(testMypoint.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    void fullUpdateMypointWithPatch() throws Exception {
        // Initialize the database
        mypointRepository.save(mypoint);

        int databaseSizeBeforeUpdate = mypointRepository.findAll().size();

        // Update the mypoint using partial update
        Mypoint partialUpdatedMypoint = new Mypoint();
        partialUpdatedMypoint.setId(mypoint.getId());

        partialUpdatedMypoint.userid(UPDATED_USERID).total_amount(UPDATED_TOTAL_AMOUNT).unit_amount(UPDATED_UNIT_AMOUNT);
        //            .createdAt(UPDATED_CREATED_AT);

        restMypointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMypoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMypoint))
            )
            .andExpect(status().isOk());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeUpdate);
        Mypoint testMypoint = mypointList.get(mypointList.size() - 1);
        assertThat(testMypoint.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testMypoint.getTotal_amount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testMypoint.getUnit_amount()).isEqualTo(UPDATED_UNIT_AMOUNT);
        //        assertThat(testMypoint.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    void patchNonExistingMypoint() throws Exception {
        int databaseSizeBeforeUpdate = mypointRepository.findAll().size();
        mypoint.setId(UUID.randomUUID().toString());

        // Create the Mypoint
        MypointDTO mypointDTO = mypointMapper.toDto(mypoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMypointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mypointDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mypointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMypoint() throws Exception {
        int databaseSizeBeforeUpdate = mypointRepository.findAll().size();
        mypoint.setId(UUID.randomUUID().toString());

        // Create the Mypoint
        MypointDTO mypointDTO = mypointMapper.toDto(mypoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMypointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mypointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMypoint() throws Exception {
        int databaseSizeBeforeUpdate = mypointRepository.findAll().size();
        mypoint.setId(UUID.randomUUID().toString());

        // Create the Mypoint
        MypointDTO mypointDTO = mypointMapper.toDto(mypoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMypointMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mypointDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mypoint in the database
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMypoint() throws Exception {
        // Initialize the database
        mypointRepository.save(mypoint);

        int databaseSizeBeforeDelete = mypointRepository.findAll().size();

        // Delete the mypoint
        restMypointMockMvc
            .perform(delete(ENTITY_API_URL_ID, mypoint.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mypoint> mypointList = mypointRepository.findAll();
        assertThat(mypointList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
