package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.ItemTypeEntity;
import hu.ps.ss.data.entity.ItemGroupEntity;
import hu.ps.ss.domain.ItemTypeModel;
import hu.ps.ss.domain.ports.basic.ItemTypeEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class ItemTypeEditorAdapter extends AbstractModelAdapter<ItemTypeEntity, ItemTypeModel, Integer>
    implements ItemTypeEditorPort {

  public ItemTypeEditorAdapter(AbstractEntityService<ItemTypeEntity, ItemTypeModel, Integer> service) {
    super(service);
  }

  @Override
  public ItemTypeModel save(ItemTypeModel item) {
    return super.save(item);
  }
}
