package hu.ps.ss.apiservice.mapper;

import hu.ps.ss.apiservice.dto.StorageDto;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.StorageModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface StorageEditorMapper extends ObjectMapperBase<StorageModel, StorageDto> {

}
