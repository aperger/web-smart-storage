package hu.ps.ss.apiservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hu.ps.ss.apiservice.dto.PaymentMethodDto;
import hu.ps.ss.apiservice.mapper.PaymentMethodEditorMapper;
import hu.ps.ss.domain.PaymentMethodModel;
import hu.ps.ss.domain.pojo.PageDetails;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.PaymentMethodEditorPort;
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
class PaymentMethodEditorControllerTest {

  @Mock
  private PaymentMethodEditorPort service;

  @Mock
  private PaymentMethodEditorMapper mapper;

  private PaymentMethodEditorController controller;

  @BeforeEach
  void setUp() {
    controller = new PaymentMethodEditorController(service, mapper);
    org.mockito.Mockito.lenient().when(service.getModelClass()).thenReturn(PaymentMethodModel.class);
  }

  @Test
  void searchReturnsFilteredPage() {
    var model = paymentMethodModel(1, "Transfer", 8, 1);
    var dto = paymentMethodDto(1, "Transfer", 8, 1);
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
  void getByIdReturnsPaymentMethod() {
    var model = paymentMethodModel(1, "Transfer", 8, 1);
    var dto = paymentMethodDto(1, "Transfer", 8, 1);
    when(service.findById(1)).thenReturn(Optional.of(model));
    when(mapper.map(model)).thenReturn(dto);

    var response = controller.actionFindById(1);
    Assertions.assertEquals(200, response.getStatusCode().value());
    Assertions.assertEquals(dto, response.getBody());
  }

  @Test
  void getByIdReturnsNotFoundWhenPaymentMethodDoesNotExist() {
    when(service.findById(99)).thenReturn(Optional.empty());

    Assertions.assertThrows(org.springframework.web.server.ResponseStatusException.class,
        () -> controller.actionFindById(99));
  }

  @Test
  void createReturnsCreatedPaymentMethod() {
    var requestModel = paymentMethodModel(0, "Card", 14, 3);
    var savedModel = paymentMethodModel(2, "Card", 14, 3);
    var savedDto = paymentMethodDto(2, "Card", 14, 3);
    var requestDto = paymentMethodDto(0, "Card", 14, 3);

    when(mapper.parseFrom(any(PaymentMethodDto.class))).thenReturn(requestModel);
    when(service.save(requestModel)).thenReturn(savedModel);
    when(mapper.map(savedModel)).thenReturn(savedDto);

    var response = controller.actionCreateItem(requestDto);
    Assertions.assertEquals(201, response.getStatusCode().value());
    Assertions.assertEquals(savedDto, response.getBody());
  }

  @Test
  void deleteReturnsNoContent() {
    var model = paymentMethodModel(1, "Transfer", 8, 1);
    when(service.findById(1)).thenReturn(Optional.of(model));

    controller.actionDeleteById(1);
    verify(service).deleteById(1);
  }

  private PaymentMethodModel paymentMethodModel(int id, String name, int dueDate, int invoiceFormat) {
    var model = new PaymentMethodModel();
    model.setId(id);
    model.setName(name);
    model.setDueDate(dueDate);
    model.setInvoiceFormat(invoiceFormat);
    model.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    model.setModifiedBy("tester");
    return model;
  }

  private PaymentMethodDto paymentMethodDto(int id, String name, int dueDate, int invoiceFormat) {
    return PaymentMethodDto.builder()
        .id(id)
        .modified(LocalDateTime.of(2026, 9, 14, 22, 0))
        .modifiedBy("tester")
        .name(name)
        .dueDate(dueDate)
        .invoiceFormat(invoiceFormat)
        .build();
  }
}
