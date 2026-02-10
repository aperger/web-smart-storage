package hu.ps.ss.infra.database.service;

import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.infra.database.repository.BaseRepository;
import jakarta.annotation.Nullable;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@RequiredArgsConstructor
@Getter
public abstract class AbstractEntityService<E, M, ID> {

  public static final String PARAM_PAGE_INDEX = "page";
  public static final String PARAM_PAGE_SIZE = "size";
  public static final String PARAM_SORT = "sort";

  private final ObjectMapperBase<E, M> mapper;
  private final BaseRepository<E, ID> repository;

  public abstract Class<E> getEntityClass();

  public List<M> findAll() {
    return getMapper().mapList(getRepository().findAll());
  }

  public Optional<M> getItemById(final ID id) {
    return getRepository().findById(id).map(e -> getMapper().map(e));
  }

  public Page<M> searchPaginated(
      MultiValueMap<String, String> queryParams,
      Integer pageIndex,
      Integer pageSize,
      @Nullable String sortBy
  ) {
    log.trace("Search for {}, queryParams: {}", this.getClass().getSimpleName(), queryParams);
    var sort = this.parseSortParam(sortBy);
    Pageable pageable = Objects.nonNull(sort) ? PageRequest.of(pageIndex, pageSize, sort) :
        PageRequest.of(pageIndex, pageSize);
    var page = searchForEntities(queryParams, pageable);
    return mapPage(page);
  }

  public List<M> search(
      MultiValueMap<String, String> queryParams,
      String sortBy
  ) {
    log.trace("Search for {}, queryParams: {}", this.getClass().getSimpleName(), queryParams);
    var sort = this.parseSortParam(sortBy);
    var list = searchForEntities(queryParams, sort);
    return mapper.mapList(list);
  }

  private Page<M> mapPage(Page<E> page) {
    Pageable pageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
    return new PageImpl<>(getMapper().mapList(page.getContent()), pageable,
        page.getTotalElements());
  }

  private Page<E> searchForEntities(MultiValueMap<String, String> params, final Pageable pageable) {
    params.remove(PARAM_PAGE_INDEX);
    params.remove(PARAM_PAGE_SIZE);
    params.remove(PARAM_SORT);
    Specification<E> specification = createSearchSpecification(params);
    return getRepository().findAll(Specification.where(specification), pageable);
  }

  private List<E> searchForEntities(MultiValueMap<String, String> params, final Sort sort) {
    params.remove(PARAM_PAGE_INDEX);
    params.remove(PARAM_PAGE_SIZE);
    params.remove(PARAM_SORT);
    Specification<E> specification = createSearchSpecification(params);
    return getRepository().findAll(Specification.where(specification), sort);
  }

  abstract Specification<E> createSearchSpecification(MultiValueMap<String, String> params);

  /**
   * Need this mapping when the incoming sort value is not a DB field
   *
   * @param originalName incoming query parameter
   * @return the mapped name
   */
  protected String mapSortKeys(final String originalName) {
    return originalName;
  }

  protected String getDecodedUrlValue(String firstValue) {
    return URLDecoder.decode(firstValue, StandardCharsets.UTF_8);
  }

  private Sort.Direction extractSortDirection(String[] sortProperties) {
    Sort.Direction sortDirection = Sort.Direction.ASC;
    if (sortProperties.length > 1) {
      sortDirection =
          "DESC".equalsIgnoreCase(sortProperties[1]) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
    return sortDirection;
  }

  public final Sort parseSortParam(String sortParam) {
    log.trace("Parse sor value: {}", sortParam);
    String[] sortProperties = Objects.nonNull(sortParam) && !sortParam.isEmpty() ?
        sortParam.split(",") : new String[]{};
    if (sortProperties.length == 0) {
      return null;
    }
    String sortBy = mapSortKeys(sortProperties[0]);
    var direction = extractSortDirection(sortProperties);
    return Sort.by(direction, sortBy);
  }


  public M saveItem(M item) {
    log.trace("Save a {}: {}", this.getClass().getSimpleName(), item);
    return getMapper().map(getRepository().save(mapper.parseFrom(item)));
  }

  public void deleteItem(M item) {
    log.trace("Delete a {}: {}", this.getClass().getSimpleName(), item);
    getRepository().delete(mapper.parseFrom(item));
  }

  public void deleteItemById(ID id) {
    log.trace("Delete by id (#{}) an {}", id, this.getClass().getSimpleName());
    getRepository().deleteById(id);
  }

  public MultiValueMap<String, String> mapToMultivalueMap(Map<String, List<String>> params) {
    return new LinkedMultiValueMap<>(params);
  }

}
