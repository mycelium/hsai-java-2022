package ru.spbstu.telematics.parameters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbstu.telematics.variables.Variable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ResultParameters {

    private static Logger logger = LogManager.getLogger(Variable.class);
    private Map<String, Optional<Double>> properties = new TreeMap();
    private String name;

    public static List<ResultParameters> getResultParameters(List<Variable<Double>> variables) {
        return variables.stream()
                .map(ResultParameters::new)
                .collect(Collectors.toList());
    }

    ResultParameters(Variable<Double> variable) {
        name = variable.getName();
        properties.put("max", ParametersCalculator.max(variable));
        properties.put("min", ParametersCalculator.min(variable));
        properties.put("mean", ParametersCalculator.mean(variable));
        properties.put("median", ParametersCalculator.median(variable));
        logger.debug("Create ResultParameters: " + this.toJSON());
    }

    public String toJSON() {
        StringBuilder json = new StringBuilder();
        json.append("{\"name\":\"").append(name).append("\",");
        for (var entry : properties.entrySet()) {
            json.append("\"");
            json.append(entry.getKey());
            json.append("\":");
            json.append(entry.getValue().get());
            json.append(",");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }
}

