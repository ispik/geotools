/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2004-2010, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.feature.simple;

import org.junit.Test;
import org.opengis.feature.simple.SimpleFeatureType;

import static org.junit.Assert.*;


/**
 * @author Sebastian Graca, ISPiK S.A.
 */
public class SimpleFeatureTypeBuilderTest {
    @Test
    public void setsDefaultValue() {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("working");
        builder.nillable(false).defaultValue(12).add("attrWithDefault", Integer.class);
        builder.nillable(true).defaultValue(null).add("attrWithoutDefault", Integer.class);
        SimpleFeatureType featureType = builder.buildFeatureType();
        assertEquals(12, featureType.getDescriptor("attrWithDefault").getDefaultValue());
        assertNull(featureType.getDescriptor("attrWithoutDefault").getDefaultValue());
    }
}
