/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modal.Answer;
import modal.Question;

/**
 *
 * @author Dell Inc
 */
public class QuestionDAO {

    public void insertQuestion(String username, String content, Date created) throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO [Online_Quiz].[dbo].[Question]\n"
                    + "           ([username]\n"
                    + "           ,[content]\n"
                    + "           ,[created])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, content);
            pre.setDate(3, created);
            pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
    }

    public void deleteQuestion(int qid) throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "DELETE FROM [Online_Quiz].[dbo].[Question]\n"
                    + "      WHERE qid = ?";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, qid);
            pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
    }

    public int getLatestQid() throws Exception {
        DBContext db = new DBContext();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT top 1 [qid]\n"
                    + "FROM [Online_Quiz].[dbo].[Question]\n"
                    + "order by qid desc";
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            if(rs.next()){
                return rs.getInt("qid");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
        return -1;
    }

    public int countQuestion() throws Exception {
        DBContext db = null;
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(qid) as number FROM Question";
            db = new DBContext();
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("number");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
        return 0;
    }

    public ArrayList<Question> getListQuestions(int pageIndex, int pageSize) throws Exception {
        DBContext db = null;
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        ArrayList<Question> listQuestions = new ArrayList<>();
        try {
            String sql = "select [qid],[username],[content],[created] from\n"
                    + "(select ROW_NUMBER() over (order by created desc)\n"
                    + "as rid, [qid]\n"
                    + "	  ,[username]\n"
                    + "      ,[content]\n"
                    + "      ,[created] from Question) tbl\n"
                    + "where rid between ? and ?";
            db = new DBContext();
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            int first = (pageIndex - 1) * pageSize + 1;
            int last = pageIndex * pageSize;
            pre.setInt(1, first);
            pre.setInt(2, last);
            rs = pre.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setQid(rs.getInt("qid"));
                question.setUsername(rs.getString("username"));
                question.setContent(rs.getString("content"));
                question.setDate(rs.getDate("created"));
                listQuestions.add(question);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
        return listQuestions;
    }

    public ArrayList<Question> getQuestions(int number) throws Exception {
        DBContext db = null;
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        AnswerDAO answerDAO = new AnswerDAO();
        ArrayList<Question> listQuestions = new ArrayList<>();
        try {
            String sql = "SELECT top (?) [qid]\n"
                    + "      ,[username]\n"
                    + "      ,[content]\n"
                    + "      ,[created]\n"
                    + "  FROM [Online_Quiz].[dbo].[Question]\n"
                    + "  order by NEWID()";
            db = new DBContext();
            con = db.getConnection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, number);
            rs = pre.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setQid(rs.getInt("qid"));
                question.setUsername(rs.getString("username"));
                question.setContent(rs.getString("content"));
                question.setDate(rs.getDate("created"));
                ArrayList<Answer> answers = answerDAO.getAnswers(rs.getInt("qid"));
                question.setAnswers(answers);
                listQuestions.add(question);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(con, pre, rs);
        }
        return listQuestions;
    }
    public static void main(String[] args) {
      
    }
}
