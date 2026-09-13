package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.ItemPropertyValueEntity;
import hu.ps.ss.domain.ItemPropertyValueModel;
import hu.ps.ss.infra.database.mapper.ItemPropertyValueMapper;
import hu.ps.ss.infra.database.repository.ItemPropertyValueRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class ItemPropertyValueEntityService extends
    AbstractEntityService<ItemPropertyValueEntity, ItemPropertyValueModel, Integer> {

  public ItemPropertyValueEntityService(
      ItemPropertyValueMapper mapper,
      ItemPropertyValueRepository repository
  ) {
    super(mapper, repository);
  }

  @Override
  public Class<ItemPropertyValueEntity> getEntityClass() {
    return ItemPropertyValueEntity.class;
  }

  @Override
  Specification<ItemPropertyValueEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return Specification.unrestricted();
  }
}
