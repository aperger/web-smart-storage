package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.PartnerEntity;
import hu.ps.ss.domain.PartnerModel;
import hu.ps.ss.infra.database.mapper.PartnerMapper;
import hu.ps.ss.infra.database.repository.PartnerRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class PartnerEntityService extends AbstractEntityService<PartnerEntity, PartnerModel, Integer> {

  public PartnerEntityService(PartnerMapper mapper, PartnerRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<PartnerEntity> getEntityClass() {
    return PartnerEntity.class;
  }

  @Override
  Specification<PartnerEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return Specification.unrestricted();
  }
}
