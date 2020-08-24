/*
* Description: 
* The Admin class mainly contains methods required to generate reports.
* It has also a few other admin specific functions like processing orders and salary payment.
*/

// Required imports
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class Admin {

	// Class members
	private static dbConnection db = new dbConnection();
	private static Connection conn = db.getConnection();
	private static Scanner sc = new Scanner(System.in);
	private static ResultSet rs = null;
	private static Distributor d = new Distributor();
	private static Calendar calendar = Calendar.getInstance();

	/* 
	* Description: 
	* Number and total price of copies of each publication bought per distributor per month.
	*/
	public static void getDistributorData() {
		try {
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery(
					"select dist_id,isbn,order_month,order_year,sum(num_ordered_copies) as copies,sum(total_amount) as price from OrdersBooks where status=\"completed paid\" group by dist_id,isbn,order_month,order_year \r\n"
							+ "union all \r\n"
							+ "select dist_id,isbn,order_month,order_year,sum(num_ordered_copies) as copies,sum(total_amount) as price from OrdersPerPub where status=\"completed paid\" group by dist_id,isbn,order_month,order_year;\r\n"
							+ "");
			System.out.println("\n|    dist_id    | \t isbn \t | \t order_month \t | \t order_year \t |    copies    | \t price \t|");
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------");
			while (rs.next()) {
				System.out.println("|\t " + rs.getInt(1) + "\t | \t" + rs.getInt(2) + "\t | \t" + rs.getInt(3) + "\t | \t"
						+ rs.getInt(4) + "\t | \t" + rs.getInt(5) + "\t | \t" + rs.getFloat(6) + " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Total revenue of the publishing house.
	*/
	public static void getTotalRevenue() {
		try {
			Statement stmt = conn.createStatement();
			System.out.println("Enter month:");
			int month = Integer.parseInt(sc.nextLine());
			System.out.println("Enter year:");
			int year = Integer.parseInt(sc.nextLine());
			rs = stmt.executeQuery("select sum(amount) as Total_Revenue \r\n" + "from Transactions \r\n"
					+ "where description like \"Payment Received%\" and trans_month='" + month + "' and trans_year= '" + year
					+ "';");
			
			rs.next();
			System.out.println("\nTotal Revenue is: " + rs.getFloat(1));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Total expense of the publishing house (i.e., shipping costs and salaries).
	*/
	public static void getTotalExpenses() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter month:");
			int month = Integer.parseInt(sc.nextLine());
			System.out.println("Enter year:");
			int year = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select abs(sum(amount)) as Total_Expenses \r\n" + "from Transactions \r\n"
					+ "where trans_month= '" + month + "' and trans_year= '" + year
					+ "' and description like \"%Salary Paid%\";");
			
			rs.next();
			System.out.println("\nTotal Expenses are: " + rs.getFloat(1));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Total current number of distributors.
	* Current distributors are those whose orders are not completed or those whose orders are completed but the payment is yet to be done.
	*/
	public static void getCurrentDistributors() {
		try {
			Statement stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select count(dist_id) as Total_Current_Distributors \r\n" + "from (\r\n"
					+ "select distinct dist_id from OrdersBooks  where status in(\"accepted\",\"completed not paid\") union \r\n"
					+ "select distinct dist_id from OrdersPerPub  where status in(\"accepted\",\"completed not paid\")\r\n"
					+ ")union_dist;");

			rs.next();
			System.out.println("\nTotal Current Distributors are: " + rs.getInt(1));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Total revenue of the publishing house per city.
	*/
	public static void calcTotalRevenuePerCity() {
		try {
			Statement stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select d.city,sum(amount) as Total_Revenue \r\n" + "from Transactions t \r\n"
					+ "inner join \r\n" + "Distributors d \r\n"
					+ "on t.client_id = d.dist_id and description like \"Payment Received%\" \r\n" + "group by d.city;");
			
			System.out.println("\n|\t City \t | \t Total Revenue \t |");
			System.out.println("------------------------------------------");
			
			while (rs.next()) {
				System.out.println("|\t " + rs.getString(1) + "\t | \t" + rs.getFloat(2) +  " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Total revenue of the publishing house per location.
	*/
	public static void calcTotalRevenuePerLocation() {
		try {
			Statement stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select d.location,sum(amount) as Total_Revenue \r\n" + "from Transactions t \r\n"
					+ "inner join \r\n" + "Distributors d \r\n"
					+ "on t.client_id = d.dist_id and description like \"Payment Received%\" \r\n" + "group by d.location;");
			
			System.out.println("\n|\t Location \t | \t Total Revenue \t|");
			System.out.println("-----------------------------------------------");
			
			while (rs.next()) {
				System.out.println("|\t " + rs.getString(1) + "\t | \t" + rs.getFloat(2) + " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Total revenue of the publishing house per distributor.
	*/
	public static void calcTotalRevenuePerDistributor() {
		try {
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery("select client_id,sum(amount) as Total_Revenue \r\n" + "from Transactions\r\n"
					+ "where description like \"Payment Received%\"\r\n" + "group by client_id;");
			
			System.out.println("\n|\t Distributor \t | \t Total Revenue \t|");
			System.out.println("--------------------------------------------------");
			
			while (rs.next()) {
				System.out.println("|\t " + rs.getInt(1) + "\t | \t" + rs.getFloat(2) + " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description: 
	* Total payments to the editors and authors, per time period.
	*/
	public static void calcTotalPaymentsPerTimePeriod() {
		try {
			Statement stmt = conn.createStatement();

			rs = stmt
					.executeQuery("select abs(sum(amount)) as Total_Payment,trans_month as Month,trans_year as Year\r\n"
							+ "from Transactions \r\n" + "where \r\n"
							+ "client_id in(select emp_id from Employees where emp_type in(\"staff\",\"invited\"))\r\n"
							+ "group by trans_month,trans_year;");
			
			System.out.println("\n|\t Total Payment \t | \t Month \t | \t Year \t|");
			System.out.println("-----------------------------------------------------------");
			
			while (rs.next()) {
				System.out.println("|\t " + rs.getFloat(1) + "\t | \t" + rs.getInt(2) + "\t | \t" + rs.getInt(3) + " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Total payments to the editors and authors, per work type (book authorship, article authorship, or editorial work)
	*/
	public static void calcTotalPaymentsPerWorkType() {
		try {
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery("select abs(sum(amount)) as Total_Payment, \"Authors\" as Work_Type\r\n"
					+ "from Transactions \r\n"
					+ "where description=\"Salary Paid\" and client_id in(select emp_id from Authors)\r\n"
					+ "union all\r\n" + "select abs(sum(amount)) as Total_Payment, \"Journalists\" as Work_Type\r\n"
					+ "from Transactions \r\n"
					+ "where description=\"Salary Paid\" and client_id in(select emp_id from Journalists)\r\n"
					+ "union all\r\n" + "select abs(sum(amount)) as Total_Payment, \"Editors\" as Work_Type\r\n"
					+ "from Transactions\r\n"
					+ "where client_id in(select distinct(emp_id) from EditsChapters union select distinct(emp_id) from EditsArticles);");
			
			System.out.println("\n|\t Total Payment \t | \t Work Type \t|");
			System.out.println("--------------------------------------------------");
			
			while (rs.next()) {
				System.out.println("|\t " + rs.getFloat(1) + "\t | \t" + rs.getString(2) + " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Payment for author, editor or journalist
	*/
	public static void addPayment(int emp_id) {
		try {
			Statement stmt = conn.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();

			int todaysDate = cal.get(Calendar.DATE);
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentYear = cal.get(Calendar.YEAR);

			// Check if employee is staff or invited
			rs = stmt.executeQuery("select salary from Employees where emp_id = '" + emp_id + "';");
			float amount = 0;
			if (rs.next()) {
				amount = rs.getFloat(1);
			} else {
				System.out.println("No such employee exists.");
				return;
			}

			System.out.println("Salary for given employee is: " + amount);
			System.out.println("\nDo you want to change the amount to be paid \n1. Yes\n2. No");
			int ch = Integer.parseInt(sc.nextLine());

			int count = 0;
			int count1 = 0;
			
			// Salary payment must be reflected in the Employees tables as well as the Transactions table.
			// Transaction is added to ensure that both opertions execute successfully or neither of them execute.
			conn.setAutoCommit(false);
			
			if (ch == 1) {
				System.out.println("Enter new salary:");
				amount = Float.parseFloat(sc.nextLine());

				// Add a transaction
				count = stmt.executeUpdate(
						"insert into Transactions(trans_day,trans_month,trans_year,client_id,amount,description) values('"
								+ todaysDate + "','" + currentMonth + "','" + currentYear + "','" + emp_id + "',-'"
								+ amount + "',\"Salary Paid\");");

				// Update employee data
				count1 = stmt.executeUpdate("update Employees set pay_date= '" + sdf.format(cal.getTime())
						+ "',salary = '" + amount + "' where emp_id= '" + emp_id + "';");
			} else if (ch == 2) {
				// Add a transaction
				count = stmt.executeUpdate(
						"insert into Transactions(trans_day,trans_month,trans_year,client_id,amount,description) values('"
								+ todaysDate + "','" + currentMonth + "','" + currentYear + "','" + emp_id + "',-'"
								+ amount + "',\"Salary Paid\");");

				// Update employee data
				count1 = stmt.executeUpdate("update Employees set pay_date= '" + sdf.format(cal.getTime())
						+ "',salary = '" + amount + "'  where emp_id= '" + emp_id + "';");
			}

			if (count == 1 && count1 == 1) {
				conn.commit();
				System.out.println("Successfully executed transaction.");
			} else {
				conn.rollback();
				System.out.println("Could not execute transaction.");
			}
			
			conn.setAutoCommit(true);
		}

		catch (SQLException e) {
			System.out.println("Could not execute transaction.");
			e.printStackTrace();
		}

	}
	
	/*
	* Description:
	* Keep track of when each payment was claimed by its addressee (author, editor or journalist).
	*/
	public static void trackWriterPayment() {
		try {
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery("select emp_id,emp_name,salary,pay_date from Employees where emp_type in(\"staff\",\"invited\");");
			System.out.println("\n|\t emp_id \t | \t emp_name \t | \t salary \t | \t pay_date \t|");
			System.out.println(
					"------------------------------------------------------------------------------------------------");
			while (rs.next()) {
				System.out.println("|\t " + rs.getInt(1) + "\t | \t" + rs.getString(2) + "\t | \t" + rs.getFloat(3) + "\t | \t"
						+ rs.getString(4) + " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	* Description:
	* Process a single order. 
	* Manage status of the order.
	*/
	public static void processOrder() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter order ID to be processed:");
			int order_id = Integer.parseInt(sc.nextLine());
			String order_table = null;
			int dist_id = 0;
			float total_amount = 0;

			// Find the order and retrieve required parameters.
			// Check if order is of a book.
			rs = stmt.executeQuery(
					"select status, exp_del_date, dist_id, total_amount from OrdersBooks where order_id = '" + order_id
							+ "';");
			if (!rs.next()) {
				// Check if order is of a periodic publication if not of a book.
				rs = stmt.executeQuery(
						"select status, exp_del_date, dist_id, total_amount from OrdersPerPub where order_id = '"
								+ order_id + "';");
				if (!rs.next()) {
					// If the entered order_id is wrong.
					System.out.println("No such order exists.");
					return;
				} else {
					order_table = "OrdersPerPub";
					dist_id = rs.getInt(3);
					total_amount = rs.getFloat(4);
				}
			} else {
				order_table = "OrdersBooks";
				dist_id = rs.getInt(3);
				total_amount = rs.getFloat(4);
			}

			String status = rs.getString(1);
			String exp_del_date_str = rs.getString(2);
			Date exp_del_date = Date.valueOf(exp_del_date_str);

			// Based on the status of the order, perform the appropriate operation.
			if (status.equals("discarded")) {
				System.out.println("The order is already discarded.");
				return;
			} else if (status.equals("completed paid")) {
				System.out.println("The order is completed and paid for.");
				return;
			} else if (status.equals("completed not paid")) {
				System.out.println("The order is completed but not paid for.");
				return;
			} else {
				if (exp_del_date.before(calendar.getTime())) {
					// Discard order if expecte delivery date is already passed and subtract the order amount from distributor balance.
					// Transation is added to ensure that both the operations are executed successfully or neither are executed.
					conn.setAutoCommit(false);
					
					int count = stmt.executeUpdate("update " + order_table
							+ " set status = \"discarded\" where order_id = '" + order_id + "';");
					
					int count_dist = d.updateDistributorBalance(dist_id, "subtract", total_amount);
					
					if (count == 1 && count_dist == 1) {
						conn.commit();
						System.out.println("The order is being discarded.");
					} else {
						conn.rollback();
						System.out.println("The process request was not successful.");
					}
					conn.setAutoCommit(true);

				} else {
					// Complete order if expecte delivery date is before current date.
					int count = stmt.executeUpdate("update " + order_table
							+ " set status = \"completed not paid\" where order_id = '" + order_id + "';");
					if (count == 1) {
						System.out.println("The order is now completed.");
					}
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Add a new employee to the publishing house
	*/
	public static void addEmployee(){
		try{
			Statement stmt = conn.createStatement();
			System.out.println("Enter the employee name:");
			String emp_name =sc.nextLine();
			System.out.println("Enter the employee email:");
			String email =sc.nextLine();
			System.out.println("Enter the employee salary:");
			Float salary = Float.parseFloat(sc.nextLine());


			System.out.println("Enter the employee type (admin/staff/invited):");
			String emp_type = sc.nextLine();
			
			System.out.println("Enter the employee age:");
			int age = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the employee gender:");
			String gender =sc.nextLine();
			System.out.println("Enter the employee address:");
			String addr =sc.nextLine();
			System.out.println("Enter the employee phone number:");
			String phone =sc.nextLine();

			conn.setAutoCommit(false);

			int count = stmt.executeUpdate("insert into Employees(emp_name,email,salary,pay_date,emp_type,age,gender,addr,phone) values('" + emp_name + "','" + email + "','" + salary + "','"
								+ "0000-00-00" + "','" + emp_type + "','" + age + "','"+ gender + "','" + addr + "','" + phone +"');");
			
			int count1 = 0;
			if(count == 1){
				
				rs = stmt.executeQuery("select emp_id from Employees order by emp_id desc limit 1;");
				rs.next();
				int emp_id = rs.getInt(1);

				if(!(emp_type.equals("admin"))){
				System.out.println("Enter the creative staff type (author/journalist/editor):");
				String staff_type = sc.nextLine();

					if(staff_type.equals("author")){
						count1 = stmt.executeUpdate("insert into Authors values('" + emp_id + "');");
					} else if(staff_type.equals("editor")){
						count1 = stmt.executeUpdate("insert into Editors values('" + emp_id + "');");
					} else {
						count1 = stmt.executeUpdate("insert into Journalists values('" + emp_id + "');");
					}

					if(count1 == 1){
						conn.commit();
						System.out.println("Added employee successfully.");
					}
				} else {
					count1 = stmt.executeUpdate("insert into Admins values('" + emp_id + "');");
					if(count1 == 1){
						conn.commit();
						System.out.println("Added employee successfully.");
					}
				}

			}
			else{
				conn.rollback();
				System.out.println("Could not add employee.");
			}
			conn.setAutoCommit(false);
		}
		catch(SQLException e){
			System.out.println("Could not add employee.");
			e.printStackTrace();
		}
		catch(Exception e){
			System.out.println("Could not add employee.");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Update details the the employee.
	*/
	public static void updateEmployee(){
		try{
			Statement stmt = conn.createStatement();
			System.out.println("Enter the employee ID you want to update:");
			int emp_id = Integer.parseInt(sc.nextLine());
			rs = stmt.executeQuery("select emp_id from Employees where emp_id = '" + emp_id+ "';");
			if(!rs.next()){
				System.out.println("Employee with the given ID does not exist.");
				return;
			}
			else {
				int ch = 0;
				String ans = "y";
				while (ans.equals("y") && ch != 9) {
					System.out.println("\nWhat do you want to update?\n1. Name\n2. Email\n3. Salary\n4. Employee Type\n5. Age\n6. Gender\n7. Address \n8. Phone \n9. Exit");
					ch = Integer.parseInt(sc.nextLine());

					if (ch == 1) {
						System.out.println("Enter the name of employee:");
						String emp_name = sc.nextLine();
						int j = stmt.executeUpdate(
								"update Employees SET emp_name = '" + emp_name + "' where emp_id = '" + emp_id + "';");
						if (j != 1) {
							System.out.println("Name could not be updated.");
						} else {
							System.out.println("Name updated successfully.");
						}
					} else if (ch == 2) {
						System.out.println("Enter the email ID:");
						String email = sc.nextLine();

						int j = stmt.executeUpdate(
								"update Employees SET email = '" + email + "' where emp_id = '" + emp_id + "';");
						if (j != 1) {
							System.out.println("Email could not be updated.");
						} else {
							System.out.println("Email updated successfully.");
						}
					} else if (ch == 3) {
						System.out.println("Enter the salary:");
						Float salary = Float.parseFloat(sc.nextLine());

						int j = stmt.executeUpdate(
								"update Employees SET salary = '" + salary + "' where emp_id = '" + emp_id + "';");
						if (j != 1) {
							System.out.println("Salary could not be updated.");
						} else {
							System.out.println("Salary updated successfully.");
						}
					}
					else if (ch == 4) {
						System.out.println("Enter the employee type (admin/staff/invited):");
						String emp_type = sc.nextLine();

						int j = stmt.executeUpdate(
								"update Employees SET emp_type = '" + emp_type + "' where emp_id = '" + emp_id + "';");
						if (j != 1) {
							System.out.println("Employee type could not be updated.");
						} else {
							System.out.println("Employee type updated successfully.");
						}
					} else if (ch == 5) {
						System.out.println("Enter the age:");
						int age = Integer.parseInt(sc.nextLine());

						int j = stmt.executeUpdate(
								"update Employees SET age = '" + age + "' where emp_id = '" + emp_id + "';");
						if (j != 1) {
							System.out.println("Age could not be updated.");
						} else {
							System.out.println("Age updated successfully.");
						}
					} else if (ch == 6) {
						System.out.println("Enter the gender:");
						String gender = sc.nextLine();

						int j = stmt.executeUpdate(
								"update Employees SET gender = '" + gender + "' where emp_id = '" + emp_id + "';");
						if (j != 1) {
							System.out.println("Gender could not be updated.");
						} else {
							System.out.println("Gender updated successfully.");
						}
					} else if (ch == 7) {
						System.out.println("Enter the address:");
						String addr = sc.nextLine();

						int j = stmt.executeUpdate(
								"update Employees SET addr = '" + addr + "' where emp_id = '" + emp_id + "';");
						if (j != 1) {
							System.out.println("Address could not be updated.");
						} else {
							System.out.println("Address updated successfully.");
						}
					} else if (ch == 8) {
						System.out.println("Enter the phone number:");
						String phone = sc.nextLine();

						int j = stmt.executeUpdate(
								"update Employees SET phone = '" + phone + "' where emp_id = '" + emp_id + "';");
						if (j != 1) {
							System.out.println("Phone number could not be updated.");
						} else {
							System.out.println("Phone number updated successfully.");
						}
					}
					if (ch != 9) {
						System.out.println("\nDo you want to continue? (y/n)");
						ans = sc.nextLine();
					}
				}
			}

		}
		catch(SQLException e){
			System.out.println("Employee information could not be updated");
			e.printStackTrace();
		}
		catch(Exception e){
			System.out.println("Employee information could not be updated");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Delete employee of the publishing house
	*/
	public static void deleteEmployee(){
		try{
			int count = 0;
			Statement stmt = conn.createStatement();
			System.out.println("Enter the employee ID:");
			int emp_id = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Employees where emp_id = '" +emp_id+"';");
			if(!rs.next()){
				System.out.println("Employee with the given ID does not exist.");
				return;
			}
			else{
				 count = stmt.executeUpdate("delete from Employees where emp_id = '" +emp_id+"';");
				 if(count == 1){
					 System.out.println("Employee deleted successfully");
				 }
				 else{
					 System.out.println("Employee could not be deleted");
				 }
			}
		} catch(SQLException e){
			System.out.println("Employee could not be deleted");
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}