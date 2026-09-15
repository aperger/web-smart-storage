package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.StorageEntity;
import hu.ps.ss.domain.StorageModel;
import hu.ps.ss.infra.database.mapper.StorageMapper;
import hu.ps.ss.infra.database.repository.StorageRepository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
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
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();

      var name = params.getFirst("name");
      if (name != null && !name.isBlank()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("name")),
            "%" + getDecodedUrlValue(name).toLowerCase() + "%"));
      }

      var type = params.getFirst("type");
      if (type != null && !type.isBlank()) {
        try {
          predicates.add(criteriaBuilder.equal(
              root.get("type"),
              Integer.parseInt(getDecodedUrlValue(type))));
        } catch (NumberFormatException ignored) {
          // Ignore malformed type filter values.
        }
      }

      return predicates.isEmpty()
          ? criteriaBuilder.conjunction()
          : criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }
}
