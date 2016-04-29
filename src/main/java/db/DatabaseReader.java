package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseReader {
	private Statement db;
	
	public DatabaseReader(Statement db) {
		this.db = db;
	}
	
	/**
	 * Returns the number of genomes that the given segment is part of, or -1 if
	 * no segment with the given id exists.
	 * 
	 * @param segmentID
	 *            Id of the segment.
	 * @return the number of genomes that the given segment is part of, or -1 if
	 *         no segment with the given id exists.
	 */
	public int countGenomesInSeg(int segmentID) {
		try {
			ResultSet rs = this.db.executeQuery("SELECT COUNT(GENOMEID) FROM GENOMESEGMENTLINK WHERE SEGMENTID = "  + segmentID);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Returns the number of genomes in the link between fromID and toID, given
	 * that a link exists between the 2. Returns -1 if no link exists.
	 * 
	 * @param fromID
	 *            Segment id.
	 * @param toID
	 *            Segment id.
	 * @return The number of genomes in the link from fromID to toID, or -1 if
	 *         the link does not exist.
	 */
	public int countGenomesInLink(int fromID, int toID) {
		try {
			String query = new StringBuilder()
				.append("SELECT COUNT(G1) ")
				.append("FROM (")
				.append("SELECT * FROM (")
				.append("SELECT FROMID, TOID ")
				.append("FROM LINKS ")
				.append("WHERE FROMID = " + fromID +" AND TOID = " + toID + " ")
				.append(") AS T1 ")
				.append("LEFT OUTER JOIN (SELECT SEGMENTID AS S1, GENOMEID AS G1 FROM GENOMESEGMENTLINK) AS GSL ")
				.append("ON T1.FROMID = GSL.S1 ")
				.append("LEFT OUTER JOIN (SELECT SEGMENTID AS S2, GENOMEID AS G2 FROM GENOMESEGMENTLINK) AS GSL2 ")
				.append("ON T1.TOID = GSL2.S2")
				.append(") ")
				.append("WHERE G1 = G2")
				.toString();
			ResultSet rs = this.db.executeQuery(query);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Returns all id's of the segments that have one or more outgoing links.
	 * them.
	 * 
	 * @return All id's of the segments that have one or more outgoing links.
	 */
	public ArrayList<Integer> getAllFromID() {
		try {
			ResultSet rs = this.db.executeQuery("SELECT * FROM LINKS");
			ArrayList<Integer> fromIDList = new ArrayList<Integer>();
			while(rs.next()) {
				fromIDList.add(rs.getInt(1));
			 }
			return fromIDList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns all id's of the segments that have one or more ingoing links.
	 * 
	 * @return All id's of the segments that have one or more ingoing links.
	 */
	public ArrayList<Integer> getAllToID() {
		try {
			ResultSet rs = this.db.executeQuery("SELECT * FROM LINKS");
			ArrayList<Integer> toIDList = new ArrayList<Integer>();
			while(rs.next()) {
				toIDList.add(rs.getInt(2));
			 }
			return toIDList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	/**
	 * Returns all id's of the links entering the segment with the given id, or
	 * null if no segment exists with the given id.
	 * 
	 * @param fromID
	 *            Segment id.
	 * @return All id's of the links entering the segment with the given id, or
	 *         null if no segment exists with the given id.
	 */
	public ArrayList<Integer> getFromIDs(int toID) {
		try {
			ResultSet rs = this.db.executeQuery("SELECT FROMID FROM LINKS WHERE TOID = " + toID);
			ArrayList<Integer> fromIDList = new ArrayList<Integer>();
			while(rs.next()) {
				fromIDList.add(rs.getInt(1));
			 }
			return fromIDList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns all id's of the links leaving the segment with the given id, or
	 * null if no segment exists with the given id.
	 * 
	 * @param fromID
	 *            Segment id.
	 * @return All id's of the links leaving the segment with the given id, or
	 *         null if no segment exists with the given id.
	 */
	public ArrayList<Integer> getToIDs(int fromID) {
		try {
			ResultSet rs = this.db.executeQuery("SELECT TOID FROM LINKS WHERE FROMID = " + fromID);
			ArrayList<Integer> toIDList = new ArrayList<Integer>();
			while(rs.next()) {
				toIDList.add(rs.getInt(1));
			 }
			return toIDList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns the contents of the segment with the given id, or null if no
	 * segment with the given id exists.
	 * 
	 * @param segmentID
	 *            the id of the segment of which to return the contents.
	 * @return The contents of the segment with the given id, or null if no
	 *         segment with the given id exists.
	 */
	public String getContent(int segmentID) {
		try {
			ResultSet rs = this.db.executeQuery("SELECT CONTENT FROM SEGMENTS WHERE ID = " + segmentID);
			rs.next();
			return rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}