package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.ItemPropertyEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.ItemPropertyModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface ItemPropertyMapper extends ObjectMapperBase<ItemPropertyEntity, ItemPropertyModel> {

}
