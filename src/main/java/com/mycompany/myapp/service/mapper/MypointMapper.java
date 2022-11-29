package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Mypoint;
import com.mycompany.myapp.service.dto.MypointDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mypoint} and its DTO {@link MypointDTO}.
 */
@Mapper(componentModel = "spring")
public interface MypointMapper extends EntityMapper<MypointDTO, Mypoint> {}
