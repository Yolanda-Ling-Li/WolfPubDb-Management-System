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
		op3Tasks.add("Enter new distributor; update distributor information; delete a distributor");
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
				String title = scanner.next();
				System.out.println("date: ");
				String date = scanner.next();
				
				System.out.println(title + " " + date);
			}
			if (selectedTask == 2) {
				
			}
			if (selectedTask == 3) {
				
			}
			if (selectedTask == 4) {
				
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
				
			}
			if (selectedTask == 2) {
				
			}
			if (selectedTask == 3) {
				
			}
		}
		
		if (selectedOperation == 4) {
			if (selectedTask == 1) {
				
			}
			if (selectedTask == 2) {
				
			}
			if (selectedTask == 3) {
				
			}
			if (selectedTask == 4) {
				
			}
			if (selectedTask == 5) {
				
			}
			if (selectedTask == 6) {
				
			}
		}
		
		scanner.close();
		
	}

}
