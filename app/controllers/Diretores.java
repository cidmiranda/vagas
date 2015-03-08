package controllers;

import models.Diretor;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class Diretores extends Controller{

	private static final Form<Diretor> diretorForm = Form.form(Diretor.class);
	public static Result index() {
	    return redirect(routes.Diretores.lista(0));
	  }
	public static Result lista(Integer page){
		Page<Diretor> diretores = Diretor.buscarTodos(page);
	    return ok(views.html.diretores.list.render(diretores));
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
	    return redirect(routes.Diretores.lista(0));
	}
	public static Result delete(Long id) {
	  final Diretor diretor = Diretor.buscarPorId(id);
	  if(diretor == null) {
	    return notFound(String.format("Diretor com id %s não existe.", id));
	  }
	  diretor.delete();
	  return redirect(routes.Diretores.lista(0));
	}
}
