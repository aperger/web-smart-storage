package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.ItemGroupEntity;
import hu.ps.ss.domain.ItemGroupModel;
import hu.ps.ss.infra.database.mapper.ItemGroupMapper;
import hu.ps.ss.infra.database.repository.ItemGroupRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class ItemGroupEntityService extends
    AbstractEntityService<ItemGroupEntity, ItemGroupModel, Integer> {

  public ItemGroupEntityService(ItemGroupMapper mapper, ItemGroupRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<ItemGroupEntity> getEntityClass() {
    return ItemGroupEntity.class;
  }

  @Override
  Specification<ItemGroupEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return Specification.unrestricted();
  }
}
