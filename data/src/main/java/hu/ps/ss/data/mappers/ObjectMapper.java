package hu.ps.ss.data.mappers;

import org.mapstruct.MappingTarget;

/**
 * Base interface clas to use MapStruct
 *
 * @param <S> Source class of mapping, usually it is a JPA entity or domain model,
 * @param <D> Target class of mapping, usually it is POJO or DTO
 */
public interface ObjectMapper<S, D> extends ObjectMapperBase<S, D> {

  /**
   * Update an instance
   *
   * @param target the target instance
   * @param source the source instance
   */
  void updateSource(@MappingTarget S target, final S source);

  /**
   * Update an instance
   *
   * @param target the target instance
   * @param source the source instance
   */
  void updateDest(@MappingTarget D target, final D source);
}
