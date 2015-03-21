package models;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.avaje.ebean.Page;

import play.db.ebean.Model;
import play.libs.F.Option;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

@Entity
public class VagaCandidato extends Model implements PathBindable<VagaCandidato>, QueryStringBindable<VagaCandidato>  {
	public static Finder<Long, VagaCandidato> find = new Finder<Long, VagaCandidato>(Long.class, VagaCandidato.class);
	
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

	public VagaCandidato(Vaga vaga, Candidato candidato, Date dataCriacao,
			String criadoPor, String aprovado) {
		this.vaga = vaga;
		this.candidato = candidato;
		this.dataCriacao = dataCriacao;
		this.criadoPor = criadoPor;
		this.aprovado = aprovado;
	}
	
	public String toString() {
		return String.format("%s - %s - %s - %s - %s", vaga.id, candidato.id, dataCriacao, criadoPor, aprovado);
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
	public static VagaCandidato buscarPorIdVaga(Long id) {
		return find.where().eq("vaga.id", id).findUnique();
	}
	public static VagaCandidato buscarPorIdCandidato(Long id) {
		return find.where().eq("candidato.id", id).findUnique();
	}
	
	@Override
	public VagaCandidato bind(String key, String value) {
		return buscarPorIdVaga(new Long(value));
	}
	@Override
	public String javascriptUnbind() {
		return this.vaga.id.toString();
	}
	@Override
	public String unbind(String arg0) {
		return this.vaga.id.toString();
	}
	@Override
	public Option<VagaCandidato> bind(String key, Map<String, String[]> data) {
		return Option.Some(buscarPorIdVaga(new Long(data.get("vaga.id")[0])));
	}
}
