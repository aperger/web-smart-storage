package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.CountryEntity;
import hu.ps.ss.domain.CountryModel;
import hu.ps.ss.infra.database.mapper.CountryMapper;
import hu.ps.ss.infra.database.repository.CountryRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class CountryEntityService extends
    AbstractEntityService<CountryEntity, CountryModel, Integer> {

  public CountryEntityService(CountryMapper mapper,
      CountryRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<CountryEntity> getEntityClass() {
    return CountryEntity.class;
  }

  @Override
  Specification<CountryEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return Specification.unrestricted();
  }
}
