package com.codingDojo.mvc.models;

public class IdeaInfo {
	private Idea idea ;
	
	private boolean isLiked ;
	
	
	public IdeaInfo() {
		this.isLiked = false ;
	}


	public Idea getIdea() {
		return idea;
	}


	public void setIdea(Idea idea) {
		this.idea = idea;
	}


	public boolean isLiked() {
		return isLiked;
	}


	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
	
}
