package hayashi.userservice.shared.strategy;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StrategyRegistry<FACTOR, RESULT> implements StrategyResolver<FACTOR, RESULT> {

    private final List<Strategy<FACTOR, RESULT>> strategies;

    @Override
    public Strategy<FACTOR, RESULT> getStrategy(FACTOR factor) {
        return strategies.stream()
                .filter(strategy -> strategy.support(factor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not found strategy"));
    }
}
