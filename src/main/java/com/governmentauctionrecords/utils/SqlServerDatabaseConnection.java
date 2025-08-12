package com.governmentauctionrecords.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * To configure SQL Server connection with JDBC driver on Windows,
 * follow these steps:
 *
 * 1. Go to SQL Server Configuration Manager
 * 2. Click on SQL Server Services
 * 3. Right-click SQL Server Browser and go to Properties
 * 4. Click on the Service tab
 * 5. Set Start Mode to Automatic
 * 6. Click on Apply, then click on OK
 * 7. Again right-click on SQL Server Browser, and click on Start
 *    (SQL Server Browser service must be reachable by JDBC)
 * 8. Right-click on SQL Server (SQLEXPRESS)
 * 9. Click on Restart
 * 10. Now go to SQL Server Network Configuration, then Protocols for SQLEXPRESS
 * 11. Right-click on TCP/IP and click on Enable
 *     (JDBC driver requires TCP/IP enabled to connect over the network)
 * 12. Now Java can connect with SQL Server using JDBC
 *
 * In case of error: "This driver is not configured for integrated authentication"
 * follow these steps:
 *
 * 1. This error means your Microsoft JDBC driver can’t do Windows Integrated Authentication
 *    (i.e., integratedSecurity=true) because the required native DLL is missing or not found.
 * 2. Find sqljdbc_auth.dll (e.g., mssql-jdbc_auth-12.10.1.x64 located in:
 *    C:\Users\carlo\Downloads\sqljdbc_12.10.1.0_enu\sqljdbc_12.10\enu\auth\x64)
 * 3. Copy the correct sqljdbc_auth.dll (x64 or x86) to your JAVA_HOME\bin directory
 *    (where java.exe resides, e.g., C:\Program Files\Java\jdk-16.0.2\bin).
 * 4. Restart your IDE.
 */
public class SqlServerDatabaseConnection {

    private static final String SERVER = "DESKTOP-E185THU\\SQLEXPRESS";
    private static final String DATABASE = "GovernmentAuctionRecords";
    private static final String USERNAME = null; // Or null if using integrated security
    private static final String PASSWORD = null; // Or null if using integrated security

    private static final String CONNECTION_STRING =
        "jdbc:sqlserver://" + SERVER + ";" +
        "databaseName=" + DATABASE + ";" +
        "encrypt=false;" +
        "trustServerCertificate=true;";

    // Track if we’ve already logged metadata
    private static boolean metadataLogged = false;

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQL Server JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn;

        if (USERNAME != null && PASSWORD != null) {
            conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
        } else {
            // Integrated Security (requires sqljdbc_auth.dll in PATH or JAVA_HOME/bin)
            conn = DriverManager.getConnection(CONNECTION_STRING + "integratedSecurity=true;");
        }

        if (!metadataLogged && conn != null) {
            logMetadata(conn);
            metadataLogged = true;
        }

        return conn;
    }

    private static void logMetadata(Connection conn) {
        try {
            DatabaseMetaData dm = conn.getMetaData();
            System.out.println("Driver name: " + dm.getDriverName());
            System.out.println("Driver version: " + dm.getDriverVersion());
            System.out.println("Product name: " + dm.getDatabaseProductName());
            System.out.println("Product version: " + dm.getDatabaseProductVersion());
        } catch (SQLException e) {
            System.err.println("Unable to retrieve database metadata: " + e.getMessage());
        }
    }
}