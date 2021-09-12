package fiap.com.br.SofiaBag.mapper;

import fiap.com.br.SofiaBag.dto.request.ObjectDTO;
import fiap.com.br.SofiaBag.dto.response.CalendarResponseDTO;
import fiap.com.br.SofiaBag.entity.Object;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ObjectMapper {

    ObjectMapper INSTANCE = Mappers.getMapper(ObjectMapper.class);

    @Mapping(target = "reminder", source = "reminder", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    Object toModel(ObjectDTO objectDTO);

    CalendarResponseDTO toDTO(Object object);
}
