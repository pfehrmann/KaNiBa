package de.kaniba.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
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
	private String relativeWidth = "100%";
	private String width = "300px";
	private Panel mainPanel;
	public LoginViewImpl() {			
			listeners = new ArrayList<LoginViewListener>();
			
			mainPanel = new Panel();
			mainPanel.setWidth(width);
			
			VerticalLayout cont = new VerticalLayout();
			cont.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
			cont.setSpacing(true);
			cont.addStyleName("login-panel");
			
			usernameText = new TextField("Benutzername");
			usernameText.setInputPrompt("Benutzername");
			usernameText.setWidth(relativeWidth);
			cont.addComponent(usernameText);

		    passwordText = new PasswordField("Passwort");
		    passwordText.setInputPrompt("Passwort");
		    passwordText.setWidth(relativeWidth);
		    cont.addComponent(passwordText);
		    
		    submit = new Button("Einloggen");
		    submit.addClickListener(this);
		    submit.setWidth(relativeWidth);
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

	@Override
	public void setFormWidth(String width) {
		this.width = width;
		mainPanel.setWidth(width);
	}

	@Override
	public String getFormWidth() {
		return width;
	}
}
