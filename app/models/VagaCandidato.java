package models;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.avaje.ebean.Page;

import play.db.ebean.Model;
import play.libs.F.Option;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

@Entity
@Table(
        name="vaga_candidato"
       ,uniqueConstraints=@UniqueConstraint(
    		   				columnNames={"vaga_id","candidato_id"}
    		   			  )
       )
public class VagaCandidato extends Model implements PathBindable<VagaCandidato>, QueryStringBindable<VagaCandidato>  {
	public static Finder<Long, VagaCandidato> find = new Finder<Long, VagaCandidato>(Long.class, VagaCandidato.class);
	
	@Id
	@GeneratedValue
	public Long id;
	
	@ManyToOne
	public Vaga vaga;
	
	@ManyToOne
	public Candidato candidato;
	
	@Temporal(TemporalType.DATE)
	public Date dataCriacao;
	
	@Temporal(TemporalType.DATE)
	public String criadoPor;
	
	public String aprovado;

	public VagaCandidato() {
		
	}

	public VagaCandidato(Long id, Vaga vaga, Candidato candidato, Date dataCriacao,
			String criadoPor, String aprovado) {
		this.id = id;
		this.vaga = vaga;
		this.candidato = candidato;
		this.dataCriacao = dataCriacao;
		this.criadoPor = criadoPor;
		this.aprovado = aprovado;
	}
	
	public String toString() {
		return String.format("%s", vaga.id);
	}
	
	public static Page<VagaCandidato> buscarTodos(int page) {
	    return find.where()
	    		//.eq("vaga.id", id)
                .orderBy("dataCriacao desc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static Page<VagaCandidato> buscarPorVaga(Vaga vaga, int page) {
	    return find.where()
	    		.eq("vaga.id", vaga.id)
                .orderBy("dataCriacao desc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static Page<VagaCandidato> buscarPorCandidato(Candidato candidato, int page) {
	    return find.where()
	    		.eq("candidato.id", candidato.id)
                .orderBy("dataCriacao desc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static VagaCandidato buscarPorId(Long id) {
		return find.where().eq("id", id).findUnique();
	}
	public static VagaCandidato buscarPorIdVaga(Long id) {
		return find.where().eq("vaga.id", id).findUnique();
	}
	public static VagaCandidato buscarPorIdCandidato(Long id) {
		return find.where().eq("candidato.id", id).findUnique();
	}
	
	@Override
	public VagaCandidato bind(String key, String value) {
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
	public Option<VagaCandidato> bind(String key, Map<String, String[]> data) {
		return Option.Some(buscarPorId(new Long(data.get("id")[0])));
	}
	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        options.put("Nao", "Não");
        options.put("Sim", "Sim");
        return options;
    }
}
