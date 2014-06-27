package com.gcj.jmeter;

import com.gcj.bean.AggregateReport;
import com.gcj.file.FileTools;

import javax.swing.*;
import java.util.List;

public class ResultAnalysisMain extends JFrame {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        String path = "G:\\工作\\42.新闻app性能测试\\01.性能测试\\02.20140523\\";
//        String test = "\\\\";
//        System.out.println(test);
//        if (path.endsWith("\\")) {
//            System.out.println(path);
//        } else {
//
//        }
        if (args.length == 0 || args == null) {
            System.err.println("请输入文件的路径，例如：\" /Users/gaochuanjun/Documents/12.新闻app性能测试/01.性能测试/02.20140523\"或者\"G:\\工作\\42.新闻app性能测试\\01.性能测试\\02.20140523\"");
            System.exit(1);
        }
        FileTools fileTools = new FileTools();
        List<String> filePaths = fileTools.getAllFileDirectory("G:\\工作\\42.新闻app性能测试\\01.性能测试\\02.20140523");
        for (String filePath : filePaths) {
            System.out.println(filePath);
        }
        List<AggregateReport> aggregateReports = fileTools.getContent(filePaths);
        for (AggregateReport aggregateReport : aggregateReports) {
            System.out.println(aggregateReport.getThreads() + "," + aggregateReport.getLabel() + "," + aggregateReport.getSamples() + "," + aggregateReport.getAverage() + "," + aggregateReport.getMedian() + "," + aggregateReport.getNinetyLine() + "," + aggregateReport.getMin() + "," + aggregateReport.getMax() + "," + aggregateReport.getError() + "," + aggregateReport.getThroughput());
        }
        fileTools.writeFile("/Users/gaochuanjun/Documents/12.新闻app性能测试/01.性能测试/02.20140523/result.csv", aggregateReports);
    }
}
