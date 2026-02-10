package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.VatKeyEntity;
import hu.ps.ss.domain.VatKeyModel;
import hu.ps.ss.domain.ports.basic.VatKeyEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class VatKeyEditorAdapter extends
    AbstractModelAdapter<VatKeyEntity, VatKeyModel, Integer> implements VatKeyEditorPort {


  public VatKeyEditorAdapter(AbstractEntityService<VatKeyEntity, VatKeyModel, Integer> service) {
    super(service);
  }
}
