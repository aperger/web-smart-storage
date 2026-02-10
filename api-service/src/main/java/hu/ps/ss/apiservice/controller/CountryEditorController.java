package hu.ps.ss.apiservice.controller;

import hu.ps.ss.apiservice.dto.CountryDto;
import hu.ps.ss.apiservice.mapper.CountryEditorMapper;
import hu.ps.ss.domain.CountryModel;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.CountryEditorPort;
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

@RestController()
@RequestMapping("/api/v1/countries")
@Tag(
    name = "Country Management",
    description = "REST API for managing countries used in addresses and Hungarian online invoices. " +
                  "Provides CRUD operations for country data including country codes and names."
)
@SecurityRequirement(name = "bearerAuth")
public class CountryEditorController extends
    AbstractModelController<CountryModel, CountryDto, CountryEditorPort> {

  public CountryEditorController(CountryEditorPort service, CountryEditorMapper mapper) {
    super(service, mapper);
  }

  @Operation(
      summary = "Search and list countries",
      description = "Retrieves a paginated list of countries with optional filtering and sorting. " +
                    "Supports filtering by country code, name, and other attributes. " +
                    "Used for address management and Hungarian online invoice processing."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved country list",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(
                  type = "object",
                  description = "Paginated result containing list of countries and pagination metadata",
                  example = """
                      {
                        "items": [
                          {
                            "id": 1,
                            "code": "HU",
                            "name": "Hungary",
                            "modified": "2026-02-10T14:30:00",
                            "modifiedBy": "admin@example.com"
                          }
                        ],
                        "pageDetails": {
                          "number": 0,
                          "size": 10,
                          "totalElements": 156,
                          "totalPages": 16,
                          "sortBy": "name,asc"
                        }
                      }
                      """
              )
          )
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized - User is not authenticated",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden - User does not have permission to access this resource",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error - An unexpected error occurred",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      )
  })
  @GetMapping(value = "", produces = "application/json")
  public PageResult<CountryDto> actionSearch(
      @Parameter(hidden = true)
      @RequestParam MultiValueMap<String, String> params,
      @Parameter(
          description = "Filter by country code (case-insensitive, partial match).",
          example = "HU",
          schema = @Schema(type = "string")
      )
      @RequestParam(required = false, value = "code") String code,
      @Parameter(
          description = "Filter by country name (case-insensitive, partial match).",
          example = "Hungary",
          schema = @Schema(type = "string")
      )
      @RequestParam(required = false, value = "name") String name,
      @Parameter(
          description = "Page index (zero-based)",
          example = "0",
          schema = @Schema(type = "integer", minimum = "0", defaultValue = "0")
      )
      @RequestParam(value = PARAM_PAGE_INDEX, defaultValue = "0") Integer pageIndex,
      @Parameter(
          description = "Number of items per page",
          example = "10",
          schema = @Schema(type = "integer", minimum = "1", maximum = "100", defaultValue = "10")
      )
      @RequestParam(value = PARAM_PAGE_SIZE, defaultValue = "10") Integer pageSize,
      @Parameter(
          description = "Sort criteria in format: property(,asc|desc). Multiple sort criteria are supported.",
          example = "name,asc",
          schema = @Schema(type = "string")
      )
      @RequestParam(required = false, value = PARAM_SORT) String sort
  ) {
    return this.search(params, pageIndex, pageSize, sort);
  }

  @Operation(
      summary = "Get country by ID",
      description = "Retrieves detailed information about a specific country by its unique identifier. " +
                    "Returns country code, name, and metadata for use in addresses and Hungarian online invoices."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved country details",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CountryDto.class)
          )
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized - User is not authenticated",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden - User does not have permission to access this resource",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not Found - Country with the specified ID does not exist",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error - An unexpected error occurred",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      )
  })
  @GetMapping("/{id}")
  ResponseEntity<CountryDto> actionFindById(
      @Parameter(
          description = "Unique identifier of the country",
          required = true,
          example = "1",
          schema = @Schema(type = "integer", minimum = "1")
      )
      @PathVariable final Integer id
  ) {
    return super.getItemById(id);
  }

  @Operation(
      summary = "Create a new country",
      description = "Creates a new country record with the provided country code and name. " +
                    "The created country can be used in address management and Hungarian online invoice processing. " +
                    "Requires valid country code (ISO standard recommended) and name."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Country successfully created",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CountryDto.class)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Bad Request - Invalid input data (e.g., missing required fields, invalid country code format)",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized - User is not authenticated",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden - User does not have permission to create countries",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "409",
          description = "Conflict - Country with the same code already exists",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error - An unexpected error occurred",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      )
  })
  @PostMapping("")
  ResponseEntity<CountryDto> actionCreateItem(
      @Parameter(
          description = "Country data to create. Must include country code and name.",
          required = true,
          schema = @Schema(implementation = CountryDto.class)
      )
      @RequestBody final CountryDto dto
  ) {
    return super.createItem(dto);
  }

  @Operation(
      summary = "Delete country by ID",
      description = "Deletes a country record by its unique identifier. " +
                    "Use with caution as this may affect existing addresses and Hungarian online invoice records. " +
                    "Consider archiving instead of hard deletion if the country is referenced elsewhere."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "204",
          description = "Country successfully deleted - No content returned",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized - User is not authenticated",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden - User does not have permission to delete countries",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not Found - Country with the specified ID does not exist",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "409",
          description = "Conflict - Country cannot be deleted because it is referenced by other records",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error - An unexpected error occurred",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      )
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void actionDeleteById(
      @Parameter(
          description = "Unique identifier of the country to delete",
          required = true,
          example = "1",
          schema = @Schema(type = "integer", minimum = "1")
      )
      @PathVariable final Integer id
  ) {
    this.deleteById(id);
  }
}
