package hu.ps.ss.apiservice.controller;

import hu.ps.ss.data.mappers.ObjectMapperBase;
import hu.ps.ss.data.mappers.ObjectMapperOneWay;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.BaseModelPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractModelController<M, D, S extends BaseModelPort<M, Integer>> {

  public static final String PARAM_PAGE_INDEX = "pageIndex";
  public static final String PARAM_PAGE_SIZE = "pageSize";
  public static final String PARAM_SORT = "sort";

  protected final S service;
  protected final ObjectMapperBase<M, D> mapper;

  protected PageResult<D> mapPageResult(PageResult<M> pageResultModel) {
    return new PageResult<D>(mapper.mapList(pageResultModel.items()),
        pageResultModel.pageDetails());
  }

  public PageResult<D> search(
      MultiValueMap<String, String> params,
      Integer pageIndex,
      Integer pageSize,
      String sort
  ) {
    var pageable = PageRequest.of(pageIndex, pageSize);
    return mapPageResult(service.search(params, pageIndex, pageSize, sort));
  }

  ResponseEntity<D> getItemById(final Integer id) {
    var item = throwStatusExceptionIfNotFound(id);
    return ResponseEntity.ok(mapper.map(item));
  }

  ResponseEntity<D> createItem(D item) {
    var model = mapper.parseFrom(item);
    var savedItem = service.save(model);
    return new ResponseEntity<>(mapper.map(savedItem), HttpStatus.CREATED);
  }

  void deleteById(final Integer id) {
    throwStatusExceptionIfNotFound(id);
    service.deleteById(id);
  }

  private @NonNull M throwStatusExceptionIfNotFound(Integer id) {
    var optional = service.findById(id);
    if (optional.isEmpty()) {
      var message = service.getModelClass().getSimpleName() + " was not found by id: " + id;
      log.warn(message);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }
    return optional.get();
  }
}
