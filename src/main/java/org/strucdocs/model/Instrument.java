package org.strucdocs.model;

import org.apache.commons.lang3.StringUtils;

public enum Instrument {

    GENERAL,
    VOCALIST,
    GUITARIST,
    PIANIST,
    BASSIST,
    DRUMMER,
    PERCUSSIONIST,
    BRASS,
    WOODWIND,
    TECHNICIAN,
    OTHER;

    @Override
    public String toString() {
        return StringUtils.capitalize(StringUtils.lowerCase(name()));
    }
}
