package hu.ps.ss.apiservice.mapper;

import hu.ps.ss.apiservice.dto.CurrencyDto;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.CurrencyModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface CurrencyEditorMapper extends ObjectMapperBase<CurrencyModel, CurrencyDto> {

}
