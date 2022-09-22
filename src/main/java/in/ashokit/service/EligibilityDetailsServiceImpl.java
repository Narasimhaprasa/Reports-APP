package in.ashokit.service;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.ashokit.entity.EligibilityDetails;
import in.ashokit.repo.EligibilitiRepo;
import in.ashokit.reponse.SearchResponse;
import in.ashokit.reqest.SearchRequest;

@Service
public class EligibilityDetailsServiceImpl implements IEligibilityDetailsService {
	@Autowired
	private EligibilitiRepo repo;

	@Override
	public List<String> getUniquePlanNames() {
		List<String> planNames = repo.getPlanNames();
		return planNames;
	}

	@Override
	public List<String> getUniquePlanStatuses() {
		List<String> planStatuses = repo.getPlanStatuses();
		return planStatuses;
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		List<SearchResponse> responses = new ArrayList<SearchResponse>();
		EligibilityDetails queryBuilder = new EligibilityDetails();

		String planName = request.getPlanName();
		if (planName != null && !planName.equals("")) {
			queryBuilder.setPlanName(planName);
		}

		String planStatus = request.getPlanStatus();
		if (planStatus != null && !planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}

		LocalDate planStartDate = request.getPlanStartDate();
		if (planStartDate != null) {
			queryBuilder.setPlanStartDate(planStartDate);
		}

		LocalDate planEndDate = request.getPlanEndDate();
		if (planEndDate != null) {
			queryBuilder.setPlanEndDate(planEndDate);
		}

		Example<EligibilityDetails> example = Example.of(queryBuilder);
		List<EligibilityDetails> entities = repo.findAll(example);
		for (EligibilityDetails entity : entities) {
			SearchResponse response = new SearchResponse();
			BeanUtils.copyProperties(entity, response);
			responses.add(response);
		}
		return responses;
	}

	@Override
	public void generatePdf(HttpServletResponse response) throws Exception {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("Search Users", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 2.0f, 6.0f, 3.0f, 2.0f, 3.0f });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE);
		fontHeader.setColor(Color.YELLOW);

		cell.setPhrase(new Phrase("ID", fontHeader));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Name", fontHeader));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Email", fontHeader));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Mobile", fontHeader));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Gender", fontHeader));
		table.addCell(cell);
		cell.setPhrase(new Phrase("SSN", fontHeader));
		table.addCell(cell);

		List<EligibilityDetails> entities = repo.findAll();
		for (EligibilityDetails entity : entities) {
			table.addCell(String.valueOf(entity.getElig_Id()));
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getMobile()));
			table.addCell(entity.getGender());
			table.addCell(String.valueOf(entity.getSsn()));
		}

		document.add(table);
		document.close();
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {
		List<EligibilityDetails> entities = repo.findAll();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet createSheet = workbook.createSheet();
		HSSFRow headerRow = createSheet.createRow(0);

		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Name");
		headerRow.createCell(2).setCellValue("Email");
		headerRow.createCell(3).setCellValue("Phone");
		headerRow.createCell(4).setCellValue("Gender");
		headerRow.createCell(5).setCellValue("SSN");

		int i = 1;
		for (EligibilityDetails entity : entities) {
			HSSFRow dataRow = createSheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getElig_Id());
			dataRow.createCell(1).setCellValue(entity.getName());
			dataRow.createCell(2).setCellValue(entity.getEmail());
			dataRow.createCell(3).setCellValue(entity.getMobile());
			dataRow.createCell(4).setCellValue(entity.getGender());
			dataRow.createCell(5).setCellValue(entity.getSsn());
			i++;
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

}
