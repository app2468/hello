package cn.itcast.nsfw.complain.entity;

import java.sql.Timestamp;

/**
 * ComplainReply entity. @author MyEclipse Persistence Tools
 */

public class ComplainReply implements java.io.Serializable {

	// Fields

	private String replyId;
	private Complain complain;
	private Timestamp replyTime;
	private String replyDept;
	private String replyer;
	private String content;

	// Constructors

	/** default constructor */
	public ComplainReply() {
	}

	/** minimal constructor */
	public ComplainReply(Complain complain) {
		this.complain = complain;
	}

	/** full constructor */
	public ComplainReply(Complain complain, Timestamp replyTime, String replyDept, String replyer, String content) {
		this.complain = complain;
		this.replyTime = replyTime;
		this.replyDept = replyDept;
		this.replyer = replyer;
		this.content = content;
	}

	// Property accessors

	public String getReplyId() {
		return this.replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public Complain getComplain() {
		return this.complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

	public Timestamp getReplyTime() {
		return this.replyTime;
	}

	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}

	public String getReplyDept() {
		return this.replyDept;
	}

	public void setReplyDept(String replyDept) {
		this.replyDept = replyDept;
	}

	public String getReplyer() {
		return this.replyer;
	}

	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}