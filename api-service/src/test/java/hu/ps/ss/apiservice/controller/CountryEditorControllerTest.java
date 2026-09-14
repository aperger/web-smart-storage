package hu.ps.ss.apiservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToController;

import hu.ps.ss.apiservice.dto.CountryDto;
import hu.ps.ss.apiservice.mapper.CountryEditorMapper;
import hu.ps.ss.domain.CountryModel;
import hu.ps.ss.domain.pojo.PageDetails;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.CountryEditorPort;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(MockitoExtension.class)
class CountryEditorControllerTest {

  @Mock
  private CountryEditorPort service;

  @Mock
  private CountryEditorMapper mapper;

  private WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    webTestClient = bindToController(new CountryEditorController(service, mapper)).build();
  }

  @Test
  void searchReturnsFilteredPage() {
    var model = countryModel(1, "HU", "Hungary");
    var dto = countryDto(1, "HU", "Hungary");
    when(service.search(argThat(params -> "Hungary".equals(params.get("name").getFirst())), eq(0), eq(10),
        eq("name,asc")))
        .thenReturn(new PageResult<>(List.of(model), new PageDetails(0, 10, 1, 1, "name,asc")));
    when(mapper.mapList(List.of(model))).thenReturn(List.of(dto));

    webTestClient.get()
        .uri("/api/v1/countries?name=Hungary&pageIndex=0&pageSize=10&sort=name,asc")
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.items[0].id").isEqualTo(1)
        .jsonPath("$.items[0].code").isEqualTo("HU")
        .jsonPath("$.items[0].name").isEqualTo("Hungary")
        .jsonPath("$.pageDetails.totalElements").isEqualTo(1);

    verify(service).search(
        argThat(params -> "Hungary".equals(params.get("name").getFirst())),
        eq(0),
        eq(10),
        eq("name,asc"));
  }

  @Test
  void getByIdReturnsCountry() {
    var model = countryModel(1, "HU", "Hungary");
    var dto = countryDto(1, "HU", "Hungary");
    when(service.findById(1)).thenReturn(Optional.of(model));
    when(mapper.map(model)).thenReturn(dto);

    webTestClient.get()
        .uri("/api/v1/countries/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isEqualTo(1)
        .jsonPath("$.code").isEqualTo("HU");
  }

  @Test
  void getByIdReturnsNotFoundWhenCountryDoesNotExist() {
    when(service.findById(99)).thenReturn(Optional.empty());

    webTestClient.get()
        .uri("/api/v1/countries/99")
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  void createReturnsCreatedCountry() {
    var requestModel = countryModel(0, "RO", "Romania");
    var savedModel = countryModel(2, "RO", "Romania");
    var savedDto = countryDto(2, "RO", "Romania");

    when(mapper.parseFrom(any(CountryDto.class))).thenReturn(requestModel);
    when(service.save(requestModel)).thenReturn(savedModel);
    when(mapper.map(savedModel)).thenReturn(savedDto);

    webTestClient.post()
        .uri("/api/v1/countries")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("""
            {
              "code": "RO",
              "name": "Romania"
            }
            """)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .jsonPath("$.id").isEqualTo(2)
        .jsonPath("$.code").isEqualTo("RO");
  }

  @Test
  void deleteReturnsNoContent() {
    var model = countryModel(1, "HU", "Hungary");
    when(service.findById(1)).thenReturn(Optional.of(model));

    webTestClient.delete()
        .uri("/api/v1/countries/1")
        .exchange()
        .expectStatus().isNoContent();

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
    var dto = new CountryDto(code, name);
    dto.setId(id);
    dto.setModified(LocalDateTime.of(2026, 9, 14, 22, 0));
    dto.setModifiedBy("tester");
    return dto;
  }
}
