package hu.ps.ss.infra.database.service;

import static org.assertj.core.api.Assertions.assertThat;

import hu.ps.ss.data.entity.CurrencyEntity;
import hu.ps.ss.infra.database.mapper.CurrencyMapper;
import hu.ps.ss.infra.database.repository.CurrencyRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
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
@Import(CurrencyEntityServiceTest.TestConfig.class)
@ActiveProfiles("test")
class CurrencyEntityServiceTest {

  @Autowired
  private CurrencyRepository repository;

  @Autowired
  private CurrencyEntityService service;

  @Test
  void searchPaginatedFiltersByCodeCaseInsensitively() {
    repository.save(currencyEntity("HUF", "Ft"));
    repository.save(currencyEntity("EUR", "€"));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("code", "huf");

    var result = service.searchPaginated(params, 0, 10, "code,asc");

    assertThat(result.getContent())
        .extracting(currency -> currency.getCode())
        .containsExactly("HUF");
  }

  @Test
  void searchPaginatedFiltersBySymbolCaseInsensitively() {
    repository.save(currencyEntity("HUF", "Ft"));
    repository.save(currencyEntity("USD", "$"));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("symbol", "f");

    var result = service.searchPaginated(params, 0, 10, "code,asc");

    assertThat(result.getContent())
        .extracting(currency -> currency.getSymbol())
        .containsExactly("Ft");
  }

  @Test
  void saveItemPersistsCurrency() {
    var model = new hu.ps.ss.domain.CurrencyModel();
    model.setCode("CHF");
    model.setSymbol("CHF");
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");

    var saved = service.saveItem(model);

    assertThat(saved.getId()).isPositive();
    assertThat(repository.findById(saved.getId()))
        .get()
        .extracting(CurrencyEntity::getCode, CurrencyEntity::getSymbol)
        .containsExactly("CHF", "CHF");
  }

  private CurrencyEntity currencyEntity(String code, String symbol) {
    var entity = new CurrencyEntity();
    entity.setCode(code);
    entity.setSymbol(symbol);
    entity.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    entity.setModifiedBy("tester");
    return entity;
  }

  @SpringBootConfiguration
  @AutoConfigurationPackage
  @EntityScan(basePackageClasses = CurrencyEntity.class)
  @ImportAutoConfiguration(DataJpaRepositoriesAutoConfiguration.class)
  @EnableJpaRepositories(basePackageClasses = CurrencyRepository.class)
  @Import(TestConfig.class)
  static class BootConfig {
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    CurrencyMapper currencyMapper() {
      return Mappers.getMapper(CurrencyMapper.class);
    }

    @Bean
    CurrencyEntityService currencyEntityService(CurrencyMapper mapper, CurrencyRepository repository) {
      return new CurrencyEntityService(mapper, repository);
    }
  }
}
