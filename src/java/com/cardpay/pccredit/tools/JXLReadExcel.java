package com.cardpay.pccredit.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder; 
/**
 * @功能描述 POI 读取 Excel 转 HTML 支持 03xls 和 07xlsx 版本  包含样式
 */
public class JXLReadExcel {
	/*public static void main(String[] args) {
		JXLReadExcel jxl = new JXLReadExcel();
		String path = "C:\\Users\\Administrator\\Desktop\\小微贷标准版0806模板---周素平12万.xlsx";
		jxl.readExcelToHtml(path, 0, true);
	}*/
	
	//定义四个sheet需要读取的最大行列
	String RowAndCol[][] = {
			{"37","D"},
			{"39","P"},
			{"43","P"},
			{"30","C"}
		};
	
	//这两列要适当加宽
	int titleAt_zf[] = {25,25,25,25};
	int titleAt_sy[] = {10,10,5,5,5,5,5,5,5,5,5,5,5,5,10,10};
	int titleAt_jl[] = {10,10,5,5,5,5,5,5,5,5,5,5,5,5,5,10};
	int titleAt_jc[] = {34,33,33};
	
	//可编辑的单元格
	String editAble_zf[] = {
				"B6","B7","B8","D6","D7","D8",
				"B10","B11","B12","D10","D11","D12",
				"B14","B15","B16","D14","D15","D16",
				"B18","B19","B20","D18","D19","D20",
				"B22","B23","B24","B25","B26","B27","D22","D23","D24","D25","D26","D27",
				"B29","B30","B31",
				"D36"
			};
	String editAble_sy[] = {
				"C5","D5","E5","F5","G5","H5","I5","J5","K5","L5","M5","N5","O5","P5",
				"C6","D6","E6","F6","G6","H6","I6","J6","K6","L6","M6","N6","O6","P6",
				
				"C9","D9","E9","F9","G9","H9","I9","J9","K9","L9","M9","N9","O9","P9",
				"C10","D10","E10","F10","G10","H10","I10","J10","K10","L10","M10","N10","O10","P10",
				"C11","D11","E11","F11","G11","H11","I11","J11","K11","L11","M11","N11","O11","P11",
				
				"C15","D15","E15","F15","G15","H15","I15","J15","K15","L15","M15","N15","O15","P15",
				"C16","D16","E16","F16","G16","H16","I16","J16","K16","L16","M16","N16","O16","P16",
				"C17","D17","E17","F17","G17","H17","I17","J17","K17","L17","M17","N17","O17","P17",
				"C18","D18","E18","F18","G18","H18","I18","J18","K18","L18","M18","N18","O18","P18",
				"C19","D19","E19","F19","G19","H19","I19","J19","K19","L19","M19","N19","O19","P19",
				"C20","D20","E20","F20","G20","H20","I20","J20","K20","L20","M20","N20","O20","P20",
				"C21","D21","E21","F21","G21","H21","I21","J21","K21","L21","M21","N21","O21","P21",
				"C22","D22","E22","F22","G22","H22","I22","J22","K22","L22","M22","N22","O22","P22",
				"C23","D23","E23","F23","G23","H23","I23","J23","K23","L23","M23","N23","O23","P23",
				"C24","D24","E24","F24","G24","H24","I24","J24","K24","L24","M24","N24","O24","P24",
				"C25","D25","E25","F25","G25","H25","I25","J25","K25","L25","M25","N25","O25","P25",
				"C26","D26","E26","F26","G26","H26","I26","J26","K26","L26","M26","N26","O26","P26",
				"C27","D27","E27","F27","G27","H27","I27","J27","K27","L27","M27","N27","O27","P27",
				"C28","D28","E28","F28","G28","H28","I28","J28","K28","L28","M28","N28","O28","P28",
				"C29","D29","E29","F29","G29","H29","I29","J29","K29","L29","M29","N29","O29","P29",
				"C30","D30","E30","F30","G30","H30","I30","J30","K30","L30","M30","N30","O30","P30",
				"C31","D31","E31","F31","G31","H31","I31","J31","K31","L31","M31","N31","O31","P31",
				
				"C35","D35","E35","F35","G35","H35","I35","J35","K35","L35","M35","N35","O35","P35",
				"C36","D36","E36","F36","G36","H36","I36","J36","K36","L36","M36","N36","O36","P36",
				"C37","D37","E37","F37","G37","H37","I37","J37","K37","L37","M37","N37","O37","P37",
				"C38","D38","E38","F38","G38","H38","I38","J38","K38","L38","M38","N38","O38","P38"
			};
	String editAble_jl[] = {
				"C5","D5","E5","F5","G5","H5","I5","J5","K5","L5","M5","N5","O5",
				"C6","D6","E6","F6","G6","H6","I6","J6","K6","L6","M6","N6","O6",
				"C7","D7","E7","F7","G7","H7","I7","J7","K7","L7","M7","N7","O7",
				
				"C9","D9","E9","F9","G9","H9","I9","J9","K9","L9","M9","N9","O9",
				"C10","D10","E10","F10","G10","H10","I10","J10","K10","L10","M10","N10","O10",
				"C11","D11","E11","F11","G11","H11","I11","J11","K11","L11","M11","N11","O11",
				
				"C13","D13","E13","F13","G13","H13","I13","J13","K13","L13","M13","N13","O13",
				"C14","D14","E14","F14","G14","H14","I14","J14","K14","L14","M14","N14","O14",
				"C15","D15","E15","F15","G15","H15","I15","J15","K15","L15","M15","N15","O15",
				"C16","D16","E16","F16","G16","H16","I16","J16","K16","L16","M16","N16","O16",
				"C17","D17","E17","F17","G17","H17","I17","J17","K17","L17","M17","N17","O17",
				"C18","D18","E18","F18","G18","H18","I18","J18","K18","L18","M18","N18","O18",
				"C19","D19","E19","F19","G19","H19","I19","J19","K19","L19","M19","N19","O19",
				"C20","D20","E20","F20","G20","H20","I20","J20","K20","L20","M20","N20","O20",
				"C21","D21","E21","F21","G21","H21","I21","J21","K21","L21","M21","N21","O21",
				
				"C25","D25","E25","F25","G25","H25","I25","J25","K25","L25","M25","N25","O25",
				"C26","D26","E26","F26","G26","H26","I26","J26","K26","L26","M26","N26","O26",
				"C27","D27","E27","F27","G27","H27","I27","J27","K27","L27","M27","N27","O27",
				"C28","D28","E28","F28","G28","H28","I28","J28","K28","L28","M28","N28","O28",
				"C29","D29","E29","F29","G29","H29","I29","J29","K29","L29","M29","N29","O29",
				
				"C31","D31","E31","F31","G31","H31","I31","J31","K31","L31","M31","N31","O31",
				"C32","D32","E32","F32","G32","H32","I32","J32","K32","L32","M32","N32","O32",
				"C33","D33","E33","F33","G33","H33","I33","J33","K33","L33","M33","N33","O33",
				"C34","D34","E34","F34","G34","H34","I34","J34","K34","L34","M34","N34","O34",
				
				"C36","D36","E36","F36","G36","H36","I36","J36","K36","L36","M36","N36","O36",
				"C37","D37","E37","F37","G37","H37","I37","J37","K37","L37","M37","N37","O37",
				"C38","D38","E38","F38","G38","H38","I38","J38","K38","L38","M38","N38","O38"
			};
	
	String editAble_jc[] = {
				"C4","C6","C10",
				"B13","B14",
				"B16","B17","B18",
				"B20","B21","B22","B23","B24","B25"
			};
	
    /**
     * 程序入口方法
     * @param filePath 文件的路径
     * @param isWithStyle 是否需要表格样式 包含 字体 颜色 边框 对齐方式
     * @return <table>...</table> 字符串
     */
    public String[] readExcelToHtml(String filePath, boolean isWithStyle){
        
    	String sheet[] = new String[4];
        InputStream is = null;
        String htmlExcel = null;
        try {
            File sourcefile = new File(filePath);
            is = new FileInputStream(sourcefile);
            Workbook wb = WorkbookFactory.create(is);
            for(int i=0;i<wb.getNumberOfSheets();i++)
            {
            	if(wb.getSheetAt(i).getSheetName().indexOf("资负")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        htmlExcel = getExcelInfo(xWb,i,isWithStyle,RowAndCol[0],editAble_zf,titleAt_zf);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        htmlExcel = getExcelInfo(hWb,i,isWithStyle,RowAndCol[0],editAble_zf,titleAt_zf);
                    }
                	String content_base64 = getBASE64(htmlExcel);
            		sheet[0] = content_base64;
            	}
            	else if(wb.getSheetAt(i).getSheetName().indexOf("损益")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        htmlExcel = getExcelInfo(xWb,i,isWithStyle,RowAndCol[1],editAble_sy,titleAt_sy);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        htmlExcel = getExcelInfo(hWb,i,isWithStyle,RowAndCol[1],editAble_sy,titleAt_sy);
                    }
                	String content_base64 = getBASE64(htmlExcel);
            		sheet[1] = content_base64;
            	}
				else if(wb.getSheetAt(i).getSheetName().indexOf("金流")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        htmlExcel = getExcelInfo(xWb,i,isWithStyle,RowAndCol[2],editAble_jl,titleAt_jl);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        htmlExcel = getExcelInfo(hWb,i,isWithStyle,RowAndCol[2],editAble_jl,titleAt_jl);
                    }
                	String content_base64 = getBASE64(htmlExcel);
					sheet[2] = content_base64;
            	}
				else if(wb.getSheetAt(i).getSheetName().indexOf("交叉")>=0){
					if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        htmlExcel = getExcelInfo(xWb,i,isWithStyle,RowAndCol[3],editAble_jc,titleAt_jc);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        htmlExcel = getExcelInfo(hWb,i,isWithStyle,RowAndCol[3],editAble_jc,titleAt_jc);
                    }
                	String content_base64 = getBASE64(htmlExcel);
					sheet[3] = content_base64;
				}
            	
            	
            	
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sheet;
    }
    
    
    
    public String getExcelInfo(Workbook wb,int index,boolean isWithStyle,String[] rowAndcol,String[] editAble,int[] titleAt) throws Exception{
        int maxRow = Integer.parseInt(rowAndcol[0])-1;//最大行
        String maxCol = rowAndcol[1];//最大列
        
        StringBuffer sb = new StringBuffer();
        Sheet sheet = wb.getSheetAt(index);//获取第一个Sheet的内容
        int lastRowNum = sheet.getLastRowNum();
        if(lastRowNum>maxRow){
        	lastRowNum = maxRow;
        }
        Map<String, String> map[] = getRowSpanColSpanMap(sheet);
        sb.append("<table id='MyTable' style='border-collapse:collapse;' width='100%'>");
        Row row = null;        //兼容
        Cell cell = null;    //兼容
        
        for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
            row = sheet.getRow(rowNum);
            if (row == null) {
                sb.append("<tr><td > &nbsp;</td></tr>");
                continue;
            }
            sb.append("<tr>");
            int lastColNum = row.getLastCellNum();
            if(lastColNum > columnToIndex(maxCol)){
            	lastColNum = columnToIndex(maxCol);
            }
            for (int colNum = 0; colNum < lastColNum; colNum++) {
                cell = row.getCell(colNum);
                if (cell == null) {    //特殊情况 空白的单元格会返回null
                    sb.append("<td>&nbsp;</td>");
                    continue;
                }
                String stringValue = getCellValue(cell);
                if (map[0].containsKey(rowNum + "," + colNum)) {
                    String pointString = map[0].get(rowNum + "," + colNum);
                    map[0].remove(rowNum + "," + colNum);
                    int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
                    int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
                    int rowSpan = bottomeRow - rowNum + 1;
                    int colSpan = bottomeCol - colNum + 1;
                    sb.append("<td rowspan= '" + rowSpan + "' colspan= '"+ colSpan + "' ");
                } else if (map[1].containsKey(rowNum + "," + colNum)) {
                    map[1].remove(rowNum + "," + colNum);
                    continue;
                } else {
                    sb.append("<td ");
                }
                
                String tmp = indexToColumn(colNum+1) +(rowNum+1);
                sb.append("name='"+tmp+"' ");
                if(Arrays.asList(editAble).contains(tmp)){//判断是否可编辑
                	sb.append("onclick='return EditCell();' ");
                }
                
                //判断是否需要样式
                if(isWithStyle){
                    dealExcelStyle(wb, sheet, cell, sb,titleAt);//处理单元格样式
                }
                
                sb.append(">");
                
                if (stringValue == null || "".equals(stringValue.trim())) {
                    sb.append("&nbsp;");
                } else {
                    // 将ascii码为160的空格转换为html下的空格（&nbsp;）
                	stringValue = stringValue.replaceAll(",", "");
                    sb.append(stringValue.replace(String.valueOf((char) 160),"&nbsp;"));
                }
                sb.append("</td>");
            }
            sb.append("</tr>");
        }

        sb.append("</table>");
        return sb.toString();
    }
    
    private Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

        Map<String, String> map0 = new HashMap<String, String>();
        Map<String, String> map1 = new HashMap<String, String>();
        int mergedNum = sheet.getNumMergedRegions();
        CellRangeAddress range = null;
        for (int i = 0; i < mergedNum; i++) {
            range = sheet.getMergedRegion(i);
            int topRow = range.getFirstRow();
            int topCol = range.getFirstColumn();
            int bottomRow = range.getLastRow();
            int bottomCol = range.getLastColumn();
            map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
            // System.out.println(topRow + "," + topCol + "," + bottomRow + "," + bottomCol);
            int tempRow = topRow;
            while (tempRow <= bottomRow) {
                int tempCol = topCol;
                while (tempCol <= bottomCol) {
                    map1.put(tempRow + "," + tempCol, "");
                    tempCol++;
                }
                tempRow++;
            }
            map1.remove(topRow + "," + topCol);
        }
        Map[] map = { map0, map1 };
        return map;
    }
    
    
    /**
     * 获取表格单元格Cell内容
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell) {

        String result = new String();  
        switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_NUMERIC:// 数字类型  
        case Cell.CELL_TYPE_FORMULA:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
                SimpleDateFormat sdf = null;  
                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
                    sdf = new SimpleDateFormat("HH:mm");  
                } else {// 日期  
                    sdf = new SimpleDateFormat("yyyy-MM-dd");  
                }  
                Date date = cell.getDateCellValue();  
                result = sdf.format(date);  
            } else if (cell.getCellStyle().getDataFormat() == 14 
            		|| cell.getCellStyle().getDataFormat() == 20
            		|| cell.getCellStyle().getDataFormat() == 31 
    				|| cell.getCellStyle().getDataFormat() == 32 
            		|| cell.getCellStyle().getDataFormat() == 57 
            		|| cell.getCellStyle().getDataFormat() == 58) {  
                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
                double value = cell.getNumericCellValue();  
                Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
                result = sdf.format(date);  
            } else {  
                double value = cell.getNumericCellValue();  
                CellStyle style = cell.getCellStyle();  
                DecimalFormat format = new DecimalFormat();  
                String temp = style.getDataFormatString();  
                // 单元格设置成常规  
                if (temp.equals("General")) {  
                    format.applyPattern("#");  
                }  
                result = format.format(value);  
            }  
            break;  
        case Cell.CELL_TYPE_STRING:// String类型  
            result = cell.getStringCellValue().toString(); 
            break;  
        case Cell.CELL_TYPE_BLANK:  
            result = "";  
            break; 
        default:  
            result = "";  
            break;  
        }  
        return result;  
    }
    
    /**
     * 处理表格样式
     * @param wb
     * @param sheet
     * @param cell
     * @param sb
     * @throws Exception 
     */
    private void dealExcelStyle(Workbook wb,Sheet sheet,Cell cell,StringBuffer sb,int[] titleAt) throws Exception{
        
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null) {
            short alignment = cellStyle.getAlignment();
            sb.append("align='" + convertAlignToHtml(alignment) + "' ");//单元格内容的水平对齐方式
            short verticalAlignment = cellStyle.getVerticalAlignment();
            sb.append("valign='"+ convertVerticalAlignToHtml(verticalAlignment)+ "' ");//单元格中内容的垂直排列方式
            
            if (wb instanceof XSSFWorkbook) {
                            
                XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont(); 
                short boldWeight = xf.getBoldweight();
                sb.append("style='");
                sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
                sb.append("font-size: " + xf.getFontHeight() / 2 + "%;"); // 字体大小
                int columnWidth = sheet.getColumnWidth(cell.getColumnIndex()) ;
                sb.append("width:" + titleAt[cell.getColumnIndex()] + "%;");
                
                XSSFColor xc = xf.getXSSFColor();
                if (xc != null && !"".equals(xc)) {
                    sb.append("color:#" + xc.getARGBHex().substring(2) + ";"); // 字体颜色
                }
                
                XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
                //System.out.println("************************************");
                //System.out.println("BackgroundColorColor: "+cellStyle.getFillBackgroundColorColor());
                //System.out.println("ForegroundColor: "+cellStyle.getFillForegroundColor());//0
                //System.out.println("BackgroundColorColor: "+cellStyle.getFillBackgroundColorColor());
                //System.out.println("ForegroundColorColor: "+cellStyle.getFillForegroundColorColor());
                //String bgColorStr = bgColor.getARGBHex();
                //System.out.println("bgColorStr: "+bgColorStr);
                if (bgColor != null && !"".equals(bgColor)) {
                    sb.append("background-color:#" + bgColor.getARGBHex().substring(2) + ";"); // 背景颜色
                }
                sb.append(getBorderStyle(0,cellStyle.getBorderTop(), ((XSSFCellStyle) cellStyle).getTopBorderXSSFColor()));
                sb.append(getBorderStyle(1,cellStyle.getBorderRight(), ((XSSFCellStyle) cellStyle).getRightBorderXSSFColor()));
                sb.append(getBorderStyle(2,cellStyle.getBorderBottom(), ((XSSFCellStyle) cellStyle).getBottomBorderXSSFColor()));
                sb.append(getBorderStyle(3,cellStyle.getBorderLeft(), ((XSSFCellStyle) cellStyle).getLeftBorderXSSFColor()));
                    
            }else if(wb instanceof HSSFWorkbook){
                
                HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
                short boldWeight = hf.getBoldweight();
                short fontColor = hf.getColor();
                sb.append("style='");
                HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式
                HSSFColor hc = palette.getColor(fontColor);
                sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
                sb.append("font-size: " + hf.getFontHeight() / 2 + "%;"); // 字体大小
                String fontColorStr = convertToStardColor(hc);
                if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
                    sb.append("color:" + fontColorStr + ";"); // 字体颜色
                }
                int columnWidth = sheet.getColumnWidth(cell.getColumnIndex()) ;
                sb.append("width:" + titleAt[cell.getColumnIndex()] + "%;");
                //sb.append("width:" + columnWidth + "px;");
                short bgColor = cellStyle.getFillForegroundColor();
                hc = palette.getColor(bgColor);
                String bgColorStr = convertToStardColor(hc);
                if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
                    sb.append("background-color:" + bgColorStr + ";"); // 背景颜色
                }
                sb.append( getBorderStyle(palette,0,cellStyle.getBorderTop(),cellStyle.getTopBorderColor()));
                sb.append( getBorderStyle(palette,1,cellStyle.getBorderRight(),cellStyle.getRightBorderColor()));
                sb.append( getBorderStyle(palette,3,cellStyle.getBorderLeft(),cellStyle.getLeftBorderColor()));
                sb.append( getBorderStyle(palette,2,cellStyle.getBorderBottom(),cellStyle.getBottomBorderColor()));
            }

            sb.append("' ");
        }
    }
    
    /**
     * 单元格内容的水平对齐方式
     * @param alignment
     * @return
     */
    private String convertAlignToHtml(short alignment) {

        String align = "left";
        switch (alignment) {
        case CellStyle.ALIGN_LEFT:
            align = "left";
            break;
        case CellStyle.ALIGN_CENTER:
            align = "center";
            break;
        case CellStyle.ALIGN_RIGHT:
            align = "right";
            break;
        default:
            break;
        }
        return align;
    }

    /**
     * 单元格中内容的垂直排列方式
     * @param verticalAlignment
     * @return
     */
    private String convertVerticalAlignToHtml(short verticalAlignment) {

        String valign = "middle";
        switch (verticalAlignment) {
        case CellStyle.VERTICAL_BOTTOM:
            valign = "bottom";
            break;
        case CellStyle.VERTICAL_CENTER:
            valign = "center";
            break;
        case CellStyle.VERTICAL_TOP:
            valign = "top";
            break;
        default:
            break;
        }
        return valign;
    }
    
    private String convertToStardColor(HSSFColor hc) {

        StringBuffer sb = new StringBuffer("");
        if (hc != null) {
            if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {
                return null;
            }
            sb.append("#");
            for (int i = 0; i < hc.getTriplet().length; i++) {
                sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
            }
        }

        return sb.toString();
    }
    
    private String fillWithZero(String str) {
        if (str != null && str.length() < 2) {
            return "0" + str;
        }
        return str;
    }
    
    static String[] bordesr={"border-top:","border-right:","border-bottom:","border-left:"};
    static String[] borderStyles={"solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid","solid","solid","solid","solid"};

    private String getBorderStyle(  HSSFPalette palette ,int b,short s, short t){
         
        if(s==0)return  bordesr[b]+borderStyles[s]+"#d0d7e5 1px;";;
        String borderColorStr = convertToStardColor( palette.getColor(t));
        borderColorStr=borderColorStr==null|| borderColorStr.length()<1?"#000000":borderColorStr;
        return bordesr[b]+borderStyles[s]+borderColorStr+" 1px;";
        
    }
    
    private String getBorderStyle(int b,short s, XSSFColor xc){
         
         if(s==0)return  bordesr[b]+borderStyles[s]+"#d0d7e5 1px;";;
         if (xc != null && !"".equals(xc)) {
             String borderColorStr = xc.getARGBHex();//t.getARGBHex();
             borderColorStr=borderColorStr==null|| borderColorStr.length()<1?"#000000":borderColorStr.substring(2);
             return bordesr[b]+borderStyles[s]+borderColorStr+" 1px;";
         }
         
         return "";
    }
    
    public static String getBASE64(String s) { 
    	if (s == null) return null; 
    	return (new BASE64Encoder()).encode( s.getBytes() ); 
	} 

    private static int columnToIndex(String column) {
        if (!column.matches("[A-Z]+")) {
                try {
                        throw new Exception("Invalid parameter");
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
        int index = 0;
        char[] chars = column.toUpperCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
                index += ((int) chars[i] - (int) 'A' + 1)
                                * (int) Math.pow(26, chars.length - i - 1);
        }
        return index;
    }
    
    private static String indexToColumn(int index) throws Exception {
        if (index <= 0) {                 
        		throw new Exception("Invalid parameter");         
        	}         
        index--;         
        String column = "";         
        do {                 
        	if (column.length() > 0) {
                        index--;
                }
                column = ((char) (index % 26 + (int) 'A')) + column;
                index = (int) ((index - index % 26) / 26);
        } while (index > 0);
        return column;
    }
}