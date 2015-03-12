package controllers;

import models.CargoNecessario;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class CargosNecessarios extends Controller{
	private static final Form<CargoNecessario> cargoForm = Form.form(CargoNecessario.class);
	public static Result index() {
	    return redirect(routes.CargosNecessarios.lista(0));
	  }
	public static Result lista(Integer page){
		Page<CargoNecessario> cargos = CargoNecessario.buscarTodos(page);
	    return ok(views.html.cargosnecessarios.list.render(cargos));
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
	    return redirect(routes.CargosNecessarios.lista(0));
	}
	public static Result delete(Long id) {
	  final CargoNecessario cargo = CargoNecessario.buscarPorId(id);
	  if(cargo == null) {
	    return notFound(String.format("CargoNecessario com id %s não existe.", id));
	  }
	  cargo.delete();
	  return redirect(routes.CargosNecessarios.lista(0));
	}
}
