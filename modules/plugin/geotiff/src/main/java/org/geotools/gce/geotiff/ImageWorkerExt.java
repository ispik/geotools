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


import org.geotools.image.ImageWorker;
import org.geotools.resources.i18n.ErrorKeys;
import org.geotools.resources.i18n.Errors;
import javax.media.jai.ImageLayout;
import javax.media.jai.JAI;
import javax.media.jai.LookupTableJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.BandCombineDescriptor;
import javax.media.jai.operator.BandMergeDescriptor;
import javax.media.jai.operator.BandSelectDescriptor;
import javax.media.jai.operator.FormatDescriptor;
import javax.media.jai.operator.LookupDescriptor;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


/**
 * Uzupełnienie {@link org.geotools.image.ImageWorker} o kilka przydatnych w module metod.
 *
 * @author Sebastian Graca, ISPiK S.A.
 */
public class ImageWorkerExt extends ImageWorker {
    public ImageWorkerExt() {
    }

    public ImageWorkerExt(File input) throws IOException {
        super(input);
    }

    public ImageWorkerExt(RenderedImage image) {
        super(image);
    }

    public final ImageWorker makeColorsTransparent(Set<Color> transparentColors)
            throws IllegalStateException {
        if (transparentColors == null || transparentColors.isEmpty()) {
            return this;
        }
        if (transparentColors.size() == 1) {
            Color color = transparentColors.iterator().next();
            return makeColorTransparent(color);
        }

        ColorModel cm = image.getColorModel();
        if (cm instanceof IndexColorModel) {
            return maskIndexColorModel(transparentColors);
        } else if (cm instanceof ComponentColorModel) {
            switch (image.getSampleModel().getDataType()) {
                case DataBuffer.TYPE_BYTE: {
                    return maskComponentColorModelByte(transparentColors);
                }
            }
        }
        throw new IllegalStateException(Errors.format(ErrorKeys.UNSUPPORTED_DATA_TYPE));
    }

    private ImageWorker maskIndexColorModel(Set<Color> transparentColors) {
        IndexColorModel cm = (IndexColorModel)image.getColorModel();
        int numComponents = cm.getNumComponents();
        int mapSize = cm.getMapSize();

        List<Integer> transparentPixelsIndexes = new ArrayList<Integer>();
        for (int i = 0; i < mapSize; i++) {
            int color = cm.getRGB(i) & 0xffffff;
            if (transparentColors.contains(new Color(color))) {
                transparentPixelsIndexes.add(i);
            }
        }
        if (transparentPixelsIndexes.isEmpty()) {
            return this;
        }

        byte[][] rgb = new byte[4][mapSize];
        cm.getReds(rgb[0]);
        cm.getGreens(rgb[1]);
        cm.getBlues(rgb[2]);
        if (numComponents == 4) {
            cm.getAlphas(rgb[3]);
        } else {
            Arrays.fill(rgb[3], (byte)255);
        }

        if (transparentPixelsIndexes.size() == 1) {
            int transparencyIndex = transparentPixelsIndexes.get(0);
            cm = new IndexColorModel(cm.getPixelSize(), mapSize, rgb[0], rgb[1], rgb[2], transparencyIndex);
        } else {
            for (Integer idx : transparentPixelsIndexes) {
                rgb[3][idx] = (byte)0;
            }
            cm = new IndexColorModel(cm.getPixelSize(), mapSize, rgb[0], rgb[1], rgb[2], rgb[3]);
        }

        ImageLayout layout = new ImageLayout(image);
        layout.setColorModel(cm);
        RenderingHints hints = getRenderingHints();
        hints.add(new RenderingHints(JAI.KEY_IMAGE_LAYOUT, layout));
        hints.add(new RenderingHints(JAI.KEY_REPLACE_INDEX_COLOR_MODEL, Boolean.FALSE));

        // setImage zamiast przypisania do image aby została wywołana metoda
        // invalidateStatistcs, do której nie mamy dostępu
        return setImage(FormatDescriptor.create(image, image.getSampleModel().getDataType(), hints));
    }

    private ImageWorker maskComponentColorModelByte(Set<Color> transparentColors) {
        int numColorBands = image.getColorModel().getNumColorComponents();
        RenderingHints hints = getRenderingHints();
        if (numColorBands != image.getSampleModel().getNumBands()) {
            int[] opaqueBands = new int[numColorBands];
            for (int i = 0; i < opaqueBands.length; i++) {
                opaqueBands[i] = i;
            }
            image = BandSelectDescriptor.create(image, opaqueBands, hints);
        }

        // now prepare the lookups
        byte[][] tableData = new byte[numColorBands][256];
        switch (numColorBands) {
            case 3:
                Arrays.fill(tableData[2], (byte)255);
                for (Color color : transparentColors) {
                    tableData[2][color.getBlue()] = 0;
                }
                // fall through

            case 2:
                Arrays.fill(tableData[1], (byte)255);
                for (Color color : transparentColors) {
                    tableData[1][color.getGreen()] = 0;
                }
                // fall through

            case 1:
                Arrays.fill(tableData[0], (byte)255);
                for (Color color : transparentColors) {
                    tableData[0][color.getRed()] = 0;
                }
                // fall through

            case 0:
                break;
        }
        LookupTableJAI table = new LookupTableJAI(tableData);
        PlanarImage luImage = LookupDescriptor.create(image, table, hints);

        if (numColorBands > 1) {
            double[][] matrix = new double[1][4];
            Arrays.fill(matrix[0], 0, 3, 1.0);
            luImage = BandCombineDescriptor.create(luImage, matrix, hints);
        }
        return setImage(BandMergeDescriptor.create(image, luImage, hints));
    }
}
