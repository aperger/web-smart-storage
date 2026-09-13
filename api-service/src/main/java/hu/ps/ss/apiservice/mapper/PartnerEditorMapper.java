package hu.ps.ss.apiservice.mapper;

import hu.ps.ss.apiservice.dto.PartnerDto;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.PartnerModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface PartnerEditorMapper extends ObjectMapperBase<PartnerModel, PartnerDto> {

}
