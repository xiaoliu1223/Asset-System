package com.cg.test.am.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.HashMap;

public class Test {

    public static void main(String[] args) throws IOException {

        PoiHandleWordUtils poiHandleWordUtils = new PoiHandleWordUtils();
        HashMap<String, Object> paragraphMap = new HashMap<>();
        paragraphMap.put("${id}", 1234567899);
        paragraphMap.put("${reviewType}", "成品");

        // 评审单模板
        String filePath = "D:\\不合格评审单.docx";
        InputStream is = new FileInputStream(filePath);
        OutputStream os = null;
        XWPFDocument xwpfDocument = new XWPFDocument(is);

        poiHandleWordUtils.replaceInPara(xwpfDocument, paragraphMap);


        HashMap<String, Object> tableMap = new HashMap<>();
        tableMap.put("${typeSpec}", "abc");
        tableMap.put("${identifyNo}", "546454rrr");
        tableMap.put("${machineNo}", "777888");
        tableMap.put("${problemCount}", "546454rrr");
        tableMap.put("${problemDescription}", "错了哈");
        tableMap.put("${customerType}", "军品");
        tableMap.put("${updateTime}", "2021-04-21");
        tableMap.put("${mrbOpinion}", "hhhhhh");
        tableMap.put("${customerOpinion}", "546454rrr");
        tableMap.put("${qualifiedCount}", "546454rrr");
        tableMap.put("${reworkUser}", "546454rrr");
        tableMap.put("${unqualifiedCount}", "pppppp");
        tableMap.put("${reworkCheckUser}", "1231654564");
        tableMap.put("${unqualifiedHandle}", "546454rrr444");
        tableMap.put("${reworkHour}", "546454rrr");
        tableMap.put("${materialFee}", "546454rrr");
        tableMap.put("${workFee}", "546454rrr");
        tableMap.put("${remainValue}", "546454rrr");
        tableMap.put("${otherFee}", "546454rrr");
        tableMap.put("${totalFee}", "546454rrr");

        poiHandleWordUtils.replaceInTable(xwpfDocument, tableMap);

        String newPath = "D:\\" + 1234567899 + ".docx";
        os = new FileOutputStream(new File(newPath));
        xwpfDocument.write(os);

        os.close();
        is.close();
    }
}
