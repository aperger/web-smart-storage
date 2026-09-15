package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.CurrencyEntity;
import hu.ps.ss.domain.CurrencyModel;
import hu.ps.ss.infra.database.mapper.CurrencyMapper;
import hu.ps.ss.infra.database.repository.CurrencyRepository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class CurrencyEntityService extends AbstractEntityService<CurrencyEntity, CurrencyModel, Integer> {

  public CurrencyEntityService(CurrencyMapper mapper, CurrencyRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<CurrencyEntity> getEntityClass() {
    return CurrencyEntity.class;
  }

  @Override
  Specification<CurrencyEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();

      var code = params.getFirst("code");
      if (code != null && !code.isBlank()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("code")),
            "%" + getDecodedUrlValue(code).toLowerCase() + "%"));
      }

      var symbol = params.getFirst("symbol");
      if (symbol != null && !symbol.isBlank()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("symbol")),
            "%" + getDecodedUrlValue(symbol).toLowerCase() + "%"));
      }

      return predicates.isEmpty()
          ? criteriaBuilder.conjunction()
          : criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }
}
