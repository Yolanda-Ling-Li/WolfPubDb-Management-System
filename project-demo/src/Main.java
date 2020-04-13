import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * This program is designed to manage a publishing house. Admin as an user, can add/update publications, books, issues, orders, and
 * view useful information in this system. The system can also print monthly report which shows information of distributor and order
 * details, and information of location, city, salary, and payments.
 * @author Zhuowei Li, Ling Li, Larry Lai, Qingyan Wang
 *
 */
public class Main {
	// A list of operation strings for printing out
	private static List<String> operations = new ArrayList<String>();
	// A list of task strings for printing out
	private static List<List<String>> tasks = new ArrayList<List<String>>();
	
	static {
		// Basic operations
		operations.add("Editing and publishing");
		operations.add("Production of a book edition or of an issue of a publication");
		operations.add("Distribution");
		operations.add("Reports");
		// Basic tasks
		List<String> op1Tasks = new ArrayList<String>();
		op1Tasks.add("Enter basic information on a new publication");
		op1Tasks.add("Update publication information");
		op1Tasks.add("Assign editor(s) to publication");
		op1Tasks.add("Let each editor view the information on the publications he/she is responsible for");
		op1Tasks.add("Edit table of contents of a publication, by adding/deleting articles (for periodic publications) or chapters/sections (for books)");
		tasks.add(op1Tasks);
		// Basic tasks
		List<String> op2Tasks = new ArrayList<String>();
		op2Tasks.add("Enter a new book edition or new issue of a publication");
		op2Tasks.add("Update, delete a book edition or publication issue");
		op2Tasks.add("Enter an article or chapter");
		op2Tasks.add("Update, delete an article or chapter: title, author's name, topic, text, and date");
		op2Tasks.add("Find books and articles by topic, date, author's name");
		op2Tasks.add("Enter payment for author or editor");
		op2Tasks.add("Claim payment for author or editor");
		tasks.add(op2Tasks);
		// Basic tasks
		List<String> op3Tasks = new ArrayList<String>();
		op3Tasks.add("Enter new distributor");
		op3Tasks.add("Update distributor information");
		op3Tasks.add("Delete a distributor");
		op3Tasks.add("Input orders from distributors, for a book edition or an issue of a publication per distributor, for a certain date");
		op3Tasks.add("Bill distributor for an order");
		op3Tasks.add("Change outstanding balance of a distributor on receipt of a payment");
		tasks.add(op3Tasks);
		// Basic tasks
		List<String> op4Tasks = new ArrayList<String>();
		op4Tasks.add("Generate monthly reports: number and total price of copies of each publication bought per distributor per month");
		op4Tasks.add("total revenue of the publishing house");
		op4Tasks.add("total expenses (i.e., shipping costs and salaries)");
		op4Tasks.add("Calculate the total current number of distributors");
		op4Tasks.add("Calculate total revenue (since inception) per city, per distributor, and per location");
		op4Tasks.add("Calculate total payments to the editors and authors, per time period and per work type (book authorship, article authorship, or editorial work)");
		tasks.add(op4Tasks);
	}
	/**
	 * Print a menu and prompt for user input. User can choose from menu to do different operation in this system.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Select operation:");
		for (int i = 0; i < operations.size(); i++) {
			System.out.println((i + 1) + ". " + operations.get(i));
		}
		Scanner scanner = new Scanner(System.in);
		int selectedOperation = Integer.parseInt(scanner.nextLine());
		System.out.println("Select task:");
		for (int i = 0; i < tasks.get(selectedOperation - 1).size(); i++) {
			System.out.println((i + 1) + ". " + tasks.get(selectedOperation - 1).get(i));
		}
		int selectedTask = Integer.parseInt(scanner.nextLine());
//		System.out.println(selectedTask);
		
		System.out.println("Your task: " + tasks.get(selectedOperation - 1).get(selectedTask - 1));
		// If user input is 1, printout first menu and show tasks related to publications
		if (selectedOperation == 1) {
			if (selectedTask == 1) {
				System.out.println("*The current table of Publications is:");
				DBActions.viewPublications();
				System.out.println("title: ");
				String title = scanner.nextLine();
				System.out.println("date(YYYY-MM-DD): ");
				String date = scanner.nextLine();
				DBActions.addPublication(title, date);
				System.out.println("*The current table of Publications is:");
				DBActions.viewPublications();
			}
			if (selectedTask == 2) {
				System.out.println("*The current table of Publications is:");
				DBActions.viewPublications();
				System.out.println("pub_id:");
				int pub_id = Integer.parseInt(scanner.nextLine());
				System.out.println("title:");
				String title = scanner.nextLine();
				System.out.println("date(YYYY-MM-DD): ");
				String date = scanner.nextLine();
				DBActions.updatePublication(pub_id, title.isEmpty() ? null : title, date.isEmpty() ? null : date);
				System.out.println("*The current table of Publications is:");
				DBActions.viewPublications();
			}
			if (selectedTask == 3) {
				System.out.println("*The current table of Editors is:");
				DBActions.viewEditors();
				System.out.println("*The current table of Publications is:");
				DBActions.viewPublications();
				System.out.println("*The current table of Editor_edit_Publications is:");
				DBActions.viewEditor_edit_Publication();
				System.out.println("person_id:");
				int person_id = Integer.parseInt(scanner.nextLine());
				System.out.println("pub_id:");
				int pub_id = Integer.parseInt(scanner.nextLine());
				DBActions.assignEditorToPublication(person_id, pub_id);
				
				System.out.println("*The current table of Editor_edit_Publications is:");
				DBActions.viewEditor_edit_Publication();
			}
			if (selectedTask == 4) {
				System.out.println("*The current table of Editors is:");
				DBActions.viewEditors();
				System.out.println("person_id:");
				int person_id = Integer.parseInt(scanner.nextLine());
				DBActions.viewPublicationByEditor(person_id);
			}
			if (selectedTask == 5) {
				System.out.println("1. add 2. delete");
				int selected = Integer.parseInt(scanner.nextLine());
				System.out.println("*The current table of Articles_Chapters is:");
				DBActions.viewArticlesChapters();
				System.out.println("*The current table of Publications is:");
				DBActions.viewPublications();
				System.out.println("*The current table of Author_write_Articles_or_Chapters is:");
				DBActions.viewArticles_or_Chapters_in_Publications();
				if (selected == 1) {
					System.out.println("art_id: ");
					int art_id = Integer.parseInt(scanner.nextLine());
					System.out.println("pub_id: ");
					int pub_id = Integer.parseInt(scanner.nextLine());
					DBActions.addArticleToPublication(art_id, pub_id);
				}
				if (selected == 2) {
					System.out.println("art_id: ");
					int art_id = Integer.parseInt(scanner.nextLine());
					System.out.println("pub_id: ");
					int pub_id = Integer.parseInt(scanner.nextLine());
					DBActions.deleteArticleToPublication(art_id, pub_id);
				}
				
				System.out.println("*The current table of Author_write_Articles_or_Chapters is:");
				DBActions.viewArticles_or_Chapters_in_Publications();
			}
		}
		// If user input is 2, printout second menu and show tasks related to publications, updating, and assigning editors
		if (selectedOperation == 2) {
			if (selectedTask == 1) {
				//Enter a new book edition or new issue of a publication
				System.out.println("1. New Book 2. New Issue: ");
				int newPub = Integer.parseInt(scanner.nextLine());
				if (newPub == 1) {
					DBActions.viewBooks();
					System.out.println("Adding a new Book...");
					System.out.println("title: ");
					String title = scanner.nextLine();
					System.out.println("date(YYYY-MM-DD): ");
					String date = scanner.nextLine();
					System.out.println("edition: ");
					String edition = scanner.nextLine();
					System.out.println("ISBN: ");
					String ISBN = scanner.nextLine();
					System.out.println("topic: ");
					String topic = scanner.nextLine();
					DBActions.addBook(title, date, edition, ISBN, topic);
					DBActions.viewBooks();
				} else if (newPub == 2) {
					DBActions.viewPeriodicals();
					System.out.println("The new issue is for 1. Existing Periodical 2. New Periodical.");
					int period = Integer.parseInt(scanner.nextLine());
					if (period == 2) {
						System.out.println("Adding a new Periodical...");
						System.out.println("type: ");
						String type = scanner.nextLine();
						System.out.println("periodicity: ");
						String periodicity = scanner.nextLine();
						System.out.println("topic: ");
						String topic = scanner.nextLine();
						DBActions.addPeriodical(type, periodicity, topic);
						DBActions.viewPeriodicals();
					}
					System.out.println();
					DBActions.viewIssues();
					System.out.println("Adding a new Issue...");
					System.out.println("period_id: ");
					String period_id = scanner.nextLine();
					System.out.println("title: ");
					String title = scanner.nextLine();
					System.out.println("date(YYYY-MM-DD): ");
					String date = scanner.nextLine();
					DBActions.addIssue(title, date, period_id);
					DBActions.viewIssues();
				}
			}
			if (selectedTask == 2) {
				//Update, delete a book edition or publication issue

				System.out.println("Update/delete 1. a Book 2. an Issue.");
				int pub = Integer.parseInt(scanner.nextLine());
				if (pub == 1) {
					DBActions.viewBooks();
					System.out.println("Enter the pub_id of the book you want to update/delete: ");
					String pub_id = scanner.nextLine();
					System.out.println("1. Update it 2. Delete it");
					int op = Integer.parseInt(scanner.nextLine());
					if (op == 1) {
						System.out.println("title (press enter if no change): ");
						String title = scanner.nextLine();
						System.out.println("date(YYYY-MM-DD) (press enter if no change): ");
						String date = scanner.nextLine();
						System.out.println("edition (press enter if no change): ");
						String edition = scanner.nextLine();
						System.out.println("ISBN (press enter if no change): ");
						String ISBN = scanner.nextLine();
						System.out.println("topic (press enter if no change): ");
						String topic = scanner.nextLine();
						DBActions.updateBook(pub_id, edition, ISBN, topic, title, date);
					} else if (op == 2) {
						DBActions.deletePublication(pub_id);
					}
					DBActions.viewBooks();
				} else if (pub == 2) {
					DBActions.viewIssues();
					System.out.println("Enter the pub_id of the issue you want to update/delete: ");
					String pub_id = scanner.nextLine();
					System.out.println("1. Update it 2. Delete it");
					int op = Integer.parseInt(scanner.nextLine());
					if (op == 1) {
						System.out.println("title (press enter if no change): ");
						String title = scanner.nextLine();
						System.out.println("date(YYYY-MM-DD) (press enter if no change): ");
						String date = scanner.nextLine();
						System.out.println("type (press enter if no change): ");
						String type = scanner.nextLine();
						System.out.println("periodicity (press enter if no change): ");
						String periodicity = scanner.nextLine();
						System.out.println("topic (press enter if no change): ");
						String topic = scanner.nextLine();
						DBActions.updateIssue(pub_id, title, date, type, periodicity, topic);
					} else if (op == 2) {
						DBActions.deletePublication(pub_id);
					}
					DBActions.viewIssues();
				}
			}
			if (selectedTask == 3) {
				//Enter an article or chapter
				DBActions.viewArticlesChapters();
				System.out.println("Adding a new Article/Chapter...");
				System.out.println("date(YYYY-MM-DD): ");
				String date = scanner.nextLine();
				System.out.println("text: ");
				String text = scanner.nextLine();
				System.out.println("title: ");
				String title = scanner.nextLine();
				System.out.println("topic: ");
				String topic = scanner.nextLine();
				DBActions.viewPublications();
				DBActions.addArticleChapter(date, text, title, topic);
				DBActions.viewArticlesChapters();
			}
			if (selectedTask == 4) {
				//Update, delete an article or chapter: title, author's name, topic, text, and date

				DBActions.viewArticlesChapters();
				System.out.println("Enter the art_id of the article/chapter you want to update/delete: ");
				String art_id = scanner.nextLine();
				System.out.println("1. Update it 2. Delete it");
				int op = Integer.parseInt(scanner.nextLine());
				if (op == 1) {
					System.out.println("date (YYYY-MM-DD) (press enter if no change): ");
					String date = scanner.nextLine();
					System.out.println("text (press enter if no change): ");
					String text = scanner.nextLine();
					System.out.println("title (press enter if no change): ");
					String title = scanner.nextLine();
					System.out.println("topic (press enter if no change): ");
					String topic = scanner.nextLine();
					DBActions.updateArticleChapter(art_id, date, text, title, topic);
				} else if (op == 2) {
					DBActions.deleteArticleChapter(art_id);
				}
				DBActions.viewArticlesChapters();
			}
			if (selectedTask == 5) {
				//Find books and articles by topic, date, author's name

				System.out.println("Find 1. a Book 2. an Article/Chapter");
				int pub = Integer.parseInt(scanner.nextLine());
				System.out.println("topic (press enter if not used): ");
				String topic = scanner.nextLine();
				System.out.println("date(YYYY-MM-DD) (press enter if not used): ");
				String date = scanner.nextLine();
				System.out.println("author's name (press enter if not used): ");
				String name = scanner.nextLine();
				if (pub == 1) {
					DBActions.searchBooks(topic, date, name);
				} else if (pub == 2) {
					DBActions.searchArticles(topic, date, name);
				}
			}
			if (selectedTask == 6) {
				//Enter payment for author or editor
				DBActions.viewPaymentsSalary("");
				DBActions.viewEditorsAuthors();
				System.out.println("Enter the person_id of the addressee you want to pay: ");
				String person_id = scanner.nextLine();
				System.out.println("Enter the amount of payment: ");
				String amount = scanner.nextLine();
				DBActions.addPayment(amount, person_id);
				DBActions.viewPaymentsSalary("");
			}
			if (selectedTask == 7) {
				//Claim payment for author or editor
				DBActions.viewEditorsAuthors();
				System.out.println("Enter your person_id to claim payments: ");
				String person_id = scanner.nextLine();
				DBActions.viewUnclaimedPayments(person_id);
				System.out.println("Enter the pay_id of the payment you want to claim (press enter to claim all of above): ");
				String pay_id = scanner.nextLine();
				DBActions.claimPayment(pay_id, person_id);
				DBActions.viewPaymentsSalary(person_id);
			}
		}
		// If user input is 3, printout third menu and show tasks related to orders and distributors
		if (selectedOperation == 3) {
			if (selectedTask == 1) {
				System.out.println("*The current table of Distributors is:");
				DBActions.viewDistributors();
				System.out.println("name: ");
				String name = scanner.nextLine();
				System.out.println("balance: ");
				Float balance = Float.parseFloat(scanner.nextLine());
				System.out.println("contact_person: ");
				String contact_person = scanner.nextLine();
				System.out.println("phone_num: ");
				String phone_num = scanner.nextLine();
				System.out.println("type of distributor: ");
				String d_type = scanner.nextLine();
				System.out.println("city: ");
				String city = scanner.nextLine();
				System.out.println("address: ");
				String address = scanner.nextLine();				
				DBActions.enterNewDistributor(name, balance, contact_person, phone_num, d_type, city, address);
				System.out.println("*After insert, the current table of Distributors is:");
				DBActions.viewDistributors();
			}
			if (selectedTask == 2) {
				System.out.println("*The current table of Distributors is:");
				DBActions.viewDistributors();
				System.out.println("Which distributor do you want to update? Please input id: ");
				int person_id = Integer.parseInt(scanner.nextLine());
				System.out.println("new name(Enter for not change): ");
				String name = scanner.nextLine();
				System.out.println("new balance(Enter for not change): ");
				String balance = scanner.nextLine();
				System.out.println("new contact_person(Enter for not change): ");
				String contact_person = scanner.nextLine();
				System.out.println("new phone_num(Enter for not change): ");
				String phone_num = scanner.nextLine();
				System.out.println("new type of distributor(Enter for not change): ");
				String d_type = scanner.nextLine();
				System.out.println("new city(Enter for not change): ");
				String city = scanner.nextLine();
				System.out.println("new address(Enter for not change): ");
				String address = scanner.nextLine();
				DBActions.updateDistributor(person_id, name, balance, contact_person, phone_num, d_type, city, address);
				System.out.println("*After update, the current table of Distributors is:");
				DBActions.viewDistributors();
			}
			if (selectedTask == 3) {
				System.out.println("*The current table of Distributors is:");
				DBActions.viewDistributors();
				System.out.println("which distributor do you want to delete? Please input id: ");
				int person_id = Integer.parseInt(scanner.nextLine());
				DBActions.deleteDistributor(person_id);
				System.out.println("*After delete, the current table of Distributors is:");
				DBActions.viewDistributors();
			}
			if (selectedTask == 4) {
				System.out.println("*The current table of Orders is:");
				DBActions.viewOrders();
				System.out.println("num_of_copy: ");
				Integer num_of_copy = Integer.parseInt(scanner.nextLine());
				System.out.println("order_date(YYYY-MM-DD): ");
				String order_date = scanner.nextLine();
				System.out.println("delivery_date(YYYY-MM-DD): ");
				String delivery_date = scanner.nextLine();
				System.out.println("price: ");
				Float price = Float.parseFloat(scanner.nextLine());
				System.out.println("shipping_cost: ");
				Float shipping_cost = Float.parseFloat(scanner.nextLine());
				System.out.println("*The current table of Distributors is:");
				DBActions.viewDistributors();
				System.out.println("person_id: ");
				Integer person_id = Integer.parseInt(scanner.nextLine());
				System.out.println("*The current table of Publications is:");
				DBActions.viewPublications();
				System.out.println("pub_id: ");
				Integer pub_id = Integer.parseInt(scanner.nextLine());
				DBActions.inputOrderByDistributor(num_of_copy, order_date, delivery_date, price, shipping_cost, person_id, pub_id);
				System.out.println("*After insert, the current table of Orders is:");
				DBActions.viewOrders();
				System.out.println("=====================================================================");
				System.out.println("*After insert, the current table of Distributors is:");
				DBActions.viewDistributors();
			}
			if (selectedTask == 5) {
				System.out.println("*The current table of Orders is:");
				DBActions.viewOrders();
				System.out.println("=====================================================================");
				System.out.println("*The current table of Distributors is:");
				DBActions.viewDistributors();
				System.out.println("Which order do you want to bill? Please input order id: ");
				int order_id = Integer.parseInt(scanner.nextLine());
				DBActions.billDistributorAnOrder(order_id);
				System.out.println("*After bill, the current table of Distributors is:");
				DBActions.viewDistributors();
			}
			if (selectedTask == 6) {
				System.out.println("*The current table of Distributors is:");
				DBActions.viewDistributors();
				System.out.println("=====================================================================");
				System.out.println("*The current table of Payments is:");
				DBActions.viewPayments();
				System.out.println("Which distributor do you want to change balance? Please input id: ");
				int person_id = Integer.parseInt(scanner.nextLine());
				System.out.println("date(YYYY-MM-DD): ");
				String date = scanner.nextLine();
				System.out.println("amount: ");
				Float amount = Float.parseFloat(scanner.nextLine());
				DBActions.changeBalance(date, amount, person_id);
				System.out.println("*After payment, the current table of Distributors is:");
				DBActions.viewDistributors();
				System.out.println("=====================================================================");
				System.out.println("*After payment, the current table of Payments is:");
				DBActions.viewPayments();
			}
		}
		// If user input is 4, printout fourth menu and show tasks of monthly report generation
		if (selectedOperation == 4) {
			//Generate monthly reports: number and total price of copies of each publication bought per distributor per month
			if (selectedTask == 1) {
				Scanner scan = new Scanner(System.in);
				System.out.println("Please enter a year number (YYYY)");
				int year = scan.nextInt();
				System.out.println("Please enter a month number from 1 to 12");
				int month = scan.nextInt();
				System.out.println();
				System.out.println("Monthly Report");
				DBActions.generateMonthlyReport(year, month);
			}
			//total revenue of the publishing house
			if (selectedTask == 2) {
				Scanner scan = new Scanner(System.in);
				System.out.println("Please enter a year number (YYYY)");
				int year = scan.nextInt();
				System.out.println("Please enter a month number from 1 to 12");
				int month = scan.nextInt();
				System.out.println();
				System.out.println("Total revenue of the publishing house");
				DBActions.totalRevenueofPublishingHouse(year, month);
			}
			//total expenses (i.e., shipping costs and salaries
			if (selectedTask == 3) {
				Scanner scan = new Scanner(System.in);
				System.out.println("Please enter a year number (YYYY)");
				int year = scan.nextInt();
				System.out.println("Please enter a month number from 1 to 12");
				int month = scan.nextInt();
				System.out.println();
				System.out.println("Total expenses (i.e., shipping costs and salaries).");
				DBActions.totalExpenses(year, month);
			}
			//Calculate the total current number of distributors
			if (selectedTask == 4) {
				System.out.println("Total current number of distributors");
				DBActions.totalDistributors();
			}
			//Calculate total revenue (since inception) per city, per distributor, and per location
			if (selectedTask == 5) {
				System.out.println();
				System.out.println("Total revenue per city");
				DBActions.totalRevenuePerCity();
				System.out.println();
				System.out.println("Total revenue per distributor");
				DBActions.totalRevenuePerDistributor();
				System.out.println();
				System.out.println("Total revenue per location");
				DBActions.totalRevenuePerLocation();
				System.out.println();
			}
			//Calculate total payments to the editors and authors, per time period and per work type (book authorship, article authorship, or editorial work
			if (selectedTask == 6) {
				System.out.println();
				Scanner scan = new Scanner(System.in);
				System.out.println("Please enter a year number (YYYY)");
				int year = scan.nextInt();
				System.out.println("Please enter a month number from 1 to 12");
				int month = scan.nextInt();
				System.out.println();
				System.out.println("Total payments to the editors in month " + year + "-" + month);
				DBActions.totalPaymentsEditorsPerTimePeriod(year, month);
				System.out.println();
				System.out.println("Total payments to the authors in month " + year + "-" + month);
				DBActions.totalPaymentsAuthorsPerTimePeriod(year, month);
				System.out.println();
			}
		}
		scanner.close();
		
	}

}
