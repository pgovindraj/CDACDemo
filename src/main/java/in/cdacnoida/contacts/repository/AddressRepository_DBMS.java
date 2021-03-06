package in.cdacnoida.contacts.repository;

import in.cdacnoida.contacts.entities.Address;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

public class AddressRepository_DBMS {

	private final DataSource ds;

	public AddressRepository_DBMS() {
		try {
			Context context = new InitialContext();
			try {
				ds = (DataSource) context
						.lookup("java:comp/env/jdbc/trainingdb");
			} finally {
				context.close();
			}
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}

	}

	public void create(Address address) throws SQLException {

		Connection connection = ds.getConnection();
		try {
			Statement statement = connection.createStatement();
			try {
				statement.executeUpdate(
						"insert into address(street,city,state,zip) values('"
								+ address.getStreet() + "','"
								+ address.getCity() + "','"
								+ address.getState() + "','" + address.getZip()
								+ "')", Statement.RETURN_GENERATED_KEYS);

				ResultSet generatedKeys = statement.getGeneratedKeys();
				try {
					if (generatedKeys.next()) {
						address.setId(generatedKeys.getLong("id"));
					}
				} finally {
					generatedKeys.close();
				}

			} finally {
				statement.close();
			}
		} finally { // Very Important
			connection.close();
		}

	}

	// Checked Exception
	public void init() throws SQLException {
		Connection connection = ds.getConnection();
		try {
			Statement statement = connection.createStatement();
			try {
				statement
						.execute("create table Address (id bigint generated by default as identity (start with 1), city varchar(255), state varchar(255), street varchar(255), zip varchar(255), primary key (id))");

			} finally {
				statement.close();
			}
		} finally { // Very Important
			connection.close();
		}

	}

	public Address find(long id) throws SQLException {

		Connection connection = ds.getConnection();
		try {
			Statement statement = connection.createStatement();
			try {
				ResultSet results = statement
						.executeQuery("Select * from address where id= " + id);
				try {
					if (!results.next()) {
						return null;
					} else {
						return unmarshall(results);
					}
				} finally {
					results.close();
				}
			} finally {
				statement.close();
			}
		} finally {
			connection.close();
		}
	}

	private static Address unmarshall(ResultSet result) throws SQLException {
		Address address = new Address();
		address.setId(result.getLong("id"));
		address.setStreet(result.getString("street"));
		address.setCity(result.getString("city"));
		address.setZip(result.getString("zip"));
		return address;
	}

}
