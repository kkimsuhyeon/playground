package hayashi.userservice.shared.converter;

import hayashi.userservice.shared.util.Encryptor;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String value) {
        return value == null ? null : Encryptor.encrypt(value);
    }

    @Override
    public String convertToEntityAttribute(String value) {
        return value == null ? null : Encryptor.decrypt(value);
    }
}
