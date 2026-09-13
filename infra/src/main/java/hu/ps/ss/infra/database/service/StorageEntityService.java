package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.StorageEntity;
import hu.ps.ss.domain.StorageModel;
import hu.ps.ss.infra.database.mapper.StorageMapper;
import hu.ps.ss.infra.database.repository.StorageRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class StorageEntityService extends AbstractEntityService<StorageEntity, StorageModel, Integer> {

  public StorageEntityService(StorageMapper mapper, StorageRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<StorageEntity> getEntityClass() {
    return StorageEntity.class;
  }

  @Override
  Specification<StorageEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return Specification.unrestricted();
  }
}
