package cz.muni.fi.pb162.people;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tomasskopal.
 */
public class FilterType {

    private EFilterType type;
    private Set<String> values;

    /**
     * @param type - type
     * @param values - wrap values to the set
     */
    public FilterType(EFilterType type, String ... values) {
        this.type = type;
        this.values = new HashSet<>(Arrays.asList(values));
    }

    public EFilterType getType() {
        return type;
    }

    public Set<String> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilterType that = (FilterType) o;

        if (type != that.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
