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
public class CargoNecessario extends Model implements PathBindable<CargoNecessario>, QueryStringBindable<CargoNecessario>{
	public static Finder<Long, CargoNecessario> find = new Finder<Long, CargoNecessario>(Long.class, CargoNecessario.class);
	@Id
	@GeneratedValue
	public Long id;
	@Constraints.Required
	public String nome;
	
	public CargoNecessario() {}
	public CargoNecessario(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	public String toString() {
		return String.format("%s - %s", id, nome);
	}
	public static Page<CargoNecessario> buscarTodos(int page) {
	    return find.where()
                .orderBy("id asc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static CargoNecessario buscarPorId(Long id) {
		return find.where().eq("id", id).findUnique();
	}
	public static CargoNecessario buscarPorNome(String nome) {
		return find.where().eq("nome", nome).findUnique();
	}
	@Override
	public CargoNecessario bind(String key, String value) {
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
	public Option<CargoNecessario> bind(String key, Map<String, String[]> data) {
		return Option.Some(buscarPorId(new Long(data.get("id")[0])));
	}
	
    
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(CargoNecessario c: CargoNecessario.find.orderBy("nome").findList()) {
            options.put(c.id.toString(), c.nome);
        }
        return options;
    }
	
}
