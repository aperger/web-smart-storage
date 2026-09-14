package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.PaymentMethodEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.PaymentMethodModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface PaymentMethodMapper extends ObjectMapperBase<PaymentMethodEntity, PaymentMethodModel> {

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "modified", target = "modified")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  PaymentMethodModel map(PaymentMethodEntity source);

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "modified", target = "modified")
  @Mapping(source = "modifiedBy", target = "modifiedBy")
  PaymentMethodEntity parseFrom(PaymentMethodModel source);
}
