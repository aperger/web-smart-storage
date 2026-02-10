package hu.ps.ss.domain.pojo;

import java.util.List;

public record PageResult<T>(List<T> items, PageDetails pageDetails) {
}
