package hu.ps.ss.domain.ports.basic;

import hu.ps.ss.domain.StorageModel;

public interface StorageEditorPort extends BaseModelPort<StorageModel, Integer> {

  @Override
  default Class<StorageModel> getModelClass() {
    return StorageModel.class;
  }
}
