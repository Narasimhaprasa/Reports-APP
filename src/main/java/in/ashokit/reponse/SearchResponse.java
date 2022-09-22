package in.ashokit.reponse;

import lombok.Data;

@Data
public class SearchResponse {
	private Integer elig_Id;
	private String name;
	private String planName;
	private String email;
	private Long mobile;
	private String gender;
	private Long ssn;
}
