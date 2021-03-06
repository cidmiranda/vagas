package controllers;

import java.util.Date;

import javax.persistence.PersistenceException;

import models.Vaga;
import models.VagaCandidato;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Page;

@Security.Authenticated(Secured.class)
public class VagasCandidatos extends Controller {
	private static final Form<VagaCandidato> vagaCandidatoForm = Form.form(VagaCandidato.class);
	public static Vaga vagaContext;
	
	public static Result candidatosPorVaga(Vaga vaga, int page, String sortBy, String order, String filter, String atributo){
		/*Page<VagaCandidato> vagaCandidatos = VagaCandidato.buscarPorVaga(vaga, page);
		vagaContext = Vaga.buscarPorId(vaga.id);
		return ok(views.html.vagascandidatos.list.render(vagaCandidatos));*/
		vagaContext = Vaga.buscarPorId(vaga.id);
		vaga = vagaContext;
		return ok(
        		views.html.vagascandidatos.list.render(
        		VagaCandidato.page(vaga.id, page, 10, sortBy, order, filter, atributo), vaga,
                sortBy, order, filter, atributo
            )
        );
	}
	
	public static Result novo(Vaga vaga){
		VagaCandidato vagaCandidato = new VagaCandidato();
		vagaContext = Vaga.buscarPorId(vaga.id);
		vagaCandidato.vaga = vagaContext;
		Form<VagaCandidato> filledForm = vagaCandidatoForm.fill(vagaCandidato);
		return ok(views.html.vagascandidatos.detalhes.render(filledForm));
	}
	public static Result detalhes(Long idVaga, Long idCandidato){
		VagaCandidato vagaCandidato = VagaCandidato.buscarPorVagaCandidato(idVaga, idCandidato);
		Form<VagaCandidato> filledForm = vagaCandidatoForm.fill(vagaCandidato);
		vagaContext = Vaga.buscarPorId(vagaCandidato.vaga.id);
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
			  vagaCandidato.dataCriacao = new Date();
			  try  {
				  vagaCandidato.save();
			    } catch(RuntimeException re) {
			    	vagaCandidato.update();
			    }
			flash("success",
			        String.format("Vaga atualizada %s", vagaCandidato));
			return redirect(routes.VagasCandidatos.candidatosPorVaga(vagaCandidato.vaga, 0, "vaga.area.nome", "asc", "", "vaga.area.nome"));
		}catch(PersistenceException pe){
			flash("error",
			        String.format("Ocorreu um erro: %s", pe.getMessage()));
			return badRequest(views.html.vagascandidatos.detalhes.render(boundForm));
		}	
	}
	public static Result delete(Long idVaga, Long idCandidato) {
		try{
				VagaCandidato vagaCandidato = VagaCandidato.buscarPorVagaCandidato(idVaga, idCandidato);
				vagaCandidato.delete();
				return redirect(routes.VagasCandidatos.candidatosPorVaga(vagaCandidato.vaga, 0, "vaga.area.nome", "asc", "", "vaga.area.nome"));
			}
		catch(PersistenceException pe){
			flash("error",
			        String.format("Ocorreu um erro: %s", pe.getMessage()));
			return badRequest(pe.getMessage());
		}
	}
}
