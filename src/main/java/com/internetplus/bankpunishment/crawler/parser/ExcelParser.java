package com.internetplus.bankpunishment.crawler.parser;

import com.internetplus.bankpunishment.crawler.pojo.DataEntity;
import com.internetplus.bankpunishment.crawler.util.pojo.DataFieldHelper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/21
 * 解析 excel 文件 （xls, xlsx）
 * 支持合并单元格的解析
 * 支持非固定顺序的表头
 */
public class ExcelParser {

    /**
     * 给定 Excel 文件的路径，对其进行解析
     * 一直解析单元格，直到遇到一个属于数据字段的单元格，说明这一行是表头
     * 然后记录各个字段出现的顺序，用 ArrayList
     * 接下来的每一行都是数据，获取每一行的数据
     * 按照刚刚 ArrayList 记录的数据字段出现的顺序，对 DataEntity 对象进行封装
     *
     * @return 返回解析出来的 List<DataEntity>
     */
    public static List<DataEntity> parseExcel2DataEntity(String excelPath) {
        File excel = new File(excelPath);
        String postfix = excel.getName().split("\\.")[1];
        Workbook wb = getWorkbookByFilePostfix(postfix, excel);
        if (wb == null) return new ArrayList<>();

        //开始解析
        Sheet sheet = wb.getSheetAt(0);
        int firstRowIndex = sheet.getFirstRowNum();
        int lastRowIndex = sheet.getLastRowNum();

        List<String> fieldNameList = new ArrayList<>(12);// Excel 文件中，各个字段出现的顺序
        List<DataEntity> dataEntityList = new ArrayList<>();
        boolean findHeader = false; // 是否已经发现表头
        int dataColOffset = 0; // 数据项的列数偏移量（有些 excel 文件，前几列是空的）
        boolean isOffsetTested = false; // 是否已经测量过数据偏移量 （不能直接根据 dataColOffset 等不等于0判断，因为有空数据）
        // 遍历行
        for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
            Row row = sheet.getRow(rIndex);
            if (row == null) continue;
            int lastCellIndex = row.getLastCellNum();
            DataEntity dataEntity = new DataEntity(); // 待添加的对象
            if (!findHeader) {
                // 首先找表头, 遍历列
                for (int cIndex = 0; cIndex < lastCellIndex; ++cIndex) {
                    // 先把前面为空的过滤掉
                    if (row.getCell(cIndex) == null) continue;
                    // 还没有发现表头，先找表头那一行，并记录表头字段的顺序
                    if (DataFieldHelper.typeJudge(row.getCell(cIndex).toString()) != null) {
                        // 说明这一行是表头行
                        findHeader = true;
                        // 记录每个字段的出现顺序，读完这一行
                        for (int i = cIndex; i < lastCellIndex; ++i) {
                            String name = row.getCell(i).toString();
                            if (name != null && !name.isEmpty()) {
                                fieldNameList.add(name);
                            }
                        }
                    }
                    break; // 只要看第一个有值的字段属不属于表头就行，如果不属于，后面的也不属于
                }
            } else {
                // 接下来每一行都是表格数据
                // 因为 lastIndex 有的获取的并不符合实际情况，因此我们需要以 fieldNameList 的长度为准
                for (int cIndex = 0; cIndex < fieldNameList.size(); ++cIndex) {
                    // 已经发现过了表头，剩下来的每一行都是数据项
                    // 首先需要计算一下数据项的列数偏移量
                    if (!isOffsetTested) {
                        isOffsetTested = true;
                        // 计算偏移
                        for (int i = 0; i < lastCellIndex; ++i) {
                            if (row.getCell(i) == null) ++dataColOffset;
                            else break;
                        }
                    }
                    Cell cell = row.getCell(cIndex);
                    if (cell == null) continue;
                    String value = getCellValue(sheet,cell);
                    // 对日期需要特殊处理一下
                    if (DataFieldHelper.isPunishDate(fieldNameList.get(cIndex - dataColOffset))) {
                        value = buildDate(cell);
                    }
                    DataFieldHelper.setRegardingValue(fieldNameList.get(cIndex - dataColOffset), value, dataEntity);
                    if (cIndex == fieldNameList.size() - 1) {
                        // 到最后一个字段了，还要把 dataEntity 添加到列表中
                        if (!DataFieldHelper.isAllFieldNull(dataEntity)) {
                            dataEntityList.add(dataEntity);
                        }
                    }
                }
            }
        }

        return dataEntityList;
    }

    /**
     * 通过文件后缀名来获取相对应的 Excel Workbook
     * 根据文件后缀（xls/xlsx）进行判断，两者获得的解析器不一样
     */
    private static Workbook getWorkbookByFilePostfix(String postfix, File excelFile) {
        try {
            if ("xls".equals(postfix)) {
                return new HSSFWorkbook(new FileInputStream(excelFile));
            } else if ("xlsx".equals(postfix)) {
                return new XSSFWorkbook(excelFile);
            }
        } catch (Exception ignored) {}
        return null;
    }


    /**
     * 如果单元格的值是日期，需要特殊处理一下，否则读出来可能是数字
     * 返回 yyyy-MM-dd 格式的日期
     */
    private static String buildDate(Cell cell) {
        if (cell.toString().contains("E")) {
            // 有些文件的日期是这样写：20191231 （纯数字），Excel 读取过来包含 E
            String value = cell.toString().replaceAll("\\.|E", "");
            return value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
        }
        if (cell.getCellType() == CellType.STRING.getCode()) {
            return cell.toString().replaceAll("\\.|,|年|月", "-").replaceAll("日", "");
        }
        if (cell.getCellType() == CellType.NUMERIC.getCode() && HSSFDateUtil.isCellDateFormatted(cell)) {
            // xls 文件对日期的处理
            Date date = cell.getDateCellValue();
            return DateFormatUtils.format(date, "yyyy-MM-dd");
        } else {
            // xlsx 文件对日期的处理
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue());
            return sdf.format(date);
        }
    }

    /**
     * 获取 cell 字段的值 （防止它是合并单元格取出来空的）
     */
    private static String getCellValue(Sheet sheet, Cell cell) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress mergedRegion : mergedRegions) {
            if (cell.getRowIndex() >= mergedRegion.getFirstRow() &&
                    cell.getRowIndex() <= mergedRegion.getLastRow() &&
                    cell.getColumnIndex() >= mergedRegion.getFirstColumn() &&
                    cell.getColumnIndex() <= mergedRegion.getLastColumn()) {
                return sheet.getRow(mergedRegion.getFirstRow()).getCell(mergedRegion.getFirstColumn()).toString();
            }
        }
        return cell.toString();
    }

}
