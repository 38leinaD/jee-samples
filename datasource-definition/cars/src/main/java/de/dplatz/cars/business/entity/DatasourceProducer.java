package de.dplatz.cars.business.entity;

import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

@Singleton
// Payara
@DataSourceDefinition(
        name = "java:app/jdbc/primary",
        className = "org.postgresql.xa.PGXADataSource",
        user = "${ENV=DB_USER}",
        password = "${ENV=DB_PASSWORD}",
        serverName = "${ENV=DB_SERVERNAME}",
        portNumber = 5432,
        databaseName = "${ENV=DB_DATABASENAME}",
        minPoolSize = 10,
        maxPoolSize = 50)
// Wildfly (works)
/*
@DataSourceDefinition(
    name = "java:app/jdbc/primary",
    className = "org.postgresql.xa.PGXADataSource",
    user = "postgres",
    password = "postgres",
    serverName = "${DB_SERVERNAME:postgres}",
    portNumber = 5432,
    databaseName = "${DB_DATABASENAME:postgres}",
    minPoolSize = 10,
    maxPoolSize = 50)
    */
// Wildfly (bug; not working currently)
/*
@DataSourceDefinition(
    name = "java:app/jdbc/primary",
    className = "org.postgresql.xa.PGXADataSource",
    user = "${DB_USER:postgres}",
    password = "${DB_PASSWORD:postgres}",
    serverName = "${DB_SERVERNAME:postgres}",
    portNumber = 5432,
    databaseName = "${DB_DATABASENAME:postgres}",
    minPoolSize = 10,
    maxPoolSize = 50)
*/
public class DatasourceProducer {
	
	@Resource(lookup="java:app/jdbc/primary")
	DataSource ds;
	
	@Produces
	public DataSource getDatasource() {
		return ds;
	}	
}
