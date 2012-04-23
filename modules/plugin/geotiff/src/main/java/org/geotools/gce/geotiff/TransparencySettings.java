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

package org.geotools.gce.geotiff;


import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Sebastian Graca, ISPiK S.A.
 */
public class TransparencySettings {
    public Set<Color> getTransparentColors() {
        return Collections.unmodifiableSet(transparentColors);
    }

    public void setTransparentColors(Collection<Color> colors) {
        transparentColors.clear();
        addTransparentColors(colors);
    }

    public void addTransparentColor(Color color) {
        if (color != null) {
            transparentColors.add(color);
        }
    }

    public void addTransparentColors(Collection<Color> colors) {
        if (colors != null) {
            transparentColors.addAll(colors);
        }
    }

    private final Set<Color> transparentColors = new HashSet<Color>();
}
