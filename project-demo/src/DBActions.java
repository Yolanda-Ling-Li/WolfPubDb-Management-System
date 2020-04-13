
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

	/**
	 * Coonect to our local database.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void connectToDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");

		String user = "lli46";
		String password = "ncsu123";

		connection = DriverManager.getConnection(jdbcURL, user, password);
		statement = connection.createStatement();
	}

	/**
	 * Take a ResultSet as a parameter and print its content.
	 * @param rs a ResultSet
	 */
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
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert a new publication into the Publications table.
	 * @param title the title of the new publication
	 * @param date the date of the new publication
	 */
	public static void addPublication(String title, String date) {
		try {
			statement.executeUpdate("INSERT INTO Publications VALUES(NULL,'" + title + "','" + date + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all the Publications that an Editor is responsible for.
	 * @param person_id the person_id of the Editor
	 */
	public static void viewPublicationByEditor(int person_id) {
		try {
			result = statement.executeQuery("SELECT * FROM Publications WHERE pub_id IN (SELECT pub_id FROM Editor_edit_Publications WHERE person_id=" + person_id + ")");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Assign a Publication to an Editor.
	 * @param person_id the person_id of the Editor
	 * @param pub_id the pub_id of the Publication
	 */
	public static void assignEditorToPublication(int person_id, int pub_id) {
		try {
			statement.executeUpdate(String.format("INSERT INTO Editor_edit_Publications VALUES (%d, %d)", person_id, pub_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all current Periodicals.
	 */
	public static void viewPeriodicals() {
		try {
			System.out.println("Periodicals Information");
			result = statement.executeQuery("SELECT * FROM Periodicals");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all salaries for a specified Person.
	 * @param person_id the person_id of the Person
	 */
	public static void viewPaymentsSalary(String person_id) {
		try {
			if (person_id.equals("")) {
				System.out.println("Payments: Salary Information");
				result = statement.executeQuery("SELECT pay_id, date, Payments.type, amount, " +
						"name, Persons.type FROM Payments JOIN Persons " +
						"on Payments.person_id = Persons.person_id WHERE Payments.type = 'salary'");
			} else {
				System.out.println("Payments: Salary Information of person_id" + person_id);
				result = statement.executeQuery("SELECT pay_id, date, Payments.type, amount, " +
						"name, Persons.type FROM Payments JOIN Persons " +
						"on Payments.person_id = Persons.person_id WHERE Payments.type = 'salary'" +
						" AND Payments.person_id = " + person_id);
			}
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Editors and Authors.
	 */
	public static void viewEditorsAuthors() {
		try {
			System.out.println("Editors and Authors Information");
			result = statement.executeQuery("(SELECT Persons.person_id AS person_id, name, gender, age, email, " +
					"CONCAT(Editors.type, ' ', Persons.type) AS type FROM Persons JOIN Editors ON Persons.person_id = Editors.person_id)\n" +
					"UNION (SELECT Persons.person_id AS person_id, name, gender, age, email, " +
					"CONCAT(Authors.type, ' ', Persons.type) AS type FROM Persons JOIN Authors ON Persons.person_id = Authors.person_id);");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Editors.
	 */
	public static void viewEditors() {
		try {
			result = statement.executeQuery("(SELECT Persons.person_id AS person_id, name, gender, age, email, " +
					"CONCAT(Editors.type, ' ', Persons.type) AS type FROM Persons JOIN Editors ON Persons.person_id = Editors.person_id)");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all information in Editor_edit_Publication table.
	 */
	public static void viewEditor_edit_Publication() {
		try {
			result = statement.executeQuery("SELECT * FROM Editor_edit_Publications");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all information in Articles_or_Chapters_in_Publications table.
	 */
	public static void viewArticles_or_Chapters_in_Publications() {
		try {
			result = statement.executeQuery("SELECT * FROM Articles_or_Chapters_in_Publications");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all unclaimed payments for a specified Person.
	 * @param person_id the person_id of the Person
	 */
	public static void viewUnclaimedPayments(String person_id) {
		try {
			System.out.println("Unclaimed Payments of person_id:" + person_id);
			result = statement.executeQuery("SELECT * FROM Payments WHERE person_id=" + person_id + " AND date IS NULL");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Books.
	 */
	public static void viewBooks() {
		try {
			System.out.println("Books Information");
			result = statement.executeQuery("SELECT * FROM Books NATURAL JOIN Publications");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Issues.
	 */
	public static void viewIssues() {
		try {
			System.out.println("Issues Information");
			result = statement.executeQuery("SELECT * FROM Issues NATURAL JOIN Publications NATURAL JOIN Periodicals");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Articles/Chapters.
	 */
	public static void viewArticlesChapters() {
		try {
			System.out.println("Articles/Chapters Information");
			result = statement.executeQuery("SELECT * FROM Articles_Chapters");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a new Periodical
	 * @param type the type of the Periodical (journal/magazine)
	 * @param periodicity the periodicity of the Periodical (monthly/weekly/daily)
	 * @param topic the topic of the Periodical (sports/health/science...)
	 */
	public static void addPeriodical(String type, String periodicity, String topic) {
		try {
			statement.executeUpdate("INSERT INTO Periodicals VALUES(NULL,'" + type + "', '" + periodicity + "', '" + topic + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a new Article/Chapter
	 * @param date the date of the Article/Chapter (YYYY-MM-DD)
	 * @param text the text of the Article/Chapter
	 * @param title the title of the Article/Chapter
	 * @param topic the topic of the Article/Chapter (sports/health/science...)
	 */
	public static void addArticleChapter(String date, String text, String title, String topic) {
		try {
			statement.executeUpdate("INSERT INTO Articles_Chapters VALUES (NULL, '" + date + "', '" + text + "', '" + title + "', '" + topic + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a new Article/Chapter to a Publication.
	 * @param art_id the art_id of the Article/Chapter to be added
	 * @param pub_id the pub_id of the Publication to add to
	 */
	public static void addArticleToPublication(int art_id, int pub_id) {
		try {
			statement.executeUpdate(String.format("INSERT INTO Articles_or_Chapters_in_Publications VALUES (%d, %d)", pub_id, art_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete an Article/Chapter.
	 * @param art_id the art_id of the Article/Chapter
	 */
	public static void deleteArticleChapter(String art_id) {
		try {
			statement.executeUpdate("DELETE FROM Articles_Chapters WHERE art_id=" + art_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete an Article/Chapter from a Publication. The Article/Chapter still remains in the database and may appear in other Publications.
	 * @param art_id the art_id of the Article/Chapter to be deleted
	 * @param pub_id the pub_id of the Publication to deleted from
	 */
	public static void deleteArticleToPublication(int art_id, int pub_id) {
		try {
			statement.executeUpdate(String.format("DELETE FROM Articles_or_Chapters_in_Publications WHERE art_id=%d and pub_id=%d", art_id, pub_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a new Book.
	 * @param title the title of the new Book
	 * @param date the date of the new Book (YYYY-MM-DD)
	 * @param edition the edition of the new Book
	 * @param ISBN the ISBN of the new Book
	 * @param topic the topic of the new Book (sports/health/science...)
	 */
	public static void addBook(String title, String date, String edition, String ISBN, String topic) {
		try {
			connection.setAutoCommit(false);
			statement.executeUpdate("INSERT INTO Publications VALUES(NULL,'" + title + "','" + date + "')");
			statement.executeUpdate("INSERT INTO Books VALUES(LAST_INSERT_ID()," + edition + ",'" + ISBN + "', '" + topic + "')");
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a new Issue.
	 * @param title the title of the new Issue
	 * @param date the date of the new Issue (YYYY-MM-DD)
	 * @param period_id the period_id of the Periodical the new Issue belongs to
	 */
	public static void addIssue(String title, String date, String period_id) {
		try {
			connection.setAutoCommit(false);
			statement.executeUpdate("INSERT INTO Publications VALUES(NULL, '" + title + "', '" + date + "')");
			statement.executeUpdate("INSERT INTO Issues VALUES(LAST_INSERT_ID(), " + period_id + ")");
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update a Book.
	 * @param pub_id the pub_id of the Book to be updated
	 * @param edition the new edition of the Book
	 * @param ISBN the new ISBN of the Book
	 * @param topic the new topic of the Book (sports/health/science...)
	 * @param title the new title of the Book
	 * @param date the new date of the Book (YYYY-MM-DD)
	 */
	public static void updateBook(String pub_id, String edition, String ISBN, String topic, String title, String date) {
		try {
			connection.setAutoCommit(false);
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
				statement.executeUpdate("UPDATE Publications SET title='" + title + "' WHERE pub_id=" + pub_id);
			}
			if (!date.equals("")) {
				statement.executeUpdate("UPDATE Publications SET date='" + date + "' WHERE pub_id=" + pub_id);
			}
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update an Issue.
	 * @param pub_id the pub_id of the Issue to be updated
	 * @param title the new title of the Issue
	 * @param date the new date of the Issue (YYYY-MM-DD)
	 * @param type the new type of the Periodical the Issue belongs to (journal/magazine)
	 * @param periodicity the new periodicity of the Periodical the Issue belongs to (journal/magazine)
	 * @param topic the new topic of the Periodical the Issue belongs to (sports/health/science...)
	 */
	public static void updateIssue(String pub_id, String title, String date, String type, String periodicity, String topic) {
		try {
			connection.setAutoCommit(false);
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
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete a Publication.
	 * @param pub_id the pub_id of the Publication to be deleted
	 */
	public static void deletePublication(String pub_id) {
		try {
			statement.executeUpdate("DELETE FROM Publications WHERE pub_id=" + pub_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update a Publication.
	 * @param pub_id the pub_id of the Publication to be updated
	 * @param title the new title of the Publication
	 * @param date the new date of the Publication (YYYY-MM-DD)
	 */
	public static void updatePublication(int pub_id, String title, String date) {
		try {
			connection.setAutoCommit(false);
			if (title != null) {
				statement.executeUpdate("UPDATE Publications SET title='" + title + "' WHERE pub_id=" + pub_id);
			}
			if (date != null) {
				statement.executeUpdate("UPDATE Publications SET date='" + date + "' WHERE pub_id=" + pub_id);
			}
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update an Article/Chapter
	 * @param art_id the art_id of the Article/Chapter to be updated
	 * @param date the new date of the Article/Chapter (YYYY-MM-DD)
	 * @param text the new text of the Article/Chapter
	 * @param title the new title of the Article/Chapter
	 * @param topic the new topic of the Article/Chapter
	 */
	public static void updateArticleChapter(String art_id, String date, String text, String title, String topic) {
		try {
			connection.setAutoCommit(false);
			if (!date.equals("")) {
				statement.executeUpdate("UPDATE Articles_Chapters SET date='" + date + "' WHERE art_id=" + art_id);
			}
			if (!text.equals("")) {
				statement.executeUpdate("UPDATE Articles_Chapters SET text='" + text + "' WHERE art_id=" + art_id);
			}
			if (!title.equals("")) {
				statement.executeUpdate("UPDATE Articles_Chapters SET title='" + title + "' WHERE art_id=" + art_id);
			}
			if (!topic.equals("")) {
				statement.executeUpdate("UPDATE Articles_Chapters SET topic='" + topic + "' WHERE art_id=" + art_id);
			}
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Find all Books satisfying the search criteria.
	 * @param topic the topic to search for
	 * @param date the date to search for (YYYY-MM-DD)
	 * @param authorName the authorName to search for
	 */
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
			String query = "SELECT title, edition, ISBN, date, topic, name AS author_name FROM (Books NATURAL JOIN Publications)" +
					" LEFT JOIN (Author_write_Books NATURAL JOIN Persons) ON Books.pub_id=Author_write_Books.pub_id";
			if (search.size() > 0)
				query += " WHERE " + String.join(" AND ", search);
			result = statement.executeQuery(query);
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Find all Books satisfying the search criteria.
	 * @param topic the topic to search for
	 * @param date the date to search for (YYYY-MM-DD)
	 * @param authorName the authorName to search for
	 */
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

	/**
	 * Add a new Payment (salary) to a specified Person.
	 * @param amount the amount of the new Payment
	 * @param person_id the person_id of the Person
	 */
	public static void addPayment(String amount, String person_id) {
		try {
			statement.executeUpdate("INSERT INTO Payments VALUES(NULL, NULL, 'salary', -" + amount + ", " + person_id +")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Claim a Payment (salary) for a specified Person.
	 * @param pay_id the pay_id of the Payment to claim
	 * @param person_id the person_id of the Person
	 */
	public static void claimPayment(String pay_id, String person_id) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate today = LocalDate.now();
		try {
			if (pay_id.equals(""))
				statement.executeUpdate("UPDATE Payments SET date='" + formatter.format(today) + "' WHERE person_id=" + person_id + " AND date IS NULL");
			else
				statement.executeUpdate("UPDATE Payments SET date='" + formatter.format(today) + "' WHERE pay_id=" + pay_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Distributors.
	 */
	public static void viewDistributors() {
		try {
			result = statement.executeQuery("SELECT Persons.person_id,name,phone_num,address,balance,contact_person,Distributors.type,city FROM Persons, Distributors WHERE Persons.person_id = Distributors.person_id;");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Orders.
	 */
	public static void viewOrders() {
		try {
			result = statement.executeQuery("SELECT * FROM Orders;");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Publications.
	 */
	public static void viewPublications() {
		try {
			result = statement.executeQuery("SELECT * FROM Publications ;");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Payments.
	 */
	public static void viewPayments() {
		try {
			result = statement.executeQuery("SELECT * FROM Payments;");
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a new Distributor.
	 * @param name the name of the new Distributor
	 * @param balance the balance of the new Distributor
	 * @param contact_person the contact_person of the new Distributor
	 * @param phone_num the phone_num of the new Distributor
	 * @param d_type the d_type of the new Distributor (wholesaler/bookstore...)
	 * @param city the city of the new Distributor
	 * @param address the address of the new Distributor
	 */
	public static void enterNewDistributor(String name, Float balance, String contact_person, String phone_num, String d_type, String city, String address) {
		try {
			connection.setAutoCommit(false);
			statement.executeUpdate(String.format("INSERT INTO Persons VALUES (NULL, 'distributor', '%s', NULL, NULL, NULL, '%s', '%s');", name, phone_num, address));
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

	/**
	 * Update a Distributor
	 * @param person_id the person_id of the Distributor to be updated
	 * @param name the new name of the Distributor
	 * @param balance the new balance of the Distributor
	 * @param contact_person the new contact_person of the Distributor
	 * @param phone_num the new phone_num of the Distributor
	 * @param d_type the new d_type of the Distributor (wholesaler/bookstore...)
	 * @param city the new city of the Distributor
	 * @param address the new address of the Distributor
	 */
	public static void updateDistributor(int person_id, String name, String balance, String contact_person, String phone_num, String d_type, String city, String address) {
		try {
			connection.setAutoCommit(false);
			if (!name.equals("")) {
				statement.executeUpdate(String.format("UPDATE Persons SET name = '%s' WHERE person_id =%d;", name, person_id));
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

	/**
	 * Delete a Distributor.
	 * @param person_id the person_id of the Distributor to be deleted
	 */
	public static void deleteDistributor(int person_id){
		try {
			statement.executeUpdate(String.format("DELETE FROM Persons WHERE person_id=%d;", person_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Add a new Order for a Distributor for a Publication.
	 * @param num_of_copy the num_of_copy of the new Order
	 * @param order_date the order_date of the new Order (YYYY-MM-DD)
	 * @param delivery_date the delivery_date of the new Order (YYYY-MM-DD)
	 * @param price the price of the new Order
	 * @param shipping_cost the shipping_cost of the new Order
	 * @param person_id the person_id of the Distributor to add the new Order for
	 * @param pub_id the pub_id of the Publication in the new Order
	 */
	public static void inputOrderByDistributor(int num_of_copy, String order_date, String delivery_date, float price, float shipping_cost, int person_id, int pub_id) {
		try {
			connection.setAutoCommit(false);	
			statement.executeUpdate(String.format("INSERT INTO Payments VALUES (NULL, '%s', 'shipping', %f, %d);", order_date, -shipping_cost, person_id));
			statement.executeUpdate(String.format("INSERT INTO Orders VALUES(NULL, %d, '%s', '%s', %f, %f, %d, %d);", num_of_copy, order_date, delivery_date, price, shipping_cost, person_id, pub_id));
			statement.executeUpdate("UPDATE Distributors SET balance=balance+(SELECT price FROM Orders WHERE order_id=LAST_INSERT_ID()) WHERE person_id=(SELECT person_id FROM Orders WHERE order_id=LAST_INSERT_ID()); ");
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

	/**
	 * Bill the Distributor for an Order
	 * @param order_id the order_id of the Order
	 */
	public static void billDistributorAnOrder(int order_id){
		try {
			statement.executeUpdate(String.format("UPDATE Distributors SET balance=balance+(SELECT price FROM Orders WHERE order_id=%d) WHERE person_id=(SELECT person_id FROM Orders WHERE order_id=%d); ", order_id, order_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Change balance of a Distributor on receipt of a Payment
	 * @param date the date of the Payment (YYYY-MM-DD)
	 * @param amount the amount of the Payment
	 * @param person_id the person_id of the Distributor
	 */
	public static void changeBalance(String date, Float amount, int person_id) {
		try {
			connection.setAutoCommit(false);
			statement.executeUpdate(String.format("INSERT INTO Payments VALUES (NULL, '%s', 'income', %f, %d);", date, amount, person_id));
			statement.executeUpdate(String.format("UPDATE Distributors SET balance = balance-%f WHERE person_id =%d;", amount, person_id));
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

	/**
	 * Generate monthly report.
	 * @param year the year for the report
	 * @param month the month for the report
	 */
	public static void generateMonthlyReport(int year, int month) {
		try {
			result = statement.executeQuery(String.format("SELECT CONCAT(YEAR(order_date), '-', MONTH(order_date)) AS month, person_id," +
					" name, pub_id, title, SUM(num_of_copy), SUM(price) AS total_price, SUM(price)+SUM(shipping_cost) " +
					"AS total_cost FROM Orders NATURAL JOIN Persons NATURAL JOIN Publications " +
					"WHERE MONTH(order_date) = %d AND YEAR(order_date) = %d " +
					"GROUP BY person_id, pub_id;", month, year));
			
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all revenue.
	 * @param year the year for the report
	 * @param month the month for the report
	 */
	public static void totalRevenueofPublishingHouse(int year, int month) {
		try {
			result = statement.executeQuery(String.format("SELECT CONCAT(YEAR(order_date), '-', MONTH(order_date)) AS month, " +
					"SUM(price) AS total_price FROM Orders WHERE MONTH(order_date) = %d AND YEAR(order_date) = %d;", month, year));
			
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all expenses.
	 * @param year the year for the report
	 * @param month the month for the report
	 */
	public static void totalExpenses(int year, int month) {
		try {
			result = statement.executeQuery(String.format("SELECT CONCAT(YEAR(date), '-', MONTH(date)) AS month, SUM(amount) AS total_expense " +
					"FROM Payments WHERE (type='salary' OR type='shipping') AND MONTH(date) = %d AND YEAR(date) = %d", month, year));
			
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print the number of Distributors.
	 */
	public static void totalDistributors() {
		try {
			result = statement.executeQuery("SELECT COUNT(*) FROM Distributors;");
			
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generate report of revenue per city.
	 */
	public static void totalRevenuePerCity() {
		try {
			result = statement.executeQuery("SELECT CONCAT(YEAR(order_date), '-', MONTH(order_date)) AS month, city, " +
					"SUM(price) AS total_price FROM Orders INNER JOIN Distributors ON Orders.person_id=Distributors.person_id " +
					"GROUP BY city;");
			
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generate report of revenue per disributor.
	 */
	public static void totalRevenuePerDistributor() {
		try {
			result = statement.executeQuery("SELECT CONCAT(YEAR(order_date), '-', MONTH(order_date)) AS month, " +
					"Distributors.person_id, SUM(price) AS total_price FROM Orders INNER JOIN Distributors ON Orders.person_id=Distributors.person_id " +
					"GROUP BY Distributors.person_id;");
			
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generate report of revenue per location.
	 */
	public static void totalRevenuePerLocation() {
		try {
			result = statement.executeQuery("SELECT CONCAT(YEAR(order_date), '-', MONTH(order_date)) AS month, address, SUM(price) AS total_price " +
					"FROM Orders INNER JOIN Persons ON Orders.person_id=Persons.person_id GROUP BY address;");
			
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Payments for Editors for a month.
	 * @param year the year for the report
	 * @param month the month for the report
	 */
	public static void totalPaymentsEditorsPerTimePeriod(int year, int month) {
		try {
			result = statement.executeQuery(String.format("SELECT CONCAT(YEAR(Payments.date), '-', MONTH(Payments.date)) AS month, " +
					"SUM(amount) AS total_salary FROM Payments JOIN Editors ON Payments.person_id=Editors.person_id " +
					"WHERE Payments.type='salary' AND MONTH(Payments.date)=%d AND YEAR(Payments.date)=%d;", month, year));
			
			printResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print all Payments for Authors for a month.
	 * @param year the year for the report
	 * @param month the month for the report
	 */
	public static void totalPaymentsAuthorsPerTimePeriod(int year, int month) {
		try {
			result = statement.executeQuery(String.format("SELECT CONCAT(YEAR(date), '-', MONTH(date)) AS month, SUM(amount) AS total_salary " +
					"FROM Payments JOIN Authors ON Payments.person_id=Authors.person_id " +
					"WHERE Payments.type='salary' AND MONTH(Payments.date)=%d AND YEAR(Payments.date)=%d;", month, year));
			
			printResultSet(result);
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
