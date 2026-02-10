package hu.ps.ss.infra.database;

import hu.ps.ss.domain.pojo.PageDetails;
import hu.ps.ss.domain.pojo.PageResult;
import hu.ps.ss.domain.ports.basic.BaseModelPort;
import hu.ps.ss.infra.database.service.AbstractEntityService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


/**
 * Generic implementation of domain port (interface) to get, save, search and delete entities of
 * domain models
 *
 * @param <E> JPA entity class
 * @param <M> Domain model Class
 * @param <I> Class of identifier of JPA entity
 */
@Getter
@RequiredArgsConstructor
@Transactional
public class AbstractModelAdapter<E, M, I> implements BaseModelPort<M, I> {

  private final AbstractEntityService<E, M, I> service;

  protected MultiValueMap<String, String> mapToMultivalueMap(Map<String, List<String>> params) {
    return new LinkedMultiValueMap<>(params);
  }

  @Override
  public List<M> findAll() {
    return service.findAll();
  }

  @Override
  public Optional<M> findById(I id) {
    return service.getItemById(id);
  }

  @Override
  public PageResult<M> search(Map<String, List<String>> params, Integer pageIndex,
      Integer pageSize, String sortBy) {

    var page = service.searchPaginated(mapToMultivalueMap(params), pageIndex, pageSize, sortBy);
    var pageDetails = new PageDetails(pageIndex, pageSize, page.getTotalElements(),
        page.getTotalPages(), sortBy);

    return new PageResult<M>(page.getContent(), pageDetails);
  }


  @Override
  public List<M> search(Map<String, List<String>> params, String sortBy) {
    return service.search(mapToMultivalueMap(params), sortBy);
  }


  @Override
  public M save(M item) {
    return service.saveItem(item);
  }

  @Override
  public void deleteById(I id) {
    service.deleteItemById(id);
  }

  @Override
  public void delete(M item) {
    service.deleteItem(item);
  }
}
