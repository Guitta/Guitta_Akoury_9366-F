/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitta.akoury.view;

/**
 *
 * @author Guitta
 */
import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.JRAbstractExporter;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class InitPageBeanCatRpt {

    public String viewReportPDF() throws SQLException, JRException, IOException {
        String reportId = "depcatrpt";
        Driver mDriver = new Driver();
        DriverManager.registerDriver(mDriver);
        String url = new String("jdbc:mysql://localhost:3306/gdf?zeroDateTimeBehavior=convertToNull");
        Connection connection = (Connection) DriverManager.getConnection(
                url,
                "root",
                "admin");
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports");
        File file = new File(reportPath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                new FileInputStream(new File(file, reportId + ".jasper")),
                null, connection);
        byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        /**
         * *********************************************************************
         * Pour afficher une boîte de dialogue pour enregistrer le fichier sous
         * le nom DepensesParCategorie.pdf
         * ********************************************************************
         */
        response.addHeader("Content-disposition",
                "attachment;filename=DepensesParCategorie.pdf");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.setContentType("application/pdf");
        context.responseComplete();
        return null;
    }

   
}
