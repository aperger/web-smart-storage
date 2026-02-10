package hu.ps.ss.infra.database;

import hu.ps.ss.data.entity.CountryEntity;
import hu.ps.ss.domain.CountryModel;
import hu.ps.ss.domain.ports.basic.CountryEditorPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import org.springframework.stereotype.Service;


@Service
public class CountryEditorAdapter extends AbstractModelAdapter<CountryEntity, CountryModel, Integer> implements CountryEditorPort {

  public CountryEditorAdapter(AbstractEntityService<CountryEntity, CountryModel, Integer> service) {
    super(service);
  }
}
