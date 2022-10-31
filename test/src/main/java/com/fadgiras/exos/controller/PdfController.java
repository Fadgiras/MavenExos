package com.fadgiras.exos.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;


@RestController
public class PdfController {
    
    @RequestMapping(value = "/testPdf")
    public void pdftest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        final String MARIANNE_REGULAR = "src/main/java/com/fadgiras/exos/fonts/marianne/Marianne-Regular.otf";
        final String MARIANNE_BOLD = "src/main/java/com/fadgiras/exos/fonts/marianne/Marianne-Bold.otf";
        try {
            // Get the text that will be added to the PDF
            String text = request.getParameter("text");
            if (text == null || text.trim().length() == 0) {
                 text = "You didn't enter any text.";
            }

            // step 1 : Initialize all the required vars
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
            Document document = new Document(pdf);

            // step 2 : have fun building the document


            document.add(new Paragraph(String.format(
                "You have submitted the following text using the %s method:",
                request.getMethod())).setPaddingTop(80));
            document.add(new Paragraph(text));

            String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
            Paragraph paragraph = new Paragraph(content);
            
            FontProgram fontProgram = FontProgramFactory.createFont(MARIANNE_BOLD);
            PdfFont marianne = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
            paragraph.setFont(marianne);

            document.add(paragraph);

            document.add(new Paragraph("République".toUpperCase()).setFont(marianne).setFontSize(15).setTextAlignment(TextAlignment.LEFT).setFixedPosition(10, pdf.getDefaultPageSize().getHeight()-40, 200));
            document.add(new Paragraph("française".toUpperCase()).setFont(marianne).setFontSize(15).setTextAlignment(TextAlignment.LEFT).setFixedPosition(10, pdf.getDefaultPageSize().getHeight()- 55, 200));

            Text title1 = new Text("The Strange Case of ").setFontSize(12);
            Text title2 = new Text("Dr. Jekyll and Mr. Hyde").setFontSize(16);
            Text author = new Text("Robert Louis Stevenson");
            Paragraph p = new Paragraph().setFontSize(8)
                    .add(title1).add(title2).add(" by ").add(author);
            document.add(p);

            

            List list = new List();

            list.setBackgroundColor(new DeviceCmyk(0, 0, 0, 36));
            
            Color color = new DeviceRgb(65, 93, 115);
            list.setFontColor(color);

            list.setMargin(25);

            list.add("Java");
            list.add("Go");
            list.add("React");
            list.add("Apache Kafka");
            list.add("Jenkins");
            list.add("Elastic Search");
            document.add(list);

            // step 3
            document.close();
    
            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            ServletOutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
            System.out.println("All good");
        }
        catch(Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}
