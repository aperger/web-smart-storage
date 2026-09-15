package hu.ps.ss.apiservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hu.ps.ss.apiservice.dto.ItemGroupDto;
import hu.ps.ss.apiservice.mapper.ItemGroupEditorMapper;
import hu.ps.ss.domain.ItemGroupModel;
import hu.ps.ss.domain.pojo.PageDetails;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.ItemGroupEditorPort;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemGroupEditorControllerTest {

  @Mock
  private ItemGroupEditorPort service;

  @Mock
  private ItemGroupEditorMapper mapper;

  private ItemGroupEditorController controller;

  @BeforeEach
  void setUp() {
    controller = new ItemGroupEditorController(service, mapper);
    org.mockito.Mockito.lenient().when(service.getModelClass()).thenReturn(ItemGroupModel.class);
  }

  @Test
  void searchReturnsFilteredPage() {
    var model = itemGroupModel(1, "Services", 1, "Service items");
    var dto = itemGroupDto(1, "Services", 1, "Service items");
    when(service.search(org.springframework.util.CollectionUtils.toMultiValueMap(java.util.Collections.emptyMap()),
        0, 10, "name,asc"))
        .thenReturn(new PageResult<>(List.of(model), new PageDetails(0, 10, 1, 1, "name,asc")));
    when(mapper.mapList(List.of(model))).thenReturn(List.of(dto));

    var response = controller.actionSearch(new org.springframework.util.LinkedMultiValueMap<>(), null,
        null, 0, 10, "name,asc");
    Assertions.assertEquals(1, response.items().size());
    Assertions.assertEquals(dto, response.items().get(0));

    verify(service).search(org.springframework.util.CollectionUtils.toMultiValueMap(java.util.Collections.emptyMap()),
        0, 10, "name,asc");
  }

  @Test
  void getByIdReturnsItemGroup() {
    var model = itemGroupModel(1, "Services", 1, "Service items");
    var dto = itemGroupDto(1, "Services", 1, "Service items");
    when(service.findById(1)).thenReturn(Optional.of(model));
    when(mapper.map(model)).thenReturn(dto);

    var response = controller.actionFindById(1);
    Assertions.assertEquals(200, response.getStatusCode().value());
    Assertions.assertEquals(dto, response.getBody());
  }

  @Test
  void getByIdReturnsNotFoundWhenItemGroupDoesNotExist() {
    when(service.findById(99)).thenReturn(Optional.empty());

    Assertions.assertThrows(org.springframework.web.server.ResponseStatusException.class,
        () -> controller.actionFindById(99));
  }

  @Test
  void createReturnsCreatedItemGroup() {
    var requestModel = itemGroupModel(0, "Accessories", 3, "Add-on products");
    var savedModel = itemGroupModel(2, "Accessories", 3, "Add-on products");
    var savedDto = itemGroupDto(2, "Accessories", 3, "Add-on products");
    var requestDto = itemGroupDto(0, "Accessories", 3, "Add-on products");

    when(mapper.parseFrom(any(ItemGroupDto.class))).thenReturn(requestModel);
    when(service.save(requestModel)).thenReturn(savedModel);
    when(mapper.map(savedModel)).thenReturn(savedDto);

    var response = controller.actionCreateItem(requestDto);
    Assertions.assertEquals(201, response.getStatusCode().value());
    Assertions.assertEquals(savedDto, response.getBody());
  }

  @Test
  void deleteReturnsNoContent() {
    var model = itemGroupModel(1, "Services", 1, "Service items");
    when(service.findById(1)).thenReturn(Optional.of(model));

    controller.actionDeleteById(1);
    verify(service).deleteById(1);
  }

  private ItemGroupModel itemGroupModel(int id, String name, int function, String description) {
    var model = new ItemGroupModel();
    model.setId(id);
    model.setName(name);
    model.setFunction(function);
    model.setDescription(description);
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");
    return model;
  }

  private ItemGroupDto itemGroupDto(int id, String name, int function, String description) {
    return ItemGroupDto.builder()
        .id(id)
        .modified(LocalDateTime.of(2026, 9, 14, 22, 0))
        .modifiedBy("tester")
        .name(name)
        .function(function)
        .description(description)
        .build();
  }
}
