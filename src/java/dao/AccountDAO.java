/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modal.Account;
import modal.Feature;
import modal.Group;

/**
 *
 * @author Dell Inc
 */
public class AccountDAO {

    //get account by username and password
    public Account getAccount(String username, String password) throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        Account account = null;
        Group group = new Group();
        try {
            String sql = "select a.username, a.password, a.email , g.id as gid, g.name, f.id as fid, f.url \n"
                    + "from Account a left join [GroupAccount] ga on a.username = ga.username\n"
                    + "left join [Group] g on g.id = ga.gid\n"
                    + "left join FeatureGroup fg on fg.gid = g.id\n"
                    + "left join Feature f on f.id = fg.fid\n"
                    + "where a.username = ? and a.password = ?";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            rs = pre.executeQuery();
            while (rs.next()) {
                //set account for the first loop
                if (account == null) {
                    account = new Account();
                    account.setUsername(rs.getString("username"));
                    account.setPassword(rs.getString("password"));
                    account.setEmail(rs.getString("email"));
                    group.setId(rs.getInt("gid"));
                    group.setName(rs.getString("name"));
                    account.setGroup(group);
                }
                Feature feature = new Feature();
                feature.setId(rs.getInt("fid"));
                feature.setUrl(rs.getString("url"));
                group.getFeatures().add(feature);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
        return account;
    }

    public String checkTeacherAccount(String username) throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "select g.name from  GroupAccount ga , [Group] g \n"
                    + "where g.id = ga.gid and ga.username=?";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, username);
            rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getString(1);

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
        return null;
    }

    public boolean checkUsernameExist(String username) throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [username]\n"
                    + "      ,[password]\n"
                    + "      ,[email]\n"
                    + "  FROM [Online_Quiz].[dbo].[Account]\n"
                    + "  where username = ?";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, username);
            rs = pre.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
        return false;
    }

    public void insertAccount(Account account) throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO [Online_Quiz].[dbo].[Account]\n"
                    + "           ([username]\n"
                    + "           ,[password]\n"
                    + "           ,[email])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, account.getUsername());
            pre.setString(2, account.getPassword());
            pre.setString(3, account.getEmail());
            pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
    }

    public void insertAccountGroup(int gid, String username) throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO [Online_Quiz].[dbo].[GroupAccount]\n"
                    + "           ([gid]\n"
                    + "           ,[username])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, gid);
            pre.setString(2, username);
            pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
    }

    public static void main(String[] args) throws Exception {
        AccountDAO a = new AccountDAO();
        System.out.println(a.checkTeacherAccount("a"));
    }
}
