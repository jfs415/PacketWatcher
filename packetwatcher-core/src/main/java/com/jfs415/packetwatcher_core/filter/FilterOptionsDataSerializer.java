package com.jfs415.packetwatcher_core.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class FilterOptionsDataSerializer extends JsonSerializer<FilterOptionsManager> {

    @Override
    public void serialize(FilterOptionsManager filterOptionsManager, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        filterOptionsManager.getOptions().forEach((option, filterList) -> {
            try {
                processFilterOption(option, filterList, gen);
            } catch (IOException filterDataIoe) {
                filterDataIoe.printStackTrace();
            }
        });

        gen.writeEndObject();
    }

    private void processFilterOption(FilterOption option, List<IFilter<?>> filterList, JsonGenerator gen) throws IOException {
        gen.writeFieldName(option.name());
        gen.writeStartArray();

        filterList.forEach(listValue -> {
            try {
                processFilterList(listValue, gen);
            } catch (IOException filterListIoe) {
                filterListIoe.printStackTrace();
            }
        });

        gen.writeEndArray();
    }

    private void processFilterList(IFilter<?> listValue, JsonGenerator gen) throws IOException {
        if (listValue instanceof FilterSet) {
            FilterSet<?> filterSet = (FilterSet<?>) listValue;
            filterSet.getFilteredValues().forEach(setValue -> {
                try {
                    gen.writeString(setValue.toString());
                } catch (IOException filterValuesIoe) {
                    filterValuesIoe.printStackTrace();
                }
            });
        } else {
            gen.writeObject(listValue);
        }
    }

}
