package com.smartcook.fooddeliveryapi.configuration;

import java.io.IOException;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/*
 * This class is not being used anymore. 
 * It just for history, in order to show a way to serialize pageable elements.
 */
//@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<Object>> {

	@Override
	public void serialize(Page<Object> page, JsonGenerator generator, SerializerProvider serializers) throws IOException {
		generator.writeStartObject();
		generator.writeObjectField("content", page.getContent());
		generator.writeNumberField("size", page.getSize());
		generator.writeNumberField("totalElements", page.getTotalElements());
		generator.writeNumberField("totalPages", page.getTotalPages());
		generator.writeNumberField("page", page.getNumber());
		generator.writeEndObject();
	}

}
