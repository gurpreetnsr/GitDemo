package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		if(StepDefination.place_id==null)
		{
			StepDefination st = new StepDefination();
			st.add_Place_Payload("Japtej Singh", "Hindi", "#2550, Sunny Enclave");
			st.user_calls_with_Http_request("addPlaceAPI", "POST");
			st.verify_place_id_created_maps_to_using("Japtej Singh", "getPlaceAPI");
		}
		
		
	}
}
