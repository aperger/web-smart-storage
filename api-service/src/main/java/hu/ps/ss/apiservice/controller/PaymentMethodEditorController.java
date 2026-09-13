package hu.ps.ss.apiservice.controller;

import hu.ps.ss.apiservice.dto.PaymentMethodDto;
import hu.ps.ss.apiservice.mapper.PaymentMethodEditorMapper;
import hu.ps.ss.domain.PaymentMethodModel;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.PaymentMethodEditorPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment-methods")
@Tag(name = "Payment Method Management",
    description = "REST API for managing payment methods from the Basics menu.")
@SecurityRequirement(name = "bearerAuth")
public class PaymentMethodEditorController extends
    AbstractModelController<PaymentMethodModel, PaymentMethodDto, Integer, PaymentMethodEditorPort> {

  public PaymentMethodEditorController(
      PaymentMethodEditorPort service,
      PaymentMethodEditorMapper mapper
  ) {
    super(service, mapper);
  }

  @Operation(summary = "Search and list payment methods",
      description = "Retrieves a paginated list of payment methods with optional filtering and sorting.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved payment method list",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public PageResult<PaymentMethodDto> actionSearch(
      @Parameter(hidden = true) @RequestParam MultiValueMap<String, String> params,
      @Parameter(description = "Filter by payment method name.", example = "Transfer")
      @RequestParam(required = false, value = "name") String name,
      @Parameter(description = "Filter by due date in days.", example = "8")
      @RequestParam(required = false, value = "dueDate") String dueDate,
      @Parameter(description = "Page index (zero-based)", example = "0",
          schema = @Schema(type = "integer", minimum = "0"))
      @RequestParam(value = PARAM_PAGE_INDEX, defaultValue = "0") Integer pageIndex,
      @Parameter(description = "Number of items per page", example = "10",
          schema = @Schema(type = "integer", minimum = "1"))
      @RequestParam(value = PARAM_PAGE_SIZE, defaultValue = "10") Integer pageSize,
      @Parameter(description = "Sort criteria in format: property(,asc|desc)", example = "name,asc")
      @RequestParam(required = false, value = PARAM_SORT) String sort
  ) {
    return this.search(params, pageIndex, pageSize, sort);
  }

  @Operation(summary = "Get payment method by ID",
      description = "Retrieves a payment method by its unique identifier.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved payment method",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PaymentMethodDto.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "404", description = "Not Found",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping("/{id}")
  ResponseEntity<PaymentMethodDto> actionFindById(
      @Parameter(description = "Unique identifier of the payment method", required = true,
          example = "1")
      @PathVariable final Integer id
  ) {
    return super.getItemById(id);
  }

  @Operation(summary = "Create a new payment method",
      description = "Creates a payment method with name, due date, and invoice format.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Payment method successfully created",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PaymentMethodDto.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @PostMapping("")
  ResponseEntity<PaymentMethodDto> actionCreateItem(
      @Parameter(description = "Payment method data to create", required = true,
          schema = @Schema(implementation = PaymentMethodDto.class))
      @RequestBody final PaymentMethodDto dto
  ) {
    return super.createItem(dto);
  }

  @Operation(summary = "Delete payment method by ID",
      description = "Deletes a payment method record by its unique identifier.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Payment method successfully deleted",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "404", description = "Not Found",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void actionDeleteById(
      @Parameter(description = "Unique identifier of the payment method to delete", required = true,
          example = "1")
      @PathVariable final Integer id
  ) {
    this.deleteById(id);
  }
}
