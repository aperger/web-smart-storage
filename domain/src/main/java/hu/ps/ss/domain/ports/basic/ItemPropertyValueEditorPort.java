package hu.ps.ss.domain.ports.basic;

import hu.ps.ss.domain.ItemPropertyValueModel;

public interface ItemPropertyValueEditorPort extends BaseModelPort<ItemPropertyValueModel, Integer> {

  @Override
  default Class<ItemPropertyValueModel> getModelClass() {
    return ItemPropertyValueModel.class;
  }
}
