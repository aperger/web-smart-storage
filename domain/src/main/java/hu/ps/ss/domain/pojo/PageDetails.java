package hu.ps.ss.domain.pojo;

public record PageDetails(
		long number,
		long size,
		long totalElements,
		long totalPages,
		String sortBy) {

}
