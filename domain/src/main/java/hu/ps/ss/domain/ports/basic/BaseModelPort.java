package hu.ps.ss.domain.ports.basic;

import hu.ps.ss.domain.pojo.PageResult;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Common base of model services
 *
 * @param <M> Model type
 * @param <I> ItemWithId type
 */
public interface BaseModelPort<M, I> {

  default Class<M> getModelClass() {
    return null;
  }

  List<M> findAll();

  Optional<M> findById(final I id);

  PageResult<M> search(Map<String, List<String>> params, Integer pageIndex,
      Integer pageSize, String sortBy);

  List<M> search(Map<String, List<String>> params, String sortBy);

  M save(M item);

  void deleteById(I id);

  void delete(M item);
}
