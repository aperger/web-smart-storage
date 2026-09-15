package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.ItemGroupEntity;
import hu.ps.ss.domain.ItemGroupModel;
import hu.ps.ss.infra.database.mapper.ItemGroupMapper;
import hu.ps.ss.infra.database.repository.ItemGroupRepository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
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
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();

      var name = params.getFirst("name");
      if (name != null && !name.isBlank()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("name")),
            "%" + getDecodedUrlValue(name).toLowerCase() + "%"));
      }

      var function = params.getFirst("function");
      if (function != null && !function.isBlank()) {
        try {
          predicates.add(criteriaBuilder.equal(
              root.get("function"),
              Integer.parseInt(getDecodedUrlValue(function))));
        } catch (NumberFormatException ignored) {
          // Ignore malformed function filter values.
        }
      }

      var description = params.getFirst("description");
      if (description != null && !description.isBlank()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("description")),
            "%" + getDecodedUrlValue(description).toLowerCase() + "%"));
      }

      return predicates.isEmpty()
          ? criteriaBuilder.conjunction()
          : criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }
}
