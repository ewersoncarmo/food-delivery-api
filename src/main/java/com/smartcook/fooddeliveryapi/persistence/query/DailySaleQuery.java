package com.smartcook.fooddeliveryapi.persistence.query;

import java.util.List;

import com.smartcook.fooddeliveryapi.domain.model.dto.DailySale;
import com.smartcook.fooddeliveryapi.domain.model.filter.DailySaleFilter;

public interface DailySaleQuery {

	List<DailySale> searchDailySales(DailySaleFilter filter);
}
