package de.kaniba.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
	private List<LoginViewListener> listeners;
	private String width = "100%";
	
	public LoginViewImpl() {			
			listeners = new ArrayList<LoginViewListener>();
			
			Panel mainPanel = new Panel();
			mainPanel.setWidth("550px");
			
			VerticalLayout cont = new VerticalLayout();
			cont.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
			cont.setSpacing(true);
			cont.addStyleName("login");
			cont.setWidth("95%");
			cont.setHeight("95%");
			
			//cont.addComponent(new Label("Login"));
			
			usernameText = new TextField("Benutzername");
			usernameText.addStyleName("login");
			usernameText.setWidth(width);
			cont.addComponent(usernameText);

		    passwordText = new PasswordField("Passwort");
		    passwordText.addStyleName("login");
		    passwordText.setWidth(width);
		    cont.addComponent(passwordText);
		    
		    submit = new Button("Einloggen");
		    submit.addClickListener(this);
		    submit.addStyleName("login");
		    submit.setWidth(width);
		    cont.addComponent(submit);
		    
		    mainPanel.setContent(cont);
		    setCompositionRoot(mainPanel);
	}
	
	public TextField getUsernameText() {
		return usernameText;
	}

	public PasswordField getPasswordText() {
		return passwordText;
	}

	@Override
	public void addListener(LoginViewListener listener) {
		listeners.add(listener);
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		for(LoginViewListener listener : listeners) {
			listener.click(event);
		}
		
	}
}
