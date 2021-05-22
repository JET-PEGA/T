package sfis.raspberrypi.piclient;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImagePrintable implements Printable {

    private double x, y, width;
    private int orientation;
    private BufferedImage image;

    public ImagePrintable(PrinterJob printJob, BufferedImage image) {

        this.image = image;
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {

        g.drawImage(image, (int) 0, (int) 0, 2500, 1250, null);
        return PAGE_EXISTS;

    }

    /**
     * 輸出 Log (時間 @PiClient> )
     *
     * @param value
     */
    public void outputLog(String value) {
        String time = new SimpleDateFormat("HH.mm.ss.SSS").format(new Date());
        System.out.println(time + " @PiClient> " + value);
    }
}
