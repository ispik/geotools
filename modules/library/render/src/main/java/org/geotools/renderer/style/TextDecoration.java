/*
 * GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2010, Open Source Geospatial Foundation (OSGeo)
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

package org.geotools.renderer.style;

import java.awt.font.TextAttribute;
import java.util.Map;


/**
 * @author Sebastian Graca, ISPiK S.A.
 */
public enum TextDecoration {
    UNDERLINE(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON),
    LINE_THROUGH(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);

    private TextDecoration(TextAttribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    public void enableDecoration(Map<TextAttribute, Object> attributes) {
        attributes.put(attribute, value);
    }

    private final TextAttribute attribute;
    private final Object value;
}
