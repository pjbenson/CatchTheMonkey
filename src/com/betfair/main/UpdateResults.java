package com.betfair.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.betfair.entities.Result;

public class UpdateResults {
	
	private final String RAGLANROAD_PATH = "C:\\Users\\paulb\\Documents\\College\\CatchTheMonkey\\RaglanRoadResults.xlsx";
	private final String GINGERMC_PATH = "C:\\Users\\paulb\\Documents\\College\\CatchTheMonkey\\GingerMcResults.xlsx";
	private final String LUCAYAN_PATH = "C:\\Users\\paulb\\Documents\\College\\CatchTheMonkey\\LucayanResults.xlsx";
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("CatchTheMonkey");
	private EntityManager em = emf.createEntityManager();
	private Double raglanReturn=0.;
	private Double gingerMcReturn=0.;
	private Double lucayanReturn=0.;
	
	public UpdateResults() throws IOException, ParseException{
		readRaglanRoadResultsFromExcel(RAGLANROAD_PATH, 1);
		readRaglanRoadResultsFromExcel(GINGERMC_PATH, 2);
		readRaglanRoadResultsFromExcel(LUCAYAN_PATH, 3);
		em.close();
	}
	
	public void readRaglanRoadResultsFromExcel(String filePath, Integer strategyID) throws IOException, ParseException{
		List<Result> results = new ArrayList<Result>();
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(filePath);
			Workbook workbook = new XSSFWorkbook(fis);
            int numberOfSheets = workbook.getNumberOfSheets();
            
            for (int i = 0; i < numberOfSheets; i++) {
            	Sheet sheet = workbook.getSheetAt(i);
                Iterator rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                	Result result = new Result();
                	Row row = (Row) rowIterator.next();
                    Iterator cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                    	Cell cell = (Cell) cellIterator.next();
                    	if (cell.getColumnIndex() == 0) {
                    		result.setDate(cell.getDateCellValue());
                    		result.setStrategyId(strategyID);
                    	}
                    	if(cell.getColumnIndex() == 1){
                    		result.setHorseName(cell.getStringCellValue());
                    	}
                    	if(cell.getColumnIndex() == 2){
                    		result.setAmount(cell.getNumericCellValue());
                    		if(strategyID==1)raglanReturn = raglanReturn+cell.getNumericCellValue();
                    		if(strategyID==2)gingerMcReturn += cell.getNumericCellValue();
                    		if(strategyID==2)lucayanReturn += cell.getNumericCellValue();
                    	}
                    }
                    if(result.getDate() != null){
                    	 results.add(result);
                         persistResult(result);
                    }
                   
                }
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void persistResult(Result result){
		em.getTransaction().begin();
		em.persist(result);
		em.flush();
        em.clear();
		em.getTransaction().commit();
	}

	public Double getRaglanReturn() {
		return raglanReturn;
	}

	public void setRaglanReturn(Double raglanReturn) {
		this.raglanReturn = raglanReturn;
	}

	public Double getGingerMcReturn() {
		return gingerMcReturn;
	}

	public void setGingerMcReturn(Double gingerMcReturn) {
		this.gingerMcReturn = gingerMcReturn;
	}

	public Double getLucayanReturn() {
		return lucayanReturn;
	}

	public void setLucayanReturn(Double lucayanReturn) {
		this.lucayanReturn = lucayanReturn;
	}

}
