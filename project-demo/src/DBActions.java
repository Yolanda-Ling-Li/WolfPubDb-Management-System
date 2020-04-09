

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

		String user = "zli58";
		String password = "ncsu123";

		connection = DriverManager.getConnection(jdbcURL, user, password);
		statement = connection.createStatement();
	}
	 

	private static boolean checkAbilityToStudy(String studentName) {
		try {
			result = statement
					.executeQuery("SELECT (FundingReceived+Income) AS TotalIncome, (TuitonFees+LivingExpenses) AS "
							+ "TotalFees FROM Students, Schools WHERE Students.School = Schools.Name AND Students.Name "
							+ "LIKE '" + studentName + "%'");

			if (result.next()) {
				return (result.getInt("TotalIncome") > result.getInt("TotalFees"));
			}
			throw new RuntimeException(studentName + " cannot be found.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
