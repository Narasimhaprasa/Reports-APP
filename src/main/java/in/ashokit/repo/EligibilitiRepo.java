package in.ashokit.repo;

 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.ashokit.entity.EligibilityDetails;

public interface EligibilitiRepo extends JpaRepository<EligibilityDetails, Integer> {
	@Query("select distinct(planName) from EligibilityDetails")
	public List<String> getPlanNames();

	@Query("select distinct(planStatus) from EligibilityDetails")
	public List<String> getPlanStatuses();
}
