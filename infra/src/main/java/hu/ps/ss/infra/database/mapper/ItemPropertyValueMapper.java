package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.ItemPropertyValueEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.ItemPropertyValueModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface ItemPropertyValueMapper extends ObjectMapperBase<ItemPropertyValueEntity, ItemPropertyValueModel> {

  @Override
  @Mapping(source = "itemProperty.id", target = "itemPropertyId")
  @Mapping(source = "masterItem.stockCode", target = "masterItemCode")
  ItemPropertyValueModel map(ItemPropertyValueEntity source);

  @Override
  @Mapping(target = "itemProperty", ignore = true)
  @Mapping(target = "masterItem", ignore = true)
  ItemPropertyValueEntity parseFrom(ItemPropertyValueModel source);
}
