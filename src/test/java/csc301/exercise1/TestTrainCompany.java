package csc301.exercise1;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestTrainCompany {

	/*
	 * Test Train Company Names
	 */

	// An example of how to verify that an exception is thrown
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateCompanyWithNullName() {
		new TrainCompany(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateCompanyWithEmptyName() {
		new TrainCompany("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateCompanyWithBlankName() {
		new TrainCompany(" ");
	}

	@Test
	public void shouldCreateCompanyWithNonWhitespaceNames() {
		new TrainCompany("A");
		new TrainCompany("B ");
		new TrainCompany(" C");
		new TrainCompany(" D ");
		new TrainCompany(" A  B  C ");
	}

	// There should never be two TrainCompany instances with the same name.
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateCompanyWithSameName() {
		new TrainCompany("E");
		new TrainCompany("E");
	}

	@Test
	public void emptyDirectRouteCollectionShouldNotReturnNull() {
		TrainCompany tc = new TrainCompany("tc");
		assertNotNull(tc.getAllDirectRoutes());
		// should also test that it returns an empty collection
	}
}
