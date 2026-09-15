package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.entity.PaymentMethodEntity;
import hu.ps.ss.domain.PaymentMethodModel;
import hu.ps.ss.infra.database.mapper.PaymentMethodMapper;
import hu.ps.ss.infra.database.repository.PaymentMethodRepository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class PaymentMethodEntityService extends
   AbstractEntityService<PaymentMethodEntity, PaymentMethodModel, Integer> {

  public PaymentMethodEntityService(
      PaymentMethodMapper mapper,
      PaymentMethodRepository repository
  ) {
    super(mapper, repository);
  }

  @Override
  public Class<PaymentMethodEntity> getEntityClass() {
    return PaymentMethodEntity.class;
  }

  @Override
  Specification<PaymentMethodEntity> createSearchSpecification(MultiValueMap<String, String> params) {
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();

      var name = params.getFirst("name");
      if (name != null && !name.isBlank()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("name")),
            "%" + getDecodedUrlValue(name).toLowerCase() + "%"));
      }

      var dueDate = params.getFirst("dueDate");
      if (dueDate != null && !dueDate.isBlank()) {
        try {
          predicates.add(criteriaBuilder.equal(
              root.get("dueDate"),
              Integer.parseInt(getDecodedUrlValue(dueDate))));
        } catch (NumberFormatException ignored) {
          // Ignore malformed dueDate filter values.
        }
      }

      var invoiceFormat = params.getFirst("invoiceFormat");
      if (invoiceFormat != null && !invoiceFormat.isBlank()) {
        try {
          predicates.add(criteriaBuilder.equal(
              root.get("invoiceFormat"),
              Integer.parseInt(getDecodedUrlValue(invoiceFormat))));
        } catch (NumberFormatException ignored) {
          // Ignore malformed invoiceFormat filter values.
        }
      }

      return predicates.isEmpty()
          ? criteriaBuilder.conjunction()
          : criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }
}
