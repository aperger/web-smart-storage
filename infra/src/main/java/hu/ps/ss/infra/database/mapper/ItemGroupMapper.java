package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.ItemGroupEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.ItemGroupModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class, uses = {ItemTypeMapper.class})
public interface ItemGroupMapper extends ObjectMapperBase<ItemGroupEntity, ItemGroupModel> {

}
