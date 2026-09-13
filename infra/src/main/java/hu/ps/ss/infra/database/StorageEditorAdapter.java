package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.StorageEntity;
import hu.ps.ss.domain.StorageModel;
import hu.ps.ss.domain.ports.basic.StorageEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class StorageEditorAdapter extends AbstractModelAdapter<StorageEntity, StorageModel, Integer>
    implements StorageEditorPort {

  public StorageEditorAdapter(AbstractEntityService<StorageEntity, StorageModel, Integer> service) {
    super(service);
  }
}
