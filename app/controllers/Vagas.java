package controllers;

import models.Area;
import models.Vaga;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class Vagas extends Controller{
	private static final Form<Vaga> vagaForm = Form.form(Vaga.class);
	public static Result index() {
		return redirect(routes.Vagas.lista(0));
	  }
	public static Result lista(Integer page){
		Page<Vaga> vagas = Vaga.buscarTodos(page);
		return ok(views.html.vagas.list.render(vagas));
	}
	public static Result novo(){
		return ok(views.html.vagas.detalhes.render(vagaForm));
	}
	public static Result detalhes(Vaga vaga){
		Form<Vaga> filledForm = vagaForm.fill(vaga);
		return ok(views.html.vagas.detalhes.render(filledForm));
	}
	public static Result vagasPorArea(Area area, Integer page){
		Page<Vaga> vagas = Vaga.buscarPorArea(area.id, page);
		return ok(views.html.vagas.list.render(vagas));
	}
	
	public static Result salvar(){
		Form<Vaga> boundForm = vagaForm.bindFromRequest();
		  if(boundForm.hasErrors()) {
		    flash("error", "Por favor corrija o campo abaixo");
		    return badRequest(views.html.vagas.detalhes.render(boundForm));
		  }
		Vaga vaga = boundForm.get();
		if (vaga.id == null) {
			vaga.save();
	    } else {
	    	vaga.update();
	    }
		flash("success",
		        String.format("Vaga atualizada %s", vaga));
		return redirect(routes.Vagas.lista(0));
	}
	public static Result delete(Long id) {
	  final Vaga vaga = Vaga.buscarPorId(id);
	  if(vaga == null) {
		  return ok();
	  }
	  vaga.delete();
	  return redirect(routes.Vagas.lista(0));
	}
}
