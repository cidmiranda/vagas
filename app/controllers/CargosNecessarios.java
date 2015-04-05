package controllers;

import models.Cargo;
import models.CargoNecessario;
import models.Vaga;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Page;

@Security.Authenticated(Secured.class)
public class CargosNecessarios extends Controller{
	private static final Form<CargoNecessario> cargoForm = Form.form(CargoNecessario.class);
	public static Result GO_HOME = redirect(
	        routes.CargosNecessarios.list(0, "id", "asc", "", "nome")
	    );
	public static Result list(int page, String sortBy, String order, String filter, String atributo) {
        return ok(
        		views.html.cargosnecessarios.list.render(
        		CargoNecessario.page(page, 10, sortBy, order, filter, atributo),
                sortBy, order, filter, atributo
            )
        );
    }
	public static Result index() {
        return GO_HOME;
    }
	public static Result novo(){
		 return ok(views.html.cargosnecessarios.detalhes.render(cargoForm));
	}
	public static Result detalhes(CargoNecessario cargo){
		Form<CargoNecessario> filledForm = cargoForm.fill(cargo);
	    return ok(views.html.cargosnecessarios.detalhes.render(filledForm));
	}
	public static Result salvar(){
		Form<CargoNecessario> boundForm = cargoForm.bindFromRequest();
		  if(boundForm.hasErrors()) {
		    flash("error", "Por favor corrija o campo abaixo");
		    return badRequest(views.html.cargosnecessarios.detalhes.render(boundForm));
		  }
		CargoNecessario cargo = boundForm.get();
		if (cargo.id == null) {
			cargo.save();
	    } else {
	    	cargo.update();
	    }
		flash("success",
		        String.format("CargoNecessario atualizado %s", cargo));
	    return redirect(routes.CargosNecessarios.list(0, "id", "asc", "", "nome"));
	}
	public static Result delete(Long id) {
	  final CargoNecessario cargo = CargoNecessario.buscarPorId(id);
	  if(cargo == null) {
	    return notFound(String.format("CargoNecessario com id %s não existe.", id));
	  }
	  final Vaga vaga = Vaga.buscarPorCargoNecessario(id);
	  if(vaga != null) {
	    return badRequest(String.format("Cargo Necessário %s não pode ser excluído.", id));
	  }
	  cargo.delete();
	  return redirect(routes.CargosNecessarios.list(0, "id", "asc", "", "nome"));
	}
}
