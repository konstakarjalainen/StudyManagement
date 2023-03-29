package fi.tuni.prog3.sisu.util;

import fi.tuni.prog3.sisu.entity.sisu.DegreeProgramme;
import javafx.util.StringConverter;

import java.util.List;

/**
 * Converter for ChoiceBox labels
 */
public class SelectConverter {

    /**
     * Degree programme to ChoiceBox label and vice versa.
     * @param degrees degree programmes
     * @return StringConverter for ChoiceBox
     */
    public static StringConverter<DegreeProgramme> getDegreeConverter(List<DegreeProgramme> degrees) {
        return new StringConverter<DegreeProgramme>() {
            @Override
            public String toString(DegreeProgramme degreeProgramme) {
                return degreeProgramme.getName();
            }

            @Override
            public DegreeProgramme fromString(String s) {
                return degrees.stream().filter((degree) -> degree.getName().equalsIgnoreCase(s)).findFirst().orElse(null);
            }
        };
    }
}
