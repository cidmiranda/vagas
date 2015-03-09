package controllers;

import models.Gestor;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class Gestores extends Controller{

	private static final Form<Gestor> gestorForm = Form.form(Gestor.class);
	public static Result index() {
		return redirect(routes.Gestores.lista(0));
	  }
	public static Result lista(Integer page){
		Page<Gestor> gestores = Gestor.buscarTodos(page);
		return ok(views.html.gestores.list.render(gestores));
	}
	public static Result novo(){
		return ok(views.html.gestores.detalhes.render(gestorForm));
	}
	public static Result detalhes(Gestor gestor){
		Form<Gestor> filledForm = gestorForm.fill(gestor);
		return ok(views.html.gestores.detalhes.render(filledForm));
	}
	public static Result salvar(){
		Form<Gestor> boundForm = gestorForm.bindFromRequest();
		  if(boundForm.hasErrors()) {
		    flash("error", "Por favor corrija o campo abaixo");
		    return badRequest(views.html.gestores.detalhes.render(boundForm));
		  }
		Gestor gestor = boundForm.get();
		if (gestor.id == null) {
			gestor.save();
	    } else {
	    	gestor.update();
	    }
		flash("success",
		        String.format("Gestor atualizado %s", gestor));
		return redirect(routes.Gestores.lista(0));
	}
	public static Result delete(Long id) {
	  final Gestor gestor = Gestor.buscarPorId(id);
	  if(gestor == null) {
		  return ok();
	  }
	  gestor.delete();
	  return redirect(routes.Gestores.lista(0));
	}
}
