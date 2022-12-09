package com.fadgiras.exos.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
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
                .setFixedPosition((float)(base-.5)/* -.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*(nbLignes+3.75)))), (float)(pdf.getDefaultPageSize().getWidth())));
            
            document.add(
                new Paragraph("Égalité")
                .setFont(font).setFontSize((float) base-6)
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedPosition((float)(base-.5)/* -.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*(nbLignes+4.40)))), (float)(pdf.getDefaultPageSize().getWidth())));
            
            document.add(
                new Paragraph("Fraternité")
                .setFont(font).setFontSize((float) base-6)
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedPosition((float)(base-.5)/* -.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*(nbLignes+5.05)))), (float)(pdf.getDefaultPageSize().getWidth())));
    }

    private void titre(String title, Document document, PdfFont marianneb){
        //Title
        document.add(new Paragraph(title)
        .setFont(marianneb)
        .setFontSize(15)
        .setTextAlignment(TextAlignment.CENTER)
        );
    }

    private void soustitre(String subt, Document document, PdfFont marianneb){
        //Sub title
        document.add(new Paragraph(subt)
        .setFont(marianneb)
        .setFontSize(13)
        .setPaddingTop(13)
        .setPaddingBottom((float)(13*1.5)));
    }

    private void blocMarianne(String Ligne1, Document document, PdfDocument pdf, PdfFont marianneb, PdfFont spectrali) throws FileNotFoundException, IOException{
        String SVG_FILE = "src/main/resources/Bloc_Marianne.svg";
        int base = 18;
        SvgConverter.drawOnDocument(new FileInputStream(SVG_FILE), pdf, 1, base, pdf.getDefaultPageSize().getHeight()-base*2);

        document.add(
            new Paragraph(Ligne1.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph("").setPaddingTop((float)(base*4.75))
        );

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
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne2.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*4.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph("").setPaddingTop((float)(base*4.75))
        );
        
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
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne2.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*4.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne3.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*5.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph("").setPaddingTop((float)(base*4.75))
        );
            
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
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne2.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*4.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne3.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*5.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne4.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*6.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph("").setPaddingTop((float)(base*4.75))
        );
            
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
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne2.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*4.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne3.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*5.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne4.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*6.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne5.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*7.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph("").setPaddingTop((float)(base*4.75))
        );
            
        devise(document, pdf, spectrali, base, 5);
    }

    private void blocMarianne(String Ligne1, String Ligne2, String Ligne3, String Ligne4, String Ligne5, String Ligne6, Document document, PdfDocument pdf, PdfFont marianneb, PdfFont spectrali) throws FileNotFoundException, IOException{
        String SVG_FILE = "src/main/resources/Bloc_Marianne.svg";
        int base = 18;
        SvgConverter.drawOnDocument(new FileInputStream(SVG_FILE), pdf, 1, base, pdf.getDefaultPageSize().getHeight()-base*2);

        document.add(
            new Paragraph(Ligne1.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*3.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne2.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*4.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne3.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*5.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne4.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*6.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne5.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*7.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph(Ligne6.toUpperCase())
            .setFont(marianneb).setFontSize((float) base+1)
            .setTextAlignment(TextAlignment.LEFT)
            .setFixedPosition((float)(base-1.5)/* -1.5 because of the offset*/,(float)(pdf.getDefaultPageSize().getHeight()-((base*8.75))), (float)(pdf.getDefaultPageSize().getWidth())));

        document.add(
            new Paragraph("").setPaddingTop((float)(base*4.75))
        );
            
        devise(document, pdf, spectrali, base, 6);
    }

    private void tableau(float [] cols, String[][] data, Document document, PdfFont marianne, PdfFont marianneb){
        Table table = new Table(cols);
            for (String[] strings : data) {
                if(strings[0].equals("")){
                    table.addCell(new Cell().setBorder(Border.NO_BORDER));
                }
                else{
                    table.addCell(
                        new Cell()
                            .add(new Paragraph(strings[0])
                            .setFont(marianneb)
                            )

                            .add(new Paragraph(strings[1])
                            .setFont(marianne)
                            )
                            .setBorder(Border.NO_BORDER)
                    );
                }
            }
        document.add(table);
    }
    
    private String[][] donneesTabAccord(String etat, String numDos, String dateDepot, String dateSign, String dateRecep, String siret, String raison, String effectifs){
        
        String[][] data= {
                            {"État de la demande d'agrément : ",etat},
                            {"",""},// toujours vide
                            {"Numéro de dossier : ",numDos},
                            {"Date de dépot : ",dateDepot},
                            {"Date de signature : ",dateSign},
                            {"Date de récépissé : ", dateRecep },
                            {"N° SIRET : ",siret},
                            {"Raison sociale : ",raison},
                            {"Effectifs : ",effectifs},
                            {"",""}// toujours vide
                        };
        return data;
    }

    private void donneesAccord(
                        String etat, 
                        String numDos, 
                        String dateDepot, 
                        String dateSign, 
                        String dateRecep, 
                        String siret, 
                        String raison, 
                        String effectifs, 
                        String activite, 
                        String conv,
                        Document document,
                        PdfFont marianne,
                        PdfFont marianneb,
                        float[] cols
                    )
    {

        soustitre("Données de l'accord : ", document, marianneb);

        String[][] data=donneesTabAccord(etat, numDos, dateDepot, dateSign, dateRecep, siret, raison, effectifs);

        tableau(cols, data, document, marianne, marianneb);

        document.add(new Paragraph()
                            .add(new Text("Activité principale exercée : ").setFont(marianneb))
                            .add(new Text(activite).setFont(marianne)));

        document.add(new Paragraph()
                            .add(new Text("Convention collective de branche : ").setFont(marianneb))
                            .add(new Text(conv).setFont(marianne)));
    }
    
    private void donneesAccord(
                        String etat, 
                        String numDos, 
                        String dateDepot, 
                        String dateSign, 
                        String dateRecep, 
                        String siret, 
                        String raison, 
                        int effectifs, 
                        String activite, 
                        String conv,
                        Document document,
                        PdfFont marianne,
                        PdfFont marianneb,
                        float[] cols
                    )
                    {
                        donneesAccord(etat, numDos, dateDepot, dateSign, dateRecep, siret, raison, Integer.toString(effectifs), activite, conv, document, marianne, marianneb, cols);
                    }
    
    private String[][] donneesTabSiege(String add, String CP, String commune){
        String[][] data= {
            {"Adresse : ",add},
            {"",""},// toujours vide
            {"Code Postal : ",CP},
            {"Commune : ",commune}
        };
        return data;
    }

    private void siegeSoc(String add, String CP, String commune, float[] cols, Document document, PdfFont marianne, PdfFont marianneb ){
        
        soustitre("Adresse du siège social : ", document, marianneb);

        String[][] data = donneesTabSiege(add, CP, commune);

        tableau(cols, data, document, marianne, marianneb);
    }

    private void siegeSoc(String add, int CP, String commune, float[] cols, Document document, PdfFont marianne, PdfFont marianneb ){
        String[][] data = donneesTabSiege(add, Integer.toString(CP), commune);

        tableau(cols, data, document, marianne, marianneb);
    }

    private void addPost(String add, String CP, String commune, float[] cols, Document document, PdfFont marianne, PdfFont marianneb ){
        
        soustitre("Adresse postale de l'accord : ", document, marianneb);

        String[][] data = donneesTabSiege(add, CP, commune);

        tableau(cols, data, document, marianne, marianneb);
    }

    private void addPost(String add, int CP, String commune, float[] cols, Document document, PdfFont marianne, PdfFont marianneb ){
        
        addPost(add, Integer.toString(CP), commune, cols, document, marianne, marianneb);
    }

    private void signataire(String  contact, double masseSal, double masseSalCharge, double effConv, double effMes, double salMoyEffG, double salMoyMes, Document document, PdfFont marianne, PdfFont marianneb){
        soustitre("Signataire de l'accord", document, marianneb);
        
        document.add(new Paragraph()
                            .add(new Text("Courriel de contact : ").setFont(marianneb))
                            .add(new Text(contact).setFont(marianne)));
        document.add(new Paragraph()
                            .add(new Text("Masse Salariale : ").setFont(marianneb))
                            .add(new Text(masseSal%1==0 ? Integer.toString((int) masseSal):Double.toString(masseSal)).setFont(marianne)));
        document.add(new Paragraph()
                            .add(new Text("Masse salariale charges comprises de l'effectifs concerné : ").setFont(marianneb))
                            .add(new Text(masseSalCharge%1==0 ? Integer.toString((int) masseSalCharge):Double.toString(masseSalCharge)).setFont(marianne)));
        document.add(new Paragraph()
                            .add(new Text("Effectif total de la convention : ").setFont(marianneb))
                            .add(new Text(effConv%1==0 ? Integer.toString((int) effConv):Double.toString(effConv)).setFont(marianne)));
        document.add(new Paragraph()
                            .add(new Text("Effectif concerné par la mesure : ").setFont(marianneb))
                            .add(new Text(effMes%1==0 ? Integer.toString((int) effMes):Double.toString(effMes)).setFont(marianne)));
        document.add(new Paragraph()
                            .add(new Text("Salaire moyen de l'effectif global de la convention : ").setFont(marianneb))
                            .add(new Text(salMoyEffG%1==0 ? Integer.toString((int) salMoyEffG):Double.toString(salMoyEffG)).setFont(marianne)));
        document.add(new Paragraph()
                            .add(new Text("Salaire moyen des ETP concernés par la mesure : ").setFont(marianneb))
                            .add(new Text(salMoyMes%1==0 ? Integer.toString((int) salMoyMes):Double.toString(salMoyMes)).setFont(marianne)));
                        
    }

    public void sautDePage(Document document){
        document.add(new AreaBreak());
    }
    
    class EventHandler implements IEventHandler {
    
        private Document document;
        private PdfFont font;
        
        EventHandler(Document document, PdfFont font){
            this.document = document;
            this.font = font;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            Rectangle pageSize = docEvent.getPage().getPageSize();

            float coordX = ((pageSize.getLeft() + document.getLeftMargin())
                    + (pageSize.getRight() - document.getRightMargin())) / 2;
            float footerY = document.getBottomMargin();
            Canvas canvas = new Canvas(docEvent.getPage(), pageSize);
            canvas  .setFont(font)
                    .setFontSize(12)
                    .showTextAligned(Integer.toString(docEvent.getDocument().getLastPage().getStructParentIndex()), coordX, footerY, TextAlignment.CENTER)
                    .close();
        }
    }

    private void numberedFooter(Document document, PdfFont font){
        for (int i = 1; i <= document.getPdfDocument().getNumberOfPages(); i++) {
            Rectangle pageSize = document.getPdfDocument().getPage(i).getPageSize();

            float coordX = ((pageSize.getLeft() + document.getLeftMargin())
                    + (pageSize.getRight() - document.getRightMargin())) / 2;
            float footerY = document.getBottomMargin();
            Canvas canvas = new Canvas(document.getPdfDocument().getPage(i), pageSize);
            canvas  .setFont(font)
                    .setFontSize(12)
                    .showTextAligned(Integer.toString(i), coordX, footerY, TextAlignment.CENTER)
                    .close();
        }
    }
    
    private void tableauEntete(Document document, String[][] data, int column, PdfFont marianne, PdfFont marianneb, boolean headerBold){
        Table table = new Table(column);
        for(int i = 0 ; i<=data.length-1; i++){
            for(int y = 0 ; y<=column-1; y++){
                if(i==0){
                    if(headerBold){
                        
                        table.addHeaderCell(new Cell().add(new Paragraph(data[i][y]).setFont(marianneb).setTextAlignment(TextAlignment.CENTER)));
                    }
                    else{
                        table.addHeaderCell(new Paragraph(data[i][y]).setFont(marianne).setTextAlignment(TextAlignment.CENTER));
                    }
                }else{
                    if(isNumeric(data[i][y])){
                        table.addCell(new Paragraph(data[i][y]).setFont(marianne).setTextAlignment(TextAlignment.RIGHT));
                    }
                    else{
                        table.addCell(new Paragraph(data[i][y]).setFont(marianne).setTextAlignment(TextAlignment.LEFT));
                    }
                }
            }
        }
        table.setWidth(document.getPdfDocument().getPage(1).getPageSize().getWidth()-(document.getRightMargin()*2));
        document.add(table);
    }

    private boolean isNumeric(String str){
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            try {
                float d = Float.parseFloat(str);
            } catch (NumberFormatException nfe2) {
                try {
                    int d = Integer.parseInt(str);
                } catch (NumberFormatException nfe3) {
                    try {
                        long d = Long.parseLong(str);
                    } catch (NumberFormatException nfe4) {
                        return false;
                    }
                    return true;
                }
                return true;
            }
            return true;
        }
        return true;
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
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, new PageSize(PageSize.A4));

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

            blocMarianne("Ministère","des tests",document, pdf, marianneb, spectrali);
            
            float[] cols = {250F,250F};

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            titre("Demande d'agrément TXXXXXXXXXXX-X / " + sdf.format(timestamp), document, marianneb);

            donneesAccord(  
                            "Décision notifiée",
                            "TXXXXXXXXXXX-X",
                            "01/01/1900",
                            "01/01/1900",
                            "01/01/1900",
                            "12345678901234",
                            "L'association des quarante-deux",
                            123,
                            "9499Z - Autres organisations fonctionnant par adhésion volontaire",
                            "0783 - Convention collective des centres d'hébergement et de réadaptation sociale et dans les services d'accueil, d'orientation et d'insertion pour adultes",
                            document, 
                            marianne,
                            marianneb,
                            cols
            );

            siegeSoc("2 RUE DE PONTCHATEAU ENTREE B", "44260", "SAVENAY", cols, document, marianne, marianneb);

            sautDePage(document);
            
            addPost("2 RUE DE PONTCHATEAU ENTREE B", "44260", "SAVENAY", cols, document, marianne, marianneb);

            signataire("siege@asso-leseauxvives.fr", 4456895, 4456895, 125.73, 125.73, 35448.15, 35448.15, document, marianne, marianneb);

            String[][] data= {
                {"Cat 1 ","Cat 2 ","Cat 3 ","Cat 4 ","Cat 5 ","Cat 6 ","Cat 7 ", "Cat 8 "},
                {"12","123.02","1245.23","r","t","y","u","i"},
                {"a","z","e","r","t","y","u","i"},
                {"a","z","e","r","t","y","u","i"}
            };

            int column = 8;
            tableauEntete(document, data, column, marianne, marianneb, true);

            numberedFooter(document, marianne);
            
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
