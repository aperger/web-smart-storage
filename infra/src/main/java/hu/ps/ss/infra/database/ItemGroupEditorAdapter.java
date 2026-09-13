package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.ItemGroupEntity;
import hu.ps.ss.domain.ItemGroupModel;
import hu.ps.ss.domain.ports.basic.ItemGroupEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class ItemGroupEditorAdapter
    extends AbstractModelAdapter<ItemGroupEntity, ItemGroupModel, Integer>
    implements ItemGroupEditorPort {

  public ItemGroupEditorAdapter(AbstractEntityService<ItemGroupEntity, ItemGroupModel, Integer> service) {
    super(service);
  }
}
