package controllers;

import models.Login;
import models.Usuario;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Logins extends Controller {
	private static final Form<Login> loginForm = Form.form(Login.class);
	public static Result login() {
		return ok(views.html.login.render(loginForm));
	}
	public static Result logoff() {
		session().clear(); 
		return redirect(routes.Logins.login());
	}
	public static Result authenticate() {
		Form<Login> formLogin = loginForm.bindFromRequest();       
		String email = formLogin.get().email;    
		String senha = formLogin.get().senha; 
		Usuario usuario = Usuario.authenticate(email, senha);
		if (usuario == null){ 
			flash("error", "Dados inválidos");
		    return redirect(routes.Logins.login());
		}
		session().clear(); 
		session("email", email);
		session("tipo", usuario.tipo); 
		return redirect(routes.Areas.vagasPorArea(0));
	}
}
