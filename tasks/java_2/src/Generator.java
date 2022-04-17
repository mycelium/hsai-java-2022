import Distributions.Distribution;
import Distributions.NormalDistribution;
import Distributions.PoissonDistribution;
import Distributions.UniformDistribution;

public class Generator {
    public StringBuilder generateData() {
        StringBuilder data = new StringBuilder();

        Distribution dist = getDistribution();

        for (int i = 0; i < Main.cnt; i++) {
            String currentData = dist.getRnd().toString();
            data.append(currentData).append("\n");
        }
        return data;
    }

    private Distribution getDistribution() {
        Distribution tmp;
        switch (Main.type) {
            case 'u' -> {
                return new UniformDistribution(Main.params[0], Main.params[1]);
            }
            case 'p' -> {
                return new PoissonDistribution(Main.params[0]);
            }
            case 'n' -> {
                return new NormalDistribution(Main.params[0], Main.params[1]);
            }
        }
        return null;
    }
}
