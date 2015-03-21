package controllers;

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
		return ok(views.html.vagascandidatos.detalhes.render(vagaCandidatoForm));
	}
	public static Result salvar(){
		Form<VagaCandidato> boundForm = vagaCandidatoForm.bindFromRequest();
		  if(boundForm.hasErrors()) {
		    flash("error", "Por favor corrija o campo abaixo");
		    return badRequest(views.html.vagascandidatos.detalhes.render(boundForm));
		  }
		  VagaCandidato vaga = boundForm.get();
		if (vaga.candidato.id == null) {
			vaga.save();
	    } else {
	    	vaga.update();
	    }
		flash("success",
		        String.format("Vaga atualizada %s", vaga));
		return redirect("");
	}
}
