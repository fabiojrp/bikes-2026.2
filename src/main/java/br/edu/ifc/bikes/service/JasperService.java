package br.edu.ifc.bikes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class JasperService {

    private final ResourceLoader resourceLoader;
    private final DataSource dataSource;

    private Map<String, Object> params = new HashMap<>();
    private final String JASPER_DIR = "classpath:reports/";

    public void addParameter(String key, Object value) {
        params.put("IMG_DIR", JASPER_DIR);
        params.put("REPORT_LOCALE", new Locale("pt", "BR"));
        params.put(key, value);
    }

    public void addParameter() {
        params.put("IMG_DIR", JASPER_DIR);
        params.put("REPORT_LOCALE", new Locale("pt", "BR"));
    }

    public byte[] generatePdf(){
        this.addParameter();
        byte[] bytes = null;
        try {
            Resource resource = resourceLoader.getResource(JASPER_DIR.concat("clientes-bikes.jasper"));
            InputStream inputStream = resource.getInputStream();
            JasperPrint print = JasperFillManager.fillReport(inputStream, params, dataSource.getConnection());
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            log.error("Error generating PDF report", e);
        }
        return bytes;
    }
}
