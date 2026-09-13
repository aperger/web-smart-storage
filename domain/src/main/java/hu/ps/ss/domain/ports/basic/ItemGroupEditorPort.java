package hu.ps.ss.domain.ports.basic;

import hu.ps.ss.domain.ItemGroupModel;

public interface ItemGroupEditorPort extends BaseModelPort<ItemGroupModel, Integer> {

  @Override
  default Class<ItemGroupModel> getModelClass() {
    return ItemGroupModel.class;
  }
}
