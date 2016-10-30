package org.outrospective.dreamstime.reconciler

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import static org.apache.poi.ss.usermodel.Cell.*

import java.nio.file.Paths
import groovy.json.JsonOutput

/* Adapted from http://www.groovy-tutorial.org/basic-xlsx/ */
class XLSXReader {
    private final def path

    public XLSXReader(path) {
        this.path = path
    }

    def header = []
    def values = []

    def readFile() {
        Paths.get(path).
                withInputStream {
                    input ->

                        def workbook = new XSSFWorkbook(input)
                        def sheet = workbook.getSheetAt(0)
                        sheet.shiftRows(2, sheet.lastRowNum, -2);
                        for (cell in sheet.getRow(0).cellIterator()) {
                            header << cell.stringCellValue
                        }

                        def headerFlag = true
                        for (row in sheet.rowIterator()) {
                            if (headerFlag) {
                                headerFlag = false
                                continue
                            }
                            def rowData = [:]
                            for (cell in row.cellIterator()) {
                                def value = ''
                                switch (cell.cellType) {
                                    case CELL_TYPE_STRING:
                                        value = cell.stringCellValue
                                        break
                                    case CELL_TYPE_NUMERIC:
                                        value = cell.numericCellValue
                                        break
                                    default:
                                        value = ''
                                }
                                rowData << [("${header[cell.columnIndex]}".toString()): value]
                            }
                            values << rowData
                        }
                }
        }

    def toJson() {
        Paths.get(jsonPathname()).withWriter {
            jsonWriter ->
            jsonWriter.write JsonOutput.prettyPrint(JsonOutput.toJson(values))
        }
    }

    private String jsonPathname() {
        "$path.json"
    }


}