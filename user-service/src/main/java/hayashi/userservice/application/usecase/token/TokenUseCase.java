package hayashi.userservice.application.usecase.token;

public interface TokenUseCase {

    String getToken(String key);

    void deleteToken(String key);
}
