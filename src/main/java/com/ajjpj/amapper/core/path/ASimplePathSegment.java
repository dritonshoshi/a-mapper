package com.ajjpj.amapper.core.path;

/**
 * @author arno
 */
public class ASimplePathSegment extends APathSegment {
    ASimplePathSegment(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return getName();
    }
}
