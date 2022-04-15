import java.util.Random;
import java.util.function.BiFunction;

public enum Distribution {
    UNIFORM(2, "Min", "Max",
            (pars, rnd) -> String.valueOf(pars[0] + rnd.nextDouble(pars[1] - pars[0] + 1))),
    NORMAL(2, "Mean", "St.dev",
            (pars, rnd) -> String.valueOf(rnd.nextGaussian() * pars[1] + pars[0])),
    POISSON(1, "Mean", null,
            (pars, rnd) -> {
        double lim = Math.exp(-pars[0]);
        double prod = rnd.nextDouble();
        int val;
        for (val = 0; prod >= lim; val++) { prod *= rnd.nextDouble(); }
        return String.valueOf(val);});

    private final int parCount;
    private final String firstParName;
    private final String secondParName;
    private final BiFunction<Float[], Random, String> evaluation;

    Distribution(int parCount, String firstParName, String secondParName, BiFunction<Float[], Random, String> evaluation) {
        this.parCount = parCount;
        this.firstParName = firstParName;
        this.secondParName = secondParName;
        this.evaluation = evaluation;
    }

    public int getParameterNumber() {
        return this.parCount;
    }

    public String[] getParameterNames() {
        return new String[] {firstParName, secondParName};
    }

    public BiFunction<Float[], Random, String> getEvaluation() {
        return evaluation;
    }
}
