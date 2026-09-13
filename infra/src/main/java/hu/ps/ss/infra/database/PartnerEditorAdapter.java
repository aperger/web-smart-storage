package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.PartnerEntity;
import hu.ps.ss.domain.PartnerModel;
import hu.ps.ss.domain.ports.basic.PartnerEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class PartnerEditorAdapter extends AbstractModelAdapter<PartnerEntity, PartnerModel, Integer>
    implements PartnerEditorPort {

  public PartnerEditorAdapter(AbstractEntityService<PartnerEntity, PartnerModel, Integer> service) {
    super(service);
  }
}
