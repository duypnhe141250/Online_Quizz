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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modal.Answer;

/**
 *
 * @author Dell Inc
 */
public class AnswerDAO {

    public void insertAnswer(Answer answer) throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO [Online_Quiz].[dbo].[Answer]\n"
                    + "           ([qid]\n"
                    + "           ,[content]\n"
                    + "           ,[isTrue])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, answer.getQid());
            pre.setString(2, answer.getContent());
            pre.setBoolean(3, answer.isIsTrue());
            pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
    }

    public void deleteAnswer(int aid) throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "DELETE FROM [Online_Quiz].[dbo].[Answer]\n"
                    + "      WHERE aid = ?";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, aid);
            pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
    }

    public ArrayList<Answer> getAnswers(int qid) throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        ArrayList<Answer> answers = new ArrayList<>();
        try {
            String sql = "SELECT [aid]\n"
                    + "      ,[qid]\n"
                    + "      ,[content]\n"
                    + "      ,[isTrue]\n"
                    + "  FROM [Online_Quiz].[dbo].[Answer]\n"
                    + "  where qid = ?";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, qid);
            rs = pre.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setAid(rs.getInt("aid"));
                answer.setContent(rs.getString("content"));
                answer.setIsTrue(rs.getBoolean("isTrue"));
                answers.add(answer);
            }
        } catch (Exception ex) {
            throw ex;
        }finally{
            db.closeConnection(con, pre, rs);
        }
        return answers;
    }
}
