package hu.ps.ss.apiservice.mapper;

import hu.ps.ss.apiservice.dto.MasterItemDto;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.MasterItemModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class, uses = {ItemPropertyValueEditorMapper.class})
public interface MasterItemEditorMapper extends ObjectMapperBase<MasterItemModel, MasterItemDto> {

}
