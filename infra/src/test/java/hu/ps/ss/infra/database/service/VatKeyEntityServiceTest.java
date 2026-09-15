package hu.ps.ss.infra.database.service;

import static org.assertj.core.api.Assertions.assertThat;

import hu.ps.ss.data.entity.VatKeyEntity;
import hu.ps.ss.infra.database.mapper.VatKeyMapper;
import hu.ps.ss.infra.database.repository.VatKeyRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;

@DataJpaTest
@Import(VatKeyEntityServiceTest.TestConfig.class)
@ActiveProfiles("test")
class VatKeyEntityServiceTest {

  @Autowired
  private VatKeyRepository repository;

  @Autowired
  private VatKeyEntityService service;

  @Test
  void searchPaginatedFiltersByValueExactly() {
    repository.save(vatKeyEntity(new BigDecimal("27.00"), 0, "27%"));
    repository.save(vatKeyEntity(new BigDecimal("5.00"), 1, "AAM"));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("value", "27.00");

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(vatKey -> vatKey.getName())
        .containsExactly("27%");
  }

  @Test
  void searchPaginatedFiltersByNameCaseInsensitively() {
    repository.save(vatKeyEntity(new BigDecimal("27.00"), 0, "27%"));
    repository.save(vatKeyEntity(new BigDecimal("19.00"), 0, "Reduced"));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("name", "redu");

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(vatKey -> vatKey.getName())
        .containsExactly("Reduced");
  }

  @Test
  void searchPaginatedFiltersByExemption() {
    repository.save(vatKeyEntity(new BigDecimal("27.00"), 0, "27%"));
    repository.save(vatKeyEntity(new BigDecimal("5.00"), 1, "AAM"));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("exemption", "1");

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(vatKey -> vatKey.getName())
        .containsExactly("AAM");
  }

  @Test
  void saveItemPersistsVatKey() {
    var model = new hu.ps.ss.domain.VatKeyModel();
    model.setValue(new BigDecimal("5.00"));
    model.setExemption(1);
    model.setName("AAM");
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");

    var saved = service.saveItem(model);

    assertThat(saved.getId()).isPositive();
    assertThat(repository.findById(saved.getId()))
        .get()
        .extracting(VatKeyEntity::getValue, VatKeyEntity::getExemption, VatKeyEntity::getName)
        .containsExactly(new BigDecimal("5.00"), 1, "AAM");
  }

  private VatKeyEntity vatKeyEntity(BigDecimal value, int exemption, String name) {
    var entity = new VatKeyEntity(value, exemption, name);
    entity.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    entity.setModifiedBy("tester");
    return entity;
  }

  @SpringBootConfiguration
  @AutoConfigurationPackage
  @EntityScan(basePackageClasses = VatKeyEntity.class)
  @ImportAutoConfiguration(DataJpaRepositoriesAutoConfiguration.class)
  @EnableJpaRepositories(basePackageClasses = VatKeyRepository.class)
  @Import(TestConfig.class)
  static class BootConfig {
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    VatKeyMapper vatKeyMapper() {
      return Mappers.getMapper(VatKeyMapper.class);
    }

    @Bean
    VatKeyEntityService vatKeyEntityService(VatKeyMapper mapper, VatKeyRepository repository) {
      return new VatKeyEntityService(mapper, repository);
    }
  }
}
