package admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class webpage {
	String script = "";
	SqlSession se = null;
	PrintWriter pw = null;
	ResultSet rs = null;

	@Autowired
	public BasicDataSource dataSource;
	
	@Inject SqlSessionFactory sqlSessionFactory;
	
	@Resource
	private SqlSessionTemplate sqlSession;
	
	@Resource(name="admins")	
	private admin_moduler mm;
	
	@RequestMapping("/homepage.do")
	public String home_footer(@RequestParam(defaultValue = "1", required = false) String sidx, Model m) {
		
		List<siteinfo_dto> dto = mm.footer(sidx);	
		
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		
		for(int i=0; i < dto.size(); i++) {
			jo.put("1", dto.get(i).getSite_corpnm());
			jo.put("2", dto.get(i).getSite_corpno());
			jo.put("3", dto.get(i).getSite_ceo());
			jo.put("4", dto.get(i).getSite_tel());
			jo.put("5", dto.get(i).getSite_number1());
			jo.put("6", dto.get(i).getSite_number2());
			jo.put("7", dto.get(i).getSite_post());
			jo.put("8", dto.get(i).getSite_add());
			jo.put("9", dto.get(i).getSite_admin());
			jo.put("10", dto.get(i).getSite_admail());
		}
		
		String data = jo.toJSONString();
		m.addAttribute("data",data);
		
		return "script";
	}
	
	//티켓 선택 삭제
	@GetMapping("/ticket_cancel.do")
	public String ticket_cancel(Model m, @RequestParam String tidx[]) {
		
		int result = mm.ticket_cancel(tidx);
		if(result > 0) {
			this.script = "<script>alert('정상적으로 취소되었습니다.'); location.href = './ticket_list.do';</script>";
		}
		m.addAttribute("script", script);
		
		return "script";
	}
	
	
	//티켓 예매 리스트
	@RequestMapping("/ticket_list.do")
	public String ticket_list(Model m,
			@RequestParam(defaultValue = "", required = false) String search,
			@RequestParam(defaultValue = "", required = false) String part
			) {
		
		List<ticket_dto> all = mm.ticket_list();
		
		if(part.equals("") && search.equals("")) {	
			all = mm.ticket_list();	
		}
		else {
			m.addAttribute("username", search);
			m.addAttribute("part", part);
			
			if(part.equals("1")) {						//고객명
				all = mm.search_ticket(search,1);
			}
			else if (part.equals("2")) {				//연락처
				all = mm.search_ticket(search,2);
			}
		}
	
		m.addAttribute("all",all);

		return "ticketing";
	}
	
	
	//공지사항 선택 삭제
	@GetMapping("/code_check_del.do")
	public String code_ck_del(Model m, @RequestParam String aidx[]) {
		
		int result = mm.code_ck_del(aidx);
		if(result > 0) {
			this.script = "<script>alert('정상적으로 삭제되었습니다.'); location.href = './air_list.do';</script>";
		}
		m.addAttribute("script", script);
		
		return "script";
	}
	
	
	//코드 삭제
	@GetMapping ("/code_delete.do")
	public String code_delete(@RequestParam(defaultValue = "", required = false) String aidx, Model m) {
		
		int result = mm.code_delete(aidx);
		if(result > 0) {
			this.script = "<script>alert('정상적으로 삭제 되었습니다.');location.href='./air_list.do';</script>";
		}
		else {
			this.script = "<script>alert('비정상적으로 활용되었습니다.');location.href='./air_list.do';</script>";
		}
		m.addAttribute("script",script);
		
		return "script";
	}
	
	
	//코드 수정 완료
	@RequestMapping("/code_modifyok.do")
	
	public String code_modiok(Model m, air_code_dto dto) {
		int result = mm.code_modify(dto);
			 
		if(result > 0) {
			this.script = "<script>alert('정상적으로 수정 되었습니다.');location.href='./air_list.do';</script>";
		}
		else {
			this.script = "<script>alert('데이터오류로 인하여 수정이 되지 않았습니다.'); history.go(-1);</script>";
		}
		
		m.addAttribute("script",script);
		return "script";
	}
	
	//코드 수정
	@RequestMapping("/code_modify.do")
	public String code_modi(Model m, @RequestParam(defaultValue = "",required = false) String aidx) {
		
		air_code_dto dto = mm.code_view(aidx);		
		List<Object> one = new ArrayList<Object>();
		
		one.add(dto.getAidx());		//0
		one.add(dto.getAirplane());	//1
		one.add(dto.getAirname());	//2
		one.add(dto.getAircode());	//3
		one.add(dto.getAirflight());//4
		one.add(dto.getAiruse());	//5
		
		m.addAttribute("one",one);
	
		return "admin_category_modify";
	}
	
	//코드 중복체크
	@RequestMapping("/codeck.do")
	public void code_ck(@RequestParam String aircode, HttpServletResponse res)throws Exception {
		
		this.pw = res.getWriter();
		String check = null;
		int result = mm.code_ck(aircode);
		
		if (result == 0) {
			check ="ok";
		}
		else {
			check = "no";
		}
		pw.print(check);
		this.pw.close();
		
	}
	

	//승인
	@GetMapping("/ok.do")
	public String login_ok (Model m, @RequestParam String aidx, @RequestParam String aid) {
		
		int result = mm.log_ok(aidx);
		mm.log_count_reset(aid);
		
		if(result > 0) {
			this.script = "<script>alert('승인 되었습니다.');location.href='./admin_main.do';</script>";
		}
		else {
			this.script = "<script>alert('데이터오류로 인하여 승인 되지 않았습니다.'); history.go(-1);</script>";
		}
		m.addAttribute("script",script);
		
		return "script";
	}
	
	//미승인
	@GetMapping("/no.do")
	public String login_no (Model m, @RequestParam String aidx) {
		
		int result = mm.log_no(aidx);
		
		if(result > 0) {
			this.script = "<script>alert('미승인 되었습니다.');location.href='./admin_main.do';</script>";
		}
		else {
			this.script = "<script>alert('데이터오류로 인하여 작동되지 않았습니다.'); history.go(-1);</script>";
		}
		m.addAttribute("script",script);
		
		return "script";
	}

	//Faq 수정
	@RequestMapping("/faq_modifyok.do")
	public String faq_modi(Model m, faq_dto dto) {
		int result = mm.faq_modify(dto);
			 
		if(result > 0) {
			this.script = "<script>alert('정상적으로 수정 되었습니다.');location.href='./faq_list.do';</script>";
		}
		else {
			this.script = "<script>alert('데이터오류로 인하여 수정이 되지 않았습니다.'); history.go(-1);</script>";
		}
		
		m.addAttribute("script",script);
		return "script";
	}
	
	//Faq 보기 
	@RequestMapping("/faq_view.do")
	public String faq_view(Model m, @RequestParam(defaultValue = "",required = false) String fidx) {

		faq_dto dto = mm.faq_view(fidx);		
		
		List<Object> one = new ArrayList<Object>();
		
		one.add(dto.getFsubject());	//0
		one.add(dto.getFwriter());	//1
		one.add(dto.getFtext());	//2
		one.add(dto.getFidx());		//3
		
		m.addAttribute("one",one);
		System.out.println(one);
		
		return "faq_modify";
	}
	
	//Faq 삭제
	@GetMapping ("/faq_delete.do")
	public String faq_delete(@RequestParam(defaultValue = "", required = false) String fidx, Model m) {
		
		int result = mm.faq_delete(fidx);
		if(result > 0) {
			this.script = "<script>alert('정상적으로 삭제 되었습니다.');location.href='./faq_list.do';</script>";
		}
		else {
			this.script = "<script>alert('비정상적으로 활용되었습니다.');location.href='./faq_list.do';</script>";
		}
		m.addAttribute("script",script);
		
		return "script";
	}
	
	//Faq 작성 ok 페이지
	@RequestMapping ("/faq_writeok.do")
	public String faq_writeok(faq_dto dto, Model m) {
		
		int result = mm.faq_write(dto);
		if(result > 0) {
			this.script = "<script>alert('정상적으로 글이 등록되었습니다.'); location.href='./faq_list.do';</script>";
		}
		else {
			this.script = "<script>alert('FAQ 등록에 문제가 발생하였습니다.'); location.histroy.go(-1);</script>";
		}
		m.addAttribute("script",script);
		
		return "script";
	}
	
	//Faq 작성 페이지
	@RequestMapping("/faq_write.do")
	public String faq_write(Model m) {
		return null;
	}
	
	//Faq list
	@RequestMapping("/faq_list.do")
	public String faq_list(Model m, @RequestParam(defaultValue = "", required = false) String search) {
		
		List<faq_dto> all = mm.faq_list();
		if(search.equals("")) {	
			all = mm.faq_list();	
		}
		else {
			m.addAttribute("faqname", search);
			all= mm.search_faq(search);
		}
		m.addAttribute("all",all);
		
		return null;
	}
	
	
	//JSON API 페이지
	@RequestMapping("/aircode_json.do")
	public String aircode_json (Model m) {
		
		List<air_code_dto> all = mm.code_list();
		JSONArray ja = new JSONArray();
		int a = 0;
		
		while(all.size() > a) {
		
			JSONObject jo = new JSONObject();	//부속키 생성
			jo.put("airplane", all.get(a).getAirplane());
			jo.put("airname", all.get(a).getAirname());
			jo.put("aircode", all.get(a).getAircode());
			jo.put("airflight", all.get(a).getAirflight());
			ja.add(jo);	//1차 배열로 그룹
			
			a++;
		}
		
		//대표키 생성
		JSONObject jo2 = new JSONObject();
		jo2.put("aircode", ja);
		String result = jo2.toJSONString();
		
		m.addAttribute("result", result);
		return null;
	}
	
	
	//항공코드 등록 페이지
	@RequestMapping("/ari_newcodeok.do")
	public String product_codeok (Model m, air_code_dto dto) {
		int code = mm.aircode(dto);
 
		if(code > 0) {
			this.script = "<script>alert('정상적으로 코드가 등록되었습니다.');location.href='./air_list.do';</script>";
		}
		else {
			this.script = "<script>alert('시스템 오류로 인하여 등록에 실패했습니다.');location.href='./ari_newcode.do';</script>";
		}
		
		m.addAttribute("script",script);
		return "script";
	}
	
	
	//항공편 등록 페이지
	@RequestMapping("/product_new.do")
	public String product_air(Model m, air_plane_dto dto) {
		int airwr = mm.airplane(dto);
		
		if(airwr > 0) {
			this.script = "<script>alert('정상적으로 항공편이 등록되었습니다.'); location.href='./product_list.do';</script>";
		}
		else {
			this.script = "<script>alert('시스템 오류로 인하여 등록에 실패했습니다.'); location.href='./product_insert.do';</script>";
		}
		
		m.addAttribute("script",script);
		return "script";
	}
	

	//항공코드 페이지
	@RequestMapping("/ari_newcode.do")
	public String product_code(Model m) {
		return "admin_category_write";
	}
	
	//항공편 페이지
	@RequestMapping("/product_insert.do")
	public String product_new (Model m) {
		return "admin_product_write";
	}
	
	//항공코드 리스트
	@RequestMapping("/air_list.do")
	public String aircode_list(Model m,
			@RequestParam(defaultValue = "", required = false) String search,
			@RequestParam(defaultValue = "", required = false) String part
			) {
		
		List<air_code_dto> all = mm.code_list();
		
		if(part.equals("") && search.equals("")) {	
			all = mm.code_list();	
		}
		else {
			m.addAttribute("airname", search);
			m.addAttribute("part", part);
			
			if(part.equals("1")) {						//항공사명
				all = mm.search_code(search,1);
			}
			else if (part.equals("2")) {				//항공코드
				all = mm.search_code(search,2);
			}
		}
	
		m.addAttribute("all",all);

		return "admin_category";
	}
	
	//항공편 리스트
	@RequestMapping("/product_list.do")
	public String airplane_list(Model m,
			@RequestParam(defaultValue = "", required = false) String search,
			@RequestParam(defaultValue = "", required = false) String part
			) {
		
		
		List<air_plane_dto> all = mm.airplane_list();
		
		if(part.equals("") && search.equals("")) {	
			all = mm.airplane_list();	
		}
		else {
			m.addAttribute("airname", search);
			m.addAttribute("part", part);
			
			if(part.equals("1")) {						//항공명
				all = mm.search_airplane(search,1);
			}
			else if (part.equals("2")) {				//항공코드
				all = mm.search_airplane(search,2);
			}
		}
	
		m.addAttribute("all",all);
		return "admin_product";
	}
	
	//사이트 정보 수정
	@PostMapping("/admin_siteinfook.do")
	public String siteinfook(siteinfo_dto dto, Model m) {
		
		int data = mm.site_update(dto);
		String script = "";
		if(data > 0) {
			script = "<script>location.href='./admin_siteinfo.do';</script>";
		}
		m.addAttribute("script", script);
		return "script";
	}
	
	//사이트 정보
	@RequestMapping("/admin_siteinfo.do")
	public String siteinfo(@RequestParam(defaultValue = "1", required = false) String sidx, Model m) {
		
		siteinfo_dto dto = mm.siteinfo(sidx);	
		
		ArrayList<Object> onedata = new ArrayList<Object>();
		
		onedata.add(dto.getSidx()); 			//0
		onedata.add(dto.getSite_title());		
		onedata.add(dto.getSite_email());		
		onedata.add(dto.getSite_point()); 		
		onedata.add(dto.getSite_reserves());	
		onedata.add(dto.getSite_level());		//5
		onedata.add(dto.getSite_corpnm());		
		onedata.add(dto.getSite_corpno());		
		onedata.add(dto.getSite_ceo());			
		onedata.add(dto.getSite_tel());
		onedata.add(dto.getSite_number1());		//10
		onedata.add(dto.getSite_number2());
		onedata.add(dto.getSite_post());
		onedata.add(dto.getSite_add());
		onedata.add(dto.getSite_admin());
		onedata.add(dto.getSite_admail());		//15
		onedata.add(dto.getSite_banknm());
		onedata.add(dto.getSite_bankno());
		onedata.add(dto.getPay_card());
		onedata.add(dto.getPay_hp());
		onedata.add(dto.getPay_book());			//20
		onedata.add(dto.getPay_min_point());
		onedata.add(dto.getPay_max_point());
		onedata.add(dto.getPay_paper());
		
		m.addAttribute("one", onedata);
		
		return null;
	}
	
	
	
	//공지사항 선택 삭제
	@GetMapping("/notice_check_del.do")
	public String notice_ck_del(Model m, @RequestParam String nidx[], @RequestParam(defaultValue = "", required = false) String url[]) {
		int ea = url.length;
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("utf-8");
		FTPClientConfig cf = new FTPClientConfig();
		try {

			String host = "";
			String user = "";
			String pass = "";
			int port = 21;
			ftp.configure(cf);
			ftp.connect(host,port);
			if(ftp.login(user, pass)) {
				for(int a = 0; a < ea; a++) {
					boolean delok = ftp.deleteFile("/www/"+url[a]);
					if(delok == true) {
						System.out.println("정상적으로 파일이 삭제되었습니다.");
					} else {
						System.out.println("경로 또는 파일문제로 인하여 삭제되지않았습니다.");
					}
				}
			} else {
				System.out.println("FTP 접속 오류 발생!!");
			}
		} catch (Exception e) {
			System.out.println("FTP 접속 정보 오류");
		} finally {
			try {
				ftp.disconnect();
			} catch (Exception e2) {
				System.out.println("CDN 서버");
			}
		}
		
		int result = mm.notice_ck_del(nidx);
		if(result > 0) {
			this.script = "<script>alert('정상적으로 삭제되었습니다.'); location.href = './notice_list.do';</script>";
		}
		m.addAttribute("script", script);
		
		return "script";
	}
	
	
	//게시물 수정
	@PostMapping("/notice_modifyok.do")
	public String notice_modifyok(@ModelAttribute("board") notice_dao dao, @RequestParam String nidx ,@RequestParam("userfile")MultipartFile files, HttpServletRequest req, Model m,@RequestParam String exfile) {
		
		
		if(files.getOriginalFilename() != "") {	// 신규 첨부파일 있을 경우
			String file_name = files.getOriginalFilename();
			long file_size = files.getSize();
			
			
			if(file_size > 2097152) {	// 용량 초과 되었을 경우
				this.script = "<script>"
						+ "alert('첨부파일은 2MB이하로 사용하셔야합니다.');"
						+ "history.go(-1);"
						+ "</script>";
			}
			else { //첨부파일 용량이 맞을 경우
				cdn_ftp(files,m);
				
				try {
					FileCopyUtils.copy(files.getBytes(), new File(cdn_ftp(files,m)));
					
					cdn_file_delete(exfile);
					dao.setNattach(file_name);
					
					int result = mm.notice_modify(dao);
					if(result > 0) {
						this.script = "<script>"
								+ "alert('정상적으로 게시물이 수정 완료되었습니다.');"
								+ "location.href='./notice_list.do';"
								+ "</script>";
					}
					else {
						this.script = "<script>"
								+ "alert('데이터오류로 인하여 수정이 되지 않았습니다.');"
								+ "history.go(-1);"
								+ "</script>";
					}
				}
				catch(Exception e) {
					e.getStackTrace();
					System.out.println("데이터베이스 접속오류 및 첨부파일 경로 오류!"+e);
				}
			}
			
		}
		else {	//신규 첨부파일 없을 경우 (기존파일)
			try {
				
				int result = mm.notice_modify_x(dao);

				if(result > 0) {
					this.script = "<script>"
							+ "alert('정상적으로 게시물이 수정 완료되었습니다.');"
							+ "location.href='./notice_list.do';"
							+ "</script>";
				}
				else {
					this.script = "<script>"
							+ "alert('데이터오류로 인하여 수정이 되지 않았습니다.');"
							+ "history.go(-1);"
							+ "</script>";
				}
			}
			catch(Exception e) {
				e.getStackTrace();
				System.out.println("Database 오류!2323"+e);
			}
			
		}
		m.addAttribute("script", this.script);
		return "script";
	}
	
	
	//게시물 삭제
	@GetMapping ("/notice_delete.do")
	public String notice_delete(@RequestParam(defaultValue = "", required = false) String nidx, Model m, @RequestParam(defaultValue = "", required = false) String url) {
		
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("utf-8");
		FTPClientConfig cf = new FTPClientConfig();
		try {

			String host = "";
			String user = "";
			String pass = "";
			int port = 21;
			ftp.configure(cf);
			ftp.connect(host,port);
			if(ftp.login(user, pass)) {
					boolean delok = ftp.deleteFile("/www/"+url);
					if(delok == true) {
						System.out.println("정상적으로 파일이 삭제되었습니다.");
					} else {
						System.out.println("경로 또는 파일문제로 인하여 삭제되지않았습니다.");
					}
				
			} else {
				System.out.println("FTP 접속 오류 발생!!");
			}
		} catch (Exception e) {
			System.out.println("FTP 접속 정보 오류");
		} finally {
			try {
				ftp.disconnect();
			} catch (Exception e2) {
				System.out.println("CDN 서버");
			}
		}
	
		if(nidx.equals("") || nidx == "") {	//파라미터 값이 정상이 아닐 경우
			this.script = "<script>"
					+ "alert('정상적인 접근이 아닙니다.');"
					+ "location.href = './notice_list.do';"
					+ "</script>";
		}
		else {			// 정상적으로 파라미터가 적용될 경우
			try {
				
				this.se = sqlSessionFactory.openSession();
				int result = this.se.delete("admin_DB.notice_delete", nidx);
				
				if(result > 0) {
					this.script = "<script>"
							+ "alert('정상적으로 게시물이 삭제 되었습니다.');"
							+ "location.href='./notice_list.do';"
							+ "</script>";
				}
				else {
					this.script = "<script>"
							+ "alert('비정상적으로 활용되었습니다.');"
							+ "location.href='./notice_list.do';"
							+ "</script>";
				}
			}
			catch(Exception e) {
				System.out.println("Database 접속 오류!");
			}
			finally {
				this.se.close();
			}
		}
		m.addAttribute("script", this.script);
		
		return "script";
	}
	
	
	//게시물 수정 띄우기
	@GetMapping("/notice_modify.do")
	public String notice_modify(@RequestParam(defaultValue = "", required = false) String nidx, @RequestParam int num, Model m) {

		if(nidx.equals("") || nidx == "") {
			this.script = "<script>"
					+ "alert('정상적인 접근이 아닙니다.');"
					+ "location.href = './notice_list.do';"
					+ "</script>";
		}
		else {
			try {
				this.script = ""; // 초기화
				
				notice_dao dao = mm.admin_notice_view(nidx);	
		
				
				ArrayList<Object> onedata = new ArrayList<Object>();
				onedata.add(dao.getNidx()); 	//0
				onedata.add(dao.getNtop());		//1
				onedata.add(dao.getNsubject());	//2
				onedata.add(dao.getNwrite()); 	//3
				onedata.add(dao.getNattach());	//4
				onedata.add(dao.getNtext());	//5
				
				m.addAttribute("one", onedata);
				
			}
			catch(Exception e){
				System.out.println("DB 연결 오류");
			}
		}
		m.addAttribute("script", this.script);
		m.addAttribute("num",num);
		
		return "admin_notice_modify";
	}
	
	
	//공지사항 보기
	@RequestMapping("/notice_view.do")
	public String admin_notice_view(Model m, @RequestParam(defaultValue = "",required = false) String nidx, @RequestParam int num) {

		try {
			//조회수 증가
			int result = mm.notice_count(nidx);
			
			if(result > 0) { 
				notice_dao dao = mm.admin_notice_view(nidx);
				
				List<Object> all = new ArrayList<Object>();
				all.add(dao.getNidx());		//0
				all.add(dao.getNtop());		//1
				all.add(dao.getNsubject());	//2
				all.add(dao.getNwrite());	//3
				all.add(dao.getNtext());	//4
				all.add(dao.getNnum());		//5
				all.add(dao.getNdate());	//6
				all.add(dao.getNattach());	//7
				
				m.addAttribute("all",all);
				m.addAttribute("num",num);
					
			}
			else { //조회수 실패시
				System.out.println("조회 실패");
			}
			
		}
		catch(Exception e){
			e.getStackTrace();
			System.out.println("데이터베이스 오류!");
		}
		return "admin_notice_view";
	}
	
	
	//공지사항 작성
	@PostMapping ("notice_writeok.do")
	public String notice_writeok(@ModelAttribute("board") notice_dao notice_dao, @RequestParam("userfile") MultipartFile files, HttpServletRequest req, Model m) {
		
		String file_url = "";

		file_url= files.getOriginalFilename();
		notice_dao.setNattach(file_url);
		
		int success = mm.admin_notice_write(notice_dao);
		long filesize = files.getSize();

		String url = "";
	
		if(filesize > 2097152) { // 2MB => byte
			System.out.println("첨부파일 용량 초과!");
		}
		else {
			cdn_ftp(files,m);
			url = req.getServletContext().getRealPath("") + "notice/" + file_url;
			
			if(success > 0) {
				try {
					FileCopyUtils.copy(files.getBytes(), new File(url));
					
					
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("파일 업로드 오류 발생");
				}
				this.script = "<script>alert('공지사항 등록이 정상적으로 완료되었습니다.');"
						+ "location.href='./notice_list.do';</script>";	
			
			}
			
			else {
				this.script = "<script>alert('공지사항 등록에 문제가 발생하였습니다.');"
						+ "location.histroy.go(-1);</script>";
			}
		}

		m.addAttribute("script", script);	
		return "script";
	}
	
	

	public String cdn_ftp(MultipartFile mfile, Model m) {

		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("utf-8"); 
		FTPClientConfig cf = new FTPClientConfig();
		
		try {
			String filename = mfile.getOriginalFilename();
			String host = "";
			String user = "";
			String pass = "";
			int port = 21;
			ftp.configure(cf);
			ftp.connect(host, port);
			
			if(ftp.login(user, pass)) {
				ftp.setFileType(FTP.BINARY_FILE_TYPE);		//이미지, 동영상, PPT

				int result = ftp.getReplyCode();
				boolean ok = ftp.storeFile("/www/"+filename, mfile.getInputStream());
				
				if(ok == true) {
					System.out.println("정상적으로 업로드 되었습니다.");
					m.addAttribute("imgs", filename);
				}
				else {
					System.out.println("FTP 경로 오류발생 되었습니다.");
				}
			}
			else {
				System.out.println("FTP 정보가 올바르지 않습니다.");
			}
			
		} 
		catch (Exception e) {
			System.out.println("FTP 접속 정보 오류 및 접속 사용자 아이디/패스워드 오류");
		}
		finally {
			try {
				ftp.disconnect();
			} catch (Exception e2) {
				System.out.println("서버 루프로 인하여 접속종료 장애가 있음");
			}
		}
		return "script";
	}
	
	public boolean cdn_file_delete(@RequestParam(defaultValue = "", required = false) String url) {
		
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("utf-8");
		FTPClientConfig cf = new FTPClientConfig();
		boolean delok = false;
		try {
			String host = "";
			String user = "";
			String pass = "";
			int port = 21;
			ftp.configure(cf);
			ftp.connect(host, port);
			
				if(ftp.login(user, pass)) {
					delok = ftp.deleteFile("/www/"+url);
					if(delok == true) {
						System.out.println("정상적으로 파일이 삭제되었습니다.");
					}
					else {
						System.out.println("경로 또는 파일 문제로 인하여 삭제되지 않았습니다.");
					}
				}
				else {
					System.out.println("FTP 접속 오류 발생");
				}
			}
			catch (Exception e) {
				System.out.println("FTP 정보 오류 발생");
			}
			finally {
				try {
					ftp.disconnect();
				} catch (Exception e) {
					System.out.println("CDN 서버 지연으로 접속이 해제되지 않습니다.");
				}
			}
		return delok;
	}
	

	
	//공지사항 리스트
	@RequestMapping("/notice_list.do")
	public String admin_notice(Model m) {

		List<notice_dao> all = mm.admin_notice();
		m.addAttribute("all",all);
		
		return "admin_notice";
	}
	
	//관리자 리스트
	@RequestMapping("/admin_main.do")
	public String admin_list(Model m) {
		
		List<admin_dao> all = mm.all_admin();

		m.addAttribute("all",all);
		return null;
	}
	

	//로그아웃
	@GetMapping("/logout.do")
	public String logoutok(Model m, HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("aid");
		session.removeAttribute("anm");
		String script = "";
		script = "<script>alert('정상적으로 로그아웃 되셨습니다.');location.href='./index.jsp';</script>";
		
		m.addAttribute("script", script);
		return "script";
	}
	
	
	//로그인
	@PostMapping("/loginok.do")
	public String loginok(Model m, @RequestParam String aid, @RequestParam String apass, HttpServletRequest req){
			
		db_select ds = new db_select(this.dataSource);
		String success = ds.admincheck(aid, apass);
		String script = "";
		

		if(success == null || success =="") {
			String result2 =  mm.fail_count(aid);
			
			script = "<script>"
					+ "alert('아이디 및 패스워드를 확인하세요');"
					+ "history.go(-1);"
					+ "</script>";
			
			if(result2.equals("실패")) {
				int count = mm.log_conunt_ck(aid);
				if(count >= 5) {
					String result = mm.log_no_ck(aid);
					
					if(result.equals("ok")) {
						script = "<script>alert('로그인 실패 5회로 차단되었습니다.'); history.go(-1); </script>";
					}
				}
				
			}				
			
		}
		else {	
			String userinfo[] = success.split(",");
			if(userinfo[2].equals("N")) {
				script = "<script>"
						+ "alert('승인되지 않은 아이디입니다.');"
						+ "history.go(-1);"
						+ "</script>";
			}
			else {
				script = "<script>"
						+ "alert('정상적으로 로그인 되셨습니다.');"
						+ "location.href='./admin_main.do';"
						+ "</script>";
				
				mm.log_count_reset(aid);
				HttpSession session = req.getSession();
				session.setAttribute("aid", userinfo[0]);
				session.setAttribute("anm", userinfo[1]);
				
			}	
				
		}
			
		m.addAttribute("script",script);

		return "script";
	}
	
	//회원가입
	@RequestMapping("/joinok.do")	
	public String joinok(@RequestParam String aid,
			@RequestParam String apass,
			@RequestParam String anm,
			@RequestParam(required = false) String aemail,
			@RequestParam String atel,
			@RequestParam String atel2,
			@RequestParam String atel3,
			@RequestParam String adepartment,
			@RequestParam String aposition,
			@RequestParam(defaultValue = "N", required = false) String aok,
			Model m) {
		
		security sc = new security(apass);
		String password = sc.md5_se();
		db_insert insert = new db_insert(dataSource);
		join_dto jd = new join_dto();
		
		jd.setAid(aid);
		jd.setAnm(anm);
		jd.setAemail(aemail);
		jd.setAtel(atel);
		jd.setAtel2(atel2);
		jd.setAtel3(atel3);
		jd.setAdepartment(adepartment);
		jd.setAposition(aposition);
		jd.setAok(aok);
	
		String script = "";
		String result = mm.email_ck(aemail);
		String result2 = mm.tel_ck(atel2,atel3);
		
		if(result.equals("중복")) {
			script = "<script>alert('중복된 이메일입니다.');"
					+ "location.href='./index.jsp';</script>";
			
		}
		else {
			if(result2.equals("중복")) {
				script = "<script>alert('중복된 연락처입니다.');"
						+ "location.href='./index.jsp';</script>";
			}
			else {
				int success = insert.admin_insert(password,jd);
				if(success > 0) {
					script = "<script>alert('회원가입이 정상적으로 완료되었습니다.');"
							+ "location.href='./index.jsp';</script>";			
				}
				else {
					script = "<script>alert('회원가입에 문제가 발생하였습니다.');"
							+ "location.href='./add_master.jsp';</script>";
				}
				
			}
		}
		m.addAttribute("script", script);
		
		return "script";
	}
	
	//중복체크
	@GetMapping("/idcheck.do")
	public String id_ajax(@RequestParam String aid, Model m) {
		
		String msg = null;
		
		try {
			Connection con = dataSource.getConnection();
			String sql = "select count(*) as ctn from admin where aid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, aid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString("ctn").equals("0")) {
					msg = "0";
				}
				else {
					msg = "1";
				}
			}
			rs.close();
			ps.close();
			con.close();
		}
		catch(Exception e) {
			msg = "error";
		}
		System.out.println(msg);
		m.addAttribute("msg",msg);
		return null;
	}
	
}