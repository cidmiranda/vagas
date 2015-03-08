package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Status extends Model  {
	@Id
	@GeneratedValue
	public Long id;
	@Constraints.Required
	public String nome;
	
	public Status() {}
	public Status(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	public String toString() {
		return String.format("%s - %s", id, nome);
	}
	
	
}
