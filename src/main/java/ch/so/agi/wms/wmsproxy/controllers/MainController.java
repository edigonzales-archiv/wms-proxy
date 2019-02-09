package ch.so.agi.wms.wmsproxy.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.geotools.ows.wms.WMSCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import ch.so.agi.wms.wmsproxy.services.GetCapabilitiesServiceImpl;

@Controller
public class MainController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GetCapabilitiesServiceImpl getCapabilitiesService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET, produces={MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> handler(HttpServletRequest request, ModelMap model) {
        
        String serviceType = this.getRequestParameter(request, "service");
        String requestType = this.getRequestParameter(request, "request");
        log.info(requestType);

        if (requestType.equalsIgnoreCase("GetCapabilities")) {
            log.info(requestType);
            WMSCapabilities wmsCapabilities = getCapabilitiesService.getCapabilities();
            return ResponseEntity.ok(wmsCapabilities);

        }
        
        
        
        return ResponseEntity.ok("<foo>bar</foo>");
    }
   
    public String getRequestParameter(HttpServletRequest request, String parameter) {
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(parameter)) {
                return entry.getValue()[0];
            }
        }
        return null;
    }
}
