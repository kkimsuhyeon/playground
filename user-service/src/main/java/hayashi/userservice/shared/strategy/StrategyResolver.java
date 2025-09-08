package hayashi.userservice.shared.strategy;

public interface StrategyResolver<FACTOR, RESULT> {
    Strategy<FACTOR, RESULT> getStrategy(FACTOR factor);
}
