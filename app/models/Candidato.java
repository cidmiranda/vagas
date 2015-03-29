package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.F.Option;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import com.avaje.ebean.Page;
@Entity
@Table(
        name="candidato"
       ,uniqueConstraints=@UniqueConstraint(
    		   				columnNames={"cpf"}
    		   			  )
    )
public class Candidato extends Model implements PathBindable<Candidato>, QueryStringBindable<Candidato> {
	@Id
	@GeneratedValue
	public Long id;
	@Constraints.Required
	public String nome;
	@Constraints.Required
	public Long cpf;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "candidatos")
	public List<Vaga> vagas;
	public List<VagaCandidato> vagasCandidatos;
	
	public static Finder<Long, Candidato> find = new Finder<Long, Candidato>(Long.class, Candidato.class);
	public Candidato() {
		
	}
	public Candidato(Long id, String nome, Long cpf) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
	}
	public String toString() {
		return String.format("%s - %s - %s", id, nome, cpf);
	}
	public static Page<Candidato> page(int page, int pageSize, String sortBy, String order, String filter, String atributo) {
        return 
            find.where()
                .ilike(atributo, "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }
	public static Page<Candidato> buscarTodos(int page) {
	    return find.where()
                .orderBy("id asc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static Candidato buscarPorId(Long id) {
		return find.where().eq("id", id).findUnique();
	}
	public static Candidato buscarPorNome(String nome) {
		return find.where().eq("nome", nome).findUnique();
	}
	public static Candidato buscarPorCpf(String cpf) {
		return find.where().eq("cpf", cpf).findUnique();
	}
	@Override
	public Candidato bind(String key, String value) {
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
	public Option<Candidato> bind(String key, Map<String, String[]> data) {
		return Option.Some(buscarPorId(new Long(data.get("id")[0])));
	}
	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Candidato c: Candidato.find.orderBy("nome").findList()) {
            options.put(c.id.toString(), c.nome);
        }
        return options;
    }
}

