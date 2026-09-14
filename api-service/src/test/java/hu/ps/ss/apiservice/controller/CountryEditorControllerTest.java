package hu.ps.ss.apiservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import hu.ps.ss.apiservice.dto.CountryDto;
import hu.ps.ss.apiservice.controller.CountryEditorController;
import hu.ps.ss.apiservice.mapper.CountryEditorMapper;
import hu.ps.ss.domain.CountryModel;
import hu.ps.ss.domain.pojo.PageDetails;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.CountryEditorPort;
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
class CountryEditorControllerTest {

  @Mock
  private CountryEditorPort service;

  @Mock
  private CountryEditorMapper mapper;

  private CountryEditorController controller;

  @BeforeEach
  void setUp() {
    controller = new CountryEditorController(service, mapper);
    org.mockito.Mockito.lenient().when(service.getModelClass()).thenReturn(CountryModel.class);
  }

  @Test
  void searchReturnsFilteredPage() {
    var model = countryModel(1, "HU", "Hungary");
    var dto = countryDto(1, "HU", "Hungary");
    when(service.search(org.springframework.util.CollectionUtils.toMultiValueMap(java.util.Collections.emptyMap()),
        0, 10, "name,asc"))
        .thenReturn(new PageResult<>(List.of(model), new PageDetails(0, 10, 1, 1, "name,asc")));
    when(mapper.mapList(List.of(model))).thenReturn(List.of(dto));

    var response = controller.actionSearch(new org.springframework.util.LinkedMultiValueMap<>(), null,
        "Hungary", 0, 10, "name,asc");
    Assertions.assertEquals(1, response.items().size());
    Assertions.assertEquals(dto, response.items().get(0));

    verify(service).search(org.springframework.util.CollectionUtils.toMultiValueMap(java.util.Collections.emptyMap()),
        0, 10, "name,asc");
  }

  @Test
  void getByIdReturnsCountry() {
    var model = countryModel(1, "HU", "Hungary");
    var dto = countryDto(1, "HU", "Hungary");
    when(service.findById(1)).thenReturn(Optional.of(model));
    when(mapper.map(model)).thenReturn(dto);

    var response = controller.actionFindById(1);
    Assertions.assertEquals(200, response.getStatusCode().value());
    Assertions.assertEquals(dto, response.getBody());
  }

  @Test
  void getByIdReturnsNotFoundWhenCountryDoesNotExist() {
    when(service.findById(99)).thenReturn(Optional.empty());

    Assertions.assertThrows(org.springframework.web.server.ResponseStatusException.class,
        () -> controller.actionFindById(99));
  }

  @Test
  void createReturnsCreatedCountry() {
    var requestModel = countryModel(0, "RO", "Romania");
    var savedModel = countryModel(2, "RO", "Romania");
    var savedDto = countryDto(2, "RO", "Romania");
    var requestDto = countryDto(0, "RO", "Romania");

    when(mapper.parseFrom(any(CountryDto.class))).thenReturn(requestModel);
    when(service.save(requestModel)).thenReturn(savedModel);
    when(mapper.map(savedModel)).thenReturn(savedDto);

    var response = controller.actionCreateItem(requestDto);
    Assertions.assertEquals(201, response.getStatusCode().value());
    Assertions.assertEquals(savedDto, response.getBody());
  }

  @Test
  void deleteReturnsNoContent() {
    var model = countryModel(1, "HU", "Hungary");
    when(service.findById(1)).thenReturn(Optional.of(model));

    controller.actionDeleteById(1);
    verify(service).deleteById(1);
  }

  private CountryModel countryModel(int id, String code, String name) {
    var model = new CountryModel();
    model.setId(id);
    model.setCode(code);
    model.setName(name);
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");
    return model;
  }

  private CountryDto countryDto(int id, String code, String name) {
    return CountryDto.builder()
        .id(id)
        .modified(LocalDateTime.of(2026, 9, 14, 22, 0))
        .modifiedBy("tester")
        .code(code)
        .name(name)
        .build();
  }
}
