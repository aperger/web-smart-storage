package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.ItemTypeEntity;
import hu.ps.ss.domain.ItemTypeModel;
import hu.ps.ss.infra.database.mapper.ItemTypeMapper;
import hu.ps.ss.infra.database.repository.ItemTypeRepository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
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
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();

      var name = params.getFirst("name");
      if (name != null && !name.isBlank()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("name")),
            "%" + getDecodedUrlValue(name).toLowerCase() + "%"));
      }

      var itemGroupId = params.getFirst("itemGroupId");
      if (itemGroupId != null && !itemGroupId.isBlank()) {
        try {
          predicates.add(criteriaBuilder.equal(
              root.get("itemGroup").get("id"),
              Integer.parseInt(getDecodedUrlValue(itemGroupId))));
        } catch (NumberFormatException ignored) {
          // Ignore malformed itemGroupId filter values.
        }
      }

      return predicates.isEmpty()
          ? criteriaBuilder.conjunction()
          : criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }
}
