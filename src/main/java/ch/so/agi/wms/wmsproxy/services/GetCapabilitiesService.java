package ch.so.agi.wms.wmsproxy.services;

import org.geotools.ows.wms.WMSCapabilities;

public interface GetCapabilitiesService {
    WMSCapabilities getCapabilities();
}
