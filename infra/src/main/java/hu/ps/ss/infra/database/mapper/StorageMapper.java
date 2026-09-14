package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.StorageEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.StorageModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface StorageMapper extends ObjectMapperBase<StorageEntity, StorageModel> {

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "modified", target = "modified")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  StorageModel map(StorageEntity source);

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "modified", target = "modified")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  StorageEntity parseFrom(StorageModel source);
}
