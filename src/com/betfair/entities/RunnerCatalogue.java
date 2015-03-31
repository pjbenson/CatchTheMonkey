package com.betfair.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@NamedQueries({		
	@NamedQuery(name = "Runners.findById", query = "select o from RunnerCatalogue o where o.selectionId=:a"),
	@NamedQuery(name = "Runners.findAll", query = "select o from RunnerCatalogue o")
})
@Entity
@Table(name="Runner_Catalogue")
public class RunnerCatalogue implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="SELECTION_ID")
	private Long selectionId;
	@Column(name="RUNNER_NAME")
	private String runnerName;
	@Transient
	private Double handicap;
	@Transient
	private Map<String, String> metadata = new HashMap<String, String>();
	
	public Long getSelectionId() {
		return selectionId;
	}

	public void setSelectionId(Long selectionId) {
		this.selectionId = selectionId;
	}

	public String getRunnerName() {
		return runnerName;
	}

	public void setRunnerName(String runnerName) {
		this.runnerName = runnerName;
	}

	public Double getHandicap() {
		return handicap;
	}

	public void setHandicap(Double handicap) {
		this.handicap = handicap;
	}

	public String toString() {
		return "{" + "" + "selectionId=" + getSelectionId() + ","
				+ "runnerName=" + getRunnerName() + "," + "handicap="
				+ getHandicap() + "," + "}";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
}
