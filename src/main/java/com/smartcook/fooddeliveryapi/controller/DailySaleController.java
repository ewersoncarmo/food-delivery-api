package com.smartcook.fooddeliveryapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcook.fooddeliveryapi.controller.security.CheckSecurity;
import com.smartcook.fooddeliveryapi.domain.model.dto.DailySale;
import com.smartcook.fooddeliveryapi.domain.model.filter.DailySaleFilter;
import com.smartcook.fooddeliveryapi.domain.model.response.ModelResponse;
import com.smartcook.fooddeliveryapi.service.DailySaleService;

@RestController
@RequestMapping("/api/v1/statistics")
public class DailySaleController {

	@Autowired
	private DailySaleService dailySaleService;
	
	@CheckSecurity.Statistics.CanQuery
	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ModelResponse<List<DailySale>>> search(DailySaleFilter filter) {
		List<DailySale> dailySales = dailySaleService.search(filter);
		
		return ResponseEntity.ok()
				.body(ModelResponse.withData(dailySales));
	}
	
	@CheckSecurity.Statistics.CanQuery
	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> searchPdf(DailySaleFilter filter) {
		byte[] report = dailySaleService.generatePdfDailySales(filter);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(report);
	}
	
}
