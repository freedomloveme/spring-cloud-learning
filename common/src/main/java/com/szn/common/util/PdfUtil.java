package com.szn.common.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PdfUtil {

    private static final String USER_PASSWORD = null;

    private static final String OWNER_PASSWORD = "123456";

        private static final String WATER_MARK = "测试使用";

    /**
     * pdf添加水印
     *
     * @param localTempOutFile 本地生成文件
     * @param is               需要添加水印的文件
     * @param imageFilePath    水印图片路径
     * @throws DocumentException pdf处理异常
     * @throws IOException       IO异常
     */
    public static void watermarkPdf(File localTempOutFile, InputStream is, String imageFilePath) throws DocumentException, IOException {
        PdfReader pdfReader = null;
        PdfStamper stamper = null;
        FileOutputStream fos = null;
        try {
            pdfReader = new PdfReader(is);
            fos = new FileOutputStream(localTempOutFile);
            stamper = new PdfStamper(pdfReader, fos);
            stamper.setEncryption(null, OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING, true);
            if (StringUtils.isNotBlank(WATER_MARK)) {
                BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                int total = pdfReader.getNumberOfPages() + 1;
                // 添加水印图片
                Image image = null;
                if (StringUtils.isNotBlank(imageFilePath)) {
                    image = Image.getInstance(imageFilePath);
                    image.setAbsolutePosition(200, 400);
                }
                PdfContentByte under;
                Rectangle pageRect;
                for (int i = 1; i < total; i++) {
                    pageRect = stamper.getReader().getPageSizeWithRotation(i);
                    // 计算水印X,Y坐标
                    float x = pageRect.getWidth() / 2;
                    float y = pageRect.getHeight() / 2;
                    // 获得PDF最顶层
                    under = stamper.getOverContent(i);
                    under.saveState();
                    // set Transparency
                    PdfGState gs = new PdfGState();
                    // 设置透明度为0.2
                    gs.setFillOpacity(0.2f);
                    under.setGState(gs);
                    // 注意这里必须调用一次restoreState 否则设置无效
                    // under.restoreState();
                    under.beginText();
                    under.setFontAndSize(base, 20);
                    under.setColorFill(BaseColor.RED);
                    // 水印文字成45度角倾斜
                    under.showTextAligned(Element.ALIGN_CENTER, WATER_MARK, x, y, 60);
                    // 添加水印文字
                    under.endText();
                    // 添加水印图片
                    if (null != image) {
                        under.addImage(image);
                    }
                    under.setLineWidth(1f);
                    under.stroke();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new DocumentException(e);
        } finally {
            if (null != stamper) {
                stamper.close();
            }
            if (null != fos) {
                fos.close();
            }
            if (null != pdfReader) {
                pdfReader.close();
            }
        }
    }

    /**
     * pdf合并
     *
     * @param streamOfPDFFiles 需要合并的pdf文件集合
     * @param outputStream     合并后的文件
     * @param paginate         是否需要添加页码
     */
    public static void concatPdfs(List<InputStream> streamOfPDFFiles, OutputStream outputStream, boolean paginate) {
        Document document = new Document();
        try {
            List<PdfReader> readers = new ArrayList<>();
            int totalPages = 0;

            // 获取需要读取PDF文件
            for (InputStream inputStream : streamOfPDFFiles) {
                PdfReader pdfReader = new PdfReader(inputStream);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            // 创建PDF输出流
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            // 加密
            if (StringUtils.isNotBlank(USER_PASSWORD) && StringUtils.isNotBlank(OWNER_PASSWORD)) {
                writer.setEncryption(USER_PASSWORD.getBytes(), OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
            } else if (StringUtils.isBlank(USER_PASSWORD) && StringUtils.isNotBlank(OWNER_PASSWORD)) {
                writer.setEncryption(null, OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
            } else if (StringUtils.isBlank(USER_PASSWORD) && StringUtils.isBlank(OWNER_PASSWORD)) {
                writer.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
            }

            document.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            // Holds the PDF
            PdfContentByte cb = writer.getDirectContent();
            // data
            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;

            // Loop through the PDF files and add to the output.
            for (PdfReader pdfReader : readers) {
                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);

                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + currentPageNumber + " of " + totalPages, 520, 5, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }
}