package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;
@Entity
public class Candidato  extends Model {
	@Id
	@GeneratedValue
	public Long id;
	@Constraints.Required
	public String nome;
	@Constraints.Required
	public Long cpf;
	public Candidato() {
		
	}
	public Candidato(Long id, String nome, Long cpf) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
	}
	public String toString() {
		return String.format("%s - %s", id, nome);
	}
	
}

