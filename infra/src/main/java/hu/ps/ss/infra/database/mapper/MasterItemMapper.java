package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.MasterItemEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.MasterItemModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class, uses = {ItemPropertyValueMapper.class})
public interface MasterItemMapper extends ObjectMapperBase<MasterItemEntity, MasterItemModel> {

  @Override
  @Mapping(source = "vatKey.id", target = "vatKeyId")
  @Mapping(source = "itemType.id", target = "itemTypeId")
  MasterItemModel map(MasterItemEntity source);

  @Override
  @Mapping(target = "vatKey", ignore = true)
  @Mapping(target = "itemType", ignore = true)
  @Mapping(target = "image", ignore = true)
  @Mapping(target = "user", ignore = true)
  MasterItemEntity parseFrom(MasterItemModel source);
}
