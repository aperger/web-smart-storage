package hu.ps.ss.apiservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hu.ps.ss.apiservice.dto.ItemTypeDto;
import hu.ps.ss.apiservice.mapper.ItemTypeEditorMapper;
import hu.ps.ss.domain.ItemTypeModel;
import hu.ps.ss.domain.pojo.PageDetails;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.ItemTypeEditorPort;
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
class ItemTypeEditorControllerTest {

  @Mock
  private ItemTypeEditorPort service;

  @Mock
  private ItemTypeEditorMapper mapper;

  private ItemTypeEditorController controller;

  @BeforeEach
  void setUp() {
    controller = new ItemTypeEditorController(service, mapper);
    org.mockito.Mockito.lenient().when(service.getModelClass()).thenReturn(ItemTypeModel.class);
  }

  @Test
  void searchReturnsFilteredPage() {
    var model = itemTypeModel(1, 1, "Consulting", "Consulting service");
    var dto = itemTypeDto(1, 1, "Consulting", "Consulting service");
    when(service.search(org.springframework.util.CollectionUtils.toMultiValueMap(java.util.Collections.emptyMap()),
        0, 10, "name,asc"))
        .thenReturn(new PageResult<>(List.of(model), new PageDetails(0, 10, 1, 1, "name,asc")));
    when(mapper.mapList(List.of(model))).thenReturn(List.of(dto));

    var response = controller.actionSearch(new org.springframework.util.LinkedMultiValueMap<>(), null,
        "1", 0, 10, "name,asc");
    Assertions.assertEquals(1, response.items().size());
    Assertions.assertEquals(dto, response.items().get(0));

    verify(service).search(org.springframework.util.CollectionUtils.toMultiValueMap(java.util.Collections.emptyMap()),
        0, 10, "name,asc");
  }

  @Test
  void getByIdReturnsItemType() {
    var model = itemTypeModel(1, 1, "Consulting", "Consulting service");
    var dto = itemTypeDto(1, 1, "Consulting", "Consulting service");
    when(service.findById(1)).thenReturn(Optional.of(model));
    when(mapper.map(model)).thenReturn(dto);

    var response = controller.actionFindById(1);
    Assertions.assertEquals(200, response.getStatusCode().value());
    Assertions.assertEquals(dto, response.getBody());
  }

  @Test
  void getByIdReturnsNotFoundWhenItemTypeDoesNotExist() {
    when(service.findById(99)).thenReturn(Optional.empty());

    Assertions.assertThrows(org.springframework.web.server.ResponseStatusException.class,
        () -> controller.actionFindById(99));
  }

  @Test
  void createReturnsCreatedItemType() {
    var requestModel = itemTypeModel(0, 1, "Support", "Support service");
    var savedModel = itemTypeModel(2, 1, "Support", "Support service");
    var savedDto = itemTypeDto(2, 1, "Support", "Support service");
    var requestDto = itemTypeDto(0, 1, "Support", "Support service");

    when(mapper.parseFrom(any(ItemTypeDto.class))).thenReturn(requestModel);
    when(service.save(requestModel)).thenReturn(savedModel);
    when(mapper.map(savedModel)).thenReturn(savedDto);

    var response = controller.actionCreateItem(requestDto);
    Assertions.assertEquals(201, response.getStatusCode().value());
    Assertions.assertEquals(savedDto, response.getBody());
  }

  @Test
  void deleteReturnsNoContent() {
    var model = itemTypeModel(1, 1, "Consulting", "Consulting service");
    when(service.findById(1)).thenReturn(Optional.of(model));

    controller.actionDeleteById(1);
    verify(service).deleteById(1);
  }

  private ItemTypeModel itemTypeModel(int id, int itemGroupId, String name, String description) {
    var model = new ItemTypeModel();
    model.setId(id);
    model.setItemGroupId(itemGroupId);
    model.setName(name);
    model.setDescription(description);
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");
    return model;
  }

  private ItemTypeDto itemTypeDto(int id, int itemGroupId, String name, String description) {
    return ItemTypeDto.builder()
        .id(id)
        .modified(LocalDateTime.of(2026, 9, 14, 22, 0))
        .modifiedBy("tester")
        .itemGroupId(itemGroupId)
        .name(name)
        .description(description)
        .build();
  }
}
