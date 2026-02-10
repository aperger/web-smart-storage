package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.VatKeyEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.VatKeyModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface VatKeyMapper extends ObjectMapperBase<VatKeyEntity, VatKeyModel> {

}
