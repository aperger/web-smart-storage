package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.CurrencyEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.CurrencyModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface CurrencyMapper extends ObjectMapperBase<CurrencyEntity, CurrencyModel> {

  @Override
  CurrencyModel map(CurrencyEntity source);

  @Override
  CurrencyEntity parseFrom(CurrencyModel source);
}
