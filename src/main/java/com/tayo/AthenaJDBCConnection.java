package com.tayo;

import com.amazonaws.services.lambda.runtime.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Install library
 * mvn install:install-file -Dfile=AthenaJDBC41-1.0.0.jar -DgroupId=com.amazonaws.athena.jdbc.AthenaDriver -DartifactId=AthenaDriver -Dversion=1.0.0 -Dpackaging=jar
 */

public class AthenaJDBCConnection
{
    static final String athenaUrl = "jdbc:awsathena://athena.us-west-2.amazonaws.com:443";

    static final String myAthenaStg = "myathenastg";

    public static void handleRequest(Object input, Context context) {

        Connection conn = null;
        Statement statement = null;
        String staging_dir = System.getenv(myAthenaStg);
        System.out.println("Staging directory is " + staging_dir);

        try {
            Class.forName("com.amazonaws.athena.jdbc.AthenaDriver");
            Properties info = new Properties();
            info.put("s3_staging_dir", staging_dir);
            info.put("aws_credentials_provider_class","com.amazonaws.auth.DefaultAWSCredentialsProviderChain");



            String databaseName = "default";

            System.out.println("Connecting to Athena...");
            conn = DriverManager.getConnection(athenaUrl, info);

            System.out.println("Listing tables...");
            String sql = "show tables in "+ databaseName;
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                //Retrieve table column.
                String name = rs.getString("tab_name");

                //Display values.
                System.out.println("Name: " + name);
            }
            rs.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (Exception ex) {

            }
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
        System.out.printf("Finished connectivity test.");
    }
}
