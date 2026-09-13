package hu.ps.ss.apiservice.controller;

import hu.ps.ss.apiservice.dto.ItemGroupDto;
import hu.ps.ss.apiservice.mapper.ItemGroupEditorMapper;
import hu.ps.ss.domain.ItemGroupModel;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.ItemGroupEditorPort;
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
@RequestMapping("/api/v1/item-groups")
@Tag(name = "Item Group Management",
    description = "REST API for managing item groups from the Basics menu.")
@SecurityRequirement(name = "bearerAuth")
public class ItemGroupEditorController extends
    AbstractModelController<ItemGroupModel, ItemGroupDto, Integer, ItemGroupEditorPort> {

  public ItemGroupEditorController(ItemGroupEditorPort service, ItemGroupEditorMapper mapper) {
    super(service, mapper);
  }

  @Operation(summary = "Search and list item groups",
      description = "Retrieves a paginated list of item groups with optional filtering and sorting.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved item group list",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public PageResult<ItemGroupDto> actionSearch(
      @Parameter(hidden = true) @RequestParam MultiValueMap<String, String> params,
      @Parameter(description = "Filter by item group name.", example = "Services")
      @RequestParam(required = false, value = "name") String name,
      @Parameter(description = "Filter by function code.", example = "1")
      @RequestParam(required = false, value = "function") String function,
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

  @Operation(summary = "Get item group by ID",
      description = "Retrieves an item group by its unique identifier.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved item group",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ItemGroupDto.class))),
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
  ResponseEntity<ItemGroupDto> actionFindById(
      @Parameter(description = "Unique identifier of the item group", required = true, example = "1")
      @PathVariable final Integer id
  ) {
    return super.getItemById(id);
  }

  @Operation(summary = "Create a new item group",
      description = "Creates an item group with name, function, and optional description.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Item group successfully created",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ItemGroupDto.class))),
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
  ResponseEntity<ItemGroupDto> actionCreateItem(
      @Parameter(description = "Item group data to create", required = true,
          schema = @Schema(implementation = ItemGroupDto.class))
      @RequestBody final ItemGroupDto dto
  ) {
    return super.createItem(dto);
  }

  @Operation(summary = "Delete item group by ID",
      description = "Deletes an item group record by its unique identifier.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Item group successfully deleted",
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
      @Parameter(description = "Unique identifier of the item group to delete", required = true,
          example = "1")
      @PathVariable final Integer id
  ) {
    this.deleteById(id);
  }
}
