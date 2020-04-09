

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBActions {
	static final String jdbcURL = "jdbc:mariadb://karlm3.hopto.org:3306/csc540demo";
	// Put your oracle ID and password here

	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet result = null;
	
	static {
		try {
			connectToDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println("Is User 1 admin: " + isUserAdmin(1));
			System.out.println("Is User 2 admin: " + isUserAdmin(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
	}
	
	public static boolean isUserAdmin(int person_id) {
		try {
			result = statement.executeQuery(String.format("SELECT type FROM Persons WHERE person_id = %d", person_id));
			if (result.next()) {
				return result.getString(1).equals("admin");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static void connectToDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");

		String user = "lli46";
		String password = "ncsu123";

		connection = DriverManager.getConnection(jdbcURL, user, password);
		statement = connection.createStatement();
	}
	 

	private static void enterNewDistributor(int person_id, String name, String type, Float balance, String contact_person, String phone_num, String d_type, String city, String address) {
		try {
			statement.executeUpdate(String.format("INSERT INTO Persons VALUES (%d, %s, %s, %s, %s);", person_id, name, type, phone_num, address));
			statement.executeUpdate(String.format("INSERT INTO Distributors VALUES (%d, %f, %s, %s, %s, %s, %s);", person_id, balance, contact_person, d_type, city));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void updateDistributor(int person_id, String name, String type, Float balance, String contact_person, String phone_num, String d_type, String city, String address) throws SQLException {
		if (name != null) {
			statement.executeUpdate(String.format("UPDATE Persons SET name = %s WHERE person_id =%d;", name, person_id));
		}
		if (type != null) {
			statement.executeUpdate(String.format("UPDATE Persons SET type = %s WHERE person_id =%d;", type, person_id));
		}
		if (balance != null) {
			statement.executeUpdate(String.format("UPDATE Distributors SET balance = %f WHERE person_id =%d;", balance, person_id));
		}
		if (contact_person != null) {
			statement.executeUpdate(String.format("UPDATE Distributors SET contact_person = %s WHERE person_id =%d;", contact_person, person_id));
		}
		if (phone_num != null) {
			statement.executeUpdate(String.format("UPDATE Persons SET phone_num = %s WHERE person_id =%d;", phone_num, person_id));
		}
		if (d_type != null) {
			statement.executeUpdate(String.format("UPDATE Distributors SET type = %s WHERE person_id =%d;", d_type, person_id));
		}
		if (city != null) {
			statement.executeUpdate(String.format("UPDATE Distributors SET city = %s WHERE person_id =%d;", city, person_id));
		}
		if (address != null) {
			statement.executeUpdate(String.format("UPDATE Persons SET address = %s WHERE person_id =%d;", address, person_id));
		}	
	}

	private static void deleteDistributor(int person_id) throws SQLException {
		statement.executeUpdate(String.format("DELETE FROM Distributors WHERE person_id=%d;", person_id));
	}

	private static void inputOrderByDistributor(int order_id, int num_of_copy, String date, float price, float shipping_cost, int person_id, int pub_id) {
		try {
			statement.executeUpdate(String.format("INSERT INTO Orders VALUES(%d, %d, %s, %f, %f, %d, %d);", order_id, num_of_copy, date, price, shipping_cost, person_id, pub_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void billDistributorAnOrder(int order_id) throws SQLException {
		statement.executeUpdate(String.format("UPDATE Distributors SET balance=balance+(SELECT price+shipping_cost FROM Orders WHERE order_id=%d) WHERE person_id=(SELECT person_id FROM Orders WHERE order_id=%d); ", order_id, order_id));
	}
	
	private static void generateMonthlyReport() {
		try {
			result = statement
					.executeQuery("SELECT MONTH(date), person_id, pub_id, COUNT(*), SUM(price)" + 
							"FROM Orders WHERE MONTH(date) = MONTH(CURDATE())" + 
							"GROUP BY pub_id, person_id;");
			
			System.out.println("MONTH(date) | person_id | pub_id | COUNT(*) | SUM(price) ");
			while (result.next()) {
				System.out.println(result.getInt("MONTH") + " | " + result.getInt("person_id") + " | " + result.getInt("pub_id") + " | " + result.getInt("COUNT(*)") + " | " + result.getFloat("SUM(price)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void totalRevenueofPublishingHouse() {
		try {
			result = statement
					.executeQuery("SELECT MONTH(date), SUM(price) FROM Orders" + 
							"WHERE MONTH(date) = MONTH(CURDATE());");
			
			System.out.println("MONTH(date) | SUM(price) ");
			while (result.next()) {
				System.out.println(result.getInt("MONTH") + " | " + result.getFloat("SUM(price)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void totalExpenses() {
		try {
			result = statement
					.executeQuery("SELECT MONTH(date), SUM(expense) FROM (" + 
							"SELECT shipping_cost AS expense, date FROM Orders" + 
							"UNION ALL" + 
							"SELECT amount AS expense, date FROM Payments" + 
							") AS Expenses" + 
							"WHERE MONTH(date) = MONTH(CURDATE());");
			
			System.out.println("MONTH(date) | SUM(expense) ");
			while (result.next()) {
				System.out.println(result.getInt("MONTH") + " | " + result.getFloat("SUM(expense)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void totalDistributors() {
		try {
			result = statement
					.executeQuery("SELECT COUNT(person_id) FROM Distributors;");
			
			System.out.println("COUNT(person_id)");
			while (result.next()) {
				System.out.println(result.getInt("COUNT(person_id)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void totalRevenuePerCity() {
		try {
			result = statement
					.executeQuery("SELECT city, SUM(price) FROM Orders, Distributor" + 
							"WHERE Orders.person_id=Distributors.person_id GROUP BY city;");
			
			System.out.println("city | SUM(price)");
			while (result.next()) {
				System.out.println(result.getString("city") + " | " + result.getFloat("SUM(price)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void totalRevenuePerDistributor() {
		try {
			result = statement
					.executeQuery("SELECT Orders.person_id, name, SUM(price)" + 
							"FROM Orders, Distributors" + 
							"WHERE Orders.person_id=Distributors.person_id" + 
							"GROUP BY Orders.person_id;");
			
			System.out.println(" person_id | name | SUM(price) ");
			while (result.next()) {
				System.out.println(result.getInt("person_id") + " | " + result.getString("name") + " | " + result.getFloat("SUM(price)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void totalRevenuePerLocation() {
		try {
			result = statement
					.executeQuery("SELECT address, SUM(price) FROM Orders, Persons" + 
							"WHERE Orders.person_id=Persons.person_id GROUP BY address;");
			
			System.out.println(" address | SUM(price) ");
			while (result.next()) {
				System.out.println(result.getString("address") + " | " + result.getFloat("SUM(price)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void totalPaymentsEditorsPerTimePeriod () {
		try {
			result = statement
					.executeQuery("SELECT MONTH(Payments.date), SUM(amount) FROM Payments, Editors WHERE Payments.person_id=Editors.person_id GROUP BY MONTH(Payments.date);");
			
			System.out.println(" MONTH(Payments.date) | SUM(amount)");
			while (result.next()) {
				System.out.println(result.getInt(" MONTH(Payments.date)") + " | " + result.getFloat(" SUM(amount)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void totalPaymentsEditorsPerWorkType() {
		try {
			result = statement
					.executeQuery("SELECT Editors.type, SUM(amount) FROM Payments, Editors" + 
							"WHERE Payments.person_id=Editors.person_id GROUP BY Editors.type;");
			
			System.out.println(" type | SUM(amount) ");
			while (result.next()) {
				System.out.println(result.getString("type") + " | " + result.getFloat("SUM(amount)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void totalPaymentsAuthorsPerTimePeriod() {
		try {
			result = statement
					.executeQuery("SELECT MONTH(Payments.date), SUM(amount) FROM Payments, Authors WHERE Payments.person_id=Authors.person_id GROUP BY MONTH(Payments.date);");
			
			System.out.println(" MONTH(Payments.date) | SUM(amount) ");
			while (result.next()) {
				System.out.println(result.getString("MONTH(Payments.date)") + " | " + result.getFloat("SUM(amount)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void totalPaymentsAuthorsPerWorkType() {
		try {
			result = statement
					.executeQuery("SELECT Authors.type, SUM(amount) FROM Payments, Authors WHERE Payments.person_id=Authors.person_id GROUP BY Authors.type;");
			
			System.out.println(" type | SUM(amount) ");
			while (result.next()) {
				System.out.println(result.getString("type") + " | " + result.getFloat("SUM(amount)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
