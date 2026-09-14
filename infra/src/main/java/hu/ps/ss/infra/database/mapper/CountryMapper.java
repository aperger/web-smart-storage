package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.CountryEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.CountryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface CountryMapper extends ObjectMapperBase<CountryEntity, CountryModel> {

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "modified", target = "modified")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  CountryModel map(CountryEntity source);

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "modified", target = "modified")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  CountryEntity parseFrom(CountryModel source);
}
