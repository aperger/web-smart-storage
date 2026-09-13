package hu.ps.ss.apiservice.controller;

import hu.ps.ss.apiservice.dto.VatKeyDto;
import hu.ps.ss.apiservice.mapper.VatKeyEditorMapper;
import hu.ps.ss.domain.VatKeyModel;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.VatKeyEditorPort;
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
@RequestMapping("/api/v1/vat-keys")
@Tag(
    name = "VAT Key Management",
    description = "REST API for managing VAT keys and tax rates used in invoice calculations and Hungarian NAV documents."
)
@SecurityRequirement(name = "bearerAuth")
public class VatKeyEditorController extends
    AbstractModelController<VatKeyModel, VatKeyDto, Integer, VatKeyEditorPort> {

  public VatKeyEditorController(VatKeyEditorPort service, VatKeyEditorMapper mapper) {
    super(service, mapper);
  }

  @Operation(
      summary = "Search and list VAT keys",
      description = "Retrieves a paginated list of VAT keys with optional filtering and sorting."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved VAT key list",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping(value = "", produces = "application/json")
  public PageResult<VatKeyDto> actionSearch(
      @Parameter(hidden = true)
      @RequestParam MultiValueMap<String, String> params,
      @Parameter(description = "Filter by VAT key value.", example = "27.00")
      @RequestParam(required = false, value = "value") String value,
      @Parameter(description = "Filter by VAT key name.", example = "27%")
      @RequestParam(required = false, value = "name") String name,
      @Parameter(description = "Filter by exemption flag.", example = "0")
      @RequestParam(required = false, value = "exemption") String exemption,
      @Parameter(description = "Page index (zero-based)", example = "0", schema = @Schema(type = "integer", minimum = "0"))
      @RequestParam(value = PARAM_PAGE_INDEX, defaultValue = "0") Integer pageIndex,
      @Parameter(description = "Number of items per page", example = "10", schema = @Schema(type = "integer", minimum = "1"))
      @RequestParam(value = PARAM_PAGE_SIZE, defaultValue = "10") Integer pageSize,
      @Parameter(description = "Sort criteria in format: property(,asc|desc)", example = "name,asc")
      @RequestParam(required = false, value = PARAM_SORT) String sort
  ) {
    return this.search(params, pageIndex, pageSize, sort);
  }

  @Operation(
      summary = "Get VAT key by ID",
      description = "Retrieves a VAT key by its unique identifier."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved VAT key",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VatKeyDto.class))
      ),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping("/{id}")
  ResponseEntity<VatKeyDto> actionFindById(
      @Parameter(description = "Unique identifier of the VAT key", required = true, example = "1")
      @PathVariable final Integer id
  ) {
    return super.getItemById(id);
  }

  @Operation(
      summary = "Create a new VAT key",
      description = "Creates a VAT key with the provided rate value, exemption flag and name."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "VAT key successfully created",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VatKeyDto.class))
      ),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @PostMapping("")
  ResponseEntity<VatKeyDto> actionCreateItem(
      @Parameter(description = "VAT key data to create", required = true, schema = @Schema(implementation = VatKeyDto.class))
      @RequestBody final VatKeyDto dto
  ) {
    return super.createItem(dto);
  }

  @Operation(
      summary = "Delete VAT key by ID",
      description = "Deletes a VAT key record by its unique identifier."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "VAT key successfully deleted", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void actionDeleteById(
      @Parameter(description = "Unique identifier of the VAT key to delete", required = true, example = "1")
      @PathVariable final Integer id
  ) {
    this.deleteById(id);
  }
}
