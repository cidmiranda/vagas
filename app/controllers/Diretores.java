package controllers;

import models.Area;
import models.Diretor;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class Diretores extends Controller{

	private static final Form<Diretor> diretorForm = Form.form(Diretor.class);
	public static Result GO_HOME = redirect(
	        routes.Diretores.list(0, "id", "asc", "", "nome")
	    );
	public static Result list(int page, String sortBy, String order, String filter, String atributo) {
		return ok(
        		views.html.diretores.list.render(
            	Diretor.page(page, 10, sortBy, order, filter, atributo),
                sortBy, order, filter, atributo
            )
        );
    }
	public static Result index() {
        return GO_HOME;
    }
	
	public static Result novo(){
		 return ok(views.html.diretores.detalhes.render(diretorForm));
	}
	public static Result detalhes(Diretor diretor){
		Form<Diretor> filledForm = diretorForm.fill(diretor);
	    return ok(views.html.diretores.detalhes.render(filledForm));
	}
	public static Result salvar(){
		Form<Diretor> boundForm = diretorForm.bindFromRequest();
		  if(boundForm.hasErrors()) {
		    flash("error", "Por favor corrija o campo abaixo");
		    return badRequest(views.html.diretores.detalhes.render(boundForm));
		  }
		Diretor diretor = boundForm.get();
		if (diretor.id == null) {
			diretor.save();
	    } else {
	    	diretor.update();
	    }
		flash("success",
		        String.format("Diretor atualizado %s", diretor));
	    return redirect(routes.Diretores.list(0, "id", "asc", "", "nome"));
	}
	public static Result delete(Long id) {
	  final Diretor diretor = Diretor.buscarPorId(id);
	  
	  if(diretor == null) {
	    return notFound(String.format("Diretor com id %s não existe.", id));
	  }
	  final Area area = Area.buscarPorDiretor(id);
	  if(area != null) {
	    return badRequest(String.format("Diretor %s não pode ser excluído.", id));
	  }
	  diretor.delete();
	  return redirect(routes.Diretores.list(0, "id", "asc", "", "nome"));
	}
}
