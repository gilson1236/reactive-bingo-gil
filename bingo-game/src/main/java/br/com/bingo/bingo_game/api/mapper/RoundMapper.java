package br.com.bingo.bingo_game.api.mapper;

import br.com.bingo.bingo_game.api.dto.response.RoundResponse;
import br.com.bingo.bingo_game.domain.document.Round;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BingoCardMapper.class})
public interface RoundMapper {

    RoundResponse toResponse(final Round domainModel);


}
