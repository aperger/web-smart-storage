package hu.ps.ss.apiservice.mapper;

import hu.ps.ss.apiservice.dto.CountryDto;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.CountryModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface CountryEditorMapper extends ObjectMapperBase<CountryModel, CountryDto> {

}
