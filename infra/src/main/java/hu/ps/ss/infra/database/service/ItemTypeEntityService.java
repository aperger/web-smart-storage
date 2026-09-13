package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.ItemTypeEntity;
import hu.ps.ss.domain.ItemTypeModel;
import hu.ps.ss.infra.database.mapper.ItemTypeMapper;
import hu.ps.ss.infra.database.repository.ItemTypeRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class ItemTypeEntityService extends AbstractEntityService<ItemTypeEntity, ItemTypeModel, Integer> {

  public ItemTypeEntityService(ItemTypeMapper mapper, ItemTypeRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<ItemTypeEntity> getEntityClass() {
    return ItemTypeEntity.class;
  }

  @Override
  Specification<ItemTypeEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return Specification.unrestricted();
  }
}
