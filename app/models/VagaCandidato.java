package models;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import play.db.ebean.Model;
import play.libs.F.Option;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.SqlUpdate;

@Entity
@Table(
        name="vaga_candidato"
       ,uniqueConstraints=@UniqueConstraint(
    		   				columnNames={"vaga_id","candidato_id"}
    		   			  )
       )
public class VagaCandidato extends Model implements PathBindable<VagaCandidato>, QueryStringBindable<VagaCandidato>  {
	public static Finder<Long, VagaCandidato> find = new Finder<Long, VagaCandidato>(Long.class, VagaCandidato.class);
	
/*	@Id
	@GeneratedValue
	public Long id;
*/	
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

	public VagaCandidato(/*Long id, */Vaga vaga, Candidato candidato, Date dataCriacao,
			String criadoPor, String aprovado) {
		//this.id = id;
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
	public static VagaCandidato buscarPorVagaCandidato(Long idVaga, Long id) {
		return find.where().eq("vaga.id", idVaga).eq("candidato.id", id).findUnique();
	}
	public static VagaCandidato buscarPorId(Long idVaga) {
		return find.where().eq("vaga.id", idVaga).findUnique();
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
		return this.vaga.id.toString();
	}
	@Override
	public String unbind(String arg0) {
		return this.vaga.id.toString();
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
	@Override
	public void update() {
		String sql = "UPDATE  vaga_candidato SET aprovado = :aprovado where vaga_id = :idVaga and candidato_id = :idCandidato";
		SqlUpdate update = Ebean.createSqlUpdate(sql);
		
		update.setParameter("aprovado", this.aprovado);
		update.setParameter("idVaga", this.vaga.id);
		update.setParameter("idCandidato", this.candidato.id);

		int modifiedCount = Ebean.execute(update); 
		System.out.println("update override " + modifiedCount);
	}
	
	@Override
	public void delete() {
		String sql = "DELETE from vaga_candidato where vaga_id = :idVaga and candidato_id = :idCandidato";
		SqlUpdate delete = Ebean.createSqlUpdate(sql);
		
		delete.setParameter("idVaga", this.vaga.id);
		delete.setParameter("idCandidato", this.candidato.id);

		int modifiedCount = Ebean.execute(delete); 
		System.out.println("delete override " + modifiedCount);
	}
}
