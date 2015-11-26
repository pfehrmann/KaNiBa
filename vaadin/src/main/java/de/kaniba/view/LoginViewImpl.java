package de.kaniba.view;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginViewImpl extends CustomComponent implements LoginView, ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1558301678387298316L;
	
	private TextField usernameText;
	private PasswordField passwordText;
	private Button submit;
	
	public LoginViewImpl() {			
			Panel mainPanel = new Panel();
			mainPanel.setWidth("550px");
			
			VerticalLayout cont = new VerticalLayout();
			cont.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
			cont.setSpacing(true);
			
			usernameText = new TextField("Benutzername");
			cont.addComponent(usernameText);

		    passwordText = new PasswordField("Passwort");
		    cont.addComponent(passwordText);
		    
		    submit = new Button("Einloggen");
		    cont.addComponent(submit);
		    
		    mainPanel.setContent(cont);
		    
		    setCompositionRoot(mainPanel);
	}
	
	@Override
	public void click(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDisplay(double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(LoginViewListener listener) {
		// TODO Auto-generated method stub
		
	}

}
