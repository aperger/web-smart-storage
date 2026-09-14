package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.ItemGroupEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.ItemGroupModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface ItemGroupMapper extends ObjectMapperBase<ItemGroupEntity, ItemGroupModel> {

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "modified", target = "modified")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  ItemGroupModel map(ItemGroupEntity source);

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "modified", target = "modified")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  ItemGroupEntity parseFrom(ItemGroupModel source);
}
