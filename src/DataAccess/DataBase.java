/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package DataAccess;

import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	private static String driver = "com.mysql.jdbc.Driver";

	private String spath = null;
	private String dbAccess = null;
	private String dbUrl;
	private String user;
	private String pwd;
	private Connection conn = null;
	private Connection connBatch = null;

	private ResultSet rs = null;
	private Statement stmt = null;
	private PreparedStatement ps = null;
	private Fileio fileAcc;

	public DataBase() {
		try {
			this.fileAcc = new Fileio();
			this.dbUrl = this.fileAcc.getConfServer(0);
			this.user = this.fileAcc.getConfServer(1);
			this.pwd = this.fileAcc.getConfServer(2);

			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private String getDbUrl() {
		return this.dbUrl;
	}

	private String getDbUsername() {
		return this.user;
	}

	private String getDbPassword() {
		return this.pwd;
	}

	private Connection getConnection() throws SQLException {
		if ((this.conn == null) || (this.conn.isClosed())) {
			try {
				this.conn = DriverManager.getConnection(getDbUrl(),
						getDbUsername(), getDbPassword());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Get connection error");
			}
		}

		return this.conn;
	}

	private Connection getBatchConn() throws SQLException {
		if ((this.connBatch == null) || (this.connBatch.isClosed())) {
			try {
				this.connBatch = DriverManager.getConnection(getDbUrl(),
						getDbUsername(), getDbPassword());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return this.connBatch;
	}

	private void setDone(Connection conn) throws SQLException {
		if (conn == null)
			return;
		conn.close();
		conn = null;
	}

	private Statement getStatement() {
		try {
			this.stmt = getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(getDbUrl() + getDbUsername() + getDbPassword());
			System.out.println("Get statment error");
		}
		return this.stmt;
	}

	private void setCloseStmt(Statement stmt) throws SQLException {
		if (stmt == null)
			return;
		stmt.close();
		stmt = null;
	}

	public PreparedStatement getPsStatement(String SQL) {
		try {
			this.ps = getBatchConn().prepareStatement(SQL);
			getBatchConn().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.ps;
	}

	public void setClosePsStmt(PreparedStatement stmt) throws SQLException {
		if (stmt == null)
			return;
		stmt.close();
		stmt = null;
	}

	public ResultSet getSelect(String sql) {
		try {
			this.rs = getStatement().executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.rs;
	}

	public int setUpdate(String sql) {
		int result = 0;
		try {
			result = getStatement().executeUpdate(sql);
			setCloseStmt(this.stmt);
			setDone(this.conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean IsExist(String sql) {
		boolean result = false;
		int count = 0;
		try {
			this.rs = getStatement().executeQuery(sql);
			this.rs.next();
			count = Integer.parseInt(this.rs.getString(1));
			setCloseStmt(this.stmt);
			setDone(this.conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count > 0) {
			result = true;
		}
		return result;
	}

	public String getLastInsert(String sql) {
		String lastid = null;
		try {
			this.rs = getStatement().executeQuery(sql);
			this.rs.next();
			lastid = this.rs.getString(1);
			setCloseStmt(this.stmt);
			setDone(this.conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastid;
	}

	public String getid(String sql) {
		String id = null;
		try {
			this.rs = getStatement().executeQuery(sql);
			this.rs.next();
			id = this.rs.getString(1);
			setCloseStmt(this.stmt);
			setDone(this.conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public void closeResource() {
		try {
			setCloseStmt(this.stmt);
			setDone(this.conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String executeProcedure(String SQL) {
		String tmpRst = null;
		try {
			CallableStatement callstmt = getConnection().prepareCall(SQL);
			this.rs = callstmt.executeQuery();
			if (this.rs.next()) {
				tmpRst = this.rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tmpRst;
	}

	public void executeBatchInsert(PreparedStatement tmpPs) {
		try {
			this.ps = tmpPs;
			this.ps.executeBatch();
			getBatchConn().commit();
			this.ps.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}