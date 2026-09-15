package hu.ps.ss.infra.database.service;

import static org.assertj.core.api.Assertions.assertThat;

import hu.ps.ss.data.entity.StorageEntity;
import hu.ps.ss.infra.database.mapper.StorageMapper;
import hu.ps.ss.infra.database.repository.StorageRepository;
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
@Import(StorageEntityServiceTest.TestConfig.class)
@ActiveProfiles("test")
class StorageEntityServiceTest {

  @Autowired
  private StorageRepository repository;

  @Autowired
  private StorageEntityService service;

  @Test
  void searchPaginatedFiltersByNameCaseInsensitively() {
    repository.save(storageEntity("Main warehouse", 1));
    repository.save(storageEntity("Office storage", 0));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("name", "ware");

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(storage -> storage.getName())
        .containsExactly("Main warehouse");
  }

  @Test
  void searchPaginatedFiltersByType() {
    repository.save(storageEntity("Main warehouse", 1));
    repository.save(storageEntity("Office storage", 0));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("type", "0");

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(storage -> storage.getName())
        .containsExactly("Office storage");
  }

  @Test
  void saveItemPersistsStorage() {
    var model = new hu.ps.ss.domain.StorageModel();
    model.setName("Warehouse B");
    model.setType(1);
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");

    var saved = service.saveItem(model);

    assertThat(saved.getId()).isPositive();
    assertThat(repository.findById(saved.getId()))
        .get()
        .extracting(StorageEntity::getName, StorageEntity::getType)
        .containsExactly("Warehouse B", 1);
  }

  private StorageEntity storageEntity(String name, int type) {
    var entity = new StorageEntity();
    entity.setName(name);
    entity.setType(type);
    entity.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    entity.setModifiedBy("tester");
    return entity;
  }

  @SpringBootConfiguration
  @AutoConfigurationPackage
  @EntityScan(basePackageClasses = StorageEntity.class)
  @ImportAutoConfiguration(DataJpaRepositoriesAutoConfiguration.class)
  @EnableJpaRepositories(basePackageClasses = StorageRepository.class)
  @Import(TestConfig.class)
  static class BootConfig {
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    StorageMapper storageMapper() {
      return Mappers.getMapper(StorageMapper.class);
    }

    @Bean
    StorageEntityService storageEntityService(StorageMapper mapper, StorageRepository repository) {
      return new StorageEntityService(mapper, repository);
    }
  }
}
