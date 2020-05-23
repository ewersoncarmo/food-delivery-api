package com.smartcook.fooddeliveryapi.service.report;

import com.smartcook.fooddeliveryapi.domain.model.filter.DailySaleFilter;

public interface DailySaleReportService {

	byte[] generatePdfDailySales(DailySaleFilter filter);
}
