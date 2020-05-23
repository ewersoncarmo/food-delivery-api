package com.smartcook.fooddeliveryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.model.dto.DailySale;
import com.smartcook.fooddeliveryapi.domain.model.filter.DailySaleFilter;
import com.smartcook.fooddeliveryapi.persistence.query.DailySaleQuery;
import com.smartcook.fooddeliveryapi.service.report.DailySaleReportService;

@Service
public class DailySaleService {

	@Autowired
	private DailySaleQuery dailySaleQuery;
	
	@Autowired
	private DailySaleReportService dailySaleReportService;
	
	public List<DailySale> search(DailySaleFilter filter) {
		return dailySaleQuery.searchDailySales(filter);
	}

	public byte[] generatePdfDailySales(DailySaleFilter filter) {
		return dailySaleReportService.generatePdfDailySales(filter);
	}

}
