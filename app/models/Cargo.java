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
public class Cargo extends Model implements PathBindable<Cargo>, QueryStringBindable<Cargo>{
	@Id
	@GeneratedValue
	public Long id;
	@Constraints.Required
	public String nome;
	
	public static Finder<Long, Cargo> find = new Finder<Long, Cargo>(Long.class, Cargo.class);
	
	public Cargo() {}
	public Cargo(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	public String toString() {
		return String.format("%s - %s", id, nome);
	}
	public static Page<Cargo> page(int page, int pageSize, String sortBy, String order, String filter, String atributo) {
        return 
            find.where()
                .ilike(atributo, "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }
	public static Page<Cargo> buscarTodos(int page) {
	    return find.where()
                .orderBy("id asc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static Cargo buscarPorId(Long id) {
		return find.where().eq("id", id).findUnique();
	}
	public static Cargo buscarPorNome(String nome) {
		return find.where().eq("nome", nome).findUnique();
	}
	@Override
	public Cargo bind(String key, String value) {
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
	public Option<Cargo> bind(String key, Map<String, String[]> data) {
		return Option.Some(buscarPorId(new Long(data.get("id")[0])));
	}
	
    
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Cargo c: Cargo.find.orderBy("nome").findList()) {
            options.put(c.id.toString(), c.nome);
        }
        return options;
    }
	
}
