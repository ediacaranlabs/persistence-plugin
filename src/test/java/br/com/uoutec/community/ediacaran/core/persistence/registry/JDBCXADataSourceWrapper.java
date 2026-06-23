package br.com.uoutec.community.ediacaran.core.persistence.registry;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.Xid;

import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.hsqldb.jdbc.pool.JDBCXAResource;

public class JDBCXADataSourceWrapper implements XADataSource {

	private JDBCXADataSource o;
	
	public int hashCode() {
		return o.hashCode();
	}

	public PrintWriter getLogWriter() throws SQLException {
		return o.getLogWriter();
	}

	public XAConnection getXAConnection() throws SQLException {
		return o.getXAConnection();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		o.setLogWriter(out);
	}

	public XAConnection getXAConnection(String user, String password) throws SQLException {
		return o.getXAConnection(user, password);
	}

	public boolean equals(Object obj) {
		return o.equals(obj);
	}

	public Reference getReference() throws NamingException {
		return o.getReference();
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		o.setLoginTimeout(seconds);
	}

	public void addResource(Xid xid, JDBCXAResource xaResource) {
		o.addResource(xid, xaResource);
	}

	public int getLoginTimeout() throws SQLException {
		return o.getLoginTimeout();
	}

	public JDBCXAResource removeResource(Xid xid) {
		return o.removeResource(xid);
	}

	public String getDescription() {
		return o.getDescription();
	}

	public String getDataSourceName() {
		return o.getDataSourceName();
	}

	public String getNetworkProtocol() {
		return o.getNetworkProtocol();
	}

	public String getServerName() {
		return o.getServerName();
	}

	public String getDatabaseName() {
		return o.getDatabaseName();
	}

	public String getDatabase() {
		return o.getDatabase();
	}

	public String getUrl() {
		return o.getUrl();
	}

	public String getURL() {
		return o.getURL();
	}

	public String getUser() {
		return o.getUser();
	}

	public void setDatabaseName(String databaseName) {
		o.setDatabaseName(databaseName);
	}

	public void setDatabase(String database) {
		o.setDatabase(database);
	}

	public void setUrl(String url) {
		o.setUrl(url);
	}

	public void setURL(String url) {
		o.setURL(url);
	}

	public void setPassword(String password) {
		o.setPassword(password);
	}

	public void setUser(String user) {
		o.setUser(user);
	}

	public void setProperties(Properties props) {
		o.setProperties(props);
	}

	public String toString() {
		return o.toString();
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return o.getParentLogger();
	}

	public JDBCXADataSourceWrapper() throws SQLException {
		this.o = new JDBCXADataSource();
	}
	
}
