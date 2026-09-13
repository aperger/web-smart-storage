package hu.ps.ss.domain.ports.basic;

import hu.ps.ss.domain.ItemTypeModel;

public interface ItemTypeEditorPort extends BaseModelPort<ItemTypeModel, Integer> {

  @Override
  default Class<ItemTypeModel> getModelClass() {
    return ItemTypeModel.class;
  }
}
