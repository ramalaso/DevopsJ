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

import java.sql.*;

/**
 * Implements Query Manager classes.
 *
 * @author Fernando Hinojosa on 10/02/2019
 * @version v1.0
 */
public class QueryManager {
    private Connection connection;

    /**
     * Create the connection with the DB
     */
    public QueryManager() {
        connection = DBConnection.getInstance().getConnection();
    }

    /**
     * This classes permit insert values in the table FileStorage
     * @param checksum
     * @param path
     * @return method returns as a boolean
     */
    public boolean insert (String checksum, String path) {
        String sql = "INSERT INTO FileStorage (checksum,path)" + "VALUES (?,?)";
        boolean query = false;
        try  (PreparedStatement state = connection.prepareStatement(sql)){
            state.setString(1,checksum);
            state.setString(2,path);
            query = state.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * This classes gethPath the checksum if exist.
     * @param checksum
     * @return a String with the path if exist.
     */

    public String getPath (String checksum) {

        Statement state = null;
        ResultSet resultSet = null;
        String path = "";
        try{
            String sql = "Select path from FileStorage where trim(checksum)='" + checksum +"';";
            state = connection.createStatement();
            resultSet = state.executeQuery(sql);
            while (resultSet.next()) {
                path = resultSet.getString("path");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return path;
    }

}
