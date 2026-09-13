package hu.ps.ss.apiservice.mapper;

import hu.ps.ss.apiservice.dto.ItemPropertyValueDto;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.ItemPropertyValueModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface ItemPropertyValueEditorMapper extends ObjectMapperBase<ItemPropertyValueModel, ItemPropertyValueDto> {

}
