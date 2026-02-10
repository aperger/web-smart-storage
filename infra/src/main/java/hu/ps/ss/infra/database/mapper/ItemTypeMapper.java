package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.ItemTypeEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.ItemTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface ItemTypeMapper extends ObjectMapperBase<ItemTypeEntity, ItemTypeModel> {

  @Override
  @Mapping(source = "itemGroup.id", target = "itemGroupId")
  ItemTypeModel map(final ItemTypeEntity source);


  @Override
  @Mapping(target = "itemGroup", ignore = true)
  ItemTypeEntity parseFrom(final ItemTypeModel source);
}
