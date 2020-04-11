

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

	private static void printResultSet(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) System.out.print(" | ");
				System.out.print(rsmd.getColumnName(i));
			}
			System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - -");
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(" | ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue);
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 
	
	public static void addPublication(String title, String date) {
		try {
			statement.executeUpdate("INSERT INTO Publications VALUES(NULL,'" + title + "','" + date + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void viewPublication(int person_id) {
		try {
			result = statement.executeQuery("SELECT * FROM Publications WHERE pub_id IN (SELECT pub_id FROM Editor_edit_Publications WHERE person_id=" + person_id + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void viewPeriodicals() {
		try {
			result = statement.executeQuery("SELECT * FROM Periodicals");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void viewBooks() {
		try {
			result = statement.executeQuery("SELECT * FROM Books NATURAL JOIN Publications");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void viewIssues() {
		try {
			result = statement.executeQuery("SELECT * FROM Issues NATURAL JOIN Publications NATURAL JOIN Periodicals");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void viewArticlesChapters() {
		try {
			result = statement.executeQuery("SELECT art_id, title, name AS author_name, topic, date, text FROM Articles_Chapters NATURAL JOIN Author_write_Articles_or_Chapters NATURAL JOIN Persons");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addPeriodical(String type, String periodicity, String topic) {
		try {
			statement.executeUpdate("INSERT INTO Periodicals VALUES(NULL,'" + type + "', '" + periodicity + "', '" + topic + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addArticleToPublication(String date, String text, String title, String topic, int pub_id) {
		try {
			statement.executeUpdate("INSERT INTO Articles_Chapters VALUES(NULL,'" + date + "', '" + text + "', '" + title + "', '" + topic + "'," + pub_id + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteArticleToPublication(int art_id) {
		try {
			statement.executeUpdate("DELETE FROM Articles_Chapters WHERE art_id=" + art_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addBook(String title, String date, String edition, String ISBN, String topic) {
		try {
			statement.executeUpdate("INSERT INTO Publications VALUES(NULL,'" + title + "','" + date + "')");
			statement.executeUpdate("INSERT INTO Books VALUES(LAST_INSERT_ID()," + edition + ",'" + ISBN + "', '" + topic + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addIssue(String title, String date, String period_id) {
		try {
			statement.executeUpdate("INSERT INTO Publications VALUES(NULL, '" + title + "', '" + date + "')");
			statement.executeUpdate("INSERT INTO Issues VALUES(LAST_INSERT_ID(), " + period_id + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateBook(String pub_id, String edition, String ISBN, String topic, String title, String date) {
		try {
			if (!edition.equals("")) {
				statement.executeUpdate("UPDATE Books SET edition=" + edition + " WHERE pub_id=" + pub_id);
			}
			if (!ISBN.equals("")) {
				statement.executeUpdate("UPDATE Books SET ISBN='" + ISBN + "' WHERE pub_id=" + pub_id);
			}
			if (!topic.equals("")) {
				statement.executeUpdate("UPDATE Books SET topic='" + topic + "' WHERE pub_id=" + pub_id);
			}
			if (!title.equals("")) {
				statement.executeUpdate("UPDATE Publications SET title='" + topic + "' WHERE pub_id=" + pub_id);
			}
			if (!date.equals("")) {
				statement.executeUpdate("UPDATE Publications SET date='" + date + "' WHERE pub_id=" + pub_id);
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateIssue(String pub_id, String title, String date, String type, String periodicity, String topic) {
		try {
			if (!title.equals("")) {
				statement.executeUpdate("UPDATE Publications SET title='" + title + "' WHERE pub_id=" + pub_id);
			}
			if (!date.equals("")) {
				statement.executeUpdate("UPDATE Publications SET date='" + date + "' WHERE pub_id=" + pub_id);
			}
			if (!type.equals("")) {
				statement.executeUpdate("UPDATE Periodicals SET type='" + type + "' WHERE period_id=(SELECT period_id FROM Issues WHERE pub_id=" + pub_id + ")");
			}
			if (!periodicity.equals("")) {
				statement.executeUpdate("UPDATE Periodicals SET periodicity='" + periodicity + "' WHERE period_id=(SELECT period_id FROM Issues WHERE pub_id=" + pub_id + ")");
			}
			if (!topic.equals("")) {
				statement.executeUpdate("UPDATE Periodicals SET topic='" + topic + "' WHERE period_id=(SELECT period_id FROM Issues WHERE pub_id=" + pub_id + ")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deletePublication(String pub_id) {
		try {
			statement.executeUpdate("DELETE FROM Publications WHERE pub_id=" + pub_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updatePublication(int pub_id, String title, String date) {
		try {
			if (title != null) {
				statement.executeUpdate("UPDATE Publications SET title='" + title + "' WHERE pub_id=" + pub_id);
			}
			if (date != null) {
				statement.executeUpdate("UPDATE Publications SET date='" + date + "' WHERE pub_id=" + pub_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateArticleOrChapter(int art_id, String title, String text, String topic, String date) {
		try {
			if (title != null) {
				statement.executeUpdate("UPDATE Articles_Chapters SET title='" + title + "' WHERE art_id=" + art_id);
			}
			if (text != null) {
				statement.executeUpdate("UPDATE Articles_Chapters SET text='" + text + "' WHERE art_id=" + art_id);
			}
			if (topic != null) {
				statement.executeUpdate("UPDATE Articles_Chapters SET topic='" + topic + "' WHERE art_id=" + art_id);
			}
			if (date != null) {
				statement.executeUpdate("UPDATE Articles_Chapters SET date='" + date + "' WHERE art_id=" + art_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updatePerson(int person_id, String name, String type, String gender, Integer age, String email, String phone_num, String address) {
		try {
			if (name != null) {
				statement.executeUpdate("UPDATE Persons SET name='" + name + "' WHERE person_id=" + person_id);
			}
			if (type != null) {
				statement.executeUpdate("UPDATE Persons SET type='" + type + "' WHERE person_id=" + person_id);
			}
			if (gender != null) {
				statement.executeUpdate("UPDATE Persons SET gender='" + gender + "' WHERE person_id=" + person_id);
			}
			if (age != null) {
				statement.executeUpdate("UPDATE Persons SET age='" + age + "' WHERE person_id=" + person_id);
			}
			if (email != null) {
				statement.executeUpdate("UPDATE Persons SET email='" + email + "' WHERE person_id=" + person_id);
			}
			if (phone_num != null) {
				statement.executeUpdate("UPDATE Persons SET phone_num='" + phone_num + "' WHERE person_id=" + person_id);
			}
			if (address != null) {
				statement.executeUpdate("UPDATE Persons SET address='" + address + "' WHERE person_id=" + person_id);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void searchBooks(String topic, String date, String authorName) {
		List<String> search = new ArrayList<String>();

		if (!topic.equals("")) {
			search.add("topic LIKE '%" + topic + "%'");
		}
		if (!date.equals("")) {
			search.add("date='" + date + "'");
		}
		if (!authorName.equals("")) {
			search.add("name LIKE '%" + authorName + "%'");
		}
		try {
			String query = "SELECT title, edition, ISBN, date, topic, name AS author_name FROM Books NATURAL JOIN " +
					"Publications NATURAL JOIN Author_write_Books NATURAL JOIN Persons";
			if (search.size() > 0)
				query += " WHERE " + String.join(" AND ", search);
			result = statement.executeQuery(query);
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void searchArticles(String topic, String date, String authorName){
		List<String> search = new ArrayList<String>();

		if (!topic.equals("")) {
			search.add("topic LIKE '%" + topic + "%'");
		}
		if (!date.equals("")) {
			search.add("date='" + date + "'");
		}
		if (!authorName.equals("")) {
			search.add("name LIKE '%" + authorName + "%'");
		}
		try {
			String query = "SELECT title, date, topic, text, name AS author_name FROM Articles_Chapters NATURAL JOIN " +
					"Author_write_Articles_or_Chapters NATURAL JOIN Persons";
			if (search.size() > 0)
				query += " WHERE " + String.join(" AND ", search);
			result = statement.executeQuery(query);
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addPayment(int pay_id, String date, String type, float amount, String person_id) {
		try {
			statement.executeUpdate("INSERT INTO Payments VALUES(NULL, '" + date + "', '" + type + "', " + amount + ", " + person_id +")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
