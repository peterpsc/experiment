package com.pcconsulting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloDB {
	Connection conn;

	public HelloDB() throws ClassNotFoundException, SQLException {
		this("db/myDB");
	}

	public HelloDB(String dbNamePrefix) throws ClassNotFoundException, SQLException {
		Class.forName("org.hsqldb.jdbcDriver");
		conn = DriverManager.getConnection("jdbc:hsqldb:file:" + dbNamePrefix, // filenames
				"sa", // username
				""); // password
	}

	public static void main(String... args) throws ClassNotFoundException, SQLException {
		HelloDB helloDB = null;
		SQLException hadSe = null;
		ClassNotFoundException hadCnfe = null;
		try {
			helloDB = create(args);
			helloDB.run();
		} catch (SQLException se) {
			hadSe = se;
		} catch (ClassNotFoundException cnfe) {
			hadCnfe = cnfe;
		} finally {
			if (helloDB != null)
				helloDB.shutdown();
		}
		if (hadSe != null)
			throw hadSe;
		if (hadCnfe != null)
			throw hadCnfe;
	}

	private void run() throws SQLException {
		String expression = "SELECT CITY FROM CUSTOMER WHERE ID=6";
		Statement st = null;
		ResultSet rs = null;
		SQLException hadSe = null;
		try {
			st = conn.createStatement();

			rs = st.executeQuery(expression);

			dump(rs);

		} catch (SQLException se) {
			hadSe = se;
		} finally {
			if (st != null)
				st.close();
		}
		if (hadSe != null)
			throw hadSe;
	}

	private void dump(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int colmax = meta.getColumnCount();
		int i;
		Object o = null;

		for (; rs.next();) {
			for (i = 0; i < colmax; ++i) {
				o = rs.getObject(i + 1);
				System.out.print(o.toString() + " ");
			}

			System.out.println(" ");
		}
	}

	public void shutdown() throws SQLException {
		Statement st = conn.createStatement();

		st.execute("SHUTDOWN");
		conn.close(); // if there are no other open connection
	}

	private static HelloDB create(String... args) throws ClassNotFoundException, SQLException {
		String dbNamePrefix = "db/myDB";
		if (args.length > 0) {
			dbNamePrefix = args[0];
		}

		return new HelloDB(dbNamePrefix);
	}

}
