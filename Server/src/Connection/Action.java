package Connection;

import DBConnection.ConnectionClass;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.StreamSupport;

public class Action {
    public void execute(Handler handler, String action) throws IOException, ClassNotFoundException, SQLException {
        switch (action) {
            case "enterStudent": {
                String log = (String)handler.read();
                String pass = (String)handler.read();
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String query = "SELECT * FROM scholarship.Student WHERE login = ? AND password = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, log);
                preparedStmt.setString(2, pass);
                ResultSet data = preparedStmt.executeQuery();
                if(data.next()) {
                    handler.write(String.valueOf(data.getInt("idStudent")));
                }
                else {
                    handler.write("Nothing");
                }
                connection.close();
                break;
            }
            case "enterDean": {
                String log = (String)handler.read();
                String pass = (String)handler.read();
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String query = "SELECT * FROM scholarship.Deanery WHERE login = ? AND password = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, log);
                preparedStmt.setString(2, pass);
                ResultSet data = preparedStmt.executeQuery();
                if(data.next()) {
                    handler.write(String.valueOf(data.getInt("idDeanery")));
                }
                else {
                    handler.write("Nothing");
                }
                connection.close();
                break;
            }
            case "enterAccountant": {
                String log = (String)handler.read();
                String pass = (String)handler.read();
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String query = "SELECT * FROM scholarship.Accountant WHERE login = ? AND password = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, log);
                preparedStmt.setString(2, pass);
                ResultSet data = preparedStmt.executeQuery();
                if(data.next()) {
                    handler.write(String.valueOf(data.getInt("idAccountant")));
                }
                else {
                    handler.write("Nothing");
                }
                connection.close();
                break;
            }

            case "regAccountant": {
                String log = (String)handler.read();
                String pass = (String)handler.read();
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String count = "SELECT COUNT(*) FROM scholarship.Accountant";
                Statement statement = connection.createStatement();
                ResultSet count_acc_set = statement.executeQuery(count);
                count_acc_set.next();
                int next_id = count_acc_set.getInt("COUNT(*)");
                String query = "INSERT INTO scholarship.Accountant (idAccountant, name, lastName, patronymic, phone, login, password) VALUES (?, 'Anon', 'Anon', 'Anon', 'None', ?, ?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, next_id + 1);
                preparedStmt.setString(2, log);
                preparedStmt.setString(3, pass);
                preparedStmt.execute();
                connection.close();
                break;
            }

            case "returnId": {
                Integer id = (Integer) handler.read();
                handler.write(id);
                break;
            }

            case "loadExamInfo": {
                Integer student_id = (Integer) handler.read();

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String query = "SELECT session FROM scholarship.student WHERE idStudent = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, student_id);
                ResultSet res = preparedStmt.executeQuery();
                res.next();

                int session_num = res.getInt("session");

                String query1 = "SELECT * FROM scholarship.exam WHERE Session = ?";
                PreparedStatement preparedStmt1 = connection.prepareStatement(query1);
                preparedStmt1.setInt(1, session_num);
                ResultSet res1 = preparedStmt1.executeQuery();
                while (res1.next()) {
                    ArrayList r = new ArrayList();
                    r.add(res1.getString("subject"));
                    r.add(res1.getString("date"));
                    r.add(res1.getString("mark"));
                    System.out.println(r.get(0));
                    System.out.println(r.get(1));
                    System.out.println(r.get(2));
                    handler.write(r);
                    r.clear();
                }
                ArrayList r = new ArrayList();
                r.clear();
                r.add("stop");
                handler.write(r);
                break;
            }

            case "loadBankInfo": {
                Integer student_id = (Integer) handler.read();

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String query = "SELECT name, lastName, patronymic, Scholarship_idScholarship, bankCard FROM scholarship.student WHERE idStudent = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, student_id);
                ResultSet res = preparedStmt.executeQuery();
                res.next();

                int scholarship_id = res.getInt("Scholarship_idScholarship");

                String query1 = "SELECT * FROM scholarship.scholarship WHERE idScholarship = ?;";
                PreparedStatement preparedStmt1 = connection.prepareStatement(query1);
                preparedStmt1.setInt(1, scholarship_id);
                ResultSet res1 = preparedStmt1.executeQuery();
                while (res1.next()) {
                    ArrayList r = new ArrayList();
                    r.add(res.getString("lastName") + " " + res.getString("name") + " " + res.getString("patronymic"));
                    r.add(res.getString("bankCard"));
                    r.add(res1.getString("date"));
                    System.out.println(r.get(0));
                    System.out.println(r.get(1));
                    System.out.println(r.get(2));
                    handler.write(r);
                    r.clear();
                }
                ArrayList r = new ArrayList();
                r.clear();
                r.add("stop");
                handler.write(r);
                break;
            }
            case "loadScholarshipInfo": {
                Integer student_id = (Integer) handler.read();

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String query = "SELECT Scholarship_idScholarship FROM scholarship.student WHERE idStudent = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, student_id);
                ResultSet res = preparedStmt.executeQuery();
                res.next();

                int scholarship_id = res.getInt("Scholarship_idScholarship");

                String query1 = "SELECT * FROM scholarship.scholarship WHERE idScholarship = ?;";
                PreparedStatement preparedStmt1 = connection.prepareStatement(query1);
                preparedStmt1.setInt(1, scholarship_id);
                ResultSet res1 = preparedStmt1.executeQuery();
                while (res1.next()) {
                    ArrayList r = new ArrayList();
                    r.add(res1.getString("type"));
                    r.add(res1.getString("amount"));
                    System.out.println(r.get(0));
                    System.out.println(r.get(1));
                    handler.write(r);
                    r.clear();
                }
                ArrayList r = new ArrayList();
                r.clear();
                r.add("stop");
                handler.write(r);
                break;
            }
            case "loadShowStudentInfo": {
                // Принимаем логин декана для недопущения ошибки
                Integer dean_id = (Integer) handler.read();
                System.out.println(dean_id);

                // Выбираем всех студентов
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();

                // Добавляем inner join session и faculty чтобы мы сразу получали avgMark и faculty.name (не нужно писать отдельные запрос)
                String query = "SELECT student.*, session.avgMark AS avg, faculty.name AS facname FROM scholarship.student INNER JOIN scholarship.session on Idsession=student.session INNER JOIN scholarship.faculty on idFaculty=student.faculty;";
                Statement statement = connection.createStatement();
                ResultSet students = statement.executeQuery(query);
                System.out.println(students);

                // Прогоняем всех студентов
                while(students.next()) {
                    System.out.println("next");
                    ArrayList r = new ArrayList();
                    r.add(students.getString("name"));
                    r.add(students.getString("lastName"));
                    r.add(students.getString("patronymic"));
                    r.add(students.getString("group"));
                    r.add(students.getString("recordBook"));
                    r.add(students.getString("facname"));
                    r.add(students.getString("avg"));
                    handler.write(r);
                    r.clear();
                }

                // Посылаем сообщение о конце считывания
                ArrayList r = new ArrayList();
                r.clear();
                r.add("stop");
                handler.write(r);
                break;
            }
        }
    }
}
