
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

	private static void printResultSet(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) System.out.print(" | ");
				System.out.print(rsmd.getColumnName(i));
			}
			System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
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
	
	public static void viewPublicationByEditor(int person_id) {
		try {
			result = statement.executeQuery("SELECT * FROM Publications WHERE pub_id IN (SELECT pub_id FROM Editor_edit_Publications WHERE person_id=" + person_id + ")");
			while (result.next()) {
				System.out.println(String.format("%d %s %s", result.getInt(1), result.getString(2), result.getDate(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void assignEditorToPublication(int person_id, int pub_id) {
		try {
			statement.executeUpdate(String.format("INSERT INTO Editor_edit_Publications VALUES (%d, %d)", person_id, pub_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void viewPublications() {
		try {
			result = statement.executeQuery("SELECT * FROM Publications");
			printResultSet(result);
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

	public static void viewEditorsAuthors() {
		try {
			result = statement.executeQuery("(SELECT Persons.person_id AS person_id, name, gender, age, email, " +
					"CONCAT(Editors.type, ' ', Persons.type) AS type FROM Persons JOIN Editors ON Persons.person_id = Editors.person_id)\n" +
					"UNION (SELECT Persons.person_id AS person_id, name, gender, age, email, " +
					"CONCAT(Authors.type, ' ', Persons.type) AS type FROM Persons JOIN Authors ON Persons.person_id = Authors.person_id);");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void viewUnclaimedPayments(String person_id) {
		try {
			result = statement.executeQuery("SELECT * FROM Payments WHERE person_id=" + person_id + " AND date IS NULL");
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
	
	public static void deleteArticleToPublication(String art_id) {
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
	
	public static void updateArticleChapter(String art_id, String title, String text, String topic, String date) {
		try {
			if (!title.equals("")) {
				statement.executeUpdate("UPDATE Articles_Chapters SET title='" + title + "' WHERE art_id=" + art_id);
			}
			if (!text.equals("")) {
				statement.executeUpdate("UPDATE Articles_Chapters SET text='" + text + "' WHERE art_id=" + art_id);
			}
			if (!topic.equals("")) {
				statement.executeUpdate("UPDATE Articles_Chapters SET topic='" + topic + "' WHERE art_id=" + art_id);
			}
			if (!date.equals("")) {
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
	
	public static void addPayment(String type, String amount, String person_id) {
		try {
			statement.executeUpdate("INSERT INTO Payments VALUES(NULL, NULL, '" + type + "', " + amount + ", " + person_id +")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void claimPayment(String person_id) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate today = LocalDate.now();
		try {
			statement.executeUpdate("UPDATE Payments SET date='" + formatter.format(today) + "' WHERE person_id=" + person_id + " AND date is NULL");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void viewDistributors() {
		try {
			result = statement.executeQuery("SELECT Persons.person_id,name,gender,age,email,phone_num,address,balance,contact_person,Distributors.type,city FROM Persons, Distributors WHERE Persons.person_id = Distributors.person_id;");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void viewOrders() {
		try {
			result = statement.executeQuery("SELECT * FROM Orders;");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void enterNewDistributor(String type, String name, String gender, Integer age, String email, Float balance, String contact_person, String phone_num, String d_type, String city, String address) {
		try {
			connection.setAutoCommit(false);
			statement.executeUpdate(String.format("INSERT INTO Persons VALUES (NULL, '%s', '%s', '%s', %d, '%s', '%s', '%s');", type, name, gender, age, email, phone_num, address));
			statement.executeUpdate(String.format("INSERT INTO Distributors VALUES (LAST_INSERT_ID(), %f, '%s', '%s', '%s');", balance, contact_person, d_type, city));
			connection.commit();
			connection.setAutoCommit(true); 
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback(); 
					connection.setAutoCommit(true);
				} catch (SQLException e1) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void updateDistributor(int person_id, String name, String gender, String age, String email, String balance, String contact_person, String phone_num, String d_type, String city, String address) {
		try {
			connection.setAutoCommit(false);
			if (!name.equals("")) {
				statement.executeUpdate(String.format("UPDATE Persons SET name = '%s' WHERE person_id =%d;", name, person_id));
			}
			if (!gender.equals("")) {
				statement.executeUpdate(String.format("UPDATE Persons SET gender = '%s' WHERE person_id =%d;", gender, person_id));
			}
			if (!age.equals("")) {
				statement.executeUpdate(String.format("UPDATE Persons SET age = %d WHERE person_id =%d;", Integer.parseInt(age), person_id));
			}
			if (!email.equals("")) {
				statement.executeUpdate(String.format("UPDATE Persons SET email = '%s' WHERE person_id =%d;", email, person_id));
			}
			if (!phone_num.equals("")) {
				statement.executeUpdate(String.format("UPDATE Persons SET phone_num = '%s' WHERE person_id =%d;", phone_num, person_id));
			}
			if (!address.equals("")) {
				statement.executeUpdate(String.format("UPDATE Persons SET address = '%s' WHERE person_id =%d;", address, person_id));
			}	
			if (!balance.equals("")) {
				statement.executeUpdate(String.format("UPDATE Distributors SET balance = %f WHERE person_id =%d;", Float.parseFloat(balance), person_id));
			}
			if (!contact_person.equals("")) {
				statement.executeUpdate(String.format("UPDATE Distributors SET contact_person = '%s' WHERE person_id =%d;", contact_person, person_id));
			}
			if (!d_type.equals("")) {
				statement.executeUpdate(String.format("UPDATE Distributors SET type = '%s' WHERE person_id =%d;", d_type, person_id));
			}
			if (!city.equals("")) {
				statement.executeUpdate(String.format("UPDATE Distributors SET city = '%s' WHERE person_id =%d;", city, person_id));
			}
			connection.commit();
			connection.setAutoCommit(true); 
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback(); 
					connection.setAutoCommit(true);
				} catch (SQLException e1) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void deleteDistributor(int person_id){
		try {
			statement.executeUpdate(String.format("DELETE FROM Persons WHERE person_id=%d;", person_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public static void inputOrderByDistributor(int num_of_copy, String order_date, String delivery_date, float price, float shipping_cost, int person_id, int pub_id) {
		try {
			statement.executeUpdate(String.format("INSERT INTO Orders VALUES(NULL, %d, '%s', '%s', %f, %f, %d, %d);", num_of_copy, order_date, delivery_date, price, shipping_cost, person_id, pub_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void billDistributorAnOrder(int order_id){
		try {
			statement.executeUpdate(String.format("UPDATE Distributors SET balance=balance+(SELECT price+shipping_cost FROM Orders WHERE order_id=%d) WHERE person_id=(SELECT person_id FROM Orders WHERE order_id=%d); ", order_id, order_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void generateMonthlyReport(int month) {
		try {
			result = statement
					.executeQuery(String.format("SELECT MONTH(order_date), person_id, pub_id, COUNT(*), SUM(price) FROM Orders WHERE MONTH(order_date) = %d GROUP BY pub_id, person_id;", month));
			
			System.out.println("MONTH(Order Date) | person_id | pub_id | COUNT(*) | SUM(price) ");
			while (result.next()) {
				System.out.println(result.getInt("MONTH(order_date)") + " | " + result.getInt("person_id") + " | " + result.getInt("pub_id") + " | " + result.getInt("COUNT(*)") + " | " + result.getFloat("SUM(price)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void totalRevenueofPublishingHouse(int month) {
		try {
			result = statement
					.executeQuery(String.format("SELECT MONTH(order_date), SUM(price) FROM Orders WHERE MONTH(order_date) = %d;", month));
			
			System.out.println("MONTH(order date) | SUM(price) ");
			while (result.next()) {
				System.out.println(result.getInt("MONTH(order_date)") + " | " + result.getFloat("SUM(price)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void totalExpenses(int month) {
		try {
			result = statement
					.executeQuery(String.format("SELECT MONTH(order_date), SUM(expense) FROM ( SELECT shipping_cost AS expense, order_date FROM Orders UNION ALL SELECT amount AS expense, date FROM Payments )AS Expenses WHERE MONTH(order_date) = %d;", month));
			
			System.out.println("MONTH(order date) | SUM(expense) ");
			while (result.next()) {
				System.out.println(result.getInt("MONTH(order_date)") + " | " + result.getFloat("SUM(expense)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void totalDistributors() {
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
	
	public static void totalRevenuePerCity() {
		try {
			result = statement
					.executeQuery("SELECT city, SUM(price) FROM Orders INNER JOIN Distributors ON Orders.person_id=Distributors.person_id GROUP BY city;");
			
			System.out.println("city | SUM(price)");
			while (result.next()) {
				System.out.println(result.getString("city") + " | " + result.getFloat("SUM(price)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void totalRevenuePerDistributor() {
		try {
			result = statement
					.executeQuery("SELECT Distributors.person_id, SUM(price) FROM Orders INNER JOIN Distributors ON Orders.person_id=Distributors.person_id GROUP BY Distributors.person_id;");
			
			System.out.println(" person_id | SUM(price) ");
			while (result.next()) {
				System.out.println(result.getInt("person_id") + " | " + result.getFloat("SUM(price)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void totalRevenuePerLocation() {
		try {
			result = statement
					.executeQuery("SELECT address, SUM(price) FROM Orders INNER JOIN Persons ON Orders.person_id=Persons.person_id GROUP BY address;");
			
			System.out.println(" address | SUM(price) ");
			while (result.next()) {
				System.out.println(result.getString("address") + " | " + result.getFloat("SUM(price)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void totalPaymentsEditorsPerTimePeriod () {
		try {
			result = statement
					.executeQuery("SELECT MONTH(Payments.date), SUM(amount) FROM Payments INNER JOIN Editors ON Payments.person_id=Editors.person_id GROUP BY MONTH(Payments.date);");
			
			System.out.println(" MONTH(Payments.date) | SUM(amount)");
			while (result.next()) {
				System.out.println(result.getInt("MONTH(Payments.date)") + " | " + result.getFloat("SUM(amount)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void totalPaymentsEditorsPerWorkType() {
		try {
			result = statement
					.executeQuery("SELECT Editors.type, SUM(amount) FROM Payments INNER JOIN Editors ON Payments.person_id=Editors.person_id GROUP BY Editors.type;");
			
			System.out.println(" type | SUM(amount) ");
			while (result.next()) {
				System.out.println(result.getString("Editors.type") + " | " + result.getFloat("SUM(amount)"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void totalPaymentsAuthorsPerTimePeriod() {
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
	
	public static void totalPaymentsAuthorsPerWorkType() {
		try {
			result = statement
					.executeQuery("SELECT Authors.type, SUM(amount) FROM Payments, Authors WHERE Payments.person_id=Authors.person_id GROUP BY Authors.type;");
			
			System.out.println(" type | SUM(amount) ");
			while (result.next()) {
				System.out.println(result.getString("Authors.type") + " | " + result.getFloat("SUM(amount)"));	
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
