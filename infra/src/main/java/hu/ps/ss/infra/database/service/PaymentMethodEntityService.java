package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.PaymentMethodEntity;
import hu.ps.ss.domain.PaymentMethodModel;
import hu.ps.ss.infra.database.mapper.PaymentMethodMapper;
import hu.ps.ss.infra.database.repository.PaymentMethodRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class PaymentMethodEntityService extends
    AbstractEntityService<PaymentMethodEntity, PaymentMethodModel, Integer> {

  public PaymentMethodEntityService(
      PaymentMethodMapper mapper,
      PaymentMethodRepository repository
  ) {
    super(mapper, repository);
  }

  @Override
  public Class<PaymentMethodEntity> getEntityClass() {
    return PaymentMethodEntity.class;
  }

  @Override
  Specification<PaymentMethodEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return Specification.unrestricted();
  }
}
