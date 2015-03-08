package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import play.data.format.Formats.DateTime;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Vaga extends Model {
	@Id
	@GeneratedValue
	public Long id;
	
	@Constraints.Required
	@DateTime(pattern = "dd/MM/yyyy")
	public Date dataAbertura = new Date();
	@Constraints.Required
	public Double remuneracao;
	@Constraints.Required
	@DateTime(pattern = "dd/MM/yyyy")
	public Date dataInicio = new Date();
	@Constraints.Required
	public Integer prioridade;
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Candidato> candidatos;
	@Constraints.Required
	@OneToOne
	public Area area;
	@Constraints.Required
	@OneToOne
	public Cargo cargo;
	@Constraints.Required
	@OneToOne
	public CargoNecessario cargoNecessaio;
	@Constraints.Required
	@OneToOne
	public Status status;
	
	
	
	public Vaga() {
		
	}



	public Vaga(Long id, Date dataAbertura, Double remuneracao,
			Date dataInicio, Integer prioridade, List<Candidato> candidatos,
			Area area, Cargo cargo, CargoNecessario cargoNecessaio,
			Status status) {
		this.id = id;
		this.dataAbertura = dataAbertura;
		this.remuneracao = remuneracao;
		this.dataInicio = dataInicio;
		this.prioridade = prioridade;
		this.candidatos = candidatos;
		this.area = area;
		this.cargo = cargo;
		this.cargoNecessaio = cargoNecessaio;
		this.status = status;
	}
	
	
}
