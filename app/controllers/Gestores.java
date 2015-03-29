package controllers;

import models.Area;
import models.Gestor;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Gestores extends Controller{

	private static final Form<Gestor> gestorForm = Form.form(Gestor.class);
	public static Result GO_HOME = redirect(
	        routes.Gestores.list(0, "nome", "asc", "", "nome")
	    );
	public static Result list(int page, String sortBy, String order, String filter, String atributo) {
        return ok(
        		views.html.gestores.list.render(
				Gestor.page(page, 10, sortBy, order, filter, atributo),
                sortBy, order, filter, atributo
            )
        );
    }
	public static Result index() {
        return GO_HOME;
    }
	/*public static Result lista(Integer page){
		Page<Gestor> gestores = Gestor.buscarTodos(page);
		return ok(views.html.gestores.list.render(gestores));
	}*/
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
		return redirect(routes.Gestores.list(0, "nome", "asc", "", "nome"));
	}
	public static Result delete(Long id) {
	  final Gestor gestor = Gestor.buscarPorId(id);
	  if(gestor == null) {
		  return ok();
	  }
	  final Area area = Area.buscarPorGestor(id);
	  if(area != null) {
	    return badRequest(String.format("Gestor %s não pode ser excluído.", id));
	  }
	  gestor.delete();
	  return redirect(routes.Gestores.list(0, "nome", "asc", "", "nome"));
	}
}
