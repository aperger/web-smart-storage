package hu.ps.ss.domain.ports.basic;

import hu.ps.ss.domain.ItemPropertyModel;

public interface ItemPropertyEditorPort extends BaseModelPort<ItemPropertyModel, Integer> {

  @Override
  default Class<ItemPropertyModel> getModelClass() {
    return ItemPropertyModel.class;
  }
}
