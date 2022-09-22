package in.ashokit.rest;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import in.ashokit.reponse.SearchResponse;
import in.ashokit.reqest.SearchRequest;
import in.ashokit.service.IEligibilityDetailsService;

@RestController
public class ReportsRestController {
	@Autowired
	private IEligibilityDetailsService service;

	@GetMapping("/planNames")
	public ResponseEntity<List<String>> getUniquePlanName() {
		List<String> uniquePlanNames = service.getUniquePlanNames();
		return new ResponseEntity<List<String>>(uniquePlanNames, HttpStatus.OK);
	}

	@GetMapping("/planStatus")
	public ResponseEntity<List<String>> getUniquePlanStatuses() {
		List<String> uniquePlanStatuses = service.getUniquePlanStatuses();
		return new ResponseEntity<List<String>>(uniquePlanStatuses, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request) {
		List<SearchResponse> search = service.search(request);
		return new ResponseEntity<List<SearchResponse>>(search, HttpStatus.OK);
	}

	@GetMapping("/excel")
	public void generateExcel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=data.xls";
		response.setHeader(headerKey, headerValue);
		service.generateExcel(response);

	}

	@GetMapping("/pdf")
	public void generatePdf(HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=data.pdf";
		response.setHeader(headerKey, headerValue);
		service.generatePdf(response);

	}
}
