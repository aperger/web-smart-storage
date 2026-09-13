package hu.ps.ss.domain.ports.basic;

import hu.ps.ss.domain.PaymentMethodModel;

public interface PaymentMethodEditorPort extends BaseModelPort<PaymentMethodModel, Integer> {

  @Override
  default Class<PaymentMethodModel> getModelClass() {
    return PaymentMethodModel.class;
  }
}
