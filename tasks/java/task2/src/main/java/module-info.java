module org.polytech.snd_task {
    requires org.polytech.Generators;
    requires org.jooq;
    requires info.picocli;
    requires com.google.common;
    exports org.polytech;
    exports org.polytech.Cmd.Distributions;
    exports org.polytech.Utils;
}