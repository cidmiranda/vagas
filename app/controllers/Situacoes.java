package controllers;

import models.Situacao;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class Situacoes extends Controller {
	private static final Form<Situacao> situacaoForm = Form.form(Situacao.class);
	public static Result index() {
		return redirect(routes.Situacoes.lista(0));
	  }
	public static Result lista(Integer page){
		Page<Situacao> situacoes = Situacao.buscarTodos(page);
		return ok(views.html.situacoes.list.render(situacoes));
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
		return redirect(routes.Situacoes.lista(0));
	}
	public static Result delete(Long id) {
	  final Situacao situacao = Situacao.buscarPorId(id);
	  if(situacao == null) {
		  return ok();
	  }
	  situacao.delete();
	  return redirect(routes.Situacoes.lista(0));
	}
}
