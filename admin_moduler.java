package admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("admins")
public class admin_moduler {

	@Resource(name="sqlSession")
	private SqlSessionTemplate tm;
	
	//연락처
	public String tel_ck(String Atel2, String Atel3) {
		int data = 0;
		int data2 = 0;
		data = tm.selectOne("admin_DB.hp", Atel2);
		data2 = tm.selectOne("admin_DB.hp_2", Atel3);
		String result = "";
		
		if(data > 0 && data2 > 0) {
			result = "중복";
		}
		return result;
	}
	
	
	//이메일
	public String email_ck(String Aemail) {
		int data = 0;
		data = tm.selectOne("admin_DB.email",Aemail);
		String result = "";
		
		if(data > 0) {
			result = "중복";
		}
		return result;
	}
	
	
	//티켓 리스트
	public List<ticket_dto> ticket_list(){
		List<ticket_dto> data = new ArrayList<ticket_dto>();
		data = tm.selectList("admin_DB.ticket_list");
		
		return data;
	}
	
	//검색어를 이용한 Module (티켓)
	public List<ticket_dto> search_ticket(String search, int part) {
		List<ticket_dto> data = new ArrayList<ticket_dto>();
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("search", search);
		m.put("part", Integer.toString(part));	
		
		data = tm.selectList("admin_DB.ticket_search", m);
		
		return data;
	}
	
	//티켓 체크 취소
	public int ticket_cancel(String tidx[]) {
		int data = 0;
		int w = 0;
		int ea = tidx.length;
		
		while( w < ea) {
			data = tm.delete("admin_DB.ticket_cancel", tidx[w]);
			w++;
		}
		return data;
	}
	
	//login 카운트 확인
	public int log_conunt_ck(String aid) {
		int data = tm.selectOne("admin_DB.count_ck", aid);

		return data;
	}
	
	
	//login 카운트
	public String fail_count(String aid) {
		int data = tm.update("admin_DB.fail_count", aid);
		String result = "";
		
		if(data > 0) {
			result = "실패";
		}
		return result;
	}
	
	//조회수 카운트
	public int notice_count(String nidx) {
		int data = tm.update("admin_DB.notice_count", nidx);
		
		return data;
	}
	
	//코드 체크 삭제
	public int code_ck_del(String aidx[]) {
		int data = 0;
		int w = 0;
		int ea = aidx.length;
		
		while( w < ea) {
			data = tm.delete("admin_DB.code_ck_del", aidx[w]);
			w++;
		}
		return data;
	}
	
	
	//code 삭제
	public int code_delete(String aidx) {
		int result = tm.delete("admin_DB.code_delete", aidx);
		
		return result;
	}
	
	
	//faq 수정
	public int code_modify(air_code_dto dto) {
		int result = tm.update("admin_DB.code_modify", dto);
		//System.out.println(result);
		
		return result;
	}

	//code 하나 보기
	public air_code_dto code_view(String aidx) {
		
		air_code_dto dto = tm.selectOne("admin_DB.code_view",aidx);

		System.out.println(dto.getAidx());
		System.out.println(dto.getAirplane());
		System.out.println(dto.getAircode());
		System.out.println(dto.getAirflight());
		System.out.println(dto.getAiruse());
		
		return dto;
	}
	
	
	//code 중복체크
	public int code_ck(String aircode) {
		List<air_code_dto> d = tm.selectList("admin_DB.code_ck",aircode);
		int result = d.size();
		return result;
	}
	
	//승인 체크
	public int log_ok(String aidx) {
		int result = tm.update("admin_DB.ok_admin", aidx);		
		return result;
	}
	
	//로그인 실패 체크
	public String log_no_ck(String aid) {
		int result = tm.update("admin_DB.no_admin", aid);
		String ck = "";
		if(result > 0) {
			ck="ok";
		}
		return ck;
	}
	
	//미승인 체크
	public int log_no(String aidx) {
		int result = tm.update("admin_DB.no_admin2", aidx);
		return result;
	}
	
	//로그인 카운팅 초기화
	public String log_count_reset(String aid) {
		int result = tm.update("admin_DB.log_count_reset",aid);
		
		return null;
	}
	
	//faq 수정
	public int faq_modify(faq_dto dto) {
		int result = tm.update("admin_DB.faq_modify", dto);
		//System.out.println(result);
		
		return result;
	}
	
	//faq 보기
	public faq_dto faq_view(String fidx) {
		faq_dto dto = tm.selectOne("admin_DB.faq_view",fidx);
		return dto;
	}
	
	//검색어를 이용한 Module (Faq)
	public List<faq_dto> search_faq(String search) {
		List<faq_dto> data = new ArrayList<faq_dto>();
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("search", search);	
		
		data = tm.selectList("admin_DB.search_faq", m);
		
		return data;
	}
	
	//faq 작성
	public int faq_write(faq_dto dto) {
		int result = tm.insert("admin_DB.faq_write", dto);
		return result;
	}
	
	//faq 삭제
	public int faq_delete(String fidx) {
		int result = tm.delete("admin_DB.faq_delete", fidx);
		
		return result;
	}
	
	//faq 리스트
	public List<faq_dto> faq_list(){
		List<faq_dto> data = new ArrayList<faq_dto>();
		data = tm.selectList("admin_DB.faq_list");
		
		return data;
	}
	
	//항공코드 등록 페이지
	public int aircode(air_code_dto dto) {
		int data = tm.insert("admin_DB.code_insert", dto);
		return data;
	}
	
	//항공편 등록 페이지
	public int airplane(air_plane_dto dto) {
		int data = tm.insert("admin_DB.airplane_insert", dto);
		
		return data;
	}
	
	//항공코드 리스트
	public List<air_code_dto> code_list(){
		List<air_code_dto> data = new ArrayList<air_code_dto>();
		data = tm.selectList("admin_DB.code_list");
		
		return data;
	}
	
	//항공편 리스트
	public List<air_plane_dto> airplane_list(){
		List<air_plane_dto> data = new ArrayList<air_plane_dto>();
		data = tm.selectList("admin_DB.all_airplane");
		
		return data;
	}
	
	//검색어를 이용한 Module (항공코드)
	public List<air_code_dto> search_code(String search, int part) {
		List<air_code_dto> data = new ArrayList<air_code_dto>();
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("search", search);
		m.put("part", Integer.toString(part));	
		
		data = tm.selectList("admin_DB.search_aircode", m);
		
		return data;
	}
	
	//검색어를 이용한 Module (항공편)
	public List<air_plane_dto> search_airplane(String search, int part) {
		List<air_plane_dto> data = new ArrayList<air_plane_dto>();
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("search", search);
		m.put("part", Integer.toString(part));	
		
		data = tm.selectList("admin_DB.search_airplane", m);
		
		return data;
	}
	
	//사이트 정보 수정
	public int site_update(siteinfo_dto dto) {
		int dto1 = tm.update("admin_DB.site_update", dto);
		return dto1;
	}

	//사이트 정보
	public siteinfo_dto siteinfo(String sidx) {
		siteinfo_dto dto = tm.selectOne("admin_DB.siteinfo",sidx);	
		return dto;
	}
	
	// 푸터
	public List<siteinfo_dto> footer(String sidx) {
		List<siteinfo_dto> dto = tm.selectList("admin_DB.siteinfo",sidx);	
		return dto;
	}
	
	//공지사항 체크 삭제
	public int notice_ck_del(String nidx[]) {
		int data = 0;
		int w = 0;
		int ea = nidx.length;
		
		while( w < ea) {
			data = tm.delete("admin_DB.notice_ck_del", nidx[w]);
			w++;
		}
		return data;
	}
	
	
	//공지사항 수정 (신규 첨부파일 없을경우 - 기존)
	public int notice_modify_x(notice_dao notice_dao) {
		int result = tm.update("admin_DB.notice_existing", notice_dao);
		return result;
	}
	
	//공지사항 수정 (신규 첨부파일 있을경우)
	public int notice_modify(notice_dao notice_dao) {
		int result = tm.update("admin_DB.notice_update", notice_dao);
		return result;
	}
	
	//공지사항 작성
	public int admin_notice_write(notice_dao notice_dao) {
		//System.out.println(member_dao.getRid());
		int result = tm.insert("admin_DB.admin_notice_write", notice_dao);
		return result;
	}
	
	//notice 리스트
	public List<notice_dao> admin_notice() {
		List<notice_dao> data = new ArrayList<notice_dao>();
		data = tm.selectList("admin_DB.admin_notice");
		
		//System.out.println(data);
		return data;
	}
	
	//notice 보기
	public notice_dao admin_notice_view(String idx) {
		notice_dao dao = tm.selectOne("admin_DB.notice_view",idx);	//setter
		return dao;
	}

	//관리자 리스트
	public List<admin_dao> all_admin(){
		List<admin_dao> data = new ArrayList<admin_dao>();
		data = tm.selectList("admin_DB.all_admin");
		//System.out.println(data.get(0));
		return data;
	}
	
	//검색어를 이용한 Module
	public List<admin_dao> search_admin(String search, int part) {
		List<admin_dao> data = new ArrayList<admin_dao>();
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("search", search);
		m.put("part", Integer.toString(part));	
		
		data = tm.selectList("admin_DB.search_admin", m);
		
		return data;
	}
	
	//공지사항 삭제
	public int notice_delete(String nidx) {
		int result = tm.delete("admin_DB.notice_view", nidx);
		return result;
	}
}