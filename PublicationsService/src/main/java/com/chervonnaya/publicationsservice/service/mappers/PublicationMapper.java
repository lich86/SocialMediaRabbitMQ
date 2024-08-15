package com.chervonnaya.publicationsservice.service.mappers;

import com.chervonnaya.publicationsservice.dto.PublicationDTO;
import com.chervonnaya.publicationsservice.model.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PublicationMapper {

    Publication map(PublicationDTO dto);
}
