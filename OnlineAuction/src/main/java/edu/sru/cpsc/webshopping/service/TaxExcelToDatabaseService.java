package edu.sru.cpsc.webshopping.service;

import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.billing.StateDetails;
import edu.sru.cpsc.webshopping.repository.billing.StateDetailsRepository;

@Service
public class TaxExcelToDatabaseService {

    @Autowired
    private StateDetailsRepository taxDetailRepository;

    public void loadFromExcelFile(Resource excelFile) throws Exception {
        try (InputStream inputStream = excelFile.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                StateDetails detail = new StateDetails();
                // Assuming first column is some tax name, second is some tax value etc.
                detail.setStateName(row.getCell(0).getStringCellValue());
                BigDecimal taxRate = new BigDecimal(row.getCell(1).getNumericCellValue());
                detail.setSalesTaxRate(taxRate);
                // Save to database
                taxDetailRepository.save(detail);
            }
        }
    }
}
