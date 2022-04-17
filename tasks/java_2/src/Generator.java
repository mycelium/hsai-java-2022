import Distributions.Distribution;
import Distributions.NormalDistribution;
import Distributions.PoissonDistribution;
import Distributions.UniformDistribution;

public class Generator {
    public StringBuilder generateData() throws InterruptedException {
        StringBuilder data = new StringBuilder();

        Distribution dist = getDistribution();

        ProgressBar bar = new ProgressBar();

        for (int i = 0; i < Main.cnt; i++) {
            String currentData = dist.getRnd().toString();
            data.append(currentData).append("\n");
            bar.animate((i + 1) + "");
            // Можно убрать, добавил для красоты вывода анимации прогресса)
            Thread.sleep(1);
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
