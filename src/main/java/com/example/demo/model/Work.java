package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Work implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Date start;
	private Date end;
	private String status;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	public Work(){
		id=0;
	}
	
	public Work(String name, Date start, Date end,String status) {
		this.id =id;
		this.name = name;
		this.start = start;
		this.end = end;
		this.status = status;
	}

	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the star
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param star the star to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
