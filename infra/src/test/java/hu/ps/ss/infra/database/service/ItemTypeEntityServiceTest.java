package hu.ps.ss.infra.database.service;

import static org.assertj.core.api.Assertions.assertThat;

import hu.ps.ss.data.entity.ItemGroupEntity;
import hu.ps.ss.data.entity.ItemTypeEntity;
import hu.ps.ss.infra.database.mapper.ItemGroupMapper;
import hu.ps.ss.infra.database.mapper.ItemTypeMapper;
import hu.ps.ss.infra.database.repository.ItemGroupRepository;
import hu.ps.ss.infra.database.repository.ItemTypeRepository;
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
@Import(ItemTypeEntityServiceTest.TestConfig.class)
@ActiveProfiles("test")
class ItemTypeEntityServiceTest {

  @Autowired
  private ItemGroupRepository itemGroupRepository;

  @Autowired
  private ItemTypeRepository repository;

  @Autowired
  private ItemTypeEntityService service;

  @Test
  void searchPaginatedFiltersByNameCaseInsensitively() {
    var group = itemGroupRepository.save(itemGroupEntity("Services", 1, "Service related items"));
    repository.save(itemTypeEntity(group, "Consulting", "Consulting service"));
    repository.save(itemTypeEntity(group, "Hardware", "Hardware item"));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("name", "consult");

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(itemType -> itemType.getName())
        .containsExactly("Consulting");
  }

  @Test
  void searchPaginatedFiltersByItemGroupId() {
    var servicesGroup = itemGroupRepository.save(itemGroupEntity("Services", 1, "Service related items"));
    var goodsGroup = itemGroupRepository.save(itemGroupEntity("Goods", 2, "Goods related items"));
    repository.save(itemTypeEntity(servicesGroup, "Consulting", "Consulting service"));
    repository.save(itemTypeEntity(goodsGroup, "Laptop", "Laptop item"));

    var params = new LinkedMultiValueMap<String, String>();
    params.add("itemGroupId", String.valueOf(goodsGroup.getId()));

    var result = service.searchPaginated(params, 0, 10, "name,asc");

    assertThat(result.getContent())
        .extracting(itemType -> itemType.getName())
        .containsExactly("Laptop");
  }

  @Test
  void saveItemPersistsItemType() {
    var group = itemGroupRepository.save(itemGroupEntity("Services", 1, "Service related items"));
    var model = new hu.ps.ss.domain.ItemTypeModel();
    model.setItemGroupId(group.getId());
    model.setName("Support");
    model.setDescription("Support service");
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");

    var saved = service.saveItem(model);

    assertThat(saved.getId()).isPositive();
    assertThat(repository.findById(saved.getId()))
        .get()
        .extracting(ItemTypeEntity::getName, ItemTypeEntity::getDescription)
        .containsExactly("Support", "Support service");
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

  private ItemTypeEntity itemTypeEntity(ItemGroupEntity group, String name, String description) {
    var entity = new ItemTypeEntity();
    entity.setItemGroup(group);
    entity.setName(name);
    entity.setDescription(description);
    entity.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    entity.setModifiedBy("tester");
    return entity;
  }

  @SpringBootConfiguration
  @AutoConfigurationPackage
  @EntityScan(basePackageClasses = {ItemGroupEntity.class, ItemTypeEntity.class})
  @ImportAutoConfiguration(DataJpaRepositoriesAutoConfiguration.class)
  @EnableJpaRepositories(basePackageClasses = {ItemGroupRepository.class, ItemTypeRepository.class})
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
    ItemTypeMapper itemTypeMapper() {
      return Mappers.getMapper(ItemTypeMapper.class);
    }

    @Bean
    ItemTypeEntityService itemTypeEntityService(ItemTypeMapper mapper, ItemTypeRepository repository) {
      return new ItemTypeEntityService(mapper, repository);
    }
  }
}
