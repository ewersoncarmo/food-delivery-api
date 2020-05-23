package com.smartcook.fooddeliveryapi.domain.assembler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaginationAssembler<T, M> {

	Page<M> toPageableModel(Pageable pageable, Page<T> page);
}
