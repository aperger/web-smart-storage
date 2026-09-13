package hu.ps.ss.apiservice.controller;

import hu.ps.ss.apiservice.dto.PartnerDto;
import hu.ps.ss.apiservice.mapper.PartnerEditorMapper;
import hu.ps.ss.domain.PartnerModel;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.PartnerEditorPort;
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
@RequestMapping("/api/v1/partners")
@Tag(name = "Partner Management",
    description = "REST API for managing partners from the Basics menu.")
@SecurityRequirement(name = "bearerAuth")
public class PartnerEditorController extends
    AbstractModelController<PartnerModel, PartnerDto, Integer, PartnerEditorPort> {

  public PartnerEditorController(PartnerEditorPort service, PartnerEditorMapper mapper) {
    super(service, mapper);
  }

  @Operation(summary = "Search and list partners",
      description = "Retrieves a paginated list of partners with optional filtering and sorting.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved partner list",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public PageResult<PartnerDto> actionSearch(
      @Parameter(hidden = true) @RequestParam MultiValueMap<String, String> params,
      @Parameter(description = "Filter by partner name.", example = "Example Ltd.")
      @RequestParam(required = false, value = "name") String name,
      @Parameter(description = "Filter by tax number.", example = "12345678-1-12")
      @RequestParam(required = false, value = "taxNumber") String taxNumber,
      @Parameter(description = "Filter by country code.", example = "HU")
      @RequestParam(required = false, value = "countryCode") String countryCode,
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

  @Operation(summary = "Get partner by ID",
      description = "Retrieves a partner by its unique identifier.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved partner",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PartnerDto.class))),
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
  ResponseEntity<PartnerDto> actionFindById(
      @Parameter(description = "Unique identifier of the partner", required = true, example = "1")
      @PathVariable final Integer id
  ) {
    return super.getItemById(id);
  }

  @Operation(summary = "Create a new partner",
      description = "Creates a partner with address, contact, and default billing settings.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Partner successfully created",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PartnerDto.class))),
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
  ResponseEntity<PartnerDto> actionCreateItem(
      @Parameter(description = "Partner data to create", required = true,
          schema = @Schema(implementation = PartnerDto.class))
      @RequestBody final PartnerDto dto
  ) {
    return super.createItem(dto);
  }

  @Operation(summary = "Delete partner by ID",
      description = "Deletes a partner record by its unique identifier.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Partner successfully deleted",
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
      @Parameter(description = "Unique identifier of the partner to delete", required = true,
          example = "1")
      @PathVariable final Integer id
  ) {
    this.deleteById(id);
  }
}
