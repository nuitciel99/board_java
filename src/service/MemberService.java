package service;

import dao.MemberDao;
import domain.Member;

public class MemberService {
	private MemberDao dao = new MemberDao();
	
	// insert method == int method > 0 : 이미 존재하는 회원 또는 이미 존재하는 아이디
	public boolean signup(Member member) {
		return dao.insert(member) > 0;
	}
	// Member findBy Id, used for login
	public Member findBy(String id) {
		return dao.selectOne(id);
	}
	// modify except pwd
	public void modify(Member member) {
		dao.update(member);
	}
	// modify only pwd
	public void modifyPwd(Member member) {
		dao.updatePwd(member);
	}
}
