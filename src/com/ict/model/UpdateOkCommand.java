package com.ict.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ict.db.BVO;
import com.ict.db.DAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class UpdateOkCommand implements Command{
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		try {
			String path = request.getServletContext().getRealPath("/upload");
			MultipartRequest mr = new MultipartRequest(request,
					path, 100*1024*1024, "utf-8", new DefaultFileRenamePolicy());
			
			String cPage = mr.getParameter("cPage");
			
			BVO bvo = new BVO();
			bvo.setSubject(mr.getParameter("subject"));
			bvo.setWriter(mr.getParameter("writer"));
			bvo.setContent(mr.getParameter("content"));
			bvo.setPwd(mr.getParameter("pwd"));
			bvo.setB_idx(mr.getParameter("b_idx"));
			
			String f_name = mr.getParameter("f_name");
			
			if(mr.getFile("file_name") == null) {
				if(f_name==null) {
					bvo.setFile_name("");
				}else {
					bvo.setFile_name(f_name);
				}
			}else {
				bvo.setFile_name(mr.getFilesystemName("file_name"));
			}
			int result = DAO.getUpdate(bvo);
			
			if(result>0) {
				return "MyController?cmd=onelist&cPage="+cPage+"&b_idx="+bvo.getB_idx();
			}
		} catch (Exception e) {
		}
		return null;
	}
}
