package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Mypoint;
import com.mycompany.myapp.repository.MypointRepository;
import com.mycompany.myapp.service.MypointService;
import com.mycompany.myapp.service.dto.MypointDTO;
import com.mycompany.myapp.service.mapper.MypointMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Mypoint}.
 */
@Service
public class MypointServiceImpl implements MypointService {

    private final Logger log = LoggerFactory.getLogger(MypointServiceImpl.class);

    private final MypointRepository mypointRepository;

    private final MypointMapper mypointMapper;

    public MypointServiceImpl(MypointRepository mypointRepository, MypointMapper mypointMapper) {
        this.mypointRepository = mypointRepository;
        this.mypointMapper = mypointMapper;
    }

    @Override
    public MypointDTO save(MypointDTO mypointDTO) {
        log.debug("Request to save Mypoint : {}", mypointDTO);
        Mypoint mypoint = mypointMapper.toEntity(mypointDTO);
        mypoint = mypointRepository.save(mypoint);
        return mypointMapper.toDto(mypoint);
    }

    @Override
    public MypointDTO update(MypointDTO mypointDTO) {
        log.debug("Request to update Mypoint : {}", mypointDTO);
        Mypoint mypoint = mypointMapper.toEntity(mypointDTO);
        mypoint = mypointRepository.save(mypoint);
        return mypointMapper.toDto(mypoint);
    }

    @Override
    public Optional<MypointDTO> partialUpdate(MypointDTO mypointDTO) {
        log.debug("Request to partially update Mypoint : {}", mypointDTO);

        return mypointRepository
            .findById(mypointDTO.getId())
            .map(existingMypoint -> {
                mypointMapper.partialUpdate(existingMypoint, mypointDTO);

                return existingMypoint;
            })
            .map(mypointRepository::save)
            .map(mypointMapper::toDto);
    }

    @Override
    public Page<MypointDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mypoints");
        return mypointRepository.findAll(pageable).map(mypointMapper::toDto);
    }

    @Override
    public Optional<MypointDTO> findOne(String id) {
        log.debug("Request to get Mypoint : {}", id);
        return mypointRepository.findById(id).map(mypointMapper::toDto);
    }

    @Override
    public Iterable<MypointDTO> findByUserid(String userid) {
        log.debug("Request to get Mypoint by userid : {}", userid);

        // entity to dto
        return mypointMapper.toDto((List<Mypoint>) mypointRepository.findByUserid(userid));
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Mypoint : {}", id);
        mypointRepository.deleteById(id);
    }
}
