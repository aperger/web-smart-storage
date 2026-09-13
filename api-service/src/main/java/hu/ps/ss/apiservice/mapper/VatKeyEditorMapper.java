package hu.ps.ss.apiservice.mapper;

import hu.ps.ss.apiservice.dto.VatKeyDto;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.VatKeyModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface VatKeyEditorMapper extends ObjectMapperBase<VatKeyModel, VatKeyDto> {

}
