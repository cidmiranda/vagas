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
public class Diretor extends Model implements PathBindable<Diretor>, QueryStringBindable<Diretor>{

	@Id
	@GeneratedValue
	public Long id;
	@Constraints.Required
	public String nome;
	
	public static Finder<Long, Diretor> find = new Finder<Long, Diretor>(Long.class, Diretor.class);
	
	public Diretor() {}
	public Diretor(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	public String toString() {
		return String.format("%s - %s", id, nome);
	}
	public static Page<Diretor> page(int page, int pageSize, String sortBy, String order, String filter, String atributo) {
        return 
            find.where()
                .ilike(atributo, "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }
	public static Page<Diretor> buscarTodos(int page) {
	    return find.where()
                .orderBy("id asc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static Diretor buscarPorId(Long id) {
		return find.where().eq("id", id).findUnique();
	}
	public static Diretor buscarPorNome(String nome) {
		return find.where().eq("nome", nome).findUnique();
	}
	@Override
	public Diretor bind(String key, String value) {
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
	public Option<Diretor> bind(String key, Map<String, String[]> data) {
		return Option.Some(buscarPorId(new Long(data.get("id")[0])));
	}
	
    
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Diretor c: Diretor.find.orderBy("nome").findList()) {
            options.put(c.id.toString(), c.nome);
        }
        return options;
    }
	
}
