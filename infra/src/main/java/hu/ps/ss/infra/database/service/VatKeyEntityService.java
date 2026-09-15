package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.VatKeyEntity;
import hu.ps.ss.domain.VatKeyModel;
import hu.ps.ss.infra.database.mapper.VatKeyMapper;
import hu.ps.ss.infra.database.repository.VatKeyRepository;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class VatKeyEntityService extends AbstractEntityService<VatKeyEntity, VatKeyModel, Integer> {

  public VatKeyEntityService(VatKeyMapper mapper,
      VatKeyRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Class<VatKeyEntity> getEntityClass() {
    return VatKeyEntity.class;
  }

  @Override
  Specification<VatKeyEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();

      var value = params.getFirst("value");
      if (value != null && !value.isBlank()) {
        try {
          predicates.add(criteriaBuilder.equal(
              root.get("value"),
              new BigDecimal(getDecodedUrlValue(value))));
        } catch (NumberFormatException ignored) {
          // Ignore malformed value filter values.
        }
      }

      var exemption = params.getFirst("exemption");
      if (exemption != null && !exemption.isBlank()) {
        try {
          predicates.add(criteriaBuilder.equal(
              root.get("exemption"),
              Integer.parseInt(getDecodedUrlValue(exemption))));
        } catch (NumberFormatException ignored) {
          // Ignore malformed exemption filter values.
        }
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
