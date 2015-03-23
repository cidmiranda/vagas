package models;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.F.Option;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import com.avaje.ebean.Page;

@Entity
public class Area  extends Model implements PathBindable<Area>, QueryStringBindable<Area> {
	@Id
	@GeneratedValue
	public Long id;
	 
	@Constraints.Required
	public String nome;
	
	@ManyToOne
	@Constraints.Required
	public Diretor diretor;
	
	@ManyToOne
	@Constraints.Required
	public Gestor gestor;
	
	public static Finder<Long, Area> find = new Finder<Long, Area>(Long.class, Area.class);
	public Area() {}
	public Area(Long id, String nome, Diretor diretor, Gestor gestor) {
		this.id = id;
		this.nome = nome;
		this.diretor = diretor;
		this.gestor = gestor;
	}
	public String toString() {
		return String.format("%s - %s", id, nome);
	}
	public static Page<Area> buscarTodos(int page) {
	    return find.where()
                .orderBy("id asc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static Area buscarPorId(Long id) {
		return find.where().eq("id", id).findUnique();
	}
	public static Area buscarPorDiretor(Long id) {
		return find.where().eq("diretor.id", id).findUnique();
	}
	public static Area buscarPorGestor(Long id) {
		return find.where().eq("gestor.id", id).findUnique();
	}
	public static Area buscarPorNome(String nome) {
		return find.where().eq("nome", nome).findUnique();
	}
	@Override
	public Area bind(String key, String value) {
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
	public Option<Area> bind(String key, Map<String, String[]> data) {
		return Option.Some(buscarPorId(new Long(data.get("id")[0])));
	}
	
	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Area c: Area.find.orderBy("nome").findList()) {
            options.put(c.id.toString(), c.nome);
        }
        return options;
    }
}
