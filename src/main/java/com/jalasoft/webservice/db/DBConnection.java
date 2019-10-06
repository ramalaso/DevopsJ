/**
 * Copyright (c) 2019 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Implements DB connection
 *
 * @author Fernando Hinojosa on 10/02/2019
 * @version v1.0
 */
public class DBConnection {
    private static DBConnection connection;
    private static Connection dbconnection;

    /**
     * This classes init the DBConnection.
     */
    private DBConnection() {
        this.init();
    }

    /**
     * This classes return the DBconnection.
     * @return the dbconnection.
     */
    public Connection getConnection(){
        return dbconnection;
    }

    /**
     * This classes set the connection with the DB.
     */
    private void init() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            dbconnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/webservicedb","root","");
        }catch(Exception e){ e.printStackTrace();}
    }

    /**
     * This classes verify if the DBConnection exits.  
     * @return
     */
    public static DBConnection getInstance() {
        try {
            if (connection == null) {
                connection = new DBConnection();
            } else if (connection.getConnection().isClosed()) {
                connection = new DBConnection();
            }
        }
        catch (Exception e){ e.printStackTrace();}
        return connection;
    }
}
