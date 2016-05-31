/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package BizLogic;

import DataAccess.DataBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAssembly {
	private static DataBase db;
	private ResultSet rs = null;

	public SQLAssembly() {
		db = new DataBase();
	}

	public ResultSet getRecordSet(String column, String table, String condition) {
		String SQL = null;
		SQL = "SELECT " + column + " FROM " + table;
		if (condition.length() > 0) {
			SQL = SQL + " WHERE " + condition;
		}
		this.rs = db.getSelect(SQL);
		return this.rs;
	}

	public int setUpdate(String table, String setStr, String condition) {
		int result = -1;
		String SQL = null;
		SQL = "Update " + table + " set " + setStr + " where " + condition;
		result = db.setUpdate(SQL);
		return result;
	}

	public int setInsert(String table, String column, String value) {
		int result = -1;
		String SQL = null;
		SQL = "Insert " + table;
		if (column.length() > 0) {
			SQL = SQL + " (" + column + ") values (" + value + ")";
		} else {
			SQL = SQL + " values (" + value + ")";
		}
		result = db.setUpdate(SQL);
		return result;
	}

	public String setDel(String table, String condition) {
		int result = -1;
		String SQL = null;
		SQL = "Delete from " + table + " Where " + condition;

		return SQL;
	}

	public String IsExist(String column, String table, String condition) {
		boolean result = false;
		String SQL = null;
		SQL = "Select count(" + column + ") as cnt From " + table + " where "
				+ condition;

		return SQL;
	}

	public String getLastInsert() {
		String result = null;
		String SQL = null;
		SQL = "select last_insert_id() as gid";
		result = db.getLastInsert(SQL);
		return result;
	}

	public String getId(String column, String table, String condition) {
		String result = null;
		String SQL = null;
		SQL = "Select " + column + " From " + table + " Where " + condition;

		return SQL;
	}

	public String callProcMaster(String storeProc, String parameters) {
		String SQL = null;

		SQL = "{call " + storeProc + "(" + parameters + ")}";

		String maId = db.executeProcedure(SQL);
		return maId;
	}

	public String wrtCpuSession2Db(cpu_session_all_list CSAList, String maId)
			throws SQLException {
		String result = null;
		String sql = "";

		int column = -1;
		column = CSAList.getColumLv();

		if (column == 1) {
			sql = "insert into cpu_session_all (ctId,sessions,session_port1,session_port2,cpu_load,free_mem,swap,ip,cpu_wait,cpu_saturation) values (?,?,?,?,?,?,?,?,?,?)";
		} else if (column == 0) {
			sql = "insert into cpu_session_all (ctId,sessions,session_port1,session_port2,cpu_load,free_mem,swap,ip) values (?,?,?,?,?,?,?,?)";
		}

		PreparedStatement tmpPs = db.getPsStatement(sql);

		for (int i = 0; i < CSAList.getElementNo(); ++i) {
			tmpPs.setInt(1, Integer.parseInt(maId));
			tmpPs.setInt(2, Integer.parseInt(CSAList.getCSA(i).getSessions()));
			tmpPs.setInt(3,
					Integer.parseInt(CSAList.getCSA(i).getSession_port1()));
			tmpPs.setInt(4,
					Integer.parseInt(CSAList.getCSA(i).getSession_port2()));
			tmpPs.setString(5, CSAList.getCSA(i).getCpu_load());
			tmpPs.setString(6, CSAList.getCSA(i).getFree_mem());
			tmpPs.setString(7, CSAList.getCSA(i).getSwap());
			tmpPs.setString(8, CSAList.getCSA(i).getIp());
			if (column == 1) {
				tmpPs.setInt(9,
						Integer.parseInt(CSAList.getCSA(i).getCpu_wait()));
				tmpPs.setInt(10,
						Integer.parseInt(CSAList.getCSA(i).getCpu_saturation()));
			}
			tmpPs.addBatch();
		}

		db.executeBatchInsert(tmpPs);
		return result;
	}

	public String wrtCsoSession2Db(cso_session_all_list CSOList, String maId)
			throws SQLException {
		String result = null;
		String sql = "";

		int column = -1;
		column = CSOList.getColumNo();

		if (column == 1) {
			sql = "insert into cso_session_local_all (ctId,sessions,cso_sec,failure_ratio,nso_cso) values (?,?,?,?,?)";
		} else if (column == 0) {
			sql = "insert into cso_session_local_all (ctId,sessions,cso_sec) values (?,?,?)";
		}

		PreparedStatement tmpPs = db.getPsStatement(sql);

		for (int i = 0; i < CSOList.getCsoNo(); ++i) {
			tmpPs.setInt(1, Integer.parseInt(maId));
			tmpPs.setInt(2, Integer.parseInt(CSOList.getCSLA(i).getSessions()));
			tmpPs.setDouble(3,
					Double.parseDouble(CSOList.getCSLA(i).getCso_sec()));
			if (column == 1) {
				tmpPs.setString(4, CSOList.getCSLA(i).getFailure_ratio());
				tmpPs.setFloat(5,
						Float.parseFloat(CSOList.getCSLA(i).getNso_cso()));
			}
			tmpPs.addBatch();
		}

		db.executeBatchInsert(tmpPs);
		return result;
	}

	public void shutResource() {
		db.closeResource();
	}
}