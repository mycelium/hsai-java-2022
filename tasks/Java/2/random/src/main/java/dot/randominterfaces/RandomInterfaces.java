package dot.randominterfaces;
import java.nio.file.Path;

public class RandomInterfaces{
    public interface RandomlyGeneratable{
        Double generate();
    }
    public interface ArgumentsVisible{
        String showArguments();
    }
    public interface RandomlyGeneratableArgumentsVisible extends RandomlyGeneratable, ArgumentsVisible{}
    public interface RandomSelector<T>{
        T select();
    }
    public interface RandomStorable<T>{
        void store(T data, Path path);
    }
    public interface RandomStorablenArgumentsVisible<T> extends RandomStorable<T>, ArgumentsVisible{}
}
