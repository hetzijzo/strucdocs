package org.strucdocs.component.document;


import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.strucdocs.model.Song;
import org.strucdocs.model.SongPart;

import java.io.OutputStream;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentGeneratorService {

    private static final Chunk TAB = new Chunk(new VerticalPositionMark(), 200, true);
    public static final Font FONT = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

    public void generateDocument(OutputStream outputStream, Song song) throws DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        String songTitle = song.getTitle();
        document.addCreationDate();
        document.addProducer();
        document.addTitle(songTitle);

        document.add(generateTitleParagraph(songTitle, song.getArtist().getName()));

        addEmptyLine(new Paragraph(), 2);

        for (SongPart part : song.getParts()) {
            Paragraph paragraph = new Paragraph(part.getType(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            paragraph.setSpacingBefore(10f);
            paragraph.setSpacingAfter(2f);
            document.add(paragraph);
            part.getLines().forEach(line -> {
                try {
                    Paragraph chordsParagraph = new Paragraph(
                        line.getChords().stream().collect(Collectors.joining("  ")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD));
                    chordsParagraph.setIndentationLeft(15f);

                    Paragraph lyricsParagraph = new Paragraph(line.getLyrics(), FONT);
                    lyricsParagraph.setIndentationLeft(15f);

                    document.add(chordsParagraph);
                    document.add(lyricsParagraph);
                } catch (DocumentException e) {
                    throw new IllegalStateException(e);
                }
            });
        }

        BarcodeQRCode qrcode =
            new BarcodeQRCode("http://localhost:8080/documents/songs/" + song.getUuid(), 50, 50, null);
        document.add(qrcode.getImage());
        document.newPage();

        document.close();
    }

    private Paragraph generateTitleParagraph(String title, String artistName) {
        Paragraph titleParagraph = new Paragraph(title, new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD));
        titleParagraph.add(new Chunk(" - "));
        titleParagraph.add(new Chunk(artistName));
        titleParagraph.add(new Chunk(TAB));
        return titleParagraph;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
