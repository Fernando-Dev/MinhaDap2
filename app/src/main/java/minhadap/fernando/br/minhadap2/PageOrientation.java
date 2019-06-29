package minhadap.fernando.br.minhadap2;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Created by Flavia on 30/01/2019.
 */

public class PageOrientation extends PdfPageEventHelper {
    protected PdfNumber rotation = PdfPage.PORTRAIT;

    public void setRotation(PdfNumber rotation) {
        this.rotation = rotation;
    }

    public void onEndPage(PdfWriter writer, Document document) {
        writer.addPageDictEntry(PdfName.ROTATE, rotation);
    }
}
