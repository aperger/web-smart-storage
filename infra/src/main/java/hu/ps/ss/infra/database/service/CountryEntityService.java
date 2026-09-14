package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.CountryEntity;
import hu.ps.ss.domain.CountryModel;
import hu.ps.ss.infra.database.mapper.CountryMapper;
import hu.ps.ss.infra.database.repository.CountryRepository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class CountryEntityService extends
    AbstractEntityService<CountryEntity, CountryModel, Integer> {

  public CountryEntityService(CountryMapper mapper,
      CountryRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<CountryEntity> getEntityClass() {
    return CountryEntity.class;
  }

  @Override
  Specification<CountryEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();

      var code = params.getFirst("code");
      if (code != null && !code.isBlank()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("code")),
            "%" + getDecodedUrlValue(code).toLowerCase() + "%"));
      }

      var name = params.getFirst("name");
      if (name != null && !name.isBlank()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("name")),
            "%" + getDecodedUrlValue(name).toLowerCase() + "%"));
      }

      return predicates.isEmpty()
          ? criteriaBuilder.conjunction()
          : criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }
}
