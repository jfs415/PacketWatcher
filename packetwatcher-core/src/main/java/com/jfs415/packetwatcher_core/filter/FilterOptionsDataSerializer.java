package com.jfs415.packetwatcher_core.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterOptionsDataSerializer extends JsonSerializer<FilterOptionsManager> {

    private static final Logger logger = LoggerFactory.getLogger(FilterOptionsDataSerializer.class);

    @Override
    public void serialize(FilterOptionsManager filterOptionsManager, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();

        filterOptionsManager.getOptions().forEach((option, filterList) -> {
            try {
                processFilterOption(option, filterList, gen);
            } catch (IOException filterDataIoe) {
                logger.error(filterDataIoe.getMessage(), filterDataIoe);
            }
        });

        gen.writeEndObject();
    }

    private void processFilterOption(FilterOption option, List<IFilter<?>> filterList, JsonGenerator gen)
            throws IOException {
        gen.writeFieldName(option.name());
        gen.writeStartArray();

        filterList.forEach(listValue -> {
            try {
                processFilterList(listValue, gen);
            } catch (IOException filterListIoe) {
                logger.error(filterListIoe.getMessage(), filterListIoe);
            }
        });

        gen.writeEndArray();
    }

    private void processFilterList(IFilter<?> listValue, JsonGenerator gen) throws IOException {
        if (listValue instanceof FilterSet) {
            FilterSet<?> filterSet = (FilterSet<?>) listValue;
            filterSet.getDataSet().forEach(setValue -> {
                try {
                    gen.writeString(setValue.toString());
                } catch (IOException filterValuesIoe) {
                    logger.error(filterValuesIoe.getMessage(), filterValuesIoe);
                }
            });
        } else {
            gen.writeObject(listValue);
        }
    }
}
