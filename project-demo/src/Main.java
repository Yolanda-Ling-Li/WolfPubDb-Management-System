import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	private static List<String> operations = new ArrayList<String>();
	private static List<List<String>> tasks = new ArrayList<List<String>>();
	
	static {
		operations.add("Editing and publishing");
		operations.add("Production of a book edition or of an issue of a publication");
		operations.add("Distribution");
		operations.add("Reports");
		
		List<String> op1Tasks = new ArrayList<String>();
		op1Tasks.add("Enter basic information on a new publication");
		op1Tasks.add("Update information");
		op1Tasks.add("Assign editor(s) to publication");
		op1Tasks.add("Let each editor view the information on the publications he/she is responsible for");
		op1Tasks.add("Edit table of contents of a publication, by adding/deleting articles (for periodic publications) or chapters/sections (for books)");
		tasks.add(op1Tasks);
		
		List<String> op2Tasks = new ArrayList<String>();
		op2Tasks.add("Enter a new book edition or new issue of a publication");
		op2Tasks.add("Update, delete a book edition or publication issue");
		op2Tasks.add("Enter/update an article or chapter: title, author's name, topic, text, and date");
		op2Tasks.add("Find books and articles by topic, date, author's name");
		op2Tasks.add("Enter payment for author or editor, and keep track of when each payment was claimed by its addressee");
		tasks.add(op2Tasks);
		
		List<String> op3Tasks = new ArrayList<String>();
		op3Tasks.add("Enter new distributor");
		op3Tasks.add("Update distributor information");
		op3Tasks.add("Delete a distributor");
		op3Tasks.add("Input orders from distributors, for a book edition or an issue of a publication per distributor, for a certain date");
		op3Tasks.add("Bill distributor for an order;change outstanding balance of a distributor on receipt of a payment");
		tasks.add(op3Tasks);
		
		List<String> op4Tasks = new ArrayList<String>();
		op4Tasks.add("Generate monthly reports: number and total price of copies of each publication bought per distributor per month");
		op4Tasks.add("total revenue of the publishing house");
		op4Tasks.add("total expenses (i.e., shipping costs and salaries)");
		op4Tasks.add("Calculate the total current number of distributors");
		op4Tasks.add("Calculate total revenue (since inception) per city, per distributor, and per location");
		op4Tasks.add("Calculate total payments to the editors and authors, per time period and per work type (book authorship, article authorship, or editorial work)");
		tasks.add(op4Tasks);
	}

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
		
		if (selectedOperation == 1) {
			if (selectedTask == 1) {
				System.out.println("title: ");
				String title = scanner.nextLine();
				System.out.println("date: ");
				String date = scanner.nextLine();
				
				DBActions.addPublication(title, date);
				
			}
			if (selectedTask == 2) {
				System.out.println("pub_id:");
				int pub_id = Integer.parseInt(scanner.nextLine());
				System.out.println("title:");
				String title = scanner.nextLine();
				System.out.println("date: ");
				String date = scanner.nextLine();
				
				DBActions.updatePublication(pub_id, title.isEmpty() ? null : title, date.isEmpty() ? null : date);
			}
			if (selectedTask == 3) {
				System.out.println("person_id:");
				int person_id = Integer.parseInt(scanner.nextLine());
				System.out.println("pub_id:");
				int pub_id = Integer.parseInt(scanner.nextLine());
				DBActions.assignEditorToPublication(person_id, pub_id);
			}
			if (selectedTask == 4) {
				System.out.println("person_id:");
				int person_id = Integer.parseInt(scanner.nextLine());
				DBActions.viewPublication(person_id);
			}
			if (selectedTask == 5) {
				System.out.println("1. add 2. update 3. delete");
				int selected = Integer.parseInt(scanner.nextLine());
				if (selected == 1) {
					System.out.println("date: ");
					String date = scanner.nextLine();
					System.out.println("text: ");
					String text = scanner.nextLine();
					System.out.println("title: ");
					String title = scanner.nextLine();
					System.out.println("topic: ");
					String topic = scanner.nextLine();
					System.out.println("pub_id: ");
					int pub_id = Integer.parseInt(scanner.nextLine());
					DBActions.addArticleToPublication(date, text, title, topic, pub_id);
				}
				if (selected == 2) {
					System.out.println("art_id: ");
					int art_id = Integer.parseInt(scanner.nextLine());
					System.out.println("title: ");
					String title = scanner.nextLine();
					System.out.println("text: ");
					String text = scanner.nextLine();
					System.out.println("topic: ");
					String topic = scanner.nextLine();
					System.out.println("date: ");
					String date = scanner.nextLine();
					DBActions.updateArticleOrChapter(art_id, 
							title.isEmpty() ? null : title, 
							text.isEmpty() ? null : text, 
							topic.isEmpty() ? null : topic, 
							date.isEmpty() ? null : date);
				}
				if (selected == 3) {
					System.out.println("art_id: ");
					int art_id = Integer.parseInt(scanner.nextLine());
					DBActions.deleteArticleToPublication(art_id);
				}

			}
		}
		
		if (selectedOperation == 2) {
			if (selectedTask == 1) {
				//Enter a new book edition or new issue of a publication
				System.out.println("Is it a new Book or new Issue? (B/I): ");
				String newPub = scanner.nextLine();
				if (newPub.equals("B")) {
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
				} else if (newPub.equals("I")) {
					DBActions.viewPeriodicals();
					System.out.println("Is it for any of the periodicals above? (Y/N): ");
					if (scanner.nextLine().equals("N")) {
						System.out.println("Do you want to add a new periodical? (Y/N): ");
						if (scanner.nextLine().equals("Y")) {
							System.out.println("type: ");
							String type = scanner.nextLine();
							System.out.println("periodicity: ");
							String periodicity = scanner.nextLine();
							System.out.println("topic: ");
							String topic = scanner.nextLine();
							DBActions.addPeriodical(type, periodicity, topic);
						}
					}
					DBActions.viewPeriodicals();
					System.out.println("Now, add your new Issue.");
					System.out.println("period_id: ");
					String period_id = scanner.nextLine();
					System.out.println("title: ");
					String title = scanner.nextLine();
					System.out.println("date(YYYY-MM-DD): ");
					String date = scanner.nextLine();
					DBActions.addIssue(title, date, period_id);
				}
			}
			if (selectedTask == 2) {
				//Update, delete a book edition or publication issue

				System.out.println("Do you want to update/delete a Book or an Issue? (B/I): ");
				String pub = scanner.nextLine();
				if (pub.equals("B")) {
					DBActions.viewBooks();
					System.out.println("Enter the pub_id of the book you want to update/delete: ");
					String pub_id = scanner.nextLine();
					System.out.println("Do you want to update or delete it? (U/D): ");
					String op = scanner.nextLine();
					if (op.equals("U")) {
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
					} else if (op.equals("D")) {
						DBActions.deletePublication(pub_id);
					}
				} else if (pub.equals("I")) {
					DBActions.viewIssues();
					System.out.println("Enter the pub_id of the issue you want to update/delete: ");
					String pub_id = scanner.nextLine();
					System.out.println("Do you want to update or delete it? (U/D): ");
					String op = scanner.nextLine();
					if (op.equals("U")) {
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
					} else if (op.equals("D")) {
						DBActions.deletePublication(pub_id);
					}
				}
			}
			if (selectedTask == 3) {
				//Enter/update an article or chapter: title, author's name, topic, text, and date

				DBActions.viewArticlesChapters();
				System.out.println("Enter the art_id of the article/chapter you want to update/delete: ");
				String art_id = scanner.nextLine();
				System.out.println("Do you want to update or delete it? (U/D): ");
				String op = scanner.nextLine();
				if (op.equals("U")) {
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
					DBActions.updateBook(art_id, edition, ISBN, topic, title, date);
				} else if (op.equals("D")) {
					DBActions.deletePublication(art_id);
				}
			}
			if (selectedTask == 4) {
				//Find books and articles by topic, date, author's name

				System.out.println("Do you want to find a Book or an Article/Chapter? (B/A): ");
				String pub = scanner.nextLine();
				System.out.println("topic (press enter if not used): ");
				String topic = scanner.nextLine();
				System.out.println("date(YYYY-MM-DD) (press enter if not used): ");
				String date = scanner.nextLine();
				System.out.println("author's name (press enter if not used): ");
				String name = scanner.nextLine();
				if (pub.equals("B")) {
					DBActions.searchBooks(topic, date, name);
				} else if (pub.equals("A")) {
					DBActions.searchArticles(topic, date, name);
				}
			}
			if (selectedTask == 5) {
				
			}
		}
		
		if (selectedOperation == 3) {
			if (selectedTask == 1) {
				String type = "distributor";
				System.out.println("name: ");
				String name = scanner.nextLine();
				System.out.println("gender: ");
				String gender = scanner.nextLine();
				System.out.println("age: ");
				Integer age = Integer.parseInt(scanner.nextLine());
				System.out.println("email: ");
				String email = scanner.nextLine();
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
				DBActions.enterNewDistributor(type, name, gender, age, email, balance, contact_person, phone_num, d_type, city, address);
			}
			if (selectedTask == 2) {
				System.out.println("which distributor do you to want change? Please input id: ");
				int person_id = Integer.parseInt(scanner.nextLine());
				System.out.println("new name(Enter for not change): ");
				String name = scanner.nextLine();
				System.out.println("new gender(Enter for not change): ");
				String gender = scanner.nextLine();
				System.out.println("new age(Enter for not change): ");
				String age = scanner.nextLine();
				System.out.println("new email(Enter for not change): ");
				String email = scanner.nextLine();
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
				DBActions.updateDistributor(person_id, name, gender, age, email, balance, contact_person, phone_num, d_type, city, address);
			}
			if (selectedTask == 3) {
				System.out.println("which distributor do you want to delete? Please input id: ");
				int person_id = Integer.parseInt(scanner.nextLine());
				DBActions.deleteDistributor(person_id);
			}
			if (selectedTask == 4) {
				System.out.println("num_of_copy: ");
				Integer num_of_copy = Integer.parseInt(scanner.nextLine());
				System.out.println("order_date: ");
				String order_date = scanner.nextLine();
				System.out.println("delivery_date: ");
				String delivery_date = scanner.nextLine();
				System.out.println("price: ");
				Float price = Float.parseFloat(scanner.nextLine());
				System.out.println("shipping_cost: ");
				Float shipping_cost = Float.parseFloat(scanner.nextLine());
				System.out.println("person_id: ");
				Integer person_id = Integer.parseInt(scanner.nextLine());
				System.out.println("pub_id: ");
				Integer pub_id = Integer.parseInt(scanner.nextLine());
				DBActions.inputOrderByDistributor(num_of_copy, order_date, delivery_date, price, shipping_cost, person_id, pub_id);
			}
			if (selectedTask == 5) {
				System.out.println("which order do you want to bill? Please input id: ");
				int order_id = Integer.parseInt(scanner.nextLine());
				DBActions.billDistributorAnOrder(order_id);
			}
		}
		
		if (selectedOperation == 4) {
			//Generate monthly reports: number and total price of copies of each publication bought per distributor per month
			if (selectedTask == 1) {
				System.out.println("Please enter a month number from 0 to 12");
				Scanner scan = new Scanner(System.in);
				int month = scan.nextInt();
				System.out.println();
				System.out.println("Monthly Report");
				DBActions.generateMonthlyReport(month);
			}
			//total revenue of the publishing house
			if (selectedTask == 2) {
				System.out.println("Please enter a month number from 0 to 12");
				Scanner scan = new Scanner(System.in);
				int month = scan.nextInt();
				System.out.println();
				System.out.println("Total revenue of the publishing house");
				DBActions.totalRevenueofPublishingHouse(month);
			}
			//total expenses (i.e., shipping costs and salaries
			if (selectedTask == 3) {
				System.out.println("Please enter a month number from 0 to 12");
				Scanner scan = new Scanner(System.in);
				int month = scan.nextInt();
				System.out.println();
				System.out.println("Total expenses (i.e., shipping costs and salaries).");
				DBActions.totalExpenses(month);
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
				System.out.println("Total payments to the editors per time period");
				DBActions.totalPaymentsEditorsPerTimePeriod();
				System.out.println();
				System.out.println("Total payments to the work type per time period");
				DBActions.totalPaymentsEditorsPerWorkType();
				System.out.println();
				System.out.println("Total payments to the authors per time period");
				DBActions.totalPaymentsAuthorsPerTimePeriod();
				System.out.println();
			}
		}
		
		scanner.close();
		
	}

}
