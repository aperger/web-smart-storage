package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.ItemPropertyEntity;
import hu.ps.ss.domain.ItemPropertyModel;
import hu.ps.ss.infra.database.mapper.ItemPropertyMapper;
import hu.ps.ss.infra.database.repository.ItemPropertyRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class ItemPropertyEntityService extends
    AbstractEntityService<ItemPropertyEntity, ItemPropertyModel, Integer> {

  public ItemPropertyEntityService(ItemPropertyMapper mapper, ItemPropertyRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<ItemPropertyEntity> getEntityClass() {
    return ItemPropertyEntity.class;
  }

  @Override
  Specification<ItemPropertyEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return Specification.unrestricted();
  }
}
