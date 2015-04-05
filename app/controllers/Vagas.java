package controllers;

import models.Area;
import models.Vaga;
import models.VagaCandidato;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class Vagas extends Controller{
	private static final Form<Vaga> vagaForm = Form.form(Vaga.class);
	public static Area areaContext;
	/*public static Result index() {
		return redirect(routes.Vagas.lista(0));
	  }*/
	/*public static Result lista(Integer page){
		Page<Vaga> vagas = Vaga.buscarTodos(page);
		return ok(views.html.vagas.list.render(vagas));
	}*/
	public static Result novo(Area area){
		areaContext = area;
		return ok(views.html.vagas.detalhes.render(vagaForm));
	}
	public static Result detalhes(Vaga vaga){
		vaga = Vaga.buscarPorId(vaga.id);
		System.out.println(">>>>>>>>>"+vaga.area.id);
		areaContext = vaga.area;
		Form<Vaga> filledForm = vagaForm.fill(vaga);
		return ok(views.html.vagas.detalhes.render(filledForm));
	}
	public static Result vagasPorArea(Area area, int page, String sortBy, String order, String filter, String atributo){
		//Page<Vaga> vagas = Vaga.buscarPorArea(area.id, page);
		//return ok(views.html.vagas.list.render(vagas));
		areaContext = area;
		return ok(
        		views.html.vagas.list.render(
        		Vaga.page(area.id, page, 10, sortBy, order, filter, atributo), area,
                sortBy, order, filter, atributo
            )
        );
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
		return redirect(routes.Vagas.vagasPorArea(vaga.area, 0, "id", "asc", "", "cargo.nome"));
	}
	public static Result delete(Long id) {
	  final Vaga vaga = Vaga.buscarPorId(id);
	  if(vaga == null) {
		  return notFound(String.format("Vaga com id %s não existe.", id));
	  }
	  final VagaCandidato vagaCandidato = Vaga.buscarPorVaga(id);
	  if(vagaCandidato != null) {
	    return badRequest(String.format("Vaga %s não pode ser excluída.", id));
	  }
	  vaga.delete();
	  return redirect(routes.Vagas.vagasPorArea(vaga.area, 0, "id", "asc", "", "cargo.nome"));
	}
}
