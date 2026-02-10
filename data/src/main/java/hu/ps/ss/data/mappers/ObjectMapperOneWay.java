package hu.ps.ss.data.mappers;

import java.util.List;

/**
 * Base interface clas to use MapStruct
 *
 * @param <S> Source class of mapping, usually it is a JPA entity or domain model,
 * @param <D> Target class of mapping, usually it is POJO or DTO
 */
public interface ObjectMapperOneWay<S, D> {

  /**
   * Maps S type into D
   *
   * @param source the source instance
   * @return instance of D
   */
  D map(final S source);

  /**
   * Maps list of S instances into list of D
   *
   * @param source the source instances
   * @return instances of D
   */
  List<D> mapList(final List<S> source);
}
