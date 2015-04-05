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
public class Usuario extends Model implements PathBindable<Usuario>, QueryStringBindable<Usuario> {
	@Id
	@GeneratedValue
	public Long id;
	@Constraints.Required
	public String email;
	@Constraints.Required
	public String senha;
	@Constraints.Required
	public String tipo;
	
	public static Finder<Long, Usuario> find = new Finder<Long, Usuario>(Long.class, Usuario.class);
	public Usuario() {}
	public Usuario(String email, String senha) {
	    this.email = email;
	    this.senha = senha;
	  }
	public String toString() {
		return String.format("%s - %s", id, email);
	}
	public static Usuario authenticate(String email, String senha) { 
		return find.where().eq("email", email).eq("senha", senha).findUnique();
	}
	
	public static Page<Usuario> page(int page, int pageSize, String sortBy, String order, String filter, String atributo) {
        return 
            find.where()
                .ilike(atributo, "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }
	public static Page<Usuario> buscarTodos(int page) {
	    return find.where()
                .orderBy("id asc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
	}
	public static Usuario buscarPorId(Long id) {
		return find.where().eq("id", id).findUnique();
	}
	public static Usuario buscarPorNome(String nome) {
		return find.where().eq("nome", nome).findUnique();
	}
	@Override
	public Usuario bind(String key, String value) {
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
	public Option<Usuario> bind(String key, Map<String, String[]> data) {
		return Option.Some(buscarPorId(new Long(data.get("id")[0])));
	}
	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        options.put("user", "Usuário");
        options.put("adm", "Administrador");
        return options;
    }
}
