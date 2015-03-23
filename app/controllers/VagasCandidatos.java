package controllers;

import javax.persistence.PersistenceException;

import models.Vaga;
import models.VagaCandidato;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class VagasCandidatos extends Controller {
	private static final Form<VagaCandidato> vagaCandidatoForm = Form.form(VagaCandidato.class);
	
	public static Result candidatosPorVaga(Vaga vaga, Integer page){
		Page<VagaCandidato> vagaCandidatos = VagaCandidato.buscarPorVaga(vaga, page);
		return ok(views.html.vagascandidatos.list.render(vagaCandidatos));
	}
	
	public static Result novo(){
		//Form<VagaCandidato> filledForm = vagaCandidatoForm.fill(vagaCandidato);
		//return ok(views.html.vagascandidatos.detalhes.render(filledForm));
		return ok(views.html.vagascandidatos.detalhes.render(vagaCandidatoForm));
	}
	public static Result detalhes(VagaCandidato vagaCandidato){
		vagaCandidato = VagaCandidato.buscarPorId(vagaCandidato.id);
		Form<VagaCandidato> filledForm = vagaCandidatoForm.fill(vagaCandidato);
		return ok(views.html.vagascandidatos.detalhes.render(filledForm));
	}
	public static Result salvar(){
		Form<VagaCandidato> boundForm = vagaCandidatoForm.bindFromRequest();
		try{
			
			  if(boundForm.hasErrors()) {
			    flash("error", "Por favor corrija o campo abaixo");
			    return badRequest(views.html.vagascandidatos.detalhes.render(boundForm));
			  }
			  VagaCandidato vagaCandidato = boundForm.get();
			  if (vagaCandidato.id == null) {
				  vagaCandidato.save();
			    } else {
			    	vagaCandidato.update();
			    }
			flash("success",
			        String.format("Vaga atualizada %s", vagaCandidato));
			return redirect(routes.VagasCandidatos.candidatosPorVaga(vagaCandidato.vaga,0));
		}catch(PersistenceException pe){
			flash("error",
			        String.format("Ocorreu um erro: %s", pe.getMessage()));
			return badRequest(views.html.vagascandidatos.detalhes.render(boundForm));
		}	
	}
	public static Result delete(Long id) {
		  final VagaCandidato vagaCandidato = VagaCandidato.buscarPorId(id);
		  if(vagaCandidato == null) {
			  return ok();
		  }
		  vagaCandidato.delete();
		  return redirect(routes.VagasCandidatos.candidatosPorVaga(vagaCandidato.vaga,0));
		}
}
