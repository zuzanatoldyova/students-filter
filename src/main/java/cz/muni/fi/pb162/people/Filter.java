package cz.muni.fi.pb162.people;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Filter contains whole structure for filtering data from storage. Any property could be null.
 *
 * @author tomasskopal.
 */
public class Filter {

    private Set<FilterType> filterTypes = new HashSet<>();
    private EFilterOrder filterOrder = null;
    private EFilterSource filterSource = EFilterSource.SOURCE_ALL; // default value is ALL

    /**
     * @param filterSource - filterSource
     * @param filterOrder - filterOrder
     * @param filterTypes - filterTypes
     */
    public Filter(EFilterSource filterSource, EFilterOrder filterOrder, FilterType... filterTypes) {
        this(filterSource, filterTypes);
        this.filterOrder = filterOrder;
    }

    /**
     * @param filterSource - filterSource
     * @param filterTypes - filterTypes
     */
    public Filter(EFilterSource filterSource, FilterType ... filterTypes) {
        this(filterTypes);
        this.filterSource = filterSource;
    }

    /**
     * @param filterTypes - filterTypes
     */
    public Filter(FilterType ... filterTypes) {
        this();
        this.filterTypes = new HashSet<>(Arrays.asList(filterTypes));
    }

    /**
     * Create new filter object
     */
    public Filter() {}


    public Set<FilterType> getFilterTypes() {
        return filterTypes;
    }

    public EFilterOrder getFilterOrder() {
        return filterOrder;
    }

    public EFilterSource getFilterSource() {
        return filterSource;
    }

    public void setFilterSource(EFilterSource filterSource) {
        this.filterSource = filterSource;
    }
}
