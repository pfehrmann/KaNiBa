package kaniba.test;

import de.kaniba.model.ConnectionCreater;
import de.kaniba.model.Database;

public class Utils {
	public static void prepareDatabaseForTests() {
		ConnectionCreater connectionCreater = new TestDatabaseConnectionCreater();

		Database.setConnectionCreater(connectionCreater);
	}
}
