package br.com.bingo.bingo_game.api.mapper;

import br.com.bingo.bingo_game.api.dto.response.BingoCardResponse;
import br.com.bingo.bingo_game.domain.document.BingoCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BingoCardMapper {

    @Mapping(target = "playerId", source = "player.id")
    BingoCardResponse toResponse(final BingoCard domainModel);
}
