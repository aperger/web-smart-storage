package hu.ps.ss.infra.database.service;

import static org.assertj.core.api.Assertions.assertThat;

import hu.ps.ss.data.entity.PaymentMethodEntity;
import hu.ps.ss.infra.database.mapper.PaymentMethodMapper;
import hu.ps.ss.infra.database.repository.PaymentMethodRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;

@DataJpaTest
@Import(PaymentMethodEntityServiceTest.TestConfig.class)
@ActiveProfiles("test")
class PaymentMethodEntityServiceTest {

  @Autowired
  private PaymentMethodRepository repository;

  @Autowired
  private PaymentMethodEntityService service;

  @Test
  void searchPaginatedFiltersByNameCaseInsensitively() {
    repository.save(paymentMethodEntity("Transfer", 8, 1));
    repository.save(paymentMethodEntity("Cash", 0, 2));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("name", "trans");

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(paymentMethod -> paymentMethod.getName())
        .containsExactly("Transfer");
  }

  @Test
  void searchPaginatedFiltersByDueDate() {
    repository.save(paymentMethodEntity("Transfer", 8, 1));
    repository.save(paymentMethodEntity("Cash", 0, 2));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("dueDate", "0");

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(paymentMethod -> paymentMethod.getName())
        .containsExactly("Cash");
  }

  @Test
  void saveItemPersistsPaymentMethod() {
    var model = new hu.ps.ss.domain.PaymentMethodModel();
    model.setName("Card");
    model.setDueDate(14);
    model.setInvoiceFormat(3);
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");

    var saved = service.saveItem(model);

    assertThat(saved.getId()).isPositive();
    assertThat(repository.findById(saved.getId()))
        .get()
        .extracting(PaymentMethodEntity::getName, PaymentMethodEntity::getDueDate, PaymentMethodEntity::getInvoiceFormat)
        .containsExactly("Card", 14, 3);
  }

  private PaymentMethodEntity paymentMethodEntity(String name, int dueDate, int invoiceFormat) {
    var entity = new PaymentMethodEntity();
    entity.setName(name);
    entity.setDueDate(dueDate);
    entity.setInvoiceFormat(invoiceFormat);
    entity.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    entity.setModifiedBy("tester");
    return entity;
  }

  @SpringBootConfiguration
  @AutoConfigurationPackage
  @EntityScan(basePackageClasses = PaymentMethodEntity.class)
  @ImportAutoConfiguration(DataJpaRepositoriesAutoConfiguration.class)
  @EnableJpaRepositories(basePackageClasses = PaymentMethodRepository.class)
  @Import(TestConfig.class)
  static class BootConfig {
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    PaymentMethodMapper paymentMethodMapper() {
      return Mappers.getMapper(PaymentMethodMapper.class);
    }

    @Bean
    PaymentMethodEntityService paymentMethodEntityService(PaymentMethodMapper mapper, PaymentMethodRepository repository) {
      return new PaymentMethodEntityService(mapper, repository);
    }
  }
}
