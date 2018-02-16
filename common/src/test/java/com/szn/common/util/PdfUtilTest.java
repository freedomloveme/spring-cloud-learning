package com.szn.common.util;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PdfUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class PdfUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testConcatPDFs() throws Exception {
        String[] fileNames = {"E:\\新建文件夹\\卢兴康1", "E:\\新建文件夹\\卢兴康2", "E:\\新建文件夹\\卢兴康3", "E:\\新建文件夹\\卢兴康4", "E:\\新建文件夹\\卢兴康5"};
        List<InputStream> pdfs = new ArrayList<>();
        for (String string : fileNames) {
            pdfs.add(new FileInputStream(string + ".pdf"));
        }
        OutputStream output = new FileOutputStream("E:\\新建文件夹\\卢兴康.pdf");
        PdfUtil.concatPdfs(pdfs, output, false);
    }

    @Test
    public void testWatermarkPdf() throws Exception{
        File file = new File("E:\\新建文件夹\\id2_watermark.pdf");
        FileInputStream fis = new FileInputStream("E:\\新建文件夹\\id2.pdf") ;
        String imageFilePath = "";
        PdfUtil.watermarkPdf(file, fis, imageFilePath);
    }


} 
