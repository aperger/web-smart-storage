package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.StorageEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.StorageModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface StorageMapper extends ObjectMapperBase<StorageEntity, StorageModel> {

}
