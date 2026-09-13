package hu.ps.ss.domain.ports.basic;

import hu.ps.ss.domain.PartnerModel;

public interface PartnerEditorPort extends BaseModelPort<PartnerModel, Integer> {

  @Override
  default Class<PartnerModel> getModelClass() {
    return PartnerModel.class;
  }
}
