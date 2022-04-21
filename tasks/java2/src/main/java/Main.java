public class Main {

    /**
     * Params:
     * type=poisson (for Poisson)
     *      uniform (for Uniform)
     *      normal (for Normal)
     * count=11000
     * path=C:/ (absolute path without filename)
     * parameter=10,20 (separator: ,)
     */
    public static void main(String[] args) {
        Handler handler = new Handler();
        handler.start(args);
    }
}
