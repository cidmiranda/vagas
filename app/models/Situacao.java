package models;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.F.Option;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import com.avaje.ebean.Page;

@Entity
public class Situacao extends Model implements PathBindable<Situacao>, QueryStringBindable<Situacao> {
	@Id
	@GeneratedValue
	public Long id;
	@Constraints.Required
	public String nome;
	public static Finder<Long, Situacao> find = new Finder<Long, Situacao>(Long.class, Situacao.class);
	public Situacao() {}
	public Situacao(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	public String toString() {
		return String.format("%s - %s", id, nome);
	}
	public static Page<Situacao> page(int page, int pageSize, String sortBy, String order, String filter, String atributo) {
        return 
            find.where()
                .ilike(atributo, "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }
	public static Page<Situacao> buscarTodos(int page) {
	    return find.where()
                .orderBy("id asc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static Situacao buscarPorId(Long id) {
		return find.where().eq("id", id).findUnique();
	}
	public static Situacao buscarPorNome(String nome) {
		return find.where().eq("nome", nome).findUnique();
	}
	@Override
	public Situacao bind(String key, String value) {
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
	public Option<Situacao> bind(String key, Map<String, String[]> data) {
		return Option.Some(buscarPorId(new Long(data.get("id")[0])));
	}
	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Situacao c: Situacao.find.orderBy("nome").findList()) {
            options.put(c.id.toString(), c.nome);
        }
        return options;
    }
}
