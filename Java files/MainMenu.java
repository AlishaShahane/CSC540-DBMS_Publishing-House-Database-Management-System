/*
* Description: 
* This class handles all the user input for his/her choice of operations.
* Bsased on the type of user, he/she will be allowed to perform authorised operations.
*/

// Required imports
import java.util.*;
import java.sql.*;

public class MainMenu {

	// Class members
	private static Scanner sc = new Scanner(System.in);
	private static dbConnection db = new dbConnection();
	private static Connection conn = db.getConnection();
	private static Statement stmt = null;
	private static ResultSet rs = null;
	private static Publication p = new Publication();
	private static Admin a = new Admin();
	private static Distributor d = new Distributor();

	public static void main(String args[]) {

		Statement stmt;
		try {
			stmt = conn.createStatement();
			int initial_ch = 0;
			while (initial_ch != 4) {
				System.out.println("\n--------------------------------------------------------------------------\n");
				System.out.println("Who would you like to login as:\n1. Admin\n2. Creative Staff\n3. Distributor\n4. Exit");
				initial_ch = Integer.parseInt(sc.nextLine());
				if (initial_ch == 1) {
					System.out.println("\nEnter employee ID:");
					int emp_id = Integer.parseInt(sc.nextLine());
		
					String emp_type = null;

					rs = stmt.executeQuery("select * from Admins where emp_id =  '" + emp_id + "';");
					if (rs.next()) {

						int ch = 0;
						emp_type = "ADMIN";
						while (ch != 39) {
							System.out.println("\n----------------------------------------------------------------------------\n");
							System.out.println("Enter choice of operation you want to perform:");

							System.out.println(
									"\n1. Add publication\n2. Update publication\n3. Delete Publication"
									+ "\n\n4. Add book\n5. Update book\n6. Delete book"
									+ "\n\n7. Add periodic publication\n8. Update periodic publication\n9. Delete periodic publication"
									+ "\n\n10. Find articles by journalist name\n11. Find articles by topic\n12. Find articles by date"
									+ "\n13. Find books by author name\n14. Find books by date\n15. Find chapters by topic"
									+ "\n\n16. Assign book to author\n17. Unassign book from author"
									+ "\n18. Assign periodic publication to journalist\n19. Unassign periodic publication from journalist"
									+ "\n\n20. Add employee\n21. Update employee\n22. Delete employee"
									+ "\n\n23. Add distributor\n24. Update distributor\n25. Delete distributor"
									+ "\n\n26. Add payment for writer/admin\n27. Track payment to writers\n28. Process an order\n29. View all orders"
									+ "\n\n30. Get distributor data\n31. Get total revenue (for a given month)\n32. Get total expenses (for a given month)\n33. Get number of current distributors\n34. Calculate total revenue per city"
									+ "\n35. Calculate total revenue per location\n36. Calculate total revenue per distributor\n37. Calculate total payment per time period"
									+ "\n38. Calculate total payment per work type\n\n39. Exit");

							ch = Integer.parseInt(sc.nextLine());

							if (ch == 1) {
								System.out.println("-------------------------------Add Publication-------------------------------");
								p.addPublication();
							} else if (ch == 2) {
								System.out.println("------------------------------Update Publication------------------------------");
								p.updatePublication();
							} else if (ch == 3) {
								System.out.println("------------------------------Delete Publication------------------------------");
								p.deletePublication();
							} else if (ch == 4) {
								System.out.println("-----------------------------------Add Book-----------------------------------");
								p.addBook();
							} else if (ch == 5) {
								System.out.println("---------------------------------Update Book---------------------------------");
								p.updateBook();
							} else if (ch == 6) {
								System.out.println("---------------------------------Delete Book---------------------------------");
								p.deleteBook();
							} else if (ch == 7) {
								System.out.println("--------------------------Add Periodic Publication---------------------------");
								p.addPeriodicPublication();
							} else if (ch == 8) {
								System.out.println("-------------------------Update Periodic Publication------------------------");
								p.updatePeriodicPublication();
							} else if (ch == 9) {
								System.out.println("-------------------------Delete Periodic Publication-------------------------");
								p.deletePeriodicPublication();
							} else if(ch == 10) {
								System.out.println("----------------------Find Articles by Journalist Name----------------------");
								System.out.println("Enter name of journalist:");
								String name = sc.nextLine();
								p.findArticlesByJournalistName(name);
							} else if(ch == 11) {
								System.out.println("---------------------------Find Articles by Topic----------------------------");
								p.findArticlesByTopic(emp_id, emp_type);
							} else if(ch == 12) {
								System.out.println("----------------------------Find Articles by Date----------------------------");
								p.findArticlesByDate(emp_id, emp_type);
							} else if(ch == 13) {
								System.out.println("-------------------------Find Books by Author Name--------------------------");
								System.out.println("Enter name of author:");
								String name = sc.nextLine();
								p.findBooksByAuthorName(name);
							} else if(ch == 14) {
								System.out.println("-----------------------------Find Books by Date-----------------------------");
								p.findBooksByDate(emp_id, emp_type);
							} else if(ch == 15) {
								System.out.println("--------------------------Find Chapters by Topic---------------------------");
								p.findChaptersByTopic(emp_id, emp_type);
							} else if (ch == 16) {
								System.out.println("---------------------------Assign Book to Author---------------------------");
								p.assignBookToAuthor();
							} else if (ch == 17) {
								System.out.println("-------------------------Unassign Book from Author-------------------------");
								p.unassignBookFromAuthor();
							} else if (ch == 18) {
								System.out.println("-----------------Assign Periodic Publication to Journalist-----------------");
								p.assignPerPubToJournalist();
							} else if (ch == 19) {
								System.out.println("---------------Unassign Periodic Publication from Journalist---------------");
								p.unassignPerPubFromJournalist();
							} else if (ch == 20) {
								System.out.println("------------------------------Add Employee--------------------------------");
								a.addEmployee();
							} else if (ch == 21) {
								System.out.println("------------------------------Update Employee------------------------------");
								a.updateEmployee();
							} else if (ch == 22) {
								System.out.println("-------------------------------Delete Employee-------------------------------");
								a.deleteEmployee();
							} else if (ch == 23) {
								System.out.println("-------------------------------Add Distributor-------------------------------");
								d.addDistributor();
							} else if (ch == 24) {
								System.out.println("------------------------------Update Distributor------------------------------");
								d.updateDistributor();
							} else if (ch == 25) {
								System.out.println("------------------------------Delete Distributor------------------------------");
								d.deleteDistributor();
							} else if (ch == 26) {
								System.out.println("------------------------Add Payment for Writer/Admin-------------------------");
								System.out.println("Enter employee ID for whom you want to add payment:");
								int emp_id_pay = Integer.parseInt(sc.nextLine());
								a.addPayment(emp_id_pay);
							} else if (ch == 27) {
								System.out.println("-------------------------Track Payment to Writers---------------------------");
								a.trackWriterPayment();
							} else if (ch == 28) {
								System.out.println("------------------------------Process an Order------------------------------");
								a.processOrder();
							} else if (ch == 29) {
								System.out.println("--------------------------------View All Orders------------------------------");
								d.viewOrders(emp_type, emp_id);
							} else if(ch == 30) {
								System.out.println("-----------------------------Get Distributor Data-----------------------------");
								a.getDistributorData();
							} else if(ch == 31) {
								System.out.println("------------------------------Get Total Revenue-----------------------------");
								a.getTotalRevenue();
							} else if(ch == 32) {
								System.out.println("----------------------------Get Total Expenses------------------------------");
								a.getTotalExpenses();
							} else if(ch == 33) {
								System.out.println("---------------------Get Number of Current Distributors---------------------");
								a.getCurrentDistributors();
							} else if(ch == 34) {
								System.out.println("----------------------Calculate Total Revenue per City----------------------");
								a.calcTotalRevenuePerCity();
							} else if(ch == 35) {
								System.out.println("--------------------Calculate Total Revenue per Location--------------------");
								a.calcTotalRevenuePerLocation();
							} else if(ch == 36) {
								System.out.println("-------------------Calculate Total Revenue per Distributor------------------");
								a.calcTotalRevenuePerDistributor();
							} else if(ch == 37) {
								System.out.println("-------------------Calculate Total Payment per Time Priod-------------------");
								a.calcTotalPaymentsPerTimePeriod();
							} else if(ch == 38) {
								System.out.println("--------------------Calculate Total Payment per Work Type-------------------");
								a.calcTotalPaymentsPerWorkType();
							} 
						}
					} else {
						System.out.println("No such admin exists.");
					}

				} else if (initial_ch == 2) {
					System.out.println("\nEnter employee ID:");
					int emp_id = Integer.parseInt(sc.nextLine());
					String emp_type = null;

					rs = stmt.executeQuery("select * from Authors where emp_id =  '" + emp_id + "';");
					if (rs.next()) {
						emp_type = "AUTHOR";
						int ch = 0;
						while (ch != 10) {
							System.out.println("\n--------------------------------------------------------------------------\n");
							System.out.println("Enter choice of operation you want to perform:");
							System.out.println(
									"\n1. Add chapter\n2. Update chapter\n3. Delete chapter\n4. Assign chapter to editor\n5. Unassign chapter from editor\n6. Find books by author name\n7. Find chapters by topic\n8. Find books by date\n9. Update text of chapter\n10. Exit");

							ch = Integer.parseInt(sc.nextLine());
							if (ch == 1) {
								System.out.println("-------------------------------Add Chapter--------------------------------");
								p.addChapter(emp_id);
							} else if (ch == 2) {
								System.out.println("------------------------------Update Chapter------------------------------");
								p.updateChapter(emp_id, emp_type);
							} else if (ch == 3) {
								System.out.println("------------------------------Delete Chapter------------------------------");
								p.deleteChapter(emp_id);
							} else if (ch == 4) {
								System.out.println("--------------------------Assign Chapter To Editor-------------------------");
								p.assignChapterToEditor(emp_id);
							} else if (ch == 5) {
								System.out.println("------------------------Unassign Chapter From Editor-----------------------");
								p.unassignChapterFromEditor(emp_id);
							} else if (ch == 6) {
								System.out.println("------------------------Find Books by Author Name-------------------------");
								rs = stmt.executeQuery(
										"select emp_name from Employees where emp_id =  '" + emp_id + "';");
								String emp_name = null;
								if (rs.next()) {
									emp_name = rs.getString(1);
								}
								p.findBooksByAuthorName(emp_name);
							} else if (ch == 7) {
								System.out.println("-------------------------Find Chapters by Topic--------------------------");
								p.findChaptersByTopic(emp_id, emp_type);
							} else if(ch == 8) {
								System.out.println("----------------------------Find books by date----------------------------");
								p.findBooksByDate(emp_id, emp_type);
							} else if(ch == 9) {
								System.out.println("--------------------------Update Text of Chapter--------------------------");
								p.updateTextOfChapter(emp_id, emp_type);
							}
						}
					} 

					rs = stmt.executeQuery("select * from Journalists where emp_id =  '" + emp_id + "';");
					if (rs.next()) {
						emp_type = "JOURNALIST";
						int ch = 0;
						while (ch != 10) {
							System.out.println("\n--------------------------------------------------------------------------\n");
							System.out.println("Enter choice of operation you want to perform:");
							System.out.println(
									"\n1. Add article\n2. Update article\n3. Delete article\n4. Assign article to editor"
									+ "\n5. Unassign article from editor\n6. Find Articles by Journalist Name\n7. Update text of article"
									+ "\n8. Find articles by topic\n9. Find articles by date\n10. Exit");

							ch = Integer.parseInt(sc.nextLine());
							if (ch == 1) {
								System.out.println("--------------------------------Add Article--------------------------------");
								p.addArticle(emp_id);
							} else if (ch == 2) {
								System.out.println("-------------------------------Update Article-------------------------------");
								p.updateArticle(emp_id, emp_type);
							} else if (ch == 3) {
								System.out.println("-------------------------------Delete Article-------------------------------");
								p.deleteArticle(emp_id);
							} else if (ch == 4) {
								System.out.println("--------------------------Assign Article To Editor--------------------------");
								p.assignArticleToEditor(emp_id);
							} else if (ch == 5) {
								System.out.println("------------------------Unassign Article From Editor------------------------");
								p.unassignArticleFromEditor(emp_id);
							} else if (ch == 6) {
								System.out.println("----------------------Find Articles by Journalist Name----------------------");
								rs = stmt.executeQuery(
										"select emp_name from Employees where emp_id =  '" + emp_id + "';");
								String emp_name = null;
								if (rs.next()) {
									emp_name = rs.getString(1);
								}
								p.findArticlesByJournalistName(emp_name);
							} else if (ch == 7) {
								System.out.println("----------------------------Update Text of Article----------------------------");
								p.updateTextOfArticle(emp_id, emp_type);
							} else if (ch == 8) {
								System.out.println("----------------------------Find Articles by Topic----------------------------");
								p.findArticlesByTopic(emp_id, emp_type);
							} else if (ch == 9) {
								System.out.println("----------------------------Find Articles by Date----------------------------");
								p.findArticlesByDate(emp_id, emp_type);
							}
						}
					}

					rs = stmt.executeQuery("select * from Editors where emp_id =  '" + emp_id + "';");
					if (rs.next()) {
						emp_type = "EDITOR";

						int ch = 0;
						while (ch != 9) {
							System.out.println("\n--------------------------------------------------------------------------\n");
							System.out.println("Enter choice of operation you want to perform:");
							System.out.println(
									"\n1. Update article\n2. Update chapter\n3. Update text of article\n4. Update text of chapter\n5. View assigned work"
									+ "\n6. Find articles by topic\n7. Find articles by date\n8. Find chapters by topic\n9. Exit");
							ch = Integer.parseInt(sc.nextLine());
							if (ch == 1) {
								System.out.println("--------------------------------Update Article-------------------------------");
								p.updateArticle(emp_id, emp_type);
							} else if (ch == 2) {
								System.out.println("--------------------------------Update Chapter-------------------------------");
								p.updateChapter(emp_id, emp_type);
							} else if (ch == 3) {
								System.out.println("---------------------------Update text of article---------------------------");
								p.updateTextOfArticle(emp_id, emp_type);
							} else if (ch == 4) {
								System.out.println("---------------------------Update text of chapter---------------------------");
								p.updateTextOfChapter(emp_id, emp_type);
							} else if (ch == 5) {
								System.out.println("-----------------------------View Assigned Work-----------------------------");
								p.viewAssignedEditorialWork(emp_id);
							} else if(ch == 6) {
								System.out.println("---------------------------Find articles by topic---------------------------");
								p.findArticlesByTopic(emp_id, emp_type);
							} else if(ch == 7) {
								System.out.println("---------------------------Find articles by date---------------------------");
								p.findArticlesByDate(emp_id, emp_type);
							} else if(ch == 8) {
								System.out.println("--------------------------Find chapters by topic--------------------------");
								p.findChaptersByTopic(emp_id, emp_type);
							}
						}
					} if (emp_type == null) {
						System.out.println("No such creative staff exists.");
					} 
				} else if (initial_ch == 3) {
					System.out.println("\nEnter distributor ID:");
					int dist_id = Integer.parseInt(sc.nextLine());
					String emp_type = "DISTRIBUTOR";
					rs = stmt.executeQuery("select * from Distributors where dist_id =  '" + dist_id + "';");
					if (rs.next()) {
						int ch = 0;
						while (ch != 6) {
							System.out.println("\n--------------------------------------------------------------------------\n");
							System.out.println("Enter choice of operation you want to perform:");
							System.out
									.println("1. Order book\n2. Order periodic publication\n3. View orders\n4. Pay for an order\n5. See balance\n6. Exit");
							ch = Integer.parseInt(sc.nextLine());
							if (ch == 1) {
								System.out.println("---------------------------------Order Book-------------------------------");
								d.orderBooks(dist_id);
							} else if (ch == 2) {
								System.out.println("-----------------------Order Periodic Publication--------------------------");
								d.orderPerPub(dist_id);
							} else if (ch == 3) {
								System.out.println("---------------------------------View Orders-------------------------------");
								d.viewOrders(emp_type, dist_id);
							} else if (ch == 4) {
								System.out.println("------------------------------Pay for an order-----------------------------");
								d.paymentByDistributor(dist_id);
							} else if (ch == 5) {
								System.out.println("-------------------------------See Balance--------------------------------");
								d.viewMyBalance(dist_id);
							}
						}
					} else {
						System.out.println("No such distributor exists.");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
