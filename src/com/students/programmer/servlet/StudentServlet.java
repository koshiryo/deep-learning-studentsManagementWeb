package com.students.programmer.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.students.programmer.dao.StudentDao;
import com.students.programmer.model.Student;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StudentServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("toStudentListView".equals(method)){
			studentList(request,response);
		}else if("AddStudent".equals(method)){
			addStudent(request,response);
		}else if("StudentList".equals(method)){
			getStudentList(request,response);
		}else if("EditStudent".equals(method)){
			editStudent(request,response);
		}else if("DeleteStudent".equals(method)){
			deleteStudent(request,response);
		}
	}
	private void deleteStudent(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids[]");
		String idStr = "";
		for(String id : ids){
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
		StudentDao studentDao = new StudentDao();
		if(studentDao.deleteStudent(idStr)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				studentDao.closeCon();
			}
		}
	}
	private void editStudent(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		String sex = request.getParameter("sex");
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		int classId = Integer.parseInt(request.getParameter("classid"));
		Student student = new Student();
		student.setClassId(classId);
		student.setMobile(mobile);
		student.setName(name);
		student.setId(id);
		student.setQq(qq);
		student.setSex(sex);
		StudentDao studentDao = new StudentDao();
		if(studentDao.editStudent(student)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				studentDao.closeCon();
			}
		}
	}
	private void getStudentList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("studentName");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		Integer class = request.getParameter("classid") == null ? 0 : Integer.parseInt(request.getParameter("classid"));
		//��ȡ��ǰ��¼�û�����
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		Student student = new Student();
		student.setName(name);
		student.setclassId(class);
		if(userType == 2){
			//�����ѧ����ֻ�ܲ鿴�Լ�����Ϣ
			Student currentUser = (Student)request.getSession().getAttribute("user");
			student.setId(currentUser.getId());
		}
		StudentDao studentDao = new StudentDao();
		List<Student> classList = studentDao.getStudentList(student, new Page(currentPage, pageSize));
		int total = studentDao.getStudentListTotal(student);
		studentDao.closeCon();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", classList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(classList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void addStudent(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String mobile = request.getParameter("mobile");
		String qq = request.getParameter("qq");
		int classId = Integer.parseInt(request.getParameter("classid"));
		Student student = new Student();
		student.setClassId(classId);
		student.setMobile(mobile);
		student.setName(name);
		student.setPassword(password);
		student.setQq(qq);
		student.setSex(sex);
		student.setSn(SnGenerateUtil.generateSn(classId));
		StudentDao studentDao = new StudentDao();
		if(studentDao.addStudent(student)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				studentDao.closeCon();
			}
		}
	}
	private void studentList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try {
			request.getRequestDispatcher("view/studentList.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

