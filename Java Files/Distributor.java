/*
* Description: 
* This class contains all operations relevant to distributors.
*/

// Required imports
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.time.LocalDate;

public class Distributor {

	// Class members
	private static dbConnection db = new dbConnection();
	private static Connection conn = db.getConnection();
	private static Scanner sc = new Scanner(System.in);
	private static ResultSet rs = null;

	/*
	* Description: 
	* Add a new distributor.
	* This operation is performed by an admin.
	*/
	public static void addDistributor() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter distributor name (cannot be empty):");
			String dist_name = sc.nextLine();
			System.out.println("Enter distributor type (cannot be empty):");
			String dist_type = sc.nextLine();
			System.out.println("Enter location (cannot be empty):");
			String location = sc.nextLine();
			System.out.println("Enter city (cannot be empty):");
			String city = sc.nextLine();
			System.out.println("Enter street address (cannot be empty):");
			String street_addr = sc.nextLine();
			System.out.println("Enter phone (cannot be empty):");
			String phone = sc.nextLine();
			System.out.println("Enter person of contact:");
			String person_of_contact = sc.nextLine();

			int i = stmt.executeUpdate(
					"insert into Distributors (dist_name, dist_type, location, city, street_addr, phone, person_of_contact, balance_due) values ('"
							+ dist_name + "','" + dist_type + "','" + location + "','" + city + "','" + street_addr
							+ "','" + phone + "','" + person_of_contact + "',0);");
			if (i > 0) {
				System.out.println("Distributor added successfully.");
			} else {
				System.out.println("Distributor failed to add.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Update information of an existing distributor.
	* This operation is performed by an admin.
	*/
	public static void updateDistributor() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ID of the Distributor:");
			int dist_id = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Distributors where dist_id = '" + dist_id + "';");
			if (!rs.next()) {
				System.out.println("A distributor with given ID does not exist.");
				return;
			}

			int ch = 0;
			String ans = "y";
			while (ans.equals("y") && ch != 8) {
				System.out.println(
						"\nWhat do you want to update?\n1. Distributor Name\n2. Type\n3.Location\n4. City\n5. Street Address\n6. Phone\n7. Person of Contact\n8. Exit");
				ch = Integer.parseInt(sc.nextLine());

				if (ch == 1) {
					System.out.println("Enter the name:");
					String dist_name = sc.nextLine();

					if (dist_name.isEmpty()) {
						System.out.println("Name cannot be empty.");
						break;
					}

					int count = stmt.executeUpdate("update Distributors set dist_name = '" + dist_name
							+ "' where dist_id = '" + dist_id + "';");
					if (count == -1) {
						System.out.println("Could not update name.");
					} else {
						System.out.println("Name updated successfully.");
					}
				} else if (ch == 2) {
					System.out.println("Enter the type:");
					String dist_type = sc.nextLine();

					if (dist_type.isEmpty()) {
						System.out.println("Type cannot be empty.");
						break;
					}

					int count = stmt.executeUpdate("update Distributors set dist_type = '" + dist_type
							+ "' where dist_id = '" + dist_id + "';");
					if (count == -1) {
						System.out.println("Could not update type.");
					} else {
						System.out.println("Type updated successfully.");
					}
				} else if (ch == 3) {
					System.out.println("Enter the location:");
					String location = sc.nextLine();

					if (location.isEmpty()) {
						System.out.println("Location cannot be empty.");
						break;
					}

					int count = stmt.executeUpdate(
							"update Distributors set location = '" + location + "' where dist_id = '" + dist_id + "';");
					if (count == -1) {
						System.out.println("Could not update location.");
					} else {
						System.out.println("Location updated successfully.");
					}
				} else if (ch == 4) {
					System.out.println("Enter the city:");
					String city = sc.nextLine();

					if (city.isEmpty()) {
						System.out.println("City cannot be empty.");
						break;
					}

					int count = stmt.executeUpdate(
							"update Distributors set city = '" + city + "' where dist_id = '" + dist_id + "';");
					if (count == -1) {
						System.out.println("Could not update city.");
					} else {
						System.out.println("City updated successfully.");
					}
				} else if (ch == 5) {
					System.out.println("Enter the street address:");
					String street_address = sc.nextLine();

					if (street_address.isEmpty()) {
						System.out.println("Street address cannot be empty.");
						break;
					}

					int count = stmt.executeUpdate("update Distributors set street_addr = '" + street_address
							+ "' where dist_id = '" + dist_id + "';");
					if (count == -1) {
						System.out.println("Could not update street address.");
					} else {
						System.out.println("Street address updated successfully.");
					}
				} else if (ch == 6) {
					System.out.println("Enter the phone number:");
					String phone = sc.nextLine();

					if (phone.isEmpty()) {
						System.out.println("Phone cannot be empty.");
						break;
					}

					int count = stmt.executeUpdate(
							"update Distributors set phone = '" + phone + "' where dist_id = '" + dist_id + "';");
					if (count == -1) {
						System.out.println("Could not update phone number.");
					} else {
						System.out.println("Phone number updated successfully.");
					}
				} else if (ch == 7) {
					System.out.println("Enter the person of contact:");
					String person_of_contact = sc.nextLine();

					int count = stmt.executeUpdate("update Distributors set person_of_contact = '" + person_of_contact
							+ "' where dist_id = '" + dist_id + "';");
					if (count == -1) {
						System.out.println("Could not update person of contact.");
					} else {
						System.out.println("Person of contact updated successfully.");
					}
				}
				if (ch != 8) {
					System.out.println("\nDo you want to continue? (y/n)");
					ans = sc.nextLine();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Delete an existing distributor.
	* This operation is performed by an admin.
	*/
	public static void deleteDistributor() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter distributor ID:");
			int dist_id = Integer.parseInt(sc.nextLine());
			
			rs = stmt.executeQuery("select * from Distributors where dist_id = '" + dist_id + "';");
			if (!rs.next()) {
				System.out.println("No such distributor exists.");
				return;
			}

			int i = stmt.executeUpdate("delete from Distributors where dist_id = '" + dist_id + "';");
			if (i > 0) {
				System.out.println("Distributor deleted successfully.");
			} else {
				System.out.println("Distributor failed to delete.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Update distributor balance.
	* This operation is performed on several occasions.
	* 1. After an order is placed.
	* 2. After payment of an order.
	* 3. After discarding nay order.
	*/
	public static int updateDistributorBalance(int dist_id, String type, float amount) {
		try {
			Statement stmt = conn.createStatement();
			int count = 0;

			if (type.equals("add")) {
				count = stmt.executeUpdate("update Distributors set balance_due = balance_due + '" + amount
						+ "' where dist_id = '" + dist_id + "';");
			} else if (type.equals("subtract")) {
				count = stmt.executeUpdate("update Distributors set balance_due = balance_due - '" + amount
						+ "' where dist_id = '" + dist_id + "';");
			}

			if (count == 1) {
				System.out.println("Distributor balance updated successfully.");
				return 1;
			} else {
				System.out.println("Could not update balance of distributor.");
				return 0;
			}
		} catch (SQLException e) {
			System.out.println("Could not update balance of distributor.");
			e.printStackTrace();
		}
		return 0;
	}

	/*
	* Description: 
	* Payment by a distributor for a particular order.
	* This operation is performed by distributor.
	*/
	public static void paymentByDistributor(int dist_id) {
		try {
			Statement stmt = conn.createStatement();
			Calendar cal = Calendar.getInstance();
			int todaysDate = cal.get(Calendar.DATE);
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentYear = cal.get(Calendar.YEAR);
			int emp_id = 0;
			String order_table = null;
			float total_amount = 0;

			rs = stmt.executeQuery("select emp_id from Employees where emp_type = \"admin\" limit 1;");
			while (rs.next()) {
				emp_id = rs.getInt(1);
			}

			System.out.println("Enter the order ID for which the payment must be done:");
			int order_id = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select order_id, status, total_amount,dist_id from OrdersBooks where order_id = '"
					+ order_id + "';");

			if (!rs.next()) {
				rs = stmt.executeQuery(
						"select order_id, status, total_amount,dist_id from OrdersPerPub where order_id = '" + order_id
								+ "';");
				if (!rs.next()) {
					System.out.println("No such order exists.");
					return;
				} else {
					if (rs.getInt(4) != dist_id) {
						System.out.println("This is not your order to pay.");
						return;
					} else if (rs.getString(2).equals("completed not paid")) {
						order_table = "OrdersPerPub";
						total_amount = rs.getInt(3);
					} else if (rs.getString(2).equals("completed paid")) {
						System.out.println("Order is already paid for.");
						return;
					} else if (rs.getString(2).equals("discarded")) {
						System.out.println("Cannot pay for order which is discarded.");
						return;
					} else {
						System.out.println("Cannot pay for order which is not completed.");
						return;
					}
				}
			} else {
				if (rs.getInt(4) != dist_id) {
					System.out.println("This is not your order to pay.");
					return;
				} else if (rs.getString(2).equals("completed not paid")) {
					order_table = "OrdersBooks";
					total_amount = rs.getInt(3);
				} else if (rs.getString(2).equals("completed paid")) {
					System.out.println("Order is already paid for.");
					return;
				} else if (rs.getString(2).equals("discarded")) {
						System.out.println("Cannot pay for order which is discarded.");
						return;
				} else {
					System.out.println("Cannot pay for order which is not completed.");
					return;
				}
			}

			System.out.println("The amount which the distributor is paying: " + total_amount);

			// Since payment for an order is reflected in multiple tables, it is performed as a transaction.
			// This way, either all or none of the queries are executed.
			conn.setAutoCommit(false);

			// Add into pays
			int count_pays = stmt.executeUpdate("insert into Pays (dist_id, emp_id, order_id) values ('" + dist_id
					+ "','" + emp_id + "','" + order_id + "');");

			// Add transaction
			int count_trans = stmt.executeUpdate(
					"insert into Transactions (trans_day, trans_month, trans_year, client_id, amount, description ) values ('"
							+ todaysDate + "','" + currentMonth + "','" + currentYear + "','" + dist_id + "','"
							+ total_amount + "', \"Payment Received for \"'" + order_id + "');");

			// Update order status
			int count_orders = stmt.executeUpdate(
					"update " + order_table + " set status = \"completed paid\" where order_id = " + order_id + ";");

			// Update balance of distributor
			int count_dist = updateDistributorBalance(dist_id, "subtract", total_amount);

			if (count_trans == 1 && count_dist == 1 && count_pays == 1 && count_orders == 1) {
				conn.commit();
				System.out.println("Payment successful.");
			} else {
				conn.rollback();
				System.out.println("Payment not successful.");
			}
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			System.out.println("Payment not successful.");
			// e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Payment not successful.");
			// e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Function for a distributor to place an order for a book.
	*/
	public static void orderBooks(int dist_id) {
		try {
			Statement stmt = conn.createStatement();
			Calendar cal = Calendar.getInstance();
			int todaysDate = cal.get(Calendar.DATE);
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentYear = cal.get(Calendar.YEAR);
			float price_per_copy = 0;
			System.out.println("Enter title of book:");
			String title = sc.nextLine();

			rs = stmt.executeQuery(
					"select B.isbn, B.edition, P.title from Books B inner join Publications P on B.isbn = P.isbn where title like \"%"
							+ title + "%\";");

			if (!rs.next()) {
				System.out.println("No periodic publication available of the given title.");
				return;
			} else {
				System.out.println("Following books are available of the given title");
				System.out.println("------------------------------------------------");
				System.out.println("| isbn \t | \t edition \t | \t title |");
				System.out.println("------------------------------------------------");
				System.out.println("| " + rs.getInt(1) + "\t | \t" + rs.getInt(2) + "\t | \t" + rs.getString(3) + " |");
				while (rs.next()) {
					System.out.println(
							"| " + rs.getInt(1) + "\t | \t" + rs.getInt(2) + "\t | \t" + rs.getString(3) + " |");
				}
			}

			System.out.println("------------------------------------------------\n");
			System.out.println("Enter the isbn:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the edition:");
			int edition = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the required copies:");
			int num_ordered_copies = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the expected delivery date (YYYY-MM-DD):");
			String exp_del_date_str = sc.nextLine();
			Date exp_del_date = Date.valueOf(exp_del_date_str);
			if (exp_del_date.before(cal.getTime())) {
				System.out.println("Please enter a future date.");
				return;
			}

			rs = stmt.executeQuery(
					"select B.price, P.title from Books B inner join Publications P on B.isbn = P.isbn where B.isbn = '"
							+ isbn + "' and B.edition = '" + edition + "';");

			while (rs.next()) {
				price_per_copy = rs.getFloat(1);
				title = rs.getString(2);
			}

			float shipping_cost = price_per_copy * num_ordered_copies * 10 / 100;
			float total_amount = (price_per_copy * num_ordered_copies) + shipping_cost;

			System.out.println("\nThe total amount for your order is: " + total_amount);
			
			// Placing an order is reflected in two tables. Hence this operation is performed as a transaction.
			// This ensures that either all or none of the queries are execueted.
			conn.setAutoCommit(false);
			
			int count = stmt.executeUpdate(
					"insert into OrdersBooks (isbn, edition, title, dist_id, order_day, order_month, order_year, exp_del_date, status, num_ordered_copies, price_per_copy, shipping_cost, total_amount) values ('"
							+ isbn + "','" + edition + "','" + title + "','" + dist_id + "','" + todaysDate + "','"
							+ currentMonth + "','" + currentYear + "','" + exp_del_date + "', \"accepted\", '"
							+ num_ordered_copies + "','" + price_per_copy + "','" + shipping_cost + "','" + total_amount
							+ "');");

			int count_dist = updateDistributorBalance(dist_id, "add", total_amount);

			if (count == 1 && count_dist == 1) {
				conn.commit();
				System.out.println("Order placed successfully.");
			} else {
				conn.rollback();
				System.out.println("Order could not be placed.");
			}
			
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Function for a distributor to place an order for a periodic publication.
	*/
	public static void orderPerPub(int dist_id) {
		try {
			Statement stmt = conn.createStatement();
			Calendar cal = Calendar.getInstance();
			int todaysDate = cal.get(Calendar.DATE);
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentYear = cal.get(Calendar.YEAR);
			float price_per_copy = 0;
			System.out.println("Enter title of Periodic Publication:");
			String title = sc.nextLine();

			rs = stmt.executeQuery(
					"select PP.isbn, PP.issue_wm, PP.issue_year, P.title from PeriodicPublications PP inner join Publications P on PP.isbn = P.isbn where title like \"%"
							+ title + "%\";");

			if (!rs.next()) {
				System.out.println("No periodic publication available of the given title.");
				return;
			} else {
				System.out.println("Following periodic publications are available of the given title");
				System.out.println("-----------------------------------------------------------");
				System.out.println("| isbn \t | \t issue_wm \t | \t issue_year \t | \t title |");
				System.out.println("-----------------------------------------------------------");

				System.out.println("| " + rs.getInt(1) + "\t | \t" + rs.getInt(2) + "\t | \t" + rs.getInt(3) + "\t | \t"
						+ rs.getString(4) + " |");
				while (rs.next()) {
					System.out.println("| " + rs.getInt(1) + "\t | \t" + rs.getInt(2) + "\t | \t" + rs.getInt(3)
							+ "\t | \t" + rs.getString(4) + " |");
				}
			}

			System.out.println("-----------------------------------------------------------\n ");
			System.out.println("Enter the isbn:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue week/month:");
			int issue_wm = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue year:");
			int issue_year = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the required copies:");
			int num_ordered_copies = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the expected delivery date (YYYY-MM-DD):");
			String exp_del_date_str = sc.nextLine();
			Date exp_del_date = Date.valueOf(exp_del_date_str);
			if (exp_del_date.before(cal.getTime())) {
				System.out.println("Please enter a future date.");
				return;

			}

			rs = stmt.executeQuery(
					"select PP.price, P.title from PeriodicPublications PP inner join Publications P on PP.isbn = P.isbn where PP.isbn =  '"
							+ isbn + "' and PP.issue_wm = '" + issue_wm + "' and PP.issue_year = '" + issue_year
							+ "';");

			while (rs.next()) {
				price_per_copy = rs.getFloat(1);
				title = rs.getString(2);
			}

			float shipping_cost = price_per_copy * num_ordered_copies / 10;
			float total_amount = (price_per_copy * num_ordered_copies) + shipping_cost;

			System.out.println("\nThe total amount for your order is: " + total_amount);

			// Placing an order is reflected in two tables. Hence this operation is performed as a transaction.
			// This ensures that either all or none of the queries are execueted.
			conn.setAutoCommit(false);
			
			int count = stmt.executeUpdate(
					"insert into OrdersPerPub (isbn, issue_wm,issue_year, title, dist_id, order_day, order_month, order_year, exp_del_date, status, num_ordered_copies, price_per_copy, shipping_cost, total_amount) values ('"
							+ isbn + "','" + issue_wm + "','" + issue_year + "','" + title + "','" + dist_id + "','"
							+ todaysDate + "','" + currentMonth + "','" + currentYear + "','" + exp_del_date
							+ "', \"accepted\", '" + num_ordered_copies + "','" + price_per_copy + "','" + shipping_cost
							+ "','" + total_amount + "');");

			int count_dist = updateDistributorBalance(dist_id, "add", total_amount);

			if (count == 1 && count_dist == 1) {
				conn.commit();
				System.out.println("Order placed successfully.");
			} else {
				conn.rollback();
				System.out.println("Order could not be placed.");
			}
			
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Function for a distributor to see his balance due.
	*/
	public static void viewMyBalance(int dist_id) {
		try {
			Statement stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select balance_due from Distributors where dist_id = '" + dist_id + "';");
			if (rs.next()) {
				System.out.println("Your current balance is: " + rs.getFloat(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Function to view all order wrt the employee.
	*/
	public static void viewOrders(String emp_type, int emp_id) {
		try {
			Statement stmt = conn.createStatement();
			if (emp_type.equals("ADMIN")) {
				rs = stmt.executeQuery(
						"select order_id,title,dist_id,exp_del_date,status,num_ordered_copies, total_amount from OrdersBooks\r\n"
								+ "union all \r\n"
								+ "select order_id,title,dist_id,exp_del_date,status,num_ordered_copies,total_amount from OrdersPerPub;");
				System.out.println(
						"\n|    order_id    | \t title \t | \t dist_id \t | \t exp_del_date \t |    status    |   num_ordered_copies   | \t total_amount \t|");
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------");
				while (rs.next()) {
					System.out.println("|\t " + rs.getInt(1) + "\t | \t" + rs.getString(2) + "\t | \t" + rs.getInt(3)
							+ "\t | \t" + rs.getString(4) + "\t | \t" + rs.getString(5) + "\t | \t" + rs.getInt(6) +"\t | \t" + rs.getFloat(7)
							+ " \t|");
				}
			}

			else if (emp_type.equals("DISTRIBUTOR")) {
				rs = stmt.executeQuery(
						"select order_id,title,exp_del_date,status,num_ordered_copies,total_amount from OrdersBooks where dist_id = '"
								+ emp_id + "'\r\n" + "union all \r\n"
								+ "select order_id,title,exp_del_date,status,num_ordered_copies,total_amount from OrdersPerPub where dist_id = '"
								+ emp_id + "';");
				System.out.println(
						"\n|    order_id    | \t title \t | \t dist_id \t | \t exp_del_date \t |    status    |    num_ordered_copies   |\t total_amount \t|");
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------");
				while (rs.next()) {
					System.out.println("|\t " + rs.getInt(1) + "\t | \t" + rs.getString(2) + "\t | \t" + rs.getString(3)
							+ "\t | \t" + rs.getString(4) + "\t | \t"  + rs.getInt(5) + "\t | \t" + rs.getFloat(6) + " \t|");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}