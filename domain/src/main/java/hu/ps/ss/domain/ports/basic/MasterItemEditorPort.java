package hu.ps.ss.domain.ports.basic;

import hu.ps.ss.domain.MasterItemModel;

public interface MasterItemEditorPort extends BaseModelPort<MasterItemModel, String> {

  @Override
  default Class<MasterItemModel> getModelClass() {
    return MasterItemModel.class;
  }
}
