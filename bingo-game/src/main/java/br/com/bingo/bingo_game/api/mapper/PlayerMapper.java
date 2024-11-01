package br.com.bingo.bingo_game.api.mapper;

import br.com.bingo.bingo_game.api.dto.request.PlayerRequest;
import br.com.bingo.bingo_game.api.dto.response.PlayerResponse;
import br.com.bingo.bingo_game.domain.document.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Player toDocument(final PlayerRequest request);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PlayerResponse toResponse(final Player document);
}
