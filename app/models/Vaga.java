package models;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.format.Formats.DateTime;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.F.Option;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import com.avaje.ebean.Page;

@Entity
@Table(name = "vaga")
public class Vaga extends Model implements PathBindable<Vaga>, QueryStringBindable<Vaga>  {
	public static Finder<Long, Vaga> find = new Finder<Long, Vaga>(Long.class, Vaga.class);
	
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
	
	@Constraints.Required(message="Obrigatório")
	@Constraints.Min(value=1,message="Minimo 1, máximo 5")
	@Constraints.Max(value=5,message="Minimo 1, máximo 5")
	public Integer prioridade;
	
	@Constraints.Required
	@OneToOne
	public Area area;
	
	@Constraints.Required
	@OneToOne
	public Cargo cargo;
	
	@Constraints.Required
	@OneToOne
	public CargoNecessario cargoNecessario;
	
	@Constraints.Required
	@OneToOne
	public Situacao status;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "vaga_candidato", joinColumns = { 
			@JoinColumn(name = "vaga_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "candidato_id", 
					nullable = false, updatable = false) })
	public List<Candidato> candidatos;
	
	@Temporal(TemporalType.DATE)
	public Date dataCriacao;
	
	@Temporal(TemporalType.DATE)
	public String criadoPor;
	
	public List<VagaCandidato> vagasCandidatos;
	
	public Vaga() {
		
	}

	public Vaga(Long id, Date dataAbertura, Double remuneracao,
			Date dataInicio, Integer prioridade,
			Area area, Cargo cargo, CargoNecessario cargoNecessario,
			Situacao status) {
		this.id = id;
		this.dataAbertura = dataAbertura;
		this.remuneracao = remuneracao;
		this.dataInicio = dataInicio;
		this.prioridade = prioridade;
		this.area = area;
		this.cargo = cargo;
		this.cargoNecessario = cargoNecessario;
		this.status = status;
	}
	public String toString() {
		return String.format("%s", id);
	}
	
	public static Page<Vaga> buscarTodos(int page) {
	    return find.where()
	    		//.eq("vaga.id", id)
                .orderBy("dataCriacao desc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	
	public static Vaga buscarPorId(Long id) {
		return find.where().eq("id", id).findUnique();
	}
	public static Vaga buscarPorCargo(Long id) {
		return find.where().eq("cargo.id", id).findUnique();
	}
	public static Page<Vaga> buscarPorArea(Long id, int page) {
		return find.where()
	    		.eq("area.id", id)
                .orderBy("dataInicio desc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static Vaga buscarPorArea(Long id) {
		return find.where().eq("area.id", id).findUnique();
	}
	@Override
	public Vaga bind(String key, String value) {
		return buscarPorId(new Long(value));
	}
	@Override
	public String javascriptUnbind() {
		return this.id.toString();
	}
	@Override
	public String unbind(String arg0) {
		return this.id.toString();
	}
	@Override
	public Option<Vaga> bind(String key, Map<String, String[]> data) {
		return Option.Some(buscarPorId(new Long(data.get("id")[0])));
	}
	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Vaga c: Vaga.find.orderBy("cargo.nome").findList()) {
            options.put(c.id.toString(), c.cargo.nome);
        }
        return options;
    }
}
