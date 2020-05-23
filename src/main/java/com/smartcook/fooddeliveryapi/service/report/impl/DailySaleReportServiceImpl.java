package com.smartcook.fooddeliveryapi.service.report.impl;

import java.util.HashMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcook.fooddeliveryapi.domain.model.filter.DailySaleFilter;
import com.smartcook.fooddeliveryapi.persistence.query.DailySaleQuery;
import com.smartcook.fooddeliveryapi.service.exception.ReportException;
import com.smartcook.fooddeliveryapi.service.report.DailySaleReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class DailySaleReportServiceImpl implements DailySaleReportService {

	private static final Logger LOG = LoggerFactory.getLogger(DailySaleReportServiceImpl.class);
	
	@Autowired
	private DailySaleQuery dailySaleQuery;
	
	@Override
	public byte[] generatePdfDailySales(DailySaleFilter filter) {
		try {
			var inputStream = this.getClass().getResourceAsStream("/reports/daily-sales.jasper");
			
			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
			
			var dataSource = new JRBeanCollectionDataSource(dailySaleQuery.searchDailySales(filter));
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			LOG.error("There was an unexpected error while generating pdf report.", e);
			throw new ReportException("There was an unexpected error while generating pdf report."); 
		}
	}

}
