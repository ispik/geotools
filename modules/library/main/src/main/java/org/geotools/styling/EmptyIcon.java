/*
 * GeoTools - The Open Source Java GIS Toolkit
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

package org.geotools.styling;

import javax.swing.Icon;
import java.awt.Component;
import java.awt.Graphics;


/**
 * @author Sebastian Graca, ISPiK S.A.
 */
class EmptyIcon implements Icon {
    public static final EmptyIcon INSTANCE = new EmptyIcon();

    public void paintIcon(Component c, Graphics g, int x, int y) {
    }

    public int getIconWidth() {
        return 1;
    }

    public int getIconHeight() {
        return 1;
    }
}
