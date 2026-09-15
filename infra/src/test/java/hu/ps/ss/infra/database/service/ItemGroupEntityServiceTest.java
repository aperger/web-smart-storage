package hu.ps.ss.infra.database.service;

import static org.assertj.core.api.Assertions.assertThat;

import hu.ps.ss.data.entity.ItemGroupEntity;
import hu.ps.ss.infra.database.mapper.ItemGroupMapper;
import hu.ps.ss.infra.database.repository.ItemGroupRepository;
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
@Import(ItemGroupEntityServiceTest.TestConfig.class)
@ActiveProfiles("test")
class ItemGroupEntityServiceTest {

  @Autowired
  private ItemGroupRepository repository;

  @Autowired
  private ItemGroupEntityService service;

  @Test
  void searchPaginatedFiltersByNameCaseInsensitively() {
    repository.save(itemGroupEntity("Services", 1, "Service items"));
    repository.save(itemGroupEntity("Goods", 2, "Physical goods"));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("name", "serv");

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(itemGroup -> itemGroup.getName())
        .containsExactly("Services");
  }

  @Test
  void searchPaginatedFiltersByFunction() {
    repository.save(itemGroupEntity("Services", 1, "Service items"));
    repository.save(itemGroupEntity("Goods", 2, "Physical goods"));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("function", "2");

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(itemGroup -> itemGroup.getName())
        .containsExactly("Goods");
  }

  @Test
  void saveItemPersistsItemGroup() {
    var model = new hu.ps.ss.domain.ItemGroupModel();
    model.setName("Accessories");
    model.setFunction(3);
    model.setDescription("Add-on products");
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");

    var saved = service.saveItem(model);

    assertThat(saved.getId()).isPositive();
    assertThat(repository.findById(saved.getId()))
        .get()
        .extracting(ItemGroupEntity::getName, ItemGroupEntity::getFunction, ItemGroupEntity::getDescription)
        .containsExactly("Accessories", 3, "Add-on products");
  }

  private ItemGroupEntity itemGroupEntity(String name, int function, String description) {
    var entity = new ItemGroupEntity();
    entity.setName(name);
    entity.setFunction(function);
    entity.setDescription(description);
    entity.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    entity.setModifiedBy("tester");
    return entity;
  }

  @SpringBootConfiguration
  @AutoConfigurationPackage
  @EntityScan(basePackageClasses = ItemGroupEntity.class)
  @ImportAutoConfiguration(DataJpaRepositoriesAutoConfiguration.class)
  @EnableJpaRepositories(basePackageClasses = ItemGroupRepository.class)
  @Import(TestConfig.class)
  static class BootConfig {
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    ItemGroupMapper itemGroupMapper() {
      return Mappers.getMapper(ItemGroupMapper.class);
    }

    @Bean
    ItemGroupEntityService itemGroupEntityService(ItemGroupMapper mapper, ItemGroupRepository repository) {
      return new ItemGroupEntityService(mapper, repository);
    }
  }
}
