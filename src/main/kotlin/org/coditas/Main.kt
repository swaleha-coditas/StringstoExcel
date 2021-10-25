package org.coditas

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.FileReader
import java.util.regex.Pattern
import kotlin.collections.ArrayList

    private var bufferedReader: BufferedReader? = null
    fun main() {
        bufferedReader = BufferedReader(FileReader("Default.strings"))
        val fileOut = FileOutputStream("translations.xlsx")
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Multi-lingual")
        var row: Row? = null
        var cell: Cell?
        var line: String?
        var inputStream: Array<String?>

        var ROW_NUMBER_FOR_COLUMN_1 = 0
        var CELL_NUMBER_FOR_COLUMN_1 = 0
        var ROW_NUMBER_FOR_COLUMN_2 = 0
        var CELL_NUMBER_FOR_COLUMN_2 = 0
        while (bufferedReader!!.readLine().also { line = it } != null) {
            val array: ArrayList<String>? = line?.split("=", ";") as ArrayList<String>?
            println(array)
            val strPattern1 = array?.get(0)
            println(strPattern1)
            val strPattern2 = Pattern.compile("\"([^\"]*)\"")
            val matcher2 = strPattern2.matcher(line)
            inputStream = arrayOf(strPattern1)
            for (i in inputStream.indices) {
                if (CELL_NUMBER_FOR_COLUMN_1 == 0) {
                    row = sheet.createRow(ROW_NUMBER_FOR_COLUMN_1)
                }
                if (i == 0) {
                    cell = row!!.createCell(CELL_NUMBER_FOR_COLUMN_1)
                    cell.setCellValue("${inputStream[i]}")
                    ROW_NUMBER_FOR_COLUMN_1 += 1
                } else {
                    cell = row!!.createCell(1)
                    cell.setCellValue("${inputStream[i]}")
                    ROW_NUMBER_FOR_COLUMN_1 += 1
                    CELL_NUMBER_FOR_COLUMN_1 -= 1
                }
            }

            while (matcher2.find()) {
                if (matcher2.find()) {
                    print("${matcher2.group()}\n")
                    inputStream = arrayOf(matcher2.group())

                    for (i in inputStream.indices) {
                        if (CELL_NUMBER_FOR_COLUMN_2 == 1) {
                            row = sheet.createRow(ROW_NUMBER_FOR_COLUMN_2)
                        }
                        if (i == 0) {
                            cell = row!!.createCell(CELL_NUMBER_FOR_COLUMN_2 + 1)
                            cell.setCellValue("${inputStream[i]}")
                            ROW_NUMBER_FOR_COLUMN_2 += 1
                        } else {
                            cell = row!!.createCell(1)
                            cell.setCellValue("${inputStream[i]}")
                            ROW_NUMBER_FOR_COLUMN_2 += 1
                            CELL_NUMBER_FOR_COLUMN_2 -= 1
                        }
                    }
                }
            }
        }
        println()
        workbook.write(fileOut)
        fileOut.close()
    }
