package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.MasterItemEntity;
import hu.ps.ss.domain.MasterItemModel;
import hu.ps.ss.infra.database.mapper.MasterItemMapper;
import hu.ps.ss.infra.database.repository.MasterItemRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class MasterItemEntityService extends AbstractEntityService<MasterItemEntity, MasterItemModel, String> {

  public MasterItemEntityService(MasterItemMapper mapper, MasterItemRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<MasterItemEntity> getEntityClass() {
    return MasterItemEntity.class;
  }

  @Override
  Specification<MasterItemEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return Specification.unrestricted();
  }
}
