package com.vaadin.devday.mvp.ui;

import com.vaadin.annotations.Widgetset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@Push
@SpringUI
@Theme("valo")
@Widgetset("com.vaadin.devday.DevdayWidgetSet")
public class VaadinUI extends UI {

	@Autowired
	@Lazy
	private NavigationManager navigationManager;

	@Autowired
	private ApplicationViewDisplay viewDisplay;

	@Override
	protected void init(VaadinRequest request) {
		setContent(viewDisplay.getComponentContainer());
		navigationManager.navigateTo(Views.CUSTOMER);
	}
}
