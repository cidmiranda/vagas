package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Area  extends Model {
	@Id
	@GeneratedValue
	public Long id;
	@Constraints.Required
	public String nome;
	
	@OneToOne
	public Diretor diretor;
	
	@OneToOne
	public Gestor gestor;
	
	public Area() {}
	public Area(Long id, String nome, Diretor diretor, Gestor gestor) {
		this.id = id;
		this.nome = nome;
		this.diretor = diretor;
		this.gestor = gestor;
	}
	public String toString() {
		return String.format("%s - %s", id, nome);
	}
}
