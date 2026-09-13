package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.PaymentMethodEntity;
import hu.ps.ss.domain.PaymentMethodModel;
import hu.ps.ss.domain.ports.basic.PaymentMethodEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodEditorAdapter
    extends AbstractModelAdapter<PaymentMethodEntity, PaymentMethodModel, Integer>
    implements PaymentMethodEditorPort {

  public PaymentMethodEditorAdapter(
      AbstractEntityService<PaymentMethodEntity, PaymentMethodModel, Integer> service
  ) {
    super(service);
  }
}
