package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.ItemPropertyEntity;
import hu.ps.ss.domain.ItemPropertyModel;
import hu.ps.ss.domain.ports.basic.ItemPropertyEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class ItemPropertyEditorAdapter
    extends AbstractModelAdapter<ItemPropertyEntity, ItemPropertyModel, Integer>
    implements ItemPropertyEditorPort {

  public ItemPropertyEditorAdapter(
      AbstractEntityService<ItemPropertyEntity, ItemPropertyModel, Integer> service
  ) {
    super(service);
  }
}
