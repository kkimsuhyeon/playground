package hayashi.userservice.shared.strategy;

public interface Strategy<FACTOR, RESULT> {
    boolean support(FACTOR factor);

    RESULT execute(FACTOR factor);
}
