package in.ashokit.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.ashokit.entity.EligibilityDetails;
import in.ashokit.repo.EligibilitiRepo;
@Component
public class AppRunner implements CommandLineRunner {
	@Autowired
	private EligibilitiRepo runnerRepo;

	@Override
	public void run(String... args) throws Exception {
		 EligibilityDetails entity1= new EligibilityDetails();
		 entity1.setElig_Id(1);
		 entity1.setName("Prasad");
		 entity1.setEmail("narasimhaprasad@gmail.com");
		 entity1.setMobile(4556456644l);
		 entity1.setGender("M");
		 entity1.setSsn(345342334l);
		 entity1.setPlanName("Medicaid");
		 entity1.setPlanStatus("denied");
		 runnerRepo.save(entity1);
		 
		 EligibilityDetails entity2= new EligibilityDetails();
		 entity2.setElig_Id(2);
		 entity2.setName("akash");
		 entity2.setEmail("akash@gmail.com");
		 entity2.setMobile(4556456644l);
		 entity2.setGender("M");
		 entity2.setSsn(345342334l);
		 entity2.setPlanName("Food");
		 entity2.setPlanStatus("working");
		 runnerRepo.save(entity2);

		 
		 EligibilityDetails entity3= new EligibilityDetails();
		 entity3.setElig_Id(3);
		 entity3.setName("Ramu");
		 entity3.setEmail("ramu@gmail.com");
		 entity3.setMobile(4556456644l);
		 entity3.setGender("M");
		 entity3.setSsn(345342334l);
		 entity3.setPlanName("Gold");
		 entity3.setPlanStatus("progress");
		 runnerRepo.save(entity3);
		 
		 EligibilityDetails entity4= new EligibilityDetails();
		 entity4.setElig_Id(4);
		 entity4.setName("Raju");
		 entity4.setEmail("raju@gmail.com");
		 entity4.setMobile(9856741230l);
		 entity4.setGender("M");
		 entity4.setSsn(345342334l);
		 entity4.setPlanName("SCAP");
		 entity4.setPlanStatus("denied");
		 entity4.setCreatedBy("Raju");
		 runnerRepo.save(entity4);
	}

}
