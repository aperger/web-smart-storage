package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.PartnerEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.PartnerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface PartnerMapper extends ObjectMapperBase<PartnerEntity, PartnerModel> {

  @Override
  @Mapping(target = "docHeaders", ignore = true)
  PartnerEntity parseFrom(PartnerModel source);
}
