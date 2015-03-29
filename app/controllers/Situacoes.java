package controllers;

import models.Cargo;
import models.Situacao;
import models.Vaga;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class Situacoes extends Controller {
	private static final Form<Situacao> situacaoForm = Form.form(Situacao.class);
	public static Result GO_HOME = redirect(
	        routes.Situacoes.list(0, "id", "asc", "", "nome")
	    );
	public static Result list(int page, String sortBy, String order, String filter, String atributo) {
        return ok(
        		views.html.situacoes.list.render(
        		Situacao.page(page, 10, sortBy, order, filter, atributo),
                sortBy, order, filter, atributo
            )
        );
    }
	public static Result index() {
        return GO_HOME;
    }
	public static Result novo(){
		return ok(views.html.situacoes.detalhes.render(situacaoForm));
	}
	public static Result detalhes(Situacao situacao){
		Form<Situacao> filledForm = situacaoForm.fill(situacao);
		return ok(views.html.situacoes.detalhes.render(filledForm));
	}
	public static Result salvar(){
		Form<Situacao> boundForm = situacaoForm.bindFromRequest();
		  if(boundForm.hasErrors()) {
		    flash("error", "Por favor corrija o campo abaixo");
		    return badRequest(views.html.situacoes.detalhes.render(boundForm));
		  }
		Situacao situacao = boundForm.get();
		if (situacao.id == null) {
			situacao.save();
	    } else {
	    	situacao.update();
	    }
		flash("success",
		        String.format("Situacao atualizada %s", situacao));
		return redirect(routes.Situacoes.list(0, "id", "asc", "", "nome"));
	}
	public static Result delete(Long id) {
	  final Situacao situacao = Situacao.buscarPorId(id);
	  if(situacao == null) {
		  return notFound(String.format("Status com id %s não existe.", id));
	  }
	  final Vaga vaga = Vaga.buscarPorSituacao(id);
	  if(vaga != null) {
	    return badRequest(String.format("Status %s não pode ser excluído.", id));
	  }
	  situacao.delete();
	  return redirect(routes.Situacoes.list(0, "id", "asc", "", "nome"));
	}
}
