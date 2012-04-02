<?xml version="1.0" encoding="UTF-8"?>
<StyledLayerDescriptor xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'
                       xmlns="http://www.opengis.net/sld"
                       xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"
                       version="1.0.0"
        >
  <UserLayer>
    <LayerFeatureConstraints>
      <FeatureTypeConstraint/>
    </LayerFeatureConstraints>
    <UserStyle>
      <FeatureTypeStyle>
        <Rule>
          <Name>no text decoration</Name>
          <TextSymbolizer>
            <Font>
              <CssParameter name="font-family">MyFamily</CssParameter>
              <CssParameter name="font-size">13</CssParameter>
            </Font>
          </TextSymbolizer>
        </Rule>
        <Rule>
          <Name>text decoration</Name>
          <TextSymbolizer>
            <Font>
              <CssParameter name="font-family">MyFamily</CssParameter>
              <CssParameter name="font-size">13</CssParameter>
              <CssParameter name="text-decoration">line-through</CssParameter>
            </Font>
          </TextSymbolizer>
        </Rule>
      </FeatureTypeStyle>
    </UserStyle>
  </UserLayer>
</StyledLayerDescriptor>

