package hu.ps.ss.apiservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hu.ps.ss.apiservice.dto.CurrencyDto;
import hu.ps.ss.apiservice.mapper.CurrencyEditorMapper;
import hu.ps.ss.domain.CurrencyModel;
import hu.ps.ss.domain.pojo.PageDetails;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.CurrencyEditorPort;
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
class CurrencyEditorControllerTest {

  @Mock
  private CurrencyEditorPort service;

  @Mock
  private CurrencyEditorMapper mapper;

  private CurrencyEditorController controller;

  @BeforeEach
  void setUp() {
    controller = new CurrencyEditorController(service, mapper);
    org.mockito.Mockito.lenient().when(service.getModelClass()).thenReturn(CurrencyModel.class);
  }

  @Test
  void searchReturnsFilteredPage() {
    var model = currencyModel(1, "HUF", "Ft");
    var dto = currencyDto(1, "HUF", "Ft");
    when(service.search(org.springframework.util.CollectionUtils.toMultiValueMap(java.util.Collections.emptyMap()),
        0, 10, "code,asc"))
        .thenReturn(new PageResult<>(List.of(model), new PageDetails(0, 10, 1, 1, "code,asc")));
    when(mapper.mapList(List.of(model))).thenReturn(List.of(dto));

    var response = controller.actionSearch(new org.springframework.util.LinkedMultiValueMap<>(), null,
        null, 0, 10, "code,asc");
    Assertions.assertEquals(1, response.items().size());
    Assertions.assertEquals(dto, response.items().get(0));

    verify(service).search(org.springframework.util.CollectionUtils.toMultiValueMap(java.util.Collections.emptyMap()),
        0, 10, "code,asc");
  }

  @Test
  void getByIdReturnsCurrency() {
    var model = currencyModel(1, "HUF", "Ft");
    var dto = currencyDto(1, "HUF", "Ft");
    when(service.findById(1)).thenReturn(Optional.of(model));
    when(mapper.map(model)).thenReturn(dto);

    var response = controller.actionFindById(1);
    Assertions.assertEquals(200, response.getStatusCode().value());
    Assertions.assertEquals(dto, response.getBody());
  }

  @Test
  void getByIdReturnsNotFoundWhenCurrencyDoesNotExist() {
    when(service.findById(99)).thenReturn(Optional.empty());

    Assertions.assertThrows(org.springframework.web.server.ResponseStatusException.class,
        () -> controller.actionFindById(99));
  }

  @Test
  void createReturnsCreatedCurrency() {
    var requestModel = currencyModel(0, "CHF", "CHF");
    var savedModel = currencyModel(2, "CHF", "CHF");
    var savedDto = currencyDto(2, "CHF", "CHF");
    var requestDto = currencyDto(0, "CHF", "CHF");

    when(mapper.parseFrom(any(CurrencyDto.class))).thenReturn(requestModel);
    when(service.save(requestModel)).thenReturn(savedModel);
    when(mapper.map(savedModel)).thenReturn(savedDto);

    var response = controller.actionCreateItem(requestDto);
    Assertions.assertEquals(201, response.getStatusCode().value());
    Assertions.assertEquals(savedDto, response.getBody());
  }

  @Test
  void deleteReturnsNoContent() {
    var model = currencyModel(1, "HUF", "Ft");
    when(service.findById(1)).thenReturn(Optional.of(model));

    controller.actionDeleteById(1);
    verify(service).deleteById(1);
  }

  private CurrencyModel currencyModel(int id, String code, String symbol) {
    var model = new CurrencyModel();
    model.setId(id);
    model.setCode(code);
    model.setSymbol(symbol);
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");
    return model;
  }

  private CurrencyDto currencyDto(int id, String code, String symbol) {
    return CurrencyDto.builder()
        .id(id)
        .modified(LocalDateTime.of(2026, 9, 14, 22, 0))
        .modifiedBy("tester")
        .code(code)
        .symbol(symbol)
        .build();
  }
}
