package controllers;

import models.Area;
import models.Cargo;
import models.Vaga;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Page;

@Security.Authenticated(Secured.class)
public class Cargos extends Controller{
	private static final Form<Cargo> cargoForm = Form.form(Cargo.class);
	public static Result GO_HOME = redirect(
	        routes.Cargos.list(0, "id", "asc", "", "nome")
	    );
	public static Result list(int page, String sortBy, String order, String filter, String atributo) {
        return ok(
        		views.html.cargos.list.render(
            	Cargo.page(page, 10, sortBy, order, filter, atributo),
                sortBy, order, filter, atributo
            )
        );
    }
	public static Result index() {
        return GO_HOME;
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
	    return redirect(routes.Cargos.list(0, "id", "asc", "", "nome"));
	}
	public static Result delete(Long id) {
	  final Cargo cargo = Cargo.buscarPorId(id);
	  if(cargo == null) {
	    return notFound(String.format("Cargo com id %s não existe.", id));
	  }
	  final Vaga vaga = Vaga.buscarPorCargo(id);
	  if(vaga != null) {
	    return badRequest(String.format("Cargo %s não pode ser excluído.", id));
	  }
	  cargo.delete();
	  return redirect(routes.Cargos.list(0, "id", "asc", "", "nome"));
	}
}
