package hu.ps.ss.apiservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hu.ps.ss.apiservice.dto.VatKeyDto;
import hu.ps.ss.apiservice.mapper.VatKeyEditorMapper;
import hu.ps.ss.domain.VatKeyModel;
import hu.ps.ss.domain.pojo.PageDetails;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.VatKeyEditorPort;
import java.math.BigDecimal;
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
class VatKeyEditorControllerTest {

  @Mock
  private VatKeyEditorPort service;

  @Mock
  private VatKeyEditorMapper mapper;

  private VatKeyEditorController controller;

  @BeforeEach
  void setUp() {
    controller = new VatKeyEditorController(service, mapper);
    org.mockito.Mockito.lenient().when(service.getModelClass()).thenReturn(VatKeyModel.class);
  }

  @Test
  void searchReturnsFilteredPage() {
    var model = vatKeyModel(1, new BigDecimal("27.00"), 0, "27%");
    var dto = vatKeyDto(1, new BigDecimal("27.00"), 0, "27%");
    when(service.search(org.springframework.util.CollectionUtils.toMultiValueMap(java.util.Collections.emptyMap()),
        0, 10, "name,asc"))
        .thenReturn(new PageResult<>(List.of(model), new PageDetails(0, 10, 1, 1, "name,asc")));
    when(mapper.mapList(List.of(model))).thenReturn(List.of(dto));

    var response = controller.actionSearch(new org.springframework.util.LinkedMultiValueMap<>(), null,
        "27%", null, 0, 10, "name,asc");
    Assertions.assertEquals(1, response.items().size());
    Assertions.assertEquals(dto, response.items().get(0));

    verify(service).search(org.springframework.util.CollectionUtils.toMultiValueMap(java.util.Collections.emptyMap()),
        0, 10, "name,asc");
  }

  @Test
  void getByIdReturnsVatKey() {
    var model = vatKeyModel(1, new BigDecimal("27.00"), 0, "27%");
    var dto = vatKeyDto(1, new BigDecimal("27.00"), 0, "27%");
    when(service.findById(1)).thenReturn(Optional.of(model));
    when(mapper.map(model)).thenReturn(dto);

    var response = controller.actionFindById(1);
    Assertions.assertEquals(200, response.getStatusCode().value());
    Assertions.assertEquals(dto, response.getBody());
  }

  @Test
  void getByIdReturnsNotFoundWhenVatKeyDoesNotExist() {
    when(service.findById(99)).thenReturn(Optional.empty());

    Assertions.assertThrows(org.springframework.web.server.ResponseStatusException.class,
        () -> controller.actionFindById(99));
  }

  @Test
  void createReturnsCreatedVatKey() {
    var requestModel = vatKeyModel(0, new BigDecimal("5.00"), 1, "AAM");
    var savedModel = vatKeyModel(2, new BigDecimal("5.00"), 1, "AAM");
    var savedDto = vatKeyDto(2, new BigDecimal("5.00"), 1, "AAM");
    var requestDto = vatKeyDto(0, new BigDecimal("5.00"), 1, "AAM");

    when(mapper.parseFrom(any(VatKeyDto.class))).thenReturn(requestModel);
    when(service.save(requestModel)).thenReturn(savedModel);
    when(mapper.map(savedModel)).thenReturn(savedDto);

    var response = controller.actionCreateItem(requestDto);
    Assertions.assertEquals(201, response.getStatusCode().value());
    Assertions.assertEquals(savedDto, response.getBody());
  }

  @Test
  void deleteReturnsNoContent() {
    var model = vatKeyModel(1, new BigDecimal("27.00"), 0, "27%");
    when(service.findById(1)).thenReturn(Optional.of(model));

    controller.actionDeleteById(1);
    verify(service).deleteById(1);
  }

  private VatKeyModel vatKeyModel(int id, BigDecimal value, int exemption, String name) {
    var model = new VatKeyModel();
    model.setId(id);
    model.setValue(value);
    model.setExemption(exemption);
    model.setName(name);
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");
    return model;
  }

  private VatKeyDto vatKeyDto(int id, BigDecimal value, int exemption, String name) {
    return VatKeyDto.builder()
        .id(id)
        .modified(LocalDateTime.of(2026, 9, 14, 22, 0))
        .modifiedBy("tester")
        .value(value)
        .exemption(exemption)
        .name(name)
        .build();
  }
}
