package controllers;

import models.Cargo;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class Cargos extends Controller{
	private static final Form<Cargo> cargoForm = Form.form(Cargo.class);
	public static Result index() {
	    return redirect(routes.Cargos.lista(0));
	  }
	public static Result lista(Integer page){
		Page<Cargo> cargos = Cargo.buscarTodos(page);
	    return ok(views.html.cargos.list.render(cargos));
	}
	public static Result novo(){
		 return ok(views.html.cargos.detalhes.render(cargoForm));
	}
	public static Result detalhes(Cargo cargo){
		Form<Cargo> filledForm = cargoForm.fill(cargo);
	    return ok(views.html.cargos.detalhes.render(filledForm));
	}
	public static Result salvar(){
		Form<Cargo> boundForm = cargoForm.bindFromRequest();
		  if(boundForm.hasErrors()) {
		    flash("error", "Por favor corrija o campo abaixo");
		    return badRequest(views.html.cargos.detalhes.render(boundForm));
		  }
		Cargo cargo = boundForm.get();
		if (cargo.id == null) {
			cargo.save();
	    } else {
	    	cargo.update();
	    }
		flash("success",
		        String.format("Cargo atualizado %s", cargo));
	    return redirect(routes.Cargos.lista(0));
	}
	public static Result delete(Long id) {
	  final Cargo cargo = Cargo.buscarPorId(id);
	  if(cargo == null) {
	    return notFound(String.format("Cargo com id %s não existe.", id));
	  }
	  cargo.delete();
	  return redirect(routes.Cargos.lista(0));
	}
}
