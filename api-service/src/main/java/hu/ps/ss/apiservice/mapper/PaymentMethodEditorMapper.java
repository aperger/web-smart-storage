package hu.ps.ss.apiservice.mapper;

import hu.ps.ss.apiservice.dto.PaymentMethodDto;
import hu.ps.ss.data.mappers.CommonMapperConfig;
import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.domain.PaymentMethodModel;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface PaymentMethodEditorMapper extends ObjectMapperBase<PaymentMethodModel, PaymentMethodDto> {

}
