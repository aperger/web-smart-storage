package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.ItemPropertyValueEntity;
import hu.ps.ss.domain.ItemPropertyValueModel;
import hu.ps.ss.domain.ports.basic.ItemPropertyValueEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class ItemPropertyValueEditorAdapter
    extends AbstractModelAdapter<ItemPropertyValueEntity, ItemPropertyValueModel, Integer>
    implements ItemPropertyValueEditorPort {

  public ItemPropertyValueEditorAdapter(
      AbstractEntityService<ItemPropertyValueEntity, ItemPropertyValueModel, Integer> service
  ) {
    super(service);
  }
}
