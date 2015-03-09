package controllers;

import models.Area;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Page;

public class Areas extends Controller{

	private static final Form<Area> areaForm = Form.form(Area.class);
	public static Result index() {
		return redirect("");
	  }
	public static Result lista(Integer page){
		Page<Area> areaes = Area.buscarTodos(page);
		return ok();
	}
	public static Result novo(){
		return ok();
	}
	public static Result detalhes(Area area){
		Form<Area> filledForm = areaForm.fill(area);
		return ok();
	}
	public static Result salvar(){
		Form<Area> boundForm = areaForm.bindFromRequest();
		  if(boundForm.hasErrors()) {
		    flash("error", "Por favor corrija o campo abaixo");
		    return badRequest();
		  }
		Area area = boundForm.get();
		if (area.id == null) {
			area.save();
	    } else {
	    	area.update();
	    }
		flash("success",
		        String.format("Area atualizada %s", area));
		return redirect("");
	}
	public static Result delete(Long id) {
	  final Area area = Area.buscarPorId(id);
	  if(area == null) {
		  return ok();
	  }
	  area.delete();
	  return redirect("");
	}
}
