package hu.ps.ss.apiservice.controller;

import hu.ps.ss.apiservice.dto.ItemPropertyDto;
import hu.ps.ss.apiservice.mapper.ItemPropertyEditorMapper;
import hu.ps.ss.domain.ItemPropertyModel;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.ItemPropertyEditorPort;
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
@RequestMapping("/api/v1/item-properties")
@Tag(name = "Item Property Management",
    description = "REST API for managing special item property definitions from the Basics menu.")
@SecurityRequirement(name = "bearerAuth")
public class ItemPropertyEditorController extends
    AbstractModelController<ItemPropertyModel, ItemPropertyDto, Integer, ItemPropertyEditorPort> {

  public ItemPropertyEditorController(
      ItemPropertyEditorPort service,
      ItemPropertyEditorMapper mapper
  ) {
    super(service, mapper);
  }

  @Operation(summary = "Search and list item properties",
      description = "Retrieves a paginated list of item property definitions with optional filtering and sorting.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved item property list",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public PageResult<ItemPropertyDto> actionSearch(
      @Parameter(hidden = true) @RequestParam MultiValueMap<String, String> params,
      @Parameter(description = "Filter by property name.", example = "Serial number")
      @RequestParam(required = false, value = "name") String name,
      @Parameter(description = "Filter by property type.", example = "1")
      @RequestParam(required = false, value = "propertyType") String propertyType,
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

  @Operation(summary = "Get item property by ID",
      description = "Retrieves an item property definition by its unique identifier.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved item property",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ItemPropertyDto.class))),
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
  ResponseEntity<ItemPropertyDto> actionFindById(
      @Parameter(description = "Unique identifier of the item property", required = true, example = "1")
      @PathVariable final Integer id
  ) {
    return super.getItemById(id);
  }

  @Operation(summary = "Create a new item property",
      description = "Creates an item property definition with name, description, and property type.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Item property successfully created",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ItemPropertyDto.class))),
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
  ResponseEntity<ItemPropertyDto> actionCreateItem(
      @Parameter(description = "Item property data to create", required = true,
          schema = @Schema(implementation = ItemPropertyDto.class))
      @RequestBody final ItemPropertyDto dto
  ) {
    return super.createItem(dto);
  }

  @Operation(summary = "Delete item property by ID",
      description = "Deletes an item property definition by its unique identifier.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Item property successfully deleted",
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
      @Parameter(description = "Unique identifier of the item property to delete", required = true,
          example = "1")
      @PathVariable final Integer id
  ) {
    this.deleteById(id);
  }
}
