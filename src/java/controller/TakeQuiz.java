/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.QuestionDAO;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Account;
import modal.Answer;
import modal.Question;

/**
 *
 * @author Dell Inc
 */
public class TakeQuiz extends BaseAuthentication {

    private Hashtable<String, ArrayList<Question>> questions;
    private Hashtable<String, Long> startTimes;
    private long DELAYTIME = 500;

    public TakeQuiz() {
        this.questions = new Hashtable<String, ArrayList<Question>>();
        this.startTimes = new Hashtable<String, Long>();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            QuestionDAO questiondao = new QuestionDAO();
            int questionTotal = questiondao.countQuestion();
            Account account = (Account) request.getSession().getAttribute("account");
            // Take number of question that user want to take 
            if (request.getParameter("number") == null) {
                request.getRequestDispatcher("quizNumber.jsp").forward(request, response);
            } else {
                int number = -1;
                try {
                    number = Integer.parseInt(request.getParameter("number"));
                } catch (NumberFormatException ex) {
                    request.setAttribute("result", "Please enter Integer!");
                    request.getRequestDispatcher("quizNumber.jsp").forward(request, response);
                }
                //number of question must greater than 0 and less than or equal to question total
                if (number > questionTotal || number < 1) {
                    request.setAttribute("result", "Please enter the number from 1 to" + questionTotal);
                    request.getRequestDispatcher("quizNumber.jsp").forward(request, response);
                } else {
                    //set new question for every times that take  new number of question
                    if (request.getSession().getAttribute("duration") == null
                            || request.getSession().getAttribute("duration") != null
                            && number != this.questions.get(account.getUsername()).size()) {
                        long startTime = System.currentTimeMillis();
                        this.startTimes.put(account.getUsername(), startTime);
                        this.questions.put(account.getUsername(), questiondao.getQuestions(number));

                        long duration = this.questions.get(account.getUsername()).size() * 60 * 1000;
                        request.getSession().setAttribute("duration", duration);
                        request.setAttribute("questions", this.questions.get(account.getUsername()));
                        request.setAttribute("timeLabel", formatTime(duration));
                        request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
                    } else {
                        long duration = (Long) request.getSession().getAttribute("duration");
                        long currentTime = System.currentTimeMillis();
                        long takedTime = currentTime - this.startTimes.get(account.getUsername()) - DELAYTIME;
                        long remainTime = duration - takedTime;

                        //take time is not greater than duration
                        if (remainTime <= 0) {
                            request.setAttribute("result", "Your result has been rejected!");
                            request.getRequestDispatcher("result.jsp").forward(request, response);
                        } else {
                            request.setAttribute("timeLabel", formatTime(remainTime));
                            request.setAttribute("questions", this.questions.get(account.getUsername()));
                            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! There is an error now");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Account account = (Account) request.getSession().getAttribute("account");
            String result = request.getParameter("result");

            long currentTime = System.currentTimeMillis();
            long startTime = this.startTimes.get(account.getUsername());
            long duration = (Long) request.getSession().getAttribute("duration");
            long takedTime = currentTime - startTime - DELAYTIME;

            //takedTime must less than or equal to duration
            if (takedTime > duration) {
                request.setAttribute("result", "Your result has been rejected!");
                request.getRequestDispatcher("result.jsp").forward(request, response);
            } else {
                float score = calculateScore(result.split(" "), account.getUsername()) * 10;
                DecimalFormat df = new DecimalFormat("#.#");
                String scoreFormat = df.format(score);
                String percent = df.format((Float.parseFloat(scoreFormat)* 10));
                String displayScore = scoreFormat + " (" + percent +"%) - ";
                displayScore += score < 5 ? "Not Pass" : "Passed";
                request.setAttribute("displayScore", displayScore);
                request.getRequestDispatcher("result.jsp").forward(request, response);
            }
            request.getSession().removeAttribute("duration");
        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! There is an error now");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    public float calculateScore(String[] results, String userName) {
        int number = 0;
        ArrayList<String> trueAnswers = new ArrayList<>();
        ArrayList<String> falseAnswers = new ArrayList<>();
        //loop each question to check correct answer or not
        for (Question question : this.questions.get(userName)) {
            //loop each answer of each question to get true answer and false answer
            for (Answer answer : question.getAnswers()) {
                //add correct answer in trueAnswer
                if (answer.isIsTrue()) {
                    trueAnswers.add(Integer.toString(answer.getAid()));
                } else {
                    falseAnswers.add(Integer.toString(answer.getAid()));
                }
            }
            // check that answer is correct or not in each corresponding question
            if(checkAnswer(results, trueAnswers, falseAnswers)){
                ++number;
            }
            trueAnswers.removeAll(trueAnswers);
            falseAnswers.removeAll(falseAnswers);
        }
        return (float) number / this.questions.get(userName).size();
    }
    
    public boolean checkAnswer(String[] results, ArrayList<String> trueAnswer,
            ArrayList<String> falseAnswer){
        int numberOfTrueAnswer = 0;
        //loop each false answer of each question
        for (String falseA : falseAnswer) {
            //loop each result of all answer that user selected
            for (String result : results) {
                //false answer should not be included in the results
                if(falseA.equals(result)){
                    return false;
                }
            }
        }
        //loop each true answer of each question
        for (String trueA : trueAnswer) {
            //loop each result of all answer that user selected
            for (String result : results) {
                //true answer should be included in the results
                if(trueA.equals(result)){
                    ++numberOfTrueAnswer;
                }
            }
        }
        return numberOfTrueAnswer == trueAnswer.size();
    }

    public String formatTime(long millis) {
        long minutes = (millis / 1000) / 60;
        long seconds = (millis / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
