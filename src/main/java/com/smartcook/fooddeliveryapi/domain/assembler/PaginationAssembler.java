package com.smartcook.fooddeliveryapi.domain.assembler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*
 * This interface is not being used anymore. 
 * It just for history, in order to show another way to return pageable elements.
 */
public interface PaginationAssembler<T, M> {

	Page<M> toPageableModel(Pageable pageable, Page<T> page);
}
