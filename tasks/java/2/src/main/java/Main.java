import java.util.HashMap;
import java.util.Map;

public class Main {

    final public static HashMap<String, Object> params = new HashMap<>(
            Map.of("type", Distribution.NORMAL, "cnt", 10000L, "out", "",
                    "params", new Float[0]));

    /*User input implemented as CLI arguments, invoked by their names
    * Example: type=uni|norm|pois params=[0.5,23] [cnt=13000] [out=/data/]
    * Defaults: type=norm, cnt=10000, out=current dir
    * Arguments could be invoked in any order */
    public static void main(String[] args) {
        CLI.readArgs(args);
        CLI.showParams();
        CLI.showResponse(DataGenerator.getPopulatedFile());
    }
}
