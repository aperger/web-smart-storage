package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.CurrencyEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.CurrencyModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface CurrencyMapper extends ObjectMapperBase<CurrencyEntity, CurrencyModel> {

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "modified", target = "modified")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  CurrencyModel map(CurrencyEntity source);

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "modified", target = "modified")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  CurrencyEntity parseFrom(CurrencyModel source);
}
