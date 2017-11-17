package org.drulabs.picblast.data.models;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by kaushald.
 * TODO: What is this class / interface / enum is all about
 */

public class FieldExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getAnnotation(Exclude.class) != null);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
