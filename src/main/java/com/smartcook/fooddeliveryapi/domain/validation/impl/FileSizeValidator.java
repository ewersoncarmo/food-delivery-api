package com.smartcook.fooddeliveryapi.domain.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import com.smartcook.fooddeliveryapi.domain.validation.FileSize;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

	private DataSize dataSize;
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
		this.dataSize = DataSize.parse(constraintAnnotation.max());
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || value.getSize() <= this.dataSize.toBytes();
	}

}
