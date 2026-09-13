package hu.ps.ss.apiservice.mapper;

import hu.ps.ss.apiservice.dto.ItemPropertyDto;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.ItemPropertyModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface ItemPropertyEditorMapper extends ObjectMapperBase<ItemPropertyModel, ItemPropertyDto> {

}
