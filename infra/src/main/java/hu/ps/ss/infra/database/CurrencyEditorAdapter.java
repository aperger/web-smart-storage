package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.CurrencyEntity;
import hu.ps.ss.domain.CurrencyModel;
import hu.ps.ss.domain.ports.basic.CurrencyEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
public class CurrencyEditorAdapter extends AbstractModelAdapter<CurrencyEntity, CurrencyModel, Integer>
    implements CurrencyEditorPort {

  public CurrencyEditorAdapter(AbstractEntityService<CurrencyEntity, CurrencyModel, Integer> service) {
    super(service);
  }
}
