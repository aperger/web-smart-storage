package hu.ps.ss.data.mappers;

import java.util.List;

/**
 * Base interface clas to use MapStruct
 *
 * @param <S> Source class of mapping, usually it is a JPA entity or domain model,
 * @param <D> Target class of mapping, usually it is POJO or DTO
 */
public interface ObjectMapperBase<S, D> extends ObjectMapperOneWay<S, D> {

  /**
   * Mapping to opposite direction. Usually is means read back an entity based on input (DTO/POJO)
   *
   * @param source the source instances
   * @return instance of S
   */
  S parseFrom(final D source);

}
