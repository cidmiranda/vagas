package controllers;

import javax.persistence.PersistenceException;

import models.Candidato;
import models.Situacao;
import models.Vaga;
import models.VagaCandidato;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class Candidatos extends Controller {
	private static final Form<Candidato> candidatoForm = Form.form(Candidato.class);
	public static Result GO_HOME = redirect(
	        routes.Candidatos.list(0, "id", "asc", "", "nome")
	    );
	public static Result list(int page, String sortBy, String order, String filter, String atributo) {
        return ok(
        		views.html.candidatos.list.render(
        		Candidato.page(page, 10, sortBy, order, filter, atributo),
                sortBy, order, filter, atributo
            )
        );
    }
	public static Result index() {
        return GO_HOME;
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
			return redirect(routes.Candidatos.list(0, "id", "asc", "", "nome"));	
		}
		catch(PersistenceException pe){
			flash("error",
			        String.format("Ocorreu um erro: %s", pe.getMessage()));
			return redirect(routes.Candidatos.list(0, "id", "asc", "", "nome"));
		}
		
	}
	public static Result delete(Long id) {
	  final Candidato candidato = Candidato.buscarPorId(id);
	  if(candidato == null) {
		  return notFound(String.format("Candidato com id %s não existe.", id));
	  }
	  final VagaCandidato vagaCandidato = Vaga.buscarPorCandidato(id);
	  if(vagaCandidato != null) {
	    return badRequest(String.format("Candidato %s não pode ser excluído.", id));
	  }
	  candidato.delete();
	  return redirect(routes.Candidatos.list(0, "id", "asc", "", "nome"));
	}
}
