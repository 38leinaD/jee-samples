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
// Wildfly
/*
@DataSourceDefinition(
    name = "java:app/jdbc/primary",
    className = "org.postgresql.xa.PGXADataSource",
    user = "${db.user:postgres}",
    password = "postgres",
    serverName = "${db.serverName:postgres}",
    portNumber = 5432,
    databaseName = "postgres",
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
