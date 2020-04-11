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
		op2Tasks.add("Enter/update an article or chapter: title, author's name, topic, and date");
		op2Tasks.add("Enter/update text of an article");
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
				String title = scanner.nextLine();
				System.out.println("date: ");
				String date = scanner.nextLine();
				
				DBActions.addPublication(title, date);
				
			}
			if (selectedTask == 2) {
				System.out.println("person_id: ");
				int person_id = Integer.parseInt(scanner.nextLine());
				System.out.println("Select the field you want to update 1. name 2. type 3. gender 4. age 5. email 6. phone_num 7. address");
				int selectedField = Integer.parseInt(scanner.nextLine());
				switch(selectedField) {
				case 1:
					System.out.println("name: ");
					String name = scanner.nextLine();
					DBActions.updatePerson(person_id, name, null, null, null, null, null, null);
					break;
				case 2:
					System.out.println("type: ");
					String type = scanner.nextLine();
					DBActions.updatePerson(person_id, null, type, null, null, null, null, null);
					break;
				case 3:
					System.out.println("gender: ");
					String gender = scanner.nextLine();
					DBActions.updatePerson(person_id, null, null, gender, null, null, null, null);
					break;
				case 4: 
					System.out.println("age: ");
					int age = Integer.parseInt(scanner.nextLine());
					DBActions.updatePerson(person_id, null, null, null, age, null, null, null);
					break;
				case 5:
					System.out.println("email: ");
					String email = scanner.nextLine();
					DBActions.updatePerson(person_id, null, null, null, null, email, null, null);
					break;
				case 6:
					System.out.println("phone No.: ");
					String phoneNo = scanner.nextLine();
					DBActions.updatePerson(person_id, null, null, null, null, null, phoneNo, null);
					break;			
				case 7:
					System.out.println("address");
					String address = scanner.nextLine();
					DBActions.updatePerson(person_id, null, null, null, null, null, null, address);
					break;			
					
				}
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
		}
		
		if (selectedOperation == 2) {
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
