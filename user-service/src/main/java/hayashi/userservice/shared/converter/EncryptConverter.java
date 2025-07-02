package hayashi.userservice.shared.converter;

import hayashi.userservice.shared.util.ApplicationContextProvider;
import hayashi.userservice.shared.util.Encryptor;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptConverter implements AttributeConverter<String, String> {

    private final Encryptor encryptor = ApplicationContextProvider.getBean(Encryptor.class);

    @Override
    public String convertToDatabaseColumn(String value) {
        return value == null ? null : encryptor.encrypt(value);
    }

    @Override
    public String convertToEntityAttribute(String value) {
        return value == null ? null : encryptor.decrypt(value);
    }
}
