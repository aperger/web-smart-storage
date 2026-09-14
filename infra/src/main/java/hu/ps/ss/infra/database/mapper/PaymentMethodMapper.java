package hu.ps.ss.infra.database.mapper;

import hu.ps.ss.data.entity.PaymentMethodEntity;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.PaymentMethodModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface PaymentMethodMapper extends ObjectMapperBase<PaymentMethodEntity, PaymentMethodModel> {

  @Override
  PaymentMethodModel map(PaymentMethodEntity source);

  @Override
  PaymentMethodEntity parseFrom(PaymentMethodModel source);
}
