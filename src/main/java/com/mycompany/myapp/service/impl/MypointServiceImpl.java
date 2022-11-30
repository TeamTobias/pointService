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
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public MypointDTO save(MypointDTO mypointDTO) {
        log.debug("Request to save Mypoint : {}", mypointDTO);
        Mypoint mypoint = mypointMapper.toEntity(mypointDTO);

        mypoint.setTotal_amount(
            mypointRepository.findTopByUseridOrderByCreatedAtDesc(mypoint.getUserid()).getTotal_amount() + mypoint.getUnit_amount()
        );

        mypoint = mypointRepository.save(mypoint);
        return mypointMapper.toDto(mypoint);
    }

    @Override
    public MypointDTO update(MypointDTO mypointDTO) {
        log.debug("Request to save Mypoint : {}", mypointDTO);
        Mypoint mypoint = mypointMapper.toEntity(mypointDTO);

        mypoint.setUnit_amount(mypoint.getUnit_amount() * -1);

        long temp = mypointRepository.findTopByUseridOrderByCreatedAtDesc(mypoint.getUserid()).getTotal_amount() + mypoint.getUnit_amount();
        if (temp < 0) {
            throw new RuntimeException("잔액이 부족합니다.");
        } else {
            mypoint.setTotal_amount(temp);
        }

        mypoint = mypointRepository.save(mypoint);
        return mypointMapper.toDto(mypoint);
    }

    @Override
    public Optional<MypointDTO> partialUpdate(MypointDTO mypointDTO) {
        log.debug("Request to partially update Mypoint : {}", mypointDTO);

        return mypointRepository
            .findById(mypointDTO.getUserid())
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
