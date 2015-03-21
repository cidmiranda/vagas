package controllers;

import javax.persistence.PersistenceException;

import models.Candidato;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class Candidatos extends Controller {
	private static final Form<Candidato> candidatoForm = Form.form(Candidato.class);
	public static Result index() {
		return redirect(routes.Candidatos.lista(0));
	  }
	public static Result lista(Integer page){
		Page<Candidato> candidatos = Candidato.buscarTodos(page);
		return ok(views.html.candidatos.list.render(candidatos));
	}
	public static Result novo(){
		return ok(views.html.candidatos.detalhes.render(candidatoForm));
	}
	public static Result detalhes(Candidato candidato){
		Form<Candidato> filledForm = candidatoForm.fill(candidato);
		return ok(views.html.candidatos.detalhes.render(filledForm));
	}
	public static Result salvar(){
		try{
			Form<Candidato> boundForm = candidatoForm.bindFromRequest();
			  if(boundForm.hasErrors()) {
			    flash("error", "Por favor corrija o campo abaixo");
			    return badRequest(views.html.candidatos.detalhes.render(boundForm));
			  }
			Candidato candidato = boundForm.get();
			if (candidato.id == null) {
				candidato.save();
		    } else {
		    	candidato.update();
		    }
			flash("success",
			        String.format("Candidato atualizada %s", candidato));
			return redirect(routes.Candidatos.lista(0));	
		}
		catch(PersistenceException pe){
			flash("error",
			        String.format("Ocorreu um erro: %s", pe.getMessage()));
			return redirect(routes.Candidatos.lista(0));
		}
		
	}
	public static Result delete(Long id) {
	  final Candidato candidato = Candidato.buscarPorId(id);
	  if(candidato == null) {
		  return ok();
	  }
	  candidato.delete();
	  return redirect(routes.Candidatos.lista(0));
	}
}
