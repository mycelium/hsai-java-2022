import junit.framework.TestCase;
import org.testng.annotations.Test;

import java.util.ArrayList;

    public class DistributionsTest extends TestCase {

        public DistributionsTest(String testName )
        {
            super( testName );
        }

        /*Условный тест на Нормальное распределение
         * Для параметров 1 и 1 плотнось вероятности 0.39...
         * Глядя на график плотности вероятности, получить значения вне диапазона [-3;4] очень мала (см. учебник мат. статистики)
         * Сделаем проверку на то, что при данных параметрах сгенерированное значение попадет
         * в данный диапазон */

        @Test
        public void generationNormalTest() {
            System.out.println("Normal distribution generation test");
            NormalDistribution n = new NormalDistribution(1, 1, 1);
            ArrayList<Double> list = n.getNormalDistribution();
            assertTrue(list.get(0) >= -3.0 && list.get(0) < 4.0);
        }

        /*По аналогичным соображениям. В равномерном распределении получившееся значение всегда в диапазоне
         * параметров a и b (см. учебник мат. статистики)*/

        @Test
        public void generationUniformTest(){
            System.out.println("Uniform distribution generation test");
            UniformDistribution u = new UniformDistribution(1, 3, 1);
            ArrayList<Double>list = u.getUniformDistribution();
            assertTrue(list.get(0) >= 1 && list.get(0) <= 3);
        };

        //По аналогичным соображениям
        @Test
        public void generationPoissonTest(){
            System.out.println("Poisson distribution generation test");
            PoissonDistribution p = new PoissonDistribution(1, 1);
            ArrayList<Double>list = p.getPoissonDistribution();
            assertTrue(list.get(0) >= 0.0 && list.get(0) < 10.0);
        };
    }