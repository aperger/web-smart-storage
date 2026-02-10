package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.VatKeyEntity;
import hu.ps.ss.domain.VatKeyModel;
import hu.ps.ss.infra.database.mapper.VatKeyMapper;
import hu.ps.ss.infra.database.repository.VatKeyRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class VatKeyEntityService extends AbstractEntityService<VatKeyEntity, VatKeyModel, Integer> {

  public VatKeyEntityService(VatKeyMapper mapper,
      VatKeyRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<VatKeyEntity> getEntityClass() {
    return VatKeyEntity.class;
  }

  @Override
  Specification<VatKeyEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return Specification.unrestricted();
  }
}
