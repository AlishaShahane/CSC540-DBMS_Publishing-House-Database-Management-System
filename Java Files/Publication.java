/*
* Description: 
* This class has all methods relevant to publications.
* They include all methods concerning books, chapters, periodic publications and articles.
*/

// Required imports
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.*;

public class Publication {

	// Class members
	private static dbConnection db = new dbConnection();
	private static Connection conn = db.getConnection();
	private static Scanner sc = new Scanner(System.in);
	private static ResultSet rs = null;

	/*
	* Description:
	* Insert a new publicaiton in the publishing house.
	*/
	public static void addPublication() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter isbn:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter title:");
			String title = sc.nextLine();
			System.out.println("Enter genre:");
			String genre = sc.nextLine();

			int i = stmt
					.executeUpdate("insert into Publications values ('" + isbn + "','" + title + "','" + genre + "');");
			if (i > 0) {
				System.out.println("Publication added successfully");
			} else {
				System.out.println("Publication failed to add");
			}
		} catch (SQLException e) {
			System.out.println("Publication failed to add");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Update information of existing publication.
	*/
	public static void updatePublication() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter isbn of the publication you want to update:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Publications where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Publication for this ISBN does not exist");
			} else {
				int ch = 0;
				String ans = "y";
				while (ans.equals("y") && ch != 3) {
					System.out.println("\nWhat do you want to update?\n1. Title\n2. Genre\n3. Exit");
					ch = Integer.parseInt(sc.nextLine());

					if (ch == 1) {
						System.out.println("Enter the value for title:");
						String title = sc.nextLine();

						if (title.isEmpty()) {
							System.out.println("Title cannot be empty.");
							break;
						}
						int j = stmt.executeUpdate(
								"update Publications SET title = '" + title + "' where isbn = '" + isbn + "';");
						if (j == -1) {
							System.out.println("Title could not be updated.");
						} else {
							System.out.println("Title updated successfully.");
						}
					} else if (ch == 2) {
						System.out.println("Enter the value for genre:");
						String genre = sc.nextLine();

						int j = stmt.executeUpdate(
								"update Publications SET genre = '" + genre + "' where isbn = '" + isbn + "';");
						if (j == -1) {
							System.out.println("Genre could not be updated.");
						} else {
							System.out.println("Genre updated successfully.");
						}
					}
					if (ch != 3) {
						System.out.println("\nDo you want to continue? (y/n)");
						ans = sc.nextLine();
					}
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
	* Delete a publication.
	*/
	public static void deletePublication() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter isbn");
			int isbn = Integer.parseInt(sc.nextLine());
			
			rs = stmt.executeQuery("select * from Publications where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("No such publication exists.");
				return;
			}
			
			int i = stmt.executeUpdate("delete from Publications where isbn = '" + isbn + "';");
			if (i > 0) {
				System.out.println("Publication deleted successfully.");
			} else {
				System.out.println("Publication failed to delete.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	* Description:
	* Add a new book provided there is a corresponding entry in Publication table.
	*/
	public static void addBook() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Book:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Publications where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("No publication with the given isbn exists.");
				return;
			}

			rs = stmt.executeQuery("select * from PeriodicPublications where isbn = '" + isbn + "';");
			if (rs.next()) {
				System.out.println("A periodic publication with same isbn exists.");
				return;
			}

			System.out.println("Enter the edition of the Book:");
			int edition = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Books where isbn = '" + isbn + "' and edition = '" + edition + "';");
			if (rs.next()) {
				System.out.println("A book with same isbn exists.");
				return;
			}

			System.out.println("Enter the edition date of the Book as YYYY-MM-DD:");
			String str_edition_date = sc.nextLine();
			Date edition_date = Date.valueOf(str_edition_date);
			System.out.println("Enter the price of the Book:");
			float price = Float.parseFloat(sc.nextLine());

			int count = stmt.executeUpdate("insert into Books values ('" + isbn + "','" + edition + "','" + edition_date
					+ "','" + price + "');");
			if (count == 1) {
				System.out.println("Successfully added book");
			} else {
				System.out.println("Could not add book");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Update information of exisitng book.
	* Admin can update informaiton of any book.
	*/
	public static void updateBook() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Book:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the edition of the Book:");
			int edition = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Books where isbn = '" + isbn + "' and edition = '" + edition + "';");
			if (!rs.next()) {
				System.out.println("A book with given isbn and edition does not exist.");
				return;
			}
			
			int ch = 0;
			String ans = "y";
			while (ans.equals("y") && ch != 3) {
				System.out.println("\nWhat do you want to update?\n1. Edition date\n2. Price\n3. Exit");
				ch = Integer.parseInt(sc.nextLine());

				if (ch == 1) {
					System.out.println("Enter the value for edition date (YYYY-MM-DD):");
					String str_edition_date = sc.nextLine();

					if (str_edition_date.isEmpty()) {
						System.out.println("Edition date cannot be empty.");
						break;
					}
					Date edition_date = Date.valueOf(str_edition_date);

					int count = stmt.executeUpdate("update Books set edition_date = '" + edition_date
							+ "' where isbn = '" + isbn + "' and edition='" + edition + "';");
					if (count == -1) {
						System.out.println("Could not update edition date.");
					} else {
						System.out.println("Edition date updated successfully.");
					}
				} else if (ch == 2) {
					System.out.println("Enter the price of book:");
					float price = Float.parseFloat(sc.nextLine());

					if (price < 1) {
						System.out.println("Price should be a positive value.");
						break;
					}

					int count = stmt.executeUpdate("update Books set price = '" + price + "' where isbn = '" + isbn
							+ "' and edition='" + edition + "';");
					if (count == -1) {
						System.out.println("Could not update price.");
					} else {
						System.out.println("Price updated successfully.");
					}
				}
				if (ch != 3) {
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
	* Delete book from the publishing house.
	*/
	public static void deleteBook() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Book:");
			int isbn = Integer.parseInt(sc.nextLine());

			// Check if such isbn exists in Publication.
			rs = stmt.executeQuery("select * from Publications where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("No publication with the given isbn exists.");
				return;
			}

			System.out.println("Enter the edition of the Book:");
			int edition = Integer.parseInt(sc.nextLine());

			// Check if such book edition exists in Books.
			rs = stmt.executeQuery("select * from Books where isbn = '" + isbn + "' and edition = '" + edition + "';");
			if (!rs.next()) {
				System.out.println("A book with give details does not exist.");
				return;
			}

			int count = stmt
					.executeUpdate("delete from Books where isbn = '" + isbn + "'and edition = '" + edition + "';");
			if (count == 1) {
				System.out.println("Successfully deleted book");
			} else {
				System.out.println("Could not delete book");
			}

		} catch (SQLException e) {
			System.out.println("Could not delete book");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Could not delete book");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Add a new periodic publication provided there is a corresponding entry in Publication table.
	*/
	public static void addPeriodicPublication() {
		try {
			Statement stmt = conn.createStatement();
			int issue_wm = 0;
			int issue_year = 0;
			String periodicity = null;
			String pub_type = null;

			System.out.println("Enter the ISBN of the Periodic Publication:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Publications where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("No publication with the given isbn exists.");
				return;
			}

			rs = stmt.executeQuery("select * from Books where isbn = '" + isbn + "';");
			if (rs.next()) {
				System.out.println("A book with same isbn exists.");
				return;
			}

			while (true) {
				System.out.println("Enter the periodicity of the Periodic Publication (1.monthly / 2.weekly):");
				int periodicity_ch = Integer.parseInt(sc.nextLine());

				if (periodicity_ch == 1) {
					periodicity = "monthly";
					while (true) {
						System.out.println("Enter the month of the Periodic Publication (between 1 to 12):");
						issue_wm = Integer.parseInt(sc.nextLine());
						if (!(issue_wm >= 1 && issue_wm <= 12)) {
							System.out.println("Please enter valid value for the month");
						} else {
							break;
						}
					}
					break;
				} else if (periodicity_ch == 2) {
					periodicity = "weekly";
					while (true) {
						System.out.println("Enter the week of the Periodic Publication (between 1 to 52):");
						issue_wm = Integer.parseInt(sc.nextLine());
						if (!(issue_wm >= 1 && issue_wm <= 52)) {
							System.out.println("Please enter valid value for the week.");
						} else {
							break;
						}
					}
					break;
				} else {
					System.out.println("Please enter valid publication periodicity.");

				}
			}

			while (true) {
				System.out.println("Enter the year of the Periodic Publication (YYYY):");
				issue_year = Integer.parseInt(sc.nextLine());
				if (!(issue_year >= 1900 && issue_year <= 2025)) {
					System.out.println("Please enter valid value for the year.");
				} else {
					break;
				}
			}

			while (true) {
				System.out.println("Enter the type of the Periodic Publication (1.magazine / 2.journal):");
				int pub_type_ch = Integer.parseInt(sc.nextLine());

				if (pub_type_ch == 1) {
					pub_type = "magazine";
					break;
				} else if (pub_type_ch == 2) {
					pub_type = "journal";
					break;

				} else {
					System.out.println("Please enter valid periodic publication type.");
				}
			}

			rs = stmt.executeQuery("select * from PeriodicPublications where isbn = '" + isbn + "' and issue_wm = '"
					+ issue_wm + "' and issue_year = '" + issue_year + "';");
			if (rs.next()) {
				System.out.println("A Periodic Publication with same isbn exists.");
				return;
			}

			System.out.println("Enter the issue date of the Periodic Publication as YYYY-MM-DD:");
			String str_issue_date = sc.nextLine();
			Date issue_date = Date.valueOf(str_issue_date);
			System.out.println("Enter the price of the Periodic Publication:");
			float price = Float.parseFloat(sc.nextLine());

			int count = stmt.executeUpdate(
					"insert into PeriodicPublications values ('" + isbn + "','" + issue_wm + "','" + issue_year + "', '"
							+ periodicity + "','" + pub_type + "','" + issue_date + "','" + price + "');");
			if (count == 1) {
				System.out.println("Successfully added Periodic Publication.");
			} else {
				System.out.println("Could not add Periodic Publication.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Update information of exisitng periodic publication.
	* Admin can update informaiton of any periodic publication.
	*/
	public static void updatePeriodicPublication() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Periodic Publication:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue month/week of the Periodic Publication:");
			int issue_wm = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue year of the Periodic Publication:");
			int issue_year = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from PeriodicPublications where isbn = '" + isbn + "' and issue_wm = '"
					+ issue_wm + "' and issue_year = '" + issue_year + "' ;");
			if (!rs.next()) {
				System.out.println("A Periodic Publication with given isbn and issue does not exist.");
				return;
			}

			int ch = 0;
			String ans = "y";
			while (ans.equals("y") && ch != 5) {
				System.out.println(
						"\nWhat do you want to update?\n1. Issue date\n2. Periodicity\n3. Publication Type\n4. Price\n5. Exit");
				ch = Integer.parseInt(sc.nextLine());

				if (ch == 1) {
					System.out.println("Enter the value for issue date (YYYY-MM-DD):");
					String str_issue_date = sc.nextLine();

					if (str_issue_date.isEmpty()) {
						System.out.println("Issue date cannot be empty.");
						break;
					}
					Date issue_date = Date.valueOf(str_issue_date);

					int count = stmt.executeUpdate(
							"update PeriodicPublications set issue_date = '" + issue_date + "' where isbn = '" + isbn
									+ "' and issue_wm = '" + issue_wm + "' and issue_year = '" + issue_year + "';");
					if (count == -1) {
						System.out.println("Could not update issue date.");
					} else {
						System.out.println("Issue date updated successfully.");
					}
				} else if (ch == 2) {
					System.out.println("Enter the value for periodicity (monthly/weekly):");
					String periodicity = sc.nextLine();

					if (periodicity.isEmpty()) {
						System.out.println("Periodicity cannot be empty.");
						break;
					}

					int count = stmt.executeUpdate(
							"update PeriodicPublications set periodicity = '" + periodicity + "' where isbn = '" + isbn
									+ "' and issue_wm = '" + issue_wm + "' and issue_year = '" + issue_year + "';");
					if (count == -1) {
						System.out.println("Could not update periodicity.");
					} else {
						System.out.println("Periodicity updated successfully.");
					}
				} else if (ch == 3) {
					System.out.println("Enter the value for publication type (journal/magazine):");
					String pub_type = sc.nextLine();

					if (pub_type.isEmpty()) {
						System.out.println("Publication type cannot be empty.");
						break;
					}

					int count = stmt.executeUpdate(
							"update PeriodicPublications set pub_type = '" + pub_type + "' where isbn = '" + isbn
									+ "' and issue_wm = '" + issue_wm + "' and issue_year = '" + issue_year + "';");
					if (count == -1) {
						System.out.println("Could not update publication type.");
					} else {
						System.out.println("Publication type updated successfully.");
					}
				} else if (ch == 4) {
					System.out.println("Enter the value for price:");
					String price_str = sc.nextLine();
					float price;
					if (price_str.isEmpty()) {
						price = 0;
					}
					price = Float.parseFloat(price_str);
					if (price < 1) {
						System.out.println("Price should be a positive value.");
						break;

					}
					int count = stmt.executeUpdate(
							"update PeriodicPublications set price = '" + price + "' where isbn = '" + isbn
									+ "'  and issue_wm = '" + issue_wm + "' and issue_year = '" + issue_year + "';");
					if (count == -1) {
						System.out.println("Could not update price.");
					} else {
						System.out.println("Price updated successfully.");
					}
				}
				if (ch != 5) {
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
	* Delete periodic publication from the publishing house.
	*/
	public static void deletePeriodicPublication() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Periodic Publication:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Publications where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("No publication with the given isbn exists.");
				return;
			}

			System.out.println("Enter the month or week number of the Periodic Publication:");
			int issue_wm = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the year of the Periodic Publication:");
			int issue_year = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from PeriodicPublications where isbn = '" + isbn + "' and issue_wm = '"
					+ issue_wm + "' and issue_year = '" + issue_year + "';");
			if (!rs.next()) {
				System.out.println("A Periodic Publication with given details does not exist.");
				return;
			}

			int count = stmt.executeUpdate("delete from PeriodicPublications where isbn ='" + isbn
					+ "' and issue_wm = '" + issue_wm + "'and issue_year = '" + issue_year + "';");
			if (count == 1) {
				System.out.println("Successfully deleted Periodic Publication");
			} else {
				System.out.println("Could not delete Periodic Publication");
			}
		} catch (SQLException e) {
			System.out.println("Could not delete Periodic Publication");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Could not delete Periodic Publication");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Add a new chapter for an existing book.
	*/
	public static void addChapter(int emp_id) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter isbn of the book:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter edition of the book:");
			String edition = sc.nextLine();

			rs = stmt.executeQuery("select * from Books where isbn = '" + isbn + "' and edition = '" + edition + "';");
			if (!rs.next()) {
				System.out.println("A book with given details does not exist.");
				return;
			}

			rs = stmt.executeQuery("select * from WritesBooks where isbn = '" + isbn + "' and edition = '" + edition
					+ "' and emp_id = '" + emp_id + "';");
			if (!rs.next()) {
				System.out.println("You are not authorized to add chapter to this book.");
				return;
			}

			System.out.println("Enter ID of the chapter:");
			String chapter_id = sc.nextLine();

			rs = stmt.executeQuery("select * from Chapters where isbn = '" + isbn + "' and edition = '" + edition
					+ "' and chapter_id = '" + chapter_id + "' ;");
			if (rs.next()) {
				System.out.println("A chapter with given details already exists.");
				return;
			}

			LocalDate chapter_date = java.time.LocalDate.now(); 

			System.out.println("Enter title of the chapter:");
			String title = sc.nextLine();
			System.out.println("Enter topic of the chapter:");
			String topic = sc.nextLine();
			System.out.println("Enter text of the chapter:");
			String text = sc.nextLine();

			int count = stmt.executeUpdate("insert into Chapters  values( '" + isbn + "','" + edition + "' ,'"
					+ chapter_id + "','" + chapter_date + "','" + title + "','" + topic + "' ,'" + text + "' );");
			if (count == 1) {
				System.out.println("Successfully added chapter.");
			} else {
				System.out.println("Could not add chapter.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	* Description:
	* Update informaiton of an exisitng chapter of a book.
	*/
	public static void updateChapter(int emp_id, String emp_type) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Chapter:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the edition of the Book:");
			int edition = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the chapter id of the Book:");
			int chapter_id = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select text from Chapters where isbn = '" + isbn + "' and edition = '" + edition
					+ "' and chapter_id = '" + chapter_id + "';");
			if (!rs.next()) {
				System.out.println("A chapter with given details does not exist.");
				return;
			}

			Date book_date = null;
			rs = stmt.executeQuery("select edition_date from Books where isbn = '" + isbn + "' and edition = '" + edition + "';");
			if(rs.next()){
				book_date = rs.getDate(1);
			}

			if (emp_type.equals("AUTHOR")) {
				rs = stmt.executeQuery("select * from WritesBooks where isbn = '" + isbn + "' and edition = '" + edition
						+ "' and emp_id = '" + emp_id + "';");
				if (!rs.next()) {
					System.out.println("You are not authorized to update chapter to this book.");
					return;
				}
			}
			if (emp_type.equals("EDITOR")) {
				rs = stmt.executeQuery("select * from EditsChapters where isbn = '" + isbn + "' and edition = '"
						+ edition + "'  and chapter_id = '" + chapter_id + "' and emp_id = '" + emp_id + "';");
				if (!rs.next()) {
					System.out.println("You are not authorized to update chapter to this book.");
					return;
				}
			}

			int ch = 0;
			String ans = "y";
			while (ans.equals("y") && ch != 4) {
				System.out.println("\nWhat do you want to update?\n1. Title\n2. Topic\n3. Chapter date\n4. Exit");
				ch = Integer.parseInt(sc.nextLine());

				if (ch == 1) {
					System.out.println("Enter the value for title:");
					String title = sc.nextLine();

					if (title.isEmpty()) {
						System.out.println("Title cannot be empty.");
						break;
					}

					int count = stmt.executeUpdate("update Chapters set title = '" + title + "' where isbn = '" + isbn
							+ "' and edition='" + edition + "' and chapter_id = '" + chapter_id + "';");
					if (count == -1) {
						System.out.println("Could not update title.");
					} else {
						System.out.println("Title updated successfully.");
					}
				} else if (ch == 2) {
					System.out.println("Enter the value for topic:");
					String topic = sc.nextLine();

					if (topic.isEmpty()) {
						System.out.println("Topic cannot be empty.");
						break;
					}
					int count = stmt.executeUpdate("update Chapters set topic = '" + topic + "' where isbn = '" + isbn
							+ "' and edition='" + edition + "' and chapter_id = '" + chapter_id + "';");
					if (count == -1) {
						System.out.println("Could not update topic.");
					} else {
						System.out.println("Topic updated successfully.");
					}
				} else if (ch == 3) {
					System.out.println("Enter the chapter date (YYYY-MM-DD):");
					String str_chapter_date = sc.nextLine();

					if (str_chapter_date.isEmpty()) {
						System.out.println("Chapter date cannot be empty.");
						break;
					}
					Date chapter_date = Date.valueOf(str_chapter_date);

					if (chapter_date.before(book_date)) {
						System.out.println("Chapter date cannot be before book.");
						return;
					}

					int count = stmt
							.executeUpdate("update Chapters set chapter_date = '" + chapter_date + "' where isbn = '"
									+ isbn + "' and edition='" + edition + "' and chapter_id = '" + chapter_id + "';");

					if (count == -1) {
						System.out.println("Could not update chapter date.");
					} else {
						System.out.println("Chapter date updated successfully.");
					}
				}

				if (ch != 4) {
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
	* Delete a chapter of a book.
	*/
	public static void deleteChapter(int emp_id) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter isbn of the book:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter edition of the book:");
			String edition = sc.nextLine();

			rs = stmt.executeQuery("select * from Books where isbn = '" + isbn + "' and edition = '" + edition + "';");
			if (!rs.next()) {
				System.out.println("A book with given details does not exist.");
				return;
			}

			rs = stmt.executeQuery("select * from WritesBooks where isbn = '" + isbn + "' and edition = '" + edition
					+ "' and emp_id = '" + emp_id + "';");
			if (!rs.next()) {
				System.out.println("You are not authorized to delete chapter to this book.");
				return;
			}

			System.out.println("Enter ID of the chapter:");
			String chapter_id = sc.nextLine();

			rs = stmt.executeQuery("select * from Chapters where isbn = '" + isbn + "' and edition = '" + edition
					+ "' and chapter_id = '" + chapter_id + "' ;");
			if (!rs.next()) {
				System.out.println("No such chapter exists.");
				return;
			}

			int count = stmt.executeUpdate("delete from Chapters where isbn = '" + isbn + "' and edition = '" + edition
					+ "' and chapter_id = '" + chapter_id + "' ;");
			if (count == 1) {
				System.out.println("Successfully deleted chapter.");
			} else {
				System.out.println("Could not delete chapter.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	* Description:
	* Add articles in an exisitng periodic publication.
	*/
	public static void addArticle(int emp_id) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter isbn of the Periodic Publication:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter issue_wm of the Periodic Publication:");
			String issue_wm = sc.nextLine();
			System.out.println("Enter issue_year of the Periodic Publication:");
			String issue_year = sc.nextLine();

			rs = stmt.executeQuery("select * from PeriodicPublications where isbn = '" + isbn + "' and issue_year = '"
					+ issue_year + "' and issue_year ='" + issue_year + "';");
			if (!rs.next()) {
				System.out.println("A Periodic Publication with given details does not exist.");
				return;
			}

			rs = stmt.executeQuery("select * from WritesPerPub where isbn = '" + isbn + "' and issue_wm = '" + issue_wm
					+ "' and issue_year ='" + issue_year + "' and emp_id = '" + emp_id + "';");
			if (!rs.next()) {
				System.out.println("You are not authorized to add article to this periodic publication.");
				return;
			}

			System.out.println("Enter ID of the article:");
			String article_id = sc.nextLine();

			rs = stmt.executeQuery("select * from Articles where isbn = '" + isbn + "' and issue_wm = '" + issue_wm
					+ "' and issue_year ='" + issue_year + "'and article_id = '" + article_id + "' ;");
			if (rs.next()) {
				System.out.println("An article with given details already exists.");
				return;
			}

			LocalDate article_date = java.time.LocalDate.now(); 

			System.out.println("Enter title of the article:");
			String title = sc.nextLine();
			System.out.println("Enter topic of the article:");
			String topic = sc.nextLine();
			System.out.println("Enter text of the article:");
			String text = sc.nextLine();

			int count = stmt.executeUpdate("insert into Articles  values( '" + isbn + "','" + issue_wm + "' ,'"
					+ issue_year + "' ,'" + article_id + "','" + article_date + "','" + title + "','" + topic + "' ,'"
					+ text + "' );");
			if (count == 1) {
				System.out.println("Successfully added article.");
			} else {
				System.out.println("Could not add article.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Update information of an exisitng articles of a periodic publication.
	*/
	public static void updateArticle(int emp_id, String emp_type) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Article:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue week/month of the Periodic Publication:");
			int issue_wm = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue year of the Periodic Publication:");
			int issue_year = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the article id of the Periodic Publication:");
			int article_id = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select text from Articles where isbn = '" + isbn + "' and issue_wm = '" + issue_wm
					+ "' and issue_year = '" + issue_year + "' and article_id = '" + article_id + "';");
			if (!rs.next()) {
				System.out.println("An article with given details does not exist.");
				return;
			}
			Date perpub_date = null;
			rs = stmt.executeQuery("select issue_date from PeriodicPublications where isbn = '" + isbn + "' and issue_wm = '" + issue_wm
					+ "' and issue_year = '" + issue_year + "';");
			if(rs.next()){
				perpub_date = rs.getDate(1);
			}

			if (emp_type.equals("JOURNALIST")) {
				rs = stmt.executeQuery("select * from WritesPerPub where isbn = '" + isbn + "' and issue_wm = '"
						+ issue_wm + "' and issue_year ='" + issue_year + "' and emp_id = '" + emp_id + "';");
				if (!rs.next()) {
					System.out.println("You are not authorized to update article to this periodic publication.");
					return;
				}
			}
			if (emp_type.equals("EDITOR")) {
				rs = stmt.executeQuery("select * from EditsArticles where isbn = '" + isbn + "' and issue_wm = '"
						+ issue_wm + "' and issue_year ='" + issue_year + "' and article_id = '" + article_id
						+ "' and emp_id = '" + emp_id + "';");
				if (!rs.next()) {
					System.out.println("You are not authorized to update article to this periodic publication.");
					return;
				}
			}
			
			int ch = 0;
			String ans = "y";
			while (ans.equals("y") && ch != 4) {
				System.out.println("\nWhat do you want to update?\n1. Title\n2. Topic\n3. Article date\n4. Exit");
				ch = Integer.parseInt(sc.nextLine());

				if (ch == 1) {
					System.out.println("Enter the value for title:");
					String title = sc.nextLine();

					if (title.isEmpty()) {
						System.out.println("Title cannot be empty.");
						break;
					}

					int count = stmt.executeUpdate("update Articles set title = '" + title + "' where isbn = '" + isbn
							+ "' and issue_wm = '" + issue_wm + "' and issue_year = '" + issue_year
							+ "' and article_id = '" + article_id + "';");
					if (count == -1) {
						System.out.println("Could not update title.");
					} else {
						System.out.println("Title updated successfully.");
					}
				} else if (ch == 2) {
					System.out.println("Enter the value for topic:");
					String topic = sc.nextLine();

					if (topic.isEmpty()) {
						System.out.println("Topic cannot be empty.");
						break;
					}
					int count = stmt.executeUpdate("update Articles set topic = '" + topic + "' where isbn = '" + isbn
							+ "' and issue_wm = '" + issue_wm + "' and issue_year = '" + issue_year
							+ "' and article_id = '" + article_id + "';");
					if (count == -1) {
						System.out.println("Could not update topic.");
					} else {
						System.out.println("Topic updated successfully.");
					}
				} else if (ch == 3) {
					System.out.println("Enter the article date (YYYY-MM-DD):");
					String str_article_date = sc.nextLine();

					if (str_article_date.isEmpty()) {
						System.out.println("Article date cannot be empty.");
						break;
					}
					Date article_date = Date.valueOf(str_article_date);

					if (article_date.before(perpub_date)) {
						System.out.println("Article date cannot be before periodic publication.");
						return;
					}

					int count = stmt.executeUpdate("update Articles set article_date = '" + article_date
							+ "' where isbn = '" + isbn + "' and issue_wm = '" + issue_wm + "' and issue_year = '"
							+ issue_year + "' and article_id = '" + article_id + "';");

					if (count == -1) {
						System.out.println("Could not update article date.");
					} else {
						System.out.println("Article date updated successfully.");
					}
				}
				if (ch != 4) {
					System.out.println("\nDo you want to continue? (y/n)");
					ans = sc.nextLine();
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Delete an existing article of a periodic publication.
	*/
	public static void deleteArticle(int emp_id) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter isbn of the book:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter issue_wm of the Periodic Publication:");
			String issue_wm = sc.nextLine();
			System.out.println("Enter issue_year of the Periodic Publication:");
			String issue_year = sc.nextLine();

			rs = stmt.executeQuery("select * from PeriodicPublications where isbn = '" + isbn + "' and issue_year = '"
					+ issue_year + "' and issue_year ='" + issue_year + "';");
			if (!rs.next()) {
				System.out.println("A Periodic Publication with given details does not exist.");
				return;
			}

			rs = stmt.executeQuery("select * from WritesPerPub where isbn = '" + isbn + "' and issue_wm = '" + issue_wm
					+ "' and issue_year ='" + issue_year + "' and emp_id = '" + emp_id + "';");
			if (!rs.next()) {
				System.out.println("You are not authorized to delete article to this periodic publication.");
				return;
			}

			System.out.println("Enter ID of the article:");
			String article_id = sc.nextLine();

			rs = stmt.executeQuery("select * from Articles where isbn = '" + isbn + "' and issue_wm = '" + issue_wm
					+ "' and issue_year ='" + issue_year + "'and article_id = '" + article_id + "' ;");
			if (!rs.next()) {
				System.out.println("An article with given details does not exist.");
				return;
			}

			int count = stmt.executeUpdate("delete from Articles where isbn = '" + isbn + "' and issue_wm = '"
					+ issue_wm + "' and issue_year ='" + issue_year + "'and article_id = '" + article_id + "' ;");
			if (count == 1) {
				System.out.println("Successfully deleted article.");
			} else {
				System.out.println("Could not delete article.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	* Description:
	* Assign a book to an author.
	*/
	public static void assignBookToAuthor() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the book:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Books where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Book with the given isbn does not exist.");
				return;
			}

			System.out.println("Enter the edition of the book:");
			int edition = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Books where edition = '" + edition + "' AND isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Book with the given edition and isbn does not exist.");
				return;
			}

			System.out.println("Enter the employee ID of the author:");
			int emp_id_auth = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Authors where emp_id = '" + emp_id_auth + "';");
			if (!rs.next()) {
				System.out.println("Author with the given Employee ID does not exist.");
				return;
			}

			int count = stmt.executeUpdate(
					"insert into WritesBooks values('" + emp_id_auth + "','" + isbn + "','" + edition + "');");
			if (count == 1) {
				System.out.println("Book assigned to author successfully.");
			} else {
				System.out.println("Book could not be assigned to author.");
			}
		} catch (SQLException e) {
			System.out.println("Book could not be assigned to author.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Book could not be assigned to author.");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Unassign a book from an author.
	*/
	public static void unassignBookFromAuthor() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the book:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Books where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Book with the given isbn does not exist.");
				return;
			}

			System.out.println("Enter the edition of the book:");
			int edition = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Books where edition = '" + edition + "' AND isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Book with the given edition and isbn does not exist.");
				return;
			}

			System.out.println("Enter the employee ID of the author to unassign:");
			int emp_id_auth = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Authors where emp_id = '" + emp_id_auth + "';");
			if (!rs.next()) {
				System.out.println("Author with the given Employee ID does not exist.");
				return;
			}
			int count = stmt.executeUpdate("delete from WritesBooks where emp_id ='" + emp_id_auth + "' and isbn = '"
					+ isbn + "' and edition = '" + edition + "';");
			if (count == 1) {
				System.out.println("Book unassigned from author successfully.");
			} else {
				System.out.println("Book could not be unassigned from author.");
			}
		} catch (SQLException e) {
			System.out.println("Book could not be unassigned from author.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Book could not be unassigned from author.");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Assign a periodic publication to a journalist.
	*/
	public static void assignPerPubToJournalist() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Periodic Publication:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from PeriodicPublications where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Periodic Publication with the given isbn does not exist.");
				return;
			}

			System.out.println("Enter the issue week/month of the Periodic Publication:");
			int issue_wm = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue year of the Periodic Publication:");
			int issue_year = Integer.parseInt(sc.nextLine());
			rs = stmt.executeQuery("select * from PeriodicPublications where issue_wm = '" + issue_wm + "' AND isbn = '"
					+ isbn + "' AND issue_year = '" + issue_year + "';");
			if (!rs.next()) {
				System.out.println("Periodic Publication with the given issue and isbn does not exist.");
				return;
			}

			System.out.println("Enter the Employee ID of the journalist:");
			int emp_id_journ = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Journalists where emp_id = '" + emp_id_journ + "';");
			if (!rs.next()) {
				System.out.println("Journalist with the given employee ID  does not exist.");
				return;
			}
			int count = stmt.executeUpdate("insert into WritesPerPub values('" + emp_id_journ + "','" + isbn + "','"
					+ issue_wm + "','" + issue_year + "');");
			if (count == 1) {
				System.out.println("Periodic Publication assigned to journalist successfully.");
			} else {
				System.out.println("Periodic Publication could not be assigned to journalist.");
			}
		} catch (SQLException e) {
			System.out.println("Periodic Publication could not be assigned to journalist.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Periodic Publication could not be assigned to journalist.");
			e.printStackTrace();
		}

	}

	/*
	* Description:
	* Unsssign a periodic publication from a journalist.
	*/
	public static void unassignPerPubFromJournalist() {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Periodic Publication:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from PeriodicPublications where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Periodic Publication with the given isbn does not exist.");
				return;
			}

			System.out.println("Enter the issue week/month of the Periodic Publication:");
			int issue_wm = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue year of the Periodic Publication:");
			int issue_year = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from PeriodicPublications where issue_wm = '" + issue_wm + "' AND isbn = '"
					+ isbn + "' AND issue_year = '" + issue_year + "';");
			if (!rs.next()) {
				System.out.println("Periodic Publication with the given issue and isbn does not exist.");
				return;
			}

			System.out.println("Enter the Employee ID of the journalist:");
			int emp_id_journ = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Journalists where emp_id = '" + emp_id_journ + "';");
			if (!rs.next()) {
				System.out.println("Journalist with the given employee ID  does not exist.");
				return;
			}
			int count = stmt.executeUpdate("delete from WritesPerPub where emp_id = '" + emp_id_journ + "'and isbn = '"
					+ isbn + "'and issue_wm = '" + issue_wm + "' and issue_year = '" + issue_year + "';");
			if (count == 1) {
				System.out.println("Periodic Publication unassigned from journalist successfully.");
			} else {
				System.out.println("Periodic Publication could not be unassigned from journalist.");
			}
		} catch (SQLException e) {
			System.out.println("Periodic Publication could not be unassigned from journalist.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Periodic Publication could not be unassigned from journalist.");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Assign a chapter to an editor.
	*/
	public static void assignChapterToEditor(int emp_id) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the book:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Books where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Book with the given isbn does not exist.");
				return;
			}

			System.out.println("Enter the edition of the book:");
			int edition = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Books where edition = '" + edition + "' AND isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Book with the given edition and isbn does not exist.");
				return;
			}

			// Only the author of the book can assign chapters to editors.
			rs = stmt.executeQuery("select * from WritesBooks where isbn = '" + isbn + "' and edition = '" + edition
					+ "' and emp_id = '" + emp_id + "';");
			if (!rs.next()) {
				System.out.println("You are not authorized to assign editor to this book.");
				return;
			}

			System.out.println("Enter the chapter of the given edition of the book:");
			int chapter_id = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Chapters where edition = '" + edition + "' AND isbn = '" + isbn
					+ "' AND chapter_id = '" + chapter_id + "';");
			if (!rs.next()) {
				System.out.println("Chapter for the given edition of the book does not exist.");
				return;
			}

			System.out.println("Enter the employee ID of the editor:");
			int emp_id_edit = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Editors where emp_id = '" + emp_id_edit + "';");
			if (!rs.next()) {
				System.out.println("Editor with the given Employee ID does not exist.");
				return;
			}
			int count = stmt.executeUpdate("insert into EditsChapters values('" + emp_id_edit + "','" + isbn + "','"
					+ edition + "','" + chapter_id + "');");
			if (count == 1) {
				System.out.println("Chapter assigned to editor successfully.");
			} else {
				System.out.println("Chapter could not be assigned to editor.");
			}
		} catch (SQLException e) {
			System.out.println("Chapter could not be assigned to editor.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Chapter could not be assigned to editor.");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Unassign a chapter to an editor.
	*/
	public static void unassignChapterFromEditor(int emp_id) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the book:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Books where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Book with the given isbn does not exist.");
				return;
			}

			System.out.println("Enter the edition of the book:");
			int edition = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Books where edition = '" + edition + "' AND isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Book with the given edition and isbn does not exist.");
				return;
			}

			// Only the author of the book can unassign chapters from editors.
			rs = stmt.executeQuery("select * from WritesBooks where isbn = '" + isbn + "' and edition = '" + edition
					+ "' and emp_id = '" + emp_id + "';");
			if (!rs.next()) {
				System.out.println("You are not authorized to unassign editor from this book.");
				return;
			}

			System.out.println("Enter the chapter of the given edition of the book:");
			int chapter_id = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Chapters where edition = '" + edition + "' AND isbn = '" + isbn
					+ "' AND chapter_id = '" + chapter_id + "';");
			if (!rs.next()) {
				System.out.println("Chapter for the given edition of the book does not exist.");
				return;
			}

			System.out.println("Enter the employee ID of the editor:");
			int emp_id_edit = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Editors where emp_id = '" + emp_id_edit + "';");
			if (!rs.next()) {
				System.out.println("Editor with the given Employee ID does not exist.");
				return;
			}
			int count = stmt.executeUpdate("delete from EditsChapters where emp_id = '" + emp_id_edit + "' and isbn = '"
					+ isbn + "'and edition = '" + edition + "' and chapter_id = '" + chapter_id + "';");
			if (count == 1) {
				System.out.println("Chapter unassigned from editor successfully.");
			} else {
				System.out.println("Chapter could not be unassigned from editor.");
			}
		} catch (SQLException e) {
			System.out.println("Chapter could not be unassigned from editor.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Chapter could not be unassigned from editor.");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Assign an article to an editor.
	*/
	public static void assignArticleToEditor(int emp_id) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Periodic Publication:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from PeriodicPublications where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Periodic Publication with the given isbn does not exist.");
				return;
			}

			System.out.println("Enter the issue week/month of the Periodic Publication:");
			int issue_wm = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue year of the Periodic Publication:");
			int issue_year = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from PeriodicPublications where issue_wm = '" + issue_wm + "' and isbn = '"
					+ isbn + "' and issue_year = '" + issue_year + "';");
			if (!rs.next()) {
				System.out.println("Periodic Publication with the given issue and isbn does not exist.");
				return;
			}

			// Only the journalist of the periodic publication can assign articles to editors.
			rs = stmt.executeQuery("select * from WritesPerPub where issue_wm = '" + issue_wm + "' and isbn = '"
					+ isbn + "' and issue_year = '" + issue_year + "'and emp_id = '" + emp_id + "';");
			if (!rs.next()) {
				System.out.println("You are not authorized to assign editor to this periodic publication.");
				return;
			}

			System.out.println("Enter the Article ID of the given issue of the Periodic Publication:");
			int article = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Articles where issue_wm = '" + issue_wm + "' AND isbn = '" + isbn
					+ "' and issue_year = '" + issue_year + "' and article_id = '" + article + "';");
			if (!rs.next()) {
				System.out.println("Article for given ID and issue of the Periodic Publication does not exist.");
				return;
			}

			System.out.println("Enter the Employee ID of the editor:");
			int emp_id_edit = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Editors where emp_id = '" + emp_id_edit + "';");
			if (!rs.next()) {
				System.out.println("Editor with the given employee ID  does not exist.");
				return;
			}
			int count = stmt.executeUpdate("insert into EditsArticles values('" + emp_id_edit + "','" + isbn + "','"
					+ issue_wm + "','" + issue_year + "','" + article + "');");
			if (count == 1) {
				System.out.println("Article assigned to editor successfully.");
			} else {
				System.out.println("Article could not be assigned to editor.");
			}
		} catch (SQLException e) {
			System.out.println("Article could not be assigned to editor.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Article could not be assigned to editor.");
			e.printStackTrace();
		}

	}

	/*
	* Description:
	* Unassign an article to an editor.
	*/
	public static void unassignArticleFromEditor(int emp_id) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Periodic Publication:");
			int isbn = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from PeriodicPublications where isbn = '" + isbn + "';");
			if (!rs.next()) {
				System.out.println("Periodic Publication with the given isbn does not exist.");
				return;
			}

			System.out.println("Enter the issue week/month of the Periodic Publication:");
			int issue_wm = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue year of the Periodic Publication:");
			int issue_year = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from PeriodicPublications where issue_wm = '" + issue_wm + "' and isbn = '"
					+ isbn + "' and issue_year = '" + issue_year + "';");
			if (!rs.next()) {
				System.out.println("Periodic Publication with the given issue and isbn does not exist.");
				return;
			}

			// Only the journalist of the periodic publication can unassign articles from editors.
			rs = stmt.executeQuery("select * from WritesPerPub where issue_wm = '" + issue_wm + "' and isbn = '"
					+ isbn + "' and issue_year = '" + issue_year + "'and emp_id = '" + emp_id + "';");
			if (!rs.next()) {
				System.out.println("You are not authorized to unassign editor from this periodic publication.");
				return;
			}

			System.out.println("Enter the Article ID of the given issue of the Periodic Publication:");
			int article = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Articles where issue_wm = '" + issue_wm + "' and isbn = '" + isbn
					+ "' and issue_year = '" + issue_year + "' and article_id = '" + article + "';");
			if (!rs.next()) {
				System.out.println("Article for given details of the Periodic Publication does not exist.");
				return;
			}

			System.out.println("Enter the Employee ID of the editor:");
			int emp_id_edit = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select * from Editors where emp_id = '" + emp_id_edit + "';");
			if (!rs.next()) {
				System.out.println("Editor with the given employee ID  does not exist.");
				return;
			}

			int count = stmt.executeUpdate("delete from EditsArticles where emp_id = '" + emp_id_edit + "' and isbn = '"
					+ isbn + "' and issue_wm = '" + issue_wm + "' and issue_year = '" + issue_year
					+ "' and article_id = '" + article + "';");
			if (count == 1) {
				System.out.println("Article unassigned from editor successfully.");
			} else {
				System.out.println("Article could not be unassigned from editor.");
			}
		} catch (SQLException e) {
			System.out.println("Article could not be unassigned from editor.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Article could not be unassigned from editor.");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Display all the chapters and articles which the editor is contributing to.
	*/
	public static void viewAssignedEditorialWork(int emp_id) {
		try {
			Statement stmt = conn.createStatement();

			

			rs = stmt.executeQuery(
					"select p.isbn,p.title,a.issue_wm,a.issue_year,a.article_id,a.title,a.topic,a.text from Publications p inner join Articles a inner join EditsArticles e on" 
					+ " p.isbn = a.isbn and a.isbn=e.isbn and a.issue_wm = e.issue_wm and a.issue_year = e.issue_year and a.article_id = e.article_id where e.emp_id = '" + emp_id + "';");
			if (rs.next()) {
				System.out.println("\nShowing Articles\n");
				System.out.println(
						"\n|\t isbn \t | \t title \t | \t | \t issue_wm \t | \t issue_year \t | \t aricle_id \t|\t article title \t|\t topic \t|\t text \t|");

				System.out.println(
						"------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("|\t" + rs.getInt(1) + "\t | \t" + rs.getString(2) + "\t | \t" + rs.getInt(3) + "\t | \t" + rs.getInt(4) + "\t | \t" + rs.getInt(5) + "\t | \t" + rs.getString(6)+ "\t | \t" + rs.getString(7)+ "\t | \t" + rs.getString(8) + " \t|");
			}

			while (rs.next()) {
				System.out.println("|\t" + rs.getInt(1) + "\t | \t" + rs.getString(2) + "\t | \t" + rs.getInt(3) + "\t | \t" + rs.getInt(4) + "\t | \t" + rs.getInt(5) + "\t | \t" + rs.getString(6)+ "\t | \t" + rs.getString(7)+ "\t | \t" + rs.getString(8) + " \t|");
			}

			System.out.println(
					"\n\n___________________________________________________________________________________________________________________________________________\n");
			rs = stmt.executeQuery(
					"select p.isbn,p.title,c.edition,c.chapter_id,c.title,c.topic,c.text from Publications p inner join Chapters c inner join EditsChapters e" 
					+ " on p.isbn = c.isbn and c.isbn=e.isbn and c.edition = e.edition and c.chapter_id = e.chapter_id where e.emp_id = '" + emp_id + "';");
			
			if (rs.next()) {
				System.out.println("\nShowing Chapters\n");
				System.out.println("\n|\t isbn \t | \t title \t | \t | \t edition \t | \t chapter_id \t|\t chapter title \t|\t topic \t|\t text \t|");
				System.out.println(
						"-----------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("|\t" + rs.getInt(1) + "\t | \t" + rs.getString(2) + "\t | \t" + rs.getInt(3) + "\t | \t" + rs.getInt(4) + "\t | \t" + rs.getString(5)+ "\t | \t" + rs.getString(6)+ "\t | \t" + rs.getString(7) + " \t|");
			}
			
			while (rs.next()) {
				System.out.println("|\t" + rs.getInt(1) + "\t | \t" + rs.getString(2) + "\t | \t" + rs.getInt(3) + "\t | \t" + rs.getInt(4) + "\t | \t" + rs.getString(5)+ "\t | \t" + rs.getString(6)+ "\t | \t" + rs.getString(7) + " \t|");
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Display all the books which the author is contributing to.
	*/
	public static void findBooksByAuthorName(String emp_name) {
		try {
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery("select p.isbn,p.title,b.edition from Books b\r\n" + "inner join Publications p \r\n"
					+ "on p.isbn=b.isbn\r\n" + "inner join WritesBooks w \r\n"
					+ "on w.isbn = b.isbn and w.edition=b.edition \r\n"
					+ "and w.emp_id = (select emp_id from Employees where emp_name like \"%"+ emp_name+"%\");");
			
			System.out.println("\n|\t isbn \t | \t title \t | \t edition \t|");
			System.out.println(
					"--------------------------------------------------------------------------");
			
			while (rs.next()) {
				System.out.println("|\t " + rs.getInt(1) + "\t | \t" + rs.getString(2) + "\t | \t" + rs.getInt(3) + " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Display all the periodic publications which the journalist is contributing to.
	*/
	public static void findArticlesByJournalistName(String emp_name) {
		try {
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery(
					"select a.title,a.topic,a.text from Articles a \r\n" + "inner join \r\n" + "WritesPerPub j \r\n"
							+ "on a.isbn=j.isbn and a.issue_wm=j.issue_wm and a.issue_year=j.issue_year\r\n"
							+ "and j.emp_id = (select emp_id from Employees where emp_name like \"%"+ emp_name+"%\");");
							
			System.out.println("\n|\t title \t | \t topic \t | \t text \t|");
			System.out.println(
					"---------------------------------------------------------------------------------");
			
			while (rs.next()) {
				System.out.println(
						"|\t " + rs.getString(1) + "\t | \t" + rs.getString(2) + "\t | \t" + rs.getString(3) + " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Update the text of an article.
	* Only the authorrised journlist or editor can update the text.
	* The text can either be appended ot changed completely.
	*/
	public static void updateTextOfArticle(int emp_id, String emp_type) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the periodic publication:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue week/month of the periodic publication:");
			int issue_wm = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the issue year of the periodic publication:");
			int issue_year = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the article id of the periodic publication:");
			int article_id = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select text from Articles where isbn = '" + isbn + "' and issue_wm = '" + issue_wm
					+ "' and issue_year = '" + issue_year + "' and article_id = '" + article_id + "';");
			if (!rs.next()) {
				System.out.println("An article with given details does not exist.");
				return;
			}
			String text = rs.getString(1);

			if (emp_type.equals("JOURNALIST")) {
				rs = stmt.executeQuery("select * from WritesPerPub where isbn = '" + isbn + "' and issue_wm = '"
						+ issue_wm + "' and issue_year ='" + issue_year + "' and emp_id = '" + emp_id + "';");
				if (!rs.next()) {
					System.out.println("You are not authorized to update article to this periodic publication.");
					return;
				}
			}
			if (emp_type.equals("EDITOR")) {
				rs = stmt.executeQuery("select * from EditsArticles where isbn = '" + isbn + "' and issue_wm = '"
						+ issue_wm + "' and issue_year ='" + issue_year + "' and article_id = '" + article_id
						+ "' and emp_id = '" + emp_id + "';");
				if (!rs.next()) {
					System.out.println("You are not authorized to update article to this periodic publication.");
					return;
				}
			}


			int ch = 0;
			System.out.println("Do you want to \n1. Append to existing text\n2. Change the entire text?");
			ch = Integer.parseInt(sc.nextLine());
			int count = 0;
			if (ch == 1) {
				System.out.println("Enter text to be appended:");
				String newText = sc.nextLine();
				text = text + " " + newText;
				count = stmt.executeUpdate("update Articles set text = '" + text + "' where  isbn = '" + isbn
						+ "' and issue_wm = '" + issue_wm + "' and issue_year = '" + issue_year
						+ "' and article_id = '" + article_id + "';");
			} else if (ch == 2) {
				System.out.println("Enter new text to be added:");
				String newText = sc.nextLine();
				count = stmt.executeUpdate("update Articles set text = '" + newText + "' where  isbn = '" + isbn
						+ "' and issue_wm = '" + issue_wm + "' and issue_year = '" + issue_year
						+ "' and article_id = '" + article_id + "';");
			} else {
				return;
			}

			if (count == 1) {
				System.out.println("Successfuly updated text of article.");
			} else {
				System.out.println("Could not update text of article.");
			}
		} catch (SQLException e) {
			System.out.println("Could not update text of article.");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("Could not update text of article.");
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Update the text of a chapter.
	* Only the authorrised author or editor can update the text.
	* The text can either be appended ot changed completely.
	*/
	public static void updateTextOfChapter(int emp_id, String emp_type) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter the ISBN of the Book:");
			int isbn = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the edition of the Book:");
			int edition = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the chapter id of the Book:");
			int chapter_id = Integer.parseInt(sc.nextLine());

			rs = stmt.executeQuery("select text from Chapters where isbn = '" + isbn + "' and edition = '" + edition
					+ "' and chapter_id = '" + chapter_id + "';");
			if (!rs.next()) {
				System.out.println("A chapter with given details does not exist.");
				return;
			}
			String text = rs.getString(1);

			if (emp_type.equals("AUTHOR")) {
				rs = stmt.executeQuery("select * from WritesBooks where isbn = '" + isbn + "' and edition = '" + edition
						+ "' and emp_id = '" + emp_id + "';");
				if (!rs.next()) {
					System.out.println("You are not authorized to update chapter to this book.");
					return;
				}
			}
			if (emp_type.equals("EDITOR")) {
				rs = stmt.executeQuery("select * from EditsChapters where isbn = '" + isbn + "' and edition = '"
						+ edition + "'  and chapter_id = '" + chapter_id + "' and emp_id = '" + emp_id + "';");
				if (!rs.next()) {
					System.out.println("You are not authorized to update chapter to this book.");
					return;
				}
			}

			

			int ch = 0;
			System.out.println("Do you want to \n1. Append to existing text\n2. Change the entire text?");
			ch = Integer.parseInt(sc.nextLine());
			int count = 0;
			if (ch == 1) {
				System.out.println("Enter text to be appended:");
				String newText = sc.nextLine();
				text = text + " " + newText;
				count = stmt.executeUpdate("update Chapters set text = '" + text + "' where  isbn = '" + isbn
						+ "' and edition = '" + edition + "'and chapter_id = '" + chapter_id + "';");
			} else if (ch == 2) {
				System.out.println("Enter new text to be added:");
				String newText = sc.nextLine();
				count = stmt.executeUpdate("update Chapters set text = '" + newText + "' where  isbn = '" + isbn
						+ "' and edition = '" + edition + "'and chapter_id = '" + chapter_id + "';");

			} else {
				return;
			}

			if (count == 1) {
				System.out.println("Successfuly updated text of chapter.");
			} else {
				System.out.println("Could not update text of chapter.");
			}

		} catch (SQLException e) {
			System.out.println("Could not update text of chapter.");
			e.printStackTrace();
		}

		catch (Exception e) {
			System.out.println("Could not update text of chapter.");
			e.printStackTrace();
		}

	}

	/*
	* Description:
	* Find all articles for a given topic.
	* Journlaist and editor can view only thier assigned articles.
	* Admin can view all articles.
	*/
	public static void findArticlesByTopic(int emp_id, String emp_type) {
		try {
			Statement stmt = conn.createStatement();
			System.out.println("Enter topic:");
			String topic = sc.nextLine();
			
			if (emp_type.equals("ADMIN")) {
				rs = stmt.executeQuery("select * \r\n" + "from Articles\r\n" + "where topic like \"%" + topic + "%\";");
			} else if (emp_type.equals("JOURNALIST")) {
				rs = stmt.executeQuery(
						"select a.isbn,a.issue_wm,a.issue_year,a.article_id,a.article_date,a.title,a.topic,a.text from Articles a inner join WritesPerPub w on a.isbn=w.isbn and a.issue_wm = w.issue_wm and a.issue_year = w.issue_year where w.emp_id='"
								+ emp_id + "' and a.topic like \"%" + topic + "%\";");
			} else if (emp_type.equals("EDITOR")) {
				rs = stmt.executeQuery(
						"select a.isbn,a.issue_wm,a.issue_year,a.article_id,a.article_date,a.title,a.topic,a.text from Articles a inner join EditsArticles e on a.isbn=e.isbn and a.issue_wm = e.issue_wm and a.issue_year = e.issue_year and a.article_id=e.article_id where e.emp_id='"
								+ emp_id + "' and a.topic like \"%" + topic + "%\";");
			}
			System.out.println(
					"\n|\t isbn \t | \t issue_wm \t | \t issue_year \t | \t article_id \t | \t article_date \t | \t title \t | \t topic \t | \t text \t|");
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------------------------------");
			while (rs.next()) {
				System.out.println("|\t " + rs.getInt(1) + "\t | \t" + rs.getInt(2) + "\t | \t" + rs.getInt(3) + "\t | \t"
						+ rs.getInt(4) + "\t | \t" + rs.getString(5) + "\t | \t" + rs.getString(6) + "\t | \t"
						+ rs.getString(7) + "\t | \t" + rs.getString(8) + " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Find all articles for a given date range.
	* Journlaist and editor can view only their assigned articles.
	* Admin can view all articles.
	*/
	public static void findArticlesByDate(int emp_id, String emp_type) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter start date (YYYY-MM-DD):");
			String start_date_str = sc.nextLine();
			Date start_date = Date.valueOf(start_date_str);
			
			System.out.println("Enter end date (YYYY-MM-DD):");
			String end_date_str = sc.nextLine();
			Date end_date = Date.valueOf(end_date_str);
			
			if (emp_type.equals("ADMIN")) {
				rs = stmt.executeQuery("select * from Articles where article_date between '" + start_date + "' AND '"
						+ end_date + "';");
			} else if (emp_type.equals("JOURNALIST")) {
				rs = stmt.executeQuery(
						"select a.isbn,a.issue_wm,a.issue_year,a.article_id,a.article_date,a.title,a.topic,a.text from Articles a inner join WritesPerPub w on a.isbn=w.isbn and a.issue_wm = w.issue_wm and a.issue_year = w.issue_year where w.emp_id='"
								+ emp_id + "' and article_date between '" + start_date + "' AND '" + end_date + "';");
			} else if (emp_type.equals("EDITOR")) {
				rs = stmt.executeQuery(
						"select a.isbn,a.issue_wm,a.issue_year,a.article_id,a.article_date,a.title,a.topic,a.text from Articles a inner join EditsArticles e on a.isbn=e.isbn and a.issue_wm = e.issue_wm and a.issue_year = e.issue_year and a.article_id=e.article_id where e.emp_id='"
								+ emp_id + "' and article_date between '" + start_date + "' AND '" + end_date + "';");
			}

			System.out.println(
					"\n|\t isbn \t | \t issue_wm \t | \t issue_year \t | \t article_id \t | \t article_date \t | \t title \t | \t topic \t | \t text \t|");
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------------------------------------");
			while (rs.next()) {
				System.out.println("|\t" + rs.getInt(1) + "\t | \t" + rs.getInt(2) + "\t | \t" + rs.getInt(3) + "\t | \t"
						+ rs.getInt(4) + "\t | \t" + rs.getString(5) + "\t | \t" + rs.getString(6) + "\t | \t"
						+ rs.getString(7) + "\t | \t" + rs.getString(8) + " \t|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Find all books for a given date range of edition.
	* Author can view his/her assigned books.
	* Admin can view all books.
	*/
	public static void findBooksByDate(int emp_id, String emp_type) {
		try {
			Statement stmt = conn.createStatement();

			System.out.println("Enter start date (YYYY-MM-DD):");
			String start_date_str = sc.nextLine();
			Date start_date = Date.valueOf(start_date_str);
			
			System.out.println("Enter end date (YYYY-MM-DD):");
			String end_date_str = sc.nextLine();
			Date end_date = Date.valueOf(end_date_str);
			
			if (emp_type == "ADMIN") {
				rs = stmt.executeQuery("select p.isbn,p.title,p.genre,b.edition_date from Publications p \r\n" + 
						"inner join Books b \r\n" + 
						"on p.isbn = b.isbn where b.edition_date between '" + start_date + "' and '" + end_date + "';");
			} else if (emp_type == "AUTHOR") {
				rs = stmt.executeQuery("select p.isbn,p.title,p.genre,b.edition_date from Publications p \r\n" + 
						"inner join Books b\r\n" + 
						"inner join WritesBooks w \r\n" + 
						"on p.isbn = b.isbn and b.isbn = w.isbn and b.edition = w.edition where w.emp_id = '" + emp_id + "' and b.edition_date between '" + start_date + "' and '" + end_date + "';");
			}

			System.out.println(
					"\n|\t isbn \t | \t title \t | \t genre \t | \t edition_date \t |");
			System.out.println(
					"---------------------------------------------------------------------------------------------------------");
			while (rs.next()) {
				System.out.println("|\t" + rs.getInt(1) + "\t | \t" + rs.getString(2) + "\t | \t" + rs.getString(3) + "\t | \t"
						+ rs.getString(4) + "\t | \t");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* Description:
	* Find all chapters for a given topic
	* Author and editors can view his/her assigned chapters.
	* Admin can view all chapters.
	*/
	public static void findChaptersByTopic(int emp_id, String emp_type) {
		try {
			Statement stmt = conn.createStatement();
			System.out.println("Enter topic:");
			String topic = sc.nextLine();
			
			if (emp_type == "ADMIN") {
				rs = stmt.executeQuery("select * from Chapters where topic like \"%" + topic + "%\";");
			} else if (emp_type == "AUTHOR") {
				rs = stmt.executeQuery(
						"select c.isbn,c.edition,c.chapter_id,c.chapter_date,c.title,c.topic,c.text from Chapters c inner join WritesBooks w on c.isbn=w.isbn and c.edition = w.edition "
						+ " where w.emp_id='" + emp_id + "' and c.topic like \"%" + topic + "%\";");
			} else if (emp_type == "EDITOR") {
				rs = stmt.executeQuery(
						"select c.isbn,c.edition,c.chapter_id,c.chapter_date,c.title,c.topic,c.text from Chapters c inner join EditsChapters e on c.isbn=e.isbn and c.edition = e.edition "
						+ "and c.chapter_id=e.chapter_id where e.emp_id='" + emp_id + "' and c.topic like \"%" + topic + "%\";");
			}
			System.out.println(
					"\n|\t isbn \t | \t edition \t | \t chapter_id \t | \t chapter_date \t | \t title \t | \t topic \t | \t text \t|");
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------------------------------------");
			while (rs.next()) {
				System.out.println("|\t " + rs.getInt(1) + "\t | \t" + rs.getInt(2) + "\t | \t" + rs.getInt(3) + "\t | \t"
						+ rs.getString(4) + "\t | \t" + rs.getString(5) + "\t | \t" + rs.getString(6) + "\t | \t"
						+ rs.getString(7) + "\t |");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
