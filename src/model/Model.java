package model;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import entity.ELine;
import entity.EStation;
import entity.ETimetable;
import entity.ETown;
import entity.LineType;
import entity.MissingSpriteException;
import requirement.util.Requirements;

/**
 * An implementation of the {@link IModel} interface.
 *
 * @author Alex Mandelias
 * @author Dimitris Tsirmpas
 */
public class Model implements IModel {

	private static final String URL  = "jdbc:mysql://localhost/oasaripoff"; //$NON-NLS-1$
	private static final String USER = "***";                               //$NON-NLS-1$
	private static final String PASS = "***";                               //$NON-NLS-1$

	@FunctionalInterface
	private interface SQLExecutable<S extends Statement, R> {
		R execute(S statement) throws SQLException;
	}

	private static <R> R executeStatement(SQLExecutable<Statement, R> executable)
	        throws SQLException {
		try (Connection conn = DriverManager.getConnection(Model.URL, Model.USER, Model.PASS);
		        Statement stmt = conn.createStatement();) {
			return executable.execute(stmt);
		}
	}

	private static <R> R executePrepared(String sql, SQLExecutable<PreparedStatement, R> executable)
	        throws SQLException {
		try (Connection conn = DriverManager.getConnection(Model.URL, Model.USER, Model.PASS);
		        PreparedStatement stmt = conn.prepareStatement(sql);) {
			return executable.execute(stmt);
		}
	}

	/**
	 * Constructs a Model that communicates with the underlying database to retrieve
	 * data and return them using Entity objects from the {@link entity} package.
	 */
	public Model() {}

	@Override
	public List<ETown> getTowns(Requirements reqs) throws SQLException {
		// TODO Auto-generated method stub
		return null;

		/*
		Example use of the private static methods and interface:

		@foff
		return executePrepared("SELECT * FROM City WHERE name REGEXP=?",
		        (PreparedStatement s) -> {

		            s.setString(0, reqs.getValue("regex", String.class));
			        List<ETown> towns = new LinkedList<>();
			        try (ResultSet rs = s.executeQuery()) {
				        while (rs.next()) {
					        towns.add(new ETown(rs.getString("name")));
				        }
			        }
			        return towns;
		});
		@on
		*/
	}

	@Override
	public List<ELine> getLines(Requirements reqs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EStation> getStations(Requirements reqs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage getVehicleSprite(LineType type) throws MissingSpriteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertLine(ELine line) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertTown(ETown town) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertStation(EStation station) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertTimetable(ELine line, ETimetable timetable) throws SQLException {
		// TODO Auto-generated method stub

	}

}
