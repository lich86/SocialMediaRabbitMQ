package com.chervonnaya.publicationsservice.service.mappers;

import com.chervonnaya.publicationsservice.dto.CommentDTO;
import com.chervonnaya.publicationsservice.model.Comment;
import com.chervonnaya.publicationsservice.model.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CommentMapper {

    @Mapping(source = "publicationId", target = "publication", qualifiedByName = "mapPublication")
    Comment map(CommentDTO dto);

    @Named("mapPublication")
    static Publication mapPublication(Long publicationId) {
        Publication publication = new Publication();
        publication.setId(publicationId);
        return publication;
    }
}
