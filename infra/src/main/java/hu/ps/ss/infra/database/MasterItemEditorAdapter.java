package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.MasterItemEntity;
import hu.ps.ss.domain.MasterItemModel;
import hu.ps.ss.domain.ports.basic.MasterItemEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class MasterItemEditorAdapter extends AbstractModelAdapter<MasterItemEntity, MasterItemModel, String>
    implements MasterItemEditorPort {

  public MasterItemEditorAdapter(AbstractEntityService<MasterItemEntity, MasterItemModel, String> service) {
    super(service);
  }
}
