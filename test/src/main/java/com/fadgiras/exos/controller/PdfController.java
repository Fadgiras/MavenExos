package com.fadgiras.exos.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.svg.converter.SvgConverter;
import com.itextpdf.svg.processors.ISvgConverterProperties;
import com.itextpdf.svg.processors.impl.SvgConverterProperties;
import com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;


@RestController
public class PdfController {

    private void devise(Document document, PdfDocument pdf, PdfFont font, float base, int nbLignes){
        document.add(
                new Paragraph("Liberté")
                .setFont(font).setFontSize((float) base-6)
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedPosition((float)(base-.5)/* -.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*(nbLignes+3.75)))), 200));
            
            document.add(
                new Paragraph("Égalité")
                .setFont(font).setFontSize((float) base-6)
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedPosition((float)(base-.5)/* -.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*(nbLignes+4.40)))), 200));
            
            document.add(
                new Paragraph("Fraternité")
                .setFont(font).setFontSize((float) base-6)
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedPosition((float)(base-.5)/* -.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*(nbLignes+5.05)))), 200));
    }


    private void blocMarianne(String Ligne1, Document document, PdfDocument pdf, PdfFont marianneb, PdfFont spectrali) throws FileNotFoundException, IOException{
        String SVG_FILE = "src/main/resources/Bloc_Marianne.svg";
        int base = 18;
        SvgConverter.drawOnDocument(new FileInputStream(SVG_FILE), pdf, 1, base, pdf.getDefaultPageSize().getHeight()-base*2);

        document.add(
            new Paragraph(Ligne1.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), 200));

        devise(document, pdf, spectrali, base, 1);
    }

    private void blocMarianne(String Ligne1, String Ligne2, Document document, PdfDocument pdf, PdfFont marianneb, PdfFont spectrali) throws FileNotFoundException, IOException{
        String SVG_FILE = "src/main/resources/Bloc_Marianne.svg";
        int base = 18;
        SvgConverter.drawOnDocument(new FileInputStream(SVG_FILE), pdf, 1, base, pdf.getDefaultPageSize().getHeight()-base*2);

        document.add(
            new Paragraph(Ligne1.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), 200));

        document.add(
            new Paragraph(Ligne2.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*4.75))), 200));

        devise(document, pdf, spectrali, base, 2);
    }

    private void blocMarianne(String Ligne1, String Ligne2, String Ligne3, Document document, PdfDocument pdf, PdfFont marianneb, PdfFont spectrali) throws FileNotFoundException, IOException{
        String SVG_FILE = "src/main/resources/Bloc_Marianne.svg";
        int base = 18;
        SvgConverter.drawOnDocument(new FileInputStream(SVG_FILE), pdf, 1, base, pdf.getDefaultPageSize().getHeight()-base*2);

        document.add(
            new Paragraph(Ligne1.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), 200));

        document.add(
            new Paragraph(Ligne2.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*4.75))), 200));

        document.add(
            new Paragraph(Ligne3.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*5.75))), 200));
            
        devise(document, pdf, spectrali, base, 3);
    }

    private void blocMarianne(String Ligne1, String Ligne2, String Ligne3, String Ligne4, Document document, PdfDocument pdf, PdfFont marianneb, PdfFont spectrali) throws FileNotFoundException, IOException{
        String SVG_FILE = "src/main/resources/Bloc_Marianne.svg";
        int base = 18;
        SvgConverter.drawOnDocument(new FileInputStream(SVG_FILE), pdf, 1, base, pdf.getDefaultPageSize().getHeight()-base*2);

        document.add(
            new Paragraph(Ligne1.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), 200));

        document.add(
            new Paragraph(Ligne2.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*4.75))), 200));

        document.add(
            new Paragraph(Ligne3.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*5.75))), 200));

        document.add(
            new Paragraph(Ligne4.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*6.75))), 200));

        devise(document, pdf, spectrali, base, 4);
    }

    private void blocMarianne(String Ligne1, String Ligne2, String Ligne3, String Ligne4, String Ligne5, Document document, PdfDocument pdf, PdfFont marianneb, PdfFont spectrali) throws FileNotFoundException, IOException{
        String SVG_FILE = "src/main/resources/Bloc_Marianne.svg";
        int base = 18;
        SvgConverter.drawOnDocument(new FileInputStream(SVG_FILE), pdf, 1, base, pdf.getDefaultPageSize().getHeight()-base*2);

        document.add(
            new Paragraph(Ligne1.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), 200));

        document.add(
            new Paragraph(Ligne2.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*4.75))), 200));

        document.add(
            new Paragraph(Ligne3.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*5.75))), 200));

        document.add(
            new Paragraph(Ligne4.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*6.75))), 200));

        devise(document, pdf, spectrali, base, 2);
    }

    private void blocMarianne(String Ligne1, String Ligne2, String Ligne3, String Ligne4, String Ligne5, String Ligne6, Document document, PdfDocument pdf, PdfFont marianneb, PdfFont spectrali) throws FileNotFoundException, IOException{
        String SVG_FILE = "src/main/resources/Bloc_Marianne.svg";
        int base = 18;
        SvgConverter.drawOnDocument(new FileInputStream(SVG_FILE), pdf, 1, base, pdf.getDefaultPageSize().getHeight()-base*2);

        document.add(
            new Paragraph(Ligne1.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), 200));

        document.add(
            new Paragraph(Ligne2.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*4.75))), 200));

        document.add(
            new Paragraph(Ligne3.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*5.75))), 200));

        document.add(
            new Paragraph(Ligne4.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*6.75))), 200));

        document.add(
            new Paragraph(Ligne4.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*7.75))), 200));

        document.add(
            new Paragraph(Ligne4.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*8.75))), 200));

        devise(document, pdf, spectrali, base, 2);
    }

    
    @RequestMapping(value = "/testPdf")
    public void pdftest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        final String MARIANNE_REGULAR = "src/main/java/com/fadgiras/exos/fonts/marianne/Marianne-Regular.otf";
        final String MARIANNE_BOLD = "src/main/java/com/fadgiras/exos/fonts/marianne/Marianne-Bold.otf";
        final String SPECTRAL_ITALIC = "src/main/java/com/fadgiras/exos/fonts/spectral/Spectral-italic.ttf";

        FontProgram fontProgram = FontProgramFactory.createFont(MARIANNE_BOLD);
        PdfFont marianneb = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI);

        fontProgram = FontProgramFactory.createFont(MARIANNE_REGULAR);
        PdfFont marianne = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI);

        fontProgram = FontProgramFactory.createFont(SPECTRAL_ITALIC);
        PdfFont spectrali = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI);

        try {
            // step 1 : Initialize all the required vars
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
            Document document = new Document(pdf);

            // step 2 : have fun building the document

            //Empty paragraph as an anchor
            document.add(new Paragraph(""));


            // String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
            // Paragraph paragraph = new Paragraph(content);


            // paragraph.setFont(marianne);

            // document.add(paragraph);

            // Text title1 = new Text("The Strange Case of ").setFontSize(12);
            // Text title2 = new Text("Dr. Jekyll and Mr. Hyde").setFontSize(16);
            // Text author = new Text("Robert Louis Stevenson");
            // Paragraph p = new Paragraph().setFontSize(8)
            //         .add(title1).add(title2).add(" by ").add(author);
            // document.add(p);

            

            // List list = new List();

            // list.setBackgroundColor(new DeviceCmyk(0, 0, 0, 36));
            
            // Color color = new DeviceRgb(65, 93, 115);
            // list.setFontColor(color);

            // list.setMargin(25);

            // list.add("Java");
            // list.add("Go");
            // list.add("React");
            // list.add("Apache Kafka");
            // list.add("Jenkins");
            // list.add("Elastic Search");
            // document.add(list);

            blocMarianne("République","Française",document, pdf, marianneb, spectrali);

            

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
