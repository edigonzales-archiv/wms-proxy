package ch.so.agi.wms.wmsproxy.services;

import java.net.MalformedURLException;
import java.net.URL;

import org.geotools.ows.wms.Layer;
import org.geotools.ows.wms.WMSCapabilities;
import org.geotools.ows.wms.WMSUtils;
import org.geotools.ows.wms.WebMapServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GetCapabilitiesServiceImpl implements GetCapabilitiesService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${wms.url}")
    private String wmsUrl;

    @Override
    public WMSCapabilities getCapabilities() {
        URL url = null;
        try {
          url = new URL(wmsUrl + "?VERSION=1.3.0&REQUEST=GetCapabilities&SERVICE=WMS");
          log.info(url.toString());
          WebMapServer wms = new WebMapServer(url);
          WMSCapabilities capabilities = wms.getCapabilities();
          
          //log.info(wms.getCapabilities().getLayerList().get(0).getTitle());

          Layer[] layers = WMSUtils.getNamedLayers(capabilities);
          
          
          
          for (Layer layer : layers) {
              log.info("*********************************");
              log.info(layer.getName());
              log.info(layer.getTitle());
              log.info(String.valueOf(layer.getChildren().length));
              
          }
          
          log.info(String.valueOf(layers.length));
          
          WMSCapabilities wmsCapabilities = new WMSCapabilities();
          
          
          Layer rootLayer = new Layer();
          rootLayer.setName("SOGIS");
          rootLayer.setTitle("WMS Amt für Geoinformation Kanton Solothurn");

          wmsCapabilities.setLayer(rootLayer);
          //log.info(wmsCapabilities);
          
          return wmsCapabilities;
          
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return null;


    }

}
