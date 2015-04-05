package controllers;

import models.Cargo;
import models.Usuario;
import models.Vaga;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Page;

@Security.Authenticated(Secured.class)
public class Usuarios extends Controller {
	private static final Form<Usuario> usuarioForm = Form.form(Usuario.class);
	public static Result GO_HOME = redirect(
	        routes.Usuarios.list(0, "id", "asc", "", "email")
	    );
	public static Result list(int page, String sortBy, String order, String filter, String atributo) {
        return ok(
        		views.html.usuarios.list.render(
        		Usuario.page(page, 10, sortBy, order, filter, atributo),
                sortBy, order, filter, atributo
            )
        );
    }
	public static Result index() {
        return GO_HOME;
    }
	public static Result novo(){
		return ok(views.html.usuarios.detalhes.render(usuarioForm));
	}
	public static Result detalhes(Usuario usuario){
		Form<Usuario> filledForm = usuarioForm.fill(usuario);
		return ok(views.html.usuarios.detalhes.render(filledForm));
	}
	public static Result salvar(){
		Form<Usuario> boundForm = usuarioForm.bindFromRequest();
		  if(boundForm.hasErrors()) {
		    flash("error", "Por favor corrija o campo abaixo");
		    return badRequest(views.html.usuarios.detalhes.render(boundForm));
		  }
		Usuario usuario = boundForm.get();
		if (usuario.id == null) {
			usuario.save();
	    } else {
	    	usuario.update();
	    }
		flash("success",
		        String.format("Usuario atualizada %s", usuario));
		return redirect(routes.Usuarios.list(0, "id", "asc", "", "email"));
	}
	public static Result delete(Long id) {
	  final Usuario usuario = Usuario.buscarPorId(id);
	  if(usuario == null) {
		  return notFound(String.format("Usuário com id %s não existe.", id));
	  }
	  usuario.delete();
	  return redirect(routes.Usuarios.list(0, "id", "asc", "", "email"));
	}
}
