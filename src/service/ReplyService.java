package service;

import domain.Board;
import domain.Reply;

import java.util.List;

import dao.ReplyDao;

public class ReplyService {
	private ReplyDao replyDao = new ReplyDao();
	
	public int register(Reply reply) {
		return replyDao.insert(reply);
	}
	public List<Reply> getList(Long bno, int amount, Long lastRno) {
		return replyDao.selectList(bno, amount, lastRno);
	}
	public Reply get(Long rno) {
		return replyDao.selectOne(rno);
	}
	public int modify(Reply reply) {
		return replyDao.update(reply);
	}
	public int remove(Long rno) {
		return replyDao.delete(rno);
	}
	public int getCount(Long bno) {
		return replyDao.selectCount(bno);
	}
}
