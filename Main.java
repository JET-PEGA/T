package sfis.raspberrypi.piclient;

import com.stimulsoft.base.serializing.StiDeserializationException;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.report.components.StiPage;
import com.stimulsoft.report.enums.StiExportFormat;
import com.stimulsoft.report.export.service.*;
import com.stimulsoft.report.export.settings.*;
import com.stimulsoft.report.export.tools.StiColorImageFormat;
import com.stimulsoft.report.export.tools.StiImageType;
import com.stimulsoft.report.print.StiPrintHelper;
import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import javafx.application.Application;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Printer;
import javafx.stage.Stage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.awt.print.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 2021-05-16 Jet JPG 列印測試
 */
public class Main extends Application {

    /**
     * 程式入口
     *
     * @param args 帶入參數
     */
    public static void main(String[] args) {

        // 設定 licenses key
        com.stimulsoft.base.licenses.StiLicense.setKey("6vJhGtLLLz2GNviWmUTrhSqnOItdDwjBylQzQcAOiHkJXag9EplRT7fxJppbZ2vIpAAsIxbgnpIBCRdupvVVjf3SiE" +
                "8lUHBQIYIDc7SBT9YOo+vxjCjggUm3R7T8EIv4vnBITxQDi8dlvGc2h7mTgOIRPZMdjgcpS/3cOx70RU1BYdw5lyaz" +
                "3+TeIb4UWXYJ1bw9H+2VwNjA3NHWO1h2ucRUSICbUORlZd1LLv29TnjnU8IFnw+jCOMxP8S/rk7jH08ykROvim8b50" +
                "nWJL26v5JzHwe5tH5Vd8HKJHS6VfncromJn7WMEhEfqXytmXsnhXSsOc7Lo3Do8oibhnEVf5sc9z/o4IianhrtBFKk" +
                "ll6Q+fs0QZu+QYfM6q8G/FFJxiF1/PPHR15D2qQ1WMLOROgbk6XO+QaLocTIvGT6vh4sK/WUDh1xeHb/yberEmYVQ/" +
                "bM8jQaQDwM9sx8860HCg2E+98/eWBppMrHFvPVCDr3eZzjiB16pYPTXAy3AUYH9lJllCiqCyuloeOaUSGvvngqqKh2" +
                "F4L5PjUjlEkOjNIvfW+tM6/AT4gs3rxmaO9a");
        launch();
    }

    /**
     * 主畫面 起始點
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
//        getPrinterList(300); // 取得印表機清單

        String printerName = "QL-800";
        //       String printerName = "GK888t";
        //       String printerName = "Zebra_Technologies_ZTC_110Xi4-600dpi_ZPL";
        StiReport stiReport = getStiReport();

        exportJpeg(stiReport, StiImageType.Jpeg, 1200,1);
        exportJpeg(stiReport, StiImageType.Jpeg, 1200,2);
        exportPng(stiReport, StiImageType.Jpeg, 1200,1);
        exportPng(stiReport, StiImageType.Jpeg, 1200,2);

//        printFile2("GK888t", exportJpeg(stiReport, StiImageType.Jpeg, 1200));
//        printFile("GK888t", exportJpeg(stiReport, StiImageType.Jpeg, 1200), 1200, PrintQuality.DRAFT);


        //        直印
//        print(printerName, stiReport,203);
//        print(printerName, stiReport,300);
//        print(printerName, stiReport,600);
//        print(printerName, stiReport,1200);

//        printFile("GK888t", exportBmp(stiReport, StiImageType.Jpeg, 600), 600, PrintQuality.HIGH);

        //        printFile("GK888t", exportPdf(stiReport, 600), 600, PrintQuality.HIGH);
//

//        String fileName600 = export(stiReport, StiImageType.Jpeg, StiExportFormat.ImageJpeg, 600);
//        print(printerName, fileName600, 600, PrintQuality.HIGH);

        stiReport.dispose();


//        ExportImage("GK888t", "/home/pi/SFIS/Data/AP000000111/Template/35800134_LAB_1.0.0.1.mrt", testData);
//        printPdfTest("GK888t", "/home/pi/piclient/Label4_Flate_1200_Exactly.pdf");
//        printMrtTest("QL-800", "/home/pi/SFIS/Data/AP000000111/Template/21300005_LAB_1.0.0.1.mrt", testData);
//        printMrtTest("QL-800", "/home/pi/SFIS/Data/AP000000111/Template/35800134_LAB_1.0.0.1.mrt", testData);
//        printMrtTest("Zebra_Technologies_ZTC_110Xi4-600dpi_ZPL", "/home/pi/SFIS/Data/AP000000111/Template/35800134_LAB_1.0.0.1.mrt", testData);
//        printPdfTest("Zebra_Technologies_ZTC_110Xi4-600dpi_ZPL", "/home/pi/SFIS/Data/AP000000111/72300001_20201105_185100.pdf");

        System.exit(0); // 關閉程式
    }


    /**
     * 取得 StiReport 物件
     *
     * @return
     */
    private StiReport getStiReport() {
        StiReport stiReport = null;

        String template = "/home/pi/SFIS/Data/AP000000111/Template/35800134_LAB_1.0.0.1.mrt";

        // 準備合併資料
        Map<String, String> testData = new HashMap<String, String>();
        testData.put("CUSTINFO01_1", "EHAN");
        testData.put("CUSTITEMDESC1_1", "BO-IC400");
        testData.put("SSN_1", "X8XEXZ00011");

        // 載入範本檔
        try {
            stiReport = StiSerializeManager.deserializeReport(new File(template));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (StiDeserializationException e) {
            e.printStackTrace();
        }

        // 載入參數
        for (Map.Entry<String, String> entry : testData.entrySet()) {
            stiReport.setVariable(entry.getKey(), entry.getValue());
        }
        stiReport.Render(false);
        return stiReport;
    }

    /**
     * 將 stiReport 匯出檔案
     *
     * @param stiReport
     * @param imageResolution
     * @param fileName
     */
    private String exportBmp(StiReport stiReport, StiImageType stiImageType, int imageResolution, double imageZoom) {
        String fileName = null;
        StiBmpExportService exportService = new StiBmpExportService();
        // 輸出成JPG
        try {
            String extension = "bmp";

            fileName = String.format("/home/pi/piclient/label-%d.%s", imageResolution, extension);
            StiBmpExportSettings exportSettings = new StiBmpExportSettings();
            exportSettings.setImageFormat(StiColorImageFormat.Grayscale);   // 灰階(黑白反而粗糙)
            exportSettings.setImageType(stiImageType);                      // 格式"
            exportSettings.setImageResolution(imageResolution);             // 解析度
            exportSettings.setExportFormat(StiExportFormat.ImageBmp);
            exportSettings.setImageZoom(imageZoom);
            FileOutputStream file = new FileOutputStream(fileName);
            exportService.exportImage(stiReport, file, exportSettings);
            outputLog(extension);
            file.flush();
            file.close();

        } catch (Exception e) {


        }
        return fileName;
    }

    /**
     * 將 stiReport 匯出檔案
     *
     * @param stiReport
     * @param imageResolution
     * @param fileName
     */
    private String exportPng(StiReport stiReport, StiImageType stiImageType, int imageResolution, double imageZoom) {
        String fileName = null;
        StiPngExportService exportService = new StiPngExportService();
        // 輸出成JPG
        try {
            String extension = "png";
            fileName = String.format("/home/pi/piclient/label-%d-%f.%s", imageResolution, imageZoom,extension);
            StiPngExportSettings exportSettings = new StiPngExportSettings();
            exportSettings.setImageFormat(StiColorImageFormat.Grayscale);   // 灰階(黑白反而粗糙)
            exportSettings.setImageType(stiImageType);                      // 格式"
            exportSettings.setImageResolution(imageResolution);             // 解析度
            exportSettings.setExportFormat(StiExportFormat.ImageJpeg);
            exportSettings.setImageZoom(imageZoom);


            FileOutputStream file = new FileOutputStream(fileName);
            exportService.exportImage(stiReport, file, exportSettings);
            outputLog(extension);
            file.flush();
            file.close();

        } catch (Exception e) {


        }
        return fileName;
    }


    /**
     * 將 stiReport 匯出檔案
     *
     * @param stiReport
     * @param imageResolution
     * @param fileName
     */
    private String exportJpeg(StiReport stiReport, StiImageType stiImageType, int imageResolution, double imageZoom) {
        String fileName = null;
        StiJpegExportService exportService = new StiJpegExportService();
        // 輸出成JPG
        try {
            String extension = "jpeg";
            fileName = String.format("/home/pi/piclient/label-%d-%f.%s", imageResolution, imageZoom,extension);
            StiJpegExportSettings exportSettings = new StiJpegExportSettings();
            exportSettings.setImageFormat(StiColorImageFormat.Grayscale);   // 灰階(黑白反而粗糙)
            exportSettings.setImageType(stiImageType);                      // 格式"
            exportSettings.setImageResolution(imageResolution);             // 解析度
            exportSettings.setExportFormat(StiExportFormat.ImageJpeg);
            exportSettings.setImageZoom(imageZoom);
            FileOutputStream file = new FileOutputStream(fileName);
            exportService.exportImage(stiReport, file, exportSettings);
            outputLog(extension);
            file.flush();
            file.close();

        } catch (Exception e) {


        }
        return fileName;
    }

    /**
     * 將 stiReport 匯出檔案
     *
     * @param stiReport
     * @param imageResolution
     * @param fileName
     */
    private String exportPdf(StiReport stiReport, int imageResolution) {
        String fileName = null;
        StiPdfExportService exportService = new StiPdfExportService();

        try {
            String extension = "Pdf";

            fileName = String.format("/home/pi/piclient/label-%d.%s", imageResolution, extension);
            StiPdfExportSettings exportSettings = new StiPdfExportSettings();
            exportSettings.setImageFormat(StiColorImageFormat.Grayscale);   // 灰階(黑白反而粗糙)
            exportSettings.setImageResolution(imageResolution);             // 解析度
            exportSettings.setExportFormat(StiExportFormat.Pdf);
            FileOutputStream file = new FileOutputStream(fileName);
            exportService.exportPdf(stiReport, file, exportSettings);
            outputLog(extension);
            file.flush();
            file.close();

        } catch (Exception e) {


        }
        return fileName;
    }


    /**
     * 列印檔案(Printable )
     */
    public void printFile(String printerName, String fileName) {
        PrintService printService = getPrinter(printerName); // 取得印表機服務

        // 設置打印參數
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(new Copies(1));                  //份數 [有效]
        printRequestAttributeSet.add(OrientationRequested.PORTRAIT);         //轉向 [有效]


        Doc doc = new SimpleDoc(new TestSample(fileName), DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
        DocPrintJob job = printService.createPrintJob(); // 創建打印作業
        try {
            job.print(doc, printRequestAttributeSet);
        } catch (PrintException e) {
            e.printStackTrace();
        }

    }


    /**
     * 列印檔案
     *
     * @param printerName
     * @param fileName
     * @param imageResolution https://yowlab.shps.kh.edu.tw/javadocs/javax/print/attribute/DocAttribute.html
     *                        https://yowlab.shps.kh.edu.tw/javadocs/javax/print/attribute/PrintRequestAttribute.html
     */
    public void printPng(String printerName, String fileName, int imageResolution, Attribute quality) {
        PrintService printService = getPrinter(printerName); // 取得印表機服務

        InputStream fis = null;

        //日誌輸出打印機的各項屬性
        AttributeSet attrs = printService.getAttributes();
        for (Attribute attr : attrs.toArray()) {
            String attributeName = attr.getName();
            String attributeValue = attrs.get(attr.getClass()).toString();
            outputLog("*" + attributeName + " : " + attributeValue);
        }

        // 設置打印參數
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(new Copies(1));                  //份數 [有效]
        printRequestAttributeSet.add(OrientationRequested.PORTRAIT);         //轉向 [有效]

//        printRequestAttributeSet.add(Chromaticity.MONOCHROME); //轉向 [無效]
//        printRequestAttributeSet.add(new MediaPrintableArea(10, 0, 63, 15, MediaPrintableArea.MM));  // [無效]
//        printRequestAttributeSet.add(new RequestingUserName("Jet", null));  // [無效]
//        printRequestAttributeSet.add(new JobName("Jet", null));             // [無效]
//        printRequestAttributeSet.add(new PrinterResolution(imageResolution, imageResolution, PrinterResolution.DPI));  // [無效]
//        printRequestAttributeSet.add(quality);  // [無效]

        // 設置文件參數
        DocAttributeSet docAttributeSet = new HashDocAttributeSet();
//        printRequestAttributeSet.add(Chromaticity.COLOR); //轉向 [無效]
//        docAttributeSet.add(new PrinterResolution(imageResolution, imageResolution, PrinterResolution.DPI));  // [無效]
//        docAttributeSet.add(new MediaPrintableArea(10, 0, 63, 15, MediaPrintableArea.MM));  // [無效]
//        docAttributeSet.add(quality);  // [無效]
//        docAttributeSet.add(new DocumentName("Jet", null));  // [無效]


        try {
            Doc doc = new SimpleDoc(new FileInputStream(new File(fileName)), DocFlavor.INPUT_STREAM.PNG, docAttributeSet);
            DocPrintJob job = printService.createPrintJob(); // 創建打印作業
            try {
                job.print(doc, printRequestAttributeSet);
            } catch (PrintException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            // 關閉打印的文件流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 列印檔案(Printable )
     */
    public void printFile_Printable(String printerName, String fileName) {
        PrintService printService = getPrinter(printerName); // 取得印表機服務

        PrinterJob printJob = PrinterJob.getPrinterJob();
        try {
            printJob.setPrintService(printService);
        } catch (PrinterException e) {
            e.printStackTrace();
        }

        try {

            printJob.setPrintable(new ImagePrintable(printJob, ImageIO.read(new File(fileName))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            printJob.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }

    }

    /**
     * 列印檔案
     *
     * @param printerName
     * @param fileName
     * @param imageResolution https://yowlab.shps.kh.edu.tw/javadocs/javax/print/attribute/DocAttribute.html
     *                        https://yowlab.shps.kh.edu.tw/javadocs/javax/print/attribute/PrintRequestAttribute.html
     */
    public void printFile(String printerName, String fileName, int imageResolution, Attribute quality) {
        PrintService printService = getPrinter(printerName); // 取得印表機服務

        InputStream fis = null;


        // 設置打印參數
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(new Copies(1));                  //份數 [有效]
        printRequestAttributeSet.add(OrientationRequested.PORTRAIT);         //轉向 [有效]

        //日誌輸出打印機的各項屬性
        AttributeSet attrs = printService.getAttributes();
        for (Attribute attr : attrs.toArray()) {
            String attributeName = attr.getName();
            String attributeValue = attrs.get(attr.getClass()).toString();
            outputLog("*" + attributeName + " : " + attributeValue);
        }


//        printRequestAttributeSet.add(Chromaticity.MONOCHROME); //轉向 [無效]

//        printRequestAttributeSet.add(new MediaPrintableArea(10, 0, 63, 15, MediaPrintableArea.MM));  // [無效]
//        printRequestAttributeSet.add(new RequestingUserName("Jet", null));  // [無效]
//        printRequestAttributeSet.add(new JobName("Jet", null));             // [無效]
//        printRequestAttributeSet.add(new PrinterResolution(imageResolution, imageResolution, PrinterResolution.DPI));  // [無效]

//        printRequestAttributeSet.add(quality);  // [無效]

        // 設置文件參數
        DocAttributeSet docAttributeSet = new HashDocAttributeSet();
//        printRequestAttributeSet.add(Chromaticity.COLOR); //轉向 [無效]
//        docAttributeSet.add(new PrinterResolution(imageResolution, imageResolution, PrinterResolution.DPI));  // [無效]
//        docAttributeSet.add(new MediaPrintableArea(10, 0, 63, 15, MediaPrintableArea.MM));  // [無效]
//        docAttributeSet.add(quality);  // [無效]
//        docAttributeSet.add(new DocumentName("Jet", null));  // [無效]


        try {


            Doc doc = new SimpleDoc(new FileInputStream(new File(fileName)), DocFlavor.INPUT_STREAM.JPEG, docAttributeSet);

            DocPrintJob job = printService.createPrintJob(); // 創建打印作業
            try {
                job.print(doc, printRequestAttributeSet);
            } catch (PrintException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            // 關閉打印的文件流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 直印
     *
     * @param printerName
     * @param stiReport
     * @param imageResolution
     */
    public void print(String printerName, StiReport stiReport, int imageResolution) {

        PrintService printService = getPrinter(printerName); // 取得印表機服務

//            stiReport.getPages().get(0).setOrientation(StiPageOrientation.Portrait); // 0=橫印 , 1直印

//        StiPage page = stiReport.getRenderedPages().get(0);
//        page.setPrintable(true);
//            page.setOrientation(StiPageOrientation.Landscape); // 0=橫印 , 1直印 (Landscape 有轉向)
//            double width = page.getWidth();
//            double height = page.getHeight();
//            page.setWidth(height);
//            page.setHeight(width);


        // 指定輸出印表機
        PrinterJob printerJob = StiPrintHelper.preparePrinterJob(stiReport.getRenderedPages()); // 產生
        try {
            printerJob.setPrintService(printService); // 設定使用的印表機
//            printerJob.setCopies(1);
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//            printRequestAttributeSet.add(new Copies(5)); //份數 [有效]
            printRequestAttributeSet.add(OrientationRequested.REVERSE_LANDSCAPE); //轉向 [有效]
//        printRequestAttributeSet.add(new MediaPrintableArea(0, 0, 63, 15, MediaPrintableArea.MM));  // [無效]
            printRequestAttributeSet.add(new PrinterResolution(imageResolution, imageResolution, PrinterResolution.DPI));  // [無效]

            printRequestAttributeSet.add(PrintQuality.HIGH);


            printerJob.print(printRequestAttributeSet);


//            // 設定紙張
//            PageFormat pageFormat = new PageFormat();
//            pageFormat.setOrientation(PageFormat.REVERSE_LANDSCAPE); // LANDSCAPE(橫) PORTRAIT(縱)
//            Book book = new Book();
//            book.append(pdfPrintable, pageFormat);

        } catch (PrinterException e) {
            e.printStackTrace();
        }


//            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
//            pras.add(OrientationRequested.PORTRAIT) ;
//            printerJob.print(pras);
//            StiPrintHelper.printJob(printerJob, stiReport, false);
        outputLog("printing Success");

    }


    /**
     * 依指定的條件, 取得印表機列表
     *
     * @param resolution 解析度
     */
    private void getPrinterList(int resolution) {
        outputLog("===== List Printer Start =====");

        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(new PrinterResolution(resolution, resolution, PrinterResolution.DPI)); // 篩選印表機

        // 取得印表機
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, pras);
        if (printServices != null) {
            for (int i = 0; i < printServices.length; i++) {
                ;
                outputLog(printServices[i].getName());
            }
        }
        outputLog("===== List Printer Success =====");
        return;
    }


    /**
     * 依指定的印表機名稱, 取得印表機物件 PrintService
     *
     * @param printerName 印表機名稱
     * @return
     */
    private PrintService getPrinter(String printerName) {
        outputLog("===== Get Printer Start =====");

        // 取得印表機
        PrintService printService = null;
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        if (printServices != null) {
            for (int i = 0; i < printServices.length; i++) {
                String printName = printServices[i].getName();
                if (printName.equals(printerName)) {
                    printService = printServices[i];
                }
            }
        }
        outputLog("===== Get Printer Success =====");
        return printService;
    }


    // =================================================================================================================
    // =================================================================================================================
    // =================================================================================================================
    // =================================================================================================================


    /**
     * 輸出 Log (時間 @PiClient> )
     *
     * @param value
     */
    public void outputLog(String value) {
        String time = new SimpleDateFormat("HH.mm.ss.SSS").format(new Date());
        System.out.println(time + " @PiClient> " + value);
    }


    // ========================================================================================================================================================
    // 測試用
    // ========================================================================================================================================================

    /**
     * 旋轉列印 MRT (測試完請刪除)
     *
     * @param printer   印表機名稱
     * @param template  範本檔
     * @param mergeData 合併資料
     */
    public void printMrt(String printer, String template, Map<String, String> mergeData) {
        StiReport stiReport = null;
        PrintService printService = getPrinter(printer); // 取得印表機
        // 讀取範本
        try {
            // 載入範本檔
            stiReport = StiSerializeManager.deserializeReport(new File(template));
            // 載入參數
            for (Map.Entry<String, String> entry : mergeData.entrySet()) {
                stiReport.setVariable(entry.getKey(), entry.getValue() + "                                      ");
            }

            stiReport.Render(false);

//            stiReport.getPages().get(0).setOrientation(StiPageOrientation.Portrait); // 0=橫印 , 1直印

            StiPage page = stiReport.getRenderedPages().get(0);
            page.setPrintable(true);
//            page.setOrientation(StiPageOrientation.Landscape); // 0=橫印 , 1直印 (Landscape 有轉向)
//            double width = page.getWidth();
//            double height = page.getHeight();
//            page.setWidth(height);
//            page.setHeight(width);


        } catch (IOException e) {
            outputLog("----- IOException: " + e.getMessage());
        } catch (SAXException e) {
            outputLog("----- SAXException: " + e.getMessage());
        } catch (StiDeserializationException e) {
            outputLog("----- StiDeserializationException: " + e.getMessage());
        }

        // 指定輸出印表機
        PrinterJob printerJob = StiPrintHelper.preparePrinterJob(stiReport.getRenderedPages()); // 產生
        try {
            printerJob.setPrintService(printService); // 設定使用的印表機
            printerJob.setCopies(1);


            printerJob.print();
            ;

//            // 設定紙張
//            PageFormat pageFormat = new PageFormat();
//            pageFormat.setOrientation(PageFormat.REVERSE_LANDSCAPE); // LANDSCAPE(橫) PORTRAIT(縱)
//            Book book = new Book();
//            book.append(pdfPrintable, pageFormat);

        } catch (PrinterException e) {
            e.printStackTrace();
        }

        try {
//            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
//            pras.add(OrientationRequested.PORTRAIT) ;
//            printerJob.print(pras);
            StiPrintHelper.printJob(printerJob, stiReport, false);
            outputLog("printing Success");
        } catch (PrinterException pe) {

        }
    }


    // =========================================================================================================================================================================
    // 測試列印
    // =========================================================================================================================================================================


    //
//    /**
//     * 旋轉列印 MRT (測試完請刪除)
//     *
//     * @param printer   印表機名稱
//     * @param template  範本檔
//     * @param mergeData 合併資料
//     */
    public void ExportImage(String printer, String template, Map<String, String> mergeData) {
        StiReport stiReport = null;
        PrintService printService = getPrinter(printer); // 取得印表機

        // 載入範本檔
        try {
            stiReport = StiSerializeManager.deserializeReport(new File(template));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (StiDeserializationException e) {
            e.printStackTrace();
        }

        // 載入參數
        for (Map.Entry<String, String> entry : mergeData.entrySet()) {
            stiReport.setVariable(entry.getKey(), entry.getValue());
        }

        stiReport.Render(false);


        try {
            ExportJPG(stiReport, 203, "/home/pi/piclient/label");
            JPGPrint(String.format("%s-%d.jpg", "/home/pi/piclient/label", 203), printService);
//            PDFPrint("/home/pi/piclient/Label4_Flate_1200_Exactly.pdf", printService);
            outputLog("SUCCESS");
        } catch (Exception e) {

        }

        PrinterJob printerJob = StiPrintHelper.preparePrinterJob(stiReport.getRenderedPages());

        try {
            printerJob.setPrintService(printService);
        } catch (PrinterException e) {
            e.printStackTrace();
        }

        try {
            // 職印
            StiPrintHelper.printJob(printerJob, stiReport, false);

        } catch (PrinterException pe) {

        }
    }


    /**
     * 將 stiReport 匯出成 JPG
     *
     * @param stiReport
     * @param imageResolution
     * @param fileName
     */
    public void ExportJPG(StiReport stiReport, int imageResolution, String fileName) {

        // 輸出成JPG
        try {
            outputLog(String.format("%s-%d.jpg", fileName, imageResolution));
            StiJpegExportSettings exportSettings = new StiJpegExportSettings();
            exportSettings.setImageFormat(StiColorImageFormat.Grayscale);  // 灰階
            exportSettings.setImageType(StiImageType.Jpeg);                // 格式
            exportSettings.setImageResolution(imageResolution);            // 解析度
            exportSettings.setExportFormat(StiExportFormat.ImageJpeg);
            StiImageExportService exportService1 = new StiJpegExportService();
            FileOutputStream file = new FileOutputStream(String.format("%s-%d.jpg", fileName, imageResolution));
            exportService1.exportImage(stiReport, file, exportSettings);
            file.flush();
            file.close();


            outputLog("SUCCESS");
        } catch (Exception e) {


        }

    }


    public void JPGPrint(String fileName, PrintService printService) throws PrintException {
        InputStream fis = null;
        try {
            // 設置打印參數
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

            aset.add(new Copies(1)); //份數
            aset.add(OrientationRequested.PORTRAIT);
            aset.add(new PrinterResolution(203, 203, PrinterResolution.DPI));
            DocAttributeSet docAttributeSet = new HashDocAttributeSet();
            docAttributeSet.add(new PrinterResolution(203, 203, PrinterResolution.DPI));
//            docAttributeSet.add(new PrintQuality(1));
            Doc doc = new SimpleDoc(new FileInputStream(new File(fileName)), DocFlavor.INPUT_STREAM.JPEG, docAttributeSet);
            DocPrintJob job = printService.createPrintJob(); // 創建打印作業
            job.print(doc, aset);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            // 關閉打印的文件流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void PDFPrint(String fileName, PrintService printService) throws PrintException {
        InputStream fis = null;
        try {
            // 設置打印參數
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

            aset.add(new Copies(1)); //份數
            aset.add(OrientationRequested.PORTRAIT);
            aset.add(new PrinterResolution(203, 203, PrinterResolution.DPI));
            DocAttributeSet docAttributeSet = new HashDocAttributeSet();
            docAttributeSet.add(new PrinterResolution(203, 203, PrinterResolution.DPI));
//            docAttributeSet.add(new PrintQuality(1));
            Doc doc = new SimpleDoc(new FileInputStream(new File(fileName)), DocFlavor.INPUT_STREAM.PDF, docAttributeSet);
            DocPrintJob job = printService.createPrintJob(); // 創建打印作業
            job.print(doc, aset);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            // 關閉打印的文件流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
