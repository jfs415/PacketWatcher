package com.jfs415.packetwatcher_api.model.user;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AuthorityConverter implements AttributeConverter<Authority, String> {

    @Override
    public String convertToDatabaseColumn(Authority authority) {
        if (authority == null) {
            return null;
        }

        return authority.getAuthority();
    }

    @Override
    public Authority convertToEntityAttribute(String authority) {
        if (authority == null) {
            return null;
        }

        return Authority.of(authority);
    }

}
