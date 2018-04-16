/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 文章详情Entity
 * @author luo
 * @version 2017-05-19
 */
public class CmsArticleData extends DataEntity<CmsArticleData> {
	
	private static final long serialVersionUID = 1L;
	

	
	private String content;		// 文章内容
	private String copyfrom;		// 文章来源
	private String relation;		// 相关文章
	private String allowComment;		// 是否允许评论
	
	public CmsArticleData() {
		super();
	}

	public CmsArticleData(String id){
		super(id);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=255, message="文章来源长度必须介于 0 和 255 之间")
	public String getCopyfrom() {
		return copyfrom;
	}

	public void setCopyfrom(String copyfrom) {
		this.copyfrom = copyfrom;
	}
	
	@Length(min=0, max=255, message="相关文章长度必须介于 0 和 255 之间")
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	@Length(min=0, max=1, message="是否允许评论长度必须介于 0 和 1 之间")
	public String getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(String allowComment) {
		this.allowComment = allowComment;
	}
	
	

	
}